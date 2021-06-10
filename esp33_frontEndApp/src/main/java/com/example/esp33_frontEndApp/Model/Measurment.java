package com.example.esp33_frontEndApp.Model;

import lombok.Data;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

@Data
@Measurement(name = "SensorData")
public class Measurment {

    @Column(name = "time")
    private Instant time;

    @Column(name = "ff_num")
    private String ff_num;

    @Column(name = "CO")
    private String co;

    @Column(name = "temp")
    private String temp;

    @Column(name = "hum")
    private String hum;
    
    @Column(name = "bat")
    private String bat;

    @Column(name = "lat")
    private String lat;

    @Column(name = "longi")
    private String longi;

    @Column(name = "alt")
    private String alt;

    @Column(name = "hr")
    private String hr;

    
    public String getFF(){
    	return this.ff_num;
    }
    
    public String getCO(){
    	return this.co;
    }
    
    public String getTemp(){
    	return this.temp;
    }
    
    public String getHum(){
    	return this.hum;
    }
    
    public String getbat(){
    	return this.bat;
    }
    
    public String getLat(){
    	return this.lat;
    }
    
    public String getLongi(){
    	return this.longi;
    }
    
    public String getAlt(){
    	return this.alt;
    }
    
    public String getHr(){
    	return this.hr;
    }

}
