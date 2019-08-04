package com.evo.states;

import com.evo.Handler;
import com.evo.entities.moveable.fish.Fish;
import com.evo.entities.moveable.fish.FishStateManager;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class IntroState implements IState {

    private Handler handler;

    private int bodySizeIndex, bodyTextureIndex, jawsIndex, actionStateIndex, frameNumberIndex;

    public IntroState(Handler handler) {
        this.handler = handler;

        bodySizeIndex = 0;
        bodyTextureIndex = 0;
        jawsIndex = 0;
        actionStateIndex = 0;
        frameNumberIndex = 0;
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
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_Z)) {
                    bodySizeIndex++;

                    if (bodySizeIndex >= FishStateManager.BodySize.values().length) {
                        bodySizeIndex = 0;
                    }
                } else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_X)) {
                    bodyTextureIndex++;

                    if (bodyTextureIndex >= FishStateManager.BodyTexture.values().length) {
                        bodyTextureIndex = 0;
                    }
                } else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)) {
                    jawsIndex++;

                    if (jawsIndex >= FishStateManager.Jaws.values().length) {
                        jawsIndex = 0;
                    }
                } else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_B)) {
                    frameNumberIndex++;

                    if (frameNumberIndex >= 3) {
                        frameNumberIndex = 0;
                    }
                }

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

        g.drawImage(Assets.eatFrames[bodySizeIndex][bodyTextureIndex][jawsIndex][0][frameNumberIndex],
            0, 0, null);
        g.drawImage(Assets.biteFrames[bodySizeIndex][bodyTextureIndex][jawsIndex][1][frameNumberIndex],
                0, 16, null);
        if (frameNumberIndex != 2) {
            g.drawImage(Assets.hurtFrames[bodySizeIndex][bodyTextureIndex][jawsIndex][2][frameNumberIndex],
                    0, 32, null);
        }

/*
        int xOffset = 0;
        int yOffset = 0;

        for (FishStateManager.BodySize bodySize : FishStateManager.BodySize.values()) {
            for (FishStateManager.BodyTexture bodyTexture : FishStateManager.BodyTexture.values()) {
                for (FishStateManager.Jaws jaws : FishStateManager.Jaws.values()) {
                    for (FishStateManager.ActionState actionState : FishStateManager.ActionState.values()) {

                        if (actionState.ordinal() < FishStateManager.ActionState.values().length-1) {
                            for (int index = 0; index < Assets.eatFrames[bodySize.ordinal()][bodyTexture.ordinal()]
                                    [jaws.ordinal()][actionState.ordinal()].length; index++) {
                                if (xOffset < handler.panelWidth - 16) {
                                    g.drawImage(Assets.eatFrames[bodySize.ordinal()]
                                            [bodyTexture.ordinal()]
                                            [jaws.ordinal()]
                                            [actionState.ordinal()]
                                            [index], xOffset, yOffset, null);

                                    xOffset += 16;

                                    if (xOffset >= handler.panelWidth - 16) {
                                        xOffset = 0;
                                        if (yOffset < handler.panelHeight) {
                                            yOffset += 16;
                                        }
                                    }
                                }
                            }
                            /////////////////////////////////////////////////////////////////////////////
                            for (int index = 0; index < Assets.biteFrames[bodySize.ordinal()][bodyTexture.ordinal()]
                                    [jaws.ordinal()][actionState.ordinal()].length; index++) {
                                if (xOffset < handler.panelWidth - 16) {
                                    g.drawImage(Assets.biteFrames[bodySize.ordinal()]
                                            [bodyTexture.ordinal()]
                                            [jaws.ordinal()]
                                            [actionState.ordinal()]
                                            [index], xOffset, yOffset, null);

                                    xOffset += 16;

                                    if (xOffset >= handler.panelWidth - 16) {
                                        xOffset = 0;
                                        if (yOffset < handler.panelHeight) {
                                            yOffset += 16;
                                        }
                                    }
                                }
                            }
                            //////////////////////////////////////////////////////////////////////////////
                            for (int index = 0; index < Assets.hurtFrames[bodySize.ordinal()][bodyTexture.ordinal()]
                                    [jaws.ordinal()][actionState.ordinal()].length; index++) {
                                if (xOffset < handler.panelWidth - 16) {
                                    g.drawImage(Assets.hurtFrames[bodySize.ordinal()]
                                            [bodyTexture.ordinal()]
                                            [jaws.ordinal()]
                                            [actionState.ordinal()]
                                            [index], xOffset, yOffset, null);

                                    xOffset += 16;

                                    if (xOffset >= handler.panelWidth - 16) {
                                        xOffset = 0;
                                        if (yOffset < handler.panelHeight) {
                                            yOffset += 16;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
*/


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