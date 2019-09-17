package com.evo.states;

import java.awt.*;
import java.io.Serializable;

public interface IState {

    void tick(long timeElapsed);
    void getInput();
    void render(Graphics g);

    void enter(Object[] args);
    void exit();

}
