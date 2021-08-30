//package com.med.services;
//
//import com.med.model.MedRangeInfo;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class MqConsumerService {
//
//    final MedRangeProcessor processor;
//
//    public MqConsumerService(MedRangeProcessor processor) {
//        this.processor = processor;
//    }
//
//    @RabbitListener(queues = "${consumer.rabbitmq.queue}")
//    public void receiveMessage(MedRangeInfo medRange) {
//        System.out.println("Consumer app has Received Message From RabbitMQ: " + medRange);
//
//        processor.processMedRange(medRange);
//    }
//}
