package com.evo.entities.moveable;

import com.evo.Handler;
import com.evo.states.WorldMapState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OverWorldCursor extends Creature {

    public OverWorldCursor(Handler handler, BufferedImage image, float x, float y) {
        super(handler, image, x, y, image.getWidth(), image.getHeight());
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(image, (int)x, (int)y, width, height,null);
    }

}