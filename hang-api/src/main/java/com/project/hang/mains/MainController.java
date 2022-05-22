package com.project.hang.mains;

import com.project.hang.api.LHApi;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
    public ResponseEntity<String> getAllList() {

        // 행복주택인 항목만 리턴해주기
        // PAN_ID 구할 수 있음
        LHApi lhApi = new LHApi();
        try {
            String result = lhApi.getLhList();
            JSONParser jsonParser = new JSONParser();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("test");
    }
}
