package com.evo.items;

import com.evo.Handler;
import com.evo.entities.moveable.fish.Fish;
import com.evo.gfx.Assets;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Item {

    //HANDLER
    public static Item[] items = new Item[256];
    public static Item meatItem = new Item(Assets.meat, "Meat", 0);

    //CLASS
    public static final int ITEMWIDTH = 16, ITEMHEIGHT = 16, PICKED_UP = -1;

    protected Handler handler;

    protected BufferedImage texture;
    protected String name;
    protected final int id;

    protected Rectangle bounds;

    protected int x, y, count;

    public Item(BufferedImage texture, String name, int id) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        count = 1;

        //x and y are defaulting to 0 at the moment, they get set/initialize when setPosition() is called.
        bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);

        items[id] = this;
    } // **** end Item(BufferedImage, String, int) constructor ****

    public void tick() {
        Fish player = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();

        //add && condition where KeyManager.keyJustPressed( button-that-triggers-eating-state ).
        //player stepped on this item and that means it should get picked up.
        if ( (player.getCollisionBounds(0, 0).intersects(bounds)) &&
                (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) ) {
            //TODO: give player health/experience.
            count = PICKED_UP;
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

} // **** end Item class ****