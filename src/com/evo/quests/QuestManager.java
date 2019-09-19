package com.evo.quests;

import com.evo.Handler;

import java.awt.*;
import java.util.ArrayList;

public class QuestManager {

    private Handler handler;

    private ArrayList<Quest> quests;

    public QuestManager(Handler handler) {
        this.handler = handler;

        quests = new ArrayList<Quest>();
    } // **** end QuestManager(Handler) constructor ****

    public void tick() {
        for (Quest quest : quests) {
            if (quest.isActive()) {
                quest.tick();
            }
        }
    }

    public void render(Graphics g) {

    }

    public Quest findQuest(String thingBeingCounted) {
        for (Quest quest : quests) {
            if (quest.getThingBeingCounted().equals(thingBeingCounted)) {
                return quest;
            }
        }
        //if haven't returned, Quest is not in QuestManager.
        return null;
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
    }

} // **** end QuestManager class ****