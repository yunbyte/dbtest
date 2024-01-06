package com.yunbyte.dbtest.dto;

import java.util.List;

import com.yunbyte.dbtest.dao.TransLog;

/**
 * 转账操作响应实体封装类
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-3
 */
public class ResponseInfo {

    private String code;
    private String message;
    private long useTime;
    private List<TransLog> list;

    public ResponseInfo() {
    }

    public ResponseInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseInfo(String code, String message, List<TransLog> list) {
        this.code = code;
        this.message = message;
        this.list = list;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public long getUseTime() {
        return useTime;
    }

    public List<TransLog> getList() {
        return list;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public void setList(List<TransLog> list) {
        this.list = list;
    }
}
