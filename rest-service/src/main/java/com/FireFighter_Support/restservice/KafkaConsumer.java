package com.FireFighter_Support.restservice;


import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.json.JSONObject;
import org.json.JSONArray;

@Component
public class KafkaConsumer {

    private List<String> messages = new LinkedList<>();

    private static final String TOPIC = "SensorData";

    private int idx = 1;

    Logger log = LoggerFactory.getLogger(InfluxDBFactory.class);
    
    public List<String> get_messages() {
        return messages;
    }

    @KafkaListener(topics = TOPIC)
    public void messageListener(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {

        String key = consumerRecord.key();
        String value = consumerRecord.value();
        int partition = consumerRecord.partition();

        System.out.println("Consumed message : " + value);

        //connecting to DB
        InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086", "admin", "secret");
        // test connection           
        Pong response = influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            log.error("Error pinging server.");
            return;
        } 
        influxDB.createDatabase("firefighters");
        influxDB.createRetentionPolicy(
        "defaultPolicy", "firefighters", "30d", 1, true);
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        //influxDB.setRetentionPolicy("policy");
        influxDB.setDatabase("firefighters");
        influxDB.enableBatch(100, 200, TimeUnit.MILLISECONDS);

        //parsing data to send 
        JSONObject obj = new JSONObject(value);
        JSONArray data_points = (JSONArray) obj.get("firefighters");
        Iterator itr2 = data_points.iterator();
        idx=1; //firefighter index
        
        BatchPoints batchPoints = BatchPoints
                        .database("firefighters")
                        //.retentionPolicy("defaultPolicy")
                        .build();
        while(itr2.hasNext())
        {
            JSONObject data = (JSONObject) itr2.next();
   
            Point p = Point.measurement("SensorData")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("ff_num", idx )
                .addField("CO", data.get("CO").toString())
                .addField("temp", data.get("temp").toString())
                .addField("hum", data.get("hum").toString())
                .addField("bat", data.get("bat").toString())
                .addField("lat", data.get("lat").toString())
                .addField("longi", data.get("longi").toString())
                .addField("alt", data.get("alt").toString())
                .addField("hr", data.get("hr").toString())
                .build();
                batchPoints.point(p);
                influxDB.write(batchPoints);
            idx++;
        }
        idx=1;
        /*System.out.println("Consumed message : " + value
                         " with key : " + key
                        + " from partition : "+ partition);
              */      

        
        
        messages.add(value.toString());
        
        ack.acknowledge();
    }
}