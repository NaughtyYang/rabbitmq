package com.example.springboot_rabbitmq.controller;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerController {

    //接受消息
    @RabbitListener(queues = "QD")
    public void revice(Message message, Channel channel){
        String msg = new String(message.getBody());
        log.info("收到消息"+msg);
    }
}
