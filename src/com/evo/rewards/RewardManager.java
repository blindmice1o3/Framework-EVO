package com.evo.rewards;

import com.evo.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class RewardManager {

    //TODO: make this HUDManager class and change Reward to HUDComponentToRender.

    private Handler handler;
    private ArrayList<Reward> rewards;

    public RewardManager(Handler handler) {
        this.handler = handler;
        rewards = new ArrayList<Reward>();
    } // **** end RewardManager(Handler) constructor ****

    public void tick() {
        Iterator<Reward> it = rewards.iterator();
        while(it.hasNext()) {
            Reward reward = it.next();

            reward.tick();

            if (reward.isTimerFinished()) {
                it.remove();
            }
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < rewards.size(); i++) {
            Reward reward = rewards.get(i);
            reward.render(g);
        }
    }

    public void addReward(Reward reward) {
        rewards.add(reward);
    }

} // **** end RewardManager class ****