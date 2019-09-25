package com.evo.entities.moveable.player;

public interface IPlayable {
    void getInput();

    int getHealthMax();
    int getHealth();
    int getExperiencePoints();
}