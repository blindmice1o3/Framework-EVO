package com.evo.entities.moveable.enemies.frogger;

import com.evo.Handler;
import com.evo.entities.moveable.Creature;

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

        if (x > 0) {
            xMove = -speed;
        } else {
            die();
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