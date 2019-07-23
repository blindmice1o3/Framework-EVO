package com.evo.states;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StateManager {

    public static enum State { INTRO, WORLD_MAP, GAME_STAGE, MENU; }

    private static Map<State, IState> states;
    private static IState currentState;

    public static void init() {
        states = new HashMap<State, IState>();

        states.put(State.INTRO, new IntroState());
        states.put(State.WORLD_MAP, new WorldMapState());

        currentState = states.get(State.INTRO);
    }

    public static void tick() {
        currentState.tick();
    }

    public static void render(Graphics g) {
        currentState.render(g);
    }

    public static void changeIState(State key, Object[] args) {
        currentState.exit();
        currentState = states.get(key);
        currentState.enter(args);
    }

    //GETTERS AND SETTERS
    public static IState getCurrentState() {
        return currentState;
    }

}
