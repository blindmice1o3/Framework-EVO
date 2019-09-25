package com.evo.entities.moveable.enemies.frogger;

import com.evo.Handler;
import com.evo.entities.moveable.Creature;
import com.evo.entities.moveable.player.IPlayable;
import com.evo.gfx.Animation;
import com.evo.gfx.Assets;
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

    public Frog(Handler handler, BufferedImage image, float x, float y, int width, int height) {
        super(handler, image, x, y, width, height);

        directionFacing = DirectionFacing.UP;
        experiencePoints = 0;
        healthMax = DEFAULT_HEALTH;

        initAnimations();

        speed = Tile.screenTileWidth;
    } // **** end Frog(Handler, BufferedImage, float, float, int, int) constructor ****

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

    private void getInput() {
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