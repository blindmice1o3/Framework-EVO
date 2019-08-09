package com.evo;

import com.evo.input.KeyManager;
import com.evo.states.StateManager;

import java.io.Serializable;

public class Handler implements Serializable {

    private transient Game game;

    public int panelWidth;
    public int panelHeight;

    public Handler(Game game) {
        this.game = game;
    } // **** end Handler(Game) constructor ****

    public void init() {
        panelWidth = game.getDisplayer().getPanel().getWidth();
        panelHeight = game.getDisplayer().getPanel().getHeight();
        System.out.println("customed JPanel's width and height: " + panelWidth + ", " + panelHeight);
    }

    public Game getGame() {
        return game;
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public StateManager getStateManager() {
        return game.getStateManager();
    }

    public Displayer getDisplayer() {
        return game.getDisplayer();
    }

} // **** end Handler class ****