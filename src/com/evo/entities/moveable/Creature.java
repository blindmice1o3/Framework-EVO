package com.evo.entities.moveable;

import com.evo.Handler;
import com.evo.entities.Entity;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;
import com.evo.tiles.Tile;

import java.awt.image.BufferedImage;

public abstract class Creature extends Entity {

    public static final float DEFAULT_SPEED = 3;

    protected float speed;
    protected float xMove, yMove;

    public Creature(Handler handler, BufferedImage image, float x, float y, int width, int height) {
        super(handler, image, x, y, width, height);

        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    } // **** end Creature(Handler, BufferedImage, float, float, int, int) constructor

    public void move() {
        //COLLISION W ENTITY CHECK depending on where we WILL BE moving to (xMove).
        if (!checkEntityCollisions(xMove, 0f)) {
            moveX();
        }
        //COLLISION W ENTITY CHECK depending on where we WILL BE moving to (yMove).
        if (!checkEntityCollisions(0f, yMove)) {
            moveY();
        }
    }

    public void moveX() {
        //moving RIGHT (check right corners of Creature):
        if (xMove > 0) {
            //future location (location trying to move into) (RIGHT side of Creature).
            int tx = (int) ((x + bounds.x + bounds.width + xMove) / Tile.screenTileWidth);

            //check TOP-right and BOTTOM-right corners.
            //if within tiles (AND no-collision-with-solid), change the Creature's x-coordinate value.

            //not moving off the edge of tiles.
            if (isWithinRightEdgeOfMap( (int)(x + bounds.x + bounds.width + xMove) )) {

                if (!collisionWithTile(tx, (int) ((y + bounds.y) / Tile.screenTileHeight)) &&
                        !collisionWithTile(tx, (int) ((y + bounds.y + bounds.height) / Tile.screenTileHeight))) {
                    x += xMove;     //MOVE INTO NEW POSITION.
                }
                //there was a COLLISION, Creature's future x-position is inside a tile, re-adjust it to JUST outside tile.
                else {
                    //convert to pixel-coordinate by multiplying with Tile.screenTileWidth; minus 1 allows y-movement.
                    x = (tx * Tile.screenTileWidth) - bounds.x - bounds.width - 1;  //COLLISION WITH TILE.
                }

            }
            else {
                x = (tx * Tile.screenTileWidth) - bounds.x - bounds.width - 1;
            }

        }
        //moving LEFT (check left corners of Creature):
        else if (xMove < 0) {
            //future location (location trying to move into) (LEFT side of Creature).
            int tx = (int) ((x + bounds.x + xMove) / Tile.screenTileWidth);

            //check TOP-left and BOTTOM-left corners.
            //if within tiles (AND no-collision-with-solid), change the Creature's x-coordinate value.

            //not moving off the edge of tiles.
            if (isWithinLeftEdgeOfMap( (int)(x + bounds.x + xMove) )) {    //TODO: CHECK LEFT EDGE OF MAP.

                if (!collisionWithTile(tx, (int) ((y + bounds.y) / Tile.screenTileHeight)) &&
                        !collisionWithTile(tx, (int) ((y + bounds.y + bounds.height) / Tile.screenTileHeight))) {
                    x += xMove;     //MOVE INTO NEW POSITION.
                }
                //there was a COLLISION, Creature's future x-position is inside a tile, re-adjust it to JUST outside tile.
                else {
                    //convert to pixel-coordinate by multiplying with Tile.screenTileWidth.
                    x = (tx * Tile.screenTileWidth) + Tile.screenTileWidth - bounds.x;  //COLLISION WITH TILE.
                }

            }
            else {                            //TODO: STILL NOT RIGHT.
                x = 0;
            }
        }
    }

    private boolean isWithinRightEdgeOfMap(int xFutureRightCorner) {
        int widthMapInPixel = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getTiles().length * Tile.screenTileWidth;

        //System.out.println("Creature.isWithinRightEdgeOfMap(), widthMapInPixel: " + widthMapInPixel);

        return (xFutureRightCorner < widthMapInPixel);
    }

    private boolean isWithinLeftEdgeOfMap(int xFutureLeftCorner) {
        return (xFutureLeftCorner >= 0);
    }

    private boolean isWithinBottomEdgeOfMap(int yFutureBottomCorner) {
        int heightMapInPixel = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getTiles()[0].length * Tile.screenTileHeight;

        //System.out.println("Creature.isWithinBottomEdgeOfMap(), heightMapInPixel: " + heightMapInPixel);

        return (yFutureBottomCorner < heightMapInPixel);
    }

    private boolean isWithinTopEdgeOfMap(int yFutureTopCorner) {
        return (yFutureTopCorner >= 0);
    }

    public void moveY() {
        //moving DOWN (check bottom corners of Creature):
        if (yMove > 0) {
            //future location (location trying to move into) (BOTTOM side of Creature).
            int ty = (int)((y + bounds.y + bounds.height + yMove) / Tile.screenTileHeight);

            //check LEFT-bottom and RIGHT-bottom corners.
            //if within tiles (AND no-collision-with-solid), change the Creature's y-coordinate value.

            //not moving off the edge of tiles.
            if (isWithinBottomEdgeOfMap( (int)(y + bounds.y + bounds.height + yMove) )) {

                if (!collisionWithTile((int) ((x + bounds.x) / Tile.screenTileWidth), ty) &&
                        !collisionWithTile((int) ((x + bounds.x + bounds.width) / Tile.screenTileWidth), ty)) {
                    y += yMove;     //MOVE INTO NEW POSITION.
                }
                //there was a COLLISION, Creature's future y-position is inside a tile, re-adjust it to JUST outside tile.
                else {
                    //convert to pixel-coordinate by multiplying with Tile.screenTileHeight; minus 1 allows x-movement.
                    y = (ty * Tile.screenTileHeight) - bounds.y - bounds.height - 1;    //COLLISION WITH TILE.
                }

            }
            else {
                y = (ty * Tile.screenTileHeight) - bounds.y - bounds.height - 1;
            }
        }
        //moving UP (check top corners of Creature):
        else if (yMove < 0) {
            //future location (location trying to move into) (TOP side of Creature).
            int ty = (int)((y + bounds.y + yMove) / Tile.screenTileHeight);

            //check LEFT-top and RIGHT-top corners.
            //if within tiles (AND no-collision-with-solid), change the Creature's y-coordinate value.

            //not moving off the edge of tiles.
            if (isWithinTopEdgeOfMap( (int)(y + bounds.y + yMove) )) {

                if (!collisionWithTile((int) ((x + bounds.x) / Tile.screenTileWidth), ty) &&
                        !collisionWithTile((int) ((x + bounds.x + bounds.width) / Tile.screenTileWidth), ty)) {
                    y += yMove;     //MOVE INTO NEW POSITION.
                }
                //there was a COLLISION, Creature's future y-position is inside a tile, re-adjust it to JUST outside tile.
                else {
                    //convert to pixel-coordinate by multiplying with Tile.screenTileHeight.
                    y = (ty * Tile.screenTileHeight) + Tile.screenTileHeight - bounds.y;    //COLLISION WITH TILE.
                }

            } else {
                y = 0;
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

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

} // **** end Creature abstract class ****