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
        ORIGINAL(10, 0), KURASELACHES(3000, 2), ZINICHTHY(300, 4)/*, SWORDFISH*/;

        Jaws(int cost, int damageBiteBonus) {
            this.cost = cost;
            this.damageBiteBonus = damageBiteBonus;
        }

        private int cost;
        private int damageBiteBonus;
        public int getCost() { return cost; }
        public int getDamageBiteBonus() { return damageBiteBonus; }
    }
    //HEAD-ATTACHMENT
    public enum Horn { ORIGINAL, SPIRALED, ANGLER, SWORDFISH, NONE; }

    //BODY-ATTACHMENT
    public enum FinDorsal { ORIGINAL, SAILING, KURASELACHE; }

    ////////////////////////////////////////////////////////////

    public static final int BASE_BITE = 1, BASE_STRENGTH = 1, BASE_KICK = 0, BASE_STRIKE = 0, BASE_HORN = 0,
            BASE_DEFENSE = 0, BASE_AGILITY = 4, BASE_JUMP = 1;

    //INSTANCE FIELDS
    private ActionState currentActionState;

    private BodySize currentBodySize;
    private BodyTexture currentBodyTexture;

    private Jaws currentJaws;
    private Horn currentHorn;

    private FinPectoral currentFinPectoral;
    private Tail currentTail;
    private FinDorsal currentFinDorsal;

    private int damageBite, damageStrength, damageKick, damageStrike, damageHorn,
            defense, agility, jump;
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

        damageBite = BASE_BITE;
        damageStrength = BASE_STRENGTH;
        damageKick = BASE_KICK;
        damageStrike = BASE_STRIKE;
        damageHorn = BASE_HORN;
        defense = BASE_DEFENSE;
        agility = BASE_AGILITY;
        jump = BASE_JUMP;
    } // **** end FishStateManager() constructor ****

    ////////////////////////////////////////////////////////////
    //TODO: update BONUSES from the new body part.
    public void setCurrentJaws(Jaws currentJaws) {
        this.currentJaws = currentJaws;
        damageBite = BASE_BITE + currentJaws.getDamageBiteBonus();
    }

    public void setCurrentBodyTexture(BodyTexture currentBodyTexture) { this.currentBodyTexture = currentBodyTexture; }

    public void setCurrentBodySize(BodySize currentBodySize) { this.currentBodySize = currentBodySize; }

    public void setCurrentFinPectoral(FinPectoral currentFinPectoral) { this.currentFinPectoral = currentFinPectoral; }

    public void setCurrentTail(Tail currentTail) { this.currentTail = currentTail; }

    public void setCurrentFinDorsal(FinDorsal currentFinDorsal) { this.currentFinDorsal = currentFinDorsal; }

    public void setCurrentHorn(Horn currentHorn) { this.currentHorn = currentHorn; }

    // GETTERS AND SETTERS
    public ActionState getCurrentActionState() {
        return currentActionState;
    }

    public void setCurrentActionState(ActionState currentActionState) { this.currentActionState = currentActionState; }

    public Jaws getCurrentJaws() {
        return currentJaws;
    }

    public BodyTexture getCurrentBodyTexture() {
        return currentBodyTexture;
    }

    public BodySize getCurrentBodySize() {
        return currentBodySize;
    }

    public FinPectoral getCurrentFinPectoral() {
        return currentFinPectoral;
    }

    public Tail getCurrentTail() {
        return currentTail;
    }

    public FinDorsal getCurrentFinDorsal() {
        return currentFinDorsal;
    }

    public Horn getCurrentHorn() {
        return currentHorn;
    }

    public int getDamageBite() {
        return damageBite;
    }

    public int getDamageStrength() {
        return damageStrength;
    }

    public int getDamageKick() {
        return damageKick;
    }

    public int getDamageStrike() {
        return damageStrike;
    }

    public int getDamageHorn() {
        return damageHorn;
    }

    public int getDefense() {
        return defense;
    }

    public int getAgility() {
        return agility;
    }

    public int getJump() {
        return jump;
    }

} // **** end FishStateManager class ****