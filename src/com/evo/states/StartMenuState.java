package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StartMenuState implements IState {

    public enum MenuList { BACK_PACK, SAVE, OPTION, EXIT; }

    private Handler handler;

    private int index;

    public StartMenuState(Handler handler) {
        this.handler = handler;

        index = 0;
    } // **** end StartMenuState(Handler) constructor ****

    @Override
    public void tick() {
        getInput();
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
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end StartMenuState class ****