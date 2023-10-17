package com.bjw.shopping;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShoppingController {
    @Autowired
    private DB db;

    @GetMapping("/create")
    public String createTable() {
        db.createTable();
        return "redirect:/";
    }


    @GetMapping("/")
    public String index(Model model) {
        ArrayList<Shopping> shopList = db.selectData();
        model.addAttribute("shopList", shopList);
        return "index";
    }
    @GetMapping("/regi")
    public String regi(Model model) {
        return "regi";
    }


    @GetMapping("/add")
    public String showAddForm() {
        return "add";
    }

    @PostMapping("/add")
    public String addProduct(
            @RequestParam("article") String article,
            @RequestParam("category") String category, // 변경된 부분
            @RequestParam("price") int price) {
        db.insertData(article, category, price);
        return "redirect:/";
    }

    @GetMapping("/delete/{idx}")
    public String deleteProduct(@PathVariable int idx) {
        db.deleteData(idx);
        return "redirect:/";
    }
}
