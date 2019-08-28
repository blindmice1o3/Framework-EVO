package com.evo.entities.moveable.player;

import com.evo.Handler;
import com.evo.entities.Entity;
import com.evo.entities.moveable.Creature;
import com.evo.gfx.Animation;
import com.evo.gfx.Assets;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Fish extends Creature {

    public enum DirectionFacing { LEFT, RIGHT; }

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
    //private BufferedImage currentHeadImage;
    //private BufferedImage currentBodyImage;

    //ATTACK TIMER
    private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;

    public Fish(Handler handler, float x, float y) {
        super(handler, null, x, y,
                Assets.eatFrames[0][0][0][0][0].getWidth()
                        + Assets.tailOriginal[0][0][0][0][0].getWidth(),
                Assets.eatFrames[0][0][0][0][0].getHeight());
        System.out.println("Fish.constructor (width/height): " + width + "/" + height);

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

        bounds.x = 4;
        bounds.y = 3;
        bounds.width = 24;
        bounds.height = 8;
    } // **** end Fish(Handler, float, float) constructor ****

    @Override
    public void initAnimations() {
        initHeadAnimations();
        currentBodyAnimation = new Animation(600,
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
        idleHeadAnimation = new Animation(400, idleFrames);

        //for FishStateManager.ActionState.HURT
        BufferedImage[] hurtFrames = new BufferedImage[1];
        hurtFrames[0] = Assets.hurtFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.HURT.ordinal()]
                [0];
        hurtHeadAnimation = new Animation(400, hurtFrames);

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
        eatHeadAnimation = new Animation(400, eatFrames);

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
        biteHeadAnimation = new Animation(400, biteFrames);

        /////////////////////////////////////////
        currentHeadAnimation = idleHeadAnimation;
        /////////////////////////////////////////
    }

    @Override
    public void tick() {
        // ANIMATIONS
        if (currentHeadAnimation != idleHeadAnimation) {
            currentHeadAnimation.tick();

            if (currentHeadAnimation.getIndex() == currentHeadAnimation.getFrames().length-1) {
                fishStateManager.setCurrentActionState(FishStateManager.ActionState.NONE);
                currentHeadAnimation = idleHeadAnimation;
            }
        }
        currentBodyAnimation.tick();

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

        // MOVEMENT
        getInput();
        move();

        // ATTACK
        checkAttacks();
    }

    /**
     * cb is "Collision Bounds", which is the collision rectangle of the player.
     * ar is "Attack Rectangle", which is the collision rectangle of an attack.
     */
    private void checkAttacks() {
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown) {
            return;
        }

        Rectangle cb = getCollisionBounds(0, 0);
        Rectangle ar = new Rectangle();
        ar.width = Assets.eatFrames[0][0][0][0][0].getWidth();
        ar.height = Assets.eatFrames[0][0][0][0][0].getHeight();

        //a-button (check where to set the attack rectangle).
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            if (directionFacing == DirectionFacing.LEFT) {
                ar.x = cb.x - 4;
                ar.y = cb.y;
            } else if (directionFacing == DirectionFacing.RIGHT) {
                ar.x = cb.x + Assets.tailOriginal[0][0][0][0][0].getWidth() + 3;
                ar.y = cb.y;
            }
        }
        //if NOT attacking, return.
        else {
            return;
        }

        // if we're at this line, we didn't call return in the else-clause; which means the attack button was pressed
        // and we need to reset the attackTimer (a cool-down time before the player can attack again).
        attackTimer = 0;

        //LOOP through every entity in the current game stage.
        ArrayList<Entity> entities = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getEntityManager().getEntities();
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

    @Override
    public void die() {
        System.out.println("You lose.");
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
        //a-button (bite).
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            fishStateManager.setCurrentActionState(FishStateManager.ActionState.BITE);
            currentHeadAnimation = biteHeadAnimation;
        }
        //x-button (eat).
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) {
            fishStateManager.setCurrentActionState(FishStateManager.ActionState.EAT);
            currentHeadAnimation = eatHeadAnimation;
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
            currentBodyAnimation = new Animation(600,
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
            currentBodyAnimation = new Animation(600,
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
            currentBodyAnimation = new Animation(600,
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
            BufferedImage flippedCurrentHeadImage = Fish.flipHorizontally(currentHeadAnimation.getCurrentFrame());
            BufferedImage flippedCurrentBodyImage = Fish.flipHorizontally(currentBodyAnimation.getCurrentFrame());

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

    public static BufferedImage flipHorizontally(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        return op.filter(image, null);
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

    /*
    public BufferedImage getCurrentHeadImage() { return currentHeadImage; }

    public BufferedImage getCurrentBodyImage() { return currentBodyImage; }
*/

} // **** end Fish class ****