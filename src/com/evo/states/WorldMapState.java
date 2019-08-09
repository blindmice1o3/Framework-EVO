package com.evo.states;

import com.evo.Handler;
import com.evo.entities.moveable.OverWorldCursor;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class WorldMapState implements IState {

    public final static int TILE_WIDTH = 16;
    public final static int TILE_HEIGHT = 16;

    private Handler handler;
    public final double X_CONVERSION_FACTOR;
    public final double Y_CONVERSION_FACTOR;

    private Object[] stages;
    private OverWorldCursor overWorldCursor;
    private int index;

    public WorldMapState(Handler handler) {
        this.handler = handler;

        overWorldCursor = new OverWorldCursor(handler, Assets.upOverworld0, 0, 0);
        index = 0;
        stages = new Object[7];

        X_CONVERSION_FACTOR = ((double)handler.panelWidth / Assets.chapter1WorldMap.getWidth());
        Y_CONVERSION_FACTOR = ((double)handler.panelHeight / Assets.chapter1WorldMap.getHeight());
        System.out.println("WorldMapState.X_CONVERSION_FACTOR: " + X_CONVERSION_FACTOR);
        System.out.println("WorldMapState.Y_CONVERSION_FACTOR: " + Y_CONVERSION_FACTOR);
    }

    @Override
    public void tick() {
        getInput();

        switch (handler.getStateManager().getCurrentChapter()) {
            case ONE:
                switch (index) {
                    case 0:
                        overWorldCursor.setX((int)(6.25 * TILE_WIDTH));
                        overWorldCursor.setY((int)(11 * TILE_HEIGHT));
                        break;
                    case 1:
                        overWorldCursor.setX((int)(4.85 * TILE_WIDTH));
                        overWorldCursor.setY((int)(8 * TILE_HEIGHT));
                        break;
                    case 2:
                        overWorldCursor.setX((int)(4.95 * TILE_WIDTH));
                        overWorldCursor.setY((int)(5.35 * TILE_HEIGHT));
                        break;
                    case 3:
                        overWorldCursor.setX((int)(8.6 * TILE_WIDTH));
                        overWorldCursor.setY((int)(5.35 * TILE_HEIGHT));
                        break;
                    case 4:
                        overWorldCursor.setX((int)(10.5 * TILE_WIDTH));
                        overWorldCursor.setY((int)(6.7 * TILE_HEIGHT));
                        break;
                    case 5:
                        overWorldCursor.setX((int)(12.35 * TILE_WIDTH));
                        overWorldCursor.setY((int)(5.35 * TILE_HEIGHT));
                        break;
                    case 6:
                        overWorldCursor.setX((int)(5.6 * TILE_WIDTH));
                        overWorldCursor.setY((int)(2.95 * TILE_HEIGHT));
                        break;
                    default:
                        overWorldCursor.setX((int)(6.25 * TILE_WIDTH));
                        overWorldCursor.setY((int)(11 * TILE_HEIGHT));
                        break;
                }

                break;
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
                break;
            default:
                break;
        }
    }

    @Override
    public void getInput() {
        //b-button
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            handler.getStateManager().popIState();
        }
        //start-button (pause)
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT)) {
            handler.getStateManager().pushIState(StateManager.State.START_MENU, null);
        }

        switch (handler.getStateManager().getCurrentChapter()) {
            case ONE:
                //a-button
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    handler.getStateManager().setCurrentChapter(StateManager.Chapter.TWO);
                    handler.getStateManager().pushIState(StateManager.State.INTRO, null);
                }
                else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                    index++;

                    if (index > (stages.length - 1)) {
                        index = 0;
                    }
                }
                else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                    index--;

                    if (index < 0) {
                        index = (stages.length - 1);
                    }
                }

                //MOVED TO TICK()

                //overWorldCursor.tick();

                break;
            case TWO:
                //a-button
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    handler.getStateManager().setCurrentChapter(StateManager.Chapter.THREE);
                    handler.getStateManager().pushIState(StateManager.State.INTRO, null);
                }

                break;
            case THREE:
                //a-button
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    handler.getStateManager().setCurrentChapter(StateManager.Chapter.FOUR);
                    handler.getStateManager().pushIState(StateManager.State.INTRO, null);
                }

                break;
            case FOUR:
                //a-button
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    handler.getStateManager().setCurrentChapter(StateManager.Chapter.FIVE);
                    handler.getStateManager().pushIState(StateManager.State.INTRO, null);
                }

                break;
            case FIVE:
                break;
            default:
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        //NullState.render(Graphics) ------> fills the screen with background color of Displayer's panel.
        handler.getStateManager().getStatesStack().get(0).render(g);

        Graphics2D g2d = (Graphics2D)g;
        float opacity = 1.0f;

        switch (handler.getStateManager().getCurrentChapter()) {
            case ONE:
                g2d.drawImage(Assets.chapter1WorldMap, 0, 0, handler.panelWidth, handler.panelHeight,
                        0, 0, Assets.chapter1WorldMap.getWidth(), Assets.chapter1WorldMap.getHeight(),
                        null);
/*
                //////////////////// TOKEN /////////////////
                g2d.drawImage(Assets.upOverworld0, (int)((6.25 * 16) * 2.4765625), (int)((11 * 16) * 2.017857142857143),
                        (int)(((6.25 * 16) * 2.4765625) + (Assets.upOverworld0.getWidth() * 2.4765625)),
                        (int)(((11 * 16) * 2.017857142857143) + (Assets.upOverworld0.getHeight() * 2.017857142857143)),
                        0, 0, Assets.upOverworld0.getWidth(), Assets.upOverworld0.getHeight(), null);
                ////////////////////////////////////////////
*/
                /////OVERWORLDCURSOR//////
                overWorldCursor.render(g);
                //////////////////////////

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter1Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            case TWO:
                g2d.drawImage(Assets.chapter2WorldMap, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter2Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            case THREE:
                g2d.drawImage(Assets.chapter3WorldMap, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter3Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            case FOUR:
                g2d.drawImage(Assets.chapter4WorldMap, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter4Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            case FIVE:
                g2d.drawImage(Assets.chapter5WorldMap, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                opacity = 0.25f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter5Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                break;
            default:
                break;
        }
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    public int getIndex() {
        return index;
    }

}