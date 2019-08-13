package com.evo.entities.moveable;

import com.evo.Handler;
import com.evo.entities.Entity;

import java.awt.image.BufferedImage;

public abstract class Creature extends Entity {

    public static final int DEFAULT_HEALTH = 10;
    public static final float DEFAULT_SPEED = 3;

    protected int health;
    protected float speed;
    protected float xMove, yMove;

    public Creature(Handler handler, BufferedImage image, float x, float y, int width, int height) {
        super(handler, image, x, y, width, height);

        health = DEFAULT_HEALTH;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    } // **** end Creature(Handler, BufferedImage, float, float, int, int) constructor

    public void move() {
        moveX();
        moveY();
    }

    public void moveX() {
        x += xMove;
    }

    public void moveY() {
        y += yMove;
    }

    //protected boolean collision

    // GETTERS AND SETTERS

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

} // **** end Creature abstract class ****