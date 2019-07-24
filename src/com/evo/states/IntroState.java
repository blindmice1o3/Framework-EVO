package com.evo.states;

import com.evo.Handler;
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
        switch (handler.getStateManager().getCurrentChapter()) {
            case ONE:
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    handler.getStateManager().changeIState(StateManager.State.CHAPTER, null);
                }

                System.out.println("IntroState.getInput() =====> " + handler.getStateManager().getCurrentChapter());
                break;
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
                break;
            default:
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        switch (handler.getStateManager().getCurrentChapter()) {
            case ONE:
                g.drawImage(Assets.chapter1Intro, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                float opacity = 0.25f;
                Graphics2D g2d = (Graphics2D)g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter1Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                System.out.println("IntroState.render(Graphics) =====> " + handler.getStateManager().getCurrentChapter());
                break;
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
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