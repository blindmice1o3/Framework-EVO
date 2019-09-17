package com.evo.game_stages.hud;

import com.evo.Handler;
import com.evo.entities.Entity;

import java.awt.*;

public class ComponentHUD {

    public enum ComponentType { HP, EXP, DAMAGE; }

    protected Handler handler;

    protected float x, y;
    protected ComponentType currentComponentType;
    protected int value;

    protected boolean timerStarted, timerFinished;

    protected long timeElapsed, /*timePrevious,*/ timerTarget;

    public ComponentHUD(Handler handler, ComponentType componentType, int value, Entity entity) {
        this.handler = handler;

        this.currentComponentType = componentType;
        this.value = value;
        x = entity.getX();
        y = entity.getY();

        timerStarted = false;
        timerFinished = false;

        timeElapsed = 0;
        //timerTarget = 5000;
        timerTarget = 5000000000L;      //TODO: timerTarget has to be in NANOSECOND now!!!!
        //timeElapsed = 0;
        //timePrevious = System.currentTimeMillis();
    } // **** end ComponentHUD(Handler, ComponentType, int, Entity) constructor ****

    public void tick(long timeElapsed) {
        if (timerStarted) {
            //long timeNow = System.currentTimeMillis();

            //timeElapsed += (timeNow - timePrevious);
            //timePrevious = timeNow;

            this.timeElapsed += timeElapsed;

            System.out.println("ComponentHUD.tick(long), timeElapsed: " + this.timeElapsed + " | timerTarget: " + timerTarget);

            if (this.timeElapsed >= timerTarget) {
                timerFinished = true;
            }
        }
    }

    public void render(Graphics g) {
        if (timerStarted) {
            switch (currentComponentType) {
                case HP:
                    g.setColor(Color.GREEN);
                    g.drawString("+" + Integer.toString(value),
                            (int) (x - handler.getGameCamera().getxOffset()),
                            (int) (y - handler.getGameCamera().getyOffset()));
                    break;
                case EXP:
                    g.setColor(Color.WHITE);
                    g.drawString("+" + Integer.toString(value),
                            (int) (x - handler.getGameCamera().getxOffset()),
                            (int) (y - handler.getGameCamera().getyOffset() + 10));

                    break;
                case DAMAGE:
                    g.setColor(Color.RED);
                    g.drawString("-" + Integer.toString(value),
                            (int) (x - handler.getGameCamera().getxOffset() - 10),
                            (int) (y - handler.getGameCamera().getyOffset() - 10));

                    break;
                default:
                    System.out.println("ComponentHUD.render(Graphics), switch-constructor's default.");
                    break;
            }
        }
    }

    public void startRenderingToScreen() {
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        timerStarted = true;
        //timePrevious = System.currentTimeMillis();
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    // GETTERS AND SETTERS

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

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