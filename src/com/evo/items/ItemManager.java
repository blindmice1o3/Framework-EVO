package com.evo.items;

import com.evo.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ItemManager {

    private Handler handler;
    private ArrayList<Item> items;

    public ItemManager(Handler handler) {
        this.handler = handler;
        items = new ArrayList<Item>();
    } // **** end ItemManager(Handler) constructor

    public void tick() {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item i = it.next();
            i.tick();

            if ( (i.isPickedUp()) || (i.isExpired()) ) {
                it.remove();
            }
        }
    }

    public void render(Graphics g) {
        for (Item i : items) {
            //using the 1 parameter render() (NOT the overloaded one with int x, int y) because we want to render
            //this item onto the ground of our game world (which means we need the item's stored position, x and y).
            i.render(g);
        }
    }

    public void addItem(Item i) {
        i.setHandler(handler);
        items.add(i);
    }

    // GETTERS AND SETTERS

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
} // **** end ItemManager class ****