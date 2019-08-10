package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameStageState implements IState {

    private Handler handler;

    public GameStageState(Handler handler) {
        this.handler = handler;
    } // **** end GameStageState(Handler) constructor

    @Override
    public void tick() {
        getInput();


    }

    @Override
    public void getInput() {
        //b-button (goes back to previous IState).
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            handler.getStateManager().popIState();
        }
        //select-button (will enter MainMenuState).
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT)) {
            handler.getStateManager().pushIState(StateManager.State.MAIN_MENU, null);
        }

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.chapter1GameStage, 0, 0, null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end GameStageState class ****