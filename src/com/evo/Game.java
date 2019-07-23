package com.evo;

import com.evo.gfx.Assets;
import com.evo.states.StateManager;

public class Game implements Runnable {

    //CONSTANTS
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    private static final int UPDATES_PER_SEC = 60;  // number of game update per second
    private static final long UPDATE_PERIOD_NSEC = 1000000000L / UPDATES_PER_SEC; // timeAllotted (in nanoseconds)

    //MEMBER FIELDS
    private Displayer displayer;

    public Game() {

    } // **** end Game() constructor ****

    // Interface: Runnable
    // Thread class's start() method is where this run() method hooks into.
    @Override
    public void run() {
        initGame();

        gameLoop();

        shutdownGame();
    }

    private void initGame() {
        Assets.init();
        StateManager.init();

        displayer = new Displayer("EVO: Search for Eden", WIDTH, HEIGHT);
    }

    private void gameLoop() {
        long timeBegin;
        long timeElapsed;
        long timeLeft; // in milliseconds (for Thread.sleep() method)

        int tickCounter = 0;
        int secondCounter = 0;
        while(true) {
            timeBegin = System.nanoTime();

            // !!!!!GAME LOOP DOING WORK!!!!!
            //////////////////////////////////
            tick();
            render();
            //////////////////////////////////

            // CONSOLE OUTPUT to track number of seconds.
            tickCounter++;
            //if (tickCounter < UPDATES_PER_SEC) {
            //    System.out.println("tick: " + tickCounter);
            //}
            if (tickCounter == UPDATES_PER_SEC) {
                secondCounter++;
                System.out.println("Game.gameLoop() - SECONDS: " + secondCounter);

                tickCounter = 0;
            }

            // DELAY TIMER to provide the necessary delay to meet the target rate.
            timeElapsed = System.nanoTime() - timeBegin;
            timeLeft = (UPDATE_PERIOD_NSEC - timeElapsed) / 1000000L; // in milliseconds (for Thread.sleep() method)

            // SET A MINIMUM.
            if (timeLeft < 10) {
                timeLeft = 10;
            }

            // SLEEP THE THREAD.
            try {
                //Provides the necessary delay and also yields control so that other thread can do work.
                Thread.sleep(timeLeft); // in milliseconds (for Thread.sleep() method)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void shutdownGame() {

        // display high score and clean-up code.

    }

    private void tick() {
        //System.out.println("Game.tick()");
        StateManager.tick();
    }

    private void render() {
        //System.out.println("Game.render()");
        displayer.getPanel().repaint();
    }

} // **** end Game class ****