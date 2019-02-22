package com.lenovo.mq;

import com.lenovo.util.Constants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitExchangeConfig {

    @Bean("manualCheckExchange")
    Exchange manualCheckExchange(){
        return  new DirectExchange("manualCheckExchange",true,false);
    }

    /**
     * 预处理队列
     * @return
     */
    @Bean("dataPreprocessQueue")
    Queue dataPreprocessQueue(){
        return new Queue("dataPreprocessQueue",true,false,false);
    }

    @Bean
    public Binding exchangeBindingdataPreprocess(){
        return BindingBuilder.bind(dataPreprocessQueue()).to(manualCheckExchange()).with(Constants.MQ_DATA_PREPROCESS_KEY).noargs();
    }

    /**
     * 预处理失败，消费失败队列
     * @return
     */
    @Bean("dataPreprocessFailQueue")
    Queue dataPreprocessFailQueue(){
        return new Queue("dataPreprocessFailQueue",true,false,false);
    }

    public Binding exchangeBindingdataPreprocessFail(){
        return BindingBuilder.bind(dataPreprocessFailQueue()).to(manualCheckExchange()).with(Constants.MQ_DATA_PREPROCESS_FAIL_KEY).noargs();
    }

}
