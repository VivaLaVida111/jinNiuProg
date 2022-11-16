package com.example.jinniuprog.controller;

import com.example.jinniuprog.service.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
@RestController
@RequestMapping("/ddzh")
public class DdzhController {
    Date calDate= new Date(System.currentTimeMillis());
    SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String time = formatter_time.format(calDate);
    @Autowired
    JsonService jsonService;
}
