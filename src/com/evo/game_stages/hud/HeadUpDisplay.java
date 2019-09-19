package com.evo.game_stages.hud;

import com.evo.Handler;
import com.evo.entities.moveable.player.Fish;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class HeadUpDisplay {

    private Handler handler;
    private ArrayList<ComponentHUD> timedNumericIndicators;

    public HeadUpDisplay(Handler handler) {
        this.handler = handler;

        timedNumericIndicators = new ArrayList<ComponentHUD>();
    } // **** end HeadUpDisplay(Handler) constructor ****

    public void tick(long timeElapsed) {
        Iterator<ComponentHUD> it = timedNumericIndicators.iterator();
        while (it.hasNext()) {
            ComponentHUD componentHUD = it.next();

            ////////////////////
            componentHUD.tick(timeElapsed);
            ////////////////////

            if (componentHUD.isTimerFinished()) {
                it.remove();
            }
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < timedNumericIndicators.size(); i++) {
            ComponentHUD componentHUD = timedNumericIndicators.get(i);
            ///////////////////////
            componentHUD.render(g);
            ///////////////////////
        }

        //hp and exp indicators.
        renderHUD(g);
    }

    private void renderHUD(Graphics g) {
        Fish player = ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getPlayer();
        /* TOP OF SCREEN */
        //HP BAR
        g.setColor(Color.BLACK);
        g.fillRect(28, 11, 10*(player.getHealthMax()) +4, 12);
        g.setColor(Color.GREEN);
        g.fillRect(30, 13, 10*player.getHealth(), 8);

        //HP
        g.setColor(Color.GREEN);
        g.drawString("hp: ", 10, 20);
        g.drawString(Integer.toString(player.getHealth()), 10, 35);

        //XP
        g.setColor(Color.WHITE);
        g.drawString("experiencePoints: " + player.getExperiencePoints(), handler.panelWidth/2, 20);
    }

    public void addTimedNumericIndicator(ComponentHUD componentHUD) {
        timedNumericIndicators.add(componentHUD);
    }

    // GETTERS AND SETTERS

    public ArrayList<ComponentHUD> getTimedNumericIndicators() {
        return timedNumericIndicators;
    }

    public void setTimedNumericIndicators(ArrayList<ComponentHUD> timedNumericIndicators) {
        this.timedNumericIndicators = timedNumericIndicators;
    }

} // **** end HeadUpDisplay class ****