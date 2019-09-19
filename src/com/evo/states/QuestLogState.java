package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.FontGrabber;
import com.evo.quests.Quest;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class QuestLogState implements IState {

    private Handler handler;

    public QuestLogState(Handler handler) {
        this.handler = handler;
    } // **** end QuestLogState(Handler) constructor ****

    @Override
    public void tick(long timeElapsed) {
        getInput();


    }

    @Override
    public void getInput() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_L)) {
            handler.getStateManager().popIState();
        }
    }

    @Override
    public void render(Graphics g) {
        //BACKGROUND PANEL
        g.setColor(Color.BLUE);
        g.fillRect( (handler.panelWidth/2)+25, 25, (handler.panelWidth/2)-50, handler.panelHeight-50 );
        //BORDER
        g.setColor(Color.YELLOW);
        g.drawRect( (handler.panelWidth/2)+25, 25, (handler.panelWidth/2)-50, handler.panelHeight-50 );

        //QUEST NAMES
        g.setColor(Color.BLACK);
        int y = 25+15;
        ArrayList<Quest> quests = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer().getQuestManager().getQuests();
        if (quests.size() > 0) {
            for (int i = 0; i < quests.size(); i++) {
                FontGrabber.renderString(g, quests.get(i).getThingBeingCounted(),
                        (handler.panelWidth/2)+25+15, y, 10, 10);
                y += 15;
            }
        } else {
            FontGrabber.renderString(g, "currently not on any quest",
                    (handler.panelWidth/2)+25+15, y, 10, 10);
        }
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end QuestLogState class ****