package com.evo.states;

import com.evo.Handler;
import com.evo.entities.moveable.fish.FishStateManager;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class IntroState implements IState {

    private Handler handler;

    public IntroState(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void tick() {
        getInput();
    }

    @Override
    public void getInput() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            handler.getStateManager().changeIState(StateManager.State.CHAPTER, null);
        }

        switch (handler.getStateManager().getCurrentChapter()) {
            case ONE:
                break;
            case TWO:
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    handler.getStateManager().setCurrentChapter(StateManager.Chapter.ONE);
                    handler.getStateManager().changeIState(StateManager.State.WORLD_MAP, null);
                }

                break;
            case THREE:
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    handler.getStateManager().setCurrentChapter(StateManager.Chapter.TWO);
                    handler.getStateManager().changeIState(StateManager.State.WORLD_MAP, null);
                }

                break;
            case FOUR:
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    handler.getStateManager().setCurrentChapter(StateManager.Chapter.THREE);
                    handler.getStateManager().changeIState(StateManager.State.WORLD_MAP, null);
                }

                break;
            case FIVE:
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    handler.getStateManager().setCurrentChapter(StateManager.Chapter.FOUR);
                    handler.getStateManager().changeIState(StateManager.State.WORLD_MAP, null);
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.eatFrames[FishStateManager.BodySize.DECREASE.ordinal()]
                [FishStateManager.BodyTexture.SLICK.ordinal()]
                [FishStateManager.Jaws.ORIGINAL.ordinal()][FishStateManager.ActionState.EAT.ordinal()]
                [0], 10, 50, null);



        /*
        Graphics2D g2d = (Graphics2D)g;
        float opacity = 1.0f;

        switch (handler.getStateManager().getCurrentChapter()) {
            case ONE:
                g2d.drawImage(Assets.chapter1Intro, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter1Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            case TWO:
                g2d.drawImage(Assets.chapter2Intro, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter2Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            case THREE:
                g2d.drawImage(Assets.chapter3Intro, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter3Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            case FOUR:
                g2d.drawImage(Assets.chapter4Intro, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter4Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            case FIVE:
                g2d.drawImage(Assets.chapter5Intro, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter5Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            default:
                break;
        }
        */
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

}