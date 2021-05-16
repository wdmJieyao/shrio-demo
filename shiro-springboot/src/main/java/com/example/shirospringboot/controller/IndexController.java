package com.example.shirospringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String toIndex(Model model) {
        model.addAttribute("name", "LiJieYao");
        return "index";
    }
}
