package com.lenovo.service;

import com.lenovo.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestMongoService {

    @Value("${mongo.collectionName}")
    private String collectionName;

    @Autowired
    MongoService mongoService;

    public List<User> getUsers(Map<String,String> queryMap){
        Query query = new Query();
        return mongoService.find(query,User.class,collectionName);
    }
}
