package com.evo.states;

import com.evo.Handler;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateManager implements Serializable {

    public enum State { NULL, INTRO, CHAPTER, WORLD_MAP, GAME_STAGE, MAIN_MENU; }
    public enum Chapter { ONE, TWO, THREE, FOUR, FIVE; }

    private Handler handler;

    private Map<State, IState> states;
    private List<IState> statesStack;

    //private IState currentState;
    private Chapter currentChapter;

    public StateManager() {
        states = new HashMap<State, IState>();
        statesStack = new ArrayList<IState>();

        //currentState = null;
        currentChapter = Chapter.ONE;
    }

    public void init(Handler handler) {
        this.handler = handler;

        states.put(State.NULL, new NullState(handler));
        states.put(State.INTRO, new IntroState(handler));
        states.put(State.CHAPTER, new ChapterState(handler));
        states.put(State.WORLD_MAP, new WorldMapState(handler));
        states.put(State.MAIN_MENU, new MainMenuState(handler));
        states.put(State.GAME_STAGE, new GameStageState(handler));

        statesStack.add(states.get(State.NULL));

        //currentState = states.get(State.INTRO);
    }

    public void tick() {
        getTop().tick();
    }

    public void render(Graphics g) {
        if (getTop() != null) {
            getTop().render(g);
        }
    }

    //this is pushIState(State state, Object[] args)
    public void pushIState(State state, Object[] args) {
        getTop().exit();
        statesStack.add(states.get(state));
        getTop().enter(args);
    }

    //this is popIState()
    public void popIState() {
        getTop().exit();
        statesStack.remove(statesStack.size()-1);
    }

    /*
    public void pushIState(State state, Object[] args) {
        currentState.exit();
        currentState = states.get(state);
        currentState.enter(args);
    }
    */

    public IState getTop() {
        return statesStack.get(statesStack.size()-1);
    }

    public IState getState(State state) { return states.get(state); }

    //GETTERS AND SETTERS

    public List<IState> getStatesStack() { return statesStack; }

    public Chapter getCurrentChapter() { return currentChapter; }

    public void setCurrentChapter(Chapter chapter) { currentChapter = chapter; }
}
