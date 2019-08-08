package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StartMenuState implements IState {

    private Handler handler;

    public StartMenuState(Handler handler) {
        this.handler = handler;
    } // **** end StartMenuState(Handler) constructor ****

    @Override
    public void tick() {
        getInput();
    }

    @Override
    public void getInput() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            handler.getStateManager().changeIState(StateManager.State.INTRO, null);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.startMenu, handler.panelWidth-85, 10, null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end StartMenuState class ****