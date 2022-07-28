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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/szcg")
public class szcgController {
    JsonService jsonService;

    @CrossOrigin(origins = "*")
    @RequestMapping("/mainInfo")
    public CommonResult<JSONArray> getMainInfo(){
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
        List<HttpCookie> cookies = loginRes.getCookies();

        //System.out.println(loginRes.getCookies().toString());
        Date calDate= new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);

        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("queryID", "173");
        queryParam.put("condparams", "%7B%22queryConds%22:%5B%7B%22condId%22:%222%22,%22fieldName%22:%22district_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22124%22,%22fieldTypeInfo%22:%22$$vc_region_district$item_id$0$item_name%22,%22values%22:%5B%22'%E9%87%91%E7%89%9B%E5%8C%BA'%22%5D,%22condProperty%22:%2216%22,%22mutiCond%22:%221%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22105$%E6%89%80%E5%B1%9E%E5%8C%BA%E5%8E%BF%22%7D,%7B%22condId%22:%223%22,%22fieldName%22:%22create_time%22,%22compType%22:%22%E4%BB%8B%E4%BA%8E%22,%22dataTypeID%22:%2211%22,%22values%22:%5B%22"+date+"%2000:00:00%22,%22"+date+"%2023:59:59%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22%22%7D%5D%7D");
        queryParam.put("headerColumns", "");
//        queryParam.put("page", "1");
//        queryParam.put("rows", "100");
        queryParam.put("missionID",-1);
        queryParam.put("hidenCond","ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3Nicp");
        String queryUrl = "http://171.221.172.74:6888/eUrbanMIS/home/stat/query/getqueryresult";
        String queryUrl_question = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat";
        JSONArray data = new JSONArray();
        HttpResponse queryRes = HttpRequest.get(queryUrl)
                .form(queryParam)
                .cookie(cookies)
                .execute();

        JSONObject queryJsonRes = JSON.parseObject(queryRes.body());

        JSONObject makeJson = new JSONObject();
        makeJson.put("infoKey","今日上报问题数");
        makeJson.put("infoVal",queryJsonRes.get("total"));




        HashMap<String, Object> queryParam_case = new HashMap<>();
        queryParam_case.put("queryID", "2");
        queryParam_case.put("condparams", "{\"queryConds\": %5B%7B%22condId%22:%221%22,%22fieldName%22:%22create_time%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%2211%22,%22values%22:%5B%22"+date+"%2000:00:00%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22%22%7D,%7B%22condId%22:%228%22,%22fieldName%22:%22district_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22126%22,%22fieldTypeInfo%22:%22(district_id%20in%20(%7B0%7D)%20or%20street_id%20in%20(%7B1%7D)%20or%20community_id%20in%20(%7B2%7D))%22,%22values%22:%5B%220:3;1:100,101,102,103,104,105,106,107,108,109,110,111,112;2:10090,10091,10092,10093,10103,10104,10149,10587,10589,10621,10003,10004,10082,10083,10084,10085,10086,10087,10088,10089,10027,10031,10052,10053,10054,10070,10071,10072,10094,10095,10096,10097,10098,10099,10100,10101,10102,10568,10577,10620,10015,10016,10017,10047,10008,10009,10010,10040,10041,10042,10062,10077,10078,10563,10571,10055,10056,10057,10058,10073,10074,10075,10076,10000,10001,10002,10005,10006,10007,10033,10034,10035,10036,10037,10038,10039,10059,10060,10061,10080,10081,10023,10024,10025,10026,10079,10578,10018,10019,10020,10021,10022,10028,10029,10030,10032,10011,10012,10013,10014,10043,10044,10045,10046,10063,10064,10065,10066,10588,10048,10049,10050,10067,10068,10069,10590%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%221$%E5%8C%BA%E5%9F%9F%E6%9D%A1%E4%BB%B6%22%7D%5D}");
        queryParam_case.put("hidenCond", "ZXZlbnRfc3JjX2lkIGluICgnMScsJzUnLCczNCcsJzcxJywnNzInLCc3NicsJzQnLCc3NycsJzc4JywnNzknLCc4MCcp");
        queryParam_case.put("headerColumns", "");
        queryParam_case.put("antiParam", "");
//        queryParam.put("rows", "100");
        queryParam_case.put("missionID",-1);
        queryParam_case.put("condInfo","    【上报时间  等于  "+date+" 00:00:00  】并且      【问题状态  等于  结案  】并且      【所属区域  等于  金牛区;茶店子街道;奥林社区;化成社区;锦城社区;育苗路社区;金沙公园东;金沙公园社;金沙公园北;高家社区;跃进社区;郞家社区;凤凰山街道;凤翔社区;金韦社区;抚琴街道;光荣小区社;金沙路社区;圃园路社区;铁路新村社;金琴路社区;金鱼街社区;西南街社区;西北街社区;荷花池街道;城隍庙社区;五丁路社区;西北桥社区;西三巷社区;杨柳巷社区;东一路社区;荷花池社区;互助路社区;金泉街道;高家社区;金桥社区;蜀西社区;土桥社区;迎宾路社区;淳风桥社区;金科苑社区;两河社区;清水河社区;何家社区;互助社区;郞家社区;九里堤街道;九里堤北路;康禧路社区;星河路社区;西南交大社;沙河源街道;川建社区;友联社区;长久社区;古柏社区;踏水桥社区;新桥社区;政通路社区;王贾桥社区;泉水社区;汇泽路社区;陆家桥社区;驷马桥街道;工人村社区;恒德路社区;马鞍东路社;星辉东路社;高笋塘社区;树蓓街社区;一环路北四;红花社区;天回镇街道;车站社区;红星社区;金华社区;土门社区;银杏园社区;长胜社区;白塔社区;杜家碾社区;甘油社区;明月社区;石门社区;天回社区;余家巷社区;大湾社区;太华社区;万圣社区;宝年社区;木龙湾社区;五块石街道;五福社区;五块石社区;五块石新社;玉局庵社区;五福新社区;铁路;西安路街道;白果林社区;金琴南路社;青羊北路社;永陵社区;枣子巷社区;花牌坊社区;金仙桥社区;马家花园社;西体路社区;西华街道;华府社区;金牛社区;瓦子社区;跃进社区;富家社区;侯家社区;涧漕社区;金罗社区;府河新居社;青杠社区;兴盛社区;兴盛世家社;金桥社区;营门口街道;银桂桥社区;银沙路社区;营门口路社;茶店社区;花照社区;长庆路社区;跃进社区  】");
        HttpResponse queryRes_case = HttpRequest.post(queryUrl)
                .form(queryParam_case)
                .cookie(cookies)
                .execute();
        JSONObject queryJsonRes_case= JSON.parseObject(queryRes_case.body());

        JSONObject makeJson_case = new JSONObject();

        makeJson_case.put("infoKey","今日上报案件数");
        makeJson_case.put("infoVal",queryJsonRes_case.get("total"));
        data.add(makeJson_case);

        HashMap<String, Object> queryParam_case_ended = new HashMap<>();
        queryParam_case_ended.put("queryID", "2");
        queryParam_case_ended.put("condparams", "{\"queryConds\": %5B%7B%22condId%22:%221%22,%22fieldName%22:%22create_time%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%2211%22,%22values%22:%5B%22"+date+"%2000:00:00%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22%22%7D,%7B%22condId%22:%225%22,%22fieldName%22:%22event_state_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22124%22,%22fieldTypeInfo%22:%22$$vc_act_property_list_wtzt$item_id$0$item_name%22,%22values%22:%5B%22'%E7%BB%93%E6%A1%88'%22%5D,%22condProperty%22:%2216%22,%22mutiCond%22:%221%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22109$%E9%97%AE%E9%A2%98%E7%8A%B6%E6%80%81%22%7D,%7B%22condId%22:%228%22,%22fieldName%22:%22district_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22126%22,%22fieldTypeInfo%22:%22(district_id%20in%20(%7B0%7D)%20or%20street_id%20in%20(%7B1%7D)%20or%20community_id%20in%20(%7B2%7D))%22,%22values%22:%5B%220:3;1:100,101,102,103,104,105,106,107,108,109,110,111,112;2:10090,10091,10092,10093,10103,10104,10149,10587,10589,10621,10003,10004,10082,10083,10084,10085,10086,10087,10088,10089,10027,10031,10052,10053,10054,10070,10071,10072,10094,10095,10096,10097,10098,10099,10100,10101,10102,10568,10577,10620,10015,10016,10017,10047,10008,10009,10010,10040,10041,10042,10062,10077,10078,10563,10571,10055,10056,10057,10058,10073,10074,10075,10076,10000,10001,10002,10005,10006,10007,10033,10034,10035,10036,10037,10038,10039,10059,10060,10061,10080,10081,10023,10024,10025,10026,10079,10578,10018,10019,10020,10021,10022,10028,10029,10030,10032,10011,10012,10013,10014,10043,10044,10045,10046,10063,10064,10065,10066,10588,10048,10049,10050,10067,10068,10069,10590%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%221$%E5%8C%BA%E5%9F%9F%E6%9D%A1%E4%BB%B6%22%7D%5D}");
        queryParam_case_ended.put("hidenCond", "ZXZlbnRfc3JjX2lkIGluICgnMScsJzUnLCczNCcsJzcxJywnNzInLCc3NicsJzQnLCc3NycsJzc4JywnNzknLCc4MCcp");
        queryParam_case_ended.put("headerColumns", "");
//        queryParam.put("page", "1");
//        queryParam.put("rows", "100");
        queryParam_case_ended.put("missionID",-1);
        queryParam_case_ended.put("condInfo","   【上报时间  等于  "+date+" 00:00:00  】并且      【所属区域  等于  金牛区;茶店子街道;奥林社区;化成社区;锦城社区;育苗路社区;金沙公园东;金沙公园社;金沙公园北;高家社区;跃进社区;郞家社区;凤凰山街道;凤翔社区;金韦社区;抚琴街道;光荣小区社;金沙路社区;圃园路社区;铁路新村社;金琴路社区;金鱼街社区;西南街社区;西北街社区;荷花池街道;城隍庙社区;五丁路社区;西北桥社区;西三巷社区;杨柳巷社区;东一路社区;荷花池社区;互助路社区;金泉街道;高家社区;金桥社区;蜀西社区;土桥社区;迎宾路社区;淳风桥社区;金科苑社区;两河社区;清水河社区;何家社区;互助社区;郞家社区;九里堤街道;九里堤北路;康禧路社区;星河路社区;西南交大社;沙河源街道;川建社区;友联社区;长久社区;古柏社区;踏水桥社区;新桥社区;政通路社区;王贾桥社区;泉水社区;汇泽路社区;陆家桥社区;驷马桥街道;工人村社区;恒德路社区;马鞍东路社;星辉东路社;高笋塘社区;树蓓街社区;一环路北四;红花社区;天回镇街道;车站社区;红星社区;金华社区;土门社区;银杏园社区;长胜社区;白塔社区;杜家碾社区;甘油社区;明月社区;石门社区;天回社区;余家巷社区;大湾社区;太华社区;万圣社区;宝年社区;木龙湾社区;五块石街道;五福社区;五块石社区;五块石新社;玉局庵社区;五福新社区;铁路;西安路街道;白果林社区;金琴南路社;青羊北路社;永陵社区;枣子巷社区;花牌坊社区;金仙桥社区;马家花园社;西体路社区;西华街道;华府社区;金牛社区;瓦子社区;跃进社区;富家社区;侯家社区;涧漕社区;金罗社区;府河新居社;青杠社区;兴盛社区;兴盛世家社;金桥社区;营门口街道;银桂桥社区;银沙路社区;营门口路社;茶店社区;花照社区;长庆路社区;跃进社区  】");
        HttpResponse queryRes_case_ended = HttpRequest.post(queryUrl)
                .form(queryParam_case_ended)
                .cookie(cookies)
                .execute();
        JSONObject queryJsonRes_case_ended= JSON.parseObject(queryRes_case_ended.body());
        JSONObject makeJson_case_ended = new JSONObject();
        makeJson_case_ended.put("infoKey","今日已结案数");
        makeJson_case_ended.put("infoVal",queryJsonRes_case_ended.get("total"));
        data.add(makeJson_case_ended);
        JSONObject makeJson_case_rate = new JSONObject();

        makeJson_case_rate.put("infoKey","今日结案率");
        if (queryJsonRes_case.getIntValue("total") == 0){
            makeJson_case_rate.put("infoVal","暂无案件");
        }else{
            makeJson_case_rate.put("infoVal",queryJsonRes_case_ended.getIntValue("total")*100/queryJsonRes_case.getIntValue("total")+"%");

        }

        //makeJson_case_rate.put("infoValue",rate);
        data.add(makeJson_case_rate);
        data.add(makeJson);

