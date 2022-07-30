package com.example.springboot_rabbitmq.Configurtion;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 高级发布确认
 */

@Configuration
public class ConfirmConfig {

    //声明队列
    public static final String CONFIRMQUEEN = "confirmQueen";

    //声明交换机
    public static final String CONFIRMEXCHANGE = "confirmExchange";

    //routingKey
    public static final String CONFIRMROUTINGKEY = "confirmRountingKey";


    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(CONFIRMEXCHANGE);
    }

    @Bean
    public Queue queue(){
        return new Queue(CONFIRMQUEEN);
    }

    @Bean
    public Binding bingExtoQue(@Qualifier("directExchange") DirectExchange directExchange,@Qualifier("queue") Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with(CONFIRMROUTINGKEY);
    }
}
