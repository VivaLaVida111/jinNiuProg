package com.example.jinniuprog.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.jinniuprog.Util.DateUtil;
import com.example.jinniuprog.common.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/ycxt")
public class YcxtController {
    @RequestMapping("/mainInfo")
    public CommonResult<JSONArray> getMainInfo(){
        Date calDate= new Date(System.currentTimeMillis());
        long time = System.currentTimeMillis()/1000;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        JSONArray data = new JSONArray();
        JSONObject loginJson = new JSONObject();
        loginJson.put("username","18280097871");
        loginJson.put("password","39a31f5ab6bb2a6447a678cc1f4a85ab");
        loginJson.put("visitorId","60f23931fdccfee1292cafe04bfa3019");
        loginJson.put("timestamp",time);
        /*
         * END 构造登陆请求中要用到的参数
         */
        // 构造执行登陆请求 发起HTTP POST请求
        String loginUrl = "http://221.237.182.175:8016/-/user/login";
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.CONTENT_LENGTH, "225")
                .body(JSONObject.toJSONString(loginJson))
                .execute();
        List<HttpCookie> cookies = loginRes.getCookies();
        System.out.println(loginRes.body());
        //System.out.println(loginRes.getCookies().toString());

        return CommonResult.success(data);

    }

    @RequestMapping("/result")
    public JSONObject getResult(){
        Date calDate= new Date(System.currentTimeMillis());
        long time = System.currentTimeMillis()/1000;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        JSONArray data = new JSONArray();
        JSONObject loginJson = new JSONObject();
        loginJson.put("username","18280097871");
        loginJson.put("password","39a31f5ab6bb2a6447a678cc1f4a85ab");
        loginJson.put("visitorId","60f23931fdccfee1292cafe04bfa3019");
        loginJson.put("timestamp",time);
        /*
         * END 构造登陆请求中要用到的参数
         */
        // 构造执行登陆请求 发起HTTP POST请求
        String loginUrl = "http://221.237.182.175:8016/-/user/login";
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.CONTENT_LENGTH, "225")
                .body(JSONObject.toJSONString(loginJson))
                .execute();
        List<HttpCookie> cookies = loginRes.getCookies();
        HttpCookie cookie = loginRes.getCookie("passport_login");
        String key = JSON.parseObject(loginRes.body()).getString("key");
        System.out.println(key);
        System.out.println(cookie);

        //System.out.println(loginRes.getCookies().toString());

       String queryUrl =  "http://221.237.182.175:8016/-/emission/get-building-lists";
        JSONObject queryJson = new JSONObject();
        JSONObject makeJson= new JSONObject();
        queryJson.put("areaID",5);
        queryJson.put("key",key);
        queryJson.put("expiresAt",1658502350);
        HttpResponse queryRes = HttpRequest.post(queryUrl)
                .header("shomes-user","43444")
                .header("shomes-type","city_web")
                .header("shomes-sign","edd3a03779424855831395b170fdb5c8")
                .header("key",key)
                .header(Header.HOST,"221.237.182.175:8016")
                .header(Header.ORIGIN,"http://221.237.182.174:8011")
                .header(Header.REFERER,"http://221.237.182.174:8011/")
                .body(JSONObject.toJSONString(queryJson))
                .cookie("Njc5MzMzNzk5LGFtYXBGcGxLZEpCWixub3Uyc3B6azJydGJsM20zNnkyZmF6dzRncGpkZHloZCwxNjU5NDA2ODM3LE5EUXdOakk1WXpKbU5tUXpaREZrWVRrNE56RmhZekprT0RJM1l6Rmtaams9")
                .execute();
        JSONObject queryJsonRes = JSON.parseObject(queryRes.body());

        return JSON.parseObject(queryRes.body());
    }


}
