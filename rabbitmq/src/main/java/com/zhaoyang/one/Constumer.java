package com.zhaoyang.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 *
 */

public class Constumer {
    public static final String QUEEN_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setUsername("zhaoyang");
        connectionFactory.setPassword("123456");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //消费消息
        DeliverCallback deliverCallback = (consumerTag,message) ->{
            System.out.println(message);
        };

        //取消接受消息
        CancelCallback cancelCallback = (message) ->{
            System.out.println(message);
        };

        /**
         * 1.第一个参数，队列名
         * 2.第二个参数，消费成功后是否自动应答，true自动，false手动
         * 3.第三个参数，消费者未成功消费的回调
         * 4.第四个参数，消费者取消消费的回调
         */
        channel.basicConsume(QUEEN_NAME,true,deliverCallback,cancelCallback);
    }
}
