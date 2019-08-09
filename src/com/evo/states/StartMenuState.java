package com.evo.states;

import com.evo.Handler;
import com.evo.entities.moveable.OverWorldCursor;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StartMenuState implements IState {

    public enum MenuList { EVOLUTION, CAPABILITY, RECORD_OF_EVOLUTION; }

    private Handler handler;

    private OverWorldCursor overWorldCursor;
    private int index;

    public StartMenuState(Handler handler) {
        this.handler = handler;

        overWorldCursor = new OverWorldCursor(handler, Assets.leftOverworld0, 23, 31);
        overWorldCursor.setWidth( (Assets.leftOverworld0.getWidth() / 2) );
        overWorldCursor.setHeight( (Assets.leftOverworld0.getHeight() / 2) );
        index = 0;
    } // **** end StartMenuState(Handler) constructor ****

    @Override
    public void tick() {
        getInput();

        //determines cursor's on-screen location based on selected index.
        switch (index) {
            case 0:
                overWorldCursor.setX(23);
                overWorldCursor.setY(31);
                break;
            case 1:
                overWorldCursor.setX(23);
                overWorldCursor.setY(43);
                break;
            case 2:
                overWorldCursor.setX(23);
                overWorldCursor.setY(55);
                break;
            default:
                overWorldCursor.setX(23);
                overWorldCursor.setY(31);
                break;
        }
    }

    @Override
    public void getInput() {
        //b-button or start-button will exit StartMenuState (goes back to previous IState).
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD) ||
                (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT))) {
            handler.getStateManager().popIState();
        }

        //down-button and up-button: changes the index position.
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            index++;

            if (index > MenuList.values().length-1) {
                index = 0;
            }
        } else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            index--;

            if (index < 0) {
                index = MenuList.values().length-1;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        //background image: start menu (pokemon).
        g.drawImage(Assets.startMenu, 20, 20, null);

        overWorldCursor.render(g);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end StartMenuState class ****