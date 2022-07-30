package com.zhaoyang.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.zhaoyang.util.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Work01 {

    public static final String QUEEN_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = Connection.getChannel();

        //不公平分发
        channel.basicQos(1);


        // 2往上代表 预取值
        channel.basicQos(2);
        //消费消息
        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println(new String(message.getBody()));
        };

        //取消接受消息
        CancelCallback cancelCallback = (message) ->{
            System.out.println(message);
        };

        channel.basicConsume(QUEEN_NAME,true,deliverCallback,cancelCallback);

    }
}
