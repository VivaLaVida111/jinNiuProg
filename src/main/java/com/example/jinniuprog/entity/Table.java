package com.example.jinniuprog.entity;


import com.alibaba.fastjson2.JSONArray;

public class Table {
    private String headerNames[];
    private String dataNames[];
    private JSONArray data;

    public Table() {

    }

    public Table(String[] headerNames, String[] dataNames, JSONArray data) {
        this.headerNames = headerNames;
        this.dataNames = dataNames;
        this.data = data;
    }

    public String[] getHeaderNames() {
        return headerNames;
    }

    public void setHeaderNames(String[] headerNames) {
        this.headerNames = headerNames;
    }

    public String[] getDataNames() {
        return dataNames;
    }

    public void setDataNames(String[] dataNames) {
        this.dataNames = dataNames;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }
}