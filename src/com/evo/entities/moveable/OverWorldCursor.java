package com.evo.entities.moveable;

import com.evo.Handler;
import com.evo.states.WorldMapState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OverWorldCursor extends Creature {

    public final double X_CONVERSION_FACTOR;
    public final double Y_CONVERSION_FACTOR;

    public OverWorldCursor(Handler handler, BufferedImage image, int x, int y,
                           double X_CONVERSION_FACTOR, double Y_CONVERSION_FACTOR) {
            super(handler, image, x, y, image.getWidth(), image.getHeight());
            this.X_CONVERSION_FACTOR = X_CONVERSION_FACTOR;
            this.Y_CONVERSION_FACTOR = Y_CONVERSION_FACTOR;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(image, (int)(x * X_CONVERSION_FACTOR),
                (int)(y * Y_CONVERSION_FACTOR),
                (int)(width * X_CONVERSION_FACTOR),
                (int)(height * Y_CONVERSION_FACTOR),
                null);
    }

}