package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.OverWorldCursor;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainMenuState implements IState {

    public enum MenuList { EVOLUTION, CAPABILITY, RECORD_OF_EVOLUTION, MAIN; }

    private Handler handler;
    private OverWorldCursor overWorldCursor;

    private MenuList currentMenuSelection;
    private int index;

    public MainMenuState(Handler handler) {
        this.handler = handler;
        overWorldCursor = new OverWorldCursor(handler, Assets.leftOverworld0, 23, 31);
        overWorldCursor.setWidth( (Assets.leftOverworld0.getWidth() / 2) );
        overWorldCursor.setHeight( (Assets.leftOverworld0.getHeight() / 2) );

        currentMenuSelection = MenuList.MAIN;
        index = 0;
    } // **** end MainMenuState(Handler) constructor ****

    @Override
    public void tick(long timeElapsed) {
        getInput();

        switch (currentMenuSelection) {
            case EVOLUTION:

                break;
            case CAPABILITY:

                break;
            case RECORD_OF_EVOLUTION:
                //determines cursor's on-screen location based on selected index.
                switch (index) {
                    case 0:
                        overWorldCursor.setX(33);
                        overWorldCursor.setY(Assets.mainMenu.getHeight()+20+18);
                        break;
                    case 1:
                        overWorldCursor.setX(83);
                        overWorldCursor.setY(Assets.mainMenu.getHeight()+20+18);
                        break;
                    default:
                        System.out.println("MainMenuState.tick(): switch-construct.RECORD_OF_EVOLUTION's switch's default.");
                        break;
                }

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
                        System.out.println("MainMenuState.tick(): switch-construct.MAIN's switch's default.");
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
        //start (will enter PauseState).
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
            handler.getStateManager().pushIState(StateManager.State.PAUSE, null);
        }

        switch (currentMenuSelection) {
            case EVOLUTION:
                //b-button (return to MenuList.MAIN).
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    index = 0;
                    currentMenuSelection = MenuList.MAIN;
                }

                break;
            case CAPABILITY:
                //b-button (return to MenuList.MAIN).
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    index = 0;
                    currentMenuSelection = MenuList.MAIN;
                }

                break;
            case RECORD_OF_EVOLUTION:
                //a-button (either SAVES game or set currentMenuSelection to MenuList.MAIN).
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    switch (index) {
                        case 0:
                            String pathSavedGame = "qwerty789";
                            //@@@@@@@@@@@@@@@
                            //SAVES THE GAME
                            //@@@@@@@@@@@@@@@
                            handler.getGame().getSaverAndLoader().save(pathSavedGame + ".bin");

                            System.out.println("SAVED GAME!");
                            currentMenuSelection = MenuList.MAIN;
                            index = 0;
                            break;
                        case 1:
                            //@@@@@@@@@@@@@@@
                            //LOADS THE GAME
                            //@@@@@@@@@@@@@@@
                            handler.getGame().getSaverAndLoader().load();

                            System.out.println("LOADED GAME!");
                            currentMenuSelection = MenuList.MAIN;
                            index = 0;
                            break;
                        default:
                            System.out.println("MainMenuState.getInput(): switch-construct.RECORD_OF_EVOLUTION's switch's default.");
                            break;
                    }
                }

                //b-button (return to MenuList.MAIN).
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    index = 0;
                    currentMenuSelection = MenuList.MAIN;
                }

                //right-button
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
                    index++;

                    if (index > 1) {
                        index = 0;
                    }
                }
                //left-button
                else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
                    index--;

                    if (index < 0) {
                        index = 1;
                    }
                }

                break;
            case MAIN:
                //a-button (assigns currentMenuSelection).
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    currentMenuSelection = MenuList.values()[index];
                    index = 0;
                }

                //b-button or select (will exit MainMenuState [goes back to previous IState]).
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD) ||
                        (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT))) {
                    currentMenuSelection = MenuList.MAIN;
                    handler.getStateManager().popIState();
                }

                //down-button and up-button (changes the index position).
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
                //background textbox.
                g.setColor(Color.GRAY);
                g.fillRect(30, Assets.mainMenu.getHeight()+20,
                        Assets.mainMenu.getWidth()+10, 30);
                //text.
                g.setColor(Color.WHITE);
                g.drawString("Record creature (save)?", 30+3, Assets.mainMenu.getHeight()+20+11);
                g.drawString("save", 43, Assets.mainMenu.getHeight()+20+25);
                g.drawString("load", 93, Assets.mainMenu.getHeight()+20+25);
                //cursor image: leftOverworld0.
                overWorldCursor.render(g);

                break;
            case MAIN:
                //repaint the render(Graphics) of the IState that is just below the top of the stack.
                handler.getStateManager().getStatesStack().get(handler.getStateManager().getStatesStack().size()-2).render(g);

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