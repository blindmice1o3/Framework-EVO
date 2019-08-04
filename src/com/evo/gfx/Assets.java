package com.evo.gfx;

import com.evo.Utils;
import com.evo.entities.moveable.fish.Fish;
import com.evo.entities.moveable.fish.FishStateManager;

import java.awt.image.BufferedImage;

public class Assets {

    // SPRITE SHEETS
    public static BufferedImage spriteSheetChapterIntroAndWorldMap, spriteSheetChapter1Creatures;

    // BACKGROUND - IntroState
    public static BufferedImage chapter1Intro, chapter2Intro, chapter3Intro, chapter4Intro, chapter5Intro;

    // BACKGROUND - ChapterState
    public static BufferedImage chapter1Chapter, chapter2Chapter, chapter3Chapter, chapter4Chapter, chapter5Chapter;

    // BACKGROUND - WorldMapState
    public static BufferedImage chapter1WorldMap, chapter2WorldMap, chapter3WorldMap, chapter4WorldMap, chapter5WorldMap;

    // TRANSPARENT LAYER - WAVE
    public static BufferedImage chapter1Wave, chapter2Wave, chapter3Wave, chapter4Wave, chapter5Wave;

    // OVERWORLD - TOKEN
    public static BufferedImage upOverworld0, upOverworld1, downOverworld0, downOverworld1,
            leftOverworld0, leftOverworld1, rightOverworld0, rightOverworld1;

    // ENTITY - Chapter 1: FISH HEAD (AND ATTACHMENTS)
    public static BufferedImage[][][][][] eatFrames, biteFrames, hurtFrames;

    // ENTITY - Chapter 1: FISH BODY (AND ATTACHMENTS)
    public static BufferedImage[][][][][] tailOriginal, tailCoelafish, tailTeratisu, tailZinichthy, tailKuraselache;

