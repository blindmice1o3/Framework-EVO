package com.evo.entities.moveable.player;

import com.evo.Handler;
import com.evo.Utils;
import com.evo.entities.Entity;
import com.evo.entities.moveable.Creature;
import com.evo.gfx.Animation;
import com.evo.gfx.Assets;
import com.evo.quests.QuestManager;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Fish extends Creature {

    public enum DirectionFacing { LEFT, RIGHT; }

    //QUESTS
    //TODO: make compatible with SaverAndLoader class (remove the transient keyword).
    private transient QuestManager questManager;

    private FishStateManager fishStateManager;
    private DirectionFacing directionFacing;

    //EXPERIENCE POINTS
    private int experiencePoints;

    //MAX_HEALTH
    private int healthMax;

    //ANIMATIONS
    private Animation idleHeadAnimation, eatHeadAnimation, biteHeadAnimation, hurtHeadAnimation;
    private Animation currentHeadAnimation;
    private Animation currentBodyAnimation;

    //ATTACK TIMER
    private long attackCooldown = 800000000L, attackTimer = attackCooldown;

    public Fish(Handler handler, float x, float y) {
        super(handler, null, x, y,
                Assets.eatFrames[0][0][0][0][0].getWidth()
                        + Assets.tailOriginal[0][0][0][0][0].getWidth(),
                Assets.eatFrames[0][0][0][0][0].getHeight());
        System.out.println("Fish.constructor (width/height): " + width + "/" + height);

        questManager = new QuestManager(handler);

        experiencePoints = 0;
        healthMax = DEFAULT_HEALTH;

        fishStateManager = new FishStateManager();
        directionFacing = DirectionFacing.RIGHT;

        initAnimations();

/*
        currentHeadImage = Assets.eatFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [fishStateManager.getCurrentActionState().ordinal()]
                [0];
        currentBodyImage = Assets.tailOriginal[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentFinPectoral().ordinal()]
                [fishStateManager.getCurrentTail().ordinal()]
                [0];
*/

        bounds.x = 2;
        bounds.y = 1;
        bounds.width = 28;
        bounds.height = 13;
    } // **** end Fish(Handler, float, float) constructor ****

    @Override
    public void initAnimations() {
        initHeadAnimations();
        currentBodyAnimation = new Animation(600000000L,
                Assets.tailOriginal[fishStateManager.getCurrentBodySize().ordinal()]
                        [fishStateManager.getCurrentBodyTexture().ordinal()]
                        [fishStateManager.getCurrentFinPectoral().ordinal()]
                        [fishStateManager.getCurrentTail().ordinal()]);
    }

    private void initHeadAnimations() {
        //for FishStateManager.ActionState.NONE
        BufferedImage[] idleFrames = new BufferedImage[1];
        idleFrames[0] = Assets.eatFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.EAT.ordinal()]
                [0];
        idleHeadAnimation = new Animation(400000000L, idleFrames);

        //for FishStateManager.ActionState.HURT
        BufferedImage[] hurtFrames = new BufferedImage[1];
        hurtFrames[0] = Assets.hurtFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.HURT.ordinal()]
                [0];
        hurtHeadAnimation = new Animation(400000000L, hurtFrames);

        //for FishStateManager.ActionState.EAT
        BufferedImage[] eatFrames = new BufferedImage[3];
        eatFrames[0] = Assets.eatFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.EAT.ordinal()]
                [1];
        eatFrames[1] = Assets.eatFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.EAT.ordinal()]
                [2];
        eatFrames[2] = Assets.biteFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.BITE.ordinal()]
                [2];
        eatHeadAnimation = new Animation(400000000L, eatFrames);

        //for FishStateManager.ActionState.BITE
        BufferedImage[] biteFrames = new BufferedImage[3];
        biteFrames[0] = Assets.biteFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.BITE.ordinal()]
                [0];
        biteFrames[1] = Assets.biteFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.BITE.ordinal()]
                [1];
        biteFrames[2] = Assets.hurtFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.HURT.ordinal()]
                [1];
        biteHeadAnimation = new Animation(400000000L, biteFrames);

        /////////////////////////////////////////
        currentHeadAnimation = idleHeadAnimation;
        /////////////////////////////////////////
    }

    private int hurtTimer = 0;
    private int hurtTimerTarget = 20;
    @Override
    public void tick(long timeElapsed) {
        //HEAD ANIMATION
        switch (fishStateManager.getCurrentActionState()) {
            case HURT:
                currentHeadAnimation = hurtHeadAnimation;
                //no need tick (1 frame).
                hurtTimer++;

                if (hurtTimer == hurtTimerTarget) {
                    fishStateManager.setCurrentActionState(FishStateManager.ActionState.NONE);

                    hurtTimer = 0;
                }

                break;
            case BITE:
                currentHeadAnimation.tick(timeElapsed);

                if (currentHeadAnimation.getIndex() == currentHeadAnimation.getFrames().length-1) {
                    //////////////////////////////////////////////////////////////////////////
                    fishStateManager.setCurrentActionState(FishStateManager.ActionState.NONE);
                    //////////////////////////////////////////////////////////////////////////
                }

                break;
            case EAT:
                currentHeadAnimation.tick(timeElapsed);

                if (currentHeadAnimation.getIndex() == currentHeadAnimation.getFrames().length-1) {
                    //////////////////////////////////////////////////////////////////////////
                    fishStateManager.setCurrentActionState(FishStateManager.ActionState.NONE);
                    //////////////////////////////////////////////////////////////////////////
                }

                break;
            case NONE:
                currentHeadAnimation = idleHeadAnimation;
                //no need tick (1 frame).

                break;
            default:
                System.out.println("Fish.tick(), switch-construct's default.");
        }
        //BODY ANIMATION
        currentBodyAnimation.tick(timeElapsed);

/*
        currentHeadImage = Assets.eatFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [fishStateManager.getCurrentActionState().ordinal()]
                [0];
        currentBodyImage = Assets.tailOriginal[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentFinPectoral().ordinal()]
                [fishStateManager.getCurrentTail().ordinal()]
                [0];
*/

        // ATTACK COOLDOWN
        tickAttackCooldown(timeElapsed);

        // MOVEMENT
        getInput();
        move();
    }

    private void tickAttackCooldown(long timeElapsed) {
        attackTimer += timeElapsed;
        //attackTimer gets reset to 0 in getInput()'s attack-button pressed.
    }

    @Override
    public void die() {
        System.out.println("Game over.");
    }

    public void getInput() {
        // MOVEMENT
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().up) {
            yMove = -speed;
        }
        if (handler.getKeyManager().down) {
            yMove = speed;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
            ////////////////////////////////////////
            directionFacing = DirectionFacing.LEFT;
            ////////////////////////////////////////
        }
        if (handler.getKeyManager().right) {
            xMove = speed;
            ////////////////////////////////////////
            directionFacing = DirectionFacing.RIGHT;
            ////////////////////////////////////////
        }

        // A and X BUTTONS (B BUTTON in GameStageState class: pops IState).
        //x-button (eat).
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) {
            //////////////////////////////////////////////////////////////////////////
            currentHeadAnimation = eatHeadAnimation;
            currentHeadAnimation.resetIndex();
            fishStateManager.setCurrentActionState(FishStateManager.ActionState.EAT);
            //////////////////////////////////////////////////////////////////////////
        }
        //a-button (bite).
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            if (attackTimer < attackCooldown) {
                return;
            }
            //////////////////////////////////////////////////////////////////////////
            currentHeadAnimation = biteHeadAnimation;
            currentHeadAnimation.resetIndex();
            fishStateManager.setCurrentActionState(FishStateManager.ActionState.BITE);
            //////////////////////////////////////////////////////////////////////////

            //cb is "Collision Bounds", which is the collision rectangle of the player.
            Rectangle cb = getCollisionBounds(0, 0);
            //ar is "Attack Rectangle", which is the collision rectangle of an attack.
            Rectangle ar = new Rectangle();

            ar.width = Assets.eatFrames[0][0][0][0][0].getWidth();
            ar.height = Assets.eatFrames[0][0][0][0][0].getHeight();
            //check where to set the attack rectangle.
            if (directionFacing == DirectionFacing.LEFT) {
                ar.x = cb.x - 4;
                ar.y = cb.y;
            } else if (directionFacing == DirectionFacing.RIGHT) {
                ar.x = cb.x + Assets.tailOriginal[0][0][0][0][0].getWidth() + 3;
                ar.y = cb.y;
            }

            // if we're at this line, we didn't call return in the else-clause; which means the attack button was pressed
            // and we need to reset the attackTimer (a cool-down time before the player can attack again).
            attackTimer = 0;

            //LOOP through every entity in the current game stage.
            ArrayList<Entity> entities = ((GameStageState) handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getEntityManager().getEntities();
            for (Entity e : entities) {
                //if current Entity e is the player, go on to the next entity object from the ArrayList<Entity>.
                if (e.equals(this)) {
                    continue;
                }

                //if there is a collision between the entity and the player's attack rectangle, hurt the entity.
                if (e.getCollisionBounds(0, 0).intersects(ar)) {
                    e.hurt(1);

                    //////////////////////////////////////////////////////////////////////////
                    //RETURN if there is collision BECAUSE CAN ONLY HURT 1 ENTITY AT A TIME.//
                    //////////////////////////////////////////////////////////////////////////
                    return;
                }
            }
        }

        // BODY-PARTS SWAPPING
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_Z)) {
            int currentBodyTextureOrdinal = fishStateManager.getCurrentBodyTexture().ordinal();
            FishStateManager.BodyTexture[] bodyTexture = FishStateManager.BodyTexture.values();

            if ((currentBodyTextureOrdinal+1) < bodyTexture.length) {
                fishStateManager.setCurrentBodyTexture(bodyTexture[currentBodyTextureOrdinal + 1]);
            } else {
                fishStateManager.setCurrentBodyTexture(bodyTexture[0]);
            }

            //TODO: inefficient, (though unlikely) could be returning to an already-existing Animation object.
            initHeadAnimations();
            currentBodyAnimation = new Animation(600000000L,
                    Assets.tailOriginal[fishStateManager.getCurrentBodySize().ordinal()]
                            [fishStateManager.getCurrentBodyTexture().ordinal()]
                            [fishStateManager.getCurrentFinPectoral().ordinal()]
                            [fishStateManager.getCurrentTail().ordinal()]);
        }
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_X)) {
            int currentJawsOrdinal = fishStateManager.getCurrentJaws().ordinal();
            FishStateManager.Jaws[] jaws = FishStateManager.Jaws.values();

            if ((currentJawsOrdinal+1) < jaws.length) {
                fishStateManager.setCurrentJaws(jaws[currentJawsOrdinal + 1]);
            } else {
                fishStateManager.setCurrentJaws(jaws[0]);
            }

            //TODO: inefficient, (though unlikely) could be returning to an already-existing Animation object.
            initHeadAnimations();
            currentBodyAnimation = new Animation(600000000L,
                    Assets.tailOriginal[fishStateManager.getCurrentBodySize().ordinal()]
                            [fishStateManager.getCurrentBodyTexture().ordinal()]
                            [fishStateManager.getCurrentFinPectoral().ordinal()]
                            [fishStateManager.getCurrentTail().ordinal()]);
        }
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)) {
            int currentFinPectoralOrdinal = fishStateManager.getCurrentFinPectoral().ordinal();
            FishStateManager.FinPectoral[] finPectoral = FishStateManager.FinPectoral.values();

            if ((currentFinPectoralOrdinal+1) < finPectoral.length) {
                fishStateManager.setCurrentFinPectoral(finPectoral[currentFinPectoralOrdinal + 1]);
            } else {
                fishStateManager.setCurrentFinPectoral(finPectoral[0]);
            }

            //TODO: inefficient, (though unlikely) could be returning to an already-existing Animation object.
            initHeadAnimations();
            currentBodyAnimation = new Animation(600000000L,
                    Assets.tailOriginal[fishStateManager.getCurrentBodySize().ordinal()]
                            [fishStateManager.getCurrentBodyTexture().ordinal()]
                            [fishStateManager.getCurrentFinPectoral().ordinal()]
                            [fishStateManager.getCurrentTail().ordinal()]);
        }

    }

    @Override
    public void render(Graphics g) {
        //ACTUAL IMAGE OF FISH
        if (directionFacing == DirectionFacing.RIGHT) {
            //BODY
            g.drawImage(currentBodyAnimation.getCurrentFrame(),
                    (int)(x - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset()),
                    Assets.tailOriginal[0][0][0][0][0].getWidth(),
                    Assets.tailOriginal[0][0][0][0][0].getHeight(),
                    null);

            /*
            g.drawImage(currentBodyAnimation.getCurrentFrame(),
                    (int)(x - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset()),
                    (int)(x - handler.getGameCamera().getxOffset() + Assets.tailOriginal[0][0][0][0][0].getWidth()),
                    (int)(y - handler.getGameCamera().getyOffset() + Assets.tailOriginal[0][0][0][0][0].getHeight()),
                    0,
                    0,
                    currentBodyAnimation.getCurrentFrame().getWidth(),
                    currentBodyAnimation.getCurrentFrame().getHeight(),
                    null);
            */

            //HEAD
            g.drawImage(currentHeadAnimation.getCurrentFrame(),
                    (int)(x - handler.getGameCamera().getxOffset() + Assets.tailOriginal[0][0][0][0][0].getWidth()),
                    (int)(y - handler.getGameCamera().getyOffset()),
                    Assets.eatFrames[0][0][0][0][0].getWidth(),
                    Assets.eatFrames[0][0][0][0][0].getHeight(),
                    null);

            /*
            g.drawImage(currentHeadAnimation.getCurrentFrame(),
                    (int)(x - handler.getGameCamera().getxOffset() + Assets.tailOriginal[0][0][0][0][0].getWidth()),
                    (int)(y - handler.getGameCamera().getyOffset()),
                    (int)(x - handler.getGameCamera().getxOffset() + Assets.tailOriginal[0][0][0][0][0].getWidth() + Assets.eatFrames[0][0][0][0][0].getWidth()),
                    (int)(y - handler.getGameCamera().getyOffset() + Assets.eatFrames[0][0][0][0][0].getHeight()),
                    0,
                    0,
                    currentHeadAnimation.getCurrentFrame().getWidth(),
                    currentHeadAnimation.getCurrentFrame().getHeight(),
                    null);
            */

        } else if (directionFacing == DirectionFacing.LEFT) {
            //FLIP IMAGES of head and body.
            BufferedImage flippedCurrentHeadImage = Utils.flipHorizontally(currentHeadAnimation.getCurrentFrame());
            BufferedImage flippedCurrentBodyImage = Utils.flipHorizontally(currentBodyAnimation.getCurrentFrame());

            //@@@@@@@@@@@@@@@@@@@@@@@@@
            //HEAD
            g.drawImage(flippedCurrentHeadImage,
                    (int)(x - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset()),
                    Assets.eatFrames[0][0][0][0][0].getWidth(),
                    Assets.eatFrames[0][0][0][0][0].getHeight(),
                    null);

            /*
            g.drawImage(flippedCurrentHeadImage,
                    (int)(x - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset()),
                    (int)(x - handler.getGameCamera().getxOffset() + Assets.eatFrames[0][0][0][0][0].getWidth()),
                    (int)(y - handler.getGameCamera().getyOffset() + Assets.eatFrames[0][0][0][0][0].getHeight()),
                    0,
                    0,
                    flippedCurrentHeadImage.getWidth(),
                    flippedCurrentHeadImage.getHeight(),
                    null);
            */

            //BODY
            g.drawImage(flippedCurrentBodyImage,
                    (int)(x - handler.getGameCamera().getxOffset() + Assets.eatFrames[0][0][0][0][0].getWidth()),
                    (int)(y - handler.getGameCamera().getyOffset()),
                    Assets.tailOriginal[0][0][0][0][0].getWidth(),
                    Assets.tailOriginal[0][0][0][0][0].getHeight(),
                    null);

            /*
            g.drawImage(flippedCurrentBodyImage,
                    (int)(x - handler.getGameCamera().getxOffset() + flippedCurrentHeadImage.getWidth()),
                    (int)(y - handler.getGameCamera().getyOffset()),
                    (int)(x - handler.getGameCamera().getxOffset() + flippedCurrentHeadImage.getWidth() + Assets.tailOriginal[0][0][0][0][0].getWidth()),
                    (int)(y - handler.getGameCamera().getyOffset() + Assets.tailOriginal[0][0][0][0][0].getHeight()),
                    0,
                    0,
                    flippedCurrentBodyImage.getWidth(),
                    flippedCurrentBodyImage.getHeight(),
                    null);
            */
            //@@@@@@@@@@@@@@@@@@@@@@@@@
        }

        //BOUNDING RECTANGLE
        //g.setColor(Color.RED);
        //g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()),
        //        (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
        //        bounds.width, bounds.height);

    }

    // GETTERS AND SETTERS

    public DirectionFacing getDirectionFacing() { return directionFacing; }

    public void setDirectionFacing(DirectionFacing directionFacing) {
        this.directionFacing = directionFacing;
    }

    public FishStateManager getFishStateManager() { return fishStateManager; }

    public void setFishStateManager(FishStateManager fishStateManager) { this.fishStateManager = fishStateManager; }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getHealthMax() {
        return healthMax;
    }

    public void setHealthMax(int healthMax) {
        this.healthMax = healthMax;
    }

    public QuestManager getQuestManager() { return questManager; }

    /*
    public BufferedImage getCurrentHeadImage() { return currentHeadImage; }

    public BufferedImage getCurrentBodyImage() { return currentBodyImage; }
*/

} // **** end Fish class ****