package com.yunbyte.dbtest.exception;

import com.yunbyte.dbtest.dao.TransLog;

/**
 * 全局异常类
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-3
 */
public class TransException extends RuntimeException {
    private String code;
    private TransLog[] transLog;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TransLog[] getTransLog() {
        return transLog;
    }

    public void setTransLog(TransLog... transLog) {
        this.transLog = transLog;
    }

    public TransException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public TransException(String code, String msg, TransLog... transLog) {
        super(msg);
        this.code = code;
        this.transLog = transLog;
    }
}
