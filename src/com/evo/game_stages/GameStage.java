package com.evo.game_stages;

import com.evo.Handler;
import com.evo.entities.moveable.fish.Fish;
import com.evo.gfx.Assets;
import com.evo.tiles.Tile;

import java.awt.*;

public class GameStage {

    private Handler handler;

    private int width, height; //(in numberOfTile) initialized by loadGameStage(String).
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
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        /* RENDERING EFFICIENCY (NOT RENDERING the entire tiles/map ANYMORE, JUST THE TILE SHOWING ON SCREEN) */
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //won't go into negative x values. (left end of screen). !!!HAD NEEDED 2X (see Tile.render(Graphics) method)!!!
        int xStart = (int)Math.max(0, (handler.getGameCamera().getxOffset() / (3*Tile.TILE_WIDTH)));
                //0;
        //won't go pass the end of the map/stage. (right end of screen). !!!HAD NEEDED 2X (see Tile.render(Graphics) method)!!!
        int xEnd = (int)Math.min(width, ((handler.getGameCamera().getxOffset() + handler.panelWidth) / (3*Tile.TILE_WIDTH)) + 2);
                //width;
        int yStart = (int)Math.max(0, (handler.getGameCamera().getyOffset() / (3*Tile.TILE_HEIGHT)));
                //0;
        int yEnd = (int)Math.min(height, ((handler.getGameCamera().getyOffset() + handler.panelHeight) / (3*Tile.TILE_HEIGHT)) + 2);
                //height;

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                //using the game camera's xOffset and yOffset
                ///////////////////////////////////////////////////////////////////////////////
                tiles[x][y].render( g, (int)((x*3*Tile.TILE_WIDTH) - handler.getGameCamera().getxOffset()),
                        (int)((y*3*Tile.TILE_HEIGHT) - handler.getGameCamera().getyOffset()) );
                ///////////////////////////////////////////////////////////////////////////////
            }
        }

        fishInstance.render(g);
    }

    private void loadGameStage(String path) {
        width = Assets.chapter1GameStage.getWidth() / Tile.TILE_WIDTH;
        height = Assets.chapter1GameStage.getHeight() / Tile.TILE_HEIGHT;
        System.out.println("number of tiles for GameStage.width: " + width);
        System.out.println("number of tiles for GameStage.height: " + height);
        //width = 192;
        //height = 14;
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

    public int getWidth() { return width; }

    public int getHeight() { return height; }

} // **** end GameStage class ****