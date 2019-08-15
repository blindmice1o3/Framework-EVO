package com.evo.entities.moveable;

import com.evo.Handler;
import com.evo.entities.Entity;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;
import com.evo.tiles.Tile;

import java.awt.image.BufferedImage;

public abstract class Creature extends Entity {

    public static final int DEFAULT_HEALTH = 10;
    public static final float DEFAULT_SPEED = 3;

    protected int health;
    protected float speed;
    protected float xMove, yMove;

    public Creature(Handler handler, BufferedImage image, float x, float y, int width, int height) {
        super(handler, image, x, y, width, height);

        health = DEFAULT_HEALTH;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    } // **** end Creature(Handler, BufferedImage, float, float, int, int) constructor

    public void move() {
        moveX();
        moveY();
    }

    public void moveX() {
        //moving RIGHT (check right corners of Creature):
        if (xMove > 0) {
            //future location (location trying to move into)
            int tx = (int)((x + bounds.x + bounds.width + xMove)/ Tile.screenTileWidth);

            //check TOP-right and BOTTOM-right corners.
            if (!collisionWithTile(tx, (int)((y + bounds.y)/Tile.screenTileHeight)) &&
                    !collisionWithTile(tx, (int)((y + bounds.y + bounds.height)/Tile.screenTileHeight))) {
                x += xMove;
            }
        }
        //moving LEFT (check left corners of Creature):
        else if (xMove < 0) {
            //future location (location trying to move into)
            int tx = (int)((x + bounds.x + xMove)/Tile.screenTileWidth);

            //check TOP-left and BOTTOM-left corners.
            if (!collisionWithTile(tx, (int)((y + bounds.y)/Tile.screenTileHeight)) &&
                    !collisionWithTile(tx, (int)((y + bounds.y + bounds.height)/Tile.screenTileHeight))) {
                x += xMove;
            }
        }
    }

    public void moveY() {
        //moving DOWN (check bottom corners of Creature):
        if (yMove > 0) {
            //future location (location trying to move into)
            int ty = (int)((y + bounds.y + bounds.height + yMove)/Tile.screenTileHeight);

            //check LEFT-bottom and RIGHT-bottom corners.
            if (!collisionWithTile((int)((x + bounds.x)/Tile.screenTileWidth), ty) &&
                    !collisionWithTile((int)((x + bounds.x + bounds.width)/Tile.screenTileWidth), ty)) {
                y += yMove;
            }
        }
        //moving UP (check top corners of Creature):
        else if (yMove < 0) {
            //future location (location trying to move into)
            int ty = (int)((y + bounds.y + yMove)/Tile.screenTileHeight);

            //check LEFT-top and RIGHT-top corners.
            if (!collisionWithTile((int)((x + bounds.x)/Tile.screenTileWidth), ty) &&
                    !collisionWithTile((int)((x + bounds.x + bounds.width)/Tile.screenTileWidth), ty)) {
                y += yMove;
            }
        }
    }

    protected boolean collisionWithTile(int x, int y) {
        GameStageState gameStageState = (GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE);

        return gameStageState.getCurrentGameStage().getTiles()[x][y].isSolid();
    }

    // GETTERS AND SETTERS

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

} // **** end Creature abstract class ****