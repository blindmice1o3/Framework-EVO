package com.evo.entities.non_moveable;

import com.evo.Handler;
import com.evo.entities.Entity;

import java.awt.image.BufferedImage;

public abstract class StaticEntity extends Entity {

    public StaticEntity(Handler handler, BufferedImage image, float x, float y, int width, int height) {
        super(handler, image, x, y, width, height);
    } // **** end StaticEntity(Handler, BufferedImage, float, float, int, int) constructor ****

} // **** end StaticEntity abstract class ****