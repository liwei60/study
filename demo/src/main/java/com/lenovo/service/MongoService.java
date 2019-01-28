package com.lenovo.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MongoService<T> {
    private final static Log logger = LogFactory.getLog(MongoService.class);

    @Autowired
    public MongoTemplate  mongoTemplate;

    public void  insert(Object object,String collectionName){
        mongoTemplate.save(object,collectionName);
    }

    public List<T>  findAll(Class<T> entityClass){
        return (List<T>)mongoTemplate.findAll(entityClass);
    }

    public List<T> find(Query query, Class<T> entityClass, String collectionName){
        return (List<T>)mongoTemplate.find(query,entityClass,collectionName);
    }
    public void update(Query query, Update update, Class<T> entityClass,String collectionName){
        mongoTemplate.updateMulti(query,update,entityClass,collectionName);
    }
    public void update(Query query, Update update, String collectionName){
        mongoTemplate.updateMulti(query,update,collectionName);
    }
    public void delete(Query query ,Class<T> entityClass,String collectionName){
        mongoTemplate.remove(query,entityClass,collectionName);
    }
    public void delete(Object object,String collectionName){
        mongoTemplate.remove(object,collectionName);
    }

}
