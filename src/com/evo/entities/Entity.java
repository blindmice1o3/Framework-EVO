package com.evo.entities;

import com.evo.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    protected Handler handler;

    protected float x, y;
    protected int width, height;
    protected Rectangle bounds;

    protected BufferedImage image;

    public Entity(Handler handler, BufferedImage image, float x, float y, int width, int height) {
        this.handler = handler;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(0, 0, width, height);

        this.image = image;
    } // **** end Entity(Handler, BufferedImage, float, float, int, int) constructor ****

    public abstract void tick();
    public abstract void render(Graphics g);

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

} // **** end Entity abstract class ****