    // Initialization
    public static void init() {
        // SPRITE SHEETS
        spriteSheetChapterIntroAndWorldMap = Utils.loadImage("/SNES - EVO Search for Eden - Maps & Chapter Images.png");
        spriteSheetChapter1Creatures = Utils.loadImage("/SNES - EVO Search for Eden - Chapter 1 Creatures.png");

        // ENTITY - Chapter 1: FISH HEAD (AND ATTACHMENTS)
        eatFrames = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.Jaws.values().length]
                [FishStateManager.ActionState.values().length-1] //minus 1 because ActionState.NONE doesn't count.
                [3];
        biteFrames = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.Jaws.values().length]
                [FishStateManager.ActionState.values().length-1]
                [3];
        hurtFrames = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.Jaws.values().length]
                [FishStateManager.ActionState.values().length-1]
                [2];

        // ENTITY - Chapter 1: FISH BODY (AND ATTACHMENTS)
        tailOriginal = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.FinPectoral.values().length]
                [FishStateManager.Tail.values().length]
                [3];

        tailCoelafish = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.FinPectoral.values().length]
                [FishStateManager.Tail.values().length]
                [3];

        tailTeratisu = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.FinPectoral.values().length]
                [FishStateManager.Tail.values().length]
                [3];

        tailZinichthy = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.FinPectoral.values().length]
                [FishStateManager.Tail.values().length]
                [3];

        tailKuraselache = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.FinPectoral.values().length]
                [FishStateManager.Tail.values().length]
                [3];

        //DECREASE-SLICK-ORIGINAL
        pullFishHeadImageSubset(FishStateManager.BodySize.DECREASE, FishStateManager.BodyTexture.SLICK,
                FishStateManager.Jaws.ORIGINAL, 2, 166, 15, 15);
        //DECREASE-SLICK-KURASELACHES
        pullFishHeadImageSubset(FishStateManager.BodySize.DECREASE, FishStateManager.BodyTexture.SLICK,
                FishStateManager.Jaws.KURASELACHES, 2, 211, 15, 15);
        //DECREASE-SLICK-ZINICHTHY
        pullFishHeadImageSubset(FishStateManager.BodySize.DECREASE, FishStateManager.BodyTexture.SLICK,
                FishStateManager.Jaws.ZINICHTHY, 2, 256, 15, 15);
        //DECREASE-SLICK-SWORDFISH
                // TODO: DECREASE-SLICK-SWORDFISH jaws have much longer width, to-figure-out.

        //DECREASE-SCALY-ORIGINAL
        pullFishHeadImageSubset(FishStateManager.BodySize.DECREASE, FishStateManager.BodyTexture.SCALY,
                FishStateManager.Jaws.ORIGINAL, 80, 166, 15, 15);
        //DECREASE-SCALY-KURASELACHES
        pullFishHeadImageSubset(FishStateManager.BodySize.DECREASE, FishStateManager.BodyTexture.SCALY,
                FishStateManager.Jaws.KURASELACHES, 80, 211, 15, 15);
        //DECREASE-SCALY-ZINICHTHY
        pullFishHeadImageSubset(FishStateManager.BodySize.DECREASE, FishStateManager.BodyTexture.SCALY,
                FishStateManager.Jaws.ZINICHTHY, 80, 256, 15, 15);
        //DECREASE-SCALY-SWORDFISH
                // TODO: DECREASE-SCALY-SWORDFISH jaws have much longer width, to-figure-out.

        //DECREASE-SHELL-ORIGINAL
        pullFishHeadImageSubset(FishStateManager.BodySize.DECREASE, FishStateManager.BodyTexture.SHELL,
                FishStateManager.Jaws.ORIGINAL, 158, 166, 15, 15);
        //DECREASE-SHELL-KURASELACHES
        pullFishHeadImageSubset(FishStateManager.BodySize.DECREASE, FishStateManager.BodyTexture.SHELL,
                FishStateManager.Jaws.KURASELACHES, 158, 211, 15, 15);
        //DECREASE-SHELL-ZINICHTHY
        pullFishHeadImageSubset(FishStateManager.BodySize.DECREASE, FishStateManager.BodyTexture.SHELL,
                FishStateManager.Jaws.ZINICHTHY, 158, 256, 15, 15);
        //DECREASE-SHELL-SWORDFISH
                // TODO: DECREASE-SHELL-SWORDFISH jaws have much longer width, to-figure-out.


        //INCREASE-SLICK-ORIGINAL
        pullFishHeadImageSubset(FishStateManager.BodySize.INCREASE, FishStateManager.BodyTexture.SLICK,
                FishStateManager.Jaws.ORIGINAL, 236, 153, 15, 15);
        //INCREASE-SLICK-KURASELACHES
        pullFishHeadImageSubset(FishStateManager.BodySize.INCREASE, FishStateManager.BodyTexture.SLICK,
                FishStateManager.Jaws.KURASELACHES, 236, 198, 15, 15);
        //INCREASE-SLICK-ZINICHTHY
        pullFishHeadImageSubset(FishStateManager.BodySize.INCREASE, FishStateManager.BodyTexture.SLICK,
                FishStateManager.Jaws.ZINICHTHY, 281, 153, 15, 15);
        //INCREASE-SLICK-SWORDFISH
                // TODO: INCREASE-SHELL-SWORDFISH jaws have much longer width, to-figure-out.

        //INCREASE-SCALY-ORIGINAL
        pullFishHeadImageSubset(FishStateManager.BodySize.INCREASE, FishStateManager.BodyTexture.SCALY,
                FishStateManager.Jaws.ORIGINAL, 338, 153, 15, 15);
        //INCREASE-SCALY-KURASELACHES
        pullFishHeadImageSubset(FishStateManager.BodySize.INCREASE, FishStateManager.BodyTexture.SCALY,
                FishStateManager.Jaws.KURASELACHES, 338, 198, 15, 15);
        //INCREASE-SCALY-ZINICHTHY
        pullFishHeadImageSubset(FishStateManager.BodySize.INCREASE, FishStateManager.BodyTexture.SCALY,
                FishStateManager.Jaws.ZINICHTHY, 383, 153, 15, 15);
        //INCREASE-SCALY-SWORDFISH
                // TODO: INCREASE-SHELL-SWORDFISH jaws have much longer width, to-figure-out.

        //INCREASE-SHELL-ORIGINAL
        pullFishHeadImageSubset(FishStateManager.BodySize.INCREASE, FishStateManager.BodyTexture.SHELL,
                FishStateManager.Jaws.ORIGINAL, 440, 153, 15, 15);
        //INCREASE-SHELL-KURASELACHES
        pullFishHeadImageSubset(FishStateManager.BodySize.INCREASE, FishStateManager.BodyTexture.SHELL,
                FishStateManager.Jaws.KURASELACHES, 440, 198, 15, 15);
        //INCREASE-SHELL-ZINICHTHY
        pullFishHeadImageSubset(FishStateManager.BodySize.INCREASE, FishStateManager.BodyTexture.SHELL,
                FishStateManager.Jaws.ZINICHTHY, 485, 153, 15, 15);
        //INCREASE-SHELL-SWORDFISH
                // TODO: INCREASE-SHELL-SWORDFISH jaws have much longer width, to-figure-out.




        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // ENTITY - Chapter 1: FISH BODY (AND ATTACHMENTS)
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //DECREASE-SLICK-ORIGINAL
        pullFishBodyImageSubset(FishStateManager.BodySize.DECREASE, FishStateManager.BodyTexture.SLICK,
                FishStateManager.FinPectoral.ORIGINAL, 6, 358);





        // BACKGROUND - IntroState
        chapter1Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(6, 6, 133+1, 85+1);
        chapter2Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(6, 232, 133+1, 85+1);
        chapter3Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(11, 756, 133+1, 85+1);
        chapter4Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(19, 1283, 133+1, 85+1);
        chapter5Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(12, 1933, 133+1, 85+1);

        // BACKGROUND - ChapterState
        chapter1Chapter = spriteSheetChapterIntroAndWorldMap.getSubimage(146, 6, 255+1, 221+1);
        chapter2Chapter = spriteSheetChapterIntroAndWorldMap.getSubimage(146, 232, 255+1, 211+1);
        chapter3Chapter = spriteSheetChapterIntroAndWorldMap.getSubimage(152, 755, 255+1, 211+1);
        chapter4Chapter = spriteSheetChapterIntroAndWorldMap.getSubimage(159, 1283, 255+1, 221+1);
        chapter5Chapter = spriteSheetChapterIntroAndWorldMap.getSubimage(151, 1934, 255+1, 221+1);

        // BACKGROUND - WorldMapState
        chapter1WorldMap = spriteSheetChapterIntroAndWorldMap.getSubimage(407, 6, 255+1, 221+1);
        chapter2WorldMap = spriteSheetChapterIntroAndWorldMap.getSubimage(408, 230, 243+1, 223+1);
        chapter3WorldMap = spriteSheetChapterIntroAndWorldMap.getSubimage(411, 755, 255+1, 221+1);
        chapter4WorldMap = spriteSheetChapterIntroAndWorldMap.getSubimage(420, 1283, 255+1, 221+1);
        chapter5WorldMap = spriteSheetChapterIntroAndWorldMap.getSubimage(411, 1934, 255+1, 221+1);

        // TRANSPARENT LAYER - WAVE
        chapter1Wave = spriteSheetChapterIntroAndWorldMap.getSubimage(667, 4, 255+1, 223+1);
        chapter2Wave = spriteSheetChapterIntroAndWorldMap.getSubimage(657, 231, 255+1, 223+1);
        chapter3Wave = spriteSheetChapterIntroAndWorldMap.getSubimage(674, 754, 255+1, 223+1);
        chapter4Wave = spriteSheetChapterIntroAndWorldMap.getSubimage(682, 1282, 255+1, 223+1);
        chapter5Wave = spriteSheetChapterIntroAndWorldMap.getSubimage(671, 1932, 255+1, 223+1);

        // OVERWORLD - TOKEN
        upOverworld0 = spriteSheetChapter1Creatures.getSubimage(149, 113, 9, 16);
        upOverworld1 = spriteSheetChapter1Creatures.getSubimage(158, 113, 9, 16);
        downOverworld0 = spriteSheetChapter1Creatures.getSubimage(148, 129, 9, 16);
        downOverworld1 = spriteSheetChapter1Creatures.getSubimage(158, 129, 9, 16);
        leftOverworld0 = spriteSheetChapter1Creatures.getSubimage(182, 113, 16, 9);
        leftOverworld1 = spriteSheetChapter1Creatures.getSubimage(199, 113, 16, 9);
        rightOverworld0 = spriteSheetChapter1Creatures.getSubimage(182, 123, 16, 9);
        rightOverworld1 = spriteSheetChapter1Creatures.getSubimage(199, 123, 16, 9);

    }

    public static void pullFishHeadImageSubset(FishStateManager.BodySize bodySize, FishStateManager.BodyTexture bodyTexture,
                                               FishStateManager.Jaws jaws, int xStart, int yStart, int width, int height) {

        //minus 1 because ActionState.NONE doesn't count.
        for (int row = 0; row < (FishStateManager.ActionState.values().length-1); row++) {

            int xStartLocal = xStart;
            int yStartLocal = yStart;
            int widthLocal = width;
            int heightLocal = height;

            FishStateManager.ActionState[] actionsArray = FishStateManager.ActionState.values();

            switch (actionsArray[row]) {

                case EAT:
                    yStartLocal = yStartLocal + (row * heightLocal);

                    for (int col = 0; col < eatFrames[0][0][0][0].length; col++) {
                        eatFrames[bodySize.ordinal()]
                                [bodyTexture.ordinal()]
                                [jaws.ordinal()]
                                [FishStateManager.ActionState.EAT.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, widthLocal, heightLocal);

                        xStartLocal = xStartLocal + widthLocal;
                    }

                    break;

                case BITE:
                    yStartLocal = yStartLocal + (row * heightLocal);

                    for (int col = 0; col < biteFrames[0][0][0][1].length; col++) {
                        biteFrames[bodySize.ordinal()]
                                [bodyTexture.ordinal()]
                                [jaws.ordinal()]
                                [FishStateManager.ActionState.BITE.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, widthLocal, heightLocal);

                        xStartLocal = xStartLocal + widthLocal;
                    }

                    break;

                case HURT:
                    yStartLocal = yStartLocal + (row * heightLocal);

                    for (int col = 0; col < hurtFrames[0][0][0][2].length; col++) {
                        hurtFrames[bodySize.ordinal()]
                                [bodyTexture.ordinal()]
                                [jaws.ordinal()]
                                [FishStateManager.ActionState.HURT.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, widthLocal, heightLocal);

                        xStartLocal = xStartLocal + widthLocal;
                    }

                    break;

                default:
                    break;

            }

        }
    }

    public static void pullFishBodyImageSubset(FishStateManager.BodySize bodySize, FishStateManager.BodyTexture bodyTexture,
                                               FishStateManager.FinPectoral finPectoral, int xStart, int yStart) {

        FishStateManager.Tail[] tailsArray = FishStateManager.Tail.values();

        for (int row = 0; row < FishStateManager.Tail.values().length; row++) {

            int xStartLocal = xStart;
            int yStartLocal = yStart;
            int width = 17;
            int height = 14;

            switch (tailsArray[row]) {

                case ORIGINAL:
                    for (int col = 0; col < 3; col++) {
                        tailOriginal[bodySize.ordinal()]
                                [bodyTexture.ordinal()]
                                [finPectoral.ordinal()]
                                [FishStateManager.Tail.ORIGINAL.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 18;
                            width = width + 7;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 26;
                            width = width - 7;
                            height = height + 2;
                        }
                    }

                    break;

                case COELAFISH:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 2;
                            yStartLocal = yStartLocal + 19;
                            width = width - 2;
                            height = height + 1;
                        }

                        tailCoelafish[bodySize.ordinal()]
                                [bodyTexture.ordinal()]
                                [finPectoral.ordinal()]
                                [FishStateManager.Tail.COELAFISH.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 20;
                            width = width + 5;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 24;
                            width = width - 5;
                            height = height + 2;
                        }
                    }

                    break;

                case TERATISU:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 1;
                            yStartLocal = yStartLocal + 39;
                            width = width - 1;
                            height = height + 1;
                        }

                        tailTeratisu[bodySize.ordinal()]
                                [bodyTexture.ordinal()]
                                [finPectoral.ordinal()]
                                [FishStateManager.Tail.TERATISU.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 21;
                            width = width + 4;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 23;
                            width = width - 4;
                            height = height + 2;
                        }
                    }

                    break;

                case ZINICHTHY:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal - 1;
                            yStartLocal = yStartLocal + 59;
                            width = width + 1;
                            height = height + 1;
                        }

                        tailZinichthy[bodySize.ordinal()]
                                [bodyTexture.ordinal()]
                                [finPectoral.ordinal()]
                                [FishStateManager.Tail.ZINICHTHY.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 19;
                            width = width + 6;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 25;
                            width = width - 6;
                            height = height + 2;
                        }
                    }

                    break;

                case KURASELACHE:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 0;
                            yStartLocal = yStartLocal + 79;
                            width = width + 0;
                            height = height + 2;
                        }

                        tailKuraselache[bodySize.ordinal()]
                                [bodyTexture.ordinal()]
                                [finPectoral.ordinal()]
                                [FishStateManager.Tail.KURASELACHE.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 20;
                            width = width + 5;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 24;
                            width = width - 5;
                            height = height + 2;
                        }
                    }

                    break;

                default:
                    break;

            }

        }

    }

}
