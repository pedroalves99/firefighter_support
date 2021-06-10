package com.example.esp33_frontEndApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.example.esp33_frontEndApp.AlertConsumer;
import com.example.esp33_frontEndApp.Model.Measurment;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.impl.InfluxDBResultMapper;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.Query;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@Controller
public class AppMappings {
    
    /* Constants */
    private static final Logger log = LoggerFactory.getLogger(AppMappings.class);
    private LinkedList<String> alerts = new LinkedList<String>();
    
    /* Instance Variables */
    @Autowired private RestTemplate restTemplate;
    @Autowired private AlertConsumer consumer;
    
    /* Views */

    @GetMapping("/historic")
    public String historic(Model model )throws Exception{
    
    	List<Measurment> meas = getInfo();
    	
    	model.addAttribute("meas", meas);

        return "historic";

    }

    @GetMapping("/realtime")
    public String alerts(Model model) throws Exception{
    
    	model.addAttribute("ffPositions", consumer.get_positions());
    	System.out.println("positions: " + consumer.get_positions().toString());
        return "realDash";
    }

    
    
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public LinkedList<String> updateAlerts() throws Exception{
    	System.out.println("UPDATING MESSAGES");
    	alerts = (LinkedList<String>)consumer.getAlert_messages().clone();
    	System.out.println(alerts);
    	consumer.removeAlert();
        return alerts;
        
    }
    
    /* Scheduler | Web Socket */

    @MessageMapping("/pos_msg")
    @SendTo("/topic/pos")
    public List<String[]> updatePos() throws Exception{
    	System.out.println("UPDATING POSITIONS");
        return consumer.get_positions();
    }
    
    @MessageMapping("/reset")
    @SendTo("/topic/reset")
    public String updateReset() throws Exception{
    	System.out.println("RESETING");
    	consumer.clearPositions();
        return "ok";
    }

    private List<Measurment> getInfo(){
    	//connecting to DB
        InfluxDB influxDB = InfluxDBFactory.connect("http://192.168.160.18:8086", "admin", "secret");
        // test connection           
        Pong response = influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            log.error("Error pinging server.");
            return null;
        } 
        
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        //influxDB.setRetentionPolicy("defaultPolicy");
        influxDB.setDatabase("esp33_firefighters");
        influxDB.enableBatch(100, 200, TimeUnit.MILLISECONDS);
        
        QueryResult queryResult = influxDB.query(new Query("SELECT * FROM SensorData WHERE time >= now() - 1h", "esp33_firefighters"));

	System.out.println(queryResult);
	InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
	List<Measurment> memPointList = resultMapper.toPOJO(queryResult, Measurment.class);
	
	return memPointList;
    }
}
