package com.lenovo.controller;

import com.alibaba.fastjson.JSONObject;
import com.lenovo.adapter.Token;
import com.lenovo.bean.User;
import com.lenovo.service.HelloService;
import com.lenovo.service.TestMongoService;
import com.lenovo.util.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class HelloController {
    private final static Log logger = LogFactory.getLog(HelloController.class);

    //@Autowired
    //RedisService redisService;

    @Autowired
    HelloService helloService;
    @Autowired
    TestMongoService testMongoService;




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
    @RequestMapping("/save")
    public JSONObject save(@RequestBody User user){
        JSONObject rs = new JSONObject();
        try {
            rs.put(Constants.STATE,Constants.SUCCESS);
        }catch (Exception e){
            logger.error("HelloController    save :" +e);
            rs.put(Constants.STATE,Constants.FALSE);
        }
        return rs;
    }
    @RequestMapping("/find")
    public List<User> find(@RequestParam Map<String,String> map){
        logger.info("HelloController findAll  map:"+map.toString());
        List<User> users = testMongoService.getUsers(map);
        logger.info("HelloController findAll json:"+users.toString());
        return users;
    }
}
