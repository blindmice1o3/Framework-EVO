package com.evo.states;

import com.evo.Game;
import com.evo.Handler;
import com.evo.gfx.Assets;

import java.awt.*;

public class IntroState implements IState {

    private Handler handler;

    public IntroState(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.chapter1Intro, 0, 0, handler.panelWidth, handler.panelHeight, null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

}