package com.project.hang.mains;

import com.project.hang.api.LHApi;
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
import java.io.IOException;
import java.util.ArrayList;
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

        List<HangMaster> masterList = new ArrayList<>();
        //AIS_TP_CD_NM이 행복주택인 항목만 꺼내기
        for(int i=0;i<dsList.size();i++){
            JSONObject obj = (JSONObject) dsList.get(i);
            if(obj.get("AIS_TP_CD_NM").equals("행복주택")){

                HangMaster hangMaster = new HangMaster();
                hangMaster.setPanId(obj.get("PAN_ID").toString());
                hangMaster.setUppAisTpNm(obj.get("UPP_AIS_TP_NM").toString());
                hangMaster.setAisTpCdNm(obj.get("AIS_TP_CD_NM").toString());
                hangMaster.setPanNm(obj.get("PAN_NM").toString());
                hangMaster.setCnpCdNm(obj.get("CNP_CD_NM").toString());
                hangMaster.setPanNtStDt(obj.get("PAN_NT_ST_DT").toString());
                hangMaster.setClsgDt(obj.get("CLSG_DT").toString());
                hangMaster.setPanSs(obj.get("PAN_SS").toString());
                hangMaster.setDtlUrl(obj.get("DTL_URL").toString());
                hangMaster.setSplInfTpCd(obj.get("SPL_INF_TP_CD").toString());
                hangMaster.setCcrCnntSysDsCd(obj.get("CCR_CNNT_SYS_DS_CD").toString());
                hangMaster.setUppAisTpCd(obj.get("UPP_AIS_TP_CD").toString());
                hangMaster.setAisTpCd(obj.get("AIS_TP_CD").toString());
                hangMaster.setPanDt(obj.get("PAN_DT").toString());

                masterList.add(hangMaster);
            }
        }
        mainService.insertHangMasterInfo(masterList);
        return ResponseEntity.ok("ok");
    }

}
