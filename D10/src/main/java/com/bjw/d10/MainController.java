package com.bjw.d10;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class MainController {
    @GetMapping("/vote")
    public String vote() {
        return "vote";
    }

    @GetMapping("/result")
    public String result(Model model) {
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
        model.addAttribute("count_a", countA);
        model.addAttribute("count_b", countB);
        model.addAttribute("count_c", countC);
        model.addAttribute("count_d", countD);
        model.addAttribute("count_e", countE);
        return "result";
    }
}
