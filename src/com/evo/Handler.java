package com.evo;

public class Handler {

    private Game game;
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

} // **** end Handler class ****