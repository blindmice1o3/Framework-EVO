package com.evo.states;

import com.evo.Handler;
import com.evo.entities.moveable.player.Fish;
import com.evo.entities.moveable.player.FishStateManager;
import com.evo.game_stages.GameStage;
import com.evo.gfx.FontGrabber;
import com.evo.gfx.OverWorldCursor;
import com.evo.gfx.Assets;
import com.evo.tiles.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainMenuState implements IState {

    public enum MenuList { EVOLUTION, CAPABILITY, RECORD_OF_EVOLUTION, MAIN; }
    public enum IndexCapabilityMenu { JAWS, BODY, HANDS_AND_FEET; }

    private Handler handler;
    private OverWorldCursor overWorldCursor;

    private MenuList currentMenuSelection;
    private IndexCapabilityMenu currentIndexCapabilityMenu;
    private int index;

    public MainMenuState(Handler handler) {
        this.handler = handler;
        overWorldCursor = new OverWorldCursor(handler, Assets.leftOverworld0, 23, 31);
        overWorldCursor.setWidth( (Assets.leftOverworld0.getWidth() / 2) );
        overWorldCursor.setHeight( (Assets.leftOverworld0.getHeight() / 2) );

        currentMenuSelection = MenuList.MAIN;
        currentIndexCapabilityMenu = IndexCapabilityMenu.JAWS;
        index = 0;
    } // **** end MainMenuState(Handler) constructor ****

    int xYellowBorder, yYellowBorder, widthYellowBorder, heightYellowBorder;
    @Override
    public void tick(long timeElapsed) {
        getInput();

        switch (currentMenuSelection) {
            case EVOLUTION:

                break;
            case CAPABILITY:
                //determines yellow-border's on-screen location based on selected index.
                switch (currentIndexCapabilityMenu) {
                    case JAWS:
                        xYellowBorder = 13;
                        yYellowBorder = 55;
                        widthYellowBorder = 100;
                        heightYellowBorder = 40;
                        break;
                    case BODY:
                        xYellowBorder = 373;
                        yYellowBorder = 55;
                        widthYellowBorder = 100;
                        heightYellowBorder = 40;
                        break;
                    case HANDS_AND_FEET:
                        xYellowBorder = 478;
                        yYellowBorder = 55;
                        widthYellowBorder = 142;
                        heightYellowBorder = 40;
                        break;
                    default:
                        System.out.println("MainMenuState.tick(): switch-construct.CAPABILITY's switch's default.");
                        xYellowBorder = 0;
                        yYellowBorder = 0;
                        widthYellowBorder = handler.panelWidth;
                        heightYellowBorder = handler.panelHeight;
                        break;
                }

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
                    currentMenuSelection = MenuList.MAIN;
                    index = 0;
                }

                break;
            case CAPABILITY:
                switch ( currentIndexCapabilityMenu ) {
                    case JAWS:
                        //down-button
                        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                            index++;

                            if ( index > (FishStateManager.Jaws.values().length-1) ) {
                                index = 0;
                            }
                        }
                        //up-button
                        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                            index--;

                            if ( index < 0 ) {
                                index = (FishStateManager.Jaws.values().length-1);
                            }
                        }

                        //a-button (execute buying).
                        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                            if ( ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer() instanceof Fish) {
                                Fish player = (Fish)((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();

                                //check if player already have this choice currently equipped.
                                if (player.getFishStateManager().getCurrentJaws() == FishStateManager.Jaws.values()[index]) {
                                    String alreadyEquipped = "Already equipped (comma) can NOT buy FishStateManager.Jaws." +
                                            FishStateManager.Jaws.values()[index] + " (exclaimation mark)";
                                    Object[] args = { alreadyEquipped };
                                    handler.getStateManager().pushIState( StateManager.State.TEXTBOX, args );
                                }
                                //check if player have enough experience points.
                                else if (player.getExperiencePoints() >= FishStateManager.Jaws.values()[index].getCost()) {
                                    String confirmation = "Would you like to buy FishStateManager.Jaws." +
                                            FishStateManager.Jaws.values()[index] + " for " +
                                            FishStateManager.Jaws.values()[index].getCost() + " experience points (question mark)";
                                    Object[] args = { confirmation };
                                    handler.getStateManager().pushIState( StateManager.State.TEXTBOX, args );
                                }
                            }
                        }

                        break;
                    case BODY:
                        //down-button
                        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                            index++;

                            if ( index > (FishStateManager.BodySize.values().length-1) ) {
                                index = 0;
                            }
                        }
                        //up-button
                        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                            index--;

                            if ( index < 0 ) {
                                index = (FishStateManager.BodySize.values().length-1);
                            }
                        }

                        //a-button (execute buying).
                        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                            if ( ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer() instanceof Fish) {
                                Fish player = (Fish)((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();

                                //check if player already have this choice currently equipped.
                                if (player.getFishStateManager().getCurrentBodySize() == FishStateManager.BodySize.values()[index]) {
                                    String alreadyEquipped = "Already equipped (comma) can NOT buy FishStateManager.BodySize." +
                                            FishStateManager.BodySize.values()[index] + " (exclaimation mark)";
                                    Object[] args = { alreadyEquipped };
                                    handler.getStateManager().pushIState( StateManager.State.TEXTBOX, args );
                                }
                                //check if player have enough experience points.
                                else if (player.getExperiencePoints() >= FishStateManager.BodySize.values()[index].getCost()) {
                                    String confirmation = "Would you like to buy FishStateManager.BodySize." +
                                            FishStateManager.BodySize.values()[index] + " for " +
                                            FishStateManager.BodySize.values()[index].getCost() + " experience points (question mark)";
                                    Object[] args = { confirmation };
                                    handler.getStateManager().pushIState( StateManager.State.TEXTBOX, args );
                                }
                            }
                        }

                        break;
                    case HANDS_AND_FEET:
                        //down-button
                        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                            index++;

                            if ( index > (FishStateManager.BodyTexture.values().length-1) ) {
                                index = 0;
                            }
                        }
                        //up-button
                        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                            index--;

                            if ( index < 0 ) {
                                index = (FishStateManager.BodyTexture.values().length-1);
                            }
                        }

                        //a-button (execute buying).
                        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                            if ( ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer() instanceof Fish) {
                                Fish player = (Fish)((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();

                                //check if player already have this choice currently equipped.
                                if (player.getFishStateManager().getCurrentBodyTexture() == FishStateManager.BodyTexture.values()[index]) {
                                    String alreadyEquipped = "Already equipped (comma) can NOT buy FishStateManager.BodyTexture. " +
                                            FishStateManager.BodyTexture.values()[index] + " (exclaimation mark)";
                                    Object[] args = { alreadyEquipped };
                                    handler.getStateManager().pushIState( StateManager.State.TEXTBOX, args );
                                }
                                //check if player have enough experience points.
                                else if (player.getExperiencePoints() >= FishStateManager.BodyTexture.values()[index].getCost()) {
                                    String confirmation = "Would you like to buy FishStateManager.BodyTexture." +
                                            FishStateManager.BodyTexture.values()[index] + " for " +
                                            FishStateManager.BodyTexture.values()[index].getCost() + " experience points (question mark)";
                                    Object[] args = { confirmation };
                                    handler.getStateManager().pushIState( StateManager.State.TEXTBOX, args );
                                }
                            }
                        }

                        break;
                    default:
                        System.out.println("MainMenuState.getInput(): switch-construct.CAPABILITY's switch's default.");
                        break;
                }

                //b-button (return to MenuList.MAIN).
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    currentMenuSelection = MenuList.MAIN;
                    index = 0;
                }

                //right-button
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
                    //we were at the end of the list, must set back to front of list.
                    if ( currentIndexCapabilityMenu.ordinal() == (IndexCapabilityMenu.values().length-1) ) {
                        currentIndexCapabilityMenu =
                                IndexCapabilityMenu.values()[ 0 ];
                    }
                    //otherwise, just increment the currentIndexCapabilityMenu to the next enum.
                    else {
                        currentIndexCapabilityMenu =
                                IndexCapabilityMenu.values()[ (currentIndexCapabilityMenu.ordinal() + 1) ];
                    }

                    //////////
                    index = 0;
                    //////////
                }
                //left-button
                else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
                    //we were at the front of the list, must set currentIndexCapabilityMenu to last enum of list.
                    if ( currentIndexCapabilityMenu.ordinal() == 0 ) {
                        currentIndexCapabilityMenu =
                                IndexCapabilityMenu.values()[ (IndexCapabilityMenu.values().length-1) ];
                    }
                    //otherwise, just decrement the currentIndexCapabilityMenu.
                    else {
                        currentIndexCapabilityMenu =
                                IndexCapabilityMenu.values()[ (currentIndexCapabilityMenu.ordinal() - 1) ];
                    }

                    //////////
                    index = 0;
                    //////////
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
                //repaint the render(Graphics) of the IState that is just below the top of the stack.
                handler.getStateManager().getState(StateManager.State.GAME_STAGE).render(g);

                /////////////////////////////////////////////////////////////////////////////////////////////////////
                Graphics2D g2d = (Graphics2D)g;
                float opacity = 0.7f;

                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.mainMenuCapability, 0, 1, handler.panelWidth, (handler.panelHeight/3), null);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                /////////////////////////////////////////////////////////////////////////////////////////////////////

                /////////////////////////////////////////////////////////////////////////////////
                int borderSize = 5;
                g.setColor(Color.YELLOW);
                for (int i = 0; i < borderSize; i++) {
                    int x = xYellowBorder + i;
                    int y = yYellowBorder + i;
                    int width = widthYellowBorder - (2*i);
                    int height = heightYellowBorder - (2*i);

                    g.drawRect(x, y, width, height);
                }
                /////////////////////////////////////////////////////////////////////////////////

                int x = 15;
                int y = 1 + (handler.panelHeight/3) + 10;
                switch (currentIndexCapabilityMenu) {
                    case JAWS:
                        for (FishStateManager.Jaws jaws : FishStateManager.Jaws.values()) {
                            FontGrabber.renderString(g, jaws.toString(), x, y, 10, 10);
                            FontGrabber.renderString(g, Integer.toString( jaws.getCost() ),
                                    x+(jaws.toString().length()*10)+10, y, 10, 10);

                            if (jaws.ordinal() == index) {
                                overWorldCursor.setX(x-10);
                                overWorldCursor.setY(y+3);
                                overWorldCursor.render(g);
                            }

                            y = y + 15;
                        }

                        break;
                    case BODY:
                        for (FishStateManager.BodySize bodySize : FishStateManager.BodySize.values()) {
                            FontGrabber.renderString(g, bodySize.toString(), x, y, 10, 10);
                            FontGrabber.renderString(g, Integer.toString( bodySize.getCost() ),
                                    x+(bodySize.toString().length()*10)+10, y, 10, 10);

                            if (bodySize.ordinal() == index) {
                                overWorldCursor.setX(x-10);
                                overWorldCursor.setY(y+3);
                                overWorldCursor.render(g);
                            }

                            y = y + 15;
                        }

                        break;
                    case HANDS_AND_FEET:
                        for (FishStateManager.BodyTexture bodyTexture : FishStateManager.BodyTexture.values()) {
                            FontGrabber.renderString(g, bodyTexture.toString(), x, y, 10, 10);
                            FontGrabber.renderString(g, Integer.toString( bodyTexture.getCost() ),
                                    x+(bodyTexture.toString().length()*10)+10, y, 10, 10);

                            if (bodyTexture.ordinal() == index) {
                                overWorldCursor.setX(x-10);
                                overWorldCursor.setY(y+3);
                                overWorldCursor.render(g);
                            }

                            y = y + 15;
                        }

                        break;
                    default:
                        System.out.println("MainMenuState.render(Graphics), switch-constructor(currentIndexCapabilityMenu)'s default");
                        break;
                }

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

    }

    @Override
    public void exit() {

    }

} // **** end MainMenuState class ****