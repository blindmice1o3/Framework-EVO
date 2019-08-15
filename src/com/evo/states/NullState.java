package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.Assets;

import java.awt.*;

public class NullState implements IState {

    private Handler handler;

    public NullState(Handler handler) {
        this.handler = handler;
    } // **** end NullState(handler) constructor ****

    @Override
    public void tick() {
        getInput();

        handler.getStateManager().pushIState(StateManager.State.INTRO, null);
    }

    @Override
    public void getInput() {

    }

    @Override
    public void render(Graphics g) {
        // Fills the screen with background color of Displayer's panel.
        g.setColor(handler.getDisplayer().getPanel().getBackground());
        g.fillRect(0, 0, handler.panelWidth, handler.panelHeight);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end NullState class ****