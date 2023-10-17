package com.bjw.color;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ColorController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/list")
    public String histories() {
        return "list";
    }
}
