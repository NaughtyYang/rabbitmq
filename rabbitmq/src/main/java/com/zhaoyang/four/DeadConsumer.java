package com.zhaoyang.four;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.zhaoyang.util.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 死信队列消费者
 *
 */

public class DeadConsumer {

    //正常交换机
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    //死信交换机
    public static final String DEAD_EXCHANGE = "dead_exchange";
    //正常队列名
    public static final String NORMAL_QUEEN = "normal_QUEEN";
    //死信队列名
    public static final String DEAD_QUEEN = "dead_QUEEN";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = Connection.getChannel();

        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        map.put("x-dead-letter-routing-key","lisi");
        map.put("x-max-length",6);

        //声明交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);

        //声明队列
        channel.queueDeclare(NORMAL_QUEEN,false,false,false,map);
        channel.queueDeclare(DEAD_QUEEN,false,false,false,null);

        //交换机，队列，routingkey绑定
        channel.queueBind(NORMAL_QUEEN,NORMAL_EXCHANGE,"zhangsan");
        channel.queueBind(DEAD_QUEEN,DEAD_EXCHANGE,"lisi");

        DeliverCallback callback = (s,delivery) ->{
            String message = new String(delivery.getBody(),"utf-8");

            //拒绝该消息
            if(s.equals("赵洋")){
                System.out.println("拒绝该消息");
                channel.basicReject(delivery.getEnvelope().getDeliveryTag(),false);
            }else{
                System.out.println("其他的正常接受");
            }
        };

        channel.basicConsume(NORMAL_QUEEN,false,callback,(can) -> {});
    }

}
