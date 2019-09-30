package com.evo.entities.moveable.player;

import java.io.Serializable;

public class FishStateManager implements Serializable {

    ////////////////////////////////////////////////////////////

    //ACTION STATES
    public enum ActionState { EAT, BITE, HURT, NONE; }

    //BODY
    //BODY TEXTURE and BODY SIZE determine the images of HEAD and BODY to use.
    public enum BodyTexture {
        SLICK(200), SCALY(300), SHELL(600);

        BodyTexture(int cost) {
            this.cost = cost;
        }

        private int cost;
        public int getCost() { return cost; }
    }
    public enum BodySize {
        DECREASE(200), INCREASE(400);

        BodySize(int cost) {
            this.cost = cost;
        }

        private int cost;
        public int getCost() { return cost; }
    }
    public enum FinPectoral {
        ORIGINAL(200), COELAFISH(300), TACKLE(400);

        FinPectoral(int cost) { this.cost = cost; }

        private int cost;
        public int getCost() { return cost; }
    }
    public enum Tail {
        ORIGINAL(100), COELAFISH(150), TERATISU(200), ZINICHTHY(300), KURASELACHE(400);

        Tail(int cost) { this.cost = cost; }

        private int cost;
        public int getCost() { return cost; }
    }

    //HEAD
    public enum Jaws {
        ORIGINAL(10), KURASELACHES(200), ZINICHTHY(8000)/*, SWORDFISH*/;

        Jaws(int cost) {
            this.cost = cost;
        }

        private int cost;
        public int getCost() { return cost; }
    }
    //HEAD-ATTACHMENT
    public enum Horn { ORIGINAL, SPIRALED, ANGLER, SWORDFISH, NONE; }

    //BODY-ATTACHMENT
    public enum FinDorsal { ORIGINAL, SAILING, KURASELACHE; }

    ////////////////////////////////////////////////////////////

    //INSTANCE FIELDS
    private ActionState currentActionState;

    private BodySize currentBodySize;
    private BodyTexture currentBodyTexture;

    private Jaws currentJaws;
    private Horn currentHorn;

    private FinPectoral currentFinPectoral;
    private Tail currentTail;
    private FinDorsal currentFinDorsal;

    ////////////////////////////////////////////////////////////

    //CONSTRUCTOR with default values.
    public FishStateManager() {
        currentActionState = ActionState.NONE;

        currentBodySize = BodySize.DECREASE;
        currentBodyTexture = BodyTexture.SLICK;

        currentJaws = Jaws.ORIGINAL;
        currentHorn =  Horn.NONE;

        currentFinPectoral = FinPectoral.ORIGINAL;
        currentTail = Tail.ORIGINAL;
        currentFinDorsal = FinDorsal.ORIGINAL;
    } // **** end FishStateManager() constructor ****

    ////////////////////////////////////////////////////////////

    // GETTERS AND SETTERS
    public ActionState getCurrentActionState() {
        return currentActionState;
    }

    public void setCurrentActionState(ActionState currentActionState) { this.currentActionState = currentActionState; }

    public BodySize getCurrentBodySize() {
        return currentBodySize;
    }

    public void setCurrentBodySize(BodySize currentBodySize) { this.currentBodySize = currentBodySize; }

    public BodyTexture getCurrentBodyTexture() {
        return currentBodyTexture;
    }

    public void setCurrentBodyTexture(BodyTexture currentBodyTexture) { this.currentBodyTexture = currentBodyTexture; }

    public Jaws getCurrentJaws() {
        return currentJaws;
    }

    public void setCurrentJaws(Jaws currentJaws) { this.currentJaws = currentJaws; }

    public Horn getCurrentHorn() {
        return currentHorn;
    }

    public void setCurrentHorn(Horn currentHorn) { this.currentHorn = currentHorn; }

    public FinPectoral getCurrentFinPectoral() {
        return currentFinPectoral;
    }

    public void setCurrentFinPectoral(FinPectoral currentFinPectoral) { this.currentFinPectoral = currentFinPectoral; }

    public Tail getCurrentTail() {
        return currentTail;
    }

    public void setCurrentTail(Tail currentTail) { this.currentTail = currentTail; }

    public FinDorsal getCurrentFinDorsal() {
        return currentFinDorsal;
    }

    public void setCurrentFinDorsal(FinDorsal currentFinDorsal) { this.currentFinDorsal = currentFinDorsal; }

} // **** end FishStateManager class ****