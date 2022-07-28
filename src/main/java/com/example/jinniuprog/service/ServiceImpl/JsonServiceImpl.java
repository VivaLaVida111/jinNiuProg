package com.example.jinniuprog.service.ServiceImpl;

import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.example.jinniuprog.service.JsonService;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;



import java.io.*;
import java.nio.charset.Charset;

@Service("JsonService")
public class JsonServiceImpl implements JsonService {

    @Override
    public JSONObject handleJson(int deptId,int systemId, JSONArray data) {

        //todo
        JSONArray systems = new JSONArray();
        JSONObject resultJson = new JSONObject();
        if(deptId==0) {
            resultJson.put("deptId", deptId);
            resultJson.put("deptLogo", "cgzf.jpg");
            resultJson.put("deptName", "调度指挥");}
        else if (deptId==1){
            resultJson.put("deptId", deptId);
            resultJson.put("deptLogo", "cgzf.jpg");
            resultJson.put("deptName", "政策法规");
        }
        else if (deptId==2){
            resultJson.put("deptId", deptId);
            resultJson.put("deptLogo", "cgzf.jpg");
            resultJson.put("deptName", "城市环境综合治理");
        }else if (deptId==3){
            resultJson.put("deptId", deptId);
            resultJson.put("deptLogo", "cgzf.jpg");
            resultJson.put("deptName", "环境卫生监督管理");
        }else if (deptId==4){
            resultJson.put("deptId", deptId);
            resultJson.put("deptLogo", "cgzf.jpg");
            resultJson.put("deptName", "广告招牌和景观照明管理");
        }else if (deptId==5){
            resultJson.put("deptId", deptId);
            resultJson.put("deptLogo", "cgzf.jpg");
            resultJson.put("deptName", "城市管理综合执法");
        }
        else if (deptId==6){
            resultJson.put("deptId", deptId);
            resultJson.put("deptLogo", "cgzf.jpg");
            resultJson.put("deptName", "数字化城市管理信息系统");
        }

            JSONObject makeJson = new JSONObject();
            makeJson.put("systemId", systemId);
            makeJson.put("systemLogo", "cgzf.jpg");
            makeJson.put("systemName", "餐饮油烟监测服务");
            makeJson.put("to", "");
            makeJson.put("data", data);
            systems.add(makeJson);
            resultJson.put("systems", systems);

        return resultJson;
    }

    @Override
    public JSONArray parseJson(String url) {

        JSONObject jsonObject = new JSONObject();
        StringBuilder sb = new StringBuilder();
        //ClassPathResource classPathResource = new ClassPathResource("static/api.json");

        InputStream in =getClass().getClassLoader().getResourceAsStream(url);
        //File file = classPathResource.getFile();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(sb.toString());
        JSONArray jsonArray= new JSONArray();
        jsonArray = JSON.parseArray(sb.toString());


        return jsonArray;

    }


}
