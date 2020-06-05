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

        initAnimations();
    } // **** end Kelp(Handler, BufferedImage, float, float) constructor ****

    @Override
    public void initAnimations() {
        kelpAnimation = new Animation(500000000L, Assets.kelpSolid);
    }

    @Override
    public void tick(long timeElapsed) {
        kelpAnimation.tick(timeElapsed);
    }

    @Override
    public void die() {
        GameStageState gameStageState = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE));
        gameStageState.getCurrentGameStage().getItemManager().addItem( Item.meatItem.createNew((int)x, (int)y) );

        //TODO: new Quest feature.
        if ( gameStageState.getQuestManager().findQuest("Kelp") != null ) {
            gameStageState.getQuestManager().findQuest("Kelp").increaseCurrentCount();

            System.out.println("Kelp.die(), kelp quest is active: " +
                    gameStageState.getQuestManager().findQuest("Kelp").getCurrentCount() +
                    " of " +
                    gameStageState.getQuestManager().findQuest("Kelp").getRequiredCount());
        }
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