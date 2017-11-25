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
    private String security;
    /**
     * уровень сигнала
     */
    private String level;

    /**
     * конструктор
     * @param title имя сети
     * @param security тип защиты
     * @param level уровень сигнала
     */
    public Element(String title, String security, String level) {
        this.title = title;
        this.security = security;
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
    public String getSecurity() {
        return security;
    }

    /**
     * получить уровень сигнала
     * @return уровень сигнала
     */
    public String getLevel() {
        return level;
    }

}