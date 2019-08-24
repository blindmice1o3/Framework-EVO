package com.evo.entities.non_moveable;

import com.evo.Handler;
import com.evo.game_stages.GameStage;
import com.evo.gfx.Animation;
import com.evo.gfx.Assets;
import com.evo.items.Item;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Kelp extends StaticEntity {

    private Animation kelpAnimation;

    public Kelp(Handler handler, BufferedImage image, float x, float y) {
        super(handler, null, x, y, 12, 32);

        kelpAnimation = new Animation(500, Assets.kelpSolid);
    } // **** end Kelp(Handler, BufferedImage, float, float) constructor ****

    @Override
    public void tick() {
        kelpAnimation.tick();
    }

    @Override
    public void die() {
        GameStage gameStage = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage();
        gameStage.getItemManager().addItem( Item.meatItem.createNew((int)x, (int)y) );
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(kelpAnimation.getCurrentFrame(),
                (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()),
                kelpAnimation.getCurrentFrame().getWidth(),
                kelpAnimation.getCurrentFrame().getHeight(),
                null);
    }

} // **** end Kelp class ****