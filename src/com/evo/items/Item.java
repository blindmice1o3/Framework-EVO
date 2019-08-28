package com.evo.items;

import com.evo.Handler;
import com.evo.entities.moveable.player.Fish;
import com.evo.game_stages.GameStage;
import com.evo.gfx.Assets;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Item implements Serializable {

    //HANDLER
    public static Item[] items = new Item[256];
    public static Item meatItem = new Item(Assets.meat, "Meat", 0);

    //CLASS
    public static final int ITEMWIDTH = 16, ITEMHEIGHT = 16;

    protected transient Handler handler;

    protected transient BufferedImage texture;
    protected String name;
    protected final int id;

    protected Rectangle bounds;

    protected int x, y, count;
    protected boolean pickedUp;

    protected int rewardHealth, rewardExperiencePoints;

    //EXPIRATION
    protected boolean expired;
    protected long expirationElapsed, expirationLimit, expirationPreviousTick;

    public Item(BufferedImage texture, String name, int id) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        count = 1;
        pickedUp = false;

        rewardHealth = 2;
        rewardExperiencePoints = 10;

        expired = false;
        expirationLimit = 9000; //milliseconds.
        expirationElapsed = 0;
        expirationPreviousTick = System.currentTimeMillis();

        //x and y are defaulting to 0 at the moment, they get set/initialize when setPosition() is called.
        bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);

        items[id] = this;
    } // **** end Item(BufferedImage, String, int) constructor ****

    public void tick() {
        //TODO: move the meat instance's x, y coordinate values to produce a floating/bobbing-in-water visual fx.

        checkExpiration();

        checkEaten();
    }

    private void checkExpiration() {
        long expirationCurrentTick = System.currentTimeMillis();
        expirationElapsed += expirationCurrentTick - expirationPreviousTick;
        expirationPreviousTick = expirationCurrentTick;

        if (expirationElapsed >= expirationLimit) {
            expired = true;
        }
    }

    private void checkEaten() {
        GameStage gameStage = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage();
        Fish player = gameStage.getPlayer();

        //add && condition where KeyManager.keyJustPressed( button-that-triggers-eating-state ).
        //player stepped on this item and that means it should get picked up.
        //TODO: change from KeyEvent to checking player's currentState == State.EAT.
        if ( (player.getCollisionBounds(0, 0).intersects(bounds)) &&
                (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) ) {

            //TODO: create RewardManager class which should be called by GameStage.tick() and GameStage.render(Graphics).
            //TODO: instantiate a new Reward() object, passing it rewardHealth, rewardExperiencePoints, and on-screen coordinates.
            //TODO: add the Reward instance to RewardManager's ArrayList<Reward> rewards.
            //TODO: have the Reward.render(Graphics, int, int)/Reward.tick() use elapsedTime to remove() the Reward object from RewardManager.

            player.setHealth( (player.getHealth() + rewardHealth) );
            player.setExperiencePoints( (player.getExperiencePoints() + rewardExperiencePoints) );

            //do NOT let eating meat items give player more health points than player's maximum health points.
            if (player.getHealth() > player.getHealthMax()) {
                player.setHealth( player.getHealthMax() );
            }
            System.out.println("player's health: " + player.getHealth());
            System.out.println("player's experience points: " + player.getExperiencePoints());

            pickedUp = true;
        }
    }

    /**
     * render to game stage (on the ground in the game world) using the item's instance variables x and y.
     */
    public void render(Graphics g) {
        // handler may not have been set yet.
        if (handler == null) {
            return;
        }

        render(g, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()));
    }

    public void renderRewards(Graphics g) {

    }


    /**
     * render to screen (e.g. inventory or HUD) using local variables x and y (passed in as arguments).
     */
    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
    }

    public Item createNew(int x, int y) {
        Item i = new Item(texture, name, id);
        i.setPosition(x, y);
        return i;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        bounds.x = x;
        bounds.y = y;
    }

    // GETTERS AND SETTERS

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void setExpirationElapsed(long expirationElapsed) { this.expirationElapsed = expirationElapsed; }

    public void setExpirationPreviousTick(long expirationPreviousTick) { this.expirationPreviousTick = expirationPreviousTick; }

} // **** end Item class ****