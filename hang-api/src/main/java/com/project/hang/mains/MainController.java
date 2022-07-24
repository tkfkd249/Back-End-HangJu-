package com.project.hang.mains;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;
import com.project.hang.api.LHApi;
import com.project.hang.dto.DsSbdDto;
import com.project.hang.dto.HangMasterDto;
import com.project.hang.entity.HangMaster;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/main")
public class MainController {

    private final MainService mainService;

    @Value("${apikey.master}")
    private String master;

    @Value("${apikey.detail}")
    private String detail;

    @Value("${apikey.kakao}")
    private String kakao;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/hellomain")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello-main");
    }

    @GetMapping("/getAllList")
    public ResponseEntity<List<HangMaster>> getHangMasterInfo() throws ParseException, IOException {
        return ResponseEntity.ok().body(mainService.getHangMasterInfo());
    }

    @PostMapping("/insertHangInfo")
    public ResponseEntity<String> insertHangInfo() throws ParseException, IOException {

        //마스터 정보 넣기
        mainService.deleteAllHangMaster();
        mainService.deleteAllHangDetail();
        // 행복주택인 항목만 리턴해주기
        LHApi lhApi = new LHApi();

        String allResult = lhApi.getLhList(master);
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray)jsonParser.parse(allResult);
        JSONObject jsonObject = (JSONObject) jsonArray.get(1);
        JSONArray dsList = (JSONArray) jsonObject.get("dsList");
        String dsListStr = dsList.toJSONString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<HangMasterDto> masterList = Arrays.asList(objectMapper.readValue(dsListStr, HangMasterDto[].class));

        mainService.insertHangMasterInfo(masterList);
        //세부정보
        for(int i=0;i<masterList.size();i++){
            String detaiilAll = lhApi.getLhDetailList(masterList.get(i),detail);
            JSONArray addrArray = new JSONArray();
            jsonArray = (JSONArray)jsonParser.parse(detaiilAll);
            JSONObject infoObj = (JSONObject) jsonArray.get(1);

            //주소
            if(infoObj.get("dsSbd") != null){

                JSONArray dsSplScdlList = (JSONArray) infoObj.get("dsSbd");

                for(int j=0;j<dsSplScdlList.size();j++){

                    JSONObject obj = (JSONObject) dsSplScdlList.get(j);
                    String dsSbdStr = obj.toJSONString();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    DsSbdDto dsSbdDto = objectMapper.readValue(dsSbdStr, DsSbdDto.class);
                    dsSbdDto.setPanId(masterList.get(i).getPanId());
                    dsSbdDto.setInfoIndex(j+"");

                    //위도, 경도 가져오기
                    try {
                        String addr = "https://dapi.kakao.com/v2/local/search/address.json";
                        String apiKey = "KakaoAK "+ kakao;

                        if(obj.get("LGDN_ADR") != null){
                            String location = obj.get("LGDN_ADR").toString();
                            location = URLEncoder.encode(location, "UTF-8");

                            String query = "query=" + location;

                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append(addr);
                            stringBuffer.append("?");
                            stringBuffer.append(query);

                            System.out.println("stringBuffer.toString() "+ stringBuffer.toString());

                            URL url = new URL(stringBuffer.toString());

                            URLConnection conn = url.openConnection();

                            conn.setRequestProperty("Authorization", apiKey);

                            BufferedReader rd = null;

                            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                            StringBuffer docJson = new StringBuffer();

                            String line;

                            while((line=rd.readLine())!=null){
                                docJson.append(line);
                            }

                            rd.close();
                            JSONParser paser = new JSONParser();
                            if(docJson.length()>0){
                                JSONObject docObj = (JSONObject) paser.parse(docJson.toString());
                                JSONArray data = (JSONArray) docObj.get("documents");
                                JSONObject jsonX = (JSONObject) data.get(0);
                                dsSbdDto.setLatitude(jsonX.get("x").toString());
                                dsSbdDto.setLongitude(jsonX.get("y").toString());
                            }
                        }


                    }catch(Exception e) {
                        e.printStackTrace();
                    }


                    mainService.insertDsSbdInfo(dsSbdDto);

                }

            }

        }
        return ResponseEntity.ok("ok");
    }

}
