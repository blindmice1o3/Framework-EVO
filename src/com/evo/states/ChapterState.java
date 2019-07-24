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
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("ChapterState.getInput(): KEY-JUST-PRESSED =====> VK_COMMA");

            handler.getStateManager().changeIState(StateManager.State.WORLD_MAP, null);
        }
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("ChapterState.getInput(): KEY-JUST-PRESSED =====> VK_PERIOD");

            handler.getStateManager().changeIState(StateManager.State.INTRO, null);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.chapter1Chapter, 0, 0, handler.panelWidth, handler.panelHeight, null);

        // CHANGING OPACITY OF NEXT IMAGE
        float opacity = 0.25f;
        Graphics2D g2d = (Graphics2D)g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(Assets.chapter1Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

}