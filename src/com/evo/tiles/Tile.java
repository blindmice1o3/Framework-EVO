package com.evo.tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    public static final int TILE_WIDTH = 16, TILE_HEIGHT = 16;

    protected BufferedImage texture;
    protected boolean solid;

    public Tile(BufferedImage texture, boolean solid) {
        this.texture = texture;
        this.solid = solid;
    } // **** end Tile(BufferedImage, boolean) constructor ****

    public void tick() {

    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

} // **** end Tile class ****