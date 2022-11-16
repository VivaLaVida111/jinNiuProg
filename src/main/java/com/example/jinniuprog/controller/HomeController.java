package com.example.jinniuprog.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.jinniuprog.common.CommonResult;
import com.example.jinniuprog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import cn.hutool.http.*;

import com.example.jinniuprog.service.JsonService;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;

@RestController
public class HomeController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired

    JsonService jsonService;
    @RequestMapping("/getDeptList")
    public CommonResult<JSONArray> getDeptList(){
        String url = "static/depts.json";
        JSONArray allJSON = jsonService.parseJson(url);
       /* allJSON.getJSONObject(5).getJSONArray("systems").getJSONObject(0).put("api","/yyxt/mainInfo");
        System.out.println("request:"+allJSON.getJSONObject(5).getJSONArray("systems").getJSONObject(0).replace("api","/yyxt/mainInfo"));
        */return CommonResult.success(allJSON);
    }
    @RequestMapping("/getSystemsList")
    public CommonResult<JSONArray> getSystemList(){
        String url = "static/systems.json";
        JSONArray allJson = jsonService.parseJson(url);
        String loginUrl = "http://101.37.246.72:8079/api/auth/login";
        JSONObject loginJson = new JSONObject();
        loginJson.put("name", "lyh");
        // 其他信息
        loginJson.put("password", "string");
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36 Edg/107.0.1418.35")
                .header(Header.ACCEPT, "application/json, text/plain, */*")
                .header(Header.CONTENT_TYPE, "application/json")
                .header(Header.CONTENT_LENGTH, "225")
                .header(Header.ORIGIN, "http://101.37.246.72:8079")
                .header(Header.HOST,"101.37.246.72:8079")
//                .header(Header.COOKIE,"HOTIME=bfd0e06c92818105854d1a9ac17bc90a")
                .header(Header.REFERER,"http://101.37.246.72:8079/login/")

                .header("isToken","false")
                .header("TENANT-ID","1")
                .body(JSONObject.toJSONString(loginJson))
                .execute();



        System.out.println(loginRes.body());

        return CommonResult.success(allJson);

    }
    @RequestMapping("/getCookie")
    public JSONObject getCookie(String url){
        HashMap<String, Object> loginParam = new HashMap<>();
        // 用户名 金牛区管理员刘磊 前端有一个des加密 这是前端直接加密好的结果
        loginParam.put("u", "F5l8jhNK7iDbfc9WTXMPRECj+YgDipnLCoOT0GPuct39A0RiZQ/wJyS8zXFg8ogrJMxKmoGHE3UBuGk3k+bmPnpVgS+dRNsWWYg1VvvspUo=");
        // 密码 Qq123456 下面也是前端加密好的结果
        loginParam.put("p", "N8V9yV9NQFVb8CxUPEFocg==");
        // 其他信息
        loginParam.put("browserVersion", "firefox/101.0");
        loginParam.put("osVersion", "Mac");
        loginParam.put("validCode", "");
        loginParam.put("ip", "");
        loginParam.put("validWay", "0");
        String loginUrl = "http://171.221.172.74:6888/eUrbanMIS/login/validpassword";
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.CONTENT_LENGTH, "225")
                .header(Header.HOST, "171.221.172.74:6888")
                .header(Header.ORIGIN, "http://171.221.172.74:6888")
                .header(Header.REFERER, "http://171.221.172.74:6888/eUrbanMIS/main.htm")
                .form(loginParam)
                .execute();
        List<HttpCookie> cookies = loginRes.getCookies();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",cookies.get(0));
        System.out.println(cookies.toString());
        return jsonObject;

    }

    void loginSubSystem(User user){
//mis-login-password//id="mis-login-user-name"
//    HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        JSONObject param1 = new JSONObject();
//        param1.put("account","13908173345");
//        param1.put("pwd","9876");
//
//        HttpEntity<String> request1 = new HttpEntity<String>(param1.toJSONString(),headers);
//        String result1 = restTemplate.postForObject(loginUrl,request1,String.class);
//        JSONObject resultJson = JSON.parseObject(result1);
//        String responseCookie =resultJson.get("data").toString();
//        Cookie cookie = new Cookie("name",responseCookie);
//        cookie.setDomain("47.93.222.102");
//        cookie.setPath("/");
//        response.addCookie(cookie);
    }
}
