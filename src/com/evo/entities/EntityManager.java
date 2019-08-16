package com.evo.entities;

import com.evo.Handler;
import com.evo.entities.moveable.fish.Fish;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager {

    private Handler handler;
    private Fish player;
    private ArrayList<Entity> entities;

    public EntityManager(Handler handler, Fish player) {
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<Entity>();
    } // **** end EntityManager(Handler, Fish) constructor ****

    public void tick() {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.tick();
        }

        player.tick();
    }

    public void render(Graphics g) {
        for (Entity e : entities) {
            e.render(g);
        }

        player.render(g);
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    // GETTERS AND SETTERS

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Fish getPlayer() {
        return player;
    }

    public void setPlayer(Fish player) {
        this.player = player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

} // **** end EntityManager class ****