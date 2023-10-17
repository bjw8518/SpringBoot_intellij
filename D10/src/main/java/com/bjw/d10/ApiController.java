package com.bjw.d10;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/")
    public String home() {
        return "hello";
    }

    @GetMapping("create")
    public String createTable() {
        // DB<People> db = new DB<People>("c:/kopo/p1.db", "people", new People());
        DB<Goods> db = new DB<Goods>("c:/kopo/p1.db", "goods", new Goods());
        db.createTable();
        return "created";
    }
}
