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
@RequestMapping("/ggzp")
public class GgzpController {
    @Autowired
    JsonService jsonService;
    @CrossOrigin(origins = "*")
    @RequestMapping("/mainInfo")
    public CommonResult<JSONArray> getMainInfo(){
        String loginUrl = "https://www.qpzhuisu.com/store-api/oauth/token?grant_type=password&randomStr=blockPuzzle";
        HashMap<String, Object> loginParam = new HashMap<>();
        loginParam.put("username", "jnq_ob");
        // 其他信息
        loginParam.put("password", "123456");
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/plain, */*")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .header(Header.CONTENT_LENGTH, "225")
                .header(Header.ORIGIN, "https://www.qpzhuisu.com")
                .header(Header.HOST,"www.qpzhuisu.com")
                .header("isToken","false")
                .header("TENANT-ID","1")
                .header(Header.AUTHORIZATION,"Basic d2ViOndlYg==")
                .form(loginParam)
                .execute();

        JSONObject queRes = JSONObject.parseObject(loginRes.body());
        List<HttpCookie> cookies = loginRes.getCookies();
        String token = queRes.getString("access_token");

        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("categoryId", "");
        // 其他信息
        queryParam.put("streetId", "");
        queryParam.put("roadId", "");
        queryParam.put("date","");

        String queryUrl = "https://www.qpzhuisu.com/store-api/statistics/store";
        HttpResponse queryRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"Bearer "+token)
                .form(queryParam)
                .execute();

        JSONArray data = new JSONArray();
        String queryUrl1 = "https://www.qpzhuisu.com/store-api/statistics/v1";
        HttpResponse queryRes1 = HttpRequest.get(queryUrl1)
                .header(Header.AUTHORIZATION,"Bearer "+token)
                .form(queryParam)
                .execute();
        JSONObject queryJson1 = JSONObject.parseObject(queryRes1.body()).getJSONObject("data");
        JSONObject makeJson1 = new JSONObject();
        makeJson1.put("infoKey","店铺总数");
        makeJson1.put("infoVal",queryJson1.get("storeCount"));
        data.add(makeJson1);
        JSONObject makeJson2 = new JSONObject();
        makeJson2.put("infoKey","巡检店铺数量");
        makeJson2.put("infoVal",queryJson1.get("patrolStoreCount"));
        data.add(makeJson2);
        JSONArray queryJson = JSONObject.parseObject(queryRes.body()).getJSONArray("data");
        for(int i =0;i<queryJson.size();i++){
            JSONObject makeJson = new JSONObject();
            makeJson.put("infoKey",queryJson.getJSONObject(i).get("parentName"));
            makeJson.put("infoVal", queryJson.getJSONObject(i).get("count"));
            data.add(makeJson);
        }

        return CommonResult.success(data);
    }
    @RequestMapping("/getXcgl")
    public CommonResult<List<Table>> getXcgl(){
        String loginUrl = "https://www.qpzhuisu.com/store-api/oauth/token?grant_type=password&randomStr=blockPuzzle";
        HashMap<String, Object> loginParam = new HashMap<>();
        loginParam.put("username", "jnq_ob");
        // 其他信息
        loginParam.put("password", "123456");
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/plain, */*")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .header(Header.CONTENT_LENGTH, "225")
                .header(Header.ORIGIN, "https://www.qpzhuisu.com")
                .header(Header.HOST,"www.qpzhuisu.com")
                .header("isToken","false")
                .header("TENANT-ID","1")
                .header(Header.AUTHORIZATION,"Basic d2ViOndlYg==")
                .form(loginParam)
                .execute();

        JSONObject queRes = JSONObject.parseObject(loginRes.body());

        String token = queRes.getString("access_token");

        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("size", "50");
        // 其他信息
        queryParam.put("current", "1");


        String queryUrl = "https://www.qpzhuisu.com/store-api/patrolrecord/page?size=50&current=1";
        HttpResponse queryRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"Bearer "+token)
//                .form(queryParam)
                .execute();
      //  System.out.println(JSONObject.parseObject(queryRes.body()));

