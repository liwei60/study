package com.lenovo.mq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public static final String EXCHANGE_NAME = "manualCheckExchange";

    /**
     * 创建工厂连接
     *
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setPort(Integer.parseInt(port));
        connectionFactory.setAddresses(this.host);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    //rabbitmq的模板配置
    @Bean
    public RabbitTemplate rabbitTemplate() {
        //confirm模式下,broker将会确认消息并处理。这种模式下是异步的，生产者可以流水式的发布而不用等待broker，broker可以批量的往磁盘写入
        RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        rabbitTemplate.setExchange(EXCHANGE_NAME);

        //确认消息是否到达broker服务器，也就是只确认是否正确到达exchange中即可，只要正确的到达exchange中，broker即可确认该消息返回给客户端ack。
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(this::confirm);
        rabbitTemplate.setReturnCallback(this::returnedMessage);

        return rabbitTemplate;
    }


    // 实现ReturnCallback
    // 当消息发送出去找不到对应路由队列时，将会把消息退回
    // 如果有任何一个路由队列接收投递消息成功，则不会退回消息
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("RabbitTemplate returnedMessage: " + new String(message.getBody()) + " replyCode: " + replyCode
                + " replyText: " + replyText + " exchange: " + exchange + " routingKey: " + routingKey);
    }

    // 实现ConfirmCallback
    // ACK=true仅仅标示消息已被Broker接收到，并不表示已成功投放至消息队列中
    // ACK=false标示消息由于Broker处理错误，消息并未处理成功
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            logger.info("RabbitTemplate 消息确认成功 correlationData: " + correlationData + " ack: " + ack + " cause: " + cause);
        } else {
            logger.info("RabbitTemplate 消息确认失败 correlationData: " + correlationData + " ack: " + ack + " cause: " + cause);
        }
    }
}