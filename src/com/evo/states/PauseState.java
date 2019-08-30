package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.FontGrabber;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PauseState implements IState {

    private Handler handler;


    public PauseState(Handler handler) {
        this.handler = handler;
    } // **** end PauseState(Handler) constructor

    @Override
    public void tick() {
        getInput();

    }

    @Override
    public void getInput() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
            handler.getStateManager().popIState();
        }
    }

    @Override
    public void render(Graphics g) {
        //CENTER
        Graphics2D g2d = (Graphics2D)g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        String text = "Cannabis sativa is what you seek";
        int widthLetter = 10;
        int heightLetter = 10;
        FontGrabber.renderString(g2d,
                text,
                (handler.panelWidth/2)-((text.length()*widthLetter)/2),
                (handler.panelHeight/2)-(heightLetter/2)-(30),
                widthLetter,
                heightLetter);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end PauseState class ****