package com.evo.states;

import com.evo.Handler;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StateManager {

    public static enum State { INTRO, CHAPTER, WORLD_MAP, GAME_STAGE, MENU; }
    public static enum Chapter { ONE, TWO, THREE, FOUR, FIVE; }

    private Handler handler;
    private Map<State, IState> states;
    private IState currentState;
    private Chapter currentChapter;

    public StateManager() {
        currentState = null;
        currentChapter = Chapter.ONE;
    }

    public void init(Handler handler) {
        this.handler = handler;
        states = new HashMap<State, IState>();

        states.put(State.INTRO, new IntroState(handler));
        states.put(State.CHAPTER, new ChapterState(handler));
        states.put(State.WORLD_MAP, new WorldMapState(handler));

        currentState = states.get(State.INTRO);
    }

    public void tick() {
        currentState.tick();
    }

    public void render(Graphics g) {
        if (currentState != null) {
            currentState.render(g);
        }
    }

    public void changeIState(State key, Object[] args) {
        currentState.exit();
        currentState = states.get(key);
        currentState.enter(args);
    }

    //GETTERS AND SETTERS
    public IState getCurrentState() {
        return currentState;
    }

    public Chapter getCurrentChapter() { return currentChapter; }

}
