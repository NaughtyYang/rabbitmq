package com.example.springboot_rabbitmq.controller;

import com.example.springboot_rabbitmq.Configurtion.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/ttl")
@Slf4j
public class ProductController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/sendMessage/{message}",method = RequestMethod.GET)
    public void putMessage(@PathVariable(value = "message") String message){
        log.info("当前时间{},发送消息给两个队列，{}",new Date().toString(),message);
        rabbitTemplate.convertAndSend("X","XA","消息来自ttl10秒的消息队列" + message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl40秒的消息队列" + message);
    }


    @GetMapping("/send/{message}")
    public void sendMessage(@PathVariable String message){
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRMEXCHANGE,ConfirmConfig.CONFIRMROUTINGKEY,message);
    }
}
