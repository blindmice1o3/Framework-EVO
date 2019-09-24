package com.evo.entities.moveable.enemies.frogger;

import com.evo.Handler;
import com.evo.entities.Entity;
import com.evo.entities.moveable.Creature;
import com.evo.entities.moveable.player.Fish;
import com.evo.entities.moveable.player.FishStateManager;
import com.evo.game_stages.GameStage;
import com.evo.game_stages.hud.ComponentHUD;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;
import com.evo.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Car extends Creature {

    public enum MovementDirection { LEFT, RIGHT; }

    private MovementDirection currentMovementDirection;
    private long attackCooldown, attackTimer;

    public Car(Handler handler, BufferedImage image, MovementDirection currentMovementDirection,
               float x, float y, int width, int height) {
        super(handler, image, x, y, width, height);
        this.currentMovementDirection = currentMovementDirection;
        active = true;

        attackCooldown = 800000000L;
        attackTimer = attackCooldown;
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
                    active = false;
                }

                break;
            case RIGHT:
                GameStage currentGameStage = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage();
                if ( (x+width+1) < ((currentGameStage.getWidthInNumOfTile()) * Tile.screenTileWidth) ) {
                    xMove = speed;
                } else {
                    active = false;
                }

                break;
            default:
                System.out.println("Car.tick(), switch construct's default.");
                break;
        }

        tickAttackCooldown(timeElapsed);
        move();
    }

    private void tickAttackCooldown(long timeElapsed) {
        attackTimer += timeElapsed;
        //attackTimer gets reset to 0 in overridden checkEntityCollisions(float, float).
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
                //@@@IF COLLISION WITH PLAYER, SET ACTIVE TO false.@@@
                if (e instanceof Fish) {
                    if (attackTimer < attackCooldown) {
                        return true;
                    }
                    ////////////////////////////////////////////////////////
                    int attackDamage = 1;
                    ComponentHUD damageHUD = new ComponentHUD(handler, ComponentHUD.ComponentType.DAMAGE, attackDamage, e);
                    GameStage gameStage = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage();
                    gameStage.getHeadUpDisplay().addTimedNumericIndicator(damageHUD);
                    ////////////////////////////////////////////////////////

                    e.hurt(attackDamage);
                    ((Fish)e).getFishStateManager().setCurrentActionState(FishStateManager.ActionState.HURT);
                    //IF HERE: we've attacked, must reset the attackTimer.
                    attackTimer = 0;

                    //e.setActive(false);
                    //e.die();
                }

                return true;
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

} // **** end Car class ****