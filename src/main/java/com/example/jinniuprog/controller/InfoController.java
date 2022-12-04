package com.example.jinniuprog.controller;

import com.example.jinniuprog.service.InfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("//info")
public class InfoController {
    @Resource
    private InfoService infoService;

    @GetMapping
    public Map<String, String> getInfo() {
        return infoService.getInfo();
    }
}

