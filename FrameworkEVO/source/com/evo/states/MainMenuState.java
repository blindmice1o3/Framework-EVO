package com.evo.states;

import com.evo.Handler;
import com.evo.entities.moveable.player.Fish;
import com.evo.entities.moveable.player.FishStateManager;
import com.evo.entities.moveable.player.IPlayable;
import com.evo.gfx.FontGrabber;
import com.evo.gfx.OverWorldCursor;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class MainMenuState implements IState {

    public enum MenuList { EVOLUTION, CAPABILITY, RECORD_OF_EVOLUTION, MAIN, CONFIRMATION; }
    public enum IndexEvolutionMenu { JAWS, BODY, HANDS_AND_FEET, TAIL; }
    public enum PlayerStatsKey {
        CLASSIFICATION, HIT_POINT_MAX, BITING, STRENGTH, KICK, STRIKE, HORN, DEFENSE_POWER, AGILITY, JUMPING_ABILITY;

        @Override
        public String toString() {
            String returner = null;

            switch (this) {
                case CLASSIFICATION:
                    returner = "Classification";
                    break;
                case HIT_POINT_MAX:
                    returner = "Hit Point Max";
                    break;
                case BITING:
                    returner = "Biting";
                    break;
                case STRENGTH:
                    returner = "Strength";
                    break;
                case KICK:
                    returner = "Kick";
                    break;
                case STRIKE:
                    returner = "Strike";
                    break;
                case HORN:
                    returner = "Horn";
                    break;
                case DEFENSE_POWER:
                    returner = "Defense Power";
                    break;
                case AGILITY:
                    returner = "Agility";
                    break;
                case JUMPING_ABILITY:
                    returner = "Jumping Ability";
                    break;
                default:
                    System.out.println("MainMenuState.PlayerStatsKey.toString() switch's default.");
                    returner = "";
                    break;
            }

            return returner;
        }
    }

    private Handler handler;
    private OverWorldCursor overWorldCursor;

    private MenuList currentMenuSelection;
    private IndexEvolutionMenu currentIndexEvolutionMenu;
    private int index;
    private boolean yesConfirm;

    private int xPlayerStatsBox;
    private int yPlayerStatsBox;
    private int widthPlayerStatsBox;
    private int heightPlayerStatsBox;

    public MainMenuState(Handler handler) {
        this.handler = handler;
        overWorldCursor = new OverWorldCursor(handler, Assets.leftOverworld0, 23, 31);
        overWorldCursor.setWidth( (Assets.leftOverworld0.getWidth() / 2) );
        overWorldCursor.setHeight( (Assets.leftOverworld0.getHeight() / 2) );

        currentMenuSelection = MenuList.MAIN;
        currentIndexEvolutionMenu = IndexEvolutionMenu.JAWS;
        index = 0;
        yesConfirm = false;

        xPlayerStatsBox = (handler.panelWidth/2) + 25;
        yPlayerStatsBox = 1+(handler.panelHeight/3)+10;
        widthPlayerStatsBox = (handler.panelWidth/2) - 50;
        heightPlayerStatsBox = (handler.panelHeight) - yPlayerStatsBox - 20;
    } // **** end MainMenuState(Handler) constructor ****

    int xYellowBorder, yYellowBorder, widthYellowBorder, heightYellowBorder;
    @Override
    public void tick(long timeElapsed) {
        getInput();

        switch (currentMenuSelection) {
            case EVOLUTION:
                switch (currentIndexEvolutionMenu) {
                    //determines yellow-border's on-screen location based on selected index.
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
                    case TAIL:
                        xYellowBorder = 160;
                        yYellowBorder = 100;
                        widthYellowBorder = 100;
                        heightYellowBorder = 43;
                        break;
                    default:
                        System.out.println("MainMenuState.tick(): switch-construct.CAPABILITY's switch's default.");
                        xYellowBorder = 0;
                        yYellowBorder = 0;
                        widthYellowBorder = handler.panelWidth;
                        heightYellowBorder = handler.panelHeight;
                        break;
                }

                overWorldCursor.setX(5);
                overWorldCursor.setY(yPlayerStatsBox+3+(index*15));

                break;
            case CAPABILITY:
                //TODO: MainMenuState(MenuList.CAPABILITY).tick().

                break;
            case RECORD_OF_EVOLUTION:
                //determines cursor's on-screen location (to indicate: yes or no) based on selected index.
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
                //determines cursor's on-screen location (to indicate: Evolution, Capability, or Record of Evolution)
                //based on selected index.
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
            case CONFIRMATION:
                if (yesConfirm) {
                    overWorldCursor.setX(33);
                    overWorldCursor.setY((handler.panelHeight/2)+150+18);
                } else {
                    overWorldCursor.setX(83);
                    overWorldCursor.setY((handler.panelHeight/2)+150+18);
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

                //right-button
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
                    //we were at the end of the list, must set back to front of list.
                    if ( currentIndexEvolutionMenu.ordinal() == (IndexEvolutionMenu.values().length-1) ) {
                        currentIndexEvolutionMenu =
                                IndexEvolutionMenu.values()[ 0 ];
                    }
                    //otherwise, just increment the currentIndexEvolutionMenu to the next enum.
                    else {
                        currentIndexEvolutionMenu =
                                IndexEvolutionMenu.values()[ (currentIndexEvolutionMenu.ordinal() + 1) ];
                    }

                    //////////
                    index = 0;
                    //////////
                }
                //left-button
                else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
                    //we were at the front of the list, must set currentIndexEvolutionMenu to last enum of list.
                    if ( currentIndexEvolutionMenu.ordinal() == 0 ) {
                        currentIndexEvolutionMenu =
                                IndexEvolutionMenu.values()[ (IndexEvolutionMenu.values().length-1) ];
                    }
                    //otherwise, just decrement the currentIndexEvolutionMenu.
                    else {
                        currentIndexEvolutionMenu =
                                IndexEvolutionMenu.values()[ (currentIndexEvolutionMenu.ordinal() - 1) ];
                    }

                    //////////
                    index = 0;
                    //////////
                }



                switch (currentIndexEvolutionMenu) {
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
                                //not enough experience points to buy element at current index.
                                else if (player.getExperiencePoints() < FishStateManager.Jaws.values()[index].getCost()) {
                                    String notEnoughExpPoints = "Not enough experience points to buy FishStateManager.Jaws." +
                                            FishStateManager.Jaws.values()[index] + ". You need " +
                                            (FishStateManager.Jaws.values()[index].getCost() - player.getExperiencePoints()) +
                                            " more experience points.";
                                    Object[] args = { notEnoughExpPoints };
                                    handler.getStateManager().pushIState( StateManager.State.TEXTBOX, args );
                                }
                                //check if player have enough experience points.
                                else if (player.getExperiencePoints() >= FishStateManager.Jaws.values()[index].getCost()) {
                                    /////////////////////////////////////////////
                                    currentMenuSelection = MenuList.CONFIRMATION;
                                    /////////////////////////////////////////////
                                }
                            }
                        }

                        break;
                    //BodyTexture !!!AND!!! BodySize
                    case BODY:
                        //down-button
                        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                            index++;

                            if ( index >
                                    ((FishStateManager.BodyTexture.values().length-1) +
                                            (FishStateManager.BodySize.values().length)) ) {
                                index = 0;
                            }
                        }
                        //up-button
                        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                            index--;

                            if ( index < 0 ) {
                                index = ((FishStateManager.BodyTexture.values().length-1) +
                                        (FishStateManager.BodySize.values().length));
                            }
                        }

                        //a-button (execute buying).
                        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                            if ( ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer() instanceof Fish) {
                                Fish player = (Fish)((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();

                                //BODY_TEXTURE
                                if (index <= (FishStateManager.BodyTexture.values().length-1)) {
                                    //check if player already have this choice currently equipped.
                                    if (player.getFishStateManager().getCurrentBodyTexture() ==
                                            FishStateManager.BodyTexture.values()[index]) {
                                        String alreadyEquipped = "Already equipped (comma) can NOT buy FishStateManager.BodyTexture." +
                                                FishStateManager.BodyTexture.values()[index] + " (exclaimation mark)";
                                        Object[] args = {alreadyEquipped};
                                        handler.getStateManager().pushIState(StateManager.State.TEXTBOX, args);
                                    }
                                    //not enough experience points to buy element at current index.
                                    else if (player.getExperiencePoints() < FishStateManager.BodyTexture.values()[index].getCost()) {
                                        String notEnoughExpPoints = "Not enough experience points to buy FishStateManager.BodyTexture." +
                                                FishStateManager.BodyTexture.values()[index] + ". You need " +
                                                (FishStateManager.BodyTexture.values()[index].getCost() - player.getExperiencePoints()) +
                                                " more experience points.";
                                        Object[] args = {notEnoughExpPoints};
                                        handler.getStateManager().pushIState(StateManager.State.TEXTBOX, args);
                                    }
                                    //check if player have enough experience points.
                                    else if (player.getExperiencePoints() >= FishStateManager.BodyTexture.values()[index].getCost()) {
                                        /////////////////////////////////////////////
                                        currentMenuSelection = MenuList.CONFIRMATION;
                                        /////////////////////////////////////////////
                                    }
                                }
                                //BODY_SIZE
                                else if (index > (FishStateManager.BodyTexture.values().length-1)) {
                                    //check if player already have this choice currently equipped.
                                    if (player.getFishStateManager().getCurrentBodySize() ==
                                            FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)]) {
                                        String alreadyEquipped = "Already equipped (comma) can NOT buy FishStateManager.BodySize." +
                                                FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)] +
                                                " (exclaimation mark)";
                                        Object[] args = {alreadyEquipped};
                                        handler.getStateManager().pushIState(StateManager.State.TEXTBOX, args);
                                    }
                                    //not enough experience points to buy element at current index.
                                    else if (player.getExperiencePoints() <
                                            FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)].getCost()) {
                                        String notEnoughExpPoints = "Not enough experience points to buy FishStateManager.BodySize." +
                                                FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)] +
                                                ". You need " +
                                                (FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)].getCost() - player.getExperiencePoints()) +
                                                " more experience points.";
                                        Object[] args = {notEnoughExpPoints};
                                        handler.getStateManager().pushIState(StateManager.State.TEXTBOX, args);
                                    }
                                    //check if player have enough experience points.
                                    else if (player.getExperiencePoints() >=
                                            FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)].getCost()) {
                                        /////////////////////////////////////////////
                                        currentMenuSelection = MenuList.CONFIRMATION;
                                        /////////////////////////////////////////////
                                    }
                                }
                            }
                        }

                        break;
                    case HANDS_AND_FEET:
                        //down-button
                        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                            index++;

                            if ( index > (FishStateManager.FinPectoral.values().length-1) ) {
                                index = 0;
                            }
                        }
                        //up-button
                        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                            index--;

                            if ( index < 0 ) {
                                index = (FishStateManager.FinPectoral.values().length-1);
                            }
                        }

                        //a-button (execute buying).
                        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                            if ( ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer() instanceof Fish) {
                                Fish player = (Fish)((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();

                                //check if player already have this choice currently equipped.
                                if (player.getFishStateManager().getCurrentFinPectoral() == FishStateManager.FinPectoral.values()[index]) {
                                    String alreadyEquipped = "Already equipped (comma) can NOT buy FishStateManager.FinPectoral. " +
                                            FishStateManager.FinPectoral.values()[index] + " (exclaimation mark)";
                                    Object[] args = { alreadyEquipped };
                                    handler.getStateManager().pushIState( StateManager.State.TEXTBOX, args );
                                }
                                //not enough experience points to buy element at current index.
                                else if (player.getExperiencePoints() < FishStateManager.FinPectoral.values()[index].getCost()) {
                                    String notEnoughExpPoints = "Not enough experience points to buy FishStateManager.FinPectoral." +
                                            FishStateManager.FinPectoral.values()[index] + ". You need " +
                                            (FishStateManager.FinPectoral.values()[index].getCost() - player.getExperiencePoints()) +
                                            " more experience points.";
                                    Object[] args = { notEnoughExpPoints };
                                    handler.getStateManager().pushIState( StateManager.State.TEXTBOX, args );
                                }
                                //check if player have enough experience points.
                                else if (player.getExperiencePoints() >= FishStateManager.FinPectoral.values()[index].getCost()) {
                                    /////////////////////////////////////////////
                                    currentMenuSelection = MenuList.CONFIRMATION;
                                    /////////////////////////////////////////////
                                }
                            }
                        }

                        break;
                    case TAIL:
                        //down-button
                        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                            index++;

                            if ( index > (FishStateManager.Tail.values().length-1) ) {
                                index = 0;
                            }
                        }
                        //up-button
                        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                            index--;

                            if ( index < 0 ) {
                                index = (FishStateManager.Tail.values().length-1);
                            }
                        }

                        //a-button (execute buying).
                        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                            if ( ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer() instanceof Fish) {
                                Fish player = (Fish)((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();

                                //check if player already have this choice currently equipped.
                                if (player.getFishStateManager().getCurrentTail() == FishStateManager.Tail.values()[index]) {
                                    String alreadyEquipped = "Already equipped (comma) can NOT buy FishStateManager.Tail." +
                                            FishStateManager.Tail.values()[index] + " (exclaimation mark)";
                                    Object[] args = { alreadyEquipped };
                                    handler.getStateManager().pushIState( StateManager.State.TEXTBOX, args );
                                }
                                //not enough experience points to buy element at current index.
                                else if (player.getExperiencePoints() < FishStateManager.Tail.values()[index].getCost()) {
                                    String notEnoughExpPoints = "Not enough experience points to buy FishStateManager.Tail." +
                                            FishStateManager.Tail.values()[index] + ". You need " +
                                            (FishStateManager.Tail.values()[index].getCost() - player.getExperiencePoints()) +
                                            " more experience points.";
                                    Object[] args = { notEnoughExpPoints };
                                    handler.getStateManager().pushIState( StateManager.State.TEXTBOX, args );
                                }
                                //check if player have enough experience points.
                                else if (player.getExperiencePoints() >= FishStateManager.Tail.values()[index].getCost()) {
                                    /////////////////////////////////////////////
                                    currentMenuSelection = MenuList.CONFIRMATION;
                                    /////////////////////////////////////////////
                                }
                            }
                        }

                        break;
                    default:
                        System.out.println("MainMenuState.getInput(): MenuList.EVOLUTION's switch(currentIndexEvolutionMenu)'s default.");
                        break;
                }



                break;
            case CAPABILITY:
                //TODO: MainMenuState.getInput() MenuList.CAPABILITY.

                //b-button (return to MenuList.MAIN).
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    currentMenuSelection = MenuList.MAIN;
                    index = 0;
                }
                //@@@@TESTING MainMenuState.MenuList.CAPABILITY (KeyEvent.VK_K)@@@@
                else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_K)) {
                    currentMenuSelection = MenuList.MAIN;
                    handler.getStateManager().popIState();
                }
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

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

                    //minus 2 extra because MAIN and CONFIRMATION do NOT count as a menu selection choice.
                    if ( index > ((MenuList.values().length-1)-1-1) ) {
                        index = 0;
                    }
                } else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                    index--;

                    if (index < 0) {
                        //minus 2 extra because MAIN and CONFIRMATION do NOT count as a menu selection choice.
                        index = ((MenuList.values().length-1)-1-1);
                    }
                }

                break;
            case CONFIRMATION:
                //right-button
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
                    yesConfirm = !yesConfirm;
                }
                //left-button
                else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
                    yesConfirm = !yesConfirm;
                }
                //b-button
                else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    currentMenuSelection = MenuList.EVOLUTION;
                }
                //a-button
                else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    //TODO: buying implementation.
                    if (yesConfirm) {
                        if ( ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer() instanceof Fish) {
                            //////////////////////////////////////////////////////////////////////////////////////
                            Fish player = (Fish)((GameStageState) handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();

                            //upgrade body part (buying feature).
                            int expCost = 0;
                            switch (currentIndexEvolutionMenu) {
                                case JAWS:
                                    //upgrade jaws.
                                    player.getFishStateManager().setCurrentJaws( (FishStateManager.Jaws.values()[index]) );
                                    System.out.println("setJaws: " + (FishStateManager.Jaws.values()[index]).toString());
                                    expCost = FishStateManager.Jaws.values()[index].getCost();
                                    break;
                                //BodyTexture !!!AND!!! BodySize
                                case BODY:
                                    if (index <= (FishStateManager.BodyTexture.values().length-1)) {
                                        player.getFishStateManager().setCurrentBodyTexture((FishStateManager.BodyTexture.values()[index]));
                                        System.out.println("setBodyTexture: " + (FishStateManager.BodyTexture.values()[index]).toString());
                                        expCost = FishStateManager.BodyTexture.values()[index].getCost();
                                    }
                                    else if (index > (FishStateManager.BodyTexture.values().length-1)) {
                                        player.getFishStateManager().setCurrentBodySize((FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)]));
                                        System.out.println("setBodySize: " + (FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)]).toString());
                                        expCost = FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)].getCost();
                                    }
                                    break;
                                case HANDS_AND_FEET:
                                    player.getFishStateManager().setCurrentFinPectoral( (FishStateManager.FinPectoral.values()[index]) );
                                    System.out.println("setFinPectoral: " + (FishStateManager.FinPectoral.values()[index]).toString());
                                    expCost = FishStateManager.FinPectoral.values()[index].getCost();
                                    break;
                                case TAIL:
                                    player.getFishStateManager().setCurrentTail( (FishStateManager.Tail.values()[index]) );
                                    System.out.println("setTail: " + (FishStateManager.Tail.values()[index]).toString());
                                    expCost = FishStateManager.Tail.values()[index].getCost();
                                    break;
                                default:
                                    System.out.println("MainMenuState(MenuList.CONFIRMATION).getInput() switch(IndexEvolutionMenu)'s default.");
                                    break;
                            }

                            /////////////////////////////////////
                            player.updateHeadAndTailAnimations();
                            player.updatePlayerStats();
                            /////////////////////////////////////

                            //deduct experience points
                            player.setExperiencePoints( (player.getExperiencePoints() - expCost) );

                            currentMenuSelection = MenuList.MAIN;
                            index = 0;
                            currentIndexEvolutionMenu = IndexEvolutionMenu.JAWS;
                            yesConfirm = false;

                            handler.getStateManager().popIState();
                            //////////////////////////////////////////////////////////////////////////////////////
                        } else {
                            System.out.println("PLAYER IS NOT A FISH INSTANCE!!!");
                        }
                    } else {
                        currentMenuSelection = MenuList.EVOLUTION;
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
                //repaint the render(Graphics) of the IState that is just below the top of the stack.
                handler.getStateManager().getState(StateManager.State.GAME_STAGE).render(g);

                /////////////////////////////////////////////////////////////////////////////////////////////////////
                Graphics2D g2d = (Graphics2D)g;
                float opacity = 0.7f;

                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.mainMenuEVOLUTION, 0, 1, handler.panelWidth, (handler.panelHeight/3), null);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                /////////////////////////////////////////////////////////////////////////////////////////////////////

                ////////////////////YELLOW_BORDER_IndexEvolutionMenu/////////////////////////////
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



                //TODO: refactor the following into something more maintainable.
                //@@@@@@@@@@@@@BOTTOM_RIGHT_MenuList.CAPABILITY-WITH-BONUSES-IN-Color.GREEN@@@@@@@@@@@@@@@@@
                renderCapability(g);
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                int x = 15;
                int y = yPlayerStatsBox;
                switch (currentIndexEvolutionMenu) {
                    case JAWS:
                        for (FishStateManager.Jaws jaws : FishStateManager.Jaws.values()) {
                            FontGrabber.renderString(g, jaws.toString(), x, y, 10, 10);
                            FontGrabber.renderString(g, Integer.toString( jaws.getCost() ),
                                    x+(jaws.toString().length()*10)+10, y, 10, 10);

                            if (jaws.ordinal() == index) {
                                overWorldCursor.render(g);

                                //TODO: display selected body-part bonuses.
                                renderBonusesFromJaws(g);
                            }

                            y = y + 15;
                        }

                        break;
                    //BodyTexture !!!AND!!! BodySize
                    case BODY:
                        for (FishStateManager.BodyTexture bodyTexture : FishStateManager.BodyTexture.values()) {
                            FontGrabber.renderString(g, bodyTexture.toString(), x, y, 10, 10);
                            FontGrabber.renderString(g, Integer.toString( bodyTexture.getCost() ),
                                    x+(bodyTexture.toString().length()*10)+10, y, 10, 10);

                            //BODY_TEXTURE
                            if (index <= (FishStateManager.BodyTexture.values().length-1)) {
                                if (bodyTexture.ordinal() == index) {
                                    overWorldCursor.render(g);

                                    //TODO: display selected body-part bonuses.
                                    renderBonusesFromBodyTexture(g);
                                }
                            }

                            y = y + 15;
                        }

                        for (FishStateManager.BodySize bodySize : FishStateManager.BodySize.values()) {
                            FontGrabber.renderString(g, bodySize.toString(), x, y, 10, 10);
                            FontGrabber.renderString(g, Integer.toString( bodySize.getCost() ),
                                    x+(bodySize.toString().length()*10)+10, y, 10, 10);

                            //BODY_SIZE
                            if (index > (FishStateManager.BodyTexture.values().length-1)) {
                                if (bodySize.ordinal() == (index-(FishStateManager.BodyTexture.values().length))) {
                                    overWorldCursor.render(g);

                                    //TODO: display selected body-part bonuses.
                                    renderBonusesFromBodySize(g);
                                }
                            }

                            y = y + 15;
                        }

                        break;
                    case HANDS_AND_FEET:
                        for (FishStateManager.FinPectoral finPectoral : FishStateManager.FinPectoral.values()) {
                            FontGrabber.renderString(g, finPectoral.toString(), x, y, 10, 10);
                            FontGrabber.renderString(g, Integer.toString( finPectoral.getCost() ),
                                    x+(finPectoral.toString().length()*10)+10, y, 10, 10);

                            if (finPectoral.ordinal() == index) {
                                overWorldCursor.render(g);

                                //TODO: display selected body-part bonuses.
                                renderBonusesFromHandsAndFeet(g);
                            }

                            y = y + 15;
                        }

                        break;
                    case TAIL:
                        for (FishStateManager.Tail tail : FishStateManager.Tail.values()) {
                            FontGrabber.renderString(g, tail.toString(), x, y, 10, 10);
                            FontGrabber.renderString(g, Integer.toString( tail.getCost() ),
                                    x+(tail.toString().length()*10)+10, y, 10, 10);

                            if (tail.ordinal() == index) {
                                overWorldCursor.render(g);

                                //TODO: display selected body-part bonuses.
                                renderBonusesFromTail(g);
                            }

                            y = y + 15;
                        }

                        break;
                    default:
                        System.out.println("MainMenuState.render(Graphics), switch-constructor(currentIndexEvolutionMenu)'s default");
                        break;
                }

                break;
            case CAPABILITY:
                renderCapability(g);

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
            case CONFIRMATION:
                //background textbox.
                g.setColor(Color.GRAY);
                g.fillRect(30, (handler.panelHeight/2)+150,
                        Assets.mainMenu.getWidth()+10, 30);
                //text.
                g.setColor(Color.WHITE);
                String confirmationMessage = null;
                switch (currentIndexEvolutionMenu) {
                    case JAWS:
                        confirmationMessage = "Spend " + FishStateManager.Jaws.values()[index].getCost() +
                                " experience point(s) for " + FishStateManager.Jaws.values()[index] + " Jaws?";
                        break;
                    case BODY:
                        if (index <= (FishStateManager.BodyTexture.values().length-1)) {
                            confirmationMessage = "Spend " + FishStateManager.BodyTexture.values()[index].getCost() +
                                    " experience point(s) for " + FishStateManager.BodyTexture.values()[index] + " BodyTexture?";
                        }
                        else if (index > (FishStateManager.BodyTexture.values().length-1)) {
                            confirmationMessage = "Spend " + FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)].getCost() +
                                    " experience point(s) for " + FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)] + " BodySize?";
                        }
                        break;
                    case HANDS_AND_FEET:
                        confirmationMessage = "Spend " + FishStateManager.FinPectoral.values()[index].getCost() +
                                " experience point(s) for " + FishStateManager.FinPectoral.values()[index] + " FinPectoral?";
                        break;
                    case TAIL:
                        confirmationMessage = "Spend " + FishStateManager.Tail.values()[index].getCost() +
                                " experience point(s) for " + FishStateManager.Tail.values()[index] + " Tail?";
                        break;
                    default:
                        System.out.println("MainMenuState(MenuList.CONFIRMATION).render(Graphics) switch(IndexEvolutionMenu)'s default.");
                        break;
                }
                g.drawString(confirmationMessage, 30+3, (handler.panelHeight/2)+150+11);
                g.drawString("yes", 43, (handler.panelHeight/2)+150+25);
                g.drawString("no", 93, (handler.panelHeight/2)+150+25);
                //cursor image: leftOverworld0.
                overWorldCursor.render(g);

                break;
            default:
                System.out.println("MainMenuState.render(Graphics): switch's default.");
                break;
        }
    }

    private void renderBonusesFromBodyTexture(Graphics g) {
        int xStringBonuses = handler.panelWidth - 75;
        int yStringBonuses = yPlayerStatsBox + 24 + 30; //+30 to skip 2 lines for PlayerStatsKey.CLASSIFICATION.

        Fish player = (Fish)((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();
        //i starting at 1 because PlayerStatsKey.CLASSIFICATION  was accounted for when initializing yStringBonuses.
        for (int i = 1; i < PlayerStatsKey.values().length; i++) {
            //PlayerStats beginning with PlayerStatsKey.Hit_Point_Max.
            if (i == PlayerStatsKey.DEFENSE_POWER.ordinal()) {
                int newDefense = FishStateManager.BodyTexture.values()[index].getDefenseBonus() -
                        player.getFishStateManager().getCurrentBodyTexture().getDefenseBonus();
                if (newDefense > 0) {
                    g.setColor(Color.GREEN);
                    g.drawString("+" + newDefense, xStringBonuses, yStringBonuses);
                } else if (newDefense < 0) {
                    g.setColor(Color.RED);
                    g.drawString(Integer.toString(newDefense), xStringBonuses, yStringBonuses);
                } else if (newDefense == 0) {
                    g.setColor(Color.YELLOW);
                    g.drawString(Integer.toString(newDefense), xStringBonuses, yStringBonuses);
                }
            }

            //just one-regular-new-line.
            yStringBonuses += 15;
        }
    }

    private void renderBonusesFromBodySize(Graphics g) {
        int xStringBonuses = handler.panelWidth - 75;
        int yStringBonuses = yPlayerStatsBox + 24 + 30; //+30 to skip 2 lines for PlayerStatsKey.CLASSIFICATION.

        Fish player = (Fish)((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();
        //i starting at 1 because PlayerStatsKey.CLASSIFICATION  was accounted for when initializing yStringBonuses.
        for (int i = 1; i < PlayerStatsKey.values().length; i++) {
            //PlayerStats beginning with PlayerStatsKey.Hit_Point_Max.
            if (i == PlayerStatsKey.HIT_POINT_MAX.ordinal()) {
                int newHitPointMax = FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)].getHealthMaxBonus() -
                        player.getFishStateManager().getCurrentBodySize().getHealthMaxBonus();
                if (newHitPointMax > 0) {
                    g.setColor(Color.GREEN);
                    g.drawString("+" + newHitPointMax, xStringBonuses, yStringBonuses);
                } else if (newHitPointMax < 0) {
                    g.setColor(Color.RED);
                    g.drawString(Integer.toString(newHitPointMax), xStringBonuses, yStringBonuses);
                } else if (newHitPointMax == 0) {
                    g.setColor(Color.YELLOW);
                    g.drawString(Integer.toString(newHitPointMax), xStringBonuses, yStringBonuses);
                }
            } else if (i == PlayerStatsKey.STRENGTH.ordinal()) {
                int newStrength = FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)].getStrengthBonus() -
                        player.getFishStateManager().getCurrentBodySize().getStrengthBonus();
                if (newStrength > 0) {
                    g.setColor(Color.GREEN);
                    g.drawString("+" + newStrength, xStringBonuses, yStringBonuses);
                } else if (newStrength < 0) {
                    g.setColor(Color.RED);
                    g.drawString(Integer.toString(newStrength), xStringBonuses, yStringBonuses);
                } else if (newStrength == 0) {
                    g.setColor(Color.YELLOW);
                    g.drawString(Integer.toString(newStrength), xStringBonuses, yStringBonuses);
                }
            } else if (i == PlayerStatsKey.DEFENSE_POWER.ordinal()) {
                int newDefense = FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)].getDefenseBonus() -
                        player.getFishStateManager().getCurrentBodySize().getDefenseBonus();
                if (newDefense > 0) {
                    g.setColor(Color.GREEN);
                    g.drawString("+" + newDefense, xStringBonuses, yStringBonuses);
                } else if (newDefense < 0) {
                    g.setColor(Color.RED);
                    g.drawString(Integer.toString(newDefense), xStringBonuses, yStringBonuses);
                } else if (newDefense == 0) {
                    g.setColor(Color.YELLOW);
                    g.drawString(Integer.toString(newDefense), xStringBonuses, yStringBonuses);
                }
            } else if (i == PlayerStatsKey.AGILITY.ordinal()) {
                int newAgility = FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)].getAgilityBonus() -
                        player.getFishStateManager().getCurrentBodySize().getAgilityBonus();
                if (newAgility > 0) {
                    g.setColor(Color.GREEN);
                    g.drawString("+" + newAgility, xStringBonuses, yStringBonuses);
                } else if (newAgility < 0) {
                    g.setColor(Color.RED);
                    g.drawString(Integer.toString(newAgility), xStringBonuses, yStringBonuses);
                } else if (newAgility == 0) {
                    g.setColor(Color.YELLOW);
                    g.drawString(Integer.toString(newAgility), xStringBonuses, yStringBonuses);
                }
            }

            //just one-regular-new-line.
            yStringBonuses += 15;
        }
    }

    private void renderBonusesFromTail(Graphics g) {
        int xStringBonuses = handler.panelWidth - 75;
        int yStringBonuses = yPlayerStatsBox + 24 + 30; //+30 to skip 2 lines for PlayerStatsKey.CLASSIFICATION.

        Fish player = (Fish)((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();
        //i starting at 1 because PlayerStatsKey.CLASSIFICATION  was accounted for when initializing yStringBonuses.
        for (int i = 1; i < PlayerStatsKey.values().length; i++) {
            //PlayerStats beginning with PlayerStatsKey.Hit_Point_Max.
            if (i == PlayerStatsKey.HIT_POINT_MAX.ordinal()) {
                int newHitPointMax = FishStateManager.Tail.values()[index].getHealthMaxBonus() -
                        player.getFishStateManager().getCurrentTail().getHealthMaxBonus();
                if (newHitPointMax > 0) {
                    g.setColor(Color.GREEN);
                    g.drawString("+" + newHitPointMax, xStringBonuses, yStringBonuses);
                } else if (newHitPointMax < 0) {
                    g.setColor(Color.RED);
                    g.drawString(Integer.toString(newHitPointMax), xStringBonuses, yStringBonuses);
                } else if (newHitPointMax == 0) {
                    g.setColor(Color.YELLOW);
                    g.drawString(Integer.toString(newHitPointMax), xStringBonuses, yStringBonuses);
                }
            } else if (i == PlayerStatsKey.AGILITY.ordinal()) {
                int newAgility = FishStateManager.Tail.values()[index].getAgilityBonus() -
                        player.getFishStateManager().getCurrentTail().getAgilityBonus();
                if (newAgility > 0) {
                    g.setColor(Color.GREEN);
                    g.drawString("+" + newAgility, xStringBonuses, yStringBonuses);
                } else if (newAgility < 0) {
                    g.setColor(Color.RED);
                    g.drawString(Integer.toString(newAgility), xStringBonuses, yStringBonuses);
                } else if (newAgility == 0) {
                    g.setColor(Color.YELLOW);
                    g.drawString(Integer.toString(newAgility), xStringBonuses, yStringBonuses);
                }
            } else if (i == PlayerStatsKey.JUMPING_ABILITY.ordinal()) {
                int newJump = FishStateManager.Tail.values()[index].getJumpBonus() -
                        player.getFishStateManager().getCurrentTail().getJumpBonus();
                if (newJump > 0) {
                    g.setColor(Color.GREEN);
                    g.drawString("+" + newJump, xStringBonuses, yStringBonuses);
                } else if (newJump < 0) {
                    g.setColor(Color.RED);
                    g.drawString(Integer.toString(newJump), xStringBonuses, yStringBonuses);
                } else if (newJump == 0) {
                    g.setColor(Color.YELLOW);
                    g.drawString(Integer.toString(newJump), xStringBonuses, yStringBonuses);
                }
            }

            //just one-regular-new-line.
            yStringBonuses += 15;
        }
    }

    private void renderBonusesFromHandsAndFeet(Graphics g) {
        int xStringBonuses = handler.panelWidth - 75;
        int yStringBonuses = yPlayerStatsBox + 24 + 30; //+30 to skip 2 lines for PlayerStatsKey.CLASSIFICATION.

        Fish player = (Fish)((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();
        //i starting at 1 because PlayerStatsKey.CLASSIFICATION  was accounted for when initializing yStringBonuses.
        for (int i = 1; i < PlayerStatsKey.values().length; i++) {
            //PlayerStats beginning with PlayerStatsKey.Hit_Point_Max.
            if (i == PlayerStatsKey.STRENGTH.ordinal()) {
                int newStrength = FishStateManager.FinPectoral.values()[index].getStrengthBonus() -
                        player.getFishStateManager().getCurrentFinPectoral().getStrengthBonus();
                if (newStrength > 0) {
                    g.setColor(Color.GREEN);
                    g.drawString("+" + newStrength, xStringBonuses, yStringBonuses);
                } else if (newStrength < 0) {
                    g.setColor(Color.RED);
                    g.drawString(Integer.toString(newStrength), xStringBonuses, yStringBonuses);
                } else if (newStrength == 0) {
                    g.setColor(Color.YELLOW);
                    g.drawString(Integer.toString(newStrength), xStringBonuses, yStringBonuses);
                }
            } else if (i == PlayerStatsKey.AGILITY.ordinal()) {
                int newAgility = FishStateManager.FinPectoral.values()[index].getAgilityBonus() -
                        player.getFishStateManager().getCurrentFinPectoral().getAgilityBonus();
                if (newAgility > 0) {
                    g.setColor(Color.GREEN);
                    g.drawString("+" + newAgility, xStringBonuses, yStringBonuses);
                } else if (newAgility < 0) {
                    g.setColor(Color.RED);
                    g.drawString(Integer.toString(newAgility), xStringBonuses, yStringBonuses);
                } else if (newAgility == 0) {
                    g.setColor(Color.YELLOW);
                    g.drawString(Integer.toString(newAgility), xStringBonuses, yStringBonuses);
                }
            }

            //just one-regular-new-line.
            yStringBonuses += 15;
        }
    }

    private void renderBonusesFromJaws(Graphics g) {
        int xStringBonuses = handler.panelWidth - 75;
        int yStringBonuses = yPlayerStatsBox + 24 + 30; //+30 to skip 2 lines for PlayerStatsKey.CLASSIFICATION.

        Fish player = (Fish)((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();
        //i starting at 1 because PlayerStatsKey.CLASSIFICATION  was accounted for when initializing yStringBonuses.
        for (int i = 1; i < PlayerStatsKey.values().length; i++) {
            //PlayerStats beginning with PlayerStatsKey.Hit_Point_Max.
            if (i == PlayerStatsKey.BITING.ordinal()) {
                int newDamage = FishStateManager.Jaws.values()[index].getDamageBiteBonus() -
                        player.getFishStateManager().getCurrentJaws().getDamageBiteBonus();
                if (newDamage > 0) {
                    g.setColor(Color.GREEN);
                    g.drawString("+" + newDamage, xStringBonuses, yStringBonuses);
                } else if (newDamage < 0) {
                    g.setColor(Color.RED);
                    g.drawString(Integer.toString(newDamage), xStringBonuses, yStringBonuses);
                } else if (newDamage == 0) {
                    g.setColor(Color.YELLOW);
                    g.drawString(Integer.toString(newDamage), xStringBonuses, yStringBonuses);
                }
            }

            //just one-regular-new-line.
            yStringBonuses += 15;
        }
    }

    private void renderCapability(Graphics g) {
        //background textbox.
        g.setColor(Color.GRAY);
        g.fillRect(xPlayerStatsBox, yPlayerStatsBox, widthPlayerStatsBox, heightPlayerStatsBox);

        IPlayable iPlayable = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();
        if (iPlayable instanceof Fish) {
            Fish player = (Fish)iPlayable;
            //////////////////////////////////////
            Map<PlayerStatsKey, String> playerStats = new HashMap<PlayerStatsKey, String>();
            playerStats.put( PlayerStatsKey.CLASSIFICATION, player.getCurrentForm().toString() );
            playerStats.put( PlayerStatsKey.HIT_POINT_MAX, Integer.toString(player.getHealthMax()) );
            playerStats.put( PlayerStatsKey.BITING, Integer.toString(player.getFishStateManager().getDamageBite()) );
            playerStats.put( PlayerStatsKey.STRENGTH, Integer.toString(player.getFishStateManager().getDamageStrength()) );
            playerStats.put( PlayerStatsKey.KICK, Integer.toString(player.getFishStateManager().getDamageKick()) );
            playerStats.put( PlayerStatsKey.STRIKE, Integer.toString(player.getFishStateManager().getDamageStrike()) );
            playerStats.put( PlayerStatsKey.HORN, Integer.toString(player.getFishStateManager().getDamageHorn()) );
            playerStats.put( PlayerStatsKey.DEFENSE_POWER, Integer.toString(player.getFishStateManager().getDefense()) );
            playerStats.put( PlayerStatsKey.AGILITY, Integer.toString(player.getFishStateManager().getAgility()) );
            playerStats.put( PlayerStatsKey.JUMPING_ABILITY, Integer.toString(player.getFishStateManager().getJump()) );
            //////////////////////////////////////

            //text.
            int xStringKey = xPlayerStatsBox + 25;
            int yString = yPlayerStatsBox + 25;
            int xStringValue = handler.panelWidth - 100;

            g.setColor(Color.WHITE);
            for (int i = 0; i < PlayerStatsKey.values().length; i++) {
                //CLASSIFICATION (Fish.Form.FISH).
                if (i == 0) {
                    g.drawString(PlayerStatsKey.values()[i].toString() + ": ", xStringKey, yString);
                    //do NOT add "P".
                    g.drawString( playerStats.get(PlayerStatsKey.values()[i]), xStringValue, yString);
                    //one-regular-new-line AND one-EXTRA-new-line.
                    yString += (15+15);
                }
                //POINTS (NUMERIC VALUES).
                else {
                    g.drawString(PlayerStatsKey.values()[i].toString() + ": ", xStringKey, yString);
                    //add "P".
                    g.drawString(playerStats.get(PlayerStatsKey.values()[i]) + "P", xStringValue, yString);
                    //just one-regular-new-line.
                    yString += 15;
                }
            }

        }
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    // GETTERS AND SETTERS

    public void setCurrentMenuSelection(MenuList menuSelection) {
        currentMenuSelection = menuSelection;
    }

} // **** end MainMenuState class ****