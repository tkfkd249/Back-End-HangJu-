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

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/hellomain")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello-main");
    }

    @GetMapping("/getAllList")
    public ResponseEntity<String> getAllList() throws ParseException, IOException {

        // 행복주택인 항목만 리턴해주기
        // PAN_ID 구할 수 있음
        LHApi lhApi = new LHApi();

        String allResult = lhApi.getLhList();
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray)jsonParser.parse(allResult);
        JSONObject jsonObject = (JSONObject) jsonArray.get(1);
        JSONArray dsList = (JSONArray) jsonObject.get("dsList");

        //AIS_TP_CD_NM이 행복주택인 항목만 꺼내기
        JSONArray filterArray = new JSONArray();
        for(int i=0;i<dsList.size();i++){
            JSONObject obj = (JSONObject) dsList.get(i);
            if(obj.get("AIS_TP_CD_NM").equals("행복주택")){
                filterArray.add(dsList.get(i));
            }
        }

        //해당항목 상세 주소 구해주기
        JSONArray addrArray = new JSONArray();
//        for(int i=0;i<filterArray.size();i++){
//            JSONObject info = (JSONObject) filterArray.get(i);
//            String addressInfo = lhApi.getLhDetailList(info);
//            jsonArray = (JSONArray)jsonParser.parse(addressInfo);
//            JSONObject infoAddr = (JSONObject) jsonArray.get(1);
//            JSONArray list = (JSONArray) jsonObject.get("dsSch");
//            addrArray.add(list);//LGDN_ADR,LGDN_DTL_ADR
//        }

        JSONArray returnArray = new JSONArray();
        //Geocoder이용하여 위도, 경도 구해주기



        return ResponseEntity.ok("test");
    }

    @PostMapping("/insertHangInfo")
    public ResponseEntity<String> insertHangInfo() throws ParseException, IOException {

        //마스터 정보 넣기
        mainService.deleteAllHangMaster();
        // 행복주택인 항목만 리턴해주기
        LHApi lhApi = new LHApi();

        String allResult = lhApi.getLhList();
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray)jsonParser.parse(allResult);
        JSONObject jsonObject = (JSONObject) jsonArray.get(1);
        JSONArray dsList = (JSONArray) jsonObject.get("dsList");
        String dsListStr = dsList.toJSONString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<HangMasterDto> masterList = Arrays.asList(objectMapper.readValue(dsListStr, HangMasterDto[].class));
        //obj.get("AIS_TP_CD_NM").equals("행복주택"

        //AIS_TP_CD_NM이 행복주택인 항목만 꺼내기
//        for(int i=0;i<dsList.size();i++){
//            JSONObject obj = (JSONObject) dsList.get(i);
//            if(obj.get("AIS_TP_CD_NM").equals("행복주택")){
//
//                HangMaster hangMaster = new HangMaster();
//                hangMaster.setPanId(obj.get("PAN_ID").toString());
//                hangMaster.setUppAisTpNm(obj.get("UPP_AIS_TP_NM").toString());
//                hangMaster.setAisTpCdNm(obj.get("AIS_TP_CD_NM").toString());
//                hangMaster.setPanNm(obj.get("PAN_NM").toString());
//                hangMaster.setCnpCdNm(obj.get("CNP_CD_NM").toString());
//                hangMaster.setPanNtStDt(obj.get("PAN_NT_ST_DT").toString());
//                hangMaster.setClsgDt(obj.get("CLSG_DT").toString());
//                hangMaster.setPanSs(obj.get("PAN_SS").toString());
//                hangMaster.setDtlUrl(obj.get("DTL_URL").toString());
//                hangMaster.setSplInfTpCd(obj.get("SPL_INF_TP_CD").toString());
//                hangMaster.setCcrCnntSysDsCd(obj.get("CCR_CNNT_SYS_DS_CD").toString());
//                hangMaster.setUppAisTpCd(obj.get("UPP_AIS_TP_CD").toString());
//                hangMaster.setAisTpCd(obj.get("AIS_TP_CD").toString());
//                hangMaster.setPanDt(obj.get("PAN_DT").toString());
//
//                masterList.add(hangMaster);
//            }
//        }
//        mainService.insertHangMasterInfo(masterList);

        //세부정보
        for(int i=0;i<masterList.size();i++){
            String detaiilAll = lhApi.getLhDetailList(masterList.get(i));
            JSONArray addrArray = new JSONArray();
            jsonArray = (JSONArray)jsonParser.parse(detaiilAll);
            JSONObject infoObj = (JSONObject) jsonArray.get(1);

            //주소
            JSONArray dsSplScdlList = (JSONArray) infoObj.get("dsSbd");
            for(int j=0;j<dsSplScdlList.size();j++){

                JSONObject obj = (JSONObject) dsSplScdlList.get(i);
                String dsSbdStr = obj.toJSONString();
                DsSbdDto dsSbdDto = objectMapper.readValue(dsSbdStr, DsSbdDto.class);
                dsSbdDto.setPanId(masterList.get(i).getPanId());
                dsSbdDto.setInfoIndex(j+"");
//                dsSbd.setLgdnAdr(obj.get("LGDN_ADR").toString());
//                dsSbd.setLgdnDtlAdr(obj.get("LGDN_DTL_ADR").toString());
//                dsSbd.setDdoAr(obj.get("DDO_AR").toString());
//                dsSbd.setHshCnt(obj.get("HSH_CNT").toString());
//                dsSbd.setHtnFmlaDesc(obj.get("HTN_FMLA_DESC").toString());
//                dsSbd.setMvinXpcYm(obj.get("MVIN_XPC_YM").toString());

                //위도, 경도 가져오기
                try {

                    String location = obj.get("LGDN_ADR").toString();

                    String addr = "https://dapi.kakao.com/v2/local/search/address.json";

                    String apiKey = "KakaoAK 7288796e557444ab852533e37aabdd5e";

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

                    if(docJson.length()>0){
//
//                        jsonArray = (JSONArray)jsonParser.parse(docJson.toString());
//                        JSONObject docObj = (JSONObject) jsonArray.get(0).toString();
//                        JSONObject tempObj = (JSONObject) jsonArray.get(0);
//
//                        System.out.println("latitude : " + tempObj.getDouble("y"));
//                        System.out.println("longitude : " + tempObj.getDouble("x"));
                    }

                }catch(Exception e) {
                    e.printStackTrace();
                }

            }

        }
        return ResponseEntity.ok("ok");
    }

}
