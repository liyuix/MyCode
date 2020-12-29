package com.liyuxi.mycode.controller;

import com.liyuxi.mycode.kafka.KafkaProducer;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/kafka")
@RestController
@Api(tags = {"开放接口"},value = "开放controller")
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping(value = "/send")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public void sendMsg(){
        kafkaProducer.send("this is a test kafka topic message!");
    }

}