        System.out.println("Request:/szcg/mainInfo"+time);
    return CommonResult.success(data);

    }


    @RequestMapping("/getCase")
    public CommonResult <List<Table>> getCase(){
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
        Date calDate= new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week= formatter.format(DateUtil.getBeginDayOfWeek());
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

        //System.out.println(loginRes.getCookies().toString());

        HashMap<String, Object> queryParam_case = new HashMap<>();
        String queryUrl = "http://171.221.172.74:6888/eUrbanMIS/home/stat/query/getqueryresult";
        queryParam_case.put("queryID", "2");
        queryParam_case.put("condparams", "{\"queryConds\": %5B%7B%22condId%22:%221%22,%22fieldName%22:%22create_time%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%2211%22,%22values%22:%5B%22"+date+"%2000:00:00%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22%22%7D,%7B%22condId%22:%228%22,%22fieldName%22:%22district_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22126%22,%22fieldTypeInfo%22:%22(district_id%20in%20(%7B0%7D)%20or%20street_id%20in%20(%7B1%7D)%20or%20community_id%20in%20(%7B2%7D))%22,%22values%22:%5B%220:3;1:100,101,102,103,104,105,106,107,108,109,110,111,112;2:10090,10091,10092,10093,10103,10104,10149,10587,10589,10621,10003,10004,10082,10083,10084,10085,10086,10087,10088,10089,10027,10031,10052,10053,10054,10070,10071,10072,10094,10095,10096,10097,10098,10099,10100,10101,10102,10568,10577,10620,10015,10016,10017,10047,10008,10009,10010,10040,10041,10042,10062,10077,10078,10563,10571,10055,10056,10057,10058,10073,10074,10075,10076,10000,10001,10002,10005,10006,10007,10033,10034,10035,10036,10037,10038,10039,10059,10060,10061,10080,10081,10023,10024,10025,10026,10079,10578,10018,10019,10020,10021,10022,10028,10029,10030,10032,10011,10012,10013,10014,10043,10044,10045,10046,10063,10064,10065,10066,10588,10048,10049,10050,10067,10068,10069,10590%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%221$%E5%8C%BA%E5%9F%9F%E6%9D%A1%E4%BB%B6%22%7D%5D}");
        queryParam_case.put("hidenCond", "ZXZlbnRfc3JjX2lkIGluICgnMScsJzUnLCczNCcsJzcxJywnNzInLCc3NicsJzQnLCc3NycsJzc4JywnNzknLCc4MCcp");
        queryParam_case.put("headerColumns", "");
        queryParam_case.put("antiParam", "");
//        queryParam.put("rows", "100");
        queryParam_case.put("missionID",-1);
        queryParam_case.put("condInfo","    【上报时间  等于  "+date+" 00:00:00  】并且      【问题状态  等于  结案  】并且      【所属区域  等于  金牛区;茶店子街道;奥林社区;化成社区;锦城社区;育苗路社区;金沙公园东;金沙公园社;金沙公园北;高家社区;跃进社区;郞家社区;凤凰山街道;凤翔社区;金韦社区;抚琴街道;光荣小区社;金沙路社区;圃园路社区;铁路新村社;金琴路社区;金鱼街社区;西南街社区;西北街社区;荷花池街道;城隍庙社区;五丁路社区;西北桥社区;西三巷社区;杨柳巷社区;东一路社区;荷花池社区;互助路社区;金泉街道;高家社区;金桥社区;蜀西社区;土桥社区;迎宾路社区;淳风桥社区;金科苑社区;两河社区;清水河社区;何家社区;互助社区;郞家社区;九里堤街道;九里堤北路;康禧路社区;星河路社区;西南交大社;沙河源街道;川建社区;友联社区;长久社区;古柏社区;踏水桥社区;新桥社区;政通路社区;王贾桥社区;泉水社区;汇泽路社区;陆家桥社区;驷马桥街道;工人村社区;恒德路社区;马鞍东路社;星辉东路社;高笋塘社区;树蓓街社区;一环路北四;红花社区;天回镇街道;车站社区;红星社区;金华社区;土门社区;银杏园社区;长胜社区;白塔社区;杜家碾社区;甘油社区;明月社区;石门社区;天回社区;余家巷社区;大湾社区;太华社区;万圣社区;宝年社区;木龙湾社区;五块石街道;五福社区;五块石社区;五块石新社;玉局庵社区;五福新社区;铁路;西安路街道;白果林社区;金琴南路社;青羊北路社;永陵社区;枣子巷社区;花牌坊社区;金仙桥社区;马家花园社;西体路社区;西华街道;华府社区;金牛社区;瓦子社区;跃进社区;富家社区;侯家社区;涧漕社区;金罗社区;府河新居社;青杠社区;兴盛社区;兴盛世家社;金桥社区;营门口街道;银桂桥社区;银沙路社区;营门口路社;茶店社区;花照社区;长庆路社区;跃进社区  】");
        HttpResponse queryRes_case = HttpRequest.post(queryUrl)
                .form(queryParam_case)
                .cookie(cookies)
                .execute();

        String queryUrl_question = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat";
        List<Table> table = new ArrayList<>();
        JSONArray data = new JSONArray();
        HttpResponse queryRes = HttpRequest.get(queryUrl)
                .form(queryParam_case)
                .cookie(cookies)
                .execute();
        HashMap<String, Object> queryParam_case_day_ended = new HashMap<>();
        queryParam_case_day_ended.put("queryID", "2");
        queryParam_case_day_ended.put("condparams", "{\"queryConds\": %5B%7B%22condId%22:%221%22,%22fieldName%22:%22create_time%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%2211%22,%22values%22:%5B%22"+date+"%2000:00:00%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22%22%7D,%7B%22condId%22:%225%22,%22fieldName%22:%22event_state_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22124%22,%22fieldTypeInfo%22:%22$$vc_act_property_list_wtzt$item_id$0$item_name%22,%22values%22:%5B%22'%E7%BB%93%E6%A1%88'%22%5D,%22condProperty%22:%2216%22,%22mutiCond%22:%221%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22109$%E9%97%AE%E9%A2%98%E7%8A%B6%E6%80%81%22%7D,%7B%22condId%22:%228%22,%22fieldName%22:%22district_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22126%22,%22fieldTypeInfo%22:%22(district_id%20in%20(%7B0%7D)%20or%20street_id%20in%20(%7B1%7D)%20or%20community_id%20in%20(%7B2%7D))%22,%22values%22:%5B%220:3;1:100,101,102,103,104,105,106,107,108,109,110,111,112;2:10090,10091,10092,10093,10103,10104,10149,10587,10589,10621,10003,10004,10082,10083,10084,10085,10086,10087,10088,10089,10027,10031,10052,10053,10054,10070,10071,10072,10094,10095,10096,10097,10098,10099,10100,10101,10102,10568,10577,10620,10015,10016,10017,10047,10008,10009,10010,10040,10041,10042,10062,10077,10078,10563,10571,10055,10056,10057,10058,10073,10074,10075,10076,10000,10001,10002,10005,10006,10007,10033,10034,10035,10036,10037,10038,10039,10059,10060,10061,10080,10081,10023,10024,10025,10026,10079,10578,10018,10019,10020,10021,10022,10028,10029,10030,10032,10011,10012,10013,10014,10043,10044,10045,10046,10063,10064,10065,10066,10588,10048,10049,10050,10067,10068,10069,10590%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%221$%E5%8C%BA%E5%9F%9F%E6%9D%A1%E4%BB%B6%22%7D%5D}");
        queryParam_case_day_ended.put("hidenCond", "ZXZlbnRfc3JjX2lkIGluICgnMScsJzUnLCczNCcsJzcxJywnNzInLCc3NicsJzQnLCc3NycsJzc4JywnNzknLCc4MCcp");
        queryParam_case_day_ended.put("headerColumns", "");
//        queryParam.put("page", "1");
//        queryParam.put("rows", "100");
        queryParam_case_day_ended.put("missionID",-1);
        queryParam_case_day_ended.put("condInfo","   【上报时间  等于  "+date+"  】并且      【所属区域  等于  金牛区;茶店子街道;奥林社区;化成社区;锦城社区;育苗路社区;金沙公园东;金沙公园社;金沙公园北;高家社区;跃进社区;郞家社区;凤凰山街道;凤翔社区;金韦社区;抚琴街道;光荣小区社;金沙路社区;圃园路社区;铁路新村社;金琴路社区;金鱼街社区;西南街社区;西北街社区;荷花池街道;城隍庙社区;五丁路社区;西北桥社区;西三巷社区;杨柳巷社区;东一路社区;荷花池社区;互助路社区;金泉街道;高家社区;金桥社区;蜀西社区;土桥社区;迎宾路社区;淳风桥社区;金科苑社区;两河社区;清水河社区;何家社区;互助社区;郞家社区;九里堤街道;九里堤北路;康禧路社区;星河路社区;西南交大社;沙河源街道;川建社区;友联社区;长久社区;古柏社区;踏水桥社区;新桥社区;政通路社区;王贾桥社区;泉水社区;汇泽路社区;陆家桥社区;驷马桥街道;工人村社区;恒德路社区;马鞍东路社;星辉东路社;高笋塘社区;树蓓街社区;一环路北四;红花社区;天回镇街道;车站社区;红星社区;金华社区;土门社区;银杏园社区;长胜社区;白塔社区;杜家碾社区;甘油社区;明月社区;石门社区;天回社区;余家巷社区;大湾社区;太华社区;万圣社区;宝年社区;木龙湾社区;五块石街道;五福社区;五块石社区;五块石新社;玉局庵社区;五福新社区;铁路;西安路街道;白果林社区;金琴南路社;青羊北路社;永陵社区;枣子巷社区;花牌坊社区;金仙桥社区;马家花园社;西体路社区;西华街道;华府社区;金牛社区;瓦子社区;跃进社区;富家社区;侯家社区;涧漕社区;金罗社区;府河新居社;青杠社区;兴盛社区;兴盛世家社;金桥社区;营门口街道;银桂桥社区;银沙路社区;营门口路社;茶店社区;花照社区;长庆路社区;跃进社区  】");
        HttpResponse queryRes_case_day_ended = HttpRequest.post(queryUrl)
                .form(queryParam_case_day_ended)
                .cookie(cookies)
                .execute();
        JSONObject queryJsonRes_case_day_ended= JSON.parseObject(queryRes_case_day_ended.body());
        JSONObject makeJson_case_day_ended = new JSONObject();

        JSONObject queryJsonRes_case_day = JSON.parseObject(queryRes.body());
        JSONObject makeJson_case_day = new JSONObject();
        String[] headerNames_case_day = new String[]{"今日上报案件数", "今日结案数", "今日结案率"};
        String[] dataNames_case_day = new String[]{"all", "finish", "rate"};
        makeJson_case_day.put("all",queryJsonRes_case_day.get("total"));
        makeJson_case_day.put("finish",queryJsonRes_case_day_ended.get("total"));
        if(queryJsonRes_case_day.getIntValue("total") == 0) {
            makeJson_case_day.put("rate","暂无案件");
        }
        else{
            makeJson_case_day.put("rate", queryJsonRes_case_day_ended.getIntValue("total") * 100 / queryJsonRes_case_day.getIntValue("total") + "%");
        }
        data.add(makeJson_case_day);
        table.add(new Table(headerNames_case_day,dataNames_case_day,data));
        System.out.println("Request:/szcg/getCase"+time);


        String[] headerNames_case_week = new String[]{"本周上报案件数", "本周结案数", "本周结案率"};
        String[] dataNames_case_week = new String[]{"all", "finish", "rate"};
        JSONArray data1 = new JSONArray();
        HashMap<String, Object> queryParam_case_week = new HashMap<>();
        queryParam_case_week.put("queryID", "2");
        queryParam_case_week.put("condparams","{\"queryConds\": %5B%7B%22condId%22:%221%22,%22fieldName%22:%22create_time%22,%22compType%22:%22%E5%A4%A7%E4%BA%8E%22,%22dataTypeID%22:%2211%22,%22values%22:%5B%22"+date_week+"%2000:00:00%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22%22%7D,%7B%22condId%22:%228%22,%22fieldName%22:%22district_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22126%22,%22fieldTypeInfo%22:%22(district_id%20in%20(%7B0%7D)%20or%20street_id%20in%20(%7B1%7D)%20or%20community_id%20in%20(%7B2%7D))%22,%22values%22:%5B%220:3;1:100,101,102,103,104,105,106,107,108,109,110,111,112;2:10090,10091,10092,10093,10103,10104,10149,10587,10589,10621,10003,10004,10082,10083,10084,10085,10086,10087,10088,10089,10027,10031,10052,10053,10054,10070,10071,10072,10094,10095,10096,10097,10098,10099,10100,10101,10102,10568,10577,10620,10015,10016,10017,10047,10008,10009,10010,10040,10041,10042,10062,10077,10078,10563,10571,10055,10056,10057,10058,10073,10074,10075,10076,10000,10001,10002,10005,10006,10007,10033,10034,10035,10036,10037,10038,10039,10059,10060,10061,10080,10081,10023,10024,10025,10026,10079,10578,10018,10019,10020,10021,10022,10028,10029,10030,10032,10011,10012,10013,10014,10043,10044,10045,10046,10063,10064,10065,10066,10588,10048,10049,10050,10067,10068,10069,10590%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%221$%E5%8C%BA%E5%9F%9F%E6%9D%A1%E4%BB%B6%22%7D%5D}");
        queryParam_case_week.put("hidenCond", "ZXZlbnRfc3JjX2lkIGluICgnMScsJzUnLCczNCcsJzcxJywnNzInLCc3NicsJzQnLCc3NycsJzc4JywnNzknLCc4MCcp");
        queryParam_case_week.put("headerColumns", "");
        queryParam_case_week.put("missionID",-1);
        queryParam_case_week.put("condInfo","   【上报时间  大于  "+date_week+"  】并且      【所属区域  等于  金牛区;茶店子街道;奥林社区;化成社区;锦城社区;育苗路社区;金沙公园东;金沙公园社;金沙公园北;高家社区;跃进社区;郞家社区;凤凰山街道;凤翔社区;金韦社区;抚琴街道;光荣小区社;金沙路社区;圃园路社区;铁路新村社;金琴路社区;金鱼街社区;西南街社区;西北街社区;荷花池街道;城隍庙社区;五丁路社区;西北桥社区;西三巷社区;杨柳巷社区;东一路社区;荷花池社区;互助路社区;金泉街道;高家社区;金桥社区;蜀西社区;土桥社区;迎宾路社区;淳风桥社区;金科苑社区;两河社区;清水河社区;何家社区;互助社区;郞家社区;九里堤街道;九里堤北路;康禧路社区;星河路社区;西南交大社;沙河源街道;川建社区;友联社区;长久社区;古柏社区;踏水桥社区;新桥社区;政通路社区;王贾桥社区;泉水社区;汇泽路社区;陆家桥社区;驷马桥街道;工人村社区;恒德路社区;马鞍东路社;星辉东路社;高笋塘社区;树蓓街社区;一环路北四;红花社区;天回镇街道;车站社区;红星社区;金华社区;土门社区;银杏园社区;长胜社区;白塔社区;杜家碾社区;甘油社区;明月社区;石门社区;天回社区;余家巷社区;大湾社区;太华社区;万圣社区;宝年社区;木龙湾社区;五块石街道;五福社区;五块石社区;五块石新社;玉局庵社区;五福新社区;铁路;西安路街道;白果林社区;金琴南路社;青羊北路社;永陵社区;枣子巷社区;花牌坊社区;金仙桥社区;马家花园社;西体路社区;西华街道;华府社区;金牛社区;瓦子社区;跃进社区;富家社区;侯家社区;涧漕社区;金罗社区;府河新居社;青杠社区;兴盛社区;兴盛世家社;金桥社区;营门口街道;银桂桥社区;银沙路社区;营门口路社;茶店社区;花照社区;长庆路社区;跃进社区  】");

        HttpResponse queryRes_case_week = HttpRequest.post(queryUrl)
                .form(queryParam_case_week)
                .cookie(cookies)
                .execute();
        JSONObject queryJsonRes_case_week = JSON.parseObject(queryRes_case_week.body());

        JSONObject makeJson_case_week = new JSONObject();
        HashMap<String, Object> queryParam_case_week_ended = new HashMap<>();
        queryParam_case_week_ended.put("queryID", "2");
        queryParam_case_week_ended.put("condparams","{\"queryConds\": %5B%7B%22condId%22:%221%22,%22fieldName%22:%22create_time%22,%22compType%22:%22%E5%A4%A7%E4%BA%8E%22,%22dataTypeID%22:%2211%22,%22values%22:%5B%22"+date_week+"%2000:00:00%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22%22%7D,%7B%22condId%22:%225%22,%22fieldName%22:%22event_state_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22124%22,%22fieldTypeInfo%22:%22$$vc_act_property_list_wtzt$item_id$0$item_name%22,%22values%22:%5B%22'%E7%BB%93%E6%A1%88'%22%5D,%22condProperty%22:%2216%22,%22mutiCond%22:%221%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22109$%E9%97%AE%E9%A2%98%E7%8A%B6%E6%80%81%22%7D,%7B%22condId%22:%228%22,%22fieldName%22:%22district_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22126%22,%22fieldTypeInfo%22:%22(district_id%20in%20(%7B0%7D)%20or%20street_id%20in%20(%7B1%7D)%20or%20community_id%20in%20(%7B2%7D))%22,%22values%22:%5B%220:3;1:100,101,102,103,104,105,106,107,108,109,110,111,112;2:10090,10091,10092,10093,10103,10104,10149,10587,10589,10621,10003,10004,10082,10083,10084,10085,10086,10087,10088,10089,10027,10031,10052,10053,10054,10070,10071,10072,10094,10095,10096,10097,10098,10099,10100,10101,10102,10568,10577,10620,10015,10016,10017,10047,10008,10009,10010,10040,10041,10042,10062,10077,10078,10563,10571,10055,10056,10057,10058,10073,10074,10075,10076,10000,10001,10002,10005,10006,10007,10033,10034,10035,10036,10037,10038,10039,10059,10060,10061,10080,10081,10023,10024,10025,10026,10079,10578,10018,10019,10020,10021,10022,10028,10029,10030,10032,10011,10012,10013,10014,10043,10044,10045,10046,10063,10064,10065,10066,10588,10048,10049,10050,10067,10068,10069,10590%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%221$%E5%8C%BA%E5%9F%9F%E6%9D%A1%E4%BB%B6%22%7D%5D}");
        queryParam_case_week_ended.put("hidenCond", "ZXZlbnRfc3JjX2lkIGluICgnMScsJzUnLCczNCcsJzcxJywnNzInLCc3NicsJzQnLCc3NycsJzc4JywnNzknLCc4MCcp");
        queryParam_case_week_ended.put("headerColumns", "");
        queryParam_case_week_ended.put("missionID",-1);
        queryParam_case_week_ended.put("condInfo","   【上报时间  大于  "+date_week+"  】并且      【所属区域  等于  金牛区;茶店子街道;奥林社区;化成社区;锦城社区;育苗路社区;金沙公园东;金沙公园社;金沙公园北;高家社区;跃进社区;郞家社区;凤凰山街道;凤翔社区;金韦社区;抚琴街道;光荣小区社;金沙路社区;圃园路社区;铁路新村社;金琴路社区;金鱼街社区;西南街社区;西北街社区;荷花池街道;城隍庙社区;五丁路社区;西北桥社区;西三巷社区;杨柳巷社区;东一路社区;荷花池社区;互助路社区;金泉街道;高家社区;金桥社区;蜀西社区;土桥社区;迎宾路社区;淳风桥社区;金科苑社区;两河社区;清水河社区;何家社区;互助社区;郞家社区;九里堤街道;九里堤北路;康禧路社区;星河路社区;西南交大社;沙河源街道;川建社区;友联社区;长久社区;古柏社区;踏水桥社区;新桥社区;政通路社区;王贾桥社区;泉水社区;汇泽路社区;陆家桥社区;驷马桥街道;工人村社区;恒德路社区;马鞍东路社;星辉东路社;高笋塘社区;树蓓街社区;一环路北四;红花社区;天回镇街道;车站社区;红星社区;金华社区;土门社区;银杏园社区;长胜社区;白塔社区;杜家碾社区;甘油社区;明月社区;石门社区;天回社区;余家巷社区;大湾社区;太华社区;万圣社区;宝年社区;木龙湾社区;五块石街道;五福社区;五块石社区;五块石新社;玉局庵社区;五福新社区;铁路;西安路街道;白果林社区;金琴南路社;青羊北路社;永陵社区;枣子巷社区;花牌坊社区;金仙桥社区;马家花园社;西体路社区;西华街道;华府社区;金牛社区;瓦子社区;跃进社区;富家社区;侯家社区;涧漕社区;金罗社区;府河新居社;青杠社区;兴盛社区;兴盛世家社;金桥社区;营门口街道;银桂桥社区;银沙路社区;营门口路社;茶店社区;花照社区;长庆路社区;跃进社区  】");

        HttpResponse queryRes_case_week_ended = HttpRequest.post(queryUrl)
                .form(queryParam_case_week_ended)
                .cookie(cookies)
                .execute();
        JSONObject queryJsonRes_case_week_ended = JSON.parseObject(queryRes_case_week_ended.body());



        makeJson_case_week.put("all",queryJsonRes_case_week.get("total"));
        makeJson_case_week.put("finish",queryJsonRes_case_week_ended.get("total"));
        if(queryJsonRes_case_week.getIntValue("total") == 0) {
            makeJson_case_week.put("rate","暂无案件");
        }
        else makeJson_case_week.put("rate",queryJsonRes_case_week_ended.getIntValue("total")*100/queryJsonRes_case_week.getIntValue("total")+"%");
        data1.add(makeJson_case_week);
        table.add(new Table(headerNames_case_week,dataNames_case_week,data1));










        String[] headerNames_case_month = new String[]{"本月上报案件数", "本月结案数", "本月结案率"};
        String[] dataNames_case_month = new String[]{"all", "finish", "rate"};
        JSONArray data2 = new JSONArray();

        HashMap<String, Object> queryParam_case_month = new HashMap<>();
        queryParam_case_month.put("queryID", "2");
        queryParam_case_month.put("condparams","{\"queryConds\": %5B%7B%22condId%22:%221%22,%22fieldName%22:%22create_time%22,%22compType%22:%22%E5%A4%A7%E4%BA%8E%22,%22dataTypeID%22:%2211%22,%22values%22:%5B%22"+date_month+"-01%2000:00:00%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22%22%7D,%7B%22condId%22:%228%22,%22fieldName%22:%22district_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22126%22,%22fieldTypeInfo%22:%22(district_id%20in%20(%7B0%7D)%20or%20street_id%20in%20(%7B1%7D)%20or%20community_id%20in%20(%7B2%7D))%22,%22values%22:%5B%220:3;1:100,101,102,103,104,105,106,107,108,109,110,111,112;2:10090,10091,10092,10093,10103,10104,10149,10587,10589,10621,10003,10004,10082,10083,10084,10085,10086,10087,10088,10089,10027,10031,10052,10053,10054,10070,10071,10072,10094,10095,10096,10097,10098,10099,10100,10101,10102,10568,10577,10620,10015,10016,10017,10047,10008,10009,10010,10040,10041,10042,10062,10077,10078,10563,10571,10055,10056,10057,10058,10073,10074,10075,10076,10000,10001,10002,10005,10006,10007,10033,10034,10035,10036,10037,10038,10039,10059,10060,10061,10080,10081,10023,10024,10025,10026,10079,10578,10018,10019,10020,10021,10022,10028,10029,10030,10032,10011,10012,10013,10014,10043,10044,10045,10046,10063,10064,10065,10066,10588,10048,10049,10050,10067,10068,10069,10590%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%221$%E5%8C%BA%E5%9F%9F%E6%9D%A1%E4%BB%B6%22%7D%5D}");

        queryParam_case_month.put("hidenCond", "ZXZlbnRfc3JjX2lkIGluICgnMScsJzUnLCczNCcsJzcxJywnNzInLCc3NicsJzQnLCc3NycsJzc4JywnNzknLCc4MCcp");
        queryParam_case_month.put("headerColumns", "");
//        queryParam.put("page", "1");
//        queryParam.put("rows", "100");
        queryParam_case_month.put("missionID",-1);
        queryParam_case_month.put("condInfo","   【上报时间  大于  "+date_month+"-01  】并且      【所属区域  等于  金牛区;茶店子街道;奥林社区;化成社区;锦城社区;育苗路社区;金沙公园东;金沙公园社;金沙公园北;高家社区;跃进社区;郞家社区;凤凰山街道;凤翔社区;金韦社区;抚琴街道;光荣小区社;金沙路社区;圃园路社区;铁路新村社;金琴路社区;金鱼街社区;西南街社区;西北街社区;荷花池街道;城隍庙社区;五丁路社区;西北桥社区;西三巷社区;杨柳巷社区;东一路社区;荷花池社区;互助路社区;金泉街道;高家社区;金桥社区;蜀西社区;土桥社区;迎宾路社区;淳风桥社区;金科苑社区;两河社区;清水河社区;何家社区;互助社区;郞家社区;九里堤街道;九里堤北路;康禧路社区;星河路社区;西南交大社;沙河源街道;川建社区;友联社区;长久社区;古柏社区;踏水桥社区;新桥社区;政通路社区;王贾桥社区;泉水社区;汇泽路社区;陆家桥社区;驷马桥街道;工人村社区;恒德路社区;马鞍东路社;星辉东路社;高笋塘社区;树蓓街社区;一环路北四;红花社区;天回镇街道;车站社区;红星社区;金华社区;土门社区;银杏园社区;长胜社区;白塔社区;杜家碾社区;甘油社区;明月社区;石门社区;天回社区;余家巷社区;大湾社区;太华社区;万圣社区;宝年社区;木龙湾社区;五块石街道;五福社区;五块石社区;五块石新社;玉局庵社区;五福新社区;铁路;西安路街道;白果林社区;金琴南路社;青羊北路社;永陵社区;枣子巷社区;花牌坊社区;金仙桥社区;马家花园社;西体路社区;西华街道;华府社区;金牛社区;瓦子社区;跃进社区;富家社区;侯家社区;涧漕社区;金罗社区;府河新居社;青杠社区;兴盛社区;兴盛世家社;金桥社区;营门口街道;银桂桥社区;银沙路社区;营门口路社;茶店社区;花照社区;长庆路社区;跃进社区  】");


        HashMap<String, Object> queryParam_case_month_ended = new HashMap<>();
        queryParam_case_month_ended.put("queryID", "2");
        queryParam_case_month_ended.put("condparams","{\"queryConds\": %5B%7B%22condId%22:%221%22,%22fieldName%22:%22create_time%22,%22compType%22:%22%E5%A4%A7%E4%BA%8E%22,%22dataTypeID%22:%2211%22,%22values%22:%5B%22"+date_month+"-01%2000:00:00%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22%22%7D,%7B%22condId%22:%225%22,%22fieldName%22:%22event_state_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22124%22,%22fieldTypeInfo%22:%22$$vc_act_property_list_wtzt$item_id$0$item_name%22,%22values%22:%5B%22'%E7%BB%93%E6%A1%88'%22%5D,%22condProperty%22:%2216%22,%22mutiCond%22:%221%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22109$%E9%97%AE%E9%A2%98%E7%8A%B6%E6%80%81%22%7D,%7B%22condId%22:%228%22,%22fieldName%22:%22district_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22126%22,%22fieldTypeInfo%22:%22(district_id%20in%20(%7B0%7D)%20or%20street_id%20in%20(%7B1%7D)%20or%20community_id%20in%20(%7B2%7D))%22,%22values%22:%5B%220:3;1:100,101,102,103,104,105,106,107,108,109,110,111,112;2:10090,10091,10092,10093,10103,10104,10149,10587,10589,10621,10003,10004,10082,10083,10084,10085,10086,10087,10088,10089,10027,10031,10052,10053,10054,10070,10071,10072,10094,10095,10096,10097,10098,10099,10100,10101,10102,10568,10577,10620,10015,10016,10017,10047,10008,10009,10010,10040,10041,10042,10062,10077,10078,10563,10571,10055,10056,10057,10058,10073,10074,10075,10076,10000,10001,10002,10005,10006,10007,10033,10034,10035,10036,10037,10038,10039,10059,10060,10061,10080,10081,10023,10024,10025,10026,10079,10578,10018,10019,10020,10021,10022,10028,10029,10030,10032,10011,10012,10013,10014,10043,10044,10045,10046,10063,10064,10065,10066,10588,10048,10049,10050,10067,10068,10069,10590%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%221$%E5%8C%BA%E5%9F%9F%E6%9D%A1%E4%BB%B6%22%7D%5D}");

        queryParam_case_month_ended.put("hidenCond", "ZXZlbnRfc3JjX2lkIGluICgnMScsJzUnLCczNCcsJzcxJywnNzInLCc3NicsJzQnLCc3NycsJzc4JywnNzknLCc4MCcp");
        queryParam_case_month_ended.put("headerColumns", "");
//        queryParam.put("page", "1");
//        queryParam.put("rows", "100");
        queryParam_case_month_ended.put("missionID",-1);
        queryParam_case_month_ended.put("condInfo","   【上报时间  大于  "+date_month+"-01  】 状态 已结案 并且      【所属区域  等于  金牛区;茶店子街道;奥林社区;化成社区;锦城社区;育苗路社区;金沙公园东;金沙公园社;金沙公园北;高家社区;跃进社区;郞家社区;凤凰山街道;凤翔社区;金韦社区;抚琴街道;光荣小区社;金沙路社区;圃园路社区;铁路新村社;金琴路社区;金鱼街社区;西南街社区;西北街社区;荷花池街道;城隍庙社区;五丁路社区;西北桥社区;西三巷社区;杨柳巷社区;东一路社区;荷花池社区;互助路社区;金泉街道;高家社区;金桥社区;蜀西社区;土桥社区;迎宾路社区;淳风桥社区;金科苑社区;两河社区;清水河社区;何家社区;互助社区;郞家社区;九里堤街道;九里堤北路;康禧路社区;星河路社区;西南交大社;沙河源街道;川建社区;友联社区;长久社区;古柏社区;踏水桥社区;新桥社区;政通路社区;王贾桥社区;泉水社区;汇泽路社区;陆家桥社区;驷马桥街道;工人村社区;恒德路社区;马鞍东路社;星辉东路社;高笋塘社区;树蓓街社区;一环路北四;红花社区;天回镇街道;车站社区;红星社区;金华社区;土门社区;银杏园社区;长胜社区;白塔社区;杜家碾社区;甘油社区;明月社区;石门社区;天回社区;余家巷社区;大湾社区;太华社区;万圣社区;宝年社区;木龙湾社区;五块石街道;五福社区;五块石社区;五块石新社;玉局庵社区;五福新社区;铁路;西安路街道;白果林社区;金琴南路社;青羊北路社;永陵社区;枣子巷社区;花牌坊社区;金仙桥社区;马家花园社;西体路社区;西华街道;华府社区;金牛社区;瓦子社区;跃进社区;富家社区;侯家社区;涧漕社区;金罗社区;府河新居社;青杠社区;兴盛社区;兴盛世家社;金桥社区;营门口街道;银桂桥社区;银沙路社区;营门口路社;茶店社区;花照社区;长庆路社区;跃进社区  】");




        HttpResponse queryRes_case_month = HttpRequest.post(queryUrl)
                .form(queryParam_case_month)
                .cookie(cookies)
                .execute();
        JSONObject queryJsonRes_case_month = JSON.parseObject(queryRes_case_month.body());

        JSONObject makeJson_case_month = new JSONObject();
        HttpResponse queryRes_case_month_ended = HttpRequest.post(queryUrl)
                .form(queryParam_case_month_ended)
                .cookie(cookies)
                .execute();
        JSONObject queryJsonRes_case_month_ended = JSON.parseObject(queryRes_case_month_ended.body());

        makeJson_case_month.put("all",queryJsonRes_case_month.get("total"));
        makeJson_case_month.put("finish",queryJsonRes_case_month_ended.get("total"));
        if(queryJsonRes_case_month.getIntValue("total") == 0) {
            makeJson_case_month.put("rate","暂无案件");
        }

        else makeJson_case_month.put("rate",queryJsonRes_case_month_ended.getIntValue("total")*100/queryJsonRes_case_month.getIntValue("total")+"%");


        data2.add(makeJson_case_month);

        table.add(new Table(headerNames_case_month,dataNames_case_month,data2));

        return CommonResult.success(table);


    }

    @RequestMapping("/getStatics")
    public CommonResult <List<Table>> getStatics(){
        Date calDate= new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week= formatter.format(DateUtil.getBeginDayOfWeek());
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
        List<HttpCookie> cookies = loginRes.getCookies();

        List <Table> table = new ArrayList<>();

        String queryUrl_day = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=173&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25223%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E4%25BB%258B%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%2C%2522"+date+"%252023%3A59%3A59%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3Nicp&_=1657007860027";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_day)
                .cookie(cookies)
                .execute();
        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");

        String[] headerNames_0 = new String[queryJsonRes_0.size()+2];
        String[] dataNames_0 = new String[queryJsonRes_0.size()+2];
        JSONObject makeJson_0 = new JSONObject();
        JSONArray data0 = new JSONArray();

        if (queryJsonRes_0.isEmpty())
        {makeJson_0.put("val1","本日暂无案件");
        headerNames_0[0]="本日";
        dataNames_0[0]="val1";
        }

        else {int size0;
            for (size0=0;size0<queryJsonRes_0.size();size0++){
                headerNames_0[size0] = "本日"+queryJsonRes_0.getJSONObject(size0).getString("EVENT_SRC_NAME");
                dataNames_0[size0] = "val"+size0;
                makeJson_0.put("val"+size0,queryJsonRes_0.getJSONObject(size0).get("NEED_DISPOSE_NUM"));
            }
            headerNames_0[size0] = "本日按时结案数";
            headerNames_0[size0+1] = "本日按期结案率";
            dataNames_0[size0] = "val"+size0;
            dataNames_0[size0+1] = "val"+(size0+1);
            makeJson_0.put("val"+size0,queryJsonRes_0.getJSONObject(size0-1).get("INTIME_SUPERVISE_NUM"));
            makeJson_0.put("val"+(size0+1),queryJsonRes_0.getJSONObject(size0-1).get("AQJQL"));}




        data0.add(makeJson_0);
        table.add(new Table(headerNames_0,dataNames_0,data0));



        String queryUrl_week = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=173&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25223%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E4%25BB%258B%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_week+"%252000%3A00%3A00%2522%2C%2522"+date+"%252023%3A59%3A59%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3Nicp&_=1657007860030\n";
        HttpResponse queryRes_1 = HttpRequest.get(queryUrl_week)
                .cookie(cookies)
                .execute();
        JSONArray queryJsonRes_1 = JSON.parseObject(queryRes_1.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");

        String[] headerNames_1 = new String[queryJsonRes_1.size()+2];
        String[] dataNames_1 = new String[queryJsonRes_1.size()+2];
        JSONObject makeJson_1 = new JSONObject();
        JSONArray data1 = new JSONArray();
        int size1;
        for (size1 = 0 ;size1<queryJsonRes_1.size();size1++){
            headerNames_1[size1] = "本周"+queryJsonRes_1.getJSONObject(size1).getString("EVENT_SRC_NAME");
            dataNames_1[size1] = "val"+size1;
            makeJson_1.put("val"+size1,queryJsonRes_1.getJSONObject(size1).get("NEED_DISPOSE_NUM"));

        }
        headerNames_1[size1] = "本周按期结案数";
        headerNames_1[size1+1] = "本周按期结案率";
        dataNames_1[size1] = "val"+size1;
        dataNames_1[size1+1] = "val"+(size1+1);
        makeJson_1.put("val"+size1,queryJsonRes_1.getJSONObject(size1-1).get("INTIME_SUPERVISE_NUM"));
        makeJson_1.put("val"+(size1+1),queryJsonRes_1.getJSONObject(size1-1).get("AQJQL"));
        data1.add(makeJson_1);

        table.add(new Table(headerNames_1,dataNames_1,data1));


        String queryUrl_month = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=173&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25223%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E4%25BB%258B%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_month+"-01%252000%3A00%3A00%2522%2C%2522"+date+"%252023%3A59%3A59%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3Nicp&_=1657007860030\n";
        HttpResponse queryRes_2 = HttpRequest.get(queryUrl_month)
                .cookie(cookies)
                .execute();
        JSONArray queryJsonRes_2 = JSON.parseObject(queryRes_2.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");

        String[] headerNames_2 = new String[queryJsonRes_2.size()+2];
        String[] dataNames_2 = new String[queryJsonRes_2.size()+2];
        JSONObject makeJson_2 = new JSONObject();
        JSONArray data2 = new JSONArray();
        int size2;
        for (size2 = 0 ;size2<queryJsonRes_2.size();size2++){
            headerNames_2[size2] = "本月"+queryJsonRes_2.getJSONObject(size2).getString("EVENT_SRC_NAME");
            dataNames_2[size2] = "val"+size2;
            makeJson_2.put("val"+size2,queryJsonRes_2.getJSONObject(size2).get("NEED_DISPOSE_NUM"));

        }
        headerNames_2[size2] = "本月按期结案数";
        headerNames_2[size2+1] = "本月按期结案率";
        dataNames_2[size2] = "val"+size2;
        dataNames_2[size2+1] = "val"+(size2+1);
        makeJson_2.put("val"+size2,queryJsonRes_2.getJSONObject(size2-1).get("INTIME_SUPERVISE_NUM"));
        makeJson_2.put("val"+(size2+1),queryJsonRes_2.getJSONObject(size2-1).get("AQJQL"));
        data2.add(makeJson_2);

        table.add(new Table(headerNames_2,dataNames_2,data2));

        System.out.println("Request:szcg/getStatics at " +time);
        return CommonResult.success(table);



    }

    @RequestMapping("/getSjTop5")
    public CommonResult<List<Table>> getBjTop5(){
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate= new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week= formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_day ="http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=16&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZXZlbnRfdHlwZV9pZCA9IDEgYW5kIGV2ZW50X3NyY19pZCBpbiAoJzcxJywnMScsJzM0Jyk%3D&_=1657161960815";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_day)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        String[] headerNames_0 = new String[queryJsonRes_0.size()*2+1];
        String[] dataNames_0 = new String[queryJsonRes_0.size()*2+1];
        headerNames_0[0] = "本日事件高发top5";
        dataNames_0[0]= "null";
        JSONObject makeJson_0 = new JSONObject();
        JSONArray data0 = new JSONArray();
        int size0;
        int num1 =0;
        for (size0=1;size0<queryJsonRes_0.size()*2;size0++){
            headerNames_0[size0] = queryJsonRes_0.getJSONObject(num1).getString("SUB_TYPE_NAME");
            dataNames_0[size0] = "val"+size0;
            makeJson_0.put("val"+size0,queryJsonRes_0.getJSONObject(num1).get("REPORT_NUM"));
            size0++;
            headerNames_0[size0] = "结案率";
            dataNames_0[size0] = "val"+size0;
            makeJson_0.put("val"+size0,queryJsonRes_0.getJSONObject(num1).get("JIEANLV"));
            num1++;
        }

        data0.add(makeJson_0);

        String queryUrl_week ="http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=16&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_week+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZXZlbnRfdHlwZV9pZCA9IDEgYW5kIGV2ZW50X3NyY19pZCBpbiAoJzcxJywnMScsJzM0Jyk%3D&_=1657161960815";
        HttpResponse queryRes_1 = HttpRequest.get(queryUrl_week)
                .cookie(cookies)
                .execute();
        JSONArray queryJsonRes_1 = JSON.parseObject(queryRes_1.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        String[] headerNames_1 = new String[queryJsonRes_1.size()*2+1];
        String[] dataNames_1 = new String[queryJsonRes_1.size()*2+1];
        headerNames_1[0] = "本周事件高发top5";
        dataNames_1[0]= "null";
        JSONObject makeJson_1 = new JSONObject();
        JSONArray data1 = new JSONArray();
        int size1;
        int num2 =0;
        for (size1=1;size1<queryJsonRes_1.size()*2;size1++){
            headerNames_1[size1] = queryJsonRes_1.getJSONObject(num2).getString("SUB_TYPE_NAME");
            dataNames_1[size1] = "val"+size1;
            makeJson_1.put("val"+size1,queryJsonRes_1.getJSONObject(num2).get("REPORT_NUM"));
            size1++;
            headerNames_1[size1] = "结案率";
            dataNames_1[size1] = "val"+size1;
            makeJson_1.put("val"+size1,queryJsonRes_1.getJSONObject(num2).get("JIEANLV"));
            num2++;
        }
        data1.add(makeJson_1);

        String queryUrl_month ="http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=16&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_month+"-01%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZXZlbnRfdHlwZV9pZCA9IDEgYW5kIGV2ZW50X3NyY19pZCBpbiAoJzcxJywnMScsJzM0Jyk%3D&_=1657161960815";
        HttpResponse queryRes_2 = HttpRequest.get(queryUrl_month)
                .cookie(cookies)
                .execute();
        JSONArray queryJsonRes_2 = JSON.parseObject(queryRes_2.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        String[] headerNames_2 = new String[queryJsonRes_2.size()*2+1];
        String[] dataNames_2 = new String[queryJsonRes_2.size()*2+1];
        headerNames_2[0] = "本月事件高发top5";
        dataNames_2[0]= "null";
        JSONObject makeJson_2 = new JSONObject();
        JSONArray data2 = new JSONArray();
        int size2;
        int num3 =0;
        for (size2=1;size2<queryJsonRes_1.size()*2;size2++){
            headerNames_2[size2] = queryJsonRes_1.getJSONObject(num3).getString("SUB_TYPE_NAME");
            dataNames_2[size2] = "val"+size2;
            makeJson_2.put("val"+size2,queryJsonRes_2.getJSONObject(num3).get("REPORT_NUM"));
            size2++;
            headerNames_2[size2] = "结案率";
            dataNames_2[size2] = "val"+size2;
            makeJson_2.put("val"+size2,queryJsonRes_2.getJSONObject(num3).get("JIEANLV"));
            num3++;
        }
        data2.add(makeJson_2);


        table.add(new Table(headerNames_0,dataNames_0,data0));
        table.add(new Table(headerNames_1,dataNames_1,data1));
        table.add(new Table(headerNames_2,dataNames_2,data2));
        System.out.println("Request:/szcg/getShiJianTop5");


        return CommonResult.success(table);


    }

    @RequestMapping("/getBjTop5")
    public CommonResult<List<Table>> getShiJianTop5(){
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate= new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week= formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_day ="http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=17&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522126%2522%2C%2522fieldTypeInfo%2522%3A%2522(district_id%2520in%2520(%257B0%257D)%2520or%2520street_id%2520in%2520(%257B1%257D)%2520or%2520community_id%2520in%2520(%257B2%257D))%2522%2C%2522values%2522%3A%255B%25220%3A3%3B1%3A100%2C101%2C102%2C103%2C104%2C105%2C106%2C107%2C108%2C109%2C110%2C111%2C112%3B2%3A10090%2C10091%2C10092%2C10093%2C10103%2C10104%2C10149%2C10587%2C10589%2C10621%2C10003%2C10004%2C10082%2C10083%2C10084%2C10085%2C10086%2C10087%2C10088%2C10089%2C10027%2C10031%2C10052%2C10053%2C10054%2C10070%2C10071%2C10072%2C10094%2C10095%2C10096%2C10097%2C10098%2C10099%2C10100%2C10101%2C10102%2C10568%2C10577%2C10620%2C10015%2C10016%2C10017%2C10047%2C10008%2C10009%2C10010%2C10040%2C10041%2C10042%2C10062%2C10077%2C10078%2C10563%2C10571%2C10055%2C10056%2C10057%2C10058%2C10073%2C10074%2C10075%2C10076%2C10000%2C10001%2C10002%2C10005%2C10006%2C10007%2C10033%2C10034%2C10035%2C10036%2C10037%2C10038%2C10039%2C10059%2C10060%2C10061%2C10080%2C10081%2C10023%2C10024%2C10025%2C10026%2C10079%2C10578%2C10018%2C10019%2C10020%2C10021%2C10022%2C10028%2C10029%2C10030%2C10032%2C10011%2C10012%2C10013%2C10014%2C10043%2C10044%2C10045%2C10046%2C10063%2C10064%2C10065%2C10066%2C10588%2C10048%2C10049%2C10050%2C10067%2C10068%2C10069%2C10590%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%25221%24%25E5%258C%25BA%25E5%259F%259F%25E6%259D%25A1%25E4%25BB%25B6%2522%257D%255D%257D&hidenCond=ZXZlbnRfdHlwZV9pZCA9IDIgYW5kIGV2ZW50X3NyY19pZCBpbiAoJzcxJywnMScsJzM0Jyk%3D&_=1657177033474";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_day)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        String[] headerNames_0 = new String[queryJsonRes_0.size()*2+1];
        String[] dataNames_0 = new String[queryJsonRes_0.size()*2+1];
        headerNames_0[0] = "本日部件高发top5";
        dataNames_0[0]= "null";
        JSONObject makeJson_0 = new JSONObject();
        JSONArray data0 = new JSONArray();
        int size0;
        int num1 =0;
        for (size0=1;size0<queryJsonRes_0.size()*2;size0++){
            headerNames_0[size0] = queryJsonRes_0.getJSONObject(num1).getString("SUB_TYPE_NAME");
            dataNames_0[size0] = "val"+size0;
            makeJson_0.put("val"+size0,queryJsonRes_0.getJSONObject(num1).get("REPORT_NUM"));
            size0++;
            headerNames_0[size0] = "结案率";
            dataNames_0[size0] = "val"+size0;
            makeJson_0.put("val"+size0,queryJsonRes_0.getJSONObject(num1).get("JIEANLV"));
            num1++;
        }

        data0.add(makeJson_0);

        String queryUrl_week ="http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=17&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_week+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522126%2522%2C%2522fieldTypeInfo%2522%3A%2522(district_id%2520in%2520(%257B0%257D)%2520or%2520street_id%2520in%2520(%257B1%257D)%2520or%2520community_id%2520in%2520(%257B2%257D))%2522%2C%2522values%2522%3A%255B%25220%3A3%3B1%3A100%2C101%2C102%2C103%2C104%2C105%2C106%2C107%2C108%2C109%2C110%2C111%2C112%3B2%3A10090%2C10091%2C10092%2C10093%2C10103%2C10104%2C10149%2C10587%2C10589%2C10621%2C10003%2C10004%2C10082%2C10083%2C10084%2C10085%2C10086%2C10087%2C10088%2C10089%2C10027%2C10031%2C10052%2C10053%2C10054%2C10070%2C10071%2C10072%2C10094%2C10095%2C10096%2C10097%2C10098%2C10099%2C10100%2C10101%2C10102%2C10568%2C10577%2C10620%2C10015%2C10016%2C10017%2C10047%2C10008%2C10009%2C10010%2C10040%2C10041%2C10042%2C10062%2C10077%2C10078%2C10563%2C10571%2C10055%2C10056%2C10057%2C10058%2C10073%2C10074%2C10075%2C10076%2C10000%2C10001%2C10002%2C10005%2C10006%2C10007%2C10033%2C10034%2C10035%2C10036%2C10037%2C10038%2C10039%2C10059%2C10060%2C10061%2C10080%2C10081%2C10023%2C10024%2C10025%2C10026%2C10079%2C10578%2C10018%2C10019%2C10020%2C10021%2C10022%2C10028%2C10029%2C10030%2C10032%2C10011%2C10012%2C10013%2C10014%2C10043%2C10044%2C10045%2C10046%2C10063%2C10064%2C10065%2C10066%2C10588%2C10048%2C10049%2C10050%2C10067%2C10068%2C10069%2C10590%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%25221%24%25E5%258C%25BA%25E5%259F%259F%25E6%259D%25A1%25E4%25BB%25B6%2522%257D%255D%257D&hidenCond=ZXZlbnRfdHlwZV9pZCA9IDIgYW5kIGV2ZW50X3NyY19pZCBpbiAoJzcxJywnMScsJzM0Jyk%3D&_=1657177033474";
        HttpResponse queryRes_1 = HttpRequest.get(queryUrl_week)
                .cookie(cookies)
                .execute();
        JSONArray queryJsonRes_1 = JSON.parseObject(queryRes_1.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        String[] headerNames_1 = new String[queryJsonRes_1.size()*2+1];
        String[] dataNames_1 = new String[queryJsonRes_1.size()*2+1];
        headerNames_1[0] = "本周部件高发top5";
        dataNames_1[0]= "null";
        JSONObject makeJson_1 = new JSONObject();
        JSONArray data1 = new JSONArray();
        int size1;
        int num2 =0;
        for (size1=1;size1<queryJsonRes_1.size()*2;size1++){
            headerNames_1[size1] = queryJsonRes_1.getJSONObject(num2).getString("SUB_TYPE_NAME");
            dataNames_1[size1] = "val"+size1;
            makeJson_1.put("val"+size1,queryJsonRes_1.getJSONObject(num2).get("REPORT_NUM"));
            size1++;
            headerNames_1[size1] = "结案率";
            dataNames_1[size1] = "val"+size1;
            makeJson_1.put("val"+size1,queryJsonRes_1.getJSONObject(num2).get("JIEANLV"));
            num2++;
        }
        data1.add(makeJson_1);

        String queryUrl_month ="http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=17&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_month+"-01%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522126%2522%2C%2522fieldTypeInfo%2522%3A%2522(district_id%2520in%2520(%257B0%257D)%2520or%2520street_id%2520in%2520(%257B1%257D)%2520or%2520community_id%2520in%2520(%257B2%257D))%2522%2C%2522values%2522%3A%255B%25220%3A3%3B1%3A100%2C101%2C102%2C103%2C104%2C105%2C106%2C107%2C108%2C109%2C110%2C111%2C112%3B2%3A10090%2C10091%2C10092%2C10093%2C10103%2C10104%2C10149%2C10587%2C10589%2C10621%2C10003%2C10004%2C10082%2C10083%2C10084%2C10085%2C10086%2C10087%2C10088%2C10089%2C10027%2C10031%2C10052%2C10053%2C10054%2C10070%2C10071%2C10072%2C10094%2C10095%2C10096%2C10097%2C10098%2C10099%2C10100%2C10101%2C10102%2C10568%2C10577%2C10620%2C10015%2C10016%2C10017%2C10047%2C10008%2C10009%2C10010%2C10040%2C10041%2C10042%2C10062%2C10077%2C10078%2C10563%2C10571%2C10055%2C10056%2C10057%2C10058%2C10073%2C10074%2C10075%2C10076%2C10000%2C10001%2C10002%2C10005%2C10006%2C10007%2C10033%2C10034%2C10035%2C10036%2C10037%2C10038%2C10039%2C10059%2C10060%2C10061%2C10080%2C10081%2C10023%2C10024%2C10025%2C10026%2C10079%2C10578%2C10018%2C10019%2C10020%2C10021%2C10022%2C10028%2C10029%2C10030%2C10032%2C10011%2C10012%2C10013%2C10014%2C10043%2C10044%2C10045%2C10046%2C10063%2C10064%2C10065%2C10066%2C10588%2C10048%2C10049%2C10050%2C10067%2C10068%2C10069%2C10590%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%25221%24%25E5%258C%25BA%25E5%259F%259F%25E6%259D%25A1%25E4%25BB%25B6%2522%257D%255D%257D&hidenCond=ZXZlbnRfdHlwZV9pZCA9IDIgYW5kIGV2ZW50X3NyY19pZCBpbiAoJzcxJywnMScsJzM0Jyk%3D&_=1657177033474";
        HttpResponse queryRes_2 = HttpRequest.get(queryUrl_month)
                .cookie(cookies)
                .execute();
        JSONArray queryJsonRes_2 = JSON.parseObject(queryRes_2.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        String[] headerNames_2 = new String[queryJsonRes_2.size()*2+1];
        String[] dataNames_2 = new String[queryJsonRes_2.size()*2+1];
        headerNames_2[0] = "本月部件高发top5";
        dataNames_2[0]= "null";
        JSONObject makeJson_2 = new JSONObject();
        JSONArray data2 = new JSONArray();
        int size2;
        int num3 =0;
        for (size2=1;size2<queryJsonRes_1.size()*2;size2++){
            headerNames_2[size2] = queryJsonRes_1.getJSONObject(num3).getString("SUB_TYPE_NAME");
            dataNames_2[size2] = "val"+size2;
            makeJson_2.put("val"+size2,queryJsonRes_2.getJSONObject(num3).get("REPORT_NUM"));
            size2++;
            headerNames_2[size2] = "结案率";
            dataNames_2[size2] = "val"+size2;
            makeJson_2.put("val"+size2,queryJsonRes_2.getJSONObject(num3).get("JIEANLV"));
            num3++;
        }
        data2.add(makeJson_2);


        table.add(new Table(headerNames_0,dataNames_0,data0));
        table.add(new Table(headerNames_1,dataNames_1,data1));
        table.add(new Table(headerNames_2,dataNames_2,data2));
        System.out.println("Request:/szcg/getShiJianTop5"+date);


        return CommonResult.success(table);


    }


    @RequestMapping("/getQypjJd")
    public CommonResult<List<Table>> getQypjJd(){
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate= new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week= formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_day ="http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=253&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25223%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3Nicp&_=1657245092933";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_day)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        String[] headerNames_0 = new String[queryJsonRes_0.size()*2];
        String[] dataNames_0 = new String[queryJsonRes_0.size()*2];

        JSONObject makeJson_0 = new JSONObject();
        JSONArray data0 = new JSONArray();
        int size0;
        int num1 =0;
        for (size0=0;size0<queryJsonRes_0.size()*2-2;size0++){
            headerNames_0[size0] = queryJsonRes_0.getJSONObject(num1).getString("STREET_NAME");
            dataNames_0[size0] = "val"+size0;
            makeJson_0.put("val"+size0,"本日立案数:"+queryJsonRes_0.getJSONObject(num1).get("ZQ_INSTNUM"));
            size0++;
            headerNames_0[size0] = "有效问题日排名";
            dataNames_0[size0] = "val"+size0;
            makeJson_0.put("val"+size0,queryJsonRes_0.getJSONObject(num1).get("RANK"));
            num1++;
        }

        data0.add(makeJson_0);

/*
        String queryUrl_week ="http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=253&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25223%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_week+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3Nicp&_=1657245092933";
        HttpResponse queryRes_1 = HttpRequest.get(queryUrl_week)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_1 = JSON.parseObject(queryRes_1.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        String[] headerNames_1 = new String[queryJsonRes_1.size()*2];
        String[] dataNames_1 = new String[queryJsonRes_1.size()*2];

        JSONObject makeJson_1 = new JSONObject();
        JSONArray data1 = new JSONArray();
        int size1;
        int num2 =0;
        for (size1=0;size1<queryJsonRes_1.size()*2-2;size1++){
            headerNames_1[size1] = queryJsonRes_1.getJSONObject(num2).getString("STREET_NAME");
            dataNames_1[size1] = "val"+size1;
            makeJson_1.put("val"+size1,"本周立案数:"+queryJsonRes_1.getJSONObject(num2).get("ZQ_INSTNUM"));
            size1++;
            headerNames_1[size1] = "有效问题周排名";
            headerNames_1[size1] = "有效问题周排名";
            dataNames_1[size1] = "val"+size1;
            makeJson_1.put("val"+size1,queryJsonRes_1.getJSONObject(num2).get("RANK"));
            num1++;
        }

        data1.add(makeJson_1);

        String queryUrl_month ="http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=253&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25223%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_month+"-01%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3Nicp&_=1657245092933";
        HttpResponse queryRes_2 = HttpRequest.get(queryUrl_month)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_2 = JSON.parseObject(queryRes_2.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        String[] headerNames_2 = new String[queryJsonRes_2.size()*2+1];
        String[] dataNames_2 = new String[queryJsonRes_2.size()*2+1];

        JSONObject makeJson_2 = new JSONObject();
        JSONArray data2 = new JSONArray();
        int size2;
        int num3 =0;
        for (size2=0;size2<queryJsonRes_2.size()*2-2;size2++){
            headerNames_2[size2] = queryJsonRes_2.getJSONObject(num3).getString("STREET_NAME");
            dataNames_2[size2] = "val"+size2;
            makeJson_2.put("val"+size2,"本月立案数:"+queryJsonRes_2.getJSONObject(num3).get("ZQ_INSTNUM"));
            size2++;
            headerNames_2[size2] = "有效问题周排名";
            headerNames_2[size2] = "有效问题周排名";
            dataNames_2[size2] = "val"+size2;
            makeJson_2.put("val"+size2,queryJsonRes_2.getJSONObject(num3).get("RANK"));
            num3++;
        }
        data2.add(makeJson_2);
        table.add(new Table(headerNames_1,dataNames_1,data1));
        table.add(new Table(headerNames_2,dataNames_2,data2));
*/

        table.add(new Table(headerNames_0,dataNames_0,data0));
        System.out.println("Request:szcg/getQypiJd"+date);
        return CommonResult.success(table);
    }

    @RequestMapping("/getQypjCq")
    public CommonResult<List<Table>> getQypjCq() {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=11&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25223%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25225%2522%2C%2522fieldName%2522%3A%2522dispose_unit_level_id%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24tc_dispose_unit_level%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%252290002%2522%255D%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522112%24%25E5%25A4%2584%25E7%25BD%25AE%25E9%2583%25A8%25E9%2597%25A8%25E7%25BA%25A7%25E5%2588%25AB%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3Nicp&_=1657245093055";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONObject("chartMap").getJSONArray("datas");
        System.out.println(queryJsonRes_0);
        String[] headerNames_0 = new String[]{"区级部门立案数","区级部门问题总数","区级部门处置数","区级部门结案数"};
        String[] dataNames_0 = new String[]{"val0","val1","val2","val3"};
        JSONObject makeJson_0 = new JSONObject();
        JSONArray data0 = new JSONArray();
        for (int i =0;i<queryJsonRes_0.size();i++)
        {
            makeJson_0.put("val"+i,queryJsonRes_0.get(i));
        }
        data0.add(makeJson_0);
        String queryUrl_city = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=11&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25223%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25225%2522%2C%2522fieldName%2522%3A%2522dispose_unit_level_id%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24tc_dispose_unit_level%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%252290001%2522%255D%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522112%24%25E5%25A4%2584%25E7%25BD%25AE%25E9%2583%25A8%25E9%2597%25A8%25E7%25BA%25A7%25E5%2588%25AB%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3Nicp&_=1657245093056";
        HttpResponse queryRes_1 = HttpRequest.get(queryUrl_city)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_1 = JSON.parseObject(queryRes_1.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONObject("chartMap").getJSONArray("datas");
        String[] headerNames_1 = new String[]{"市级部门立案数","市级部门问题总数","市级部门处置数","市级部门结案数"};
        String[] dataNames_1 = new String[]{"val0","val1","val2","val3"};
        JSONObject makeJson_1 = new JSONObject();
        JSONArray data1 = new JSONArray();
        for (int i =0;i<queryJsonRes_0.size();i++)
        {
            makeJson_1.put("val"+i,queryJsonRes_1.get(i));
        }
        data1.add(makeJson_1);


        table.add(new Table(headerNames_0,dataNames_0,data0));
        table.add(new Table(headerNames_1,dataNames_1,data1));
        System.out.println("Request:szcg/getQypiCq"+date);
        return CommonResult.success(table);
    }

    @RequestMapping("/getQypjCg")
    public CommonResult<List<Table>> getQypjCg() {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=260&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25223%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25225%2522%2C%2522fieldName%2522%3A%2522dispose_unit_level_id%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24tc_dispose_unit_level%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%252290002%2522%255D%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522112%24%25E5%25A4%2584%25E7%25BD%25AE%25E9%2583%25A8%25E9%2597%25A8%25E7%25BA%25A7%25E5%2588%25AB%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3MicsJzc2JykgYW5kIHRoaXJkX3R5cGVfaWQgaW4gKCc0MzAnLCc0MzEnLCc0MzInLCc0MzMnLCc0MzQnLCc0MzUnLCc0MzYnLCc0MzcnLCc0MzgnLCc0MzknLCc0NDAnLCc0NDEnLCc0NDInLCc0NDMnLCc0NDQnLCc0NDUnLCc0NDYnLCc0NDcnLCc0NDgnLCc0NDknLCc0NTAnLCc0NTEnLCc0NTInLCc0NTMnLCc0NTQnLCc0NjAnLCc0NjknLCc0NzAnLCc0NzEnLCc0NzInLCc0NzMnLCc0NzQnLCc0NzcnLCc0NzgnLCc0NzknLCc0ODAnLCc0ODEnLCc0ODMnLCc0ODQnLCc0ODUnLCc0ODYnLCc0ODcnLCc0ODgnLCc0ODknLCc0OTAnLCc0OTEnLCc0OTInLCc0OTQnLCc0OTUnLCc0OTYnLCc0OTcnLCc0OTgnLCc0OTknLCc1MDAnLCc1MDEnLCc1MDInLCc1MDMnLCc1MDQnLCc1MDUnLCc1MDYnLCc1MDcnLCc1MDgnLCc1MDknLCc1MTAnLCc1MTEnLCc1MTInLCc1MTMnLCc1MTQnLCc1MTUnLCc1MTcnLCc1MTgnLCc1MTknLCc1MjInLCc1MjMnLCc1MjUnLCc1MjYnLCc1MzAnLCc1MzEnLCc1MzInLCc2NjInLCc2OTAnLCc2OTEnLCc3MDQnLCc3MTcnLCc3MjEnLCc3MzQnLCc3MzgnLCc3NDAnLCc3NDUnLCc3NDYnLCc3NDcnLCc3NDgnLCc3NDknLCc3NTAnLCc3NTEnLCc3NTInLCc3NTMnLCc3NTQnLCc3NjInLCc3MjAnLCc3MTknLCc2ODknLCc2NTEnLCc4MDAnKQ%3D%3D&_=1657538727102";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data0 = new JSONArray();
        String[] headerNames0 = new String[]{"区级单位","区级部门立案数", "区级部门处置数", "区级部门结案数","区级部门结案率","常规得分","综合指标值"};
        String[] dataNames0 = new String[]{"val0", "val1", "val2", "val3","val4","val5","val6"};
        for (int i = queryJsonRes_0.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 6;  j++) {
            makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("DISPOSE_UNIT_NAME"));
            makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("INST_NUM"));
            makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("NEED_DISPOSE_NUM"));
            makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("INTIME_DISPOSE_NUM"));
            makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("DISPOSE_LV"));
            makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("REGULARSCORE"));
            makeJson.put("val6", queryJsonRes_0.getJSONObject(i).get("ZHZBZ"));
            }
            data0.add(makeJson);


        }
        table.add(new Table(headerNames0,dataNames0,data0));




        String queryUrl_city = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=260&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25223%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25225%2522%2C%2522fieldName%2522%3A%2522dispose_unit_level_id%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24tc_dispose_unit_level%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%252290001%2522%255D%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522112%24%25E5%25A4%2584%25E7%25BD%25AE%25E9%2583%25A8%25E9%2597%25A8%25E7%25BA%25A7%25E5%2588%25AB%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3MicsJzc2JykgYW5kIHRoaXJkX3R5cGVfaWQgaW4gKCc0MzAnLCc0MzEnLCc0MzInLCc0MzMnLCc0MzQnLCc0MzUnLCc0MzYnLCc0MzcnLCc0MzgnLCc0MzknLCc0NDAnLCc0NDEnLCc0NDInLCc0NDMnLCc0NDQnLCc0NDUnLCc0NDYnLCc0NDcnLCc0NDgnLCc0NDknLCc0NTAnLCc0NTEnLCc0NTInLCc0NTMnLCc0NTQnLCc0NjAnLCc0NjknLCc0NzAnLCc0NzEnLCc0NzInLCc0NzMnLCc0NzQnLCc0NzcnLCc0NzgnLCc0NzknLCc0ODAnLCc0ODEnLCc0ODMnLCc0ODQnLCc0ODUnLCc0ODYnLCc0ODcnLCc0ODgnLCc0ODknLCc0OTAnLCc0OTEnLCc0OTInLCc0OTQnLCc0OTUnLCc0OTYnLCc0OTcnLCc0OTgnLCc0OTknLCc1MDAnLCc1MDEnLCc1MDInLCc1MDMnLCc1MDQnLCc1MDUnLCc1MDYnLCc1MDcnLCc1MDgnLCc1MDknLCc1MTAnLCc1MTEnLCc1MTInLCc1MTMnLCc1MTQnLCc1MTUnLCc1MTcnLCc1MTgnLCc1MTknLCc1MjInLCc1MjMnLCc1MjUnLCc1MjYnLCc1MzAnLCc1MzEnLCc1MzInLCc2NjInLCc2OTAnLCc2OTEnLCc3MDQnLCc3MTcnLCc3MjEnLCc3MzQnLCc3MzgnLCc3NDAnLCc3NDUnLCc3NDYnLCc3NDcnLCc3NDgnLCc3NDknLCc3NTAnLCc3NTEnLCc3NTInLCc3NTMnLCc3NTQnLCc3NjInLCc3MjAnLCc3MTknLCc2ODknLCc2NTEnLCc4MDAnKQ%3D%3D&_=1657538727101";
        HttpResponse queryRes_1 = HttpRequest.get(queryUrl_city)
                .cookie(cookies)
                .execute();
        JSONArray queryJsonRes_1 = JSON.parseObject(queryRes_1.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data1 = new JSONArray();
        String[] headerNames1 = new String[]{"市级单位","市级部门立案数", "市级部门处置数", "市级部门结案数","市级部门结案率","常规得分","综合指标值"};
        String[] dataNames1 = new String[]{"val0", "val1", "val2", "val3","val4","val5","val6"};
        for (int i = queryJsonRes_1.size()-1; i >= 0 ; i--) {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 6;  j++) {
                makeJson.put("val0", queryJsonRes_1.getJSONObject(i).get("DISPOSE_UNIT_NAME"));
                makeJson.put("val1", queryJsonRes_1.getJSONObject(i).get("INST_NUM"));
                makeJson.put("val2", queryJsonRes_1.getJSONObject(i).get("NEED_DISPOSE_NUM"));
                makeJson.put("val3", queryJsonRes_1.getJSONObject(i).get("INTIME_DISPOSE_NUM"));
                makeJson.put("val4", queryJsonRes_1.getJSONObject(i).get("DISPOSE_LV"));
                makeJson.put("val5", queryJsonRes_1.getJSONObject(i).get("REGULARSCORE"));
                makeJson.put("val6", queryJsonRes_1.getJSONObject(i).get("ZHZBZ"));
            }
            data1.add(makeJson);


        }
        table.add(new Table(headerNames1,dataNames1,data1));


        System.out.println("Request:szcg/getQypjCg" + date);
        return CommonResult.success(table);
    }
    @RequestMapping("/getQypj")
    public CommonResult<List<Table>> getQypj() {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=15&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25223%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25225%2522%2C%2522fieldName%2522%3A%2522dispose_unit_level_id%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24tc_dispose_unit_level%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%252290002%2522%255D%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522112%24%25E5%25A4%2584%25E7%25BD%25AE%25E9%2583%25A8%25E9%2597%25A8%25E7%25BA%25A7%25E5%2588%25AB%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3Nicp&_=1657540538932";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data0 = new JSONArray();
        String[] headerNames0 = new String[]{"区级单位","区级部门立案数", "区级部门处置数", "区级部门结案数","区级部门结案率","常规得分","综合指标值"};
        String[] dataNames0 = new String[]{"val0", "val1", "val2", "val3","val4","val5","val6"};
        for (int i = queryJsonRes_0.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 6;  j++) {
                makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("DISPOSE_UNIT_NAME"));
                makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("INST_NUM"));
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("NEED_DISPOSE_NUM"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("INTIME_DISPOSE_NUM"));
                makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("DISPOSE_LV"));
                makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("REGULARSCORE"));
                makeJson.put("val6", queryJsonRes_0.getJSONObject(i).get("ZHZBZ"));
            }
            data0.add(makeJson);


        }
        table.add(new Table(headerNames0,dataNames0,data0));
        String queryUrl_city = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=15&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25223%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25225%2522%2C%2522fieldName%2522%3A%2522dispose_unit_level_id%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24tc_dispose_unit_level%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%252290001%2522%255D%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522112%24%25E5%25A4%2584%25E7%25BD%25AE%25E9%2583%25A8%25E9%2597%25A8%25E7%25BA%25A7%25E5%2588%25AB%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3Nicp&_=1657540538933";
        HttpResponse queryRes_1 = HttpRequest.get(queryUrl_city)
                .cookie(cookies)
                .execute();
        JSONArray queryJsonRes_1 = JSON.parseObject(queryRes_1.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data1 = new JSONArray();
        String[] headerNames1 = new String[]{"市级单位","市级部门立案数", "市级部门处置数", "市级部门结案数","市级部门结案率","常规得分","综合指标值"};
        String[] dataNames1 = new String[]{"val0", "val1", "val2", "val3","val4","val5","val6"};
        for (int i = queryJsonRes_1.size()-1; i >= 0 ; i--) {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 6;  j++) {
                makeJson.put("val0", queryJsonRes_1.getJSONObject(i).get("DISPOSE_UNIT_NAME"));
                makeJson.put("val1", queryJsonRes_1.getJSONObject(i).get("INST_NUM"));
                makeJson.put("val2", queryJsonRes_1.getJSONObject(i).get("NEED_DISPOSE_NUM"));
                makeJson.put("val3", queryJsonRes_1.getJSONObject(i).get("INTIME_DISPOSE_NUM"));
                makeJson.put("val4", queryJsonRes_1.getJSONObject(i).get("DISPOSE_LV"));
                makeJson.put("val5", queryJsonRes_1.getJSONObject(i).get("REGULARSCORE"));
                makeJson.put("val6", queryJsonRes_1.getJSONObject(i).get("ZHZBZ"));
            }
            data1.add(makeJson);


        }
        table.add(new Table(headerNames1,dataNames1,data1));
        System.out.println("Request:szcg/getQypj" + date);
        return CommonResult.success(table);
    }


    @RequestMapping("/getWtlbBj")
    public CommonResult<List<Table>> getWtlbBj() {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=208&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25221%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3NicpIGFuZCBldmVudF90eXBlX2lkID0gMg%3D%3D&_=1657540538959";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{"部件","问题总数","立案数", "处置率","结案率","排名"};
        String[] dataNames = new String[]{"val0", "val1", "val2", "val3","val4","val5"};
        for (int i = queryJsonRes_0.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 6;  j++) {
                if (i==queryJsonRes_0.size()-1) {
                    makeJson.put("val0", "总计");
                    headerNames[5] = "在本市排名";
                }
                else {
                    makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("MAIN_TYPE_NAME"));}
                    makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("REPORT_NUM"));
                    makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("NEED_DISPOSE_NUM"));
                    makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("DISPOSE_RATE"));
                    makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("JAL"));
                    makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("RANK"));

            }
            data.add(makeJson);

        }
        table.add(new Table(headerNames,dataNames,data));


        System.out.println("Request:szcg/getWtlbBj" + date);
        return CommonResult.success(table);
    }
    @RequestMapping("/getWtlbSj")
    public CommonResult<List<Table>> getWtlbSj() {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=35&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25221%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25223%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3NicpIGFuZCBldmVudF90eXBlX2lkID0gMQ%3D%3D&_=1657540538995";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{"事件","问题总数","立案数", "处置率","结案率","排名"};
        String[] dataNames = new String[]{"val0", "val1", "val2", "val3","val4","val5"};
        for (int i = queryJsonRes_0.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 6;  j++) {
                if (i==queryJsonRes_0.size()-1) {
                    makeJson.put("val0", "总计");
                    headerNames[5] = "在本市排名";
                }
                else {
                    makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("MAIN_TYPE_NAME"));}
                makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("REPORT_NUM"));
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("NEED_DISPOSE_NUM"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("DISPOSE_RATE"));
                makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("JAL"));
                makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("RANK"));

            }
            data.add(makeJson);


        }
        table.add(new Table(headerNames,dataNames,data));

        System.out.println("Request:szcg/getWtlbSj" + date);
        return CommonResult.success(table);
    }

    @RequestMapping("/getWtlbBj_xl")
    public CommonResult<List<Table>> getWtlbBj_xl() {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=209&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25221%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3NicpIGFuZCBldmVudF90eXBlX2lkID0gMg%3D%3D&_=1657596766015";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{"部件大类","小类名称","问题总数","立案数", "处置率","结案率","排名"};
        String[] dataNames = new String[]{"val0","val1" ,"val2", "val3", "val4","val5","val6"};
        for (int i = queryJsonRes_0.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 6;  j++) {
                if (i==queryJsonRes_0.size()-1) {
                    makeJson.put("val0", "总计");
                    headerNames[5] = "在本市排名";
                }
                else {
                    makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("MAIN_TYPE_NAME"));}
                makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("SUB_TYPE_NAME"));
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("REPORT_NUM"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("NEED_DISPOSE_NUM"));
                makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("DISPOSE_RATE"));
                makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("JAL"));
                makeJson.put("val6", queryJsonRes_0.getJSONObject(i).get("RANK"));

            }
            data.add(makeJson);


        }
        table.add(new Table(headerNames,dataNames,data));

        System.out.println("Request:szcg/getWtlbBj_xl" + date+time);
        return CommonResult.success(table);
    }

    @RequestMapping("/getWtlbSj_xl")
    public CommonResult<List<Table>> getWtlbSj_xl() {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=36&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25221%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3NicpIGFuZCBldmVudF90eXBlX2lkID0gMQ%3D%3D&_=1657596767375";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{"部件大类","小类名称","问题总数","立案数", "处置率","结案率","排名"};
        String[] dataNames = new String[]{"val0","val1" ,"val2", "val3", "val4","val5","val6"};
        for (int i = queryJsonRes_0.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 6;  j++) {

                makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("MAIN_TYPE_NAME"));
                makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("SUB_TYPE_NAME"));
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("REPORT_NUM"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("NEED_DISPOSE_NUM"));
                makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("DISPOSE_RATE"));
                makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("JAL"));
                makeJson.put("val6", queryJsonRes_0.getJSONObject(i).get("RANK"));

            }
            data.add(makeJson);


        }
        table.add(new Table(headerNames,dataNames,data));

        System.out.println("Request:szcg/getWtlbSj_xl" + date+time);
        return CommonResult.success(table);
    }

    @RequestMapping("/getSzcg_cgl")
    public CommonResult<List<Table>> getSzcg_cgl() {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=118&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25221%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%255D%257D&hidenCond=ZGlzcG9zZV91bml0X2xldmVsX25hbWUgPSAn5Yy657qnJyBhbmQgZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3Nicp&_=1657596769344";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{"区域","立案数","处置数", "处置率","结案率","得分情况"};
        String[] dataNames = new String[]{"val0","val1" ,"val2", "val3", "val4","val5"};
        for (int i = queryJsonRes_0.size()-1; i >= 1; i--) {
            JSONObject makeJson = new JSONObject();


                makeJson.put("val0", "金牛区");
                makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("立案数"));
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("处置数"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("处置率"));
                makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("结案率"));
                makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("考评得分"));



            data.add(makeJson);


        }
        table.add(new Table(headerNames,dataNames,data));

        System.out.println("Request:szcg/getSzcg_cgl" + date);
        return CommonResult.success(table);
    }
    @RequestMapping("/getSzcg_cgl_district")
    public CommonResult<List<Table>> getSzcg_cgl_district() {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=273&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25221%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZXZlbnRfbGV2ZWxfbmFtZSA9ICfljLrnuqcnIGFuZCBkaXNwb3NlX3VuaXRfbGV2ZWxfbmFtZSA9ICfljLrnuqcnIGFuZCBldmVudF9zcmNfaWQgaW4gKCc3MScsJzEnLCczNCcsJzc2JykgYW5kIHRoaXJkX3R5cGVfaWQgaW4gKCc2NTEnLCc2NjInLCc2ODknLCc2OTAnLCc2OTEnLCc3MDQnLCc3MTcnLCc3MTknLCc3ODcnLCc3MjAnLCc3MjEnLCc3MzQnLCc3MzgnLCc3NDAnLCc3NDUnLCc3NDYnLCc3NDcnLCc3NDgnLCc3NDknLCc3NTAnLCc3NTEnLCc3NTInLCc3NTMnLCc3NTQnLCc3NjInLCc0MzAnLCc0MzEnLCc0MzInLCc0MzMnLCc0MzQnLCc0MzUnLCc0MzYnLCc0MzcnLCc0MzgnLCc0MzknLCc0NDAnLCc0NDEnLCc0NDInLCc0NDMnLCc0NDQnLCc0NDUnLCc0NDYnLCc0NDcnLCc0NDgnLCc0NDknLCc0NTAnLCc0NTEnLCc0NTInLCc0NTMnLCc0NTQnLCc0NjAnLCc0NjknLCc0NzAnLCc0NzEnLCc0NzInLCc0NzMnLCc0NzQnLCc0NzcnLCc0NzgnLCc0NzknLCc0ODAnLCc4MDAnLCc0ODEnLCc0ODMnLCc0ODQnLCc0ODUnLCc0ODYnLCc0ODcnLCc0ODgnLCc0ODknLCc0OTAnLCc0OTEnLCc0OTInLCc0OTQnLCc0OTUnLCc0OTYnLCc0OTcnLCc0OTgnLCc0OTknLCc1MDAnLCc1MDEnLCc1MDInLCc1MDMnLCc1MDQnLCc1MDUnLCc1MDYnLCc1MDcnLCc1MDgnLCc1MDknLCc1MTAnLCc1MTEnLCc1MTInLCc1MTMnLCc1MTQnLCc1MTUnLCc1MTcnLCc1MTgnLCc1MTknLCc1MjInLCc1MjMnLCc1MjUnLCc1MjYnLCc1MzAnLCc1MzEnLCc1MzInKQ%3D%3D&_=1657596769789";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{"问题类型","大类名称","小类名称","问题总数","处置数", "处置率","考核得分"};
        String[] dataNames = new String[]{"val0","val1" ,"val2", "val3", "val4","val5","val6"};
        for (int i = queryJsonRes_0.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 6;  j++) {

                makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("EVENT_TYPE_NAME"));
                makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("SUB_TYPE_NAME"));
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("THIRD_TYPE_NAME"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("NEED_DISPOSE_NUM"));
                makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("INTIME_DISPOSE_NUM"));
                makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("DISPOSE_RATE"));
                makeJson.put("val6", queryJsonRes_0.getJSONObject(i).get("CHECK_GRADE"));

            }
            data.add(makeJson);

        }
        table.add(new Table(headerNames,dataNames,data));

        System.out.println("Request:szcg/getSzcg_cgl_district" + date);
        return CommonResult.success(table);
    }
    @RequestMapping("/getSzcg_cgl_city")
    public CommonResult<List<Table>> getSzcg_cgl_city() {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=274&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25221%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%2C%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZGlzcG9zZV91bml0X2xldmVsX25hbWUgPSAn5Yy657qnJyBhbmQgZXZlbnRfc3JjX2lkIGluICgnNzEnLCcxJywnMzQnLCc3NicpIGFuZCB0aGlyZF90eXBlX2lkIGluICgnNjUxJywnNjYyJywnNjg5JywnNjkwJywnNjkxJywnNzA0JywnNzE3JywnNzE5JywnNzg3JywnNzIwJywnNzIxJywnNzM0JywnNzM4JywnNzQwJywnNzQ1JywnNzQ2JywnNzQ3JywnNzQ4JywnNzQ5JywnNzUwJywnNzUxJywnNzUyJywnNzUzJywnNzU0JywnNzYyJywnNDMwJywnNDMxJywnNDMyJywnNDMzJywnNDM0JywnNDM1JywnNDM2JywnNDM3JywnNDM4JywnNDM5JywnNDQwJywnNDQxJywnNDQyJywnNDQzJywnNDQ0JywnNDQ1JywnNDQ2JywnNDQ3JywnNDQ4JywnNDQ5JywnNDUwJywnNDUxJywnNDUyJywnNDUzJywnNDU0JywnNDYwJywnNDY5JywnNDcwJywnNDcxJywnNDcyJywnNDczJywnNDc0JywnNDc3JywnNDc4JywnNDc5JywnNDgwJywnODAwJywnNDgxJywnNDgzJywnNDg0JywnNDg1JywnNDg2JywnNDg3JywnNDg4JywnNDg5JywnNDkwJywnNDkxJywnNDkyJywnNDk0JywnNDk1JywnNDk2JywnNDk3JywnNDk4JywnNDk5JywnNTAwJywnNTAxJywnNTAyJywnNTAzJywnNTA0JywnNTA1JywnNTA2JywnNTA3JywnNTA4JywnNTA5JywnNTEwJywnNTExJywnNTEyJywnNTEzJywnNTE0JywnNTE1JywnNTE3JywnNTE4JywnNTE5JywnNTIyJywnNTIzJywnNTI1JywnNTI2JywnNTMwJywnNTMxJywnNTMyJyk%3D&_=1657596769831";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{"问题类型","大类名称","小类名称","问题总数","处置数", "处置率","考核得分"};
        String[] dataNames = new String[]{"val0","val1" ,"val2", "val3", "val4","val5","val6"};
        for (int i = queryJsonRes_0.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 6;  j++) {

                makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("EVENT_TYPE_NAME"));
                makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("SUB_TYPE_NAME"));
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("THIRD_TYPE_NAME"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("NEED_DISPOSE_NUM"));
                makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("INTIME_DISPOSE_NUM"));
                makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("DISPOSE_RATE"));
                makeJson.put("val6", queryJsonRes_0.getJSONObject(i).get("CHECK_GRADE"));

            }
            data.add(makeJson);

        }
        table.add(new Table(headerNames,dataNames,data));

        System.out.println("Request:szcg/getSzcg_cgl_city" + date);
        return CommonResult.success(table);
    }


    @RequestMapping("/getSzcg_monitor")
    public CommonResult<List<Table>> getSzcg_monitor() {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=143&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25222%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522124%2522%2C%2522fieldTypeInfo%2522%3A%2522%24%24vc_region_district%24item_id%240%24item_name%2522%2C%2522values%2522%3A%255B%2522%27%25E9%2587%2591%25E7%2589%259B%25E5%258C%25BA%27%2522%255D%2C%2522condProperty%2522%3A%252216%2522%2C%2522mutiCond%2522%3A%25221%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522105%24%25E6%2589%2580%25E5%25B1%259E%25E5%258C%25BA%25E5%258E%25BF%2522%257D%255D%257D&hidenCond=ZGlzdHJpY3RfaWQgaW4gKCcxJywnMicsJzMnLCc0JywnNScsJzYnLCc4Jyk%3D&_=1657596770316";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");

        for (int i = queryJsonRes_0.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();
            JSONArray data = new JSONArray();
            String[] headerNames = new String[]{"区域","市级视频发现","区级视频发现", "区级视频占比","扣分"};
            String[] dataNames = new String[]{"val0","val1" ,"val2", "val3", "val4"};

            makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("districtName"));
            makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("reportNumS"));
            makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("reportNumQ"));
            makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("reportPercent"));
            makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("score"));



            data.add(makeJson);
            table.add(new Table(headerNames,dataNames,data));

        }


        System.out.println("Request:szcg/getSzcg_monitor" + date);
        return CommonResult.success(table);
    }
    @RequestMapping("/getSzcg_workman")
    public CommonResult<List<Table>> getSzcg_workman() {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
       /* String queryUrl_day =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=199&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E4%25BB%258B%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%2C%2522"+date+"%252023%3A59%3A59%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=&_=1657851336641";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_day)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");

        for (int i = queryJsonRes_0.size()-1; i >= 1; i--) {
            JSONObject makeJson = new JSONObject();
            JSONArray data = new JSONArray();
            String[] headerNames = new String[]{"日期","金牛区上岗数","金牛区缺勤数"};
            String[] dataNames = new String[]{"val0","val1" ,"val2"};

            makeJson.put("val0", date );
            makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("金牛区上岗数"));
            makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("金牛区缺勤数"));



            data.add(makeJson);
            table.add(new Table(headerNames,dataNames,data));

        }*/

        String queryUrl_week =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=199&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E4%25BB%258B%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_week+"%252000%3A00%3A00%2522%2C%2522"+date+"%252023%3A59%3A59%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=&_=1657851336641";
        HttpResponse queryRes_1 = HttpRequest.get(queryUrl_week)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_1 = JSON.parseObject(queryRes_1.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");

        for (int i = queryJsonRes_1.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();
            JSONArray data = new JSONArray();
            String[] headerNames = new String[]{"本周统计","金牛区上岗数","金牛区缺勤数"};
            String[] dataNames = new String[]{"val0","val1" ,"val2"};
            if (i==queryJsonRes_1.size()-1){makeJson.put("val0", "本周"+queryJsonRes_1.getJSONObject(i).get("日期"));}
            else makeJson.put("val0", queryJsonRes_1.getJSONObject(i).get("日期"));
            makeJson.put("val1", queryJsonRes_1.getJSONObject(i).get("金牛区上岗数"));
            makeJson.put("val2", queryJsonRes_1.getJSONObject(i).get("金牛区缺勤数"));



            data.add(makeJson);
            table.add(new Table(headerNames,dataNames,data));

        }
        String queryUrl_month =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=199&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E4%25BB%258B%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_month+"%252000%3A00%3A00%2522%2C%2522"+date+"%252023%3A59%3A59%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=&_=1657851336641";
        HttpResponse queryRes_2 = HttpRequest.get(queryUrl_month)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_2 = JSON.parseObject(queryRes_2.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        String[] headerNames = new String[]{"本月统计","金牛区上岗数","金牛区缺勤数"};
        String[] dataNames = new String[]{"val0","val1" ,"val2"};
        JSONArray data = new JSONArray();
        JSONObject makeJson = new JSONObject();
        makeJson.put("val0", queryJsonRes_1.getJSONObject(queryJsonRes_1.size()-1).get("日期"));
        makeJson.put("val1", queryJsonRes_1.getJSONObject(queryJsonRes_1.size()-1).get("金牛区上岗数"));
        makeJson.put("val2", queryJsonRes_1.getJSONObject(queryJsonRes_1.size()-1).get("金牛区缺勤数"));
        data.add(makeJson);
        table.add(new Table(headerNames,dataNames,data));
        System.out.println("Request:szcg/getSzcg_workman" + date);
        return CommonResult.success(table);
    }





    @RequestMapping("/getSzcg_period")
    public CommonResult<List<Table>> getSzcg_period () {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_day =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=184&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E4%25BB%258B%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%2C%2522"+date+"%252023%3A59%3A59%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=&_=1657851336917";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_day)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");


            JSONObject makeJson0 = new JSONObject();
            JSONArray data0 = new JSONArray();
            String[] headerNames0 = new String[]{"金牛区","金牛区有效上报基数","金牛区有效上报数", "金牛区未达标数"};
            String[] dataNames0 = new String[]{"val0","val1" ,"val2", "val3", "val4"};
            int size0 = queryJsonRes_0.size()-1;
            makeJson0.put("val0", "日"+queryJsonRes_0.getJSONObject(size0).get("period_time"));
            makeJson0.put("val1", queryJsonRes_0.getJSONObject(size0).get("金牛区有效上报基数"));
            makeJson0.put("val2", queryJsonRes_0.getJSONObject(size0).get("金牛区有效上报数"));
            makeJson0.put("val3", queryJsonRes_0.getJSONObject(size0).get("金牛区未达标数"));
            data0.add(makeJson0);

        String queryUrl_week =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=184&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E4%25BB%258B%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_week+"%252000%3A00%3A00%2522%2C%2522"+date+"%252023%3A59%3A59%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=&_=1657851336917";
        HttpResponse queryRes_1 = HttpRequest.get(queryUrl_week)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_1 = JSON.parseObject(queryRes_1.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");


        JSONObject makeJson1 = new JSONObject();
        JSONArray data1 = new JSONArray();
        String[] headerNames1 = new String[]{"金牛区","金牛区有效上报基数","金牛区有效上报数", "金牛区未达标数"};
        String[] dataNames1 = new String[]{"val0","val1" ,"val2", "val3", "val4"};
        int size1 = queryJsonRes_1.size()-1;
        makeJson1.put("val0", "周"+queryJsonRes_1.getJSONObject(size1).get("period_time"));
        makeJson1.put("val1", queryJsonRes_1.getJSONObject(size1).get("金牛区有效上报基数"));
        makeJson1.put("val2", queryJsonRes_1.getJSONObject(size1).get("金牛区有效上报数"));
        makeJson1.put("val3", queryJsonRes_1.getJSONObject(size1).get("金牛区未达标数"));
        data1.add(makeJson1);


        String queryUrl_month =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=184&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E4%25BB%258B%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_month+"-01%252000%3A00%3A00%2522%2C%2522"+date+"%252023%3A59%3A59%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=&_=1657851336917";
        HttpResponse queryRes_2 = HttpRequest.get(queryUrl_month)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_2 = JSON.parseObject(queryRes_2.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");


        JSONObject makeJson2 = new JSONObject();
        JSONArray data2 = new JSONArray();
        String[] headerNames2 = new String[]{"金牛区","金牛区有效上报基数","金牛区有效上报数", "金牛区未达标数"};
        String[] dataNames2 = new String[]{"val0","val1" ,"val2", "val3", "val4"};
        int size2 = queryJsonRes_2.size()-1;
        makeJson2.put("val0", "月"+queryJsonRes_2.getJSONObject(size2).get("period_time"));
        makeJson2.put("val1", queryJsonRes_2.getJSONObject(size2).get("金牛区有效上报基数"));
        makeJson2.put("val2", queryJsonRes_2.getJSONObject(size2).get("金牛区有效上报数"));
        makeJson2.put("val3", queryJsonRes_2.getJSONObject(size2).get("金牛区未达标数"));
        data2.add(makeJson2);



        table.add(new Table(headerNames0,dataNames0,data0));
        table.add(new Table(headerNames1,dataNames1,data1));
        table.add(new Table(headerNames2,dataNames2,data2));


        System.out.println("Request:szcg/getSzcg_period" + date);
        return CommonResult.success(table);
    }

    @RequestMapping("/getSzcg_diy")
    public CommonResult<List<Table>> getSzcg_diy () {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=170&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_month+"-01%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=&_=1657851337144";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{"日期","金牛区上报数","金牛区审核通过数","金牛区审核未通过数"};
        String[] dataNames = new String[]{"val0","val1" ,"val2", "val3"};
        for (int i = queryJsonRes_0.size()-1; i >= 0; i--) {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 4;  j++) {

                makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("日期"));
                makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("金牛区上报数"));
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("金牛区审核通过数"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("金牛区审核未通过数"));


            }
            data.add(makeJson);

        }
        table.add(new Table(headerNames,dataNames,data));

        System.out.println("Request:szcg/getSzcg_diy" + date);
        return CommonResult.success(table);
    }

    @RequestMapping("/getRoad")
    public CommonResult<List<Table>> getRoad () {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=124&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E4%25BB%258B%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%2C%2522"+date+"%252023%3A59%3A59%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25221%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522126%2522%2C%2522fieldTypeInfo%2522%3A%2522(district_id%2520in%2520(%257B0%257D)%2520or%2520street_id%2520in%2520(%257B1%257D)%2520or%2520community_id%2520in%2520(%257B2%257D))%2522%2C%2522values%2522%3A%255B%25220%3A3%3B1%3A100%2C101%2C102%2C103%2C104%2C105%2C106%2C107%2C108%2C109%2C110%2C111%2C112%3B2%3A10090%2C10091%2C10092%2C10093%2C10103%2C10104%2C10149%2C10587%2C10589%2C10621%2C10003%2C10004%2C10082%2C10083%2C10084%2C10085%2C10086%2C10087%2C10088%2C10089%2C10027%2C10031%2C10052%2C10053%2C10054%2C10070%2C10071%2C10072%2C10094%2C10095%2C10096%2C10097%2C10098%2C10099%2C10100%2C10101%2C10102%2C10568%2C10577%2C10620%2C10015%2C10016%2C10017%2C10047%2C10008%2C10009%2C10010%2C10040%2C10041%2C10042%2C10062%2C10077%2C10078%2C10563%2C10571%2C10055%2C10056%2C10057%2C10058%2C10073%2C10074%2C10075%2C10076%2C10000%2C10001%2C10002%2C10005%2C10006%2C10007%2C10033%2C10034%2C10035%2C10036%2C10037%2C10038%2C10039%2C10059%2C10060%2C10061%2C10080%2C10081%2C10023%2C10024%2C10025%2C10026%2C10079%2C10578%2C10018%2C10019%2C10020%2C10021%2C10022%2C10028%2C10029%2C10030%2C10032%2C10011%2C10012%2C10013%2C10014%2C10043%2C10044%2C10045%2C10046%2C10063%2C10064%2C10065%2C10066%2C10588%2C10048%2C10049%2C10050%2C10067%2C10068%2C10069%2C10590%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%25221%24%25E5%258C%25BA%25E5%259F%259F%25E6%259D%25A1%25E4%25BB%25B6%2522%257D%255D%257D&hidenCond=&_=1658107024354";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");

        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{"道路","所属街道","有效上报数","处置数","处置率","结案率"};
        String[] dataNames = new String[]{"val0","val1" ,"val2", "val3","val4","val5"};
        for (int i = 0; i <= queryJsonRes_0.size()-1; i++) {
            JSONObject makeJson = new JSONObject();
            for (int j = 0; j < 5;  j++) {
                ;
                makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("road_name"));
                makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("street_name"));
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("valid_report_num"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("operate_num"));
                makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("dispose_ratio"));
                makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("archive_ratio"));

            }
            data.add(makeJson);
        }

        table.add(new Table(headerNames,dataNames,data));

        System.out.println("Request:szcg/getRoad" + date +time);
        return CommonResult.success(table);
    }



    @RequestMapping("/getRoad_type")
    public CommonResult<List<Table>> getRoad_type () {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=125&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E4%25BB%258B%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%2C%2522"+date+"%252023%3A59%3A59%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25221%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522126%2522%2C%2522fieldTypeInfo%2522%3A%2522(district_id%2520in%2520(%257B0%257D)%2520or%2520street_id%2520in%2520(%257B1%257D)%2520or%2520community_id%2520in%2520(%257B2%257D))%2522%2C%2522values%2522%3A%255B%25220%3A3%3B1%3A100%2C101%2C102%2C103%2C104%2C105%2C106%2C107%2C108%2C109%2C110%2C111%2C112%3B2%3A10090%2C10091%2C10092%2C10093%2C10103%2C10104%2C10149%2C10587%2C10589%2C10621%2C10003%2C10004%2C10082%2C10083%2C10084%2C10085%2C10086%2C10087%2C10088%2C10089%2C10027%2C10031%2C10052%2C10053%2C10054%2C10070%2C10071%2C10072%2C10094%2C10095%2C10096%2C10097%2C10098%2C10099%2C10100%2C10101%2C10102%2C10568%2C10577%2C10620%2C10015%2C10016%2C10017%2C10047%2C10008%2C10009%2C10010%2C10040%2C10041%2C10042%2C10062%2C10077%2C10078%2C10563%2C10571%2C10055%2C10056%2C10057%2C10058%2C10073%2C10074%2C10075%2C10076%2C10000%2C10001%2C10002%2C10005%2C10006%2C10007%2C10033%2C10034%2C10035%2C10036%2C10037%2C10038%2C10039%2C10059%2C10060%2C10061%2C10080%2C10081%2C10023%2C10024%2C10025%2C10026%2C10079%2C10578%2C10018%2C10019%2C10020%2C10021%2C10022%2C10028%2C10029%2C10030%2C10032%2C10011%2C10012%2C10013%2C10014%2C10043%2C10044%2C10045%2C10046%2C10063%2C10064%2C10065%2C10066%2C10588%2C10048%2C10049%2C10050%2C10067%2C10068%2C10069%2C10590%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%25221%24%25E5%258C%25BA%25E5%259F%259F%25E6%259D%25A1%25E4%25BB%25B6%2522%257D%255D%257D&hidenCond=&_=1658108843976";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{"道路","大类","小类","细类","有效上报数","处置率","结案率"};
        String[] dataNames = new String[]{"val0","val1" ,"val2", "val3","val4","val5","val6"};
        for (int i = 0; i <= queryJsonRes_0.size()-1; i++) {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 7;  j++) {

                makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("road_name"));
                makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("main_type_name"));
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("sub_type_name"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("third_type_name"));
                makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("valid_report_num"));
                makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("operate_num"));
                makeJson.put("val6", queryJsonRes_0.getJSONObject(i).get("dispose_ratio"));


            }
            data.add(makeJson);

        }
        table.add(new Table(headerNames,dataNames,data));

        System.out.println("Request:szcg/getRoad_dispose" + date +time);
        return CommonResult.success(table);
    }

    @RequestMapping("/getCgzxpj")
    public CommonResult<List<Table>> getCgzxpj () {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_district = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=139&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%2C%257B%2522condId%2522%3A%25221%2522%2C%2522fieldName%2522%3A%2522district_name%2522%2C%2522compType%2522%3A%2522%25E7%25AD%2589%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%2522126%2522%2C%2522fieldTypeInfo%2522%3A%2522(district_id%2520in%2520(%257B0%257D)%2520or%2520street_id%2520in%2520(%257B1%257D)%2520or%2520community_id%2520in%2520(%257B2%257D))%2522%2C%2522values%2522%3A%255B%25220%3A3%3B1%3A100%2C101%2C102%2C103%2C104%2C105%2C106%2C107%2C108%2C109%2C110%2C111%2C112%3B2%3A10090%2C10091%2C10092%2C10093%2C10103%2C10104%2C10149%2C10587%2C10589%2C10621%2C10003%2C10004%2C10082%2C10083%2C10084%2C10085%2C10086%2C10087%2C10088%2C10089%2C10027%2C10031%2C10052%2C10053%2C10054%2C10070%2C10071%2C10072%2C10094%2C10095%2C10096%2C10097%2C10098%2C10099%2C10100%2C10101%2C10102%2C10568%2C10577%2C10620%2C10015%2C10016%2C10017%2C10047%2C10008%2C10009%2C10010%2C10040%2C10041%2C10042%2C10062%2C10077%2C10078%2C10563%2C10571%2C10055%2C10056%2C10057%2C10058%2C10073%2C10074%2C10075%2C10076%2C10000%2C10001%2C10002%2C10005%2C10006%2C10007%2C10033%2C10034%2C10035%2C10036%2C10037%2C10038%2C10039%2C10059%2C10060%2C10061%2C10080%2C10081%2C10023%2C10024%2C10025%2C10026%2C10079%2C10578%2C10018%2C10019%2C10020%2C10021%2C10022%2C10028%2C10029%2C10030%2C10032%2C10011%2C10012%2C10013%2C10014%2C10043%2C10044%2C10045%2C10046%2C10063%2C10064%2C10065%2C10066%2C10588%2C10048%2C10049%2C10050%2C10067%2C10068%2C10069%2C10590%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%25221%24%25E5%258C%25BA%25E5%259F%259F%25E6%259D%25A1%25E4%25BB%25B6%2522%257D%255D%257D&hidenCond=&_=1658108843996";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_district)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");
        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{"大类","小类","细类","市级监督考评员上报数","市级监督考评员有效上报数","区级监督考评员上报数","区级监督考评员有效上报数"};
        String[] dataNames = new String[]{"val0","val1" ,"val2", "val3","val4","val5","val6","val7"};
        for (int i = 0; i <= queryJsonRes_0.size()-1; i++) {
            JSONObject makeJson = new JSONObject();

            for (int j = 0; j < 7;  j++) {

                makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("main_type_name"));
                makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("sub_type_name"));
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("third_type_name"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("city_patrol_report_num"));
                makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("city_valid_patrol_report_num"));
                makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("district_patrol_report_num"));
                makeJson.put("val6", queryJsonRes_0.getJSONObject(i).get("district_valid_patrol_report_num"));

            }
            data.add(makeJson);

        }
        table.add(new Table(headerNames,dataNames,data));

        System.out.println("Request:szcg/getCgzxpj" + date +time);
        return CommonResult.success(table);
    }



    @RequestMapping("/getEvaluation")
    public CommonResult<List<Table>> getEvaluation () {
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week = formatter.format(DateUtil.getBeginDayOfWeek());
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
        // 构造执行登陆请求 发起HTTP POST请求 CommonResult <List<Table>>

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
        List<Table> table = new ArrayList<>();
        List<HttpCookie> cookies = loginRes.getCookies();
        String queryUrl_chief = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=153&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZXZlbnRfbGV2ZWxfaWQgPSAy&_=1658125730535";
        HttpResponse queryRes_0 = HttpRequest.get(queryUrl_chief)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_0 = JSON.parseObject(queryRes_0.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");

        JSONArray data = new JSONArray();
        String[] headerNames = new String[]{"值班员岗位评价","区域","按时立案率","结案数","结案率","综合指标评价","得分","本市排名"};
        String[] dataNames = new String[]{"val","val0","val1" ,"val2", "val3","val4","val5","val6"};
        JSONObject makeJson = new JSONObject();
        for (int i = 0; i <= queryJsonRes_0.size()-1; i++) {
            if(Objects.equals(queryJsonRes_0.getJSONObject(i).getString("district_name"), "金牛区")){
                makeJson.put("val0", queryJsonRes_0.getJSONObject(i).get("district_name"));
                makeJson.put("val1", queryJsonRes_0.getJSONObject(i).get("intime_inst_num"));
                makeJson.put("val2", queryJsonRes_0.getJSONObject(i).get("intime_archive_num"));
                makeJson.put("val3", queryJsonRes_0.getJSONObject(i).get("intime_archive_rate"));
                makeJson.put("val4", queryJsonRes_0.getJSONObject(i).get("rask"));
                makeJson.put("val5", queryJsonRes_0.getJSONObject(i).get("score"));
                makeJson.put("val6", queryJsonRes_0.getJSONObject(i).get("rank"));
                data.add(makeJson);

            }


        }


        String queryUrl_operator = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=167&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=&_=1658125730573";
        HttpResponse queryRes_1 = HttpRequest.get(queryUrl_operator)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_1 = JSON.parseObject(queryRes_1.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");

        JSONArray data1 = new JSONArray();
        String[] headerNames1 = new String[]{"派遣员岗位评价","区域","派遣数","督察数","按时督察率","综合指标评价","得分","本市排名"};
        String[] dataNames1 = new String[]{"val","val0","val1" ,"val2", "val3","val4","val5","val6"};
        JSONObject makeJson1 = new JSONObject();
        for (int i = 0; i <= queryJsonRes_1.size()-1; i++) {
            if(Objects.equals(queryJsonRes_1.getJSONObject(i).getString("district_name"), "金牛区")){
                makeJson1.put("val0", queryJsonRes_1.getJSONObject(i).get("district_name"));
                makeJson1.put("val1", queryJsonRes_1.getJSONObject(i).get("intime_dispatch_num"));
                makeJson1.put("val2", queryJsonRes_1.getJSONObject(i).get("dc_num"));
                makeJson1.put("val3", queryJsonRes_1.getJSONObject(i).get("intime_dc_rate"));
                makeJson1.put("val4", queryJsonRes_1.getJSONObject(i).get("rask"));
                makeJson1.put("val5", queryJsonRes_1.getJSONObject(i).get("score"));
                makeJson1.put("val6", queryJsonRes_1.getJSONObject(i).get("rank"));


            }

        }
        data1.add(makeJson1);
        String queryUrl_accept = "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=151&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZXZlbnRfbGV2ZWxfaWQgPSAyIGFuZCBldmVudF9zcmNfaWQgaW4gKCcxJywnMzQnLCc3MicsJzcxJyk%3D&_=1658125730623";
        HttpResponse queryRes_2 = HttpRequest.get(queryUrl_accept)
                .cookie(cookies)
                .execute();

        JSONArray queryJsonRes_2 = JSON.parseObject(queryRes_2.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");

        JSONArray data2 = new JSONArray();
        String[] headerNames2 = new String[]{"受理员岗位评价","区域","按时预立案率","预立案准确率","核查派发率","综合指标评价","得分","本市排名"};
        String[] dataNames2 = new String[]{"val","val0","val1" ,"val2", "val3","val4","val5","val6"};
        JSONObject makeJson2 = new JSONObject();
        for (int i = 0; i <= queryJsonRes_2.size()-1; i++) {
            if(Objects.equals(queryJsonRes_2.getJSONObject(i).getString("district_name"), "金牛区")){
                makeJson2.put("val0", queryJsonRes_2.getJSONObject(i).get("district_name"));
                makeJson2.put("val1", queryJsonRes_2.getJSONObject(i).get("intime_operate_num"));
                makeJson2.put("val2", queryJsonRes_2.getJSONObject(i).get("inst_rate"));
                makeJson2.put("val3", queryJsonRes_2.getJSONObject(i).get("intime_send_check_rate"));
                makeJson2.put("val4", queryJsonRes_2.getJSONObject(i).get("rask"));
                makeJson2.put("val5", queryJsonRes_2.getJSONObject(i).get("score"));
                makeJson2.put("val6", queryJsonRes_2.getJSONObject(i).get("rank"));
                data2.add(makeJson2);

            }

        }


        String queryUrl_supervisor= "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=149&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date+"%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=ZXZlbnRfc3JjX2lkIGluICgnMScsJzM0JywnNzInLCc3MScp&_=1658125730811";

        HttpResponse queryRes_3 = HttpRequest.get(queryUrl_supervisor)
                .cookie(cookies)
                .execute();


        JSONArray queryJsonRes_3 = JSON.parseObject(queryRes_3.body()).getJSONObject("data").getJSONObject("resultInfo").getJSONArray("dataStr");

        JSONArray data3 = new JSONArray();
        String[] headerNames3 = new String[]{"监督员岗位评价","区域","监督员准确上报数","有效上报率","按时核实率","按时核查率","综合指标评价","得分","本市排名"};
        String[] dataNames3 = new String[]{"val","val0","val1" ,"val2", "val3","val4","val5","val6","val7"};
        JSONObject makeJson3 = new JSONObject();
        for (int i = 0; i <= queryJsonRes_3.size()-1; i++) {
            if(Objects.equals(queryJsonRes_3.getJSONObject(i).getString("humanUnitName"), "金牛区监督员管理")){
                makeJson3.put("val0", queryJsonRes_3.getJSONObject(i).get("humanUnitName"));
                makeJson3.put("val1", queryJsonRes_3.getJSONObject(i).get("validPatrolReportNum"));
                makeJson3.put("val2", queryJsonRes_3.getJSONObject(i).get("patrolReportRate"));
                makeJson3.put("val3", queryJsonRes_3.getJSONObject(i).get("intimeVerifyRate"));
                makeJson3.put("val4", queryJsonRes_3.getJSONObject(i).get("intimeCheckRate"));
                makeJson3.put("val5", queryJsonRes_3.getJSONObject(i).get("rask"));
                makeJson3.put("val6", queryJsonRes_3.getJSONObject(i).get("score"));
                makeJson3.put("val7", queryJsonRes_3.getJSONObject(i).get("rank"));
                data3.add(makeJson3);

            }

        }
        table.add(new Table(headerNames,dataNames,data));
        table.add(new Table(headerNames1,dataNames1,data1));
        table.add(new Table(headerNames2,dataNames2,data2));
        table.add(new Table(headerNames3,dataNames3,data3));
        System.out.println("Request:szcg/getEvaluation" + date +time);
        return CommonResult.success(table);
    }


    @RequestMapping("/result")
    public JSONObject getResult(){
        HashMap<String, Object> loginParam = new HashMap<>();
        Date calDate= new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter_month = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = formatter_time.format(calDate);
        String date = formatter.format(calDate);
        String date_month = formatter_month.format(calDate);
        String date_week= formatter.format(DateUtil.getBeginDayOfWeek());
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
        List<HttpCookie> cookies = loginRes.getCookies();

        //System.out.println(loginRes.getCookies().toString());

        String queryUrl =  "http://171.221.172.74:6888/eUrbanMIS/home/stat/stat/excutestat?queryID=170&missionID=-1&condParams=%257B%2522queryConds%2522%3A%255B%257B%2522condId%2522%3A%25220%2522%2C%2522fieldName%2522%3A%2522create_time%2522%2C%2522compType%2522%3A%2522%25E5%25A4%25A7%25E4%25BA%258E%2522%2C%2522dataTypeID%2522%3A%252211%2522%2C%2522values%2522%3A%255B%2522"+date_month+"-01%252000%3A00%3A00%2522%255D%2C%2522condProperty%2522%3A%25220%2522%2C%2522mutiCond%2522%3A%25220%2522%2C%2522condType%2522%3A%25220%2522%2C%2522outParams%2522%3A%2522%2522%2C%2522fixTimeCond%2522%3A%25220%2522%2C%2522isFromMobile%2522%3A%25220%2522%2C%2522componentType%2522%3A%25220%2522%2C%2522timeCondSql%2522%3A%2522%2522%257D%255D%257D&hidenCond=&_=1657851337141";

        JSONObject makeJson_case_week = new JSONObject();
        HashMap<String, Object> queryParam_case_week_ended = new HashMap<>();
        /*queryParam_case_week_ended.put("queryID", "2");
        queryParam_case_week_ended.put("condparams","{\"queryConds\": %5B%7B%22condId%22:%221%22,%22fieldName%22:%22create_time%22,%22compType%22:%22%E5%A4%A7%E4%BA%8E%22,%22dataTypeID%22:%2211%22,%22values%22:%5B%22"+date_week+"%2000:00:00%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22%22%7D,%7B%22condId%22:%225%22,%22fieldName%22:%22event_state_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22124%22,%22fieldTypeInfo%22:%22$$vc_act_property_list_wtzt$item_id$0$item_name%22,%22values%22:%5B%22'%E7%BB%93%E6%A1%88'%22%5D,%22condProperty%22:%2216%22,%22mutiCond%22:%221%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%22109$%E9%97%AE%E9%A2%98%E7%8A%B6%E6%80%81%22%7D,%7B%22condId%22:%228%22,%22fieldName%22:%22district_name%22,%22compType%22:%22%E7%AD%89%E4%BA%8E%22,%22dataTypeID%22:%22126%22,%22fieldTypeInfo%22:%22(district_id%20in%20(%7B0%7D)%20or%20street_id%20in%20(%7B1%7D)%20or%20community_id%20in%20(%7B2%7D))%22,%22values%22:%5B%220:3;1:100,101,102,103,104,105,106,107,108,109,110,111,112;2:10090,10091,10092,10093,10103,10104,10149,10587,10589,10621,10003,10004,10082,10083,10084,10085,10086,10087,10088,10089,10027,10031,10052,10053,10054,10070,10071,10072,10094,10095,10096,10097,10098,10099,10100,10101,10102,10568,10577,10620,10015,10016,10017,10047,10008,10009,10010,10040,10041,10042,10062,10077,10078,10563,10571,10055,10056,10057,10058,10073,10074,10075,10076,10000,10001,10002,10005,10006,10007,10033,10034,10035,10036,10037,10038,10039,10059,10060,10061,10080,10081,10023,10024,10025,10026,10079,10578,10018,10019,10020,10021,10022,10028,10029,10030,10032,10011,10012,10013,10014,10043,10044,10045,10046,10063,10064,10065,10066,10588,10048,10049,10050,10067,10068,10069,10590%22%5D,%22condProperty%22:%220%22,%22mutiCond%22:%220%22,%22condType%22:%220%22,%22outParams%22:%22%22,%22fixTimeCond%22:%220%22,%22isFromMobile%22:%220%22,%22componentType%22:%220%22,%22timeCondSql%22:%221$%E5%8C%BA%E5%9F%9F%E6%9D%A1%E4%BB%B6%22%7D%5D}");
        queryParam_case_week_ended.put("hidenCond", "ZXZlbnRfc3JjX2lkIGluICgnMScsJzUnLCczNCcsJzcxJywnNzInLCc3NicsJzQnLCc3NycsJzc4JywnNzknLCc4MCcp");
        queryParam_case_week_ended.put("headerColumns", "");
        queryParam_case_week_ended.put("missionID",-1);
        queryParam_case_week_ended.put("condInfo","   【上报时间  大于  "+date_week+"  】并且      【所属区域  等于  金牛区;茶店子街道;奥林社区;化成社区;锦城社区;育苗路社区;金沙公园东;金沙公园社;金沙公园北;高家社区;跃进社区;郞家社区;凤凰山街道;凤翔社区;金韦社区;抚琴街道;光荣小区社;金沙路社区;圃园路社区;铁路新村社;金琴路社区;金鱼街社区;西南街社区;西北街社区;荷花池街道;城隍庙社区;五丁路社区;西北桥社区;西三巷社区;杨柳巷社区;东一路社区;荷花池社区;互助路社区;金泉街道;高家社区;金桥社区;蜀西社区;土桥社区;迎宾路社区;淳风桥社区;金科苑社区;两河社区;清水河社区;何家社区;互助社区;郞家社区;九里堤街道;九里堤北路;康禧路社区;星河路社区;西南交大社;沙河源街道;川建社区;友联社区;长久社区;古柏社区;踏水桥社区;新桥社区;政通路社区;王贾桥社区;泉水社区;汇泽路社区;陆家桥社区;驷马桥街道;工人村社区;恒德路社区;马鞍东路社;星辉东路社;高笋塘社区;树蓓街社区;一环路北四;红花社区;天回镇街道;车站社区;红星社区;金华社区;土门社区;银杏园社区;长胜社区;白塔社区;杜家碾社区;甘油社区;明月社区;石门社区;天回社区;余家巷社区;大湾社区;太华社区;万圣社区;宝年社区;木龙湾社区;五块石街道;五福社区;五块石社区;五块石新社;玉局庵社区;五福新社区;铁路;西安路街道;白果林社区;金琴南路社;青羊北路社;永陵社区;枣子巷社区;花牌坊社区;金仙桥社区;马家花园社;西体路社区;西华街道;华府社区;金牛社区;瓦子社区;跃进社区;富家社区;侯家社区;涧漕社区;金罗社区;府河新居社;青杠社区;兴盛社区;兴盛世家社;金桥社区;营门口街道;银桂桥社区;银沙路社区;营门口路社;茶店社区;花照社区;长庆路社区;跃进社区  】");
*/
        HttpResponse queryRes_case_week_ended = HttpRequest.get(queryUrl)
                .form(queryParam_case_week_ended)
                .cookie(cookies)
                .execute();
        JSONObject queryJsonRes_case_week_ended = JSON.parseObject(queryRes_case_week_ended.body());


        return JSON.parseObject(queryRes_case_week_ended.body());
    }

}
