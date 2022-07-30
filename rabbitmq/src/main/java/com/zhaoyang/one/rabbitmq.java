package com.zhaoyang.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * 生产者
 */
public class rabbitmq {

    public static final String QUEEN_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setUsername("zhaoyang");
        connectionFactory.setPassword("123456");

        //创建链接
        Connection connection = connectionFactory.newConnection();

        //拿到信道
        Channel channel = connection.createChannel();

        //创建队列
        /**
         * 1.第一个参数，队列名称
         * 2.第二个参数，队列里的消息是否需要持久化（默认消息存储在内存中）
         * 3.第三个参数，该队列是否是支持一个消费者消费（是否支持消息共享，默认不支持）
         * 4.第四个参数，最后一个客户端断开连接后是否自动删除
         * 5.第五个参数，其他参数（高级特性）
         */
        channel.queueDeclare(QUEEN_NAME,false,false,true,null);

        //发消息
        String message = "hello";

        /**
         * 第一个参数表示发送到哪个交换机
         * 第二个参数表示本次队列名
         * 第三个参数表示其他参数
         * 第四个参数表示消息体
         */
        channel.basicPublish("",QUEEN_NAME,null,message.getBytes());

    }
}
