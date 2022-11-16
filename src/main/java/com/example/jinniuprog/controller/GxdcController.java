package com.example.jinniuprog.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.jinniuprog.common.CommonResult;
import com.example.jinniuprog.service.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/gxdc")
public class GxdcController {
    Date calDate= new Date(System.currentTimeMillis());
    SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String time = formatter_time.format(calDate);
    @Autowired
    JsonService jsonService;
    @CrossOrigin(origins = "*")
    @RequestMapping("/token")
    public CommonResult<String> getGxdcToken(){
        String loginUrl = "http://jnbicycle.hoteas.com/admin/hotime/login";
        HashMap<String, Object> loginParam = new HashMap<>();
        loginParam.put("name","admin");
        loginParam.put("password","123456");
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.CONTENT_LENGTH, "225")

                .form(loginParam)
                .execute();
        List<HttpCookie> cookies = loginRes.getCookies();
        String token = new String();
        token = JSONObject.parseObject(loginRes.body()).getJSONObject("result").getString("token");



        return CommonResult.success(token);
    }
}
