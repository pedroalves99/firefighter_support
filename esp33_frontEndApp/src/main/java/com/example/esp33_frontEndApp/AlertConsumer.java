package com.example.esp33_frontEndApp;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;



@Component
public class AlertConsumer {



    private static final String TOPIC = "ESP33_Alerts";


    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

   

    @KafkaListener(topics = TOPIC)
    public void messageListener(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {

        String key = consumerRecord.key();
        String value = consumerRecord.value();
        int partition = consumerRecord.partition();

        System.out.println("Consumed message : value " + value+" key: " + key);
        ack.acknowledge();

    }

}