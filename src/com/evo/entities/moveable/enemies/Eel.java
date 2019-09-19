package com.evo.entities.moveable.enemies;

import com.evo.Handler;
import com.evo.Utils;
import com.evo.entities.moveable.Creature;
import com.evo.entities.moveable.player.Fish;
import com.evo.game_stages.GameStage;
import com.evo.gfx.Animation;
import com.evo.gfx.Assets;
import com.evo.items.Item;
import com.evo.quests.Quest;
import com.evo.quests.QuestGiver;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;
import com.evo.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Eel extends Creature
        implements QuestGiver {

    public enum State { PATROL, TURN, CHASE, ATTACK, HURT; }
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

        detectionRectangleBounds = new Rectangle(
                -detectionRadiusLength + (width/2), //NEGATIVE
                -detectionRadiusLength + (height/2), //NEGATIVE
                2*detectionRadiusLength,
                2*detectionRadiusLength);
    } // **** end Eel(Handler, float, float) constructor

    @Override
    public void initAnimations() {
        //LEFT
        BufferedImage[] patrolFrames = new BufferedImage[4];
        patrolFrames[0] = Assets.eel[0];
        patrolFrames[1] = Assets.eel[1];
        patrolFrames[2] = Assets.eel[2];
        patrolFrames[3] = Assets.eel[3];
        patrolAnimation = new Animation(500000000L, patrolFrames);

        //!!!RIGHT!!!-TURNING-LEFT
        BufferedImage[] turnFrames = new BufferedImage[1];
        turnFrames[0] = Assets.eel[4];
        turnAnimation = new Animation(500000000L, turnFrames);

        //LEFT
        BufferedImage[] attackFrames = new BufferedImage[2];
        attackFrames[0] = Assets.eel[4];
        attackFrames[1] = Assets.eel[5];
        attackAnimation = new Animation(500000000L, attackFrames);

        //LEFT
        BufferedImage[] hurtFrames = new BufferedImage[1];
        hurtFrames[0] = Assets.eel[6];
        hurtAnimation = new Animation(500000000L, hurtFrames);
    }

    private boolean questGiven = false;
    @Override
    public void hurt(int amount) {
        super.hurt(amount);

        ticker = 0;
        currentState = State.HURT;

        if (!questGiven) {
            String questMessage = "Ouch, you bit me! Tell you what, if you leave me alone and eat those damn photosynthesizers near the surface of the water column, I'll help you later.";
            Object[] args = { questMessage };
            handler.getStateManager().pushIState(StateManager.State.TEXTBOX, args);

            //add quest to player's QuestManager.
            ((GameStageState) handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getEntityManager().getPlayer().getQuestManager().addQuest(giveQuest());
            questGiven = true;
        } else {
            if ( checkQuestCompletion() ) {
                if ( ((GameStageState) handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getEntityManager().getPlayer().getQuestManager().findQuest("Kelp") != null ) {
                    //set active to false.
                    ((GameStageState) handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getEntityManager().getPlayer().getQuestManager().findQuest("Kelp").setActive(false);
                }
                giveReward();
            } else {
                String incompleteQuestMessage = "You haven't eaten enough of them dagnabit overgrown phytoplanktons, eat some more kelp before checking back!";
                Object[] args = { incompleteQuestMessage };
                handler.getStateManager().pushIState(StateManager.State.TEXTBOX, args);
            }
        }
    }

    //TODO: checkEntityCollisions(float, float)

    @Override
    public Quest giveQuest() {
        Quest quest = new Quest(handler, "Kelp", 3);
        quest.setActive(true);

        return quest;
    }

    @Override
    public boolean checkQuestCompletion() {
        if ( ((GameStageState) handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getEntityManager().getPlayer().getQuestManager().findQuest("Kelp")
                != null ) {
            Quest kelpQuest = ((GameStageState) handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getEntityManager().getPlayer().getQuestManager().findQuest("Kelp");

            if (kelpQuest.getCurrentCount() >= kelpQuest.getRequiredCount()) {
                return true;
            }
        }

        //if here, Quest requirements have not been met.
        return false;
    }

    @Override
    public void giveReward() {
        //currently just a message to see if it's working, no real reward given.
        String rewardForQuestCompletion = "You did it! Here's something for your efforts.";
        Object[] args = { rewardForQuestCompletion };
        handler.getStateManager().pushIState(StateManager.State.TEXTBOX, args);
    }


    private int ticker = 0;
    @Override
    public void tick(long timeElapsed) {
        //ANIMATIONS
        patrolAnimation.tick(timeElapsed);
        turnAnimation.tick(timeElapsed);
        attackAnimation.tick(timeElapsed);
        hurtAnimation.tick(timeElapsed);

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

                    //CHECK DETECTION COLLISION (is player within detection range?)
                    if ( checkDetectionCollisions(0, 0) ) {
                        xBeforeChase = (int)x;
                        yBeforeChase = (int)y;
                        ///////////////////////////
                        currentState = State.CHASE;
                        ///////////////////////////
                    }
                }
                //MOVEMENTS: END OF PATROL LENGTH (reverse directions).
                else {
                    //////////////////////////
                    currentState = State.TURN;
                    //////////////////////////
                }

                break;
            case CHASE:
                //TESTING checkDetectionCollisions(float, float)
                Fish player = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();
                //player is beyond detection range.
                if ( (Math.abs(player.getX() - x) > detectionRadiusLength) ||
                        (Math.abs(player.getY() - y) > detectionRadiusLength) ) {
                    System.out.println("awwww........ like sand slipping through the fingers (whatever those are).");

                    //TODO: call unimplemented method named: moveToBeforeChaseCoordinate().
                    //!!!RETURNING TO PATROL POSITION!!!
                    //to return to patrol position, have to move left.
                    if (x > xBeforeChase) {
                        xMove = -speed;
                        currentMovementDirection = MovementDirection.LEFT;
                    }
                    //to return to patrol position, have to move right.
                    else if (x < xBeforeChase) {
                        xMove = speed;
                        currentMovementDirection = MovementDirection.RIGHT;
                    }

                    //to return to patrol position, have to move up.
                    if (y > yBeforeChase) {
                        yMove = -speed;
                    }
                    //to return to patrol position, have to move down.
                    else if (y < yBeforeChase) {
                        yMove = speed;
                    }
                    ///////
                    move();
                    ///////

                    //ONCE BACK TO PATROL POSITION, change to State.PATROL.
                    if ( (x == xBeforeChase) && (y == yBeforeChase) ) {
                        ////////////////////////////
                        currentState = State.PATROL;
                        ////////////////////////////
                    }
                }
                //still chasing: move() Eel and see if hurt() should be called.
                else {
                    System.out.println("IMMA GETCHA!");
                    //player is towards eel's left, chase left.
                    if (player.getX() < x) {
                        xMove = -speed;
                        currentMovementDirection = MovementDirection.LEFT;
                    }
                    //player is towards eel's right, chase right.
                    else if (player.getX() > x) {
                        xMove = speed;
                        currentMovementDirection = MovementDirection.RIGHT;
                    }

                    //player is above eel, chase upward.
                    if (player.getY() < y) {
                        yMove = -speed;
                    }
                    //player is below eel, chase downward.
                    else if (player.getY() > y) {
                        yMove = speed;
                    }
                    ///////
                    move();
                    ///////

                    //TODO: check if intersection (hurt() will be called).
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

    private Rectangle detectionRectangleBounds;
    private int detectionRadiusLength = 8 * Tile.TILE_WIDTH;
    private int xBeforeChase, yBeforeChase;

    private Rectangle getDetectionRectangle(float xOffset, float yOffset) {
        return new Rectangle(
                (int)(x + detectionRectangleBounds.x + xOffset),
                (int)(y + detectionRectangleBounds.y + yOffset),
                detectionRectangleBounds.width,
                detectionRectangleBounds.height);
    }

    private boolean checkDetectionCollisions(float xOffset, float yOffset) {
        Fish player = ((GameStageState) handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getEntityManager().getPlayer();

        if (player.getCollisionBounds(0f, 0f).intersects(getDetectionRectangle(xOffset, yOffset))) {
            return true;
        }

        return false;
    }

    @Override
    public void render(Graphics g) {
        switch (currentState) {
            case PATROL:

                ///////////////////////////detection-rectangle (developer's visual aid)/////////////////////////////////
                Rectangle detectionSquare = getDetectionRectangle(0, 0);
                g.setColor(Color.GREEN);
                g.fillRect(
                        (int)(detectionSquare.x - handler.getGameCamera().getxOffset()),
                        (int)(detectionSquare.y - handler.getGameCamera().getyOffset()),
                        detectionSquare.width,
                        detectionSquare.height);
                //System.out.println("detectionSquare's: x, y, width, height (" + detectionSquare.x + ", " +
                //        detectionSquare.y + ", " + detectionSquare.width + ", " + detectionSquare.height + ").");
                //System.out.println("Eel's x, y, width, height (" + x + ", " + y + ", " + width + ", " + height + ").");
                ////////////////////////////////////////////////////////////////////////////////////////////////////////


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
            case CHASE:
                //TODO: this was done haphazardly just to see effects and know things are happening as expected or not.

                //pretty much copied/pasted State.PATROL, but using the attack animation instead.
                ///////////////////////////detection-rectangle (developer's visual aid)/////////////////////////////////
                detectionSquare = getDetectionRectangle(0, 0);
                g.setColor(Color.GREEN);
                g.fillRect(
                        (int)(detectionSquare.x - handler.getGameCamera().getxOffset()),
                        (int)(detectionSquare.y - handler.getGameCamera().getyOffset()),
                        detectionSquare.width,
                        detectionSquare.height);
                //System.out.println("detectionSquare's: x, y, width, height (" + detectionSquare.x + ", " +
                //        detectionSquare.y + ", " + detectionSquare.width + ", " + detectionSquare.height + ").");
                //System.out.println("Eel's x, y, width, height (" + x + ", " + y + ", " + width + ", " + height + ").");
                ////////////////////////////////////////////////////////////////////////////////////////////////////////


                //MOVING LEFT
                if (currentMovementDirection == MovementDirection.LEFT) {
                    g.drawImage(attackAnimation.getCurrentFrame(),
                            (int)(x - handler.getGameCamera().getxOffset()),
                            (int)(y - handler.getGameCamera().getyOffset()),
                            attackAnimation.getCurrentFrame().getWidth(),
                            attackAnimation.getCurrentFrame().getHeight(),
                            null);
                }
                //MOVING RIGHT
                else if (currentMovementDirection == MovementDirection.RIGHT) {
                    ////////////////////////////////
                    BufferedImage flippedAttackImage = Utils.flipHorizontally(attackAnimation.getCurrentFrame());
                    ////////////////////////////////
                    g.drawImage(flippedAttackImage,
                            (int)(x - handler.getGameCamera().getxOffset()),
                            (int)(y - handler.getGameCamera().getyOffset()),
                            flippedAttackImage.getWidth(),
                            flippedAttackImage.getHeight(),
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