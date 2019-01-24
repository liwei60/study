package com.lenovo.controller;

import com.alibaba.fastjson.JSONObject;
import com.lenovo.adapter.Token;
import com.lenovo.bean.User;
import com.lenovo.service.HelloService;
import com.lenovo.service.RedisService;
import com.lenovo.util.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    private final static Log logger = LogFactory.getLog(HelloController.class);

    //@Autowired
    //RedisService redisService;

    @Autowired
    HelloService helloService;


    @RequestMapping(value = "/hello",method = RequestMethod.POST)
   // @Token(value = true)
    public JSONObject hello(@RequestBody User user){
        JSONObject rs = new JSONObject();
        try {
            logger.debug("HelloController  debug  hello cond :" +user);
            logger.info("HelloController  info  hello cond :" +user);
            helloService.addHelloUser(user);
            rs.put(Constants.STATE,Constants.SUCCESS);
        }catch(Exception e){
            logger.error("HelloController    hello :" +e);
            rs.put(Constants.STATE,Constants.FALSE);
        }
        return rs;
    }
    @RequestMapping("/check")
    @Token(true)
    public String check(String a){
        logger.info("HelloController   check  "+a);
        return "index.html";
    }
}
