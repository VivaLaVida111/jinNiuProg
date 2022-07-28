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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/xzzf")
public class XzzfController {
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
        loginParam.put("userid", "460230");
        // 其他信息
        loginParam.put("password", "e10adc3949ba59abbe56e057f20f883e");
        loginParam.put("subDomain", "jnzf");

        /*
         * END 构造登陆请求中要用到的参数
         */
        // 构造执行登陆请求 发起HTTP POST请求
        String loginUrl = "https://zhzf.chengdu.gov.cn/zhzf.manage/userLogin";
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.CONTENT_LENGTH, "225")
                .form(loginParam)
                .execute();
        List<HttpCookie> cookies = loginRes.getCookies();

        //System.out.println(loginRes.getCookies().toString());
            String queryUrl = "https://zhzf.chengdu.gov.cn/zhzf.manage/jnzf/api/zf/case/getIndexNum";
            HttpResponse queryRes = HttpRequest.get(queryUrl)
                    .header(Header.COOKIE, String.valueOf(cookies.get(0)))
                    .execute();
            JSONObject queryJson = JSONObject.parseObject(queryRes.body());

            JSONObject makeJson0 = new JSONObject();
            makeJson0.put("infoKey","今日结案");
            makeJson0.put("infoVal",queryJson.getJSONObject("data").get("todayFinish"));

            JSONObject makeJson1 = new JSONObject();
            makeJson1.put("infoKey","今日上报案件");
            makeJson1.put("infoVal",queryJson.getJSONObject("data").get("todayReport"));

            JSONObject makeJson2 = new JSONObject();
            makeJson2.put("infoKey","今日结案率");
            if (queryJson.getJSONObject("data").getIntValue("todayReport")!=0){
                makeJson2.put("infoVal",queryJson.getJSONObject("data").getIntValue("todayFinish")*100/queryJson.getJSONObject("data").getIntValue("todayReport")+"%");
            }
            else {
                makeJson2.put("infoVal","今日无案件");
            }
            JSONObject makeJson3 = new JSONObject();
            makeJson3.put("infoKey","本周解决");
            makeJson3.put("infoVal",queryJson.getJSONObject("data").get("weekFinish"));

            JSONObject makeJson4 = new JSONObject();
            makeJson4.put("infoKey","本周上报案件");
            makeJson4.put("infoVal",queryJson.getJSONObject("data").get("weekReport"));



            data.add(makeJson0);
            data.add(makeJson1);
            data.add(makeJson2);
            data.add(makeJson3);
            data.add(makeJson4);

            return CommonResult.success(data);

    }

    @RequestMapping("/getHistory")
    public CommonResult <List<Table>> getHistory(){
        Date calDate= new Date(System.currentTimeMillis());
        long time = System.currentTimeMillis()/1000;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        JSONArray data = new JSONArray();
        HashMap<String, Object> loginParam = new HashMap<>();
        loginParam.put("userid", "460230");
        // 其他信息
        loginParam.put("password", "e10adc3949ba59abbe56e057f20f883e");
        loginParam.put("subDomain", "jnzf");
        String loginUrl = "https://zhzf.chengdu.gov.cn/zhzf.manage/userLogin";
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.CONTENT_LENGTH, "225")
                .form(loginParam)
                .execute();
        List<HttpCookie> cookies = loginRes.getCookies();
        List<Table> table = new ArrayList<>();
        //System.out.println(loginRes.getCookies().toString());
        JSONObject queryJson = new JSONObject();
        // 用户名 金牛区管理员刘磊 前端有一个des加密 这是前端直接加密好的结果
        queryJson.put("pageNum", "2");
        queryJson.put("pageSize","20");
        // 密码 Qq123456 下面也是前端加密好的结果

        //System.out.println(loginRes.getCookies().toString());
        String queryUrl = "https://zhzf.chengdu.gov.cn/zhzf.manage/jnzf/api/obligation/sign/getHistory";
        HttpResponse queryRes = HttpRequest.post(queryUrl)
                .header(Header.COOKIE, String.valueOf(cookies.get(0)))
                .header(Header.CONTENT_TYPE,"application/json")
                .body(JSON.toJSONString(queryJson))
                .execute();
        JSONArray queryJsonRes_0 = JSONObject.parseObject(queryRes.body()).getJSONArray("data");

        String[] headerNames = new String[]{"队员姓名","签到日期","签到","签退", "签到地点","签退地点"};
        String[] dataNames = new String[]{"val0","val1" ,"val2", "val3", "val4","val5"};
        for (int i = 0;i<queryJsonRes_0.size();i++)
        {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 5;  j++) {

                makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("signerName"));
                makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("date"));
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("signInTime"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("signOutTime"));
                makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("signInLocation"));
                makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("signOutLocation"));


            }
            data.add(makeJson);
        }
        table.add(new Table(headerNames,dataNames,data));


        return CommonResult.success(table);
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
        HashMap<String, Object> loginParam = new HashMap<>();
        loginParam.put("userid", "460230");
        // 其他信息
        loginParam.put("password", "e10adc3949ba59abbe56e057f20f883e");
        loginParam.put("subDomain", "jnzf");
        String loginUrl = "https://zhzf.chengdu.gov.cn/zhzf.manage/userLogin";
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.CONTENT_LENGTH, "225")
                .form(loginParam)
                .execute();
        List<HttpCookie> cookies = loginRes.getCookies();
        JSONObject queryJson = new JSONObject();
        // 用户名 金牛区管理员刘磊 前端有一个des加密 这是前端直接加密好的结果
        queryJson.put("pageNum", "1");
        queryJson.put("pageSize","10");
        // 密码 Qq123456 下面也是前端加密好的结果

        //System.out.println(loginRes.getCookies().toString());
        String queryUrl = "https://zhzf.chengdu.gov.cn/zhzf.manage/jnzf/api/obligation/sign/getHistory";
        HttpResponse queryRes = HttpRequest.post(queryUrl)
                .header(Header.COOKIE, String.valueOf(cookies.get(0)))
                .header(Header.CONTENT_TYPE,"application/json")
                .body(JSON.toJSONString(queryJson))
                .execute();

        JSONObject queryResJson = JSONObject.parseObject(queryRes.body());
        return queryResJson;
    }

}
