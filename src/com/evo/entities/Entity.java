package com.evo.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    protected int x, y, width, height;
    protected BufferedImage image;
    protected Rectangle bounds;

    public Entity(BufferedImage image, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.image = image;
        bounds = new Rectangle(x, y, width, height);
    } // **** end Entity(BufferedImage, int, int, int, int) constructor ****

    public abstract void tick();
    public abstract void render(Graphics g);

} // **** end Entity abstract class ****