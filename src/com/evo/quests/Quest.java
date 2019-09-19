package com.evo.quests;

import com.evo.Handler;

import java.awt.*;
import java.io.Serializable;

public class Quest
        implements Serializable {

    private transient Handler handler;

    //Status
    private boolean active;
    private int currentCount;

    //Quest Requirement
    private String thingBeingCounted; //questName?
    private int requiredCount;

    //Quest Reward
    private String reward = "Quest FINISHED!"; //currently unused

    public Quest(Handler handler, String thingBeingCounted, int requiredCount) {
        this.handler = handler;
        this.thingBeingCounted = thingBeingCounted;
        this.requiredCount = requiredCount;

        active = false;
        currentCount = 0;
    } // **** end Quest(Handler, String, int) constructor ****

    public void tick() {

    }

    public void render(Graphics g) {

    }

    public void increaseCurrentCount() {
        currentCount++;
    }

    //GETTERS AND SETTERS

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getThingBeingCounted() {
        return thingBeingCounted;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public int getRequiredCount() {
        return requiredCount;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

} // **** end Quest class ****