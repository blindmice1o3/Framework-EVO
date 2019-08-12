package com.evo.states;

import com.evo.Handler;
import com.evo.entities.moveable.fish.Fish;
import com.evo.entities.moveable.fish.FishStateManager;
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



        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_Z)) {
            int currentBodyTextureOrdinal = currentGameStage.getFishInstance().getFishStateManager().getCurrentBodyTexture().ordinal();
            FishStateManager.BodyTexture[] bodyTexture = FishStateManager.BodyTexture.values();

            if ((currentBodyTextureOrdinal+1) < bodyTexture.length) {
                currentGameStage.getFishInstance().getFishStateManager().setCurrentBodyTexture(bodyTexture[currentBodyTextureOrdinal + 1]);
            } else {
                currentGameStage.getFishInstance().getFishStateManager().setCurrentBodyTexture(bodyTexture[0]);
            }
        }

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_X)) {
            int currentJawsOrdinal = currentGameStage.getFishInstance().getFishStateManager().getCurrentJaws().ordinal();
            FishStateManager.Jaws[] jaws = FishStateManager.Jaws.values();

            if ((currentJawsOrdinal+1) < jaws.length) {
                currentGameStage.getFishInstance().getFishStateManager().setCurrentJaws(jaws[currentJawsOrdinal + 1]);
            } else {
                currentGameStage.getFishInstance().getFishStateManager().setCurrentJaws(jaws[0]);
            }
        }

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)) {
            int currentFinPectoralOrdinal = currentGameStage.getFishInstance().getFishStateManager().getCurrentFinPectoral().ordinal();
            FishStateManager.FinPectoral[] finPectoral = FishStateManager.FinPectoral.values();

            if ((currentFinPectoralOrdinal+1) < finPectoral.length) {
                currentGameStage.getFishInstance().getFishStateManager().setCurrentFinPectoral(finPectoral[currentFinPectoralOrdinal + 1]);
            } else {
                currentGameStage.getFishInstance().getFishStateManager().setCurrentFinPectoral(finPectoral[0]);
            }
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