package com.evo.game_stages.hud;

import com.evo.Handler;
import com.evo.entities.Entity;

import java.awt.*;

public class DamageHUD extends ComponentHUD {

    private Entity receiver;
    private float x, y;
    private int attackDamage;

    public DamageHUD(Handler handler, Entity receiver, int attackDamage) {
        super(handler);

        x = receiver.getX();
        y = receiver.getY();
        this.attackDamage = attackDamage;
    } // **** end DamageHUD(Handler, Entity, int) constructor ****

    @Override
    public void tick() {
        long timeNow = System.currentTimeMillis();

        timeElapsed += (timeNow - timePrevious);
        timePrevious = timeNow;

        if (timeElapsed >= timerTarget) {
            timerFinished = true;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("-" + Integer.toString(attackDamage),
                (int) (x - handler.getGameCamera().getxOffset() - 10),
                (int) (y - handler.getGameCamera().getyOffset() - 10));

    }

    @Override
    public void startRenderingToScreen() {
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        timerStarted = true;
        timePrevious = System.currentTimeMillis();
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

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

} // **** end DamageHUD class ****