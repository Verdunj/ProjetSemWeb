package com.example;

public class ItemMeteo {
    String temps;
    String heure;

    public ItemMeteo(String temps, String heure) {
        this.temps = temps;
        this.heure = heure;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

}
