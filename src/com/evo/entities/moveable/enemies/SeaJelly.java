package com.evo.entities.moveable.enemies;

import com.evo.Handler;
import com.evo.entities.Entity;
import com.evo.entities.moveable.Creature;
import com.evo.entities.moveable.player.Fish;
import com.evo.game_stages.GameStage;
import com.evo.gfx.Animation;
import com.evo.gfx.Assets;
import com.evo.items.Item;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;
import com.evo.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SeaJelly extends Creature {

    public enum State { IDLE, ATTACK, HURT; }
    public enum MovementDirection { DOWN, UP; }

    private State currentState;
    private MovementDirection currentMovementDirection;
    private int patrolLengthMax;
    private int currentPatrolLength;

    //ANIMATIONS
    private Animation idleAnimation, attackAnimation, hurtAnimation;

    public SeaJelly(Handler handler, float x, float y) {
        super(handler, null, x, y, Assets.seaJelly[0].getWidth(), Assets.seaJelly[0].getHeight());

        currentState = State.IDLE;
        currentMovementDirection = MovementDirection.DOWN;
        patrolLengthMax = 5;
        currentPatrolLength = 0;
        speed = 1;

        initAnimations();
    } // **** end SeaJelly(Handler, float, float) constructor ****

    @Override
    public void initAnimations() {
        BufferedImage[] idleFrames = new BufferedImage[3];
        idleFrames[0] = Assets.seaJelly[0];
        idleFrames[1] = Assets.seaJelly[1];
        idleFrames[2] = Assets.seaJelly[2];
        idleAnimation = new Animation(500, idleFrames);

        BufferedImage[] attackFrames = new BufferedImage[4];
        attackFrames[0] = Assets.seaJelly[3];
        attackFrames[1] = Assets.seaJelly[4];
        attackFrames[2] = Assets.seaJelly[5];
        attackFrames[3] = Assets.seaJelly[6];
        attackAnimation = new Animation(500, attackFrames);

        BufferedImage[] hurtFrames = new BufferedImage[1];
        hurtFrames[0] = Assets.seaJelly[7];
        hurtAnimation = new Animation(500, hurtFrames);
    }

    @Override
    public void hurt(int amount) {
        super.hurt(amount);

        ticker = 0;
        currentState = State.HURT;
    }

    @Override
    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        for (Entity e : ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getEntityManager().getEntities()) {
            //if the entity calling checkEntityCollisions(float, float) finds ITSELF in the collection, skip by continue.
            if (e.equals(this)) {
                continue;
            }
            //check if collision with player to perform hurt(int) call.
            else if (e instanceof Fish) {
                Fish player = (Fish)e;
                if (player.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                    ticker = 0;
                    currentState = State.ATTACK;

                    ///////////////////////
                    player.hurt(1);
                    ///////////////////////
                }
            }

            //check EACH entity to see if their collision bounds INTERSECTS with yours.
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }

        return false;
    }

    private int ticker = 0;
    @Override
    public void tick() {
        //ANIMATIONS
        idleAnimation.tick();
        attackAnimation.tick();
        hurtAnimation.tick();

        //MOVEMENTS: reset future-change-in-position to 0.
        xMove = 0;
        yMove = 0;

        //STATES
        switch (currentState) {
            case IDLE:
                //MOVEMENTS: PATROL (set values for future-change-in-position [based on moving up or down the screen]).
                if ( currentPatrolLength < (patrolLengthMax * Tile.screenTileHeight) ) {
                    switch (currentMovementDirection) {
                        case DOWN:
                            yMove = speed;

                            break;
                        case UP:
                            yMove = -speed;

                            break;
                        default:
                            System.out.println("SeaJelly.tick(), IF, switch(currentMovementDirection)'s default block.");
                            break;
                    }
                    ///////
                    move();
                    ///////
                    currentPatrolLength += speed;

                }
                //MOVEMENTS: END OF PATROL LENGTH (reverse directions).
                else {
                    switch (currentMovementDirection) {
                        case DOWN:
                            currentMovementDirection = MovementDirection.UP;
                            currentPatrolLength = 0;

                            break;
                        case UP:
                            currentMovementDirection = MovementDirection.DOWN;
                            currentPatrolLength = 0;

                            break;
                        default:
                            System.out.println("SeaJelly.tick(), ELSE, switch(currentMovementDirection)'s default block.");
                            break;
                    }
                }

                break;
            case ATTACK:
                ticker++;

                if (ticker == 40) {
                    ticker = 0;
                    currentState = State.IDLE;
                }

                break;
            case HURT:
                ticker++;

                if (ticker == 40) {
                    ticker = 0;
                    currentState = State.IDLE;
                }

                break;
            default:
                System.out.println("SeaJelly.tick(), switch (currentState)'s default block.");
                break;
        }

        // TODO: AoE (radius/collision-bound) that triggers State.ATTACK to be currentState, an entity-detection range.

        /*
         * TODO: CHASING feature...
         * if player intersects(AoE),
         * then mutate x and y depending on quadrant where player intersected from
         * (e.g. top-right quadrant: y -= moveSpeed, x += moveSpeed)
         * (e.g. top-left quadrant: y -= moveSpeed, x -= moveSpeed)
         * (e.g. bottom-left quadrant: y += moveSpeed, x -= moveSpeed)
         * (e.g. bottom-right quadrant: y += moveSpeed, x += moveSpeed)
         */
    }

    @Override
    public void render(Graphics g) {
       switch (currentState) {
           case IDLE:
               g.drawImage(idleAnimation.getCurrentFrame(),
                       (int)(x - handler.getGameCamera().getxOffset()),
                       (int)(y - handler.getGameCamera().getyOffset()),
                       idleAnimation.getCurrentFrame().getWidth(),
                       idleAnimation.getCurrentFrame().getHeight(),
                       null);

               break;
           case ATTACK:
               g.drawImage(attackAnimation.getCurrentFrame(),
                       (int)(x - handler.getGameCamera().getxOffset()),
                       (int)(y - handler.getGameCamera().getyOffset()),
                       attackAnimation.getCurrentFrame().getWidth(),
                       attackAnimation.getCurrentFrame().getHeight(),
                       null);

               break;
           case HURT:
               g.drawImage(hurtAnimation.getCurrentFrame(),
                       (int)(x - handler.getGameCamera().getxOffset()),
                       (int)(y - handler.getGameCamera().getyOffset()),
                       hurtAnimation.getCurrentFrame().getWidth(),
                       hurtAnimation.getCurrentFrame().getHeight(),
                       null);

               break;
           default:
               System.out.println("SeaJelly.render(Graphics), switch (currentState)'s default block.");
               break;
       }
    }

    @Override
    public void die() {
        System.out.println("I, an instance of a SeaJelly, have called die().");
        GameStage gameStage = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage();
        gameStage.getItemManager().addItem( Item.meatItem.createNew((int)x, (int)y) );
    }

} // **** end SeaJelly class ****