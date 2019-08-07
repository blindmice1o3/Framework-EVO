package com.evo.entities.moveable.fish;

import com.evo.Handler;
import com.evo.entities.moveable.Creature;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fish extends Creature {

    public enum DirectionFacing { UP, DOWN, LEFT, RIGHT; }

    private FishStateManager fishStateManager;
    private DirectionFacing directionFacing;

    private BufferedImage currentHeadImage;
    private BufferedImage currentBodyImage;

    public Fish(Handler handler, int x, int y) {
        //super(image, x, y, (2 * Tile.WIDTH), Tile.HEIGHT);
        super(handler, null, x, y, 100, 50);

        fishStateManager = new FishStateManager();
        directionFacing = DirectionFacing.UP;

        currentHeadImage = Assets.eatFrames[FishStateManager.BodySize.DECREASE.ordinal()]
                [FishStateManager.BodyTexture.SLICK.ordinal()]
                [FishStateManager.Jaws.ORIGINAL.ordinal()]
                [FishStateManager.ActionState.EAT.ordinal()]
                [0];
        currentBodyImage = Assets.tailOriginal[FishStateManager.BodySize.DECREASE.ordinal()]
                [FishStateManager.BodyTexture.SLICK.ordinal()]
                [FishStateManager.FinPectoral.ORIGINAL.ordinal()]
                [FishStateManager.Tail.ORIGINAL.ordinal()]
                [0];
    } // **** end Fish(Handler, int, int) constructor ****

    public void tick() {
        //update currentHeadImage.
    }

    public void render(Graphics g) {
        //HEAD
        g.drawImage(currentHeadImage, x+currentBodyImage.getWidth(), y, null);

        //BODY
        g.drawImage(currentBodyImage, x, y, null);
    }

} // **** end Fish class ****