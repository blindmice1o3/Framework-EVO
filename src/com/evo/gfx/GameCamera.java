package com.evo.gfx;

import com.evo.Handler;
import com.evo.entities.Entity;

public class GameCamera {

    private Handler handler;
    private float xOffset, yOffset;

    public GameCamera(Handler handler, float xOffset, float yOffset) {
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    } // **** end GameCamera(Handler, float, float) constructor

    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - (handler.panelWidth / 2) + (e.getWidth() / 2);
        yOffset = e.getY() - (handler.panelHeight / 2)+ (e.getHeight() / 2);
    }

    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;
    }

    // GETTERS AND SETTERS

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

} // **** end GameCamera class ****