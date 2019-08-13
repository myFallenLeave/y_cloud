package com.yhw.common.exception;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

//如果返回的为json数据或其它对象，添加该注解
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


    //添加全局异常处理流程，根据需要设置需要处理的异常，本文以MethodArgumentNotValidException为例
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JSONObject MethodArgumentNotValidHandler(HttpServletRequest request,
                                                MethodArgumentNotValidException exception) throws Exception {
        //按需重新封装需要返回的错误信息
        JSONArray jsonArray = new JSONArray();
        JSONObject errorJSON = null;
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorJSON = new JSONObject();
            errorJSON.put("defaultMessage",fieldError.getDefaultMessage());
            errorJSON.put("field",fieldError.getField());
            errorJSON.put("rejectedValue",fieldError.getRejectedValue());
            jsonArray.add(errorJSON);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "400");
        jsonObject.put("error", errorJSON);
        jsonObject.put("data", "");
        jsonObject.put("message", "400");
        return jsonObject;
    }

    //认证异常
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public JSONObject httpMessageNotReadableExceptionHandler(){

        return null;
    }


    //其他异常


    //500异常

}