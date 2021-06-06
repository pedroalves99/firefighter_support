package com.example.esp33_frontEndApp;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import org.json.JSONObject;
import org.json.JSONArray;

@Component
public class AlertConsumer {

    private static final String TOPIC_A = "ESP33_Alerts";
    private static final String TOPIC_R = "ESP33_SensorData";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private LinkedList<String> alert_messages = new LinkedList<>();
    
    private List<String[]> positions = new LinkedList<>();
    
    public List<String[]> get_positions() {
        return positions;
    }
    
    @KafkaListener(topics = TOPIC_R, groupId = "FireFighter_1")
    public void messageListener_r(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {

	System.out.println("HERE");
        String key = consumerRecord.key();
        String value = consumerRecord.value();
        int partition = consumerRecord.partition();

        //parsing data to send 
        JSONObject obj = new JSONObject(value);
        JSONArray data_points = (JSONArray) obj.get("firefighters");
        Iterator itr2 = data_points.iterator();
        int idx=1; //firefighter index
        
        while(itr2.hasNext())
        {
            JSONObject data = (JSONObject) itr2.next();
            String[] args = new String[2];
            args[0] = data.get("lat").toString();
            args[1] = data.get("longi").toString();
            positions.add(args);
   
            idx++;
        }
        idx=1;    
        
        ack.acknowledge();
    }

    @KafkaListener(topics = TOPIC_A)
    public void messageListener(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {

        String key = consumerRecord.key();
        String value = consumerRecord.value();
        int partition = consumerRecord.partition();

        System.out.println("Consumed message : value " + value +" key: " + key);
        
        alert_messages.add(value);
        System.out.println(alert_messages);
        ack.acknowledge();

    }
    
    public LinkedList<String> getAlert_messages() {
        return alert_messages;
    }
    
    public void removeAlert(){
    	alert_messages.clear();
    }
    
    public void removePositions(){
    	positions.remove(0);
    	positions.remove(1);
    	positions.remove(2);
    }
    
    public void clearPositions(){
    	positions.clear();
    }

    public int size(){
    	return alert_messages.size();
    }
    
    public int size_positions(){
    	return positions.size();
    }
    
    public String getTopic() {
        return TOPIC_A;
    }

}
