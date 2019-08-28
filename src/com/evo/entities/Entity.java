package com.evo.entities;

import com.evo.Handler;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;

import javax.sql.rowset.serial.SerialArray;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class Entity implements Serializable {

    public static final int DEFAULT_HEALTH = 10;

    protected transient Handler handler;

    protected float x, y;
    protected int width, height;
    protected Rectangle bounds;
    protected int health;
    protected boolean active;

    protected BufferedImage image;

    public Entity(Handler handler, BufferedImage image, float x, float y, int width, int height) {
        this.handler = handler;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(0, 0, width, height);
        health = DEFAULT_HEALTH;
        active = true;

        this.image = image;
    } // **** end Entity(Handler, BufferedImage, float, float, int, int) constructor ****

    public abstract void initAnimations();
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract void die();

    public void hurt(int amount) {
        health -= amount;

        System.out.println(getClass().getSimpleName() + ".hurt(int) called: " + health + " hp left.");

        if (health <= 0) {
            active = false;
            die();
        }
    }

    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        for (Entity e : ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getEntityManager().getEntities()) {
            //if the entity calling checkEntityCollisions(float, float) finds ITSELF in the collection, skip by continue.
            if (e.equals(this)) {
                continue;
            }

            //check EACH entity to see if their collision bounds INTERSECTS with yours.
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }

        return false;
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset) {
        return new Rectangle((int)(x + bounds.x + xOffset),
                (int)(y + bounds.y + yOffset),
                bounds.width,
                bounds.height);
    }

    // GETTERS AND SETTERS

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getX() { return x; }

    public float getY() { return y; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public Rectangle getBounds() { return bounds; }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setHandler(Handler handler) { this.handler = handler; }

} // **** end Entity abstract class ****