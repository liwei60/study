package com.lenovo.service;


import com.lenovo.bean.User;
import com.lenovo.mapper.HelloMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    private final static Log logger = LogFactory.getLog(HelloService.class);

//    @Autowired
//    private HelloMapper helloMapper;
    @Autowired
    private HelloMapper helloMapper;

    public void addHelloUser(User user) {

        try {
            helloMapper.addHelloInfo(user);
            logger.info("HelloService    addHelloUser: user" + user);
        } catch (Exception e) {
            logger.error("HelloService    addHelloUser   error :" + e);
        }

    }

}
