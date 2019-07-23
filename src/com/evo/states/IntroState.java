package com.evo.states;

import com.evo.Game;
import com.evo.gfx.Assets;

import java.awt.*;

public class IntroState implements IState {

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.chapter1Intro, 0, 0, Game.WIDTH, Game.HEIGHT, null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

}