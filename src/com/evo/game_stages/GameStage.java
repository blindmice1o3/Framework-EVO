package com.evo.game_stages;

import com.evo.Handler;
import com.evo.entities.EntityManager;
import com.evo.entities.moveable.enemies.Eel;
import com.evo.entities.moveable.enemies.SeaJelly;
import com.evo.entities.moveable.player.Fish;
import com.evo.entities.non_moveable.Kelp;
import com.evo.game_stages.hud.HeadUpDisplay;
import com.evo.gfx.Assets;
import com.evo.items.ItemManager;
import com.evo.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameStage {

    public enum Identifier { EVO, FROGGER; }

    private Handler handler;
    private Identifier identifier;

    // TILES
    private int widthInNumOfTile, heightInNumOfTile; //(in numberOfTile) initialized by initTiles(Identifier).
    private Tile[][] tiles;
    // ENTITIES
    private float xSpawn, ySpawn;
    private EntityManager entityManager;
    // ITEMS
    private ItemManager itemManager;
    // HUD
    private HeadUpDisplay headUpDisplay;

    public GameStage(Handler handler, Identifier identifier) {
        this.handler = handler;
        this.identifier = identifier;
        xSpawn = 310;
        ySpawn = 200;

        loadGameStage(identifier);
    } // **** end GameStage(Handler, Identifier) constructor ****

    private void loadGameStage(Identifier identifier) {
        initTiles(identifier);
        initItemManager(identifier);
        initEntityManager(identifier, new Fish(handler, xSpawn, ySpawn));
        initHeadUpDisplay(identifier);
    }

    private void initTiles(Identifier identifier) {
        switch(identifier) {
            case EVO:
                initTilesEVO();
                break;
            case FROGGER:
                initTilesFROGGER();
                break;
            default:
                System.out.println("GameStage.initTiles(Identifier), switch-construct's default.");
                break;
        }
    }
    private void initItemManager(Identifier identifier) {
        itemManager = new ItemManager(handler);

        switch (identifier) {
            case EVO:

                break;
            case FROGGER:

                break;
            default:
                System.out.println("GameStage.initItemManager(Identifier), switch-construct's default.");
        }
    }
    private void initEntityManager(Identifier identifier, Fish player) {
        entityManager = new EntityManager(handler, player);

        switch (identifier) {
            case EVO:
                entityManager.addEntity(new Kelp(handler, Assets.kelpSolid[0], xSpawn-50, ySpawn-25));
                entityManager.addEntity(new Kelp(handler, Assets.kelpSolid[0], xSpawn-63, ySpawn-25));
                entityManager.addEntity(new Kelp(handler, Assets.kelpSolid[0], xSpawn-76, ySpawn-25));
                entityManager.addEntity(new SeaJelly(handler, xSpawn+50, ySpawn-50));
                entityManager.addEntity(new Eel( handler,
                        xSpawn+150, ((tiles[0].length-3)*Tile.screenTileHeight)+(Tile.screenTileHeight/2)+7,
                        Eel.MovementDirection.LEFT, 5 ));
                entityManager.getPlayer().setSpeed(5);

                break;
            case FROGGER:
                entityManager.addEntity(new Eel(handler,
                        Tile.screenTileWidth, handler.panelHeight-Assets.eel[0].getHeight(),
                        Eel.MovementDirection.RIGHT, widthInNumOfTile-2));
                entityManager.getPlayer().setSpeed(3);

                break;
            default:
                System.out.println("GameStage.initEntityManager(Identifier, Fish), switch-construct's default.");
        }
    }
    private void initHeadUpDisplay(Identifier identifier) {
        headUpDisplay = new HeadUpDisplay(handler);

        switch (identifier) {
            case EVO:

                break;
            case FROGGER:

                break;
            default:
                System.out.println("GameStage.initHeadUpDisplay(Identifier), switch-construct's default.");
        }
    }

    public void tick(long timeElapsed) {
        itemManager.tick(timeElapsed);
        entityManager.tick(timeElapsed);
        headUpDisplay.tick(timeElapsed);

        switch (identifier) {
            case EVO:
                handler.getGameCamera().centerOnEntity(entityManager.getPlayer());

                break;
            case FROGGER:
                handler.getGameCamera().centerOnEntity(entityManager.getPlayer());

                break;
            default:
                System.out.println("GameStage.tick(), switch construct's default.");

                break;
        }
    }

    public void render(Graphics g) {
        //BACKGROUND (TILES)
        switch (identifier) {
            case EVO:

                break;
            case FROGGER:
                //BACKGROUND: whole panel
                //g.drawImage(Assets.backgroundFrogger, 0, 0, handler.panelWidth, handler.panelHeight, null);

                /*
                for (int y = 0; y < heightInNumOfTile; y++) {
                    for (int x = 0; x < widthInNumOfTile; x++) {
                        tiles[x][y].render( g, (int)((x*Tile.screenTileWidth) - handler.getGameCamera().getxOffset()),
                                (int)((y*Tile.screenTileHeight) - handler.getGameCamera().getyOffset()));
                    }
                }
                */

                /*
                for (int x = 0; x < tiles.length; x++) {
                    tiles[x][3].render( g, (x*Tile.screenTileWidth), (3*Tile.screenTileHeight));

                    tiles[x][heightInNumOfTile-3].render( g, (x*Tile.screenTileWidth),
                            ((heightInNumOfTile-3)*Tile.screenTileHeight));
                }
                */

                break;
            default:
                System.out.println("GameStage.render(Graphics), switch construct's default.");
                break;
        }

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

        //ITEMS
        itemManager.render(g);

        //ENTITIES
        entityManager.render(g);

        //HUD
        headUpDisplay.render(g);
    }

    private boolean compareTwoSprites(BufferedImage sprite1, BufferedImage sprite2, int x, int y) {
        //if width or height are not the same, the two sprites are NOT the same.
        if ((sprite1.getWidth() != sprite1.getWidth()) || (sprite1.getHeight() != sprite2.getHeight())) {
            return false;
        }


        //if any pixels are not the same, the two sprite are NOT the same.
        //for (int y = 0; y < Tile.TILE_HEIGHT; y++) {
            //for (int x = 0; x < Tile.TILE_HEIGHT; x++) {
                int pixelSprite1 = sprite1.getRGB(x, y);
                int redSprite1 = (pixelSprite1 >> 16) & 0xff;
                int greenSprite1 = (pixelSprite1 >> 8) & 0xff;
                int blueSprite1 = (pixelSprite1) & 0xff;

                int pixelSprite2 = sprite2.getRGB(x, y);
                int redSprite2 = (pixelSprite2 >> 16) & 0xff;
                int greenSprite2 = (pixelSprite2 >> 8) & 0xff;
                int blueSprite2 = (pixelSprite2) & 0xff;



                if ( ((redSprite1 == redSprite2) && (greenSprite1 == greenSprite2) && (blueSprite1 == blueSprite2)) ) {
                    return true;
                }

            //}
        //}

        return false;
    }

    private void initTilesFROGGER() {
        //widthInNumOfTile = (handler.panelWidth / Tile.TILE_WIDTH);
        //heightInNumOfTile = (handler.panelHeight / Tile.TILE_HEIGHT);
        widthInNumOfTile = (Assets.backgroundFrogger.getWidth() / Tile.screenTileWidth);
        heightInNumOfTile = (Assets.backgroundFrogger.getHeight() / Tile.screenTileHeight);
        System.out.println("number of tiles for GameStage(handler, FROGGER).widthInNumOfTile: " + widthInNumOfTile);
        System.out.println("number of tiles for GameStage(handler, FROGGER).heightInNumOfTile: " + heightInNumOfTile);
        tiles = new Tile[widthInNumOfTile][heightInNumOfTile];

        for (int y = 0; y < heightInNumOfTile; y++) {
            for (int x = 0; x < widthInNumOfTile; x++) {

                BufferedImage texture = Assets.backgroundFrogger.getSubimage(
                        (x * Tile.screenTileWidth),
                        (y * Tile.screenTileHeight),
                        Tile.screenTileWidth,
                        Tile.screenTileHeight
                );
                //TESTING (YES TEXTURE)... attempting to determine which row is associated with which street lane.
                if ( (y == 3) && (x != 0) && (x != 6) && (x != (widthInNumOfTile-1)) ) {
                    tiles[x][y] = new Tile(Assets.carPinkLeft, true);
                }
                else if ( (y == (heightInNumOfTile-3)) && (x != 0) && (x != 6) && (x != (widthInNumOfTile-1)) ) {
                    tiles[x][y] = new Tile(Assets.logSmall, true);
                }
                //ALL TILES WALKABLE (NO TEXTURE).
                else {
                    tiles[x][y] = new Tile(texture, false);
                }
            }
        }
    }

    private void initTilesEVO() {
        widthInNumOfTile = Assets.chapter1GameStage.getWidth() / Tile.TILE_WIDTH;
        heightInNumOfTile = Assets.chapter1GameStage.getHeight() / Tile.TILE_HEIGHT;
        System.out.println("number of tiles for GameStage(handler, EVO).widthInNumOfTile: " + widthInNumOfTile);
        System.out.println("number of tiles for GameStage(handler, EVO).heightInNumOfTile: " + heightInNumOfTile);
        //widthInNumOfTile = 192;
        //heightInNumOfTile = 14;
        tiles = new Tile[widthInNumOfTile][heightInNumOfTile];

        ArrayList<BufferedImage> solidTileSearchTargets = new ArrayList<BufferedImage>();
        solidTileSearchTargets.add(Assets.brickGreen);
        solidTileSearchTargets.add(Assets.coinGameObject);
        solidTileSearchTargets.add(Assets.coralPink1);
        solidTileSearchTargets.add(Assets.coralPink2);
        solidTileSearchTargets.add(Assets.coralPink3);


        //check each pixels in the tile (16x16) within the 192tiles by 14tiles map.
        for (int y = 0; y < heightInNumOfTile-1; y++) {
            for (int x = 0; x < widthInNumOfTile; x++) {

                int xOffset = (x * Tile.TILE_WIDTH);
                int yOffset = (y * Tile.TILE_HEIGHT)+8;
                BufferedImage currentTile = Assets.chapter1GameStage.getSubimage(xOffset, yOffset,
                        Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

                //for each tile, check if it's one of the solidTileSearchTargets.
                for (BufferedImage solidTileTarget : solidTileSearchTargets) {

                    int xx = 0;
                    int yy = 0;

                    if (solidTileTarget == Assets.brickGreen) {
                        xx = 0;
                        yy = 0;
                    } else {
                        xx = 9;
                        yy = 2;
                    }

                    //if it's the same, we have a SOLID tile.
                    if (compareTwoSprites(solidTileTarget, currentTile, xx, yy)) {
                        tiles[x][y] = new Tile(currentTile, true);
                        break;
                    }

                    if ((tiles[x][y] == null) && (solidTileTarget == Assets.coralPink1)) {
                        xx = 8;
                        yy = 14;
                        if (compareTwoSprites(solidTileTarget, currentTile, xx, yy)) {
                            tiles[x][y] = new Tile(currentTile, true);
                            break;
                        }
                    }
                    if ((tiles[x][y] == null) && (solidTileTarget == Assets.coralPink1)) {
                        xx = 8;
                        yy = 15;
                        if (compareTwoSprites(solidTileTarget, currentTile, xx, yy)) {
                            tiles[x][y] = new Tile(currentTile, true);
                            break;
                        }
                    }

                }

                if (tiles[x][y] == null) {
                    tiles[x][y] = new Tile(currentTile, false);
                }
            }
        }

        for (int x = 0; x < widthInNumOfTile; x++) {
            tiles[x][heightInNumOfTile-1] = new Tile(Assets.brickGreen, true);
        }
    }

    // GETTERS AND SETTERS

    public Tile[][] getTiles() { return tiles; }

    public EntityManager getEntityManager() { return entityManager; }

    public ItemManager getItemManager() { return itemManager; }

    public HeadUpDisplay getHeadUpDisplay() { return headUpDisplay; }

    public Fish getPlayer() { return entityManager.getPlayer(); }

    public int getWidthInNumOfTile() { return widthInNumOfTile; }

    public int getHeightInNumOfTile() { return heightInNumOfTile; }

} // **** end GameStage class ****