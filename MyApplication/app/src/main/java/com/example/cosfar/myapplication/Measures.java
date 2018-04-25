package com.example.cosfar.myapplication;

/**
 * Created by COSFAR on 24.04.2018.
 */

public class Measures {
    /**
     * номер замера
     */
    private int numberMeasure;
    /**
     * мак адрес
     */
    private String bssid;
    /**
     * уровень сигнала
     */
    private int level;

    /**
     * конструктор
     * @param numberMeasure номер замера
     * @param bssid мак адрес
     * @param level уровень сигнала
     */
    public Measures(int numberMeasure, String bssid, int level) {
        this.numberMeasure = numberMeasure;
        this.bssid = bssid;
        this.level = level;
    }

    /**
     * получить номер замера
     * @return номер замера
     */
    public int getNumberMeasure() {
        return numberMeasure;
    }

    /**
     * получить мак адрес
     * @return мак адрес
     */
    public String getBssid() {
        return bssid;
    }

    /**
     * получить уровень сигнала
     * @return уровень сигнала
     */
    public int getLevel() {
        return level;
    }

    /**
     * получить номер замера
     * @return номер замера
     */
    public void setNumberMeasure(int numberMeasure) {
        this.numberMeasure = numberMeasure;
    }

    /**
     * получить мак адрес
     * @return мак адрес
     */
    public void setBssid(String bssid) {
       this.bssid = bssid;
    }

    /**
     * получить уровень сигнала
     * @return уровень сигнала
     */
    public void setLevel(int level) {
        this.level=level;
    }


}



