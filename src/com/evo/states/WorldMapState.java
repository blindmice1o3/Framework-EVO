package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class WorldMapState implements IState {

    private Handler handler;

    private int index;

    public WorldMapState(Handler handler) {
        this.handler = handler;

        index = 0;
    }

    @Override
    public void tick() {
        getInput();
    }

    @Override
    public void getInput() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            handler.getStateManager().changeIState(StateManager.State.CHAPTER, null);
        }

        switch (handler.getStateManager().getCurrentChapter()) {
            case ONE:
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    handler.getStateManager().setCurrentChapter(StateManager.Chapter.TWO);
                    handler.getStateManager().changeIState(StateManager.State.INTRO, null);
                }

                break;
            case TWO:
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    handler.getStateManager().setCurrentChapter(StateManager.Chapter.THREE);
                    handler.getStateManager().changeIState(StateManager.State.INTRO, null);
                }

                break;
            case THREE:
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    handler.getStateManager().setCurrentChapter(StateManager.Chapter.FOUR);
                    handler.getStateManager().changeIState(StateManager.State.INTRO, null);
                }

                break;
            case FOUR:
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    handler.getStateManager().setCurrentChapter(StateManager.Chapter.FIVE);
                    handler.getStateManager().changeIState(StateManager.State.INTRO, null);
                }

                break;
            case FIVE:
                break;
            default:
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        float opacity = 1.0f;

        switch (handler.getStateManager().getCurrentChapter()) {
            case ONE:
                g2d.drawImage(Assets.chapter1WorldMap, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter1Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            case TWO:
                g2d.drawImage(Assets.chapter2WorldMap, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter2Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            case THREE:
                g2d.drawImage(Assets.chapter3WorldMap, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter3Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            case FOUR:
                g2d.drawImage(Assets.chapter4WorldMap, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter4Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            case FIVE:
                g2d.drawImage(Assets.chapter5WorldMap, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter5Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            default:
                break;
        }
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

}