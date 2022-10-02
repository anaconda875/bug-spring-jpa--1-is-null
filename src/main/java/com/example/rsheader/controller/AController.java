package com.example.rsheader.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
public class AController {

    @GetMapping("/bar")
    public String a(String q) {
        return "bar";
    }

//    @GetMapping("/bar/1")
//    public String b(String q) {
//        return q;
//    }

}
