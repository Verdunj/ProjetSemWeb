package com.example;

import com.opencsv.bean.CsvBindByName;

public class SensorMeasur {

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "time")
    private String time;

    @CsvBindByName(column = "HMDT")
    private double hmdt;

    @CsvBindByName(column = "LUMI")
    private double lumi;

    @CsvBindByName(column = "SND")
    private double snd;

    @CsvBindByName(column = "SNDF")
    private double sndf;

    @CsvBindByName(column = "SNDM")
    private double sndm;

    @CsvBindByName(column = "TEMP")
    private double temp;

    @CsvBindByName(column = "id")
    private String id;

    @CsvBindByName(column = "location")
    private String location;

    @CsvBindByName(column = "type")
    private String type;

    public enum ResultType {
        HMDT("g/m3"), LUMI("lm"), SND("snd"), SNDF("sndf"), SNDM("sndm"), TEMP("Cel");

        private String uom;

        private ResultType(String uom) {
            this.uom = uom;
        }

        public String getUOM() {
            return this.uom;
        }
    }

    public SensorMeasur() {
    }

    public SensorMeasur(String name, String time, double hmdt, double lumi, double snd, double sndf, double sndm,
            double temp,
            String id, String location, String type) {
        this.name = name;
        this.time = time;
        this.hmdt = hmdt;
        this.lumi = lumi;
        this.snd = snd;
        this.sndf = sndf;
        this.sndm = sndm;
        this.temp = temp;
        this.id = id;
        this.location = location;
        this.type = type;

    }

    public String getResultUOM() {
        if (this.getHmdt() != 0)
            return "g/m3";
        else if (this.getLumi() != 0)
            return "lm";
        else if (this.getSnd() != 0)
            return "snd";
        else if (this.getSndf() != 0)
            return "sndf";
        else if (this.getSndm() != 0)
            return "sndm";
        else
            return "Cel";
    }

    public double getResult() {
        if (this.getHmdt() != 0)
            return this.getHmdt();
        else if (this.getLumi() != 0)
            return this.getLumi();
        else if (this.getSnd() != 0)
            return this.getSnd();
        else if (this.getSndf() != 0)
            return this.getSndf();
        else if (this.getSndm() != 0)
            return this.getSndm();
        else
            return this.getTemp();
    }

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

    public double getHmdt() {
        return hmdt;
    }

    public void setHmdt(double hmdt) {
        this.hmdt = hmdt;
    }

    public double getLumi() {
        return lumi;
    }

    public void setLumi(double lumi) {
        this.lumi = lumi;
    }

    public double getSnd() {
        return snd;
    }

    public void setSnd(double snd) {
        this.snd = snd;
    }

    public double getSndf() {
        return sndf;
    }

    public void setSndf(double sndf) {
        this.sndf = sndf;
    }

    public double getSndm() {
        return sndm;
    }

    public void setSndm(double sndm) {
        this.sndm = sndm;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
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
