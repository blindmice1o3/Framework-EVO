package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.Assets;
import com.evo.gfx.FontGrabber;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TextboxState implements IState {

    public enum State { ENTER, LINE_IN_ANIMATION, WAIT_FOR_INPUT, PAGE_OUT_ANIMATION, EXIT; }

    Handler handler;

    private State currentState;

    // MESSAGE
    private String text;
    private ArrayList<String> lines;    //each element is a line-length-chunk of text.

    private int widthLetter, heightLetter; //size of each letter.
    private int xOffset;

    //Text Area (panel and border)
    private TextArea textArea;




    //Lines (firstLine and secondLine)
    private String firstLine;
    private String secondLine;
    //Lines - coordinates of line.
    private int xFirstLine, yFirstLine, widthFirstLine, heightFirstLine;
    private int xSecondLine, ySecondLine, widthSecondLine, heightSecondLine;
    //Lines - coordinates of type-in effect rectangle.
    private int xLine1TypeInFX, yLine1TypeInFX, widthLine1TypeInFX, heightLine1TypeInFX;
    private int xLine2TypeInFX, yLine2TypeInFX, widthLine2TypeInFX, heightLine2TypeInFX;

    public TextboxState(Handler handler) {
        lines = new ArrayList<String>();

        this.handler = handler;
        currentState = null;

        widthLetter = 10;
        heightLetter = 10;
        xOffset = 20;

        textArea = new TextArea(xOffset);



        xFirstLine = textArea.getxFinal() + xOffset;
        yFirstLine = textArea.getyFinal() + xOffset;
        widthFirstLine = textArea.getWidthFinal() - (2*xOffset) -5; //-5 just to get a specific (tester) text to fit nicely.
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

        StringBuilder sb = new StringBuilder();
        String[] words = text.split(" ");
        int currentIndex = 0;


        //for (int i = 0; i < words.length; i++) {
        //    System.out.println(words[i]);
        //}

        while (currentIndex < words.length) {
            //word will fit on this line.
            if ((sb.toString().length() + words[currentIndex].length() + 1) <= numberOfLetterPerLine) { //+1 for SPACE added after.
                sb.append(words[currentIndex]).append(" ");
                currentIndex++;
            }
            //store the line-worth of text into the lines ArrayList<String>.
            else {
                lines.add(sb.toString());
                sb.delete(0, sb.length());
            }

            //store the last line (not words.length-1 because we add a space after).
            if (currentIndex == words.length) {
                lines.add(sb.toString());
            }
        }

        //for (String line : lines) {
        //    System.out.println(line);
        //}


        /*

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

        */

        //initialize firstLine (and possibly secondLine) using currentLine#Index with textAfterLayout (each element in
        //this String array is a portion of the entire-text-to-be-displayed that will fit on one line).
        firstLine = lines.get(currentLine1Index);
        if (currentLine2Index < lines.size()) {
            secondLine = lines.get(currentLine2Index);
        }
        //secondLine does not exist.
        else {
            secondLine = null;
        }
        /*
        firstLine = textAfterLayout[currentLine1Index];
        if (currentLine2Index < textAfterLayout.length) {
            secondLine = textAfterLayout[currentLine2Index];
        }
        //secondLine does not exist.
        else {
            secondLine = null;
        }
        */
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
                //textbox-background's EXPAND-IN effect.
                if (textArea.getxCurrent() > textArea.getxFinal()) {
                    textArea.setxCurrent(textArea.getxCurrent() - 5);
                } else {
                    textArea.setxCurrent(textArea.getxFinal()); //check to make sure does NOT exceed MAX DIMENSION.
                }
                if (textArea.getWidthCurrent() < textArea.getWidthFinal()) {
                    textArea.setWidthCurrent(textArea.getWidthCurrent() + (2 * 5));
                } else {
                    textArea.setWidthCurrent(textArea.getWidthFinal()); //check to make sure does NOT exceed MAX DIMENSION.
                }
                if (textArea.getHeightCurrent() < textArea.getHeightFinal()) {
                    textArea.setHeightCurrent(textArea.getHeightCurrent() + 3);
                } else {
                    textArea.setHeightCurrent(textArea.getHeightFinal()); //check to make sure does NOT exceed MAX DIMENSION.
                }

                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                //CHANGE TO NEXT TextboxState.State
                if ( (textArea.getxCurrent() == textArea.getxFinal()) &&
                        (textArea.getWidthCurrent() == textArea.getWidthFinal()) &&
                        (textArea.getHeightCurrent() == textArea.getHeightFinal()) ) {
                    changeCurrentState(State.LINE_IN_ANIMATION);
                }
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                break;
            case LINE_IN_ANIMATION:
                //TODO: implement animationfx of line being "typed-in".
                //int textSpeed = 2; //actual in-game textSpeed.
                int textSpeed = 10; //developer-mode textSpeed.
                //reveal the lines of text by shrinking the covering-rectangle-that's-the-same-color-as-textbox-background.
                if (xLine1TypeInFX < (xFirstLine + (firstLine.length() * widthLetter)) ) {
                    xLine1TypeInFX += textSpeed;
                    widthLine1TypeInFX -= textSpeed;
                }
                //TODO: sometimes there's only one line and we shouldn't wait for the revealing of the second line.
                else if ( (secondLine != null) && (xLine2TypeInFX < (xSecondLine + (secondLine.length() * widthLetter))) ) {
                    xLine2TypeInFX += textSpeed;
                    widthLine2TypeInFX -= textSpeed;
                }

                // @@@@@@@@@@@@@@@@@ ACTUALLY... just set currentState to State.WAIT_FOR_INPUT @@@@@@@@@@@@@@@@
                //ending-situation where secondLine doesn't exist.
                if ( (secondLine == null) && (xLine1TypeInFX >= (xFirstLine + (firstLine.length() * widthLetter))) ) {
                    //else if ( (secondLine == null) && (widthLine1TypeInFX <= 0) ) {
                    changeCurrentState(State.WAIT_FOR_INPUT);
                }
                //secondLine exist.
                else if ( (xLine1TypeInFX >= (xFirstLine + (firstLine.length() * widthLetter))) &&
                        (xLine2TypeInFX >= (xSecondLine + (secondLine.length() * widthLetter))) ) {
                    changeCurrentState(State.WAIT_FOR_INPUT);
                }

                break;
            case WAIT_FOR_INPUT:
                //CHECK IF THERE'S ANOTHER PAGE: so if, continue-indicator should blink on-and-off.
                if ( (currentLine1Index + 2) < lines.size() ) {
                    //////////////////////////
                    continueIndicatorTicker++;
                    //////////////////////////

                    //continueIndicatorTickerSpeed will determine when to alternate the on/off effect of what's rendered.
                    if (continueIndicatorTicker >= continueIndicatorTickerSpeed) {
                        //alternate the continueIndicator's blinking effect.
                        renderContinueIndicator = !renderContinueIndicator;
                        //resets the continueIndicatorTicker when its max is reached.
                        continueIndicatorTicker = 0;
                    }
                }

                //a-button (if there's another page: set firstLine/secondLine to their next String from the array of lines).
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    //IF THERE'S ANOTHER PAGE: increment the currentLine#Index and re-assign firstLine.
                    if ( (currentLine1Index + 2) < lines.size() ) {
                        System.out.println("TextboxState.tick(), switch.WAIT_FOR_INPUT: ArrayList<String> lines has size() == " + lines.size());

                        currentLine1Index = currentLine1Index + 2;
                        firstLine = lines.get(currentLine1Index);
                        //CHECK IF ANOTHER secondLine exist.
                        if ( (currentLine2Index + 2) < lines.size() ) {
                            currentLine2Index = currentLine2Index + 2;
                            secondLine = lines.get(currentLine2Index);
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
                        //if reached this line: firstLine's currentLine1Index+2 is too big (no more lines).
                        firstLine = null;
                        secondLine = null;

                        ////////////////////////////////////////////
                        changeCurrentState(State.PAGE_OUT_ANIMATION);
                        ////////////////////////////////////////////
                    }
                }

                break;
            case PAGE_OUT_ANIMATION:
                //TEXT_AREA SHRINKING EFFECT.
                if (textArea.getxCurrent() < textArea.getxInit()) {
                    textArea.setxCurrent(textArea.getxCurrent() + 5);
                }
                if (textArea.getWidthCurrent() > textArea.getWidthInit()) {
                    textArea.setWidthCurrent(textArea.getWidthCurrent() - (2 * 5));
                } else {
                    textArea.setWidthCurrent(0); //when width shrink pass its INITIAL DIMENSION, set it to 0.
                }
                if (textArea.getHeightCurrent() > textArea.getHeightInit()) {
                    textArea.setHeightCurrent(textArea.getHeightCurrent() - 3);
                } else {
                    textArea.setHeightCurrent(0); //when height shrink pass its INITIAL DIMENSION, set it to 0.
                }

                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                //CHANGE TO NEXT TextboxState.State.EXIT
                if ( (textArea.getWidthCurrent() == 0) && (textArea.getHeightCurrent() == 0) ) {
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
        g.fillRect(textArea.getxCurrent(), textArea.getyCurrent(), textArea.getWidthCurrent(), textArea.getHeightCurrent());
        //BORDER
        g.setColor(Color.YELLOW);
        g.drawRect(textArea.getxCurrent(), textArea.getyCurrent(), textArea.getWidthCurrent(), textArea.getHeightCurrent());

        switch (currentState) {
            case ENTER:
                //taken care of by TEXT_AREA and BORDER (outside of this switch-construct).
                //it's outside so it can be redrawn for all cases.

                break;
            case LINE_IN_ANIMATION:
                //render: firstLine
                FontGrabber.renderString(g, firstLine, xFirstLine, yFirstLine, widthLetter, heightLetter);
                //render: secondLine
                if (secondLine != null) {
                    FontGrabber.renderString(g, secondLine, xSecondLine, ySecondLine, widthLetter, heightLetter);
                }

                //TYPE-IN EFFECT (rectangles that covers firstLine and secondLine, and reveals them by shrinking)
                //g.setColor(Color.BLUE);
                g.setColor(Color.BLACK);
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
                                textArea.getxFinal() + textArea.getWidthFinal() - (2 * widthLetter),
                                textArea.getyFinal() + textArea.getHeightFinal() - (2 * heightLetter),
                                widthLetter,
                                heightLetter,
                                null);
                    }
                    //blinking off-state
                    else {
                        g.setColor(Color.BLUE);
                        g.fillRect(textArea.getxFinal() + textArea.getWidthFinal() - (2 * widthLetter),
                                textArea.getyFinal() + textArea.getHeightFinal() - (2 * heightLetter),
                                widthLetter,
                                heightLetter);
                    }
                }
                //SECOND_LINE DOES not EXIST.
                else if ( xLine1TypeInFX >= (xFirstLine + (firstLine.length() * widthLetter)) ) {
                    //NON-blinking continue-indicator (the non-blinking version implies this is the last page).
                    g.drawImage(Assets.pokeballToken,
                            textArea.getxFinal() + textArea.getWidthFinal() - (2 * widthLetter),
                            textArea.getyFinal() + textArea.getHeightFinal() - (2 * heightLetter),
                            widthLetter,
                            heightLetter,
                            null);
                }

                break;
            case PAGE_OUT_ANIMATION:
                //taken care of by TEXT_AREA and BORDER (outside of this switch-construct).
                //it's outside so it can be redrawn for all cases.

                break;
            case EXIT:
                //in State.EXIT, TextboxState.tick() will pop itself off StateManager.stateStack.
                //which calls TextboxState.exit() (the exit() METHOD is currently empty).

                break;
            default:
                System.out.println("TextboxState.render(Graphics), switch-construct's default.");
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

        //@@@@@@@@@@@@
        lines.clear();
        //@@@@@@@@@@@@

        //RESET textbox-background to its initial dimension.
        textArea.setxCurrent(textArea.getxInit());
        textArea.setyCurrent(textArea.getyInit());
        textArea.setWidthCurrent(textArea.getWidthInit());
        textArea.setHeightCurrent(textArea.getHeightInit());

        //RESET type-in-fx rectangle (for firstLine) to cover the entire line.
        xLine1TypeInFX = xFirstLine;
        yLine1TypeInFX = yFirstLine;
        widthLine1TypeInFX = widthFirstLine;
        heightLine1TypeInFX = heightFirstLine;
        //RESET type-in-fx rectangle (for secondLine) to cover the entire line.
        xLine2TypeInFX = xSecondLine;
        yLine2TypeInFX = ySecondLine;
        widthLine2TypeInFX = widthSecondLine;
        heightLine2TypeInFX = heightSecondLine;

        //RESET continue-indicator variables.
        renderContinueIndicator = false;

        //RESET currentLine#Index.
        currentLine1Index = 0;
        currentLine2Index = 1;

        //@@@IF String WAS PASSED IN, split into line-length chunks and store those chunks in an ArrayList<String> lines.
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ INNER-CLASSES @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class TextArea {

        private Rectangle textAreaInitialCoordinates;
        private Rectangle textAreaFinalCoordinates;
        private Rectangle textAreaCurrentCoordinates;
        private int xInit, yInit, widthInit, heightInit;
        private int xFinal, yFinal, widthFinal, heightFinal;
        private int xCurrent, yCurrent, widthCurrent,heightCurrent;

        public TextArea(int xOffset) {
            initTextAreaInitialCoordinates(xOffset);
            initTextAreaFinalCoordinates(xOffset);
            initTextAreaCurrentCoordinates();
        } // **** end TextArea(int) constructor ****

        private void initTextAreaInitialCoordinates(int xOffset) {
            xInit = (handler.panelWidth/2) - xOffset;
            yInit = (handler.panelHeight/2) + 20;
            widthInit = 2 * xOffset;
            heightInit = xOffset;

            textAreaInitialCoordinates = new Rectangle(xInit, yInit, widthInit, heightInit);
        }

        private void initTextAreaFinalCoordinates(int xOffset) {
            xFinal = 30;
            yFinal = yInit;
            widthFinal = handler.panelWidth - (2 * xFinal);
            heightFinal = 9 * xOffset;

            textAreaFinalCoordinates = new Rectangle(xFinal, yFinal, widthFinal, heightFinal);
        }

        private void initTextAreaCurrentCoordinates() {
            xCurrent = xInit;
            yCurrent = yInit;
            widthCurrent = widthInit;
            heightCurrent = heightInit;

            textAreaCurrentCoordinates = new Rectangle(xCurrent, yCurrent, widthCurrent, heightCurrent);
        }

        // GETTERS AND SETTERS

        public void setxCurrent(int xCurrent) {
            this.xCurrent = xCurrent;
        }

        public void setyCurrent(int yCurrent) {
            this.yCurrent = yCurrent;
        }

        public void setWidthCurrent(int widthCurrent) {
            this.widthCurrent = widthCurrent;
        }

        public void setHeightCurrent(int heightCurrent) {
            this.heightCurrent = heightCurrent;
        }

        public int getxInit() {
            return xInit;
        }

        public int getyInit() {
            return yInit;
        }

        public int getWidthInit() {
            return widthInit;
        }

        public int getHeightInit() {
            return heightInit;
        }

        public int getxFinal() {
            return xFinal;
        }

        public int getyFinal() {
            return yFinal;
        }

        public int getWidthFinal() {
            return widthFinal;
        }

        public int getHeightFinal() {
            return heightFinal;
        }

        public int getxCurrent() {
            return xCurrent;
        }

        public int getyCurrent() {
            return yCurrent;
        }

        public int getWidthCurrent() {
            return widthCurrent;
        }

        public int getHeightCurrent() {
            return heightCurrent;
        }

    } // **** end TextArea inner-class ****

}