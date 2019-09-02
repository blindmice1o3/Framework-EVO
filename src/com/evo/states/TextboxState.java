package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.Assets;
import com.evo.gfx.FontGrabber;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TextboxState implements IState {

    public enum State { ENTER, LINE_IN_ANIMATION, WAIT_FOR_INPUT, PAGE_OUT_ANIMATION, EXIT; }

    Handler handler;

    private State currentState;

    //MESSAGE
    private String text;
    private String firstLine;
    private String secondLine;
    private String[] textAfterLayout;

    //Frame (and border)
    private int xOffset;
    private int xInit, yInit;
    private int xFinal, yFinal;
    private int widthInit, heightInit;
    private int widthFinal, heightFinal;
    private int xCurrent, yCurrent, widthCurrent,heightCurrent;

    //Text Area
    private int widthLetter, heightLetter; //size of each letter.
    private int xFirstLine, yFirstLine, widthFirstLine, heightFirstLine;
    private int xSecondLine, ySecondLine, widthSecondLine, heightSecondLine;
    //Text Area - type-in effect
    private int xLine1TypeInFX, yLine1TypeInFX, widthLine1TypeInFX, heightLine1TypeInFX;
    private int xLine2TypeInFX, yLine2TypeInFX, widthLine2TypeInFX, heightLine2TypeInFX;

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
        heightFirstLine = heightLetter;
        //BELOW LOGIC is not correct.
        //heightFirstLine = (heightFinal - (2*(xOffset/2)) - 10) / 2;

        xSecondLine = xFirstLine;
        ySecondLine = yFirstLine + 20; //+20 for space between firstLine and secondLine.
        widthSecondLine = widthFirstLine;
        heightSecondLine = heightFirstLine;

        xLine1TypeInFX = xFirstLine;
        yLine1TypeInFX = yFirstLine;
        widthLine1TypeInFX = widthFirstLine;
        heightLine1TypeInFX = heightFirstLine;

        xLine2TypeInFX = xSecondLine;
        yLine2TypeInFX = ySecondLine;
        widthLine2TypeInFX = widthSecondLine;
        heightLine2TypeInFX = heightSecondLine;
    } // **** end TextboxState(Handler) constructor ****


    private int currentLine1Index = 0;
    private int currentLine2Index = 1;
    private void initTextLayout() {
        int numberOfLetterPerLine = widthFirstLine / widthLetter;
        System.out.println("NUMBER OF LETTERS PER LINE: " + numberOfLetterPerLine);

        //TODO: if the entire-text-to-be-displayed is less than one line, we'll end up with ZERO numberOfPages!!!
        //if it's at-least one-line worth of text, we'll be okay.
        int numberOfLineToDisplay = (text.length() / numberOfLetterPerLine) + 1; //+1 possible lobed-off.
        System.out.println("NUMBER OF LINES TO DISPLAY: " + numberOfLineToDisplay);

        ////////////////////////////////////////////////////
        textAfterLayout = new String[numberOfLineToDisplay];
        ////////////////////////////////////////////////////

        int indexText = 0;
        for (int i = 0; i < numberOfLineToDisplay; i++) {
            if (indexText+numberOfLetterPerLine < text.length()) {
                textAfterLayout[i] = text.substring(indexText, indexText + numberOfLetterPerLine);
                indexText = indexText + numberOfLetterPerLine;
            } else {
                textAfterLayout[i] = text.substring(indexText);
            }
        }

        //initialize firstLine (and possibly secondLine) using currentLine#Index with textAfterLayout (each element in
        //this String array is a portion of the entire-text-to-be-displayed that will fit on one line).
        firstLine = textAfterLayout[currentLine1Index];
        if (currentLine2Index < textAfterLayout.length) {
            secondLine = textAfterLayout[currentLine2Index];
        }
        //secondLine does not exist.
        else {
            secondLine = null;
        }
    }

    private int continueIndicatorTicker = 0;
    private int continueIndicatorTickerSpeed = 30;
    private boolean renderContinueIndicator = false;
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
                int textSpeed = 2; //actual in-game textSpeed.
                //int textSpeed = 10; //developer-mode textSpeed.
                //reveal the lines of text by shrinking the covering-rectangle-that's-the-same-color-as-textbox-background.
                if (widthLine1TypeInFX > 0) {
                    xLine1TypeInFX += textSpeed;
                    widthLine1TypeInFX -= textSpeed;
                }
                //TODO: sometimes there's only one line and we shouldn't wait for the revealing of the second line.
                if ( (widthLine2TypeInFX > 0) && (widthLine1TypeInFX <= 0) && (secondLine != null) ) {
                    xLine2TypeInFX += textSpeed;
                    widthLine2TypeInFX -= textSpeed;
                }

                // @@@@@@@@@@@@@@@@@ ACTUALLY... just set currentState to State.WAIT_FOR_INPUT @@@@@@@@@@@@@@@@
                if ( (widthLine2TypeInFX <= 0) && (widthLine1TypeInFX <= 0) ) {
                    changeCurrentState(State.WAIT_FOR_INPUT);
                }
                //ending-situation where secondLine doesn't exist.
                else if ( (secondLine == null) && (xLine1TypeInFX >= (firstLine.length()*widthLetter)) ) {
                //else if ( (secondLine == null) && (widthLine1TypeInFX <= 0) ) {
                    changeCurrentState(State.WAIT_FOR_INPUT);
                }

                break;
            case WAIT_FOR_INPUT:
                //CHECK IF THERE'S ANOTHER PAGE: so if, continue-indicator should blink on-and-off.
                if (currentLine1Index+2 < textAfterLayout.length) {
                    //////////////////////////
                    continueIndicatorTicker++;
                    //////////////////////////

                    //continueIndicatorTickerSpeed will determine when to alternate the on/off effect of what's rendered.
                    if (continueIndicatorTicker >= continueIndicatorTickerSpeed) {
                        //resets the continueIndicatorTicker when its max is reached.
                        continueIndicatorTicker = 0;
                        //alternate the continueIndicator's blinking effect.
                        renderContinueIndicator = !renderContinueIndicator;
                    }
                }

                //a-button (if there's another page: set firstLine/secondLine to their next String from the array of lines).
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    //IF THERE'S ANOTHER PAGE: increment the currentLine#Index and re-assign firstLine.
                    if (currentLine1Index+2 < textAfterLayout.length) {
                        currentLine1Index = currentLine1Index + 2;
                        firstLine = textAfterLayout[currentLine1Index];
                        //CHECK IF ANOTHER secondLine exist.
                        if (currentLine2Index+2 < textAfterLayout.length) {
                            currentLine2Index = currentLine2Index + 2;
                            secondLine = textAfterLayout[currentLine2Index];
                        } else {
                            secondLine = null;
                        }

                        //RESET values related to textbox's type-in effect.
                        xLine1TypeInFX = xFirstLine;
                        yLine1TypeInFX = yFirstLine;
                        widthLine1TypeInFX = widthFirstLine;
                        heightLine1TypeInFX = heightFirstLine;

                        xLine2TypeInFX = xSecondLine;
                        yLine2TypeInFX = ySecondLine;
                        widthLine2TypeInFX = widthSecondLine;
                        heightLine2TypeInFX = heightSecondLine;

                        renderContinueIndicator = false;

                        ////////////////////////////////////////////
                        changeCurrentState(State.LINE_IN_ANIMATION);
                        ////////////////////////////////////////////
                    } else {
                        ////////////////////////////////////////////
                        changeCurrentState(State.PAGE_OUT_ANIMATION);
                        ////////////////////////////////////////////
                    }
                }

                break;
            case PAGE_OUT_ANIMATION:
                //TEXT_AREA SHRINKING EFFECT.
                if (xCurrent < xInit) {
                    xCurrent = xCurrent + 5;
                }
                if (widthCurrent > widthInit) {
                    widthCurrent = widthCurrent - (2 * 5);
                }
                if (heightCurrent > heightInit) {
                    heightCurrent = heightCurrent - 3;
                }

                //when width and height shrink pass their INITIAL DIMENSIONS, set them to 0.
                if (widthCurrent < widthInit) {
                    widthCurrent = 0;
                }
                if (heightCurrent < heightInit) {
                    heightCurrent = 0;
                }

                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                //CHANGE TO NEXT TextboxState.State.EXIT
                if ( (widthCurrent == 0) && (heightCurrent == 0) ) {
                    changeCurrentState(State.EXIT);
                }
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                break;
            case EXIT:
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                //pop.
                System.out.println("TextboxState.State.EXIT");
                handler.getStateManager().popIState();
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

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
    }



    @Override
    public void render(Graphics g) {
        //repaint the render(Graphics) of the IState that is just below the top of the stack.
        handler.getStateManager().getStatesStack().get(handler.getStateManager().getStatesStack().size()-2).render(g);

        //TEXT_AREA
        g.setColor(Color.BLUE);
        g.fillRect(xCurrent, yCurrent, widthCurrent, heightCurrent);
        //BORDER
        g.setColor(Color.YELLOW);
        g.drawRect(xCurrent, yCurrent, widthCurrent, heightCurrent);

        switch (currentState) {
            case ENTER:

                break;
            case LINE_IN_ANIMATION:
                //FIRST_LINE
                FontGrabber.renderString(g, firstLine, xFirstLine, yFirstLine, widthLetter, heightLetter);
                //SECOND_LINE
                if (secondLine != null) {
                    FontGrabber.renderString(g, secondLine, xSecondLine, ySecondLine, widthLetter, heightLetter);
                }

                //type-in effect.
                //TODO: TESTING WITH BLACK INSTEAD OF BLUE
                g.setColor(Color.BLUE);
                //g.setColor(Color.BLACK);
                g.fillRect(xLine1TypeInFX, yLine1TypeInFX, widthLine1TypeInFX, heightLine1TypeInFX);
                g.fillRect(xLine2TypeInFX, yLine2TypeInFX, widthLine2TypeInFX, heightLine2TypeInFX);

                break;
            case WAIT_FOR_INPUT:
                //render: firstLine
                FontGrabber.renderString(g, firstLine, xFirstLine, yFirstLine, widthLetter, heightLetter);

                //SECOND_LINE EXIST.
                if (secondLine != null) {
                    //render: secondLine
                    FontGrabber.renderString(g, secondLine, xSecondLine, ySecondLine, widthLetter, heightLetter);

                    // @@@@@ RENDER BLINKING continue-indicator @@@@@
                    //blinking on-state
                    if (renderContinueIndicator) {
                        g.drawImage(Assets.pokeballToken,
                                xFinal + widthFinal - (2 * widthLetter),
                                yFinal + heightFinal - (2 * heightLetter),
                                widthLetter,
                                heightLetter,
                                null);
                    }
                    //blinking off-state
                    else {
                        g.setColor(Color.BLUE);
                        g.fillRect(xFinal + widthFinal - (2 * widthLetter),
                                yFinal + heightFinal - (2 * heightLetter),
                                widthLetter,
                                heightLetter);
                    }
                }
                //SECOND_LINE DOES not EXIST.
                else if ( (secondLine == null) && (xLine1TypeInFX >= (firstLine.length()*widthLetter)) ) {
                    //NON-blinking continue-indicator (the non-blinking version implies this is the last page).
                    g.drawImage(Assets.pokeballToken,
                            xFinal + widthFinal - (2 * widthLetter),
                            yFinal + heightFinal - (2 * heightLetter),
                            widthLetter,
                            heightLetter,
                            null);
                }

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
        ///////////////////////////
        currentState = State.ENTER;
        ///////////////////////////

        //RESET values related to textbox's initial dimension.
        xCurrent = xInit;
        yCurrent = yInit;
        widthCurrent = widthInit;
        heightCurrent = heightInit;

        //RESET values related to textbox's type-in effect.
        xLine1TypeInFX = xFirstLine;
        yLine1TypeInFX = yFirstLine;
        widthLine1TypeInFX = widthFirstLine;
        heightLine1TypeInFX = heightFirstLine;

        xLine2TypeInFX = xSecondLine;
        yLine2TypeInFX = ySecondLine;
        widthLine2TypeInFX = widthSecondLine;
        heightLine2TypeInFX = heightSecondLine;

        //RESET continue-indicator variables.
        renderContinueIndicator = false;

        //RESET currentLine#Index.
        currentLine1Index = 0;
        currentLine2Index = 1;

        if (args != null) {
            if (args[0] instanceof String) {
                text = (String)args[0];
                /////////////////
                initTextLayout();
                /////////////////
            }
        }

    }

    @Override
    public void exit() {

    }

}