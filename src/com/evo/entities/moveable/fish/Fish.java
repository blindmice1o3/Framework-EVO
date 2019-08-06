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

    public Fish(Handler handler, BufferedImage image, int x, int y) {
        //super(image, x, y, (2 * Tile.WIDTH), Tile.HEIGHT);
        super(handler, image, x, y, 100, 50);

        fishStateManager = new FishStateManager();
        directionFacing = DirectionFacing.UP;
    } // **** end Fish(Handler, BufferedImage, int, int) constructor ****

    public void tick() {

    }

    public void render(Graphics g) {

    }



    class FishHead {

        private BufferedImage currentHeadImage;

        public FishHead() {
            currentHeadImage = Assets.eatFrames[FishStateManager.BodySize.DECREASE.ordinal()]
                    [FishStateManager.BodyTexture.SLICK.ordinal()]
                    [FishStateManager.Jaws.ORIGINAL.ordinal()]
                    [FishStateManager.ActionState.EAT.ordinal()]
                    [0];
        } // **** end FishHead() constructor ****

        public void tick() {
            //update currentHeadImage.
        }

        public void render(Graphics g) {
            g.drawImage(currentHeadImage, 0, 0, null);
        }

    } // **** end FishHead inner-class ****

} // **** end Fish class ****