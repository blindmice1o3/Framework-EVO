package com.evo.states;

import com.evo.Handler;
import com.evo.game_stages.GameStage;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameStageState implements IState {

    private Handler handler;
    private GameStage currentGameStage;

    public GameStageState(Handler handler) {
        this.handler = handler;
        currentGameStage = new GameStage(handler, "");
    } // **** end GameStageState(Handler) constructor

    @Override
    public void tick() {
        getInput();

        currentGameStage.tick();
    }

    @Override
    public void getInput() {
        //@@@@TESTING TextboxState (KeyEvent.VK_SLASH)@@@@
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SLASH)) {
            /////////////
            Object args[] = { "The sky above the port was the color of television, tuned to a dead channel. -William Gibson" };
            /////////////
            handler.getStateManager().pushIState(StateManager.State.TEXTBOX, args);
        }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //b-button (goes back to previous IState).
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            handler.getStateManager().popIState();
        }
        //select (will enter MainMenuState).
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT)) {
            handler.getStateManager().pushIState(StateManager.State.MAIN_MENU, null);
        }
        //start (will enter PauseState).
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
            handler.getStateManager().pushIState(StateManager.State.PAUSE, null);
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

    // GETTERS AND SETTERS

    public GameStage getCurrentGameStage() { return currentGameStage; }

} // **** end GameStageState class ****