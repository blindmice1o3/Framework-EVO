package com.evo.entities.moveable.fish;

import com.evo.Handler;
import com.evo.entities.moveable.Creature;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Fish extends Creature {

    public enum DirectionFacing { LEFT, RIGHT; }

    private FishStateManager fishStateManager;
    private DirectionFacing directionFacing;

    private BufferedImage currentHeadImage;
    private BufferedImage currentBodyImage;

    public Fish(Handler handler) {
        //super(image, x, y, (2 * Tile.WIDTH), Tile.HEIGHT);
        super(handler, null, 40, 10,
                Assets.eatFrames[0][0][0][0][0].getWidth()
                        + Assets.tailOriginal[0][0][0][0][0].getWidth(),
                Assets.eatFrames[0][0][0][0][0].getHeight());

        fishStateManager = new FishStateManager();
        directionFacing = DirectionFacing.RIGHT;

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
    } // **** end Fish(Handler) constructor ****

    public void tick() {
        //TODO: finish this.
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
    }

    public void render(Graphics g) {
        //ACTUAL IMAGE OF FISH
        if (directionFacing == DirectionFacing.RIGHT) {
            //BODY
            g.drawImage(currentBodyImage,
                    (int)(x - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset()),
                    null);

            //HEAD
            g.drawImage(currentHeadImage,
                    (int)(x + currentBodyImage.getWidth() - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset()),
                    null);
        } else if (directionFacing == DirectionFacing.LEFT) {
            //TODO: flip image of head and body.
            BufferedImage flippedCurrentHeadImage = Fish.flipHorizontally(currentHeadImage);
            BufferedImage flippedCurrentBodyImage = Fish.flipHorizontally(currentBodyImage);

            //@@@@@@@@@@@@@@@@@@@@@@@@@
            //HEAD
            g.drawImage(flippedCurrentHeadImage,
                    (int)(x - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset()),
                    null);

            //BODY
            g.drawImage(flippedCurrentBodyImage,
                    (int)(x + flippedCurrentHeadImage.getWidth() - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset()),
                    null);
            //@@@@@@@@@@@@@@@@@@@@@@@@@
        }

        //BOUNDING RECTANGLE
        g.setColor(Color.RED);
        g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()),
                (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);
    }

    public static BufferedImage flipHorizontally(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        return op.filter(image, null);
    }

    public void moveUp() {
        y = y - yMove;
    }

    public void moveDown() {
        y = y + yMove;
    }

    public void moveLeft() {
        x = x - xMove;
    }

    public void moveRight() {
        x = x + xMove;
    }

    // GETTERS AND SETTERS

    public DirectionFacing getDirectionFacing() { return directionFacing; }

    public void setDirectionFacing(DirectionFacing directionFacing) {
        this.directionFacing = directionFacing;
    }

    public FishStateManager getFishStateManager() { return fishStateManager; }

    public void setFishStateManager(FishStateManager fishStateManager) { this.fishStateManager = fishStateManager; }

    public BufferedImage getCurrentHeadImage() { return currentHeadImage; }

    public BufferedImage getCurrentBodyImage() { return currentBodyImage; }

} // **** end Fish class ****