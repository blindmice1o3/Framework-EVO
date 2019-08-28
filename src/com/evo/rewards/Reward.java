package com.evo.rewards;

import com.evo.Handler;
import com.evo.entities.moveable.player.Fish;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class Reward {

    public enum RewardType { HP, EXP; }

    private Handler handler;

    private Map<RewardType, Integer> stash;
    private int x, y;
    private boolean timerStarted, timerFinished;
    private long timeElapsed, timePrevious, timerTarget;

    public Reward(Handler handler, Integer experiencePoints, int x, int y) {
        this.handler = handler;

        stash = new HashMap<RewardType, Integer>();
        stash.put(RewardType.EXP, experiencePoints);

        this.x = x;
        this.y = y;

        timerStarted = false;
        timerFinished = false;

        timerTarget = 5000; //milliseconds.
        timePrevious = 0;
    } // **** end Reward(Handler, Integer, int, int) constructor ****

    public void addExtra(RewardType rewardType, Integer value) {
        stash.put(rewardType, value);
    }

    public void tick() {
        if (timerStarted) {
            long timeNow = System.currentTimeMillis();

            timeElapsed += (timeNow - timePrevious);
            timePrevious = timeNow;

            if (timeElapsed >= timerTarget) {
                timerFinished = true;
            }
        }
    }

    public void render(Graphics g) {
        if (timerStarted) {
            g.setColor(Color.RED);
            g.drawString(stash.get(RewardType.HP).toString(),
                    (int)(x - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset()));

            g.setColor(Color.WHITE);
            g.drawString(stash.get(RewardType.EXP).toString(),
                    (int)(x - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset() + 10));
        }
    }

    public void giveReward(Fish player) {
        player.setHealth( (player.getHealth() + stash.get(RewardType.HP)) );
        player.setExperiencePoints( (player.getExperiencePoints() + stash.get(RewardType.EXP)) );

        //do NOT let eating meat items give player more health points than player's maximum health points.
        if (player.getHealth() > player.getHealthMax()) {
            player.setHealth( player.getHealthMax() );
        }

        timerStarted = true;
        timePrevious = System.currentTimeMillis();
    }

    // GETTERS AND SETTERS

    public boolean isTimerFinished() {
        return timerFinished;
    }

} // **** end Reward class ****