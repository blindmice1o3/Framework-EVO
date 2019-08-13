package com.evo.serialize_deserialize;

import com.evo.Handler;
import com.evo.entities.moveable.fish.Fish;
import com.evo.entities.moveable.fish.FishStateManager;
import com.evo.states.GameStageState;
import com.evo.states.IntroState;
import com.evo.states.StateManager;

import java.io.*;

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
                FishStateManager fishStateManager = gameStageState.getCurrentGameStage().getFishInstance().getFishStateManager();
                Fish.DirectionFacing directionFacing = gameStageState.getCurrentGameStage().getFishInstance().getDirectionFacing();
                float x = gameStageState.getCurrentGameStage().getFishInstance().getX();
                float y = gameStageState.getCurrentGameStage().getFishInstance().getY();

                objectOutputStream.writeObject(fishStateManager);
                objectOutputStream.writeObject(directionFacing);
                objectOutputStream.writeFloat(x);
                objectOutputStream.writeFloat(y);
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

            gameStageState.getCurrentGameStage().getFishInstance().setFishStateManager(fishStateManager);
            gameStageState.getCurrentGameStage().getFishInstance().tick();
            gameStageState.getCurrentGameStage().getFishInstance().setDirectionFacing(directionFacing);
            gameStageState.getCurrentGameStage().getFishInstance().setX(x);
            gameStageState.getCurrentGameStage().getFishInstance().setY(y);



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