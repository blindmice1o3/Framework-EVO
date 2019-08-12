package com.evo.tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    public static final int TILE_WIDTH = 16, TILE_HEIGHT = 16;
    public static int conversionFactor = 3;
    public static int screenTileWidth = conversionFactor * TILE_WIDTH;
    public static int screenTileHeight = conversionFactor * TILE_HEIGHT;

    protected BufferedImage texture;
    protected boolean solid;

    public Tile(BufferedImage texture, boolean solid) {
        this.texture = texture;
        this.solid = solid;
    } // **** end Tile(BufferedImage, boolean) constructor ****

    public void tick() {

    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y,
                /* 3X */
                screenTileWidth,
                //3*TILE_WIDTH,
                /* 3X */
                screenTileHeight,
                //3*TILE_HEIGHT,
                null);
    }

} // **** end Tile class ****