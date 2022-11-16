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
@RequestMapping("/shlj")
public class ShljController {
    @Autowired
    JsonService jsonService;
    @CrossOrigin(origins = "*")
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
        loginParam.put("userName", "15388166797");
        // 其他信息
        loginParam.put("password", "jhg66797");
        loginParam.put("enterpriseId", "119");

        /*
         * END 构造登陆请求中要用到的参数
         */
        // 构造执行登陆请求 发起HTTP POST请求
        String loginUrl = "https://garbageapi.fbrrr.com/hbdr-garbage/api/admin/login/login.do";
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.CONTENT_LENGTH, "225")
                .form(loginParam)
                .execute();
        List<HttpCookie> cookies = loginRes.getCookies();


        String queryUrl = "https://garbageapi.fbrrr.com/hbdr-garbage/api/admin/weighbridge/weighbridgeList.do";
        HttpResponse queryRes = HttpRequest.post(queryUrl)
                .header(Header.COOKIE, "773DBB77F734514018B9E41C76781E9D")
                .header("authority","garbageapi.fbrrr.com")
                .execute();

        JSONObject queryJson = JSONObject.parseObject(queryRes.body());
        queryJson.getJSONObject("data");

        JSONObject makeJson0 = new JSONObject();
       makeJson0.put("infoKey","查询记录总重");
       makeJson0.put("infoVal",queryJson.getJSONObject("data").getFloatValue("totalWeight")/1000+"吨");
//
        JSONObject makeJson1 = new JSONObject();
        makeJson1.put("infoKey","垃圾记录总数");
        makeJson1.put("infoVal",queryJson.getJSONObject("data").getJSONObject("subOrderList").get("total")+"条");
//
//        JSONObject makeJson2 = new JSONObject();
//        makeJson2.put("infoKey","今日结案率");
//        if (queryJson.getJSONObject("data").getIntValue("todayReport")!=0){
//            makeJson2.put("infoVal",queryJson.getJSONObject("data").getIntValue("todayFinish")*100/queryJson.getJSONObject("data").getIntValue("todayReport")+"%");
//        }
//        else {
//            makeJson2.put("infoVal","今日无案件");
//        }
//        JSONObject makeJson3 = new JSONObject();
//        makeJson3.put("infoKey","本周解决");
//        makeJson3.put("infoVal",queryJson.getJSONObject("data").get("weekFinish"));
//
//        JSONObject makeJson4 = new JSONObject();
//        makeJson4.put("infoKey","本周上报案件");
//        makeJson4.put("infoVal",queryJson.getJSONObject("data").get("weekReport"));
//
//
//
        data.add(makeJson0);
       data.add(makeJson1);
//        data.add(makeJson2);
//        data.add(makeJson3);
//        data.add(makeJson4);

        return CommonResult.success(data);
    }
}
