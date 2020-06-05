package com.evo;

import com.evo.gfx.Assets;
import com.evo.gfx.GameCamera;
import com.evo.input.KeyManager;
import com.evo.serialize_deserialize.SaverAndLoader;
import com.evo.states.StateManager;

public class Game implements Runnable {

    //CONSTANTS
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    //TIMING AND UPDATE_FREQUENCY
    long timePrevious, timeNow, timeElapsed, timeLeft; // in milliseconds (for Thread.sleep() method)
    private static final int UPDATES_PER_SEC = 60;  // number of game update per second
    private static final long UPDATE_PERIOD_NSEC = 1000000000L / UPDATES_PER_SEC; // timeAllotted (in nanoseconds)

    //MEMBER FIELDS
    private Handler handler;
    private KeyManager keyManager;
    private Displayer displayer;
    private StateManager stateManager;
    private SaverAndLoader saverAndLoader;
    private GameCamera gameCamera;

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

    public void initGame() {
        Assets.init();

        //System.out.println("0");
        stateManager = new StateManager();
        //System.out.println("1");
        handler = new Handler(this);
        //System.out.println("2");
        gameCamera = new GameCamera(handler, 0, 0);
        //System.out.println("3");
        keyManager = new KeyManager();
        //System.out.println("4");
        displayer = new Displayer("EVO: Search for Eden", WIDTH, HEIGHT, handler);
        //System.out.println("5");
        ////////////////////////////
        handler.init();
        //System.out.println("6");
        stateManager.init(handler);
        //System.out.println("7");
        ////////////////////////////
        saverAndLoader = new SaverAndLoader(handler);
        //System.out.println("8");
    }

    private void gameLoop() {
        timePrevious = System.nanoTime();

        int tickCounter = 0;
        int secondCounter = 0;
        while(true) {
            timeNow = System.nanoTime();
            timeElapsed = timeNow - timePrevious;
            timePrevious = timeNow;

            // !!!!!GAME LOOP DOING WORK!!!!!
            //////////////////////////////////
            tick(timeElapsed);
            render();
            //////////////////////////////////

            // CONSOLE OUTPUT to track number of seconds.
            tickCounter++;
            if (tickCounter == UPDATES_PER_SEC) {
                secondCounter++;
                System.out.println("Game.gameLoop() - SECONDS: " + secondCounter);

                tickCounter = 0;
            }

            // DELAY TIMER to provide the necessary delay to meet the target rate.
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

    private void tick(long timeElapsed) {
        //@@@@@@@@@@@@@@@@
        keyManager.tick();
        //@@@@@@@@@@@@@@@@

        stateManager.tick(timeElapsed);
    }

    private void render() {
        //System.out.println("Game.render()");
        displayer.getPanel().repaint();
    }

    // GETTERS AND SETTERS
    public KeyManager getKeyManager() { return keyManager; }

    public Displayer getDisplayer() {
        return displayer;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public void setStateManager(StateManager stateManager) { this.stateManager = stateManager; }

    public SaverAndLoader getSaverAndLoader() { return saverAndLoader; }

    public GameCamera getGameCamera() { return gameCamera; }

} // **** end Game class ****