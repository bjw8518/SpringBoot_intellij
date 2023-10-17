package com.bjw.color;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {
    @GetMapping("create")
    public String createTable() {
        DB db = new DB();
        db.createTable();
        return "테이블이 생성되었습니다.";
    }

    @GetMapping("/select_color")
    public HashMap<String, String> insertCode(@RequestParam(value="code", defaultValue="") String code) {
        DB db = new DB();
        db.insertData(code);
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("message", "success");
        return result;
    }

    @GetMapping("histories_api")
    public ArrayList<Color> hiApi() {
        DB db = new DB();
        ArrayList<Color> result = db.selectData();
        return result;
    }
}
