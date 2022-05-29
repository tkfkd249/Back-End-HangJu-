package com.project.hang.mains;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.project.hang.api.LHApi;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequestMapping("/main")
public class MainController {

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
        for(int i=0;i<filterArray.size();i++){
            JSONObject info = (JSONObject) filterArray.get(i);
            String addressInfo = lhApi.getLhHomeAdrees(info);
            jsonArray = (JSONArray)jsonParser.parse(addressInfo);
            JSONObject infoAddr = (JSONObject) jsonArray.get(1);
            JSONArray list = (JSONArray) jsonObject.get("dsSch");
            addrArray.add(list);//LGDN_ADR,LGDN_DTL_ADR
        }

        JSONArray returnArray = new JSONArray();
        //Geocoder이용하여 위도, 경도 구해주기



        return ResponseEntity.ok("test");
    }
}
