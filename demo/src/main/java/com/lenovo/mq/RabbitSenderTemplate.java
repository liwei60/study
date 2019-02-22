package com.lenovo.mq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitSenderTemplate {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String routingKey,Object msg){

        CorrelationData data = new CorrelationData(UUID.randomUUID().toString());


        rabbitTemplate.convertAndSend(routingKey, msg,data);

        logger.info("RabbitSenderTemplate send  msg: " + msg + " routingKey: " + routingKey);
    }

}
