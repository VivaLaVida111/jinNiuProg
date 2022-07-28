package com.example.jinniuprog.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.jinniuprog.entity.User;
import com.example.jinniuprog.service.ServiceImpl.UserServiceImpl;
import com.example.jinniuprog.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController

@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userservice;

    @RequestMapping("/queryAll")
    public List<User> queryAll(){
        return userservice.queryAll();
    }
    @RequestMapping("/getUserById")
    public String getUserById( int id){
        return userservice.getUserById(id);
    }
    @RequestMapping("/findByUserName")
    public User findByUserName(String username){
        return  userservice.findByUserName(username);
    }
    @PostMapping("/updatePassword")

    public String updatePassword(@RequestBody User user){


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
        /*
         * END 构造登陆请求中要用到的参数
         */
        // 构造执行登陆请求 发起HTTP POST请求
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

        // 取出相应中的cookie
        // 调试发现不用手动取出给予也能正常访问，估计是库底层自动保存Cookie了，如果后面发现请求案卷失败再试试手动把登陆获取的Cookie塞进去
         //List<HttpCookie> cookies = loginRes.getCookies();

        /*
         * 请求案卷内容
         */

        // START 构造请求案卷的参数信息
        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("queryID", "2");
        queryParam.put("condparams", "");
        queryParam.put("condInfo", "");
        queryParam.put("headerColumns", "");
        queryParam.put("page", "1");
        queryParam.put("rows", "100");
        // END 构造请求案卷的参数信息

        // 开始请求案卷
        String queryUrl = "http://171.221.172.74:6888/eUrbanMIS/home/stat/query/getqueryresult";
        HttpResponse queryRes = HttpRequest.post(queryUrl)
                .form(queryParam)
                .execute();
        JSONObject queryResJson =  new JSONObject();
        queryResJson = JSON.parseObject(queryRes.body());

        //System.out.println(queryResJson.get("rows"));
        return userservice.updatePassword(user);
    }


}
