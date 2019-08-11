package com.evo.game_stages;

import com.evo.gfx.Assets;
import com.evo.tiles.Tile;

import java.awt.*;

public class GameStage {

    private int width, height;
    private Tile[][] tiles;

    public GameStage(String path) {
        loadGameStage(path);
    } // **** end GameStage(String) constructor ****

    public void tick() {

    }

    public void render(Graphics g) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                tiles[x][y].render(g, x*Tile.TILE_WIDTH, y*Tile.TILE_HEIGHT);

            }
        }
    }

    private void loadGameStage(String path) {
        width = 192;
        height = 14;
        tiles = new Tile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                //loading id of 0 as default.
                tiles[x][y] = new Tile(Assets.chapter1GameStage.getSubimage((x*Tile.TILE_WIDTH), (y*Tile.TILE_HEIGHT),
                        Tile.TILE_WIDTH, Tile.TILE_HEIGHT), false);
            }
        }
    }

} // **** end GameStage class ****