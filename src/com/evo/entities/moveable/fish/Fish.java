package com.evo.entities.moveable.fish;

import com.evo.Handler;
import com.evo.entities.moveable.Creature;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Fish extends Creature {

    public enum DirectionFacing { LEFT, RIGHT; }

    private FishStateManager fishStateManager;
    private DirectionFacing directionFacing;

    private BufferedImage currentHeadImage;
    private BufferedImage currentBodyImage;

    public Fish(Handler handler, int x, int y) {
        //super(image, x, y, (2 * Tile.WIDTH), Tile.HEIGHT);
        super(handler, null, x, y, 100, 50);

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
    } // **** end Fish(Handler, int, int) constructor ****

    public void tick() {
        //update currentHeadImage.
    }

    public void render(Graphics g) {
        if (directionFacing == DirectionFacing.RIGHT) {
            //BODY
            g.drawImage(currentBodyImage, x, y, null);

            //HEAD
            g.drawImage(currentHeadImage, x + currentBodyImage.getWidth(), y, null);
        } else if (directionFacing == DirectionFacing.LEFT) {
            //TODO: flip image of head and body.
            BufferedImage flippedCurrentHeadImage = Fish.flipHorizontally(currentHeadImage);
            BufferedImage flippedCurrentBodyImage = Fish.flipHorizontally(currentBodyImage);

            //@@@@@@@@@@@@@@@@@@@@@@@@@
            //HEAD
            g.drawImage(flippedCurrentHeadImage, x, y, null);

            //BODY
            g.drawImage(flippedCurrentBodyImage, x + flippedCurrentHeadImage.getWidth(), y, null);
            //@@@@@@@@@@@@@@@@@@@@@@@@@
        }
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

    public void setDirectionFacing(DirectionFacing directionFacing) {
        this.directionFacing = directionFacing;
    }

} // **** end Fish class ****