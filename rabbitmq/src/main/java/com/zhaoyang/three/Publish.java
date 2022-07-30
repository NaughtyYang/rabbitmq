package com.zhaoyang.three;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.zhaoyang.util.Connection;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 *
 * 开启发布确认模式
 * 单个确认
 */

public class Publish {

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        Publish.publish();
    }


    //单个发布确认
    public static void publish() throws IOException, TimeoutException, InterruptedException {
        Channel channel = Connection.getChannel();

        //发送的消息设置过期时间
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties();
        basicProperties.builder().expiration("100000").build();

        //开启发布确认模式
        channel.confirmSelect();

        String s = UUID.randomUUID().toString();

        channel.queueDeclare(s,false,false,false,null);

        //开始时间
        long begin = System.currentTimeMillis();

        for(int i = 0;i <1000;i++){
            String message = i + "";
            channel.basicPublish("",s,basicProperties,message.getBytes());

            //等待确认
            boolean flag = channel.waitForConfirms();
            if(flag){
                System.out.println("消息确认成功");
            }
        }

        //结束时间
        long end = System.currentTimeMillis();
    }

    //批量发布确认
    public static void publishBatch() throws Exception {
        Channel channel = Connection.getChannel();

        //开启发布确认模式
        channel.confirmSelect();

        String s = UUID.randomUUID().toString();

        channel.queueDeclare(s,false,false,false,null);

        int batchSize = 100;

        //开始时间
        long begin = System.currentTimeMillis();

        for(int i = 0;i <1000;i++){
            String message = i + "";
            channel.basicPublish("",s,null,message.getBytes());

            if(i%batchSize == 0){
                //每100条等待确认
                channel.waitForConfirms(batchSize);
            }
        }

        //结束时间
        long end = System.currentTimeMillis();
    }

    //异步发布确认
    public static void publishAsyn() throws Exception {
        Channel channel = Connection.getChannel();
        String s = UUID.randomUUID().toString();
        channel.queueDeclare(s,false,false,false,null);
        //开启发布确认模式
        channel.confirmSelect();

        //消息确认成功回调函数
        ConfirmCallback successCallback = (var1,var3) ->{

        };
        //消息确认失败回调函数
        ConfirmCallback failCallback2 = (var1,var3) ->{

        };

        //准备消息监听器
        //一个参数表示监听成功的
        //两个参数表示监听成功与失败的
        channel.addConfirmListener(successCallback,failCallback2);

        //开始时间
        long begin = System.currentTimeMillis();

        for(int i = 0;i <1000;i++){
            String message = i + "";
            channel.basicPublish("",s,null,message.getBytes());
        }

        //结束时间
        long end = System.currentTimeMillis();
    }
}
