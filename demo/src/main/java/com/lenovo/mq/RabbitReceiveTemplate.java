package com.lenovo.mq;

import com.lenovo.util.Constants;
import com.rabbitmq.client.Channel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


public abstract class RabbitReceiveTemplate extends SimpleMessageListenerContainer {

    private final Log logger = LogFactory.getLog(this.getClass());



    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired
    private RabbitSenderTemplate rabbitSenderTemplate;

    abstract public Queue getQueue();

    abstract public boolean doOnMessage(Message message) throws Exception;

    @PostConstruct
    public void detailMessageContainer() {
        this.setConnectionFactory(rabbitConfig.connectionFactory());
        this.setQueues(getQueue());
        this.setExposeListenerChannel(true);
        this.setMaxConcurrentConsumers(1);
        this.setConcurrentConsumers(1);
        //MANUAL 为手动确认模式
        this.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        ChannelAwareMessageListener channelAwareMessageListener = (Message message, Channel channel)-> {
            boolean flag = false;
            try{
            logger.info(this.getClass().getSimpleName()+" start  message : "+message);
            //处理消息
            flag = doOnMessage(message);
            logger.info(this.getClass().getSimpleName() +" end  message : "+message);
            }catch(Exception e){
                logger.error("deal fail  error :"+ e +"   message  :" +message);
            }finally {
                if(flag){
                    //deliveryTag 消息ID
                    //false 为只确认当前消息  true 表示确认Consumer的所有消息
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
                }else{
                    //multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
                    //requeue：被拒绝的是否重新入队列
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
                    logger.info("message  to  fail queue :  "+message);
                    rabbitSenderTemplate.send(Constants.MQ_DATA_PREPROCESS_FAIL_KEY,message);
                }
            }

        };
        this.setMessageListener(channelAwareMessageListener);
    }

}
