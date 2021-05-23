package com.FireFighter_Support.restservice;

import java.util.List;

//import com.FireFighter_Support.restservice.Model.Sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.client.RestTemplate;


@Controller
public class Controllers {

    //@Autowired private RestTemplate restTemplate;
    private List<String> data;

    @Autowired
    private KafkaConsumer consumer;

    @GetMapping("/")
    public String sensors(Model model )throws Exception{
        
        updateInfo();

        model.addAttribute("InfoSensors", data);
        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
        "<body>\n" + data + "</body>\n" + "</html>";

    }

    @Scheduled(fixedRate = 5000)

    public void updateInfo() throws Exception{
        data = consumer.get_messages();


        
    }


}