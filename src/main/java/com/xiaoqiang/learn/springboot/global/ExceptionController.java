package com.xiaoqiang.learn.springboot.global;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller异常处理类，所有Controller中未捕获的异常，都会在该类中寻找与异常类型相匹配的方法
 * ExceptionHandler 捕获到类型相匹配的异常，会进入相应的方法
 * 请求通过DispatcherServlet分发，到了doDispatcher()方法阶段，只有异常存在时，才会触发ExceptionHandler，前提是
 * 已经配置了ExceptionHandler处理器。
 * 404请求，不会抛出异常，当URL不存在时，还会HttpRequestHandlerAdapter去加载静态资源，所以不会被ExceptionHandler捕获
 * @author xiaoqiang
 * @date 2019/8/13 23:55
 */
@RestControllerAdvice
public class ExceptionController
{
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handlerRuntimeException(RuntimeException e)
    {
        e.printStackTrace();
        Map<String, Object> response = new HashMap<>();
        response.put("is_success", "false");
        response.put("error_msg", e.getMessage());
        throw new RuntimeException("");
//        return response;
    }

//    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handlerException(Exception e)
    {
        e.printStackTrace();
        Map<String, Object> response = new HashMap<>();
        response.put("is_success", false);
        response.put("error_msg", "Internal error.");
        return response;
    }
}
