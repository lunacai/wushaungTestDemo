package com.example.testsoftdemo.Controller;

import com.example.testsoftdemo.ons.MyMessageConsumer;
import com.example.testsoftdemo.ons.onsConfigParams;
import com.example.testsoftdemo.ons.onsProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/one")
public class onsController {

    @GetMapping("testmq")
    public String testmq(String msg) {
        onsProducer onsProducer = new onsProducer();
        String result = onsProducer.sendMessage(onsConfigParams.TOPIC, "*", msg);
        return result;
    }

//    @GetMapping("consumerMq")
//    public void consumerMq() {
//        MyMessageConsumer myMessageConsumer = new MyMessageConsumer();
//        myMessageConsumer.subscribe();
//    }
}
