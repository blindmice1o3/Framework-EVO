package com.evo.game_stages.hud;

import com.evo.Handler;
import com.evo.entities.moveable.player.Fish;

import java.awt.*;
import java.util.ArrayList;

public class HeadUpDisplay {

    private Handler handler;
    private ArrayList<ComponentHUD> timedNumericIndicators;

    private Fish player;



    public HeadUpDisplay(Handler handler, Fish player) {
        this.handler = handler;
        this.player = player;

        timedNumericIndicators = new ArrayList<ComponentHUD>();
    } // **** end HeadUpDisplay(Handler, Fish) constructor ****

    public void tick() {
        for (ComponentHUD componentHUD : timedNumericIndicators) {
            if (componentHUD.isTimerStarted()) {
                componentHUD.tick();
            }
        }

        //
    }

    public void render(Graphics g) {
        for (ComponentHUD componentHUD : timedNumericIndicators) {
            if (componentHUD.isTimerStarted()) {
                componentHUD.render(g);
            }
        }

        //hp and exp indicators.
        renderHUD(g);
    }

    private void renderHUD(Graphics g) {
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

} // **** end HeadUpDisplay class ****