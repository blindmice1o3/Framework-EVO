package com.evo.entities.moveable.enemies;

import com.evo.Handler;
import com.evo.Utils;
import com.evo.entities.moveable.Creature;
import com.evo.game_stages.GameStage;
import com.evo.gfx.Animation;
import com.evo.gfx.Assets;
import com.evo.items.Item;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;
import com.evo.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Eel extends Creature {

    public enum State { PATROL, TURN, ATTACK, HURT; }
    public enum MovementDirection { LEFT, RIGHT; }

    private State currentState;
    private MovementDirection currentMovementDirection;
    private int patrolLengthMax;
    private int currentPatrolLength;

    //ANIMATIONS
    private Animation patrolAnimation, turnAnimation, attackAnimation, hurtAnimation;

    public Eel(Handler handler, float x, float y) {
        super(handler, null, x, y, Assets.eel[0].getWidth(), Assets.eel[0].getHeight());

        currentState = State.PATROL;
        currentMovementDirection = MovementDirection.LEFT;
        patrolLengthMax = 5;
        currentPatrolLength = 0;
        speed = 1;

        initAnimations();
    } // **** end Eel(Handler, float, float) constructor

    @Override
    public void initAnimations() {
        //LEFT
        BufferedImage[] patrolFrames = new BufferedImage[4];
        patrolFrames[0] = Assets.eel[0];
        patrolFrames[1] = Assets.eel[1];
        patrolFrames[2] = Assets.eel[2];
        patrolFrames[3] = Assets.eel[3];
        patrolAnimation = new Animation(500, patrolFrames);

        //!!!RIGHT!!!-TURNING-LEFT
        BufferedImage[] turnFrames = new BufferedImage[1];
        turnFrames[0] = Assets.eel[4];
        turnAnimation = new Animation(500, turnFrames);

        //LEFT
        BufferedImage[] attackFrames = new BufferedImage[2];
        attackFrames[0] = Assets.eel[4];
        attackFrames[1] = Assets.eel[5];
        attackAnimation = new Animation(500, attackFrames);

        //LEFT
        BufferedImage[] hurtFrames = new BufferedImage[1];
        hurtFrames[0] = Assets.eel[6];
        hurtAnimation = new Animation(500, hurtFrames);
    }

    @Override
    public void hurt(int amount) {
        super.hurt(amount);

        ticker = 0;
        currentState = State.HURT;
    }

    //TODO: checkEntityCollisions(float, float)


    private int ticker = 0;
    @Override
    public void tick() {
        //ANIMATIONS
        patrolAnimation.tick();
        turnAnimation.tick();
        attackAnimation.tick();
        hurtAnimation.tick();

        //MOVEMENTS: reset future-change-in-position to 0.
        xMove = 0;
        yMove = 0;

        //STATES
        switch (currentState) {
            case PATROL:
                //MOVEMENTS: PATROL (set values for future-change-in-position [based on moving left or right]).
                if ( currentPatrolLength < (patrolLengthMax * Tile.screenTileWidth) ) {
                    switch (currentMovementDirection) {
                        case LEFT:
                            xMove = -speed;

                            break;
                        case RIGHT:
                            xMove = speed;

                            break;
                        default:
                            System.out.println("Eel.tick(), IF, switch(currentMovementDirection)'s default block.");
                            break;
                    }
                    ///////
                    move();
                    ///////
                    currentPatrolLength += speed; //TODO: may need to move into overridden move().
                }
                //MOVEMENTS: END OF PATROL LENGTH (reverse directions).
                else {
                    //////////////////////////
                    currentState = State.TURN;
                    //////////////////////////
                }

                break;
            case TURN:
                switch (currentMovementDirection) {
                    case LEFT:
                        currentMovementDirection = MovementDirection.RIGHT;
                        currentPatrolLength = 0;

                        break;
                    case RIGHT:
                        currentMovementDirection = MovementDirection.LEFT;
                        currentPatrolLength = 0;

                        break;
                    default:
                        System.out.println("Eel.tick(), ELSE, switch(currentMovementDirection)'s default block.");
                        break;
                }
                ////////////////////////////
                currentState = State.PATROL;
                ////////////////////////////

                break;
            case ATTACK:
                ticker++;

                //TODO: is this attack-timer-target long enough to iterate through all 2 attackFrames images???
                //make transition-back-to-State.PATROL be based on the index of attackFrames???
                if (ticker == 40) {
                    ticker = 0;

                    ////////////////////////////
                    currentState = State.PATROL;
                    ////////////////////////////
                }

                break;
            case HURT:
                ticker++;

                //only has 1 hurtFrames image, so transition-back-to-State.PATROL
                //CAN BE BASED ON A TIME LIMIT (as oppose to State.ATTACK being based on its Animation's index).
                if (ticker == 40) {
                    ticker = 0;

                    ////////////////////////////
                    currentState = State.PATROL;
                    ////////////////////////////
                }

                break;
            default:
                System.out.println("Eel.tick(), switch (currentState)'s default block.");
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
            case PATROL:
                //MOVING LEFT
                if (currentMovementDirection == MovementDirection.LEFT) {
                    g.drawImage(patrolAnimation.getCurrentFrame(),
                            (int)(x - handler.getGameCamera().getxOffset()),
                            (int)(y - handler.getGameCamera().getyOffset()),
                            patrolAnimation.getCurrentFrame().getWidth(),
                            patrolAnimation.getCurrentFrame().getHeight(),
                            null);
                }
                //MOVING RIGHT
                else if (currentMovementDirection == MovementDirection.RIGHT) {
                    ////////////////////////////////
                    BufferedImage flippedPatrolImage = Utils.flipHorizontally(patrolAnimation.getCurrentFrame());
                    ////////////////////////////////
                    g.drawImage(flippedPatrolImage,
                            (int)(x - handler.getGameCamera().getxOffset()),
                            (int)(y - handler.getGameCamera().getyOffset()),
                            flippedPatrolImage.getWidth(),
                            flippedPatrolImage.getHeight(),
                            null);
                }

                break;
            case TURN:
                //left-turning-right
                //IT'S opposite because tick() for State.TURN changed currentMovementDirection prior to this render() call.
                if (currentMovementDirection == MovementDirection.RIGHT) {
                    ////////////////////////////////
                    BufferedImage flippedTurnImage = Utils.flipHorizontally(turnAnimation.getCurrentFrame());
                    ////////////////////////////////
                    g.drawImage(flippedTurnImage,
                            (int) (x - handler.getGameCamera().getxOffset()),
                            (int) (y - handler.getGameCamera().getyOffset()),
                            turnAnimation.getCurrentFrame().getWidth(),
                            turnAnimation.getCurrentFrame().getHeight(),
                            null);
                }
                //right-turning-left
                else if (currentMovementDirection == MovementDirection.LEFT) {
                    g.drawImage(turnAnimation.getCurrentFrame(),
                            (int) (x - handler.getGameCamera().getxOffset()),
                            (int) (y - handler.getGameCamera().getyOffset()),
                            turnAnimation.getCurrentFrame().getWidth(),
                            turnAnimation.getCurrentFrame().getHeight(),
                            null);
                }

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
                System.out.println("Eel.render(Graphics), switch (currentState)'s default block.");
                break;
        }
    }

    @Override
    public void die() {
        System.out.println("I, an instance of an Eel, have called die().");
        GameStage gameStage = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage();
        gameStage.getItemManager().addItem( Item.meatItem.createNew((int)x, (int)y) );
    }

} // **** end Eel class ****