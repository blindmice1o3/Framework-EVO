package com.evo.quests;

import com.evo.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class QuestManager {

    private Handler handler;

    private ArrayList<Quest> quests;

    public QuestManager(Handler handler) {
        this.handler = handler;

        quests = new ArrayList<Quest>();
    } // **** end QuestManager(Handler) constructor ****

    public void tick() {
        Iterator<Quest> it = quests.iterator();
        while(it.hasNext()) {
            Quest quest = it.next();
            if (quest.isActive()) {
                quest.tick();
            } else {
                it.remove();
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

    //GETTERS AND SETTERS

    public ArrayList<Quest> getQuests() {
        return quests;
    }

} // **** end QuestManager class ****