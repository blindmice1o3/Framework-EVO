package com.evo.game_stages;

import com.evo.Handler;
import com.evo.entities.EntityManager;
import com.evo.entities.moveable.fish.Fish;
import com.evo.entities.non_moveable.Kelp;
import com.evo.gfx.Assets;
import com.evo.tiles.Tile;

import java.awt.*;

public class GameStage {

    private Handler handler;

    // TILES
    private int width, height; //(in numberOfTile) initialized by loadGameStage(String).
    private Tile[][] tiles;

    // ENTITIES
    private float xSpawn = 310;
    private float ySpawn = 200;
    //private Fish fishInstance;
    //private Kelp kelpInstance;

    private EntityManager entityManager;

    public GameStage(Handler handler, String path) {
        this.handler = handler;
        entityManager = new EntityManager(handler, new Fish(handler, xSpawn, ySpawn));

        loadGameStage(path);

        entityManager.getPlayer().setSpeed(5);
        entityManager.addEntity(new Kelp(handler, Assets.kelpSolid[0], xSpawn-50, ySpawn-25));
        entityManager.addEntity(new Kelp(handler, Assets.kelpSolid[0], xSpawn-62, ySpawn-25));
        entityManager.addEntity(new Kelp(handler, Assets.kelpSolid[0], xSpawn-74, ySpawn-25));
    } // **** end GameStage(Handler, String) constructor ****

    public void tick() {
        entityManager.tick();

        handler.getGameCamera().centerOnEntity(entityManager.getPlayer());
    }

    public void render(Graphics g) {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        /* RENDERING EFFICIENCY (NOT RENDERING the entire tiles/map ANYMORE, JUST THE TILE SHOWING ON SCREEN) */
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //won't go into negative x values. (left end of screen). !!!HAD NEEDED 3X (see Tile.render(Graphics) method)!!!
        int xStart = (int)Math.max(0, (handler.getGameCamera().getxOffset() / (Tile.screenTileWidth)));
                //0;
        //won't go pass the end of the map/stage. (right end of screen). !!!HAD NEEDED 3X (see Tile.render(Graphics) method)!!!
        int xEnd = (int)Math.min(width, ((handler.getGameCamera().getxOffset() + handler.panelWidth) / (Tile.screenTileWidth)) + 2);
                //width;
        int yStart = (int)Math.max(0, (handler.getGameCamera().getyOffset() / (Tile.screenTileHeight)));
                //0;
        int yEnd = (int)Math.min(height, ((handler.getGameCamera().getyOffset() + handler.panelHeight) / (Tile.screenTileHeight)) + 2);
                //height;

        //BACKGROUND/TILES
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                //using the game camera's xOffset and yOffset
                ///////////////////////////////////////////////////////////////////////////////
                tiles[x][y].render( g, (int)((x*Tile.screenTileWidth) - handler.getGameCamera().getxOffset()),
                        (int)((y*Tile.screenTileHeight) - handler.getGameCamera().getyOffset()) );
                ///////////////////////////////////////////////////////////////////////////////
            }
        }

        //ENTITIES
        entityManager.render(g);
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
                //loading tiles of the middle of the map/stage
                if ( (x != 0) && (x != width-1) && (y != 0) && (y != height-1) ) {
                    //NOT solid tile.
                    tiles[x][y] = new Tile(Assets.chapter1GameStage.getSubimage((x * Tile.TILE_WIDTH), 8+(y * Tile.TILE_HEIGHT),
                            Tile.TILE_WIDTH, Tile.TILE_HEIGHT), false);
                }
                //loading tiles of the border row/column of the map/stage.
                //!!!NOT GRABBING THE ORIGINAL Assets.chapter1GameStage background image for these tiles!!!
                else {
                    //SOLID tile.
                    tiles[x][y] = new Tile(Assets.brickSolid, true);
                }
            }
        }
    }

    // GETTERS AND SETTERS

    public Tile[][] getTiles() { return tiles; }

    public Fish getFishInstance() { return entityManager.getPlayer(); }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

} // **** end GameStage class ****