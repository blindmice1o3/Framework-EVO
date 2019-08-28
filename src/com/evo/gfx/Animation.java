package com.evo.gfx;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Animation implements Serializable {

    private int speed, index;
    private long lastTime, timer;
    private transient BufferedImage[] frames;

    public Animation(int speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        lastTime = System.currentTimeMillis();
    } // **** end Animation(int, BufferedImage[]) constructor ****

    public void tick() {
        timer += (System.currentTimeMillis() - lastTime);
        lastTime = System.currentTimeMillis();

        if (timer > speed) {
            index++;
            timer = 0;

            if (index >= frames.length) {
                index = 0;
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }

    public void resetIndex() {
        index = 0;
    }

    // GETTERS

    public int getIndex() { return index; }

    public BufferedImage[] getFrames() { return frames; }

    public long getTimer() { return timer; }

} // **** end Animation class ****