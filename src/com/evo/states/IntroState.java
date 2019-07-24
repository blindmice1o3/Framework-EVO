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
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("IntroState.getInput(): KEY-JUST-PRESSED =====> VK_COMMA");

            handler.getStateManager().changeIState(StateManager.State.CHAPTER, null);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.chapter1Intro, 0, 0, handler.panelWidth, handler.panelHeight, null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

}