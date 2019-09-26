package com.evo.game_stages;

import com.evo.Handler;
import com.evo.entities.Entity;
import com.evo.entities.EntityManager;
import com.evo.entities.moveable.enemies.Eel;
import com.evo.entities.moveable.enemies.SeaJelly;
import com.evo.entities.moveable.enemies.frogger.Car;
import com.evo.entities.moveable.enemies.frogger.Frog;
import com.evo.entities.moveable.enemies.frogger.Log;
import com.evo.entities.moveable.player.Fish;
import com.evo.entities.moveable.player.IPlayable;
import com.evo.entities.non_moveable.Kelp;
import com.evo.game_stages.hud.ComponentHUD;
import com.evo.game_stages.hud.HeadUpDisplay;
import com.evo.gfx.Assets;
import com.evo.items.ItemManager;
import com.evo.states.StateManager;
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

    //drowning timer
    private long drowningCooldown, drowningTimer;

    public GameStage(Handler handler, Identifier identifier) {
        this.handler = handler;
        this.identifier = identifier;

        loadGameStage(identifier);

        drowningCooldown = 800000000L;
        drowningTimer = drowningCooldown;
    } // **** end GameStage(Handler, Identifier) constructor ****

    private void loadGameStage(Identifier identifier) {
        initTiles(identifier);
        initItemManager(identifier);
        initEntityManager(identifier);
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
    private void initEntityManager(Identifier identifier) {
        switch (identifier) {
            case EVO:
                entityManager = new EntityManager( handler, new Fish(handler, xSpawn, ySpawn) );

                entityManager.addEntity(new Kelp(handler, Assets.kelpSolid[0], xSpawn-50, ySpawn-25));
                entityManager.addEntity(new Kelp(handler, Assets.kelpSolid[0], xSpawn-63, ySpawn-25));
                entityManager.addEntity(new Kelp(handler, Assets.kelpSolid[0], xSpawn-76, ySpawn-25));
                entityManager.addEntity(new SeaJelly(handler, xSpawn+50, ySpawn-50));
                entityManager.addEntity(new Eel( handler,
                        xSpawn+150, ((tiles[0].length-3)*Tile.screenTileHeight)+(Tile.screenTileHeight/2)+7,
                        Eel.MovementDirection.LEFT, 5 ));

                break;
            case FROGGER:
                entityManager = new EntityManager( handler, new Frog(handler, xSpawn, ySpawn) );

                entityManager.addEntity(new Car(handler, Assets.carWhiteRight, Car.MovementDirection.RIGHT,
                        0, 15+handler.panelHeight-Assets.carPinkLeft.getHeight()-Tile.screenTileHeight,
                        Tile.screenTileWidth, Tile.screenTileHeight));
                entityManager.addEntity(new Car(handler, Assets.carPinkLeft, Car.MovementDirection.LEFT,
                        (widthInNumOfTile-1)*Tile.screenTileWidth, 15+handler.panelHeight-Assets.carPinkLeft.getHeight(),
                        Tile.screenTileWidth, Tile.screenTileHeight));
                entityManager.addEntity(new Log(handler, 0,
                        (4*Tile.screenTileHeight), Log.Size.SMALL, Log.MovementDirection.RIGHT));
                entityManager.addEntity(new Log(handler, (widthInNumOfTile - 1)*Tile.screenTileWidth,
                        (5*Tile.screenTileHeight), Log.Size.LARGE, Log.MovementDirection.LEFT));
                entityManager.addEntity(new Log(handler, 0,
                        (6*Tile.screenTileHeight), Log.Size.MEDIUM, Log.MovementDirection.RIGHT));

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

    private void checkWinningState() {
        int x = 0;
        int y = 0;
        int width = widthInNumOfTile*Tile.screenTileWidth;
        int height = 3*Tile.screenTileHeight;

        Rectangle winningRectangle = new Rectangle(x, y, width, height);
        Rectangle playerCollisionBounds = ((Entity)entityManager.getPlayer()).getCollisionBounds(0, 0);

        if (winningRectangle.contains(playerCollisionBounds)) {
            String winningText = "CONGRATULATIONS!";
            Object[] args = { winningText };
            handler.getStateManager().pushIState(StateManager.State.TEXTBOX, args);
        }
    }

    private boolean isPlayerStandingOnEntity() {
        Rectangle playerBounds = ((Frog)entityManager.getPlayer()).getCollisionBounds(0,0);

        for (Entity e : entityManager.getEntities()) {
            //if it's the player, move on to the next Entity instance.
            if (e instanceof IPlayable) {
                continue;
            }

            //player is standing on a river entity, NOT DROWNING.
            if (e instanceof Log) {
                Rectangle riverEntityBounds = e.getCollisionBounds(0, 0);
                if (playerBounds.intersects(riverEntityBounds)) {
                    return true;
                }
            }
        }

        return false;
    }
    private void checkPlayerDrowning() {
        //TODO: check if intersects with player, if so, call player's hurt().
        //TODO: OR have player check intersects with water/river tile whenever move() called.

        //water/river tiles.
        if ( (((Frog)entityManager.getPlayer()).getY() / Tile.screenTileHeight) < 7 ) {
            //TODO: check all entities to see if they intersects frog, otherwise frog is in water and we call hurt().
            if ( isPlayerStandingOnEntity() ) {
                return;
            }
            //CALL HURT() (player is in water and not standing on an entity).
            else {
                if (drowningTimer < drowningCooldown) {
                    return;
                }

                Frog player = (Frog)entityManager.getPlayer();
                int damage = 1;
                ComponentHUD damageHUD = new ComponentHUD(handler, ComponentHUD.ComponentType.DAMAGE, damage, player);
                headUpDisplay.addTimedNumericIndicator(damageHUD);

                player.hurt(damage);
                drowningTimer = 0;
            }
        }

    }
    private void tickDrowningCooldown(long timeElapsed) {
        drowningTimer += timeElapsed;
        //drowningTimer gets reset to 0 in checkPlayerDrowning().
    }

    int controllerForNextEntityInstantiation = 0;
    int numTypeOfCars = 2;
    int numTypeOfRiverEntities = 3;
    int chanceToInstantiate = 0;
    public void tick(long timeElapsed) {
        itemManager.tick(timeElapsed);
        entityManager.tick(timeElapsed);
        headUpDisplay.tick(timeElapsed);

        switch (identifier) {
            case EVO:
                handler.getGameCamera().centerOnEntity( (Entity)entityManager.getPlayer() );

                break;
            case FROGGER:
                checkWinningState();

                //WATER/RIVER damage-to-player
                tickDrowningCooldown(timeElapsed);
                checkPlayerDrowning();

                if (entityManager.getEntities().size() < 8) {
                    chanceToInstantiate = (int)(Math.random()*100)+1;
                    System.out.println("chanceToInstantiate: " + chanceToInstantiate);

                    //ONLY INSTANTIATE 5% of the time TICK() IS CALLED.
                    if (chanceToInstantiate <= 5) {
                        controllerForNextEntityInstantiation = (int) (Math.random() * (numTypeOfCars+numTypeOfRiverEntities)) + 1;
                        System.out.println("controllerForNextEntityInstantiation: " + controllerForNextEntityInstantiation);
                        int x = 0;
                        int y = 0;
                        int width = 0;
                        int height = 0;

                        switch (controllerForNextEntityInstantiation) {
                            case 1:
                                x = 0;
                                y = 15 + handler.panelHeight - Assets.carPinkLeft.getHeight() - Tile.screenTileHeight;
                                width = Tile.screenTileWidth;
                                height = Tile.screenTileHeight;

                                //checking for overlap before instantiating new Car.
                                for (Entity e : entityManager.getEntities()) {
                                    if (e.getCollisionBounds(0, 0).intersects(new Rectangle(x, y, width, height))) {
                                        return;
                                    }
                                }

                                //add new right Car instance.
                                entityManager.addEntity(new Car(handler, Assets.carWhiteRight, Car.MovementDirection.RIGHT,
                                        x, y, width, height));

                                break;
                            case 2:
                                x = (widthInNumOfTile - 1) * Tile.screenTileWidth;
                                y = 15 + handler.panelHeight - Assets.carPinkLeft.getHeight();
                                width = Tile.screenTileWidth;
                                height = Tile.screenTileHeight;

                                //checking for overlap before instantiating new Car.
                                for (Entity e : entityManager.getEntities()) {
                                    if (e.getCollisionBounds(0, 0).intersects(new Rectangle(x, y, width, height))) {
                                        return;
                                    }
                                }

                                //add new left Car instance.
                                entityManager.addEntity(new Car(handler, Assets.carPinkLeft, Car.MovementDirection.LEFT,
                                        x, y, width, height));

                                break;
                            case 3:
                                //Log.Size.SMALL
                                x = 0;
                                y = (4*Tile.screenTileHeight);
                                width = Assets.logSmall.getWidth();
                                height = Tile.screenTileHeight;

                                //checking for overlap before instantiating new Log.
                                for (Entity e : entityManager.getEntities()) {
                                    if (e.getCollisionBounds(0, 0).intersects(new Rectangle(x, y, width, height))) {
                                        return;
                                    }
                                }

                                //add new Log instance (Size.SMALL).
                                entityManager.addEntity(new Log(handler, x, y, Log.Size.SMALL, Log.MovementDirection.RIGHT));

                                break;
                            case 4:
                                //Log.Size.LARGE
                                x = (widthInNumOfTile - 1) * Tile.screenTileWidth;;
                                y = (5*Tile.screenTileHeight);
                                width = Assets.logLarge.getWidth();
                                height = Tile.screenTileHeight;

                                //checking for overlap before instantiating new Log.
                                for (Entity e : entityManager.getEntities()) {
                                    if (e.getCollisionBounds(0, 0).intersects(new Rectangle(x, y, width, height))) {
                                        return;
                                    }
                                }

                                //add new Log instance (Size.LARGE).
                                entityManager.addEntity(new Log(handler, x, y, Log.Size.LARGE, Log.MovementDirection.LEFT));

                                break;
                            case 5:
                                //Log.Size.MEDIUM
                                x = 0;
                                y = (6*Tile.screenTileHeight);
                                width = Assets.logMedium.getWidth();
                                height = Tile.screenTileHeight;

                                //checking for overlap before instantiating new Log.
                                for (Entity e : entityManager.getEntities()) {
                                    if (e.getCollisionBounds(0, 0).intersects(new Rectangle(x, y, width, height))) {
                                        return;
                                    }
                                }

                                //add new Log instance (Size.MEDIUM).
                                entityManager.addEntity(new Log(handler, x, y, Log.Size.MEDIUM, Log.MovementDirection.RIGHT));

                                break;
                            default:
                                System.out.println("GameStage(Identifier.FROGGER).tick(), switch construct's default.");
                                break;
                        }
                    }
                }

                handler.getGameCamera().centerOnEntity( (Entity)entityManager.getPlayer() );

                break;
            default:
                System.out.println("GameStage.tick(), switch construct's default.");

                break;
        }
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

        //ITEMS
        itemManager.render(g);

        //ENTITIES
        entityManager.render(g);


        //SPECIALS (GRID_LINES AND PLAYER)
        switch (identifier) {
            case EVO:

                break;
            case FROGGER:
                for (int y = 0; y < (heightInNumOfTile/2); y++) {
                    for (int x = 0; x < widthInNumOfTile; x++) {
                        Tile tile = tiles[x][y];

                        g.setColor(Color.YELLOW);
                        g.drawRect((int)((x*Tile.screenTileWidth) - handler.getGameCamera().getxOffset()),
                                (int)((y*Tile.screenTileHeight) - handler.getGameCamera().getyOffset()),
                                Tile.screenTileWidth, Tile.screenTileHeight);
                    }
                }

                ((Frog)entityManager.getPlayer()).render(g);

                break;
            default:
                System.out.println("GameStage.render(Graphics), switch construct's default.");
                break;
        }


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


        //initializing spawning coordinate.
        xSpawn = (widthInNumOfTile / 2) * Tile.screenTileWidth;
        ySpawn = (heightInNumOfTile - 2) * Tile.screenTileHeight;


        for (int y = 0; y < heightInNumOfTile; y++) {
            for (int x = 0; x < widthInNumOfTile; x++) {

                BufferedImage texture = Assets.backgroundFrogger.getSubimage(
                        (x * Tile.screenTileWidth),
                        (y * Tile.screenTileHeight),
                        Tile.screenTileWidth,
                        Tile.screenTileHeight
                );

                tiles[x][y] = new Tile(texture, false);

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


        //initializing spawning coordinate.
        xSpawn = 310;
        ySpawn = 200;


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

    public IPlayable getPlayer() { return entityManager.getPlayer(); }

    public int getWidthInNumOfTile() { return widthInNumOfTile; }

    public int getHeightInNumOfTile() { return heightInNumOfTile; }

    public Identifier getIdentifier() { return identifier; }

} // **** end GameStage class ****