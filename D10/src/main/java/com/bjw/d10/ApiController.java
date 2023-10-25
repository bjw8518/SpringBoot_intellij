package com.bjw.d10;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ApiController {
    @GetMapping("/")
    public String home() {
        return "hello";
    }

    @GetMapping("create")
    public String createTable() {
        DB<Vote> db = new DB<Vote>("c:/kopo/p1.db", "Vote", new Vote());
        db.createTable();
        return "created";
    }

    @GetMapping("insert")
    public String insertData(@RequestParam("target") String target, @RequestParam("sex") String sex, @RequestParam("age") int age) {
        DB<Vote> db = new DB<Vote>("c:/kopo/p1.db", "Vote", new Vote());
        db.insertData(new Vote(target, sex, age));
        return "done";
    }

    @GetMapping("r1")
    public HashMap<String, Integer> r1() {
        DB<Vote> db = new DB<Vote>("c:/kopo/p1.db", "Vote", new Vote());
        ArrayList<Vote> list = db.selectData();

        int countA = 0;
        int countB = 0;
        int countC = 0;
        int countD = 0;
        int countE = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).target.equals("A")) {
                countA++;
            } else if (list.get(i).target.equals("B")) {
                countB++;
            } else if (list.get(i).target.equals("C")) {
                countC++;
            } else if (list.get(i).target.equals("D")) {
                countD++;
            } else if (list.get(i).target.equals("E")) {
                countE++;
            }
        }

        HashMap<String, Integer> result = new HashMap<String, Integer>();
        result.put("count_a", countA);
        result.put("count_b", countB);
        result.put("count_c", countC);
        result.put("count_d", countD);
        result.put("count_e", countE);
        return result;
    }

    // @GetMapping("update")
    // public String updateData(@RequestParam("idx") int idx, @RequestParam("name") String name, @RequestParam("sex") String sex) {
    //     DB<Vote> db = new DB<Vote>("c:/kopo/p1.db", "Vote", new Vote());
    //     db.updateData(new Vote(idx, name, sex));
    //     return "done";
    // }

    // @GetMapping("delete")
    // public String deleteData(@RequestParam("idx") int idx) {
    //     DB<Vote> db = new DB<Vote>("c:/kopo/p1.db", "Vote", new Vote());
    //     db.deleteData(new Vote(idx));
    //     return "done";
    // }

    // @GetMapping("select")
    // public ArrayList<?> selectData() {
    //     DB<Vote> db = new DB<Vote>("c:/kopo/p1.db", "Vote", new Vote());
    //     ArrayList<?> list = db.selectData();
    //     return list;
    // }

    // @GetMapping("detail")
    // public Vote detailsData(@RequestParam("idx") int idx) {
    //     DB<Vote> db = new DB<Vote>("c:/kopo/p1.db", "Vote", new Vote());
    //     Vote data = db.detailData(new Vote(idx));
    //     return data;
    // }
}
