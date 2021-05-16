package com.example.shirospringboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @GetMapping("add")
    public String toAdd(Model model) {
        model.addAttribute("name", "LiJieYao");
        return "user/add";
    }

    @GetMapping("update")
    public String toUpdate(Model model) {
        model.addAttribute("name", "LiJieYao");
        return "user/update";
    }

    @GetMapping("toLogin")
    public String toLogin(Model model) {
        return "user/login";
    }

    @RequestMapping("login")
    public String toLogin(String userName, String password, Model model) {
        // 获取当前用户信息
        Subject subject = SecurityUtils.getSubject();
        // 封装当前用户登录信息
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            // 登录
            subject.login(token);
            return "index";
        } catch (AuthenticationException e) {
            log.info("UserLogin error:", e);
            model.addAttribute("msg", "登录名或密码错误！");
            return "user/login";
        }
    }
}
