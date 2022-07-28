package com.example.jinniuprog.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.jinniuprog.Util.DateUtil;
import com.example.jinniuprog.common.CommonResult;
import com.example.jinniuprog.entity.Table;
import com.example.jinniuprog.service.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpCookie;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/jgzm")
public class JgzmController {
    @Autowired
    JsonService jsonService;
    @CrossOrigin(origins = "*")
    @RequestMapping("/mainInfo")
    public CommonResult<JSONArray> getMainInfo(){
        String loginUrl = "http://171.221.172.143:18080/api/login?lang=zh_CN";
        long beginOfMonth = DateUtil.getBeginDayOfMonth().getTime()/1000*1000;
        long time = System.currentTimeMillis()/1000*1000;
     JSONObject loginJson = new JSONObject();
        loginJson.put("username","LXF");
        loginJson.put("password","mawhaIjfFocb/iqf85MDz+GuVktxhNKVg6FWX45/k4EclTG5d2U72rsY0/MpXsi4ElQfAGBJJUy8aXkr9BcfEC/ON+TC1vg5RTgD+GcUk4ZIfiBuenp4FqcLnNrl6OWsAAn9xa/AYvB0I8ZIcoCQFrrwQKVCgi/AfS2rKSK1smw=");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time_month = formatter_time.format(new Date(System.currentTimeMillis()));

        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE, "application/json; charset=UTF-8")
                .header(Header.CONTENT_LENGTH, "225")
                .header(Header.HOST, "171.221.172.74:6888")
                .header(Header.ORIGIN, "http://171.221.172.143:18080")
                .body(JSONObject.toJSONString(loginJson))
                .execute();
         String authorization = JSON.parseObject(loginRes.body()).getString("data");
         List<HttpCookie> cookies = loginRes.getCookies();

