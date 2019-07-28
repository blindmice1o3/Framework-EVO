package com.evo.entities.moveable;

import com.evo.gfx.Assets;

import java.awt.*;

public class OverWorldCursor extends Creature {

    public OverWorldCursor(int x, int y, int width, int height) {
        super(Assets.leftOverworld0, x, y, width, height);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

}