package com.evo.entities.moveable.enemies.frogger;

import com.evo.Handler;
import com.evo.entities.Entity;
import com.evo.entities.moveable.Creature;
import com.evo.game_stages.GameStage;
import com.evo.gfx.Assets;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;
import com.evo.tiles.Tile;

import java.awt.*;

public class Log extends Creature {

    public enum MovementDirection { LEFT, RIGHT; }
    public enum Size { LARGE, MEDIUM, SMALL; }

    private MovementDirection currentMovementDirection;
    private Size mySize;

    public Log(Handler handler, float x, float y, Size size, MovementDirection movementDirection) {
        super(handler, null, x, y, 0, 0);
        mySize = size;
        currentMovementDirection = movementDirection;

        switch (size) {
            case LARGE:
                image = Assets.logLarge;
                width = Assets.logLarge.getWidth();
                height = Tile.screenTileHeight;
                break;
            case MEDIUM:
                image = Assets.logMedium;
                width = Assets.logMedium.getWidth();
                height = Tile.screenTileHeight;
                break;
            case SMALL:
                image = Assets.logSmall;
                width = Assets.logSmall.getWidth();
                height = Tile.screenTileHeight;
                break;
            default:
                System.out.println("Log(Handler, float, float, Size) constructor, switch construct's default.");
                image = Assets.logLarge;
                width = Assets.logLarge.getWidth();
                height = Tile.screenTileHeight;
                break;
        }

        bounds.width = width;
        bounds.height = height;

        speed = 1;
    } // **** end Log(Handler, float, float, Size, MovementDirection) constructor ****

    @Override
    public void initAnimations() {

    }

    @Override
    public void tick(long timeElapsed) {
        xMove = 0;
        yMove = 0;

        GameStage currentGameStage = ((GameStageState) handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage();

        switch (currentMovementDirection) {
            case LEFT:
                if ( (x-speed) > 0 ) {
                    xMove = -speed;
                } else {
                    active = false;
                }

                break;
            case RIGHT:
                if ( (x+width+speed) < (currentGameStage.getWidthInNumOfTile()*Tile.screenTileWidth) ) {
                    xMove = speed;
                } else {
                    active = false;
                }

                break;
            default:
                System.out.println("Log.tick(), switch construct's default.");
                break;
        }

        move();
    }

    @Override
    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        for (Entity e : ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getEntityManager().getEntities()) {
            //if the entity calling checkEntityCollisions(float, float) finds ITSELF in the collection, skip by continue.
            if (e.equals(this)) {
                continue;
            }

            //check EACH entity to see if their collision bounds INTERSECTS with yours.
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                //Frog can walk on Log instances.
                ////////////////////////
                if (e instanceof Frog) {
                    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                    e.setX( (e.getX() + xOffset) );
                    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                    //@@@@@@@@@@%
                    return false;
                    //@@@@@@@@@@@
                } else {
                    return true;
                }
                ////////////////////////
            }
        }

        return false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image,
                (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()),
                (int)(x - handler.getGameCamera().getxOffset() + width),
                (int)(y - handler.getGameCamera().getyOffset() + height),
                0,
                0,
                image.getWidth(),
                image.getHeight(),
                null);
    }

    @Override
    public void die() {

    }

} // **** end Log class ****