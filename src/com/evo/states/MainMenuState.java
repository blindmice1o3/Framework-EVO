package com.evo.states;

import com.evo.Handler;
import com.evo.entities.moveable.OverWorldCursor;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainMenuState implements IState {

    public enum MenuList { EVOLUTION, CAPABILITY, RECORD_OF_EVOLUTION, MAIN; }

    private Handler handler;
    private MenuList currentMenuSelection;

    private OverWorldCursor overWorldCursor;
    private int index;

    public MainMenuState(Handler handler) {
        this.handler = handler;
        currentMenuSelection = MenuList.MAIN;

        overWorldCursor = new OverWorldCursor(handler, Assets.leftOverworld0, 23, 31);
        overWorldCursor.setWidth( (Assets.leftOverworld0.getWidth() / 2) );
        overWorldCursor.setHeight( (Assets.leftOverworld0.getHeight() / 2) );
        index = 0;
    } // **** end MainMenuState(Handler) constructor ****

    @Override
    public void tick() {
        getInput();

        switch (currentMenuSelection) {
            case EVOLUTION:

                break;
            case CAPABILITY:

                break;
            case RECORD_OF_EVOLUTION:

                break;
            case MAIN:
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

                break;
            default:
                System.out.println("MainMenuState.tick(): switch's default.");
                break;
        }
    }

    @Override
    public void getInput() {
        switch (currentMenuSelection) {
            case EVOLUTION:

                break;
            case CAPABILITY:

                break;
            case RECORD_OF_EVOLUTION:

                break;
            case MAIN:
                //a-button: assigns currentMenuSelection.
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    currentMenuSelection = MenuList.values()[index];
                }

                //b-button or select-button will exit MainMenuState (goes back to previous IState).
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD) ||
                        (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT))) {
                    currentMenuSelection = MenuList.MAIN;
                    handler.getStateManager().popIState();
                }

                //down-button and up-button: changes the index position.
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                    index++;

                    //minus 2 because MenuList.MAIN doesn't count as a menu selection choice.
                    if (index > MenuList.values().length-2) {
                        index = 0;
                    }
                } else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                    index--;

                    if (index < 0) {
                        //minus 2 because MenuList.MAIN doesn't count as a menu selection choice.
                        index = MenuList.values().length-2;
                    }
                }

                break;
            default:
                System.out.println("MainMenuState.getInput(): switch's default.");
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        switch (currentMenuSelection) {
            case EVOLUTION:

                break;
            case CAPABILITY:

                break;
            case RECORD_OF_EVOLUTION:

                break;
            case MAIN:
                //background image: main menu.
                g.drawImage(Assets.mainMenu, 20, 20, null);
                //cursor image: leftOverworld0.
                overWorldCursor.render(g);

                break;
            default:
                System.out.println("MainMenuState.render(Graphics): switch's default.");
                break;
        }
    }

    @Override
    public void enter(Object[] args) {
        currentMenuSelection = MenuList.MAIN;
        index = 0;
    }

    @Override
    public void exit() {
        currentMenuSelection = MenuList.MAIN;
        index = 0;
    }

} // **** end MainMenuState class ****