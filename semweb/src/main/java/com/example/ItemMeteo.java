package com.example;

public class ItemMeteo {
    String temperature;
    String heure;

    public ItemMeteo(String temps, String heure) {
        this.temperature = temps;
        this.heure = heure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temps) {
        this.temperature = temps;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

}
