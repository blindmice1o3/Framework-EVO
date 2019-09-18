package com.evo.gfx;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Animation implements Serializable {

    //TIMING
    private long timer, speedInNanoSec;

    //FRAMES
    private transient BufferedImage[] frames;
    private int index;

    public Animation(long speedInNanoSec, BufferedImage[] frames) {
        this.speedInNanoSec = speedInNanoSec;
        this.frames = frames;
        timer = 0;
        index = 0;
    } // **** end Animation(long, BufferedImage[]) constructor ****

    public void tick(long timeElapsed) {
        timer += timeElapsed;

        if (timer > speedInNanoSec) {
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

    // GETTERS AND SETTERS

    public int getIndex() { return index; }

    public BufferedImage[] getFrames() { return frames; }

} // **** end Animation class ****