        String queryUrl = "http://171.221.172.143:18080/api/v1.0/config/getAlarmPage?lang=zh_CN";
        //page=1&limit=10&scale=&devices_id=&monitoring_status=&company_name=&area_id=&level=
        JSONObject queryParam = new JSONObject();
        queryParam.put("projectId","28fb3f87f1e34080b04450ef6d6eb6cb");
     queryParam.put("projectId","28fb3f87f1e34080b04450ef6d6eb6cb");
     queryParam.put("endDate",time);
     queryParam.put("queryType",3);
     queryParam.put("startDate",beginOfMonth);
     queryParam.put("type",1);
        HttpResponse queRes = HttpRequest.post(queryUrl)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam)).execute();
        JSONObject queryJsonRes = JSONObject.parseObject(queRes.body());
        JSONArray data = new JSONArray();
        int total = queryJsonRes.getJSONObject("data").getIntValue("total");

        JSONObject makeJson = new JSONObject();
        JSONObject makeJson1 = new JSONObject();
        JSONObject makeJson2 = new JSONObject();
        JSONObject makeJson3= new JSONObject();


        queryParam.replace("projectId","4c1cd48f5eb649a6ab3d2e49b1f57094");
        queRes = HttpRequest.post(queryUrl)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam)).execute();
        queryJsonRes = JSON.parseObject(queRes.body());

        total+=queryJsonRes.getJSONObject("data").getIntValue("total");

        //System.out.println(queRes.body());

        queryParam.replace("projectId","5c9ab624f2e54825958ee16d09ba5e51");
        queRes = HttpRequest.post(queryUrl)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam)).execute();
        queryJsonRes = JSON.parseObject(queRes.body());
        total+=queryJsonRes.getJSONObject("data").getIntValue("total");

     queryParam.replace("projectId","6ea02a99f5554e85b9e417defb121d56");
     queRes = HttpRequest.post(queryUrl)
             .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
             .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
             .header(Header.AUTHORIZATION,authorization)
             .header(Header.COOKIE, String.valueOf(cookies))
             .body(JSONObject.toJSONString(queryParam)).execute();
     queryJsonRes = JSON.parseObject(queRes.body());

     total+=queryJsonRes.getJSONObject("data").getIntValue("total");



        makeJson.put("infoKey","报警照明设备数");
        makeJson.put("infoVal",total);





        String queryUrl_overview = "http://171.221.172.143:18080/api/v1.0/energy/getOverview?lang=zh_CN";
        //page=1&limit=10&scale=&devices_id=&monitoring_status=&company_name=&area_id=&level=
        JSONObject queryParam_0 = new JSONObject();
        Long endDate = time;
        long startDate = beginOfMonth;
        queryParam_0.put("projectId","28fb3f87f1e34080b04450ef6d6eb6cb");
        queryParam_0.put("endDate",endDate);
        queryParam_0.put("queryType",3);
        queryParam_0.put("startDate",startDate);
        queryParam_0.put("type",1);

        HttpResponse queRes0 = HttpRequest.post(queryUrl_overview)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam_0)).execute();
        JSONObject queryJsonRes123 = JSONObject.parseObject(queRes0.body());
        JSONObject queryJsonRes0 = JSONObject.parseObject(queRes0.body()).getJSONObject("data");

        long yesterday_consumption = queryJsonRes0.getJSONObject("electricityConsumption").getLong("yesterday");
        long month_consumption = queryJsonRes0.getJSONObject("electricityConsumption").getLong("month");
        long year_consumption = queryJsonRes0.getJSONObject("electricityConsumption").getLong("year");
        queryParam_0.replace("projectId","5c9ab624f2e54825958ee16d09ba5e51");
        HttpResponse queRes1 = HttpRequest.post(queryUrl_overview)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam_0)).execute();
        JSONObject queryJsonRes1 = JSONObject.parseObject(queRes1.body()).getJSONObject("data");
        yesterday_consumption+=queryJsonRes1.getJSONObject("electricityConsumption").getLong("yesterday");
        month_consumption += queryJsonRes1.getJSONObject("electricityConsumption").getLong("month");
        year_consumption += queryJsonRes1.getJSONObject("electricityConsumption").getLong("year");

        queryParam_0.replace("projectId","6ea02a99f5554e85b9e417defb121d56");
        HttpResponse queRes2 = HttpRequest.post(queryUrl_overview)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam_0)).execute();
        JSONObject queryJsonRes2 = JSONObject.parseObject(queRes2.body()).getJSONObject("data");
        year_consumption+=queryJsonRes2.getJSONObject("electricityConsumption").getLong("yesterday");
        month_consumption += queryJsonRes2.getJSONObject("electricityConsumption").getLong("month");
        year_consumption += queryJsonRes2.getJSONObject("electricityConsumption").getLong("year");


        queryParam_0.replace("projectId","4c1cd48f5eb649a6ab3d2e49b1f57094");
        HttpResponse queRes3 = HttpRequest.post(queryUrl_overview)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam_0)).execute();
        JSONObject queryJsonRes3 = JSONObject.parseObject(queRes3.body()).getJSONObject("data");
        yesterday_consumption+=queryJsonRes3.getJSONObject("electricityConsumption").getLong("yesterday");
        month_consumption += queryJsonRes3.getJSONObject("electricityConsumption").getLong("month");
        year_consumption += queryJsonRes3.getJSONObject("electricityConsumption").getLong("year");
        makeJson1.put("infoKey","昨日电量统计");
        makeJson1.put("infoVal",yesterday_consumption);
        makeJson2.put("infoKey","月电量统计");
        makeJson2.put("infoVal",month_consumption);
        makeJson3.put("infoKey","年电量统计");
        makeJson3.put("infoVal",year_consumption);
        data.add(makeJson);
        data.add(makeJson1);
        data.add(makeJson2);
        data.add(makeJson3);
        return CommonResult.success(data);
    }
    @RequestMapping("/getOverview")
    public CommonResult<List<Table>> getOverview(){
        String loginUrl = "http://171.221.172.143:18080/api/login?lang=zh_CN";
        long beginOfMonth = DateUtil.getBeginDayOfMonth().getTime()/1000*1000;
        long time = System.currentTimeMillis()/1000*1000;
        JSONObject loginJson = new JSONObject();
        loginJson.put("username","LXF");
        loginJson.put("password","mawhaIjfFocb/iqf85MDz+GuVktxhNKVg6FWX45/k4EclTG5d2U72rsY0/MpXsi4ElQfAGBJJUy8aXkr9BcfEC/ON+TC1vg5RTgD+GcUk4ZIfiBuenp4FqcLnNrl6OWsAAn9xa/AYvB0I8ZIcoCQFrrwQKVCgi/AfS2rKSK1smw=");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time_month = formatter_time.format(new Date(System.currentTimeMillis()));

        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE, "application/json; charset=UTF-8")
                .header(Header.CONTENT_LENGTH, "225")
                .header(Header.HOST, "171.221.172.74:6888")
                .header(Header.ORIGIN, "http://171.221.172.143:18080")
                .body(JSONObject.toJSONString(loginJson))
                .execute();
        String authorization = JSON.parseObject(loginRes.body()).getString("data");
        List<HttpCookie> cookies = loginRes.getCookies();
        List<Table> table = new ArrayList<>();

        String queryUrl_overview = "http://171.221.172.143:18080/api/v1.0/energy/getOverview?lang=zh_CN";

        JSONObject queryParam_0 = new JSONObject();
        Long endDate = time;
        long startDate = beginOfMonth;
        queryParam_0.put("projectId","28fb3f87f1e34080b04450ef6d6eb6cb");
        queryParam_0.put("endDate",endDate);
        queryParam_0.put("queryType",3);
        queryParam_0.put("startDate",startDate);
        queryParam_0.put("type",1);

        HttpResponse queRes0 = HttpRequest.post(queryUrl_overview)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam_0)).execute();
        JSONObject queryJsonRes0 = JSONObject.parseObject(queRes0.body()).getJSONObject("data");

        long yesterday_consumption = queryJsonRes0.getJSONObject("electricityConsumption").getLong("yesterday");
        long month_consumption = queryJsonRes0.getJSONObject("electricityConsumption").getLong("month");
        long year_consumption = queryJsonRes0.getJSONObject("electricityConsumption").getLong("year");
        String[] headerNames_0 = new String[]{"区域", "昨日消耗", "本月消耗","本年消耗"};
        String[] dataNames_0 = new String[]{"val0", "val1", "val2","val3"};
        JSONArray data = new JSONArray();
        JSONObject makeJson0 = new JSONObject();
        makeJson0.put("val0","一品天下");
        makeJson0.put("val1",yesterday_consumption);
        makeJson0.put("val2",month_consumption);
        makeJson0.put("val3",year_consumption);
        data.add(makeJson0);




        queryParam_0.replace("projectId","5c9ab624f2e54825958ee16d09ba5e51");
        HttpResponse queRes1 = HttpRequest.post(queryUrl_overview)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam_0)).execute();
        JSONObject queryJsonRes1 = JSONObject.parseObject(queRes1.body()).getJSONObject("data");
        yesterday_consumption=queryJsonRes1.getJSONObject("electricityConsumption").getLong("yesterday");
        month_consumption = queryJsonRes1.getJSONObject("electricityConsumption").getLong("month");
        year_consumption = queryJsonRes1.getJSONObject("electricityConsumption").getLong("year");
        JSONObject makeJson1 = new JSONObject();
        makeJson1.put("val0","城北体育中心");
        makeJson1.put("val1",yesterday_consumption);
        makeJson1.put("val2",month_consumption);
        makeJson1.put("val3",year_consumption);
        data.add(makeJson1);

        queryParam_0.replace("projectId","6ea02a99f5554e85b9e417defb121d56");
        HttpResponse queRes2 = HttpRequest.post(queryUrl_overview)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam_0)).execute();
        JSONObject queryJsonRes2 = JSONObject.parseObject(queRes2.body()).getJSONObject("data");
        year_consumption=queryJsonRes2.getJSONObject("electricityConsumption").getLong("yesterday");
        month_consumption = queryJsonRes2.getJSONObject("electricityConsumption").getLong("month");
        year_consumption = queryJsonRes2.getJSONObject("electricityConsumption").getLong("year");
        JSONObject makeJson2 = new JSONObject();
        makeJson2.put("val0","枣子巷");
        makeJson2.put("val1",yesterday_consumption);
        makeJson2.put("val2",month_consumption);
        makeJson2.put("val3",year_consumption);
        data.add(makeJson2);
        queryParam_0.replace("projectId","4c1cd48f5eb649a6ab3d2e49b1f57094");
        HttpResponse queRes3 = HttpRequest.post(queryUrl_overview)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam_0)).execute();
        JSONObject queryJsonRes3 = JSONObject.parseObject(queRes3.body()).getJSONObject("data");
        yesterday_consumption+=queryJsonRes3.getJSONObject("electricityConsumption").getLong("yesterday");
        month_consumption += queryJsonRes3.getJSONObject("electricityConsumption").getLong("month");
        year_consumption += queryJsonRes3.getJSONObject("electricityConsumption").getLong("year");
        JSONObject makeJson3 = new JSONObject();
        makeJson3.put("val0","一环路内透");
        makeJson3.put("val1",yesterday_consumption);
        makeJson3.put("val2",month_consumption);
        makeJson3.put("val3",year_consumption);
        data.add(makeJson3);
        table.add(new Table(headerNames_0,dataNames_0,data));

        return CommonResult.success(table);
    }

    @RequestMapping("/getAlarm")
    public CommonResult<List<Table>> getAlarm(){
       String loginUrl = "http://171.221.172.143:18080/api/login?lang=zh_CN";
        long beginOfMonth = DateUtil.getBeginDayOfMonth().getTime()/1000*1000;
        long time = System.currentTimeMillis()/1000*1000;
        JSONObject loginJson = new JSONObject();
        loginJson.put("username","LXF");
        loginJson.put("password","mawhaIjfFocb/iqf85MDz+GuVktxhNKVg6FWX45/k4EclTG5d2U72rsY0/MpXsi4ElQfAGBJJUy8aXkr9BcfEC/ON+TC1vg5RTgD+GcUk4ZIfiBuenp4FqcLnNrl6OWsAAn9xa/AYvB0I8ZIcoCQFrrwQKVCgi/AfS2rKSK1smw=");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time_month = formatter_time.format(new Date(System.currentTimeMillis()));

        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE, "application/json; charset=UTF-8")
                .header(Header.CONTENT_LENGTH, "225")
                .header(Header.HOST, "171.221.172.74:6888")
                .header(Header.ORIGIN, "http://171.221.172.143:18080")
                .body(JSONObject.toJSONString(loginJson))
                .execute();
        String authorization = JSON.parseObject(loginRes.body()).getString("data");
        List<HttpCookie> cookies = loginRes.getCookies();
        List<Table> table = new ArrayList<>();
        String queryUrl = "http://171.221.172.143:18080/api/v1.0/config/getAlarmPage?lang=zh_CN";
        //page=1&limit=10&scale=&devices_id=&monitoring_status=&company_name=&area_id=&level=
        JSONObject queryParam = new JSONObject();
        queryParam.put("projectId","28fb3f87f1e34080b04450ef6d6eb6cb");
        queryParam.put("endDate",time);
        queryParam.put("queryType",3);
        queryParam.put("startDate",beginOfMonth);
        queryParam.put("type",1);
        HttpResponse queRes = HttpRequest.post(queryUrl)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam)).execute();
        JSONArray queryJsonRes_0 = JSONObject.parseObject(queRes.body()).getJSONObject("data").getJSONArray("records");
        JSONArray data = new JSONArray();
        String[] headerNames_0 = new String[]{"区域", "状态", "设备名称","设备类型","警报类型","警报时间"};
        String[] dataNames_0 = new String[]{"val0", "val1", "val2","val3","val4","val5"};
        for (int i = queryJsonRes_0.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();
            String alarmtype = "未知";
            if(queryJsonRes_0.getJSONObject(i).getIntValue("alarmType")==301){
                alarmtype = "配电柜断电警报";
            } else if (queryJsonRes_0.getJSONObject(i).getIntValue("alarmType")==302) {
                alarmtype="配电柜缺相警报";
            } else if (queryJsonRes_0.getJSONObject(i).getIntValue("alarmType")==100) {
                alarmtype="集中器离线警报";
            }
            for (int j = 0; j < 5;  j++) {

                makeJson.put("val0", "一品天下");
                makeJson.put("val1", "紧急");
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("deviceName"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("typeName"));
                makeJson.put("val4", alarmtype);
                makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("alarmTime"));

            }
            data.add(makeJson);

        }
        queryParam.replace("projectId","5c9ab624f2e54825958ee16d09ba5e51");
        queRes = HttpRequest.post(queryUrl)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam)).execute();
        JSONArray queryJsonRes_1 = JSONObject.parseObject(queRes.body()).getJSONObject("data").getJSONArray("records");
        for (int i = queryJsonRes_1.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();
            String alarmtype = "未知";
            if(queryJsonRes_1.getJSONObject(i).getIntValue("alarmType")==301){
                alarmtype = "配电柜断电警报";
            } else if (queryJsonRes_1.getJSONObject(i).getIntValue("alarmType")==302) {
                alarmtype="配电柜缺相警报";
            } else if (queryJsonRes_1.getJSONObject(i).getIntValue("alarmType")==100) {
                alarmtype="集中器离线警报";
            }
            for (int j = 0; j < 5;  j++) {

                makeJson.put("val0", "城北体育中心");
                makeJson.put("val1", "紧急");
                makeJson.put("val2", queryJsonRes_1.getJSONObject(i).get("deviceName"));
                makeJson.put("val3", queryJsonRes_1.getJSONObject(i).get("typeName"));
                makeJson.put("val4", alarmtype);
                makeJson.put("val5", queryJsonRes_1.getJSONObject(i).get("alarmTime"));

            }
            data.add(makeJson);

        }
        queryParam.replace("projectId","6ea02a99f5554e85b9e417defb121d56");
        queRes = HttpRequest.post(queryUrl)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam)).execute();
        JSONArray queryJsonRes_2 = JSONObject.parseObject(queRes.body()).getJSONObject("data").getJSONArray("records");
        for (int i = queryJsonRes_2.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();
            String alarmtype = "未知";
            if(queryJsonRes_2.getJSONObject(i).getIntValue("alarmType")==301){
                alarmtype = "配电柜断电警报";
            } else if (queryJsonRes_2.getJSONObject(i).getIntValue("alarmType")==302) {
                alarmtype="配电柜缺相警报";
            } else if (queryJsonRes_2.getJSONObject(i).getIntValue("alarmType")==100) {
                alarmtype="集中器离线警报";
            }
            for (int j = 0; j < 5;  j++) {

                makeJson.put("val0", "枣子巷");
                makeJson.put("val1", "紧急");
                makeJson.put("val2", queryJsonRes_2.getJSONObject(i).get("deviceName"));
                makeJson.put("val3", queryJsonRes_2.getJSONObject(i).get("typeName"));
                makeJson.put("val4", alarmtype);
                makeJson.put("val5", queryJsonRes_2.getJSONObject(i).get("alarmTime"));

            }
            data.add(makeJson);

        }

        queryParam.replace("projectId","4c1cd48f5eb649a6ab3d2e49b1f57094");
        queRes = HttpRequest.post(queryUrl)
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
                .header(Header.AUTHORIZATION,authorization)
                .header(Header.COOKIE, String.valueOf(cookies))
                .body(JSONObject.toJSONString(queryParam)).execute();
        JSONArray queryJsonRes_3 = JSONObject.parseObject(queRes.body()).getJSONObject("data").getJSONArray("records");
        for (int i = queryJsonRes_3.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();
            String alarmtype = "未知";
            if(queryJsonRes_3.getJSONObject(i).getIntValue("alarmType")==301){
                alarmtype = "配电柜断电警报";
            } else if (queryJsonRes_3.getJSONObject(i).getIntValue("alarmType")==302) {
                alarmtype="配电柜缺相警报";
            } else if (queryJsonRes_3.getJSONObject(i).getIntValue("alarmType")==100) {
                alarmtype="集中器离线警报";
            }
            for (int j = 0; j < 5;  j++) {

                makeJson.put("val0", "一环路内透");
                makeJson.put("val1", "紧急");
                makeJson.put("val2", queryJsonRes_3.getJSONObject(i).get("deviceName"));
                makeJson.put("val3", queryJsonRes_3.getJSONObject(i).get("typeName"));
                makeJson.put("val4", alarmtype);
                makeJson.put("val5", queryJsonRes_3.getJSONObject(i).get("alarmTime"));

            }
            data.add(makeJson);

        }
        table.add(new Table(headerNames_0,dataNames_0,data));
        return CommonResult.success(table);
    }

 @RequestMapping("/result")
 public JSONObject getResult() throws ParseException {
  String loginUrl = "http://171.221.172.143:18080/api/login?lang=zh_CN";
  JSONObject loginJson = new JSONObject();
  loginJson.put("username","LXF");
  loginJson.put("password","mawhaIjfFocb/iqf85MDz+GuVktxhNKVg6FWX45/k4EclTG5d2U72rsY0/MpXsi4ElQfAGBJJUy8aXkr9BcfEC/ON+TC1vg5RTgD+GcUk4ZIfiBuenp4FqcLnNrl6OWsAAn9xa/AYvB0I8ZIcoCQFrrwQKVCgi/AfS2rKSK1smw=");
     SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
     long beginOfMonth = DateUtil.getBeginDayOfMonth().getTime()/1000*1000;
     long time = System.currentTimeMillis()/1000*1000;

  HttpResponse loginRes = HttpRequest.post(loginUrl)
          .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
          .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
          .header(Header.CONTENT_TYPE, "application/json; charset=UTF-8")
          .header(Header.CONTENT_LENGTH, "225")
          .header(Header.ORIGIN, "http://171.221.172.143:18080")
          .body(JSONObject.toJSONString(loginJson))
          .execute();

  String authorization = JSON.parseObject(loginRes.body()).getString("data");
  List<HttpCookie> cookies = loginRes.getCookies();
  Long endDate = beginOfMonth;
  Long startDate = time;
     String queryUrl = "http://171.221.172.143:18080/api/v1.0/config/getAlarmPage?lang=zh_CN";
     //page=1&limit=10&scale=&devices_id=&monitoring_status=&company_name=&area_id=&level=
     JSONObject queryParam = new JSONObject();
     queryParam.put("projectId","4c1cd48f5eb649a6ab3d2e49b1f57094");

     queryParam.put("endDate",time);
     queryParam.put("queryType",3);
     queryParam.put("startDate",beginOfMonth);
     queryParam.put("type",1);


  HttpResponse queRes = HttpRequest.post(queryUrl)
          .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
          .header(Header.CONTENT_TYPE,"application/json; charset=UTF-8")
          .header(Header.AUTHORIZATION,authorization)
          .header(Header.COOKIE, String.valueOf(cookies))
          .body(JSONObject.toJSONString(queryParam)).execute();
  JSONObject queryJsonRes = JSONObject.parseObject(queRes.body());
  System.out.println(queRes.body());



  return JSONObject.parseObject(queRes.body());
 }

}

