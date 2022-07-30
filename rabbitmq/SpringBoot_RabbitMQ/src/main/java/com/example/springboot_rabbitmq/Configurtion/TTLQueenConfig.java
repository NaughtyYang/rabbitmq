package com.example.springboot_rabbitmq.Configurtion;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

//配置类
@Configuration
public class TTLQueenConfig {
    //声明队列
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    //交换机
    public static final String X_EXCHANGE = "X";
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    //死信队列
    public static final String DEAD_LETTER_QUEUE = "QD";


    //声明交换机
    @Bean
    public DirectExchange XExchange(){
        return new DirectExchange(X_EXCHANGE);
    }
    @Bean
    public DirectExchange YExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    //声明队列A
    @Bean
    public Queue queueA(){
        Map<String,Object> map = new HashMap<>();
        //声明当前队列绑定的死信交换机
        map.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //声明当前队列的死信路由 key
        map.put("x-dead-letter-routing-key", "YD");
        //声明队列的 TTL
        map.put("x-message-ttl", 10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(map).build();
    }

    //声明队列B
    @Bean
    public Queue queueB(){
        Map<String,Object> map = new HashMap<>();
        //声明当前队列绑定的死信交换机
        map.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //声明当前队列的死信路由 key
        map.put("x-dead-letter-routing-key", "YD");
        //声明队列的 TTL
        map.put("x-message-ttl", 40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(map).build();
    }

    //声明死信队列
    @Bean("queenD")
    public Queue queueDead(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    //queenA绑定X交换机
    @Bean
    public Binding queueABingXExchange(@Qualifier("queueA") Queue queueA,@Qualifier("XExchange") DirectExchange XExchange){
        return BindingBuilder.bind(queueA).to(XExchange).with("XA");
    }

    //queenB绑定X交换机
    @Bean
    public Binding queueBBingXExchange(@Qualifier("queueB") Queue queueB,@Qualifier("XExchange") DirectExchange XExchange){
        return BindingBuilder.bind(queueB).to(XExchange).with("XB");
    }

    //queenA绑定Y交换机
    @Bean
    public Binding queueABingYExchange(@Qualifier("queueA") Queue queueA,@Qualifier("YExchange") DirectExchange YExchange){
        return BindingBuilder.bind(queueA).to(YExchange).with("YD");
    }

    //queenB绑定Y交换机
    @Bean
    public Binding queueBBingYExchange(@Qualifier("queueB") Queue queueB,@Qualifier("YExchange") DirectExchange YExchange){
        return BindingBuilder.bind(queueB).to(YExchange).with("YD");
    }

    //queenD绑定Y交换机
    @Bean
    public Binding queueDBingYExchange(@Qualifier("queenD") Queue queenD,@Qualifier("YExchange") DirectExchange YExchange){
        return BindingBuilder.bind(queenD).to(YExchange).with("YD");
    }
}
