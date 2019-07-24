package com.evo.states;

import com.evo.Handler;
import com.evo.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;

public class WorldMapState implements IState {

    private Handler handler;

    private int index;

    public WorldMapState(Handler handler) {
        this.handler = handler;

        index = 0;
    }

    @Override
    public void tick() {
        getInput();
    }

    @Override
    public void getInput() {
        switch (handler.getStateManager().getCurrentChapter()) {
            case ONE:
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                    index++;
                    System.out.println(index);
                }
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                    index--;
                    System.out.println(index);
                }
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    //CHANGE StateManager's currentChapter!!!!!
                }
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    handler.getStateManager().changeIState(StateManager.State.CHAPTER, null);
                }

                System.out.println("WorldMapState.getInput() =====> " + handler.getStateManager().getCurrentChapter());
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
    public void render(Graphics g) {
        switch (handler.getStateManager().getCurrentChapter()) {
            case ONE:
                g.drawImage(Assets.chapter1WorldMap, 0, 0, handler.panelWidth, handler.panelHeight, null);

                // CHANGING OPACITY OF NEXT IMAGE
                float opacity = 0.25f;
                Graphics2D g2d = (Graphics2D)g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(Assets.chapter1Wave, 0, 0, handler.panelWidth, handler.panelHeight, null);

                System.out.println("WorldMapState.render(Graphics) =====> " + handler.getStateManager().getCurrentChapter());
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
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

}