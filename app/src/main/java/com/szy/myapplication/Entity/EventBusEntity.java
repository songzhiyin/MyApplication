package com.szy.myapplication.Entity;

/**
 * Created by Administrator on 2018/1/8 0008.
 * 事件控件
 */

public class EventBusEntity {
    private String name;
    private boolean hideck;
    private int number;
    private float money;

    public EventBusEntity() {
    }

    public EventBusEntity(String name) {
        this.name = name;
    }

    public EventBusEntity(boolean hideck) {
        this.hideck = hideck;
    }

    public EventBusEntity(int number) {
        this.number = number;
    }

    public EventBusEntity(float money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHideck() {
        return hideck;
    }

    public void setHideck(boolean hideck) {
        this.hideck = hideck;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
