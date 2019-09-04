package com.evo.rewards;

import com.evo.Handler;
import com.evo.entities.moveable.player.Fish;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class Reward {

    public enum RewardType { HP, EXP, DAMAGE; }

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

    /*
    TODO: create HeadUpDisplay class and instantiate in GameStage. HeadUpDisplay.tick() should be a copy of
    Reward.tick(), but with HUD-related-code after the copied updating-time-duration logic.


    -decouple Damage from Reward/RewardManager.
    -HeadUpDisplay class will appropriate the responsibility of rendering numeric-related-updaters (health points
    gained, experience points gained, damage incurred).
     */
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
            if (stash.get(RewardType.HP) != null) {
                g.setColor(Color.GREEN);
                g.drawString("+" + stash.get(RewardType.HP).toString(),
                        (int) (x - handler.getGameCamera().getxOffset()),
                        (int) (y - handler.getGameCamera().getyOffset()));
            }

            if (stash.get(RewardType.EXP) != 0) {
                g.setColor(Color.WHITE);
                g.drawString("+" + stash.get(RewardType.EXP).toString(),
                        (int) (x - handler.getGameCamera().getxOffset()),
                        (int) (y - handler.getGameCamera().getyOffset() + 10));
            }

            if (stash.get(RewardType.DAMAGE) != null) {
                g.setColor(Color.RED);
                g.drawString("-" + stash.get(RewardType.DAMAGE).toString(),
                        (int) (x - handler.getGameCamera().getxOffset() - 10),
                        (int) (y - handler.getGameCamera().getyOffset() - 10));
            }
        }
    }

    public void giveReward(Fish player) {
        player.setExperiencePoints( (player.getExperiencePoints() + stash.get(RewardType.EXP)) );
        if (stash.get(RewardType.HP) != null) {
            player.setHealth((player.getHealth() + stash.get(RewardType.HP)));
        }

        //do NOT let eating meat items give player more health points than player's maximum health points.
        if (player.getHealth() > player.getHealthMax()) {
            player.setHealth( player.getHealthMax() );
        }

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        timerStarted = true;
        timePrevious = System.currentTimeMillis();
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    // GETTERS AND SETTERS

    public boolean isTimerFinished() {
        return timerFinished;
    }

    public void setTimerStarted(boolean timerStarted) { this.timerStarted = timerStarted; }

} // **** end Reward class ****