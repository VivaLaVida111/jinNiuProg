package com.example.jinniuprog.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

@Service("JsonService")
public interface JsonService {
     JSONObject handleJson(int deptId,int systemId,JSONArray data);
     JSONArray parseJson(String url);
}
