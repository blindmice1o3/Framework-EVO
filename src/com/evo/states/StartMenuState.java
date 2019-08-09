package com.evo.states;

import com.evo.Handler;
import com.evo.entities.moveable.OverWorldCursor;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StartMenuState implements IState {

    public enum MenuList { BACK_PACK, SAVE, OPTION, EXIT; }

    private Handler handler;
    public final double X_CONVERSION_FACTOR;
    public final double Y_CONVERSION_FACTOR;

    private MenuList[] menuOptions;
    private OverWorldCursor overWorldCursor;
    private int index;

    public StartMenuState(Handler handler) {
        this.handler = handler;
        X_CONVERSION_FACTOR = ((double)handler.panelWidth / Assets.startMenu.getWidth());
        Y_CONVERSION_FACTOR = ((double)handler.panelHeight / Assets.startMenu.getHeight());

        menuOptions = StartMenuState.MenuList.values();
        overWorldCursor = new OverWorldCursor(handler, Assets.rightOverworld0, handler.panelWidth-80, 25);
        index = 0;
    } // **** end StartMenuState(Handler) constructor ****

    @Override
    public void tick() {
        getInput();

        switch (index) {
            case 0:
                overWorldCursor.setX(handler.panelWidth-80);
                overWorldCursor.setY(25);
                break;
            case 1:
                overWorldCursor.setX(handler.panelWidth-80);
                overWorldCursor.setY(45);
                break;
            case 2:
                overWorldCursor.setX(handler.panelWidth-80);
                overWorldCursor.setY(65);
                break;
            case 3:
                overWorldCursor.setX(handler.panelWidth-80);
                overWorldCursor.setY(85);
                break;
            default:
                overWorldCursor.setX(10);
                overWorldCursor.setY(30);
                break;
        }
    }

    @Override
    public void getInput() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            handler.getStateManager().popIState();
        }

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
        g.drawImage(Assets.startMenu, handler.panelWidth-85, 10, null);

        overWorldCursor.render(g);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end StartMenuState class ****