        JSONArray queryJson = JSONObject.parseObject(queryRes.body()).getJSONObject("data").getJSONArray("records");
        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{ "巡查人", "所属部门","店铺名称","详细地址","负责人","负责人联系方式","巡查人联系方式","巡查时间"};
        String[] dataNames = new String[]{"patrolUserName","patrolUserDeptName", "name","address","contact","contactPhone","patrolUserPhone","patrolTime"};
        for(int i =0;i<queryJson.size();i++){
            JSONObject makeJson = new JSONObject();
            JSONObject store = queryJson.getJSONObject(i).getJSONObject("store");
            makeJson.put("patrolUserName", queryJson.getJSONObject(i).get("patrolUserName"));
            makeJson.put("patrolUserDeptName", queryJson.getJSONObject(i).get("patrolUserDeptName"));
            makeJson.put("name", store.get("name"));
            makeJson.put("address", store.get("address"));
            makeJson.put("contact", store.get("contact"));
            makeJson.put("contactPhone", store.get("contactPhone"));
            makeJson.put("patrolUserPhone", queryJson.getJSONObject(i).get("patrolUserPhone"));
            makeJson.put("patrolTime", queryJson.getJSONObject(i).get("patrolTime"));
            data.add(makeJson);
        }
        List<Table> tables = new ArrayList<>();
        tables.add(new Table(headerNames,dataNames,data));
        return CommonResult.success(tables);
    }
    @RequestMapping("/getXjry")
    public CommonResult<List<Table>> getXjry(){
        String loginUrl = "https://www.qpzhuisu.com/store-api/oauth/token?grant_type=password&randomStr=blockPuzzle";
        HashMap<String, Object> loginParam = new HashMap<>();
        loginParam.put("username", "jnq_ob");
        // 其他信息
        loginParam.put("password", "123456");
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/plain, */*")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .header(Header.CONTENT_LENGTH, "225")
                .header(Header.ORIGIN, "https://www.qpzhuisu.com")
                .header(Header.HOST,"www.qpzhuisu.com")
                .header("isToken","false")
                .header("TENANT-ID","1")
                .header(Header.AUTHORIZATION,"Basic d2ViOndlYg==")
                .form(loginParam)
                .execute();

        JSONObject queRes = JSONObject.parseObject(loginRes.body());
        List<HttpCookie> cookies = loginRes.getCookies();
        String token = queRes.getString("access_token");

        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("size", "30");
        // 其他信息
        queryParam.put("current", "1");


        String queryUrl = "https://www.qpzhuisu.com/store-api/appuser/page/patrol";
        HttpResponse queryRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"Bearer "+token)
                .form(queryParam)
                .execute();
        JSONArray queryJson = JSONObject.parseObject(queryRes.body()).getJSONObject("data").getJSONArray("records");
        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{ "姓名", "手机号","所属机构","注册时间"};
        String[] dataNames = new String[]{"name","tel", "deptFullName","createTime"};
        for(int i =0;i<queryJson.size();i++){
            JSONObject makeJson = new JSONObject();
            makeJson.put("name", queryJson.getJSONObject(i).get("name"));
            makeJson.put("tel", queryJson.getJSONObject(i).get("phone"));
            makeJson.put("deptFullName", queryJson.getJSONObject(i).get("deptFullName"));
            makeJson.put("createTime", queryJson.getJSONObject(i).get("createTime"));
            data.add(makeJson);
        }
        List<Table> tables = new ArrayList<>();
        tables.add(new Table(headerNames,dataNames,data));
        return CommonResult.success(tables);
    }
    @RequestMapping("/getMapDataGgzp")
    public CommonResult<JSONArray> getMapDataGgzp(){
        String loginUrl = "https://www.qpzhuisu.com/store-api/oauth/token?grant_type=password&randomStr=blockPuzzle";
        HashMap<String, Object> loginParam = new HashMap<>();
        loginParam.put("username", "jnq_ob");
        // 其他信息
        loginParam.put("password", "123456");
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/plain, */*")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .header(Header.CONTENT_LENGTH, "225")
                .header(Header.ORIGIN, "https://www.qpzhuisu.com")
                .header(Header.HOST,"www.qpzhuisu.com")
                .header("isToken","false")
                .header("TENANT-ID","1")
                .header(Header.AUTHORIZATION,"Basic d2ViOndlYg==")
                .form(loginParam)
                .execute();

        JSONObject queRes = JSONObject.parseObject(loginRes.body());
        List<HttpCookie> cookies = loginRes.getCookies();
        String token = queRes.getString("access_token");
        JSONArray data = new JSONArray(); //3852荷花池
        String[] streetId = new String[]{"4002","3856","3851","3860","3852","3855","3862","4006","3858","4013","3854","3863","3861"};
        for(int i =0;i<streetId.length;i++){
            HashMap<String, Object> queryParam = new HashMap<>();
            queryParam.put("categoryId", "");
            // 其他信息
            queryParam.put("streetId", streetId[i]);
            queryParam.put("roadId", "");
            queryParam.put("date","");

            String queryUrl = "https://www.qpzhuisu.com/store-api/statistics/store";
            HttpResponse queryRes = HttpRequest.get(queryUrl)
                    .header(Header.AUTHORIZATION,"Bearer "+token)
                    .form(queryParam)
                    .execute();
            JSONArray queryJson = JSONObject.parseObject(queryRes.body()).getJSONArray("data");
            JSONObject makeJson = new JSONObject();
            for(int j=0;j<queryJson.size();j++){
                System.out.println(queryJson.get(j));
                makeJson.put(queryJson.getJSONObject(j).getString("parentName"),queryJson.getJSONObject(j).get("count"));

            }
            System.out.println(makeJson);
            data.add(makeJson);
        }

        //System.out.println(queRes);
        //System.out.println(queryJson);

        return CommonResult.success(data);
    }
    @RequestMapping("/result")
    public JSONObject getResult() throws ParseException {
        String loginUrl = "https://www.qpzhuisu.com/store-api/oauth/token?grant_type=password&randomStr=blockPuzzle";
        HashMap<String, Object> loginParam = new HashMap<>();
        loginParam.put("username", "jnq_ob");
        // 其他信息
        loginParam.put("password", "123456");
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/plain, */*")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .header(Header.CONTENT_LENGTH, "225")
                .header(Header.ORIGIN, "https://www.qpzhuisu.com")
                .header(Header.HOST,"www.qpzhuisu.com")
                .header("isToken","false")
                .header("TENANT-ID","1")
                .header(Header.AUTHORIZATION,"Basic d2ViOndlYg==")
                .form(loginParam)
                .execute();

        JSONObject queRes = JSONObject.parseObject(loginRes.body());
        List<HttpCookie> cookies = loginRes.getCookies();
        String token = queRes.getString("access_token");
        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("categoryId", "");
        // 其他信息
        queryParam.put("streetId", "");
        queryParam.put("roadId", "");

        String queryUrl = "https://www.qpzhuisu.com/store-api/statistics/store";
        HttpResponse queryRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"Bearer "+token)
                .form(queryParam)
                .execute();
        JSONObject queryJson = JSONObject.parseObject(queryRes.body());


        return queryJson;
    }

}

