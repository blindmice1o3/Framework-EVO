package com.evo.entities.moveable.enemies.frogger;

import com.evo.Handler;
import com.evo.entities.moveable.Creature;
import com.evo.game_stages.GameStage;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;
import com.evo.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Car extends Creature {

    public enum MovementDirection { LEFT, RIGHT; }

    private MovementDirection currentMovementDirection;

    public Car(Handler handler, BufferedImage image, MovementDirection currentMovementDirection,
               float x, float y, int width, int height) {
        super(handler, image, x, y, width, height);
        this.currentMovementDirection = currentMovementDirection;
        active = true;
    } // **** end Car(Handler, BufferedImage, MovementDirection, float, float, int, int) constructor ****

    @Override
    public void initAnimations() {

    }

    @Override
    public void tick(long timeElapsed) {
        xMove = 0;
        yMove = 0;

        switch (currentMovementDirection) {
            case LEFT:
                if (x > 0) {
                    xMove = -speed;
                } else {
                    die();
                }

                break;
            case RIGHT:
                GameStage currentGameStage = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage();
                if ( (x+width+1) < ((currentGameStage.getWidthInNumOfTile()) * Tile.screenTileWidth) ) {
                    xMove = speed;
                } else {
                    die();
                }

                break;
            default:
                System.out.println("Car.tick(), switch construct's default.");
                break;
        }

        move();
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
        active = false;
    }

} // **** end Car class ****