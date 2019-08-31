package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.FontGrabber;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TextboxState implements IState {

    public enum State { ENTER, LINE_IN_ANIMATION, WAIT_FOR_INPUT, PAGE_OUT_ANIMATION, EXIT; }

    Handler handler;

    private State currentState;
    private String text;

    private String firstLine;
    private String secondLine;
    private int numberOfPages;
    private String[] textAfterLayout;

    private int widthLetter, heightLetter;
    private int xFirstLine, yFirstLine, widthFirstLine, heightFirstLine;
    private int xSecondLine, ySecondLine, widthSecondLine, heightSecondLine;

    private int xOffset;
    private int xInit, yInit;
    private int xFinal, yFinal;
    private int widthInit, heightInit;
    private int widthFinal, heightFinal;
    private int xCurrent, yCurrent, widthCurrent,heightCurrent;

    public TextboxState(Handler handler) {
        this.handler = handler;
        currentState = null;

        widthLetter = 10;
        heightLetter = 10;
        xOffset = 20;

        xInit = (handler.panelWidth/2) - xOffset;
        yInit = (handler.panelHeight/2) + 20;
        widthInit = 2 * xOffset;
        heightInit = xOffset;

        xFinal = 30;
        yFinal = yInit;
        widthFinal = handler.panelWidth - (2 * xFinal);
        heightFinal = 9 * xOffset;

        xCurrent = xInit;
        yCurrent = yInit;
        widthCurrent = widthInit;
        heightCurrent = heightInit;

        xFirstLine = xFinal + xOffset;
        yFirstLine = yFinal + xOffset;
        widthFirstLine = widthFinal - (2*xOffset) -5; //-5 just to get a specific (tester) text to fit nicely.
        heightFirstLine = (heightFinal - (2*(xOffset/2)) - 10) / 2;

        xSecondLine = xFirstLine;
        ySecondLine = yFirstLine + 20; //+20 for space between firstLine and secondLine.
        widthSecondLine = widthFirstLine;
        heightSecondLine = heightFirstLine;
    } // **** end TextboxState(Handler) constructor ****

    private void initTextLayout() {
        int numberOfLetterPerLine = widthFirstLine / widthLetter;
        int lengthOfTextToDisplay = text.length() * widthLetter;
        int numberOfLineToDisplay = (lengthOfTextToDisplay / numberOfLetterPerLine) + 1; //+1 possible lobed-off.
        numberOfPages = numberOfLineToDisplay / 2; //2 lines per page.

        textAfterLayout = new String[numberOfLineToDisplay];

        int indexText = 0;
        for (int i = 0; i < numberOfLineToDisplay; i++) {
            if (indexText+numberOfLetterPerLine < text.length()) {
                textAfterLayout[i] = text.substring(indexText, indexText + numberOfLetterPerLine);
                indexText = indexText + numberOfLetterPerLine;
            } else {
                textAfterLayout[i] = text.substring(indexText);
            }
        }

        firstLine = textAfterLayout[0];
        secondLine = textAfterLayout[1];
    }

    @Override
    public void tick() {
        getInput();

        switch (currentState) {
            case ENTER:
                //TODO: implement animationfx of textbox expanding (maybe have a bounce/over-shoot expansion size then small reduction to reach intended size).
                //TEXT_AREA EXPANDING EFFECT.
                if (xCurrent > xFinal) {
                    xCurrent = xCurrent - 5;
                }
                if (widthCurrent < widthFinal) {
                    widthCurrent = widthCurrent + (2 * 5);
                }
                if (heightCurrent < heightFinal) {
                    heightCurrent = heightCurrent + 3;
                }

                //CHECK TO MAKE SURE doesn't exceed MAX DIMENSION.
                if (xCurrent < xFinal) {
                    xCurrent = xFinal;
                }
                if (widthCurrent > widthFinal) {
                    widthCurrent = widthFinal;
                }
                if (heightCurrent > heightFinal) {
                    heightCurrent = heightFinal;
                }

                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                //CHANGE TO NEXT TextboxState.State
                if ( (xCurrent == xFinal) && (widthCurrent == widthFinal) && (heightCurrent == heightFinal) ) {
                    changeCurrentState(State.LINE_IN_ANIMATION);
                }
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                break;
            case LINE_IN_ANIMATION:
                //TODO: implement animationfx of line being "typed-in".


                break;
            case WAIT_FOR_INPUT:
                //a-button
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    //TODO: IF more lines exist, changeCurrentState(PAGE_OUT_ANIMATION). ELSE, changeCurrentState(EXIT).
                }

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
        //b-button or temporary textbox-button (KeyEvent.VK_SLASH) (will pop TextboxState off stack).
        else if ( (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) ||
                (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SLASH)) ) {
            handler.getStateManager().popIState();
        }

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
                //repaint the render(Graphics) of the IState that is just below the top of the stack.
                handler.getStateManager().getStatesStack().get(handler.getStateManager().getStatesStack().size()-2).render(g);

                //TEXT_AREA
                g.setColor(Color.BLUE);
                g.fillRect(xCurrent, yCurrent, widthCurrent, heightCurrent);

                //BORDER
                g.setColor(Color.YELLOW);
                g.drawRect(xCurrent, yCurrent, widthCurrent, heightCurrent);

                break;
            case LINE_IN_ANIMATION:
                FontGrabber.renderString(g, firstLine, xFirstLine, yFirstLine, widthLetter, heightLetter);
                if (secondLine != null) {
                    FontGrabber.renderString(g, secondLine, xSecondLine, ySecondLine, widthLetter, heightLetter);
                }

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
        currentState = nextState;
    }

    @Override
    public void enter(Object[] args) {
        /* if (currentState == null) {
            currentState = State.ENTER;
        } */
        //RESET values related to textbox's initial dimension.
        xCurrent = xInit;
        yCurrent = yInit;
        widthCurrent = widthInit;
        heightCurrent = heightInit;

        //it would've been in LINE_IN_ANIMATION if we don't reset currentState.
        currentState = State.ENTER;

        if (args != null) {
            if (args[0] instanceof String) {
                text = (String)args[0];
                /////////////////
                initTextLayout();
                /////////////////
            }
        }

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
    public void exit() {

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

}