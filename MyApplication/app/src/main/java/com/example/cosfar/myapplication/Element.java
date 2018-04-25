package com.example.cosfar.myapplication;

/**
 * Created by COSFAR on 27.09.2017.
 */

public class Element {
    /**
     * имя сети;
     */
    private String title;
    /**
     * тип защиты
     */
    private String address;
    /**
     * уровень сигнала
     */
    private String level;

    /**
     * конструктор
     * @param title имя сети
     * @param address тип защиты
     * @param level уровень сигнала
     */
    public Element(String title, String address, String level) {
        this.title = title;
        this.address = address;
        this.level = level;
    }

    /**
     * получить имя сети
     * @return имя сети
     */
    public String getTitle() {
        return title;
    }

    /**
     * получить тип защиты
     * @return тип защиты
     */
    public String getAddress() {
        return address;
    }

    /**
     * получить уровень сигнала
     * @return уровень сигнала
     */
    public String getLevel() {
        return level;
    }

}