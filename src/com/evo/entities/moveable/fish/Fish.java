package com.evo.entities.moveable.fish;

import com.evo.Handler;
import com.evo.entities.moveable.Creature;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fish extends Creature {

    public enum DirectionFacing { UP, DOWN, LEFT, RIGHT; }

    private FishStateManager fishStateManager;
    private DirectionFacing directionFacing;

    public Fish(Handler handler, BufferedImage image, int x, int y) {
        //super(image, x, y, (2 * Tile.WIDTH), Tile.HEIGHT);
        super(handler, image, x, y, 100, 50);

        fishStateManager = new FishStateManager();
        directionFacing = DirectionFacing.UP;
    } // **** end Fish(Handler, BufferedImage, int, int) constructor ****

    public void tick() {

    }

    public void render(Graphics g) {

    }

} // **** end Fish class ****