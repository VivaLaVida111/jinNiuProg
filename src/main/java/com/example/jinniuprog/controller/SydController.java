package com.example.jinniuprog.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.jinniuprog.common.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/syd")
public class SydController {
    @RequestMapping("/getLoginToken")

    public CommonResult<String> getLoginToken(){
        JSONObject loginJson = new JSONObject();
        HashMap<String, Object> loginParam = new HashMap<>();
        loginParam.put("user_id",357);
        String loginUrl = "https://www.jncgsqbl.com/api/v4/user_login_token?user_id=357";
        HttpResponse loginRes = HttpRequest.post(loginUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.CONTENT_LENGTH, "225")
                .header(Header.AUTHORIZATION,"5e71e47ff99f7db80511171be5d0c749eff6379f633ce2b6dcaccc05e04f4698:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lc3BhY2VfaWQiOjF9.bkwVGEABLpkew7mIm4JPBapv6VmUFPa4ZzIJjdyYoU4")
                .form(loginParam)
                .execute();

        JSONObject queryJsonRes = JSON.parseObject(loginRes.body());
        String token = queryJsonRes.getString("user_login_token");
    return CommonResult.success(token);
    }
}
