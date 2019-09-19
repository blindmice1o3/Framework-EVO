package com.evo.quests;

public interface QuestGiver {

    Quest giveQuest();
    boolean checkQuestCompletion();
    void giveReward();

}