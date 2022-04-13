package com.example.shirotest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class T1 {

    @GetMapping("/main")
    @ResponseBody
    public String main()
    {
        return "dddd";
    }
    @GetMapping("/main1")
    @ResponseBody
    public String main1()
    {
        return "dddd111";
    }
    @GetMapping("/login")
//    @ResponseBody
    public String login()
    {
        return "index";
    }
}
