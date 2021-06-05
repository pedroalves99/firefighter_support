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


import java.util.LinkedList;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@Controller
public class AppMappings {
    
    /* Constants */
    private static final Logger log = LoggerFactory.getLogger(AppMappings.class);
    
    /* Instance Variables */
    @Autowired private RestTemplate restTemplate;
    @Autowired private AlertConsumer consumer;
    private List<String> alerts = new LinkedList<String>();
    /* Views */

    @GetMapping("/historic")
    public String historic(Model model )throws Exception{

        return "NADA";

    }

    @GetMapping("/realtime")
    public String alerts(Model model) throws Exception{
    
    	model.addAttribute("ffPositions", consumer.get_positions());
    	System.out.println("positions: " + consumer.get_positions().toString());
        return "realDash";
    }

    
    
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public List<String> updateAlerts() throws Exception{
    	System.out.println("UPDATING MESSAGES");
    	this.alerts = consumer.getAlert_messages();
    	consumer.removeAlert();
    	System.out.println(alerts);
        return this.alerts;
        
    }
    
    /* Scheduler | Web Socket */

    @MessageMapping("/pos_msg")
    @SendTo("/topic/pos")
    public List<String[]> updatePos() throws Exception{
    
        return consumer.get_positions();
        
    }

}
