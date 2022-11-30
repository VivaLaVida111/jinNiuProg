package com.example.jinniuprog.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.jinniuprog.common.CommonResult;
import com.example.jinniuprog.entity.Table;
import com.example.jinniuprog.service.JsonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/yyxt")
public class youyanController {
    Date calDate= new Date(System.currentTimeMillis());
    SimpleDateFormat formatter_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String time = formatter_time.format(calDate);
    @Autowired
    JsonService jsonService;
    @CrossOrigin(origins = "*")
    @RequestMapping("/mainInfo")

    public CommonResult<JSONArray> getMainInfo(){
        String loginUrl = "http://47.93.222.102:9501/login";
        HashMap<String, Object> loginParam = new HashMap<>();
        loginParam.put("account","18981967017");
        loginParam.put("pwd","9876huhuhu");
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

        String queryUrl = "http://47.93.222.102:9501/companies/list";
        //page=1&limit=10&scale=&devices_id=&monitoring_status=&company_name=&area_id=&level=
        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("page","1");
        queryParam.put("limit","10");
        queryParam.put("scale","");
        queryParam.put("devices_id","");
        queryParam.put("monitoring_status","");
        queryParam.put("company_name","");
        queryParam.put("area_id","");
        queryParam.put("level","");

        HttpResponse queRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                .form(queryParam).execute();
        JSONObject queryJsonRes = JSON.parseObject(queRes.body());

        JSONArray data = new JSONArray();
        JSONObject makeJson = new JSONObject();
        makeJson.put("infoKey","已安装设备企业");
        makeJson.put("infoVal",queryJsonRes.get("totalCount"));
        data.add(makeJson);

        queryParam.replace("monitoring_status","1");
        queRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                .form(queryParam).execute();
        JSONObject makeJson1 = new JSONObject();
        queryJsonRes = JSON.parseObject(queRes.body());
        makeJson1.put("infoKey","正常运行企业");
        makeJson1.put("infoVal",queryJsonRes.get("totalCount"));
        data.add(makeJson1);

        //System.out.println(queRes.body());

        queryParam.replace("monitoring_status","0");
        queRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                .form(queryParam).execute();
        JSONObject makeJson0 = new JSONObject();
        queryJsonRes = JSON.parseObject(queRes.body());
        makeJson0.put("infoKey","离线企业数");
        makeJson0.put("infoVal",queryJsonRes.get("totalCount"));
        data.add(makeJson0);

        queryParam.replace("monitoring_status","2");
        queRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                .form(queryParam).execute();
        JSONObject makeJson2 = new JSONObject();
        queryJsonRes = JSON.parseObject(queRes.body());
        makeJson2.put("infoKey","超标企业数");
        makeJson2.put("infoVal",queryJsonRes.get("totalCount"));

        data.add(makeJson2);

        queryParam.replace("monitoring_status","3");
        queRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                .form(queryParam).execute();
        JSONObject makeJson3 = new JSONObject();
        makeJson3.put("infoKey","预警企业数");queryJsonRes = JSON.parseObject(queRes.body());
        makeJson3.put("infoVal",queryJsonRes.get("totalCount"));

        data.add(makeJson3);




        return CommonResult.success(data);
    }
    @CrossOrigin(origins = "*")
    @RequestMapping("/getCompanyList")
    public CommonResult <List<Table>> getCompanyList(){
        String queryUrl = "http://47.93.222.102:9501/companies/list";
        //page=1&limit=10&scale=&devices_id=&monitoring_status=&company_name=&area_id=&level=
        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("page","1");
        queryParam.put("limit","10");
        queryParam.put("scale","");
        queryParam.put("devices_id","");
        queryParam.put("monitoring_status","");
        queryParam.put("company_name","");
        queryParam.put("area_id","");
        queryParam.put("level","");

        HttpResponse queRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                .form(queryParam).execute();
        JSONObject queryJsonRes = JSON.parseObject(queRes.body());
        JSONArray data = new JSONArray();
        JSONObject makeJson = new JSONObject();
        makeJson.put("all",queryJsonRes.get("totalCount"));

        queryParam.replace("monitoring_status","1");
        queRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                .form(queryParam).execute();
        queryJsonRes = JSON.parseObject(queRes.body());
        makeJson.put("normal",queryJsonRes.get("totalCount"));

        queryParam.replace("monitoring_status","0");
        queRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                .form(queryParam).execute();
        queryJsonRes = JSON.parseObject(queRes.body());
        makeJson.put("offline",queryJsonRes.get("totalCount"));

        queryParam.replace("monitoring_status","2");
        queRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                .form(queryParam).execute();
        queryJsonRes = JSON.parseObject(queRes.body());
        makeJson.put("overproof",queryJsonRes.get("totalCount"));

        queryParam.replace("monitoring_status","3");
        queRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                .form(queryParam).execute();
        queryJsonRes = JSON.parseObject(queRes.body());
        makeJson.put("warning",queryJsonRes.get("totalCount"));

        data.add(makeJson);
        System.out.println("request:/getCompanyList"+time);

        String[] headerNames = new String[]{"全部企业", "正常排放企业", "排放预警企业", "超标排放企业", "设备离线企业"};
        //数据名字
        String[] dataNames = new String[]{"all", "normal", "warning", "overproof", "offline"};
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("all", queryJsonRes.get("totalStatus"));
        jsonObject.put("normal", queryJsonRes.get("totalStatus"));
        jsonObject.put("warning", queryJsonRes.get("totalStatus"));
        jsonObject.put("overproof", queryJsonRes.get("totalStatus"));
        jsonObject.put("offline", queryJsonRes.get("totalStatus"));
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(makeJson);

        List<Table> table = new ArrayList<>();
        table.add(new Table(headerNames,dataNames,data));
        return CommonResult.success(table);


    }
    @RequestMapping("/getCompanyReference")
    public CommonResult<List<Table>> getCompanyReference(){
        String queryUrl = "http://47.93.222.102:9501/companyReference/epSurvey_chart";
        HashMap<String, Object> queryParam = new HashMap<>();
        HttpResponse queRes = HttpRequest.get(queryUrl)
                .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                .form(queryParam).execute();
       // System.out.println(queRes.body());
        JSONObject queryJsonRes = new JSONObject();
        queryJsonRes = JSON.parseObject(queRes.body()).getJSONObject("list");
        //System.out.println(queryJsonRes);
        List<Table> table = new ArrayList<>();
        JSONArray data0 = new JSONArray();
        JSONObject makeJson_level0 = new JSONObject();
        String[] headerNames_monitor = new String[]{"全部企业", "一般监控", "重点监控"};
        String[] dataNames_monitor = new String[]{"all", "level_g", "level_p"};

        makeJson_level0.put("all",queryJsonRes.getJSONObject("monitoring_level").getIntValue("monitoring_level_g")+queryJsonRes.getJSONObject("monitoring_level").getIntValue("monitoring_level_p"));
        makeJson_level0.put("level_g",queryJsonRes.getJSONObject("monitoring_level").get("monitoring_level_g"));
        makeJson_level0.put("level_p",queryJsonRes.getJSONObject("monitoring_level").get("monitoring_level_p"));

        data0.add(makeJson_level0);

        /*SONObject makeJson_table = new JSONObject();
        makeJson_table.put("all","小型企业数");
        makeJson_table.put("level_g","中型企业数");
        makeJson_table.put("level_p","大型企业数");
        data.add(makeJson_table);*/
        table.add(new Table(headerNames_monitor,dataNames_monitor,data0));

        JSONArray data1 = new JSONArray();
        JSONObject makeJson_scale_s = new JSONObject();
        String[] headerNames_scale = new String[]{"小型规模企业", "中型规模企业", "大型规模企业"};
        String[] dataNames_scale = new String[]{"scale_s", "scale_m", "scale_l"};
        makeJson_scale_s.put("scale_s",queryJsonRes.getJSONObject("scale").get("scale_s"));
        makeJson_scale_s.put("scale_m",queryJsonRes.getJSONObject("scale").get("scale_m"));
        makeJson_scale_s.put("scale_l",queryJsonRes.getJSONObject("scale").get("scale_l"));
        data1.add(makeJson_scale_s);
        table.add(new Table(headerNames_scale,dataNames_scale,data1));

        JSONArray data2 = new JSONArray();
        JSONObject makeJson_operating_state = new JSONObject();
        String[] headerNames_state = new String[]{"营业中企业", "停业企业", "结业企业"};
        String[] dataNames_state = new String[]{"state_n", "state_s", "state_c"};
        makeJson_operating_state.put("state_n",queryJsonRes.getJSONObject("operating_state").get("operating_state_n"));
        makeJson_operating_state.put("state_s",queryJsonRes.getJSONObject("operating_state").get("operating_state_s"));
        makeJson_operating_state.put("state_c",queryJsonRes.getJSONObject("operating_state").get("operating_state_c"));
        data2.add(makeJson_operating_state);
        table.add(new Table(headerNames_state,dataNames_state,data2));

        JSONArray data3 = new JSONArray();
        JSONObject makeJson_cuisine = new JSONObject();
        String[] headerNames_cuisine = new String[]{"中餐", "火锅", "烧烤","面食","中式包点","饮品","其他"};
        String[] dataNames_cuisine = new String[]{"mid", "hotpot", "bbq","noodles","dumplings","drink","others"};
        JSONArray jsonArray = queryJsonRes.getJSONArray("cuisine");
        for (int i = 0;i<jsonArray.size()-1;i++)
        {
            makeJson_cuisine.put(dataNames_cuisine[i],jsonArray.getJSONObject(i).get("num"));
        }

        data3.add(makeJson_cuisine);
        table.add(new Table(headerNames_cuisine,dataNames_cuisine,data3));




        System.out.println("request:yyxt/getCompanyReference"+time);
        return CommonResult.success(table);

    }

