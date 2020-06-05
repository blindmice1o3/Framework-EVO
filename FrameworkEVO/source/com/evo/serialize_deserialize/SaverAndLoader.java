package com.evo.serialize_deserialize;

import com.evo.Handler;
import com.evo.entities.Entity;
import com.evo.entities.moveable.enemies.SeaJelly;
import com.evo.entities.moveable.player.Fish;
import com.evo.entities.moveable.player.FishStateManager;
import com.evo.entities.non_moveable.Kelp;
import com.evo.game_stages.GameStage;
import com.evo.game_stages.hud.ComponentHUD;
import com.evo.gfx.Assets;
import com.evo.items.Item;
import com.evo.quests.Quest;
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
                GameStageState gameStageState = (GameStageState) handler.getGame().getStateManager().getState(StateManager.State.GAME_STAGE);
                GameStage currentGameStage = gameStageState.getCurrentGameStage();

                if (currentGameStage.getIdentifier() == GameStage.Identifier.EVO) {
                    FishStateManager fishStateManager = ((Fish) currentGameStage.getPlayer()).getFishStateManager();
                    Fish.DirectionFacing directionFacing = ((Fish) currentGameStage.getPlayer()).getDirectionFacing();
                    float x = ((Fish) currentGameStage.getPlayer()).getX();
                    float y = ((Fish) currentGameStage.getPlayer()).getY();
                    ArrayList<Entity> entities = currentGameStage.getEntityManager().getEntities();
                    ArrayList<Item> items = currentGameStage.getItemManager().getItems();
                    ArrayList<ComponentHUD> timedNumericIndicators = currentGameStage.getHeadUpDisplay().getTimedNumericIndicators();
                    ArrayList<Quest> quests = gameStageState.getQuestManager().getQuests();


                    objectOutputStream.writeObject(fishStateManager);
                    objectOutputStream.writeObject(directionFacing);
                    objectOutputStream.writeFloat(x);
                    objectOutputStream.writeFloat(y);
                    objectOutputStream.writeObject(entities);
                    objectOutputStream.writeObject(items);
                    objectOutputStream.writeObject(timedNumericIndicators);
                    objectOutputStream.writeObject(quests);
                }
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


            ////////////////////////////////////////////////////////////////////
            //should be GameStageState
            GameStageState gameStageState = (GameStageState) handler.getGame().getStateManager().getState(StateManager.State.GAME_STAGE);
            GameStage currentGameStage = gameStageState.getCurrentGameStage();

            if (currentGameStage.getIdentifier() == GameStage.Identifier.EVO) {
                FishStateManager fishStateManager = (FishStateManager) objectInputStream.readObject();
                Fish.DirectionFacing directionFacing = (Fish.DirectionFacing) objectInputStream.readObject();
                float x = objectInputStream.readFloat();
                float y = objectInputStream.readFloat();
                ArrayList<Entity> entities = (ArrayList<Entity>) objectInputStream.readObject();
                ArrayList<Item> items = (ArrayList<Item>) objectInputStream.readObject();
                ArrayList<ComponentHUD> timedNumericIndicators = (ArrayList<ComponentHUD>) objectInputStream.readObject();
                ArrayList<Quest> quests = (ArrayList<Quest>) objectInputStream.readObject();

                fishStateManager.setHandler(handler);

                ((Fish)currentGameStage.getPlayer()).setFishStateManager(fishStateManager);
                ((Fish)currentGameStage.getPlayer()).tick(0);
                ((Fish)currentGameStage.getPlayer()).setDirectionFacing(directionFacing);
                ((Fish)currentGameStage.getPlayer()).setX(x);
                ((Fish)currentGameStage.getPlayer()).setY(y);
                currentGameStage.getEntityManager().setEntities(entities);
                currentGameStage.getItemManager().setItems(items);
                currentGameStage.getHeadUpDisplay().setTimedNumericIndicators(timedNumericIndicators);
                gameStageState.getQuestManager().setQuests(quests);

                for (Entity e : currentGameStage.getEntityManager().getEntities()) {
                    e.initAnimations();

                    e.setHandler(handler);

                    if (e instanceof Fish) {
                        currentGameStage.getEntityManager().setPlayer((Fish)e);
                    }
                }

                for (Item i : currentGameStage.getItemManager().getItems()) {
                    if (i.getName().equals("Meat")) {
                        i.setTexture(Assets.meat);
                    }

                    i.setHandler(handler);
                }

                for (ComponentHUD componentHUD : currentGameStage.getHeadUpDisplay().getTimedNumericIndicators()) {
                    componentHUD.setHandler(handler);
                }

                for (Quest quest : gameStageState.getQuestManager().getQuests()) {
                    quest.setHandler(handler);
                }
            }
            ////////////////////////////////////////////////////////////////////


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