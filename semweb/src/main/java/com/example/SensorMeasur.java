package com.example;

import com.opencsv.bean.CsvBindByName;

public class SensorMeasur {

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "time")
    private String time;

    @CsvBindByName(column = "HMDT")
    private float hmdt;

    @CsvBindByName(column = "LUMI")
    private float lumi;

    @CsvBindByName(column = "SND")
    private float snd;

    @CsvBindByName(column = "SNDF")
    private float sndf;

    @CsvBindByName(column = "SNDM")
    private float sndm;

    @CsvBindByName(column = "TEMP")
    private float temp;

    @CsvBindByName(column = "id")
    private String id;

    @CsvBindByName(column = "location")
    private String location;

    @CsvBindByName(column = "type")
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getHmdt() {
        return hmdt;
    }

    public void setHmdt(float hmdt) {
        this.hmdt = hmdt;
    }

    public float getLumi() {
        return lumi;
    }

    public void setLumi(float lumi) {
        this.lumi = lumi;
    }

    public float getSnd() {
        return snd;
    }

    public void setSnd(float snd) {
        this.snd = snd;
    }

    public float getSndf() {
        return sndf;
    }

    public void setSndf(float sndf) {
        this.sndf = sndf;
    }

    public float getSndm() {
        return sndm;
    }

    public void setSndm(float sndm) {
        this.sndm = sndm;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SensorMeasur [ name=" + name + ", time=" + time + ", hmdt=" + hmdt + ", lumi=" + lumi + ", snd=" + snd
                + ", sndf=" + sndf + ", sndm=" + sndm + ", temp=" + temp + ", id=" + id + ", location=" + location
                + ", type=" + type + "]";
    }

}
