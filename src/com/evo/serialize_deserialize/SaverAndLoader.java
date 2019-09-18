package com.evo.serialize_deserialize;

import com.evo.Handler;
import com.evo.entities.Entity;
import com.evo.entities.moveable.enemies.SeaJelly;
import com.evo.entities.moveable.player.Fish;
import com.evo.entities.moveable.player.FishStateManager;
import com.evo.entities.non_moveable.Kelp;
import com.evo.gfx.Assets;
import com.evo.items.Item;
import com.evo.states.GameStageState;
import com.evo.states.StateManager;

import java.io.*;
import java.util.ArrayList;

public class SaverAndLoader {

    private Handler handler;

    public SaverAndLoader(Handler handler) {
        this.handler = handler;
    } // **** end SaverAndLoader(Handler) constructor

    public void save(String path) {
        System.out.println("SaverAndLoader.save(String)...");

        if (path.substring(path.length()-4).equals(".bin")) {

            try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);



                ////////////////////////////////////////////////////////////////////
                //should be GameStageState
                GameStageState gameStageState = (GameStageState)handler.getGame().getStateManager().getState(StateManager.State.GAME_STAGE);
                FishStateManager fishStateManager = gameStageState.getCurrentGameStage().getPlayer().getFishStateManager();
                Fish.DirectionFacing directionFacing = gameStageState.getCurrentGameStage().getPlayer().getDirectionFacing();
                float x = gameStageState.getCurrentGameStage().getPlayer().getX();
                float y = gameStageState.getCurrentGameStage().getPlayer().getY();
                ArrayList<Entity> entities = gameStageState.getCurrentGameStage().getEntityManager().getEntities();
                ArrayList<Item> items = gameStageState.getCurrentGameStage().getItemManager().getItems();

                objectOutputStream.writeObject(fishStateManager);
                objectOutputStream.writeObject(directionFacing);
                objectOutputStream.writeFloat(x);
                objectOutputStream.writeFloat(y);
                objectOutputStream.writeObject(entities);
                objectOutputStream.writeObject(items);
                ////////////////////////////////////////////////////////////////////



                objectOutputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("String argument does NOT end with \".bin\".");
        }
    }

    public void load() {
        System.out.println("SaverAndLoader.load()...");

        try (FileInputStream fileInputStream = new FileInputStream("qwerty789.bin")) {

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);



            //should be GameStageState
            GameStageState gameStageState = (GameStageState)handler.getGame().getStateManager().getState(StateManager.State.GAME_STAGE);

            FishStateManager fishStateManager = (FishStateManager)objectInputStream.readObject();
            Fish.DirectionFacing directionFacing = (Fish.DirectionFacing)objectInputStream.readObject();
            float x = objectInputStream.readFloat();
            float y = objectInputStream.readFloat();
            ArrayList<Entity> entities = (ArrayList<Entity>)objectInputStream.readObject();
            ArrayList<Item> items = (ArrayList<Item>)objectInputStream.readObject();

            gameStageState.getCurrentGameStage().getPlayer().setFishStateManager(fishStateManager);
            gameStageState.getCurrentGameStage().getPlayer().tick();
            gameStageState.getCurrentGameStage().getPlayer().setDirectionFacing(directionFacing);
            gameStageState.getCurrentGameStage().getPlayer().setX(x);
            gameStageState.getCurrentGameStage().getPlayer().setY(y);
            gameStageState.getCurrentGameStage().getEntityManager().setEntities(entities);
            gameStageState.getCurrentGameStage().getItemManager().setItems(items);

            for (Entity e : gameStageState.getCurrentGameStage().getEntityManager().getEntities()) {
                e.initAnimations();

                e.setHandler(handler);

                if (e instanceof Fish) {
                    ((GameStageState)handler.getStateManager().getState(StateManager.State.GAME_STAGE)).getCurrentGameStage().getEntityManager().setPlayer((Fish)e);
                }
            }

            for (Item i : gameStageState.getCurrentGameStage().getItemManager().getItems()) {
                if (i.getName().equals("Meat")) {
                    i.setTexture(Assets.meat);
                }

                i.setHandler(handler);
            }

            objectInputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

} // **** end SaverAndLoader class ****