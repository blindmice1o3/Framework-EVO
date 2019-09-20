package com.evo.states;

import com.evo.Handler;
import com.evo.game_stages.GameStage;
import com.evo.quests.QuestManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameStageState implements IState {

    private Handler handler;
    private QuestManager questManager;
    private GameStage currentGameStage;

    public GameStageState(Handler handler) {
        this.handler = handler;
        questManager = new QuestManager(handler);
        currentGameStage = new GameStage(handler, "");
    } // **** end GameStageState(Handler) constructor

    @Override
    public void tick(long timeElapsed) {
        getInput();

        // QUESTS
        questManager.tick();

        // GAME_STAGES
        currentGameStage.tick(timeElapsed);
    }

    @Override
    public void getInput() {
        //@@@@TESTING QuestLogState (KeyEvent.VK_L)@@@@
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_L)) {
            handler.getStateManager().pushIState(StateManager.State.QUEST_LOG, null);
        }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //@@@@TESTING TextboxState (KeyEvent.VK_SLASH)@@@@
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SLASH)) {
            /////////////
            StringBuilder text = new StringBuilder(
                    "The sky above the port was the color of television, tuned to a dead channel. -William Gibson");
            text.append(" \"It's not like I'm using,\" Case heard someone say, as he shouldered his way ").append(
                    "through the crowd around the door of the Chat. \"It's like my body's developed this ").append(
                    "massive drug deficiency.\" It was a Sprawl voice and a Sprawl joke. The Chatsubo was ").append(
                    "a bar for professional expatriates; you could drink there for a week and never hear ").append(
                    "two words in Japanese.");
            Object args[] = { text.toString() };
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

    public QuestManager getQuestManager() { return questManager; }

    public GameStage getCurrentGameStage() { return currentGameStage; }

} // **** end GameStageState class ****