package com.FireFighter_Support.restservice.Model;


import java.util.List;


public class Sensor{

    private int CO; 
    private int temp; 
    private int hum;
    private int bat;
    private float lat; 
    private float longi;
    private int alt;
    private float hr;


    public Sensor(){

    }
    

    public Sensor(List state) {
        setCO(state.get(0));
        setTemp(state.get(1));
        setHum(state.get(2));
        setBat(state.get(3));
        setLat(state.get(4));
        setLongi(state.get(5));
        setAlt(state.get(6));
        setHr(state.get(7));
        
    }

    public int getCO() {
        return CO;
    }


    public void setCO(Object cO) {
        CO = Integer.parseInt(cO.toString());
    }


    public int getTemp() {
        return temp;
    }


    public void setTemp(Object temp) {
        this.temp =Integer.parseInt(temp.toString());
    }


    public int getHum() {
        return hum;
    }


    public void setHum(Object hum) {
        this.hum = Integer.parseInt(hum.toString());
    }


    public int getBat() {
        return bat;
    }


    public void setBat(Object bat) {
        this.bat = Integer.parseInt(bat.toString());
    }


    public float getLat() {
        return lat;
    }


    public void setLat(Object lat) {
        this.lat = Float.parseFloat(lat.toString());
    }


    public float getLongi() {
        return longi;
    }


    public void setLongi(Object longi) {
        this.longi = Float.parseFloat(longi.toString());
    }


    public int getAlt() {
        return alt;
    }


    public void setAlt(Object alt) {
        this.alt = Integer.parseInt(alt.toString());
    }


    public float getHr() {
        return hr;
    }


    public void setHr(Object hr) {
        this.hr = Float.parseFloat(hr.toString());
    }

    @Override
    public String toString() {
        return "Sensor [CO=" + CO + ", alt=" + alt + ", bat=" + bat + ", hr=" + hr + ", hum=" + hum + ", lat=" + lat
                + ", longi=" + longi + ", temp=" + temp + "]";
    }
}