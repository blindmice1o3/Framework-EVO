package com.evo.entities;

import com.evo.Handler;
import com.evo.entities.moveable.player.Fish;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class EntityManager {

    private Handler handler;
    private Fish player;
    private ArrayList<Entity> entities;

    private Comparator<Entity> renderSorter = new Comparator<Entity>() {
        @Override
        public int compare(Entity a, Entity b) {
            //compare based on the y-value of the Entity's BOTTOM-left corner.
            if ( (a.getY() + a.getHeight()) < (b.getY() + b.getHeight()) ) {
                return -1;
            } else {
                return 1;
            }
        }
    };


    public EntityManager(Handler handler, Fish player) {
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<Entity>();
        addEntity(player);
    } // **** end EntityManager(Handler, Fish) constructor ****

    public void tick(long timeElapsed) {
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity e = it.next();

            ////////////////////
            e.tick(timeElapsed);
            ////////////////////

            ////////////////////////
            //check for active.
            if (!e.isActive()) {
                it.remove();
            }
            ////////////////////////
        }
        /////////////////////////////////////////
        //sort based on y-value before rendering.
        entities.sort(renderSorter);
        /////////////////////////////////////////
    }

    public void render(Graphics g) {

        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.render(g);
        }
/*
        for (Entity e : entities) {
            e.render(g);
        }
*/
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