package com.evo.states;

import java.awt.*;

public interface IState {

    void tick();
    void render(Graphics g);

    void enter(Object[] args);
    void exit();

}
