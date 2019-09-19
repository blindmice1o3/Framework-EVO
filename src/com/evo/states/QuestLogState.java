package com.evo.states;

import com.evo.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;

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
        g.setColor(Color.BLUE);
        g.fillRect( (handler.panelWidth/2)+25, 25, (handler.panelWidth/2)-50, handler.panelHeight-50 );

        g.setColor(Color.YELLOW);
        g.drawRect( (handler.panelWidth/2)+25, 25, (handler.panelWidth/2)-50, handler.panelHeight-50 );

    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end QuestLogState class ****