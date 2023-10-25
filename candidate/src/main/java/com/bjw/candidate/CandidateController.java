//CandidateController.java
package com.bjw.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class CandidateController {
    @Autowired
    private DB db;

    @GetMapping("/create")
    public String createTable() {
        db.createTable();
        return "redirect:/";
    }


    @GetMapping("/")
    public String index(Model model) {
        ArrayList<Candidate> CandidateList = db.selectData();
        model.addAttribute("CandidateList", CandidateList);
        return "index";
    }

    @GetMapping("/vote")
    public String showAddForm() {
        return "vote";
    }

    @PostMapping("/vote")
    public String voteCandidate(
            @RequestParam("sex") String sex,
            @RequestParam("age") int age,
            @RequestParam("vote") String vote) {

        db.insertData(sex, age, vote);
        return "redirect:/";
    }

    @GetMapping("/delete/{idx}")
    public String deleteCandidate(@PathVariable int idx) {
        db.deleteData(idx);
        return "redirect:/";
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {
        return "statistics";
    }


//통계



}
