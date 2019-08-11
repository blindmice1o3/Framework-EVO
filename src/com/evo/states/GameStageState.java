package com.evo.states;

import com.evo.Handler;
import com.evo.game_stages.GameStage;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameStageState implements IState {

    private Handler handler;
    private GameStage currentGameStage;

    public GameStageState(Handler handler) {
        this.handler = handler;
        currentGameStage = new GameStage("");
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
        //g.drawImage(Assets.chapter1GameStage, 0, 0, null);
        currentGameStage.render(g);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end GameStageState class ****