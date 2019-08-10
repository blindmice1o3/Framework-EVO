package com.evo.serialize_deserialize;

import com.evo.Handler;
import com.evo.entities.moveable.fish.FishStateManager;
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

            try (FileOutputStream fileOutputStream = new FileOutputStream(path)){

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

                ////////////////////////////////////////////////////////////////////
                //should be IntroState
                if (handler.getGame().getStateManager().getStatesStack().get(1) instanceof IntroState) {
                    IntroState introState = (IntroState)handler.getGame().getStateManager().getStatesStack().get(1);
                    FishStateManager fishStateManager = introState.getFishInstance().getFishStateManager();

                    objectOutputStream.writeObject(fishStateManager);
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

            //should be IntroState
            if (handler.getGame().getStateManager().getStatesStack().get(1) instanceof IntroState) {
                IntroState introState = (IntroState)handler.getGame().getStateManager().getStatesStack().get(1);

                FishStateManager fishStateManager = (FishStateManager)objectInputStream.readObject();

                introState.getFishInstance().setFishStateManager(fishStateManager);
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