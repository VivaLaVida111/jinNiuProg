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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/lzj")
public class ljzController {
    @Autowired
    JsonService jsonService;
    @CrossOrigin(origins = "*")
    @RequestMapping("/mainInfo")
    public CommonResult<JSONArray> getMainInfo(){
        Date calDate = new Date(System.currentTimeMillis());
        Date calDate_tomorrow = new Date(System.currentTimeMillis()+1000*60*60*24);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        double total_jinniu = 0;
        double total_hongxing = 0;
        double total_xihua = 0;
        JSONArray data = new JSONArray();
        String date = formatter.format(calDate);
        String date_tomorrow = formatter.format(calDate_tomorrow);
        HashMap<String, Object> queryParam = new HashMap<>();
        String queryUrl = "http://101.37.246.72:9092/dump-record/page/%E7%BA%A2%E6%98%9F/transporter/"+date+"/"+date_tomorrow+"/1/10000#/";
        HttpResponse queryRes = HttpRequest.get(queryUrl)
                .form(queryParam)
                .execute();
        JSONArray queryJsonRes_1 = JSON.parseObject(queryRes.body()).getJSONArray("data");
        for (int i=0;i<queryJsonRes_1.size();i++){
            total_hongxing+=queryJsonRes_1.getJSONObject(i).getDouble("netWeight");
        }
        JSONObject makeJson = new JSONObject();
        makeJson.put("infoKey","红星垃圾站数据");
        makeJson.put("infoVal",total_hongxing/1000+" 吨");

        String queryUrl2 = "http://101.37.246.72:9092/dump-record/page/%E8%A5%BF%E5%8D%8E/transporter/"+date+"/"+date_tomorrow+"/1/10000#/";
        HttpResponse queryRes2 = HttpRequest.get(queryUrl2)
                .form(queryParam)
                .execute();
        JSONArray queryJsonRes_2 = JSON.parseObject(queryRes2.body()).getJSONArray("data");
        for (int i=0;i<queryJsonRes_2.size();i++){
            total_xihua+=queryJsonRes_2.getJSONObject(i).getDouble("netWeight");
        }
        JSONObject makeJson2 = new JSONObject();
        makeJson2.put("infoKey","西华垃圾站数据");
        makeJson2.put("infoVal",total_xihua/1000+" 吨");
        JSONObject makeJson3 = new JSONObject();
        total_jinniu=total_hongxing+total_xihua;
        makeJson3.put("infoKey","金牛区垃圾站数据");
        makeJson3.put("infoVal",total_jinniu/1000+" 吨");
        data.add(makeJson3);
        data.add(makeJson2);
        data.add(makeJson);
        return CommonResult.success(data);
    }
}
