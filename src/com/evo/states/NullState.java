package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.Assets;

import java.awt.*;

public class NullState implements IState {

    private Handler handler;
    public final double X_CONVERSION_FACTOR;
    public final double Y_CONVERSION_FACTOR;

    public NullState(Handler handler) {
        this.handler = handler;
        X_CONVERSION_FACTOR = ((double)handler.panelWidth / handler.panelWidth);
        Y_CONVERSION_FACTOR = ((double)handler.panelHeight / handler.panelHeight);
    } // **** end NullState(handler) constructor ****

    @Override
    public void tick() {
        getInput();
    }

    @Override
    public void getInput() {
        handler.getStateManager().pushIState(StateManager.State.INTRO, null);
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