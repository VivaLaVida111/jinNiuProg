package com.example.jinniuprog.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.jinniuprog.common.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/cclj")
public class CcljController {
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
        HashMap<String, Object> loginParam = new HashMap<>();
        loginParam.put("username", "qinyp");
        // 其他信息
        loginParam.put("password", "007007");

        /*
         * END 构造登陆请求中要用到的参数
         */
        // 构造执行登陆请求 发起HTTP POST请求
        String loginUrl = "http://jinniu-admin.xbase.com.cn/login/index/check_login?username=qinyp&password=007007";
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.CONTENT_LENGTH, "225")
                .form(loginParam)
                .execute();
        List<HttpCookie> cookies = loginRes.getCookies();

        //System.out.println(loginRes.getCookies().toString());
        String queryUrl = "http://jinniu-admin.xbase.com.cn/home/v2/getSumData/from/gov_jinniu";
        HttpResponse queryRes = HttpRequest.get(queryUrl)
                .header("access_token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ4YmFzZS5jb20uY24iLCJhdWQiOiJ0cmFucyIsImlhdCI6MTY2NzM4MDY1MSwibmJmIjoxNjY3MzgwNjUxLCJleHAiOjE2NjczODQyNTEsInVzZXJfaWQiOiJDMkYxMjk3NDFfMjIzOF9BQzNGX0FDODU0NTNGMjNBOSIsInVzZXJfc2l0ZXNfaWQiOiIiLCJ1c2VyX25hbWUiOiJcdTc5ZTZcdTZjMzhcdTVlNzMiLCJ1c2VyX21vYmlsZSI6IjE4MTEzMDIwNzEzIn0.DlopjGzjt6sCK_taLqM_nPNApQNAhqtxFFaT9kFwBdo")
                .execute();
        JSONObject queryJson = JSONObject.parseObject(queryRes.body());

        System.out.println(queryJson.getJSONObject("data"));
        JSONObject makeJson0 = new JSONObject();
        makeJson0.put("infoKey","年度收运量累积(吨)");
        makeJson0.put("infoVal",String.format("%.2f",queryJson.getJSONObject("data").getDoubleValue("year")));
        JSONObject makeJson1 = new JSONObject();
        makeJson1.put("infoKey","当月收运量累积(吨)");
        makeJson1.put("infoVal",queryJson.getJSONObject("data").get("month"));
        JSONObject makeJson2 = new JSONObject();
        makeJson2.put("infoKey","当日收运量累积(吨)");
        makeJson2.put("infoVal",queryJson.getJSONObject("data").get("day"));
        JSONObject makeJson3 = new JSONObject();
        makeJson3.put("infoKey","总收运点位数（个）");
        makeJson3.put("infoVal",queryJson.getJSONObject("data").get("sites"));
        data.add(makeJson0);
        data.add(makeJson1);
        data.add(makeJson2);
        data.add(makeJson3);
        return CommonResult.success(data);
    }
}
