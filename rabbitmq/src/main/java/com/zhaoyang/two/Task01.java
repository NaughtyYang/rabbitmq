package com.zhaoyang.two;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.zhaoyang.util.Connection;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Task01 {
    public static final String QUEEN_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = Connection.getChannel();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            String message = scanner.next();

            channel.queueDeclare(QUEEN_NAME,true,false,false,null);

            channel.basicPublish("",QUEEN_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());

            System.out.println("发送 消息：" + message);
        }
    }
}
