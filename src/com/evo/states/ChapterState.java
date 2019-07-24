package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ChapterState implements IState {

    private Handler handler;

    public ChapterState(Handler handler) {
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
                    handler.getStateManager().changeIState(StateManager.State.WORLD_MAP, null);
                }
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    handler.getStateManager().changeIState(StateManager.State.INTRO, null);
                }

                System.out.println("ChapterState.getInput() =====> " + handler.getStateManager().getCurrentChapter());
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
                g.drawImage(Assets.chapter1Chapter, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                float opacity = 0.25f;
                Graphics2D g2d = (Graphics2D)g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter1Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                System.out.println("ChapterState.render(Graphics) =====> " + handler.getStateManager().getCurrentChapter());
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