package com.evo.entities.moveable.enemies.frogger;

import com.evo.Handler;
import com.evo.entities.Entity;
import com.evo.entities.moveable.Creature;
import com.evo.entities.moveable.player.IPlayable;
import com.evo.gfx.Animation;
import com.evo.gfx.Assets;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;
import com.evo.tiles.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Frog extends Creature
        implements IPlayable {

    public enum DirectionFacing { UP, DOWN, LEFT, RIGHT; }

    private DirectionFacing directionFacing;

    //EXPERIENCE POINTS
    private int experiencePoints;

    //MAX_HEALTH
    private int healthMax;

    //ANIMATIONS
    private Animation upAnimation, downAnimation, leftAnimation, rightAnimation;

    public Frog(Handler handler, float x, float y) {
        super(handler, null, x, y, Tile.screenTileWidth, Tile.screenTileHeight);

        directionFacing = DirectionFacing.UP;
        experiencePoints = 0;
        healthMax = DEFAULT_HEALTH;

        initAnimations();

        speed = Tile.screenTileWidth;
    } // **** end Frog(Handler, float, float) constructor ****

    @Override
    public void initAnimations() {
        upAnimation = new Animation(500000000L, Assets.frogUp);
        downAnimation = new Animation(500000000L, Assets.frogDown);
        leftAnimation = new Animation(500000000L, Assets.frogLeft);
        rightAnimation = new Animation(500000000L, Assets.frogRight);
    }

    @Override
    public void tick(long timeElapsed) {
        //ANIMATIONS
        upAnimation.tick(timeElapsed);
        downAnimation.tick(timeElapsed);
        leftAnimation.tick(timeElapsed);
        rightAnimation.tick(timeElapsed);

        //MOVEMENT
        getInput();
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
                if (e instanceof Log) {
                    return false;
                } else {
                    return true;
                }
                ////////////////////////
            }
        }

        return false;
    }

    @Override
    public void getInput() {
        //reset future-move
        xMove = 0;
        yMove = 0;

        //up
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            yMove = -speed;
            directionFacing = DirectionFacing.UP;
        }
        //down
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            yMove = speed;
            directionFacing = DirectionFacing.DOWN;
        }
        //left
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
            xMove = -speed;
            directionFacing = DirectionFacing.LEFT;
        }
        //right
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
            xMove = speed;
            directionFacing = DirectionFacing.RIGHT;
        }
    }

    @Override
    public void render(Graphics g) {
        BufferedImage currentFrame;

        switch (directionFacing) {
            case UP:
                currentFrame = upAnimation.getCurrentFrame();

                break;
            case DOWN:
                currentFrame = downAnimation.getCurrentFrame();

                break;
            case LEFT:
                currentFrame = leftAnimation.getCurrentFrame();

                break;
            case RIGHT:
                currentFrame = rightAnimation.getCurrentFrame();

                break;
            default:
                System.out.println("Frog.render(Graphics), switch construct's default.");
                currentFrame = upAnimation.getCurrentFrame();
                break;
        }

        g.drawImage(currentFrame,
                (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()),
                (int)(x - handler.getGameCamera().getxOffset() + width),
                (int)(y - handler.getGameCamera().getyOffset() + height),
                0, 0, currentFrame.getWidth(), currentFrame.getHeight(),
                null);
    }

    @Override
    public void die() {
        System.out.println("Frog.die()... game session is over.");
    }

    // GETTERS AND SETTERS

    @Override
    public int getExperiencePoints() {
        return experiencePoints;
    }

    @Override
    public int getHealthMax() {
        return healthMax;
    }

} // **** end Frog class ****