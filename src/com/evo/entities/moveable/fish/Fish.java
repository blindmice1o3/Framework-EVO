package com.evo.entities.moveable.fish;

import com.evo.entities.moveable.Creature;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fish extends Creature {

    private enum Jaws { ORIGINAL, KURASELACHES, ZINICHTHY, SWORDFISH; }
    private enum Horn { ORIGINAL, SPIRALED, ANGLER, SWORDFISH; }
    private enum BodyTexture { SLICK, SCALY, SHELL; }
    private enum BodySize { ORIGINAL, LARGE; }
    private enum FinPectoral { ORIGINAL, COELAFISH, TACKLE; }
    private enum FinDorsal { ORIGINAL, SAILING, KURASELACHE; }
    private enum Tail { ORIGINAL, COELAFISH, TERATISU, ZINICHTHY, KURASELACHE; }

    private enum DirectionFacing { UP, DOWN, LEFT, RIGHT; }

    private Jaws jaws;
    private Horn horn;
    private BodyTexture bodyTexture;
    private BodySize bodySize;
    private FinPectoral finPectoral;
    private FinDorsal finDorsal;
    private Tail tail;

    private DirectionFacing directionFacing;

    public Fish(BufferedImage image, int x, int y) {
        //super(image, x, y, (2 * Tile.WIDTH), Tile.HEIGHT);
        super(image, x, y, 100, 50);

        jaws = Jaws.ORIGINAL;
        horn =  Horn.ORIGINAL;
        bodyTexture = BodyTexture.SLICK;
        bodySize = BodySize.ORIGINAL;
        finPectoral = FinPectoral.ORIGINAL;
        finDorsal = FinDorsal.ORIGINAL;
        tail = Tail.ORIGINAL;

        directionFacing = DirectionFacing.UP;
    }

    public void tick() {

    }

    public void render(Graphics g) {

    }

}