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
    private int numberOfPages;
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
    //CONTINUE INDICATOR
    private boolean continueIndicator;

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

        xLine1TypeInFX = xFirstLine;
        yLine1TypeInFX = yFirstLine;
        widthLine1TypeInFX = widthFirstLine;
        heightLine1TypeInFX = heightFirstLine;

        xLine2TypeInFX = xSecondLine;
        yLine2TypeInFX = ySecondLine;
        widthLine2TypeInFX = widthSecondLine;
        heightLine2TypeInFX = heightSecondLine;

        continueIndicator = false;
    } // **** end TextboxState(Handler) constructor ****


    private int currentLine1Index = 0;
    private int currentLine2Index = 1;
    private void initTextLayout() {
        int numberOfLetterPerLine = widthFirstLine / widthLetter;
        System.out.println("NUMBER OF LETTERS PER LINE: " + numberOfLetterPerLine);

        //int lengthOfTextToDisplay = text.length() * widthLetter;
        //System.out.println("LENGTH OF TEXT TO DISPLAY: " + lengthOfTextToDisplay);

        //TODO: if the entire-text-to-be-displayed is less than one line, we'll end up with ZERO numberOfPages!!!
        //if it's at-least one-line worth of text, we'll be okay.
        //TODO: should not be lengthOfTextToDisplay... text.length().
        int numberOfLineToDisplay = (text.length() / numberOfLetterPerLine) + 1; //+1 possible lobed-off.
        numberOfPages = numberOfLineToDisplay / 2; //2 lines per page.

        System.out.println("NUMBER OF LINES TO DISPLAY: " + numberOfLineToDisplay);

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

        //initialize firstLine (and possibly secondLine) using currentLine#Index with textAfterLayout (each element in
        //this String array is a portion of the entire-text-to-be-displayed that will fit on one line).
        firstLine = textAfterLayout[currentLine1Index];
        if (currentLine2Index < textAfterLayout.length) {
            secondLine = textAfterLayout[currentLine2Index];
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
                int textSpeed = 2;
                //reveal the lines of text by shrinking the covering-rectangle-that's-the-same-color-as-textbox-background.
                if (widthLine1TypeInFX > 0) {
                    xLine1TypeInFX += textSpeed;
                    widthLine1TypeInFX -= textSpeed;
                }
                //TODO: sometimes there's only one line and we shouldn't wait for the revealing of the second line.
                if ( (widthLine2TypeInFX > 0) && (widthLine1TypeInFX <= 0) ) {
                    xLine2TypeInFX += textSpeed;
                    widthLine2TypeInFX -= textSpeed;
                }

                // @@@@@@@@@@@@@@@@@ ACTUALLY... just set currentState to State.WAIT_FOR_INPUT @@@@@@@@@@@@@@@@
                if ( (widthLine2TypeInFX <= 0) && (widthLine1TypeInFX <= 0) ) {
                    changeCurrentState(State.WAIT_FOR_INPUT);
                }

                break;
            case WAIT_FOR_INPUT:
                //a-button
                //If another line exist, increment the currentLine#Index.
                if (currentLine1Index+2 < textAfterLayout.length) {
                    // !!!!!!!!!!!!! if we're here, THERE'S ANOTHER PAGE !!!!!!!!!!!!!
                    //blinking continue indicator.
                    continueIndicator = true;
                }

                if (continueIndicator) {
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

                //trigger CONTINUE-INDICATOR after BOTH lines are done revealing their text.
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    //TODO: IF more pages exist, changeCurrentState(PAGE_OUT_ANIMATION). ELSE, changeCurrentState(EXIT).
                    if (continueIndicator) {
                        currentLine1Index = currentLine1Index + 2;
                        firstLine = textAfterLayout[currentLine1Index];
                        if (currentLine2Index+2 < textAfterLayout.length) {
                            currentLine2Index = currentLine2Index + 2;
                            secondLine = textAfterLayout[currentLine2Index];
                        } else {
                            secondLine = "";
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

                        continueIndicator = false;
                        changeCurrentState(State.LINE_IN_ANIMATION);
                        //NEXT PAGE OF 2 LINES.
                        //set currentState to State.LINE_IN_ANIMATION.
                    } else {
                        changeCurrentState(State.PAGE_OUT_ANIMATION);
                        //set currentState to State.PAGE_OUT_ANIMATION.
                    }
                }

                break;
            case PAGE_OUT_ANIMATION:
                //TODO: clear the text area of the textbox, changeCurrentState(LINE_IN_ANIMATION).
                System.out.println("State.PAGE_OUT_ANIMATION");
                /*
                //RESET values related to textbox's type-in effect.
                xLine1TypeInFX = xFirstLine;
                yLine1TypeInFX = yFirstLine;
                widthLine1TypeInFX = widthFirstLine;
                heightLine1TypeInFX = heightFirstLine;

                xLine2TypeInFX = xSecondLine;
                yLine2TypeInFX = ySecondLine;
                widthLine2TypeInFX = widthSecondLine;
                heightLine2TypeInFX = heightSecondLine;
                */
                //SET firstLine and secondLine to the next 2 String element from String[].
                //need to track the current index of the String[] or use numberOfPages, initialized from initTextLayout().

                break;
            case EXIT:
                //TODO: implement animationfx of textbox's exit transition.
                //pop.

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
                //IF the continue indicator was rendered from the previous page, cover it and turn it off.
                if (renderContinueIndicator) {
                    g.setColor(Color.BLUE);
                    g.fillRect( xFinal + widthFinal - (2*widthLetter),
                            yFinal + heightFinal - (2*heightLetter),
                            widthLetter,
                            heightLetter );
                    renderContinueIndicator = false;
                }

                //FIRST_LINE
                FontGrabber.renderString(g, firstLine, xFirstLine, yFirstLine, widthLetter, heightLetter);
                //SECOND_LINE
                if (secondLine != null) {
                    FontGrabber.renderString(g, secondLine, xSecondLine, ySecondLine, widthLetter, heightLetter);
                }

                //type-in effect.
                g.setColor(Color.BLUE);
                g.fillRect(xLine1TypeInFX, yLine1TypeInFX, widthLine1TypeInFX, heightLine1TypeInFX);
                g.fillRect(xLine2TypeInFX, yLine2TypeInFX, widthLine2TypeInFX, heightLine2TypeInFX);

                break;
            case WAIT_FOR_INPUT:
                if (renderContinueIndicator) {
                    g.drawImage( Assets.pokeballToken,
                            xFinal + widthFinal - (2*widthLetter),
                            yFinal + heightFinal - (2*heightLetter),
                            widthLetter,
                            heightLetter,
                            null );
                } else {
                    g.setColor(Color.BLUE);
                    g.fillRect( xFinal + widthFinal - (2*widthLetter),
                            yFinal + heightFinal - (2*heightLetter),
                            widthLetter,
                            heightLetter );
                }

                break;
            case PAGE_OUT_ANIMATION:
                //IF the continue indicator was rendered from the previous page, cover it and turn it off.
                if (renderContinueIndicator) {
                    g.setColor(Color.BLUE);
                    g.fillRect( xFinal + widthFinal - (2*widthLetter),
                            yFinal + heightFinal - (2*heightLetter),
                            widthLetter,
                            heightLetter );
                    renderContinueIndicator = false;
                }

                g.setColor(Color.BLUE);
                g.fillRect(xFirstLine, yFirstLine, widthFirstLine, heightFirstLine);
                g.setColor(Color.YELLOW);
                g.drawString("State.PAGE_OUT_ANIMATION", xFirstLine, yFirstLine);

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
        continueIndicator = false;
        renderContinueIndicator = false;

        //RESET currentLine#Index.
        currentLine1Index = 0;
        currentLine2Index = 1;

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