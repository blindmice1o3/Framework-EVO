package com.evo.game_stages;

import com.evo.Handler;
import com.evo.entities.EntityManager;
import com.evo.entities.moveable.fish.Fish;
import com.evo.entities.non_moveable.Kelp;
import com.evo.gfx.Assets;
import com.evo.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameStage {

    private Handler handler;

    // TILES
    private int widthInNumOfTile, heightInNumOfTile; //(in numberOfTile) initialized by loadGameStage(String).
    private Tile[][] tiles;

    // ENTITIES
    private float xSpawn = 310;
    private float ySpawn = 200;
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
        int xEnd = (int)Math.min(widthInNumOfTile, ((handler.getGameCamera().getxOffset() + handler.panelWidth) / (Tile.screenTileWidth)) + 2);
                //widthInNumOfTile;
        int yStart = (int)Math.max(0, (handler.getGameCamera().getyOffset() / (Tile.screenTileHeight)));
                //0;
        int yEnd = (int)Math.min(heightInNumOfTile, ((handler.getGameCamera().getyOffset() + handler.panelHeight) / (Tile.screenTileHeight)) + 2);
                //heightInNumOfTile;

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

    private boolean compareTwoSprites(BufferedImage sprite1, BufferedImage sprite2) {
        //if width or height are not the same, the two sprites are NOT the same.
        if ((sprite1.getWidth() != sprite1.getWidth()) || (sprite1.getHeight() != sprite2.getHeight())) {
            return false;
        }

        //if any pixels are not the same, the two sprite are NOT the same.
        for (int y = 0; y < sprite1.getHeight(); y++) {
            for (int x = 0; x < sprite1.getWidth(); x++) {
                int pixelSprite1 = sprite1.getRGB(x, y);
                int redSprite1 = (pixelSprite1 >> 16) & 0xff;
                int greenSprite1 = (pixelSprite1 >> 8) & 0xff;
                int blueSprite1 = (pixelSprite1) & 0xff;

                int pixelSprite2 = sprite2.getRGB(x, y);
                int redSprite2 = (pixelSprite2 >> 16) & 0xff;
                int greenSprite2 = (pixelSprite2 >> 8) & 0xff;
                int blueSprite2 = (pixelSprite2) & 0xff;

                if ((redSprite1 != redSprite2) || (greenSprite1 != greenSprite2) || (blueSprite1 != blueSprite2)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void loadGameStage(String path) {
        widthInNumOfTile = Assets.chapter1GameStage.getWidth() / Tile.TILE_WIDTH;
        heightInNumOfTile = Assets.chapter1GameStage.getHeight() / Tile.TILE_HEIGHT;
        System.out.println("number of tiles for GameStage.widthInNumOfTile: " + widthInNumOfTile);
        System.out.println("number of tiles for GameStage.heightInNumOfTile: " + heightInNumOfTile);
        //widthInNumOfTile = 192;
        //heightInNumOfTile = 14;
        tiles = new Tile[widthInNumOfTile][heightInNumOfTile];

        ArrayList<BufferedImage> solidTileSearchTargets = new ArrayList<BufferedImage>();
        solidTileSearchTargets.add(Assets.brickGreen);
        solidTileSearchTargets.add(Assets.coralPink);
        solidTileSearchTargets.add(Assets.coinGameObject);

        //check each pixels in the tile (16x16) within the 192tiles by 14tiles map.
        for (int y = 0; y < heightInNumOfTile-1; y++) {
            for (int x = 0; x < widthInNumOfTile; x++) {

                int xOffset = (x * Tile.TILE_WIDTH);
                int yOffset = (y * Tile.TILE_HEIGHT)+8;

                //for each tile, check if it's one of the solidTileSearchTargets.
                for (BufferedImage solidTileTarget : solidTileSearchTargets) {

                    BufferedImage currentTile = Assets.chapter1GameStage.getSubimage(xOffset, yOffset,
                            solidTileTarget.getWidth(), solidTileTarget.getHeight());

                    //if it's the same, we have a SOLID tile.
                    if (compareTwoSprites(solidTileTarget, currentTile)) {
                        tiles[x][y] = new Tile(currentTile, true);
                    }
                    //otherwise, we have a NOT solid tile.
                    else {
                        tiles[x][y] = new Tile(currentTile, false);
                    }

                }
/*
                //loading tiles of the middle of the map/stage
                if ( (x != 0) && (x != widthInNumOfTile-1) && (y != 0) && (y != heightInNumOfTile-1) ) {
                    //NOT solid tile.
                    tiles[x][y] = new Tile(Assets.chapter1GameStage.getSubimage((x * Tile.TILE_WIDTH), 8+(y * Tile.TILE_HEIGHT),
                            Tile.TILE_WIDTH, Tile.TILE_HEIGHT), false);
                }
                //loading tiles of the border row/column of the map/stage.
                //!!!NOT GRABBING THE ORIGINAL Assets.chapter1GameStage background image for these tiles!!!
                else {
                    //SOLID tile.
                    tiles[x][y] = new Tile(Assets.brickGreen, true);
                }
*/
            }
        }

        for (int x = 0; x < widthInNumOfTile; x++) {
            tiles[x][heightInNumOfTile-1] = new Tile(Assets.brickGreen, true);
        }
    }

    // GETTERS AND SETTERS

    public Tile[][] getTiles() { return tiles; }

    public EntityManager getEntityManager() { return entityManager; }

    public Fish getPlayer() { return entityManager.getPlayer(); }

    public int getWidthInNumOfTile() { return widthInNumOfTile; }

    public int getHeightInNumOfTile() { return heightInNumOfTile; }

} // **** end GameStage class ****