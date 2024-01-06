package com.yunbyte.dbtest.error;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.yunbyte.dbtest.dto.ResponseInfo;
import com.yunbyte.dbtest.exception.TransException;

/**
 * 全局异常管理类
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-6
 */
@ControllerAdvice
public class TransExceptionHandler {

    @ExceptionHandler(value = TransException.class)
    public ResponseInfo exceptionHandler(TransException e) {
        return new ResponseInfo(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseInfo exceptionHandler(MethodArgumentNotValidException e) {
        return new ResponseInfo("1008", "非法参数异常！原因是:" + e.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseInfo exceptionHandler(Throwable e) {
        return new ResponseInfo("1010", "未知异常！原因是:" + e.getMessage());
    }
}
