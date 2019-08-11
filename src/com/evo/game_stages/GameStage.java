package com.evo.game_stages;

import com.evo.Handler;
import com.evo.entities.moveable.fish.Fish;
import com.evo.gfx.Assets;
import com.evo.tiles.Tile;

import java.awt.*;

public class GameStage {

    private Handler handler;

    private int width, height;
    private Tile[][] tiles;
    private Fish fishInstance;

    public GameStage(Handler handler, String path) {
        this.handler = handler;
        loadGameStage(path);

        fishInstance = new Fish(handler);
        fishInstance.setX(310);
        fishInstance.setY(200);
        fishInstance.setxMove(5);   //move speed.
        fishInstance.setyMove(5);   //move speed.
    } // **** end GameStage(Handler, String) constructor ****

    public void tick() {
        fishInstance.tick();
        handler.getGameCamera().centerOnEntity(fishInstance);
    }

    public void render(Graphics g) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //using the game camera's xOffset and yOffset
                ///////////////////////////////////////////////////////////////////////////////
                tiles[x][y].render( g, (int)((x*2*Tile.TILE_WIDTH) - handler.getGameCamera().getxOffset()),
                        (int)((y*2*Tile.TILE_HEIGHT) - handler.getGameCamera().getyOffset()) );
                ///////////////////////////////////////////////////////////////////////////////
            }
        }

        fishInstance.render(g);
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

    public Fish getFishInstance() { return fishInstance; }

} // **** end GameStage class ****