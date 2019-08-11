package com.evo.states;

import com.evo.Handler;
import com.evo.entities.moveable.fish.Fish;
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
        //b-button (goes back to previous IState).
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            handler.getStateManager().popIState();
        }
        //select-button (will enter MainMenuState).
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT)) {
            handler.getStateManager().pushIState(StateManager.State.MAIN_MENU, null);
        }


        if (handler.getKeyManager().up) {
            currentGameStage.getFishInstance().moveUp();
        }
        if (handler.getKeyManager().down) {
            currentGameStage.getFishInstance().moveDown();
        }
        if (handler.getKeyManager().left) {
            currentGameStage.getFishInstance().moveLeft();
            ///////////////////////////////////////////////////////////
            currentGameStage.getFishInstance().setDirectionFacing(Fish.DirectionFacing.LEFT);
            ///////////////////////////////////////////////////////////
        }
        if (handler.getKeyManager().right) {
            currentGameStage.getFishInstance().moveRight();
            ///////////////////////////////////////////////////////////
            currentGameStage.getFishInstance().setDirectionFacing(Fish.DirectionFacing.RIGHT);
            ///////////////////////////////////////////////////////////
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