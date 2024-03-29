package com.liujiajun.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *    全局异常处理器
 *    springmvc提供一个HandlerExceptionResolver接口
 *      只要实现该接口，并配置到spring 容器里，该类就能
 *      成为默认全局异常处理类
 *
 *   全局异常处理器只有一个，配置多个也没用。
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        ModelAndView modelAndView = new ModelAndView();

        CustomException customException;
        if (e instanceof CustomException) {
            customException = (CustomException)e;
        } else {
            customException = new CustomException("未知错误");
        }

        //错误信息
        String message = customException.getMessage();



        //错误信息传递和错误页面跳转
        modelAndView.addObject("message", message);
        modelAndView.setViewName("error");


        return modelAndView;
    }
}
