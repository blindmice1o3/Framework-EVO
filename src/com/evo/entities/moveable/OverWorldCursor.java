package com.evo.entities.moveable;

import com.evo.Handler;
import com.evo.states.WorldMapState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OverWorldCursor extends Creature {

    public OverWorldCursor(Handler handler, BufferedImage image, int x, int y) {
            super(handler, image, x, y, image.getWidth(), image.getHeight());
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(image, (int)(x * ((WorldMapState)handler.getStateManager().getTop()).X_CONVERSION_FACTOR),
                (int)(y * ((WorldMapState)handler.getStateManager().getTop()).Y_CONVERSION_FACTOR),
                (int)(width * ((WorldMapState)handler.getStateManager().getTop()).X_CONVERSION_FACTOR),
                (int)(height * ((WorldMapState)handler.getStateManager().getTop()).Y_CONVERSION_FACTOR),
                null);
    }

}