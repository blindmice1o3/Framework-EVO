package com.evo.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
    private boolean[] keys, justPressed, cantPress;
    public boolean up, down, left, right, aButton, bButton, start, select;

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public void tick() {
        // TO LIMIT TO KEY-JUST-PRESSED
        for (int i = 0; i < keys.length; i++) {
            if (cantPress[i] && !keys[i]) {
                cantPress[i] = false;
            } else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if (!cantPress[i] && keys[i]) {
                justPressed[i] = true;
            }
        }

        // DIRECTIONALS
        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];

        // EXECUTORS
        aButton = keys[KeyEvent.VK_COMMA];
        bButton = keys[KeyEvent.VK_PERIOD];
        start = keys[KeyEvent.VK_ENTER];
        select = keys[KeyEvent.VK_SHIFT];
    }

    public boolean keyJustPressed(int keyCode) {
        return justPressed[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}