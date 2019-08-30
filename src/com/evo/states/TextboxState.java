package com.evo.states;

import com.evo.Handler;
import com.evo.states.IState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TextboxState implements IState {

    public enum State { ENTER, LINE_IN_ANIMATION, WAIT_FOR_INPUT, PAGE_OUT_ANIMATION, EXIT; }

    Handler handler;

    private State currentState;

    public TextboxState(Handler handler) {
        this.handler = handler;
        currentState = null;
    }

    @Override
    public void tick() {


        switch (currentState) {
            case ENTER:
                //TODO: implement animationfx of textbox expanding (maybe have a bounce/over-shoot expansion size then small reduction to reach intended size).

                break;
            case LINE_IN_ANIMATION:
                //TODO: implement animationfx of line being "typed-in".

                break;
            case WAIT_FOR_INPUT:
                getInput(); //NO INPUT checks for other state???

                break;
            case PAGE_OUT_ANIMATION:
                //TODO: clear the text area of the textbox, changeCurrentState(LINE_IN_ANIMATION).

                break;
            case EXIT:
                //TODO: implement animationfx of textbox's exit transition.

                break;
            default:
                break;
        }
    }

    @Override
    public void getInput() {
        //start (will enter PauseState).
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
            handler.getStateManager().pushIState(StateManager.State.PAUSE, null);
        }
        //TODO: IF more lines exist, changeCurrentState(PAGE_OUT_ANIMATION). ELSE, changeCurrentState(EXIT).

        /*
        switch (currentState) {
            case ENTER:

                break;
            case LINE_IN_ANIMATION:

                break;

            case WAIT_FOR_INPUT:

                break;
            case PAGE_OUT_ANIMATION:

                break;
            case EXIT:

                break;
            default:
                break;
        }
        */
    }

    @Override
    public void render(Graphics g) {
        switch (currentState) {
            case ENTER:

                break;
            case LINE_IN_ANIMATION:

                break;

            case WAIT_FOR_INPUT:

                break;
            case PAGE_OUT_ANIMATION:

                break;
            case EXIT:

                break;
            default:
                break;
        }
    }

    private void changeCurrentState(State nextState) {
        exit();
        currentState = nextState;
        enter(null);
    }

    @Override
    public void enter(Object[] args) {
        if (currentState == null) {
            currentState = State.ENTER;
        }

        switch (currentState) {
            case ENTER:

                break;
            case LINE_IN_ANIMATION:

                break;

            case WAIT_FOR_INPUT:

                break;
            case PAGE_OUT_ANIMATION:

                break;
            case EXIT:

                break;
            default:
                break;
        }
    }

    @Override
    public void exit() {
        switch (currentState) {
            case ENTER:

                break;
            case LINE_IN_ANIMATION:

                break;

            case WAIT_FOR_INPUT:

                break;
            case PAGE_OUT_ANIMATION:

                break;
            case EXIT:

                break;
            default:
                break;
        }
    }

}