  /*  @RequestMapping("/getStoreNum")
    public CommonResult <List<Table>> getStoreNum() {
        String queryUrl_store = "http://47.93.222.102:9501/companyReference/storeQuery";
        HashMap<String, Object> queryParam_store = new HashMap<>();
        HttpResponse queRes_store = HttpRequest.get(queryUrl_store)
                .header(Header.AUTHORIZATION, "62a843bfd1b3d61005520385")
                .form(queryParam_store).execute();
        JSONObject queryJsonRes_store;
        queryJsonRes_store = JSON.parseObject(queRes_store.body());
        JSONObject makeJson_store = new JSONObject();
        makeJson_store.put("day_over", queryJsonRes_store.get("totalCount"));
        List<Table> table = new ArrayList<>();
        JSONArray data = new JSONArray();
        String headerNames_store[] = new String[]{ "商铺总数", "金牛区备案月统计","本月增加"};
        String dataNames_store[] = new String[]{"day","day_increase", "week", "week_increase","month","month_increase"};

    }*/

    @RequestMapping("/getStatist")
    public CommonResult <List<Table>> getStatist() {
        String queryUrl_over_day = "http://47.93.222.102:9501/statistics/overStand";
        HashMap<String, Object> queryParam_over_day = new HashMap<>();
        HttpResponse queRes_over_day = HttpRequest.get(queryUrl_over_day)
                .header(Header.AUTHORIZATION, "62a843bfd1b3d61005520385")
                .form(queryParam_over_day).execute();
        JSONObject queryJsonRes_over_day;
        queryJsonRes_over_day = JSON.parseObject(queRes_over_day.body());
        JSONObject makeJson_overStand = new JSONObject();
        makeJson_overStand.put("day_over", queryJsonRes_over_day.get("totalCount"));
        List<Table> table = new ArrayList<>();
        JSONArray data = new JSONArray();


        String queryUrl_over_week = "http://47.93.222.102:9501/statistics/overStandWeek";
        HashMap<String, Object> queryParam_over_week = new HashMap<>();
        HttpResponse queRes_over_week = HttpRequest.get(queryUrl_over_week)
                .header(Header.AUTHORIZATION, "62a843bfd1b3d61005520385")
                .form(queryParam_over_week).execute();
        JSONObject queryJsonRes_over_week;
        queryJsonRes_over_week = JSON.parseObject(queRes_over_week.body());
        //System.out.println(queryJsonRes_over_week);
        JSONObject makeJson_over_week = new JSONObject();
        makeJson_overStand.put("week_over", queryJsonRes_over_week.get("totalCount"));
        //data.add(makeJson_over_week);


        String queryUrl_over_month = "http://47.93.222.102:9501/statistics/overStandMonth";
        HashMap<String, Object> queryParam_over_month = new HashMap<>();
        HttpResponse queryRes_over_month = HttpRequest.get(queryUrl_over_month)
                .header(Header.AUTHORIZATION, "62a843bfd1b3d61005520385")
                .form(queryParam_over_month).execute();
        JSONObject queryJsonRes_over_month = new JSONObject();
        queryJsonRes_over_month = JSON.parseObject(queryRes_over_month.body());
        JSONObject makeJson_over_month = new JSONObject();
        makeJson_overStand.put("month_over", queryJsonRes_over_month.get("totalCount"));
        //data.add(makeJson_over_month);


        String queryUrl_statics = "http://47.93.222.102:9501/statistics/comprehensiveStatistics_chart";
        HttpResponse queRes_total = HttpRequest.get(queryUrl_statics)
                .header(Header.AUTHORIZATION, "62a843bfd1b3d61005520385")
                .execute();
        JSONObject queryJsonRes_total = JSON.parseObject(queRes_total.body());
        JSONObject makeJson_total = new JSONObject();

        String[] headerNames_over = new String[]{"日超标", "周超标", "月超标"};
        String[] dataNames_over = new String[]{"day_over", "week_over", "month_over"};
      data.add(makeJson_overStand);


        JSONArray data2 = new JSONArray();
        JSONObject makeJson_monitor = new JSONObject();
        String[] headerNames_monitor = new String[]{"监控仪在线数", "监控仪离线数", "监控仪在线率"};
        String[] dataNames_monitor = new String[]{"zh_online", "zh_offline", "zh_rate"};
        makeJson_monitor.put("zh_online",queryJsonRes_total.getJSONObject("listTotal").get("zh_online"));
        makeJson_monitor.put("zh_offline",queryJsonRes_total.getJSONObject("listTotal").get("zh_offline"));
        makeJson_monitor.put("zh_rate",queryJsonRes_total.getJSONObject("listTotal").get("zh_online_per"));
        data2.add(makeJson_monitor);

        JSONArray data3 = new JSONArray();
        String queryUrl_epNum_day = "http://47.93.222.102:9501/statistics/epNumStat_day";
        String queryUrl_epNum_week = "http://47.93.222.102:9501/statistics/epNumStat_week";
        String queryUrl_epNum_month = "http://47.93.222.102:9501/statistics/epNumStat_month";
        HttpResponse queRes_epNum_day = HttpRequest.get(queryUrl_epNum_day)
                .header(Header.AUTHORIZATION, "62a843bfd1b3d61005520385")
                .execute();
        HttpResponse queRes_epNum_week = HttpRequest.get(queryUrl_epNum_week)
                .header(Header.AUTHORIZATION, "62a843bfd1b3d61005520385")
                .execute();
        HttpResponse queRes_epNum_month = HttpRequest.get(queryUrl_epNum_month)
                .header(Header.AUTHORIZATION, "62a843bfd1b3d61005520385")
                .execute();

        JSONObject queryJsonRes_epNum_day = JSON.parseObject(queRes_epNum_day.body());
        JSONObject queryJsonRes_epNum_week = JSON.parseObject(queRes_epNum_week.body());
        JSONObject queryJsonRes_epNum_month  = JSON.parseObject(queRes_epNum_month.body());
        JSONObject makeJson_epNum = new JSONObject();
        String[] headerNames_epNum = new String[]{ "金牛区备案日统计", "本日增加","金牛区备案周统计","本周增加","金牛区备案月统计","本月增加"};
        String[] dataNames_epNum = new String[]{"day","day_increase", "week", "week_increase","month","month_increase"};


        makeJson_epNum.put("day",queryJsonRes_epNum_day.getJSONArray("list").getJSONObject(0).get("total"));
        makeJson_epNum.put("day_increase",queryJsonRes_epNum_day.getJSONArray("list").getJSONObject(0).get("todayNum"));
        makeJson_epNum.put("week",queryJsonRes_epNum_week.getJSONArray("list").getJSONObject(0).get("total"));
        makeJson_epNum.put("week_increase",queryJsonRes_epNum_week.getJSONArray("list").getJSONObject(0).get("todayNum"));

        makeJson_epNum.put("month",queryJsonRes_epNum_month.getJSONArray("list").getJSONObject(0).get("total"));
        makeJson_epNum.put("month_increase",queryJsonRes_epNum_month.getJSONArray("list").getJSONObject(0).get("todayNum"));

        data3.add(makeJson_epNum);

        JSONArray data4 = new JSONArray();
        String queryUrl_caseNum_day = "http://47.93.222.102:9501/statistics/caseNumStatistics_day";
        String queryUrl_caseNum_week = "http://47.93.222.102:9501/statistics/caseNumStatistics_week";
        String queryUrl_caseNum_month = "http://47.93.222.102:9501/statistics/caseNumStatistics_month";
        HttpResponse queRes_caseNum_day = HttpRequest.get(queryUrl_caseNum_day)
                .header(Header.AUTHORIZATION, "62a843bfd1b3d61005520385")
                .execute();
        HttpResponse queRes_caseNum_week = HttpRequest.get(queryUrl_caseNum_week)
                .header(Header.AUTHORIZATION, "62a843bfd1b3d61005520385")
                .execute();
        HttpResponse queRes_caseNum_month = HttpRequest.get(queryUrl_caseNum_month)
                .header(Header.AUTHORIZATION, "62a843bfd1b3d61005520385")
                .execute();

        JSONObject queryJsonRes_caseNum_day = JSON.parseObject(queRes_caseNum_day.body());
        JSONObject queryJsonRes_caseNum_week = JSON.parseObject(queRes_caseNum_week.body());
        JSONObject queryJsonRes_caseNum_month  = JSON.parseObject(queRes_caseNum_month.body());
        JSONObject makeJson_caseNum = new JSONObject();
        String[] headerNames_caseNum = new String[]{ "金牛区案件日统计", "本日未结案","金牛区案件周统计","本周未结案","金牛区案件月统计","本月未结案"};
        String[] dataNames_caseNum = new String[]{"day","day_no_finish", "week", "week_no_finish","month","month_no_finish"};


        makeJson_caseNum.put("day",queryJsonRes_caseNum_day.getJSONArray("list").getJSONObject(0).get("total"));
        makeJson_caseNum.put("day_no_finish",queryJsonRes_caseNum_day.getJSONArray("list").getJSONObject(0).get("No_finist_Tem"));
        makeJson_caseNum.put("week",queryJsonRes_caseNum_week.getJSONArray("list").getJSONObject(0).get("total"));
        makeJson_caseNum.put("week_no_finish",queryJsonRes_caseNum_week.getJSONArray("list").getJSONObject(0).get("No_finist_Tem"));

        makeJson_caseNum.put("month",queryJsonRes_caseNum_month.getJSONArray("list").getJSONObject(0).get("total"));
        makeJson_caseNum.put("month_no_finish",queryJsonRes_caseNum_month.getJSONArray("list").getJSONObject(0).get("No_finist_Tem"));

        data4.add(makeJson_caseNum);

        table.add(new Table(headerNames_over,dataNames_over,data));
        table.add(new Table(headerNames_monitor,dataNames_monitor,data2));
        table.add(new Table(headerNames_epNum,dataNames_epNum,data3));
        table.add(new Table(headerNames_caseNum,dataNames_caseNum,data4));

        System.out.println("request:yyxt/getStatics"+time);
        return CommonResult.success(table);
    }
    @RequestMapping("/getCase")
    public CommonResult<List<Table>> getCase(){
        String queryUrl_letter = "http://47.93.222.102:9501/case/totalLetterCaseNum";
        HashMap<String, Object> queryParam = new HashMap<>();
        HttpResponse queRes_letter = HttpRequest.get(queryUrl_letter)
                .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                .form(queryParam).execute();
        // System.out.println(queRes.body());
        JSONObject queryJsonRes_lettter = new JSONObject();
        queryJsonRes_lettter = JSON.parseObject(queRes_letter.body()).getJSONObject("list");
        JSONObject makeJson_letter = new JSONObject();
        List<Table> table = new ArrayList<>();
        JSONArray data0 = new JSONArray();
        String[] headerNames_letter = new String[]{ "未处理投诉案件", "处理中投诉案件","已结案投诉案件","投诉案件结案率"};
        String[] dataNames_letter = new String[]{"todo","onProcess", "done","rate"};

        makeJson_letter.put("todo",queryJsonRes_lettter.get("case_status1"));
        makeJson_letter.put("onProcess",queryJsonRes_lettter.get("case_status2"));
        makeJson_letter.put("done",queryJsonRes_lettter.get("case_status3"));
        makeJson_letter.put("rate",queryJsonRes_lettter.getIntValue("case_status3")*100/queryJsonRes_lettter.getFloatValue("case_all")+"%");
        data0.add(makeJson_letter);
        table.add(new Table(headerNames_letter,dataNames_letter,data0));

        String queryUrl_inspect = "http://47.93.222.102:9501/case/totalInspectCaseNum";
        HashMap<String, Object> queryParam_inspect = new HashMap<>();
        HttpResponse queRes_inspect = HttpRequest.get(queryUrl_inspect)
                .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                .form(queryParam_inspect).execute();
        // System.out.println(queRes.body());
        JSONObject queryJsonRes_inspect = new JSONObject();
        queryJsonRes_inspect = JSON.parseObject(queRes_inspect.body()).getJSONObject("list");
        JSONObject makeJson_inspect = new JSONObject();
        JSONArray data1 = new JSONArray();
        String[] headerNames_inspect = new String[]{ "未处理巡检案件", "处理中巡检案件","已结案巡检案件","巡检案件结案率"};
        String[] dataNames_inspect= new String[]{"todo","onProcess", "done","rate"};

        makeJson_inspect.put("todo",queryJsonRes_inspect.get("case_status1"));
        makeJson_inspect.put("onProcess",queryJsonRes_inspect.get("case_status2"));
        makeJson_inspect.put("done",queryJsonRes_inspect.get("case_status3"));
        makeJson_inspect.put("rate",queryJsonRes_inspect.getIntValue("case_status3")*100/queryJsonRes_inspect.getFloatValue("case_all")+"%");

        data1.add(makeJson_inspect);
        table.add(new Table(headerNames_inspect,dataNames_inspect,data1));
        System.out.println("request:yyxt/getCase"+time);
        return CommonResult.success(table);
    }
    @RequestMapping("/getMapDataYyxt")
    public CommonResult<JSONArray> getMapData(String areaID){
        String queryUrl = "http://47.93.222.102:9501/companies/list";
        //page=1&limit=10&scale=&devices_id=&monitoring_status=&company_name=&area_id=&level=
/*
        街道查询顺序，由于要求，首先为抚琴32000，营门口38000，剩下的依次按街道查询
*/      String[] streetId = new String[]{"510106032000","510106038000","510106024000","510106025000","510106027000","510106030000" ,"510106031000","510106035000","510106036000","510106040000","510106041000","510106042000","510106043000"};

        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("page","1");
        queryParam.put("limit","10");
        queryParam.put("scale","");
        queryParam.put("devices_id","");
        queryParam.put("monitoring_status","");
        queryParam.put("company_name","");
        queryParam.put("area_id","510106032000");
        queryParam.put("level","4");
        JSONArray data = new JSONArray();
        for(int i =0;i<streetId.length;i++)
        {   queryParam.replace("area_id",streetId[i]);
            queryParam.replace("monitoring_status",""); //全部
            HttpResponse queRes = HttpRequest.get(queryUrl)
                    .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                    .form(queryParam).execute();
            JSONObject queryJsonRes = JSON.parseObject(queRes.body());
            JSONObject makeJson = new JSONObject();
            makeJson.put("totalCount_all",queryJsonRes.get("totalCount"));


            queryParam.replace("monitoring_status","1"); //正常
            queRes = HttpRequest.get(queryUrl)
                    .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                    .form(queryParam).execute();

            queryJsonRes = JSON.parseObject(queRes.body());
            makeJson.put("totalCount_normal",queryJsonRes.get("totalCount"));


            //System.out.println(queRes.body());

            queryParam.replace("monitoring_status","0"); // 离线
            queRes = HttpRequest.get(queryUrl)
                    .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                    .form(queryParam).execute();
            JSONObject makeJson0 = new JSONObject();
            queryJsonRes = JSON.parseObject(queRes.body());

            makeJson.put("totalCount_off",queryJsonRes.get("totalCount"));





            queryParam.replace("monitoring_status","3"); //预警
            queRes = HttpRequest.get(queryUrl)
                    .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                    .form(queryParam).execute();

            queryJsonRes = JSON.parseObject(queRes.body());
            makeJson.put("totalCount_warning",queryJsonRes.get("totalCount"));

            queryParam.replace("monitoring_status","2"); //超标
            queRes = HttpRequest.get(queryUrl)
                    .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                    .form(queryParam).execute();

            queryJsonRes = JSON.parseObject(queRes.body());

            makeJson.put("totalCount_over",queryJsonRes.get("totalCount"));

//超标企业需要再细分，返回详细信息
            JSONArray queryArray = queryJsonRes.getJSONArray("list");
            makeJson.put("company_over",queryArray);
            data.add(makeJson);

        }


        return CommonResult.success(data);
    }
    @RequestMapping("/test")
    public CommonResult<JSONArray> getTest(String areaID){
        String queryUrl = "http://47.93.222.102:9501/companies/list";
        //page=1&limit=10&scale=&devices_id=&monitoring_status=&company_name=&area_id=&level=
/*
        街道查询顺序，由于要求，首先为抚琴32000，营门口38000，剩下的依次按街道查询
*/      String[] streetId = new String[]{"510106032000","510106038000","510106024000","510106025000","510106027000","510106030000" ,"510106031000","510106035000","510106036000","510106040000","510106041000","510106042000","510106043000"};

        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("page","1");
        queryParam.put("limit","10");
        queryParam.put("scale","");
        queryParam.put("devices_id","");
        queryParam.put("monitoring_status","1");
        queryParam.put("company_name","");
        queryParam.put("area_id","510106032000");
        queryParam.put("level","4");
        JSONArray data = new JSONArray();
        for(int i =0;i<streetId.length;i++)
        {   queryParam.replace("area_id",streetId[i]);
            queryParam.replace("monitoring_status",""); //全部
            HttpResponse queRes = HttpRequest.get(queryUrl)
                    .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                    .form(queryParam).execute();
            JSONObject queryJsonRes = JSON.parseObject(queRes.body());
            JSONObject makeJson = new JSONObject();
            makeJson.put("totalCount_all",queryJsonRes.get("totalCount"));


            queryParam.replace("monitoring_status","1"); //正常
            queRes = HttpRequest.get(queryUrl)
                    .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                    .form(queryParam).execute();

            queryJsonRes = JSON.parseObject(queRes.body());
            makeJson.put("totalCount_normal",queryJsonRes.get("totalCount"));


            //System.out.println(queRes.body());

            queryParam.replace("monitoring_status","0"); //离线
            queRes = HttpRequest.get(queryUrl)
                    .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                    .form(queryParam).execute();
            JSONObject makeJson0 = new JSONObject();
            queryJsonRes = JSON.parseObject(queRes.body());

            makeJson.put("totalCount_off",queryJsonRes.get("totalCount"));
            queryParam.replace("monitoring_status","3"); //预警
            queRes = HttpRequest.get(queryUrl)
                    .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                    .form(queryParam).execute();

            queryJsonRes = JSON.parseObject(queRes.body());
            makeJson.put("totalCount_warning",queryJsonRes.get("totalCount"));

            queryParam.replace("monitoring_status","2"); // 超标
            queRes = HttpRequest.get(queryUrl)
                    .header(Header.AUTHORIZATION,"62a843bfd1b3d61005520385")
                    .form(queryParam).execute();

            queryJsonRes = JSON.parseObject(queRes.body());

            makeJson.put("totalCount_over",queryJsonRes.get("totalCount"));
            //超标企业需要再细分，返回详细信息
            JSONArray queryArray = queryJsonRes.getJSONArray("list");
            makeJson.put("company_over",queryArray);





            data.add(makeJson);


        }


        return CommonResult.success(data);
    }

}
