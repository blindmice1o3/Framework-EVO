package com.evo.gfx;

import com.evo.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OverWorldCursor {

    private Handler handler;
    private BufferedImage image;
    private int x, y, width, height;

    public OverWorldCursor(Handler handler, BufferedImage image, int x, int y) {
        this.handler = handler;
        this.image = image;
        this.x = x;
        this.y = y;
        width = image.getWidth();
        height = image.getHeight();
    } // **** end OverWorldCursor(Handler, BufferedImage, int, int) constructor ****

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(image, x, y, width, height,null);
    }

    // GETTERS AND SETTERS

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

} // **** end OverWorldCursor class ****