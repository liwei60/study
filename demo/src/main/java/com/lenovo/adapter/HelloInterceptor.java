package com.lenovo.adapter;

import com.lenovo.exception.BusinessException;
import com.lenovo.service.RedisService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class HelloInterceptor extends HandlerInterceptorAdapter {
    private final static Log logger = LogFactory.getLog(HelloInterceptor.class);
    //@Autowired
    //private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Token annotation = handlerMethod.getMethod().getAnnotation(Token.class);

            if(annotation!=null){
                boolean needValue = annotation.value();
                if(needValue){
                    //redisService.set("token", UUID.randomUUID().toString());
                    return true;
                }
            }else{
                logger.info("HelloInterceptor  preHandle  annotation is null");
                throw new BusinessException("验证错误,没有token！",500);
            }
        }
        return false;
    }
}
