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
        int xPanel = (handler.panelWidth/2)+25;
        int yPanel = 25;
        g.setColor(Color.BLUE);
        g.fillRect( xPanel, yPanel, (handler.panelWidth/2)-50, handler.panelHeight-50 );
        //BORDER
        g.setColor(Color.YELLOW);
        g.drawRect( xPanel, yPanel, (handler.panelWidth/2)-50, handler.panelHeight-50 );

        //QUEST NAMES
        g.setColor(Color.BLACK);
        int xText = xPanel+15;
        int yText = yPanel+15;
        ArrayList<Quest> quests = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getQuestManager().getQuests();
        if (quests.size() > 0) {
            for (int i = 0; i < quests.size(); i++) {
                FontGrabber.renderString(g, Integer.toString(quests.get(i).getCurrentCount()) +
                                " of " + Integer.toString(quests.get(i).getRequiredCount()) +
                                " " + quests.get(i).getThingBeingCounted(),
                        xText, yText, 10, 10);
                yText += 15;        //new line.
                //xText = xPanel+15;  //carriage return.
            }
        } else {
            FontGrabber.renderString(g, "not on any quest",
                    xText, yText, 10, 10);
        }
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end QuestLogState class ****