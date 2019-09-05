package com.evo.game_stages.hud;

import com.evo.Handler;
import com.evo.entities.Entity;

import java.awt.*;

public abstract class ComponentHUD {

    protected Handler handler;

    protected boolean timerStarted, timerFinished;

    protected long timeElapsed, timePrevious, timerTarget;

    public ComponentHUD(Handler handler) {
        this.handler = handler;

        timerStarted = false;
        timerFinished = false;

        timerTarget = 5000;
        timeElapsed = 0;
        timePrevious = System.currentTimeMillis();
    } // **** end ComponentHUD class ****

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract void startRenderingToScreen();

    // GETTERS AND SETTERS

    public boolean isTimerStarted() {
        return timerStarted;
    }

    public void setTimerStarted(boolean timerStarted) {
        this.timerStarted = timerStarted;
    }

    public boolean isTimerFinished() {
        return timerFinished;
    }

    public void setTimerFinished(boolean timerFinished) {
        this.timerFinished = timerFinished;
    }

} // **** end ComponentHUD class ****