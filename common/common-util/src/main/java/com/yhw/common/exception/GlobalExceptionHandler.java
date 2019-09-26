package com.yhw.common.exception;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

    /**
     * spring  RequestParameter 异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public JSONObject missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException exception){
        /*
        //HttpServletResponse response
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        //System.out.println(exception.getMessage());
        JSONObject jsonObject = JSONUtil.parseObj(ApiResult.getBuilder()
                .setMessage("必填参数：[".concat(exception.getParameterName()).concat("]为空"))
                .setCode(400)
                .setSuccess(false)
                .builder());
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "400");
        jsonObject.put("success", false);
        jsonObject.put("data", "");
        jsonObject.put("message", "必填参数：[".concat(exception.getParameterName()).concat("]为空"));
        return jsonObject;
    }


    //其他异常


    //500异常

}