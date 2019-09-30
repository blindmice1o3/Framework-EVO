package com.evo.gfx;

import com.evo.Utils;
import com.evo.entities.moveable.player.FishStateManager;
import com.evo.tiles.Tile;

import java.awt.image.BufferedImage;
import java.util.Map;

public class Assets {

    // FONT (from pokemon yellow sprite sheet for battle)
    public static BufferedImage fontSpriteSheet;
    public static BufferedImage pokeballToken;
    public static Map<String, BufferedImage> fontHashMap;

    // SPRITE SHEETS
    public static BufferedImage spriteSheetChapterIntroAndWorldMap, spriteSheetChapter1Creatures, spriteSheetStartMenu;

    // BACKGROUND - IntroState
    public static BufferedImage chapter1Intro, chapter2Intro, chapter3Intro, chapter4Intro, chapter5Intro;

    // BACKGROUND - ChapterState
    public static BufferedImage chapter1Chapter, chapter2Chapter, chapter3Chapter, chapter4Chapter, chapter5Chapter;

    // BACKGROUND - WorldMapState
    public static BufferedImage chapter1WorldMap, chapter2WorldMap, chapter3WorldMap, chapter4WorldMap, chapter5WorldMap;

    // TRANSPARENT LAYER - WAVE
    public static BufferedImage chapter1Wave, chapter2Wave, chapter3Wave, chapter4Wave, chapter5Wave;
    public static BufferedImage[] waveAnimationArray;

    // OVERWORLD - TOKEN
    public static BufferedImage upOverworld0, upOverworld1, downOverworld0, downOverworld1,
            leftOverworld0, leftOverworld1, rightOverworld0, rightOverworld1;

    // ENTITIES.MOVEABLE - Chapter 1: FISH HEAD (AND ATTACHMENTS)
    public static BufferedImage[][][][][] eatFrames, biteFrames, hurtFrames;

    // ENTITIES.MOVEABLE - Chapter 1: FISH BODY (AND ATTACHMENTS)
    public static BufferedImage[][][][] tailOriginal, tailCoelafish, tailTeratisu, tailZinichthy, tailKuraselache;

    // BACKGROUND - MainMenuState
    public static BufferedImage mainMenu, mainMenuCapability;

    // GameStage("") - BACKGROUND
    public static BufferedImage chapter1GameStage, brickGreen, coralPink1, coralPink2, coralPink3, coinGameObject;

    // GameStage("buying_lighter")
    public static BufferedImage spriteSheetFrogger, backgroundFrogger, winningRow, startingRow,
            logLarge, logMedium, logSmall, parrotRight, carPinkLeft, carWhiteRight, carYellowLeft,
            seaLionRight, bigRigLeft;
    public static BufferedImage[] frogRight, frogLeft, frogUp, frogDown, turtleLeft, crocRight, snowPlowRight,
            snakeLeft, frogNPCRight, frogNPCLeft;

    // ENTITIES.NON_MOVEABLE - Chapter 1: KELP
    public static BufferedImage[] kelpSolid;

    // ENTITIES.MOVEABLE - Chapter 1: ENEMIES
    public static BufferedImage[] seaJelly, eel;

    // ITEMS - Chapter 1: MEAT
    public static BufferedImage meat;

    // Initialization of sprites.
    public static void init() {
        // SPRITE SHEETS
        spriteSheetChapterIntroAndWorldMap = Utils.loadImage("/SNES - EVO Search for Eden - Maps & Chapter Images.png");
        spriteSheetChapter1Creatures = Utils.loadImage("/SNES - EVO Search for Eden - Chapter 1 Creatures.png");
        spriteSheetStartMenu = Utils.loadImage("/EVO_select_menu_English.png");
        spriteSheetFrogger = Utils.loadImage("/sprite sheet - frogger/33Hnhw9.png");
        backgroundFrogger = Utils.loadImage("/sprite sheet - frogger/0Meo6sH.png");

        // GameStage(Identifier.FROGGER)
        winningRow = spriteSheetFrogger.getSubimage(0, 55,  399,52);
        startingRow = spriteSheetFrogger.getSubimage(0, 119, 399, 34);
        logLarge = spriteSheetFrogger.getSubimage(7, 166, 177,21);
        logMedium = spriteSheetFrogger.getSubimage(7, 198, 116, 21);
        logSmall = spriteSheetFrogger.getSubimage(7, 230, 84, 21);
        parrotRight = spriteSheetFrogger.getSubimage(140, 236, 16, 16);
        carPinkLeft = spriteSheetFrogger.getSubimage(10, 267, 28, 20);
        carWhiteRight = spriteSheetFrogger.getSubimage(46, 265, 24, 24);
        carYellowLeft = spriteSheetFrogger.getSubimage(82, 264, 24, 26);
        seaLionRight = spriteSheetFrogger.getSubimage(116, 271, 32, 18);
        bigRigLeft = spriteSheetFrogger.getSubimage(106, 302, 46, 18);

        frogRight = new BufferedImage[2];
        frogRight[0] = spriteSheetFrogger.getSubimage(13, 334, 17, 23);
        frogRight[1] = spriteSheetFrogger.getSubimage(43, 335, 25, 22);
        frogLeft = new BufferedImage[2];
        frogLeft[0] = spriteSheetFrogger.getSubimage(83, 335, 17, 23);
        frogLeft[1] = spriteSheetFrogger.getSubimage(112, 338, 25, 22);
        frogUp = new BufferedImage[2];
        frogUp[0] = spriteSheetFrogger.getSubimage(12, 369, 23, 17);
        frogUp[1] = spriteSheetFrogger.getSubimage(46, 366, 22, 25);
        frogDown = new BufferedImage[2];
        frogDown[0] = spriteSheetFrogger.getSubimage(80, 369, 23, 17);
        frogDown[1] = spriteSheetFrogger.getSubimage(114, 366, 22, 25);
        turtleLeft = new BufferedImage[5];
        turtleLeft[0] = spriteSheetFrogger.getSubimage(15, 406, 31, 22);
        turtleLeft[1] = spriteSheetFrogger.getSubimage(54, 407, 31, 22);
        turtleLeft[2] = spriteSheetFrogger.getSubimage(94, 408, 29, 19);
        turtleLeft[3] = spriteSheetFrogger.getSubimage(134, 408, 29, 21);
        turtleLeft[4] = spriteSheetFrogger.getSubimage(179, 408, 26, 21);
        crocRight = new BufferedImage[2];
        crocRight[0] = spriteSheetFrogger.getSubimage(156, 332, 89, 29);
        crocRight[1] = spriteSheetFrogger.getSubimage(156, 373, 89, 21);
        snowPlowRight = new BufferedImage[3];
        snowPlowRight[0] = spriteSheetFrogger.getSubimage(11, 301, 23, 21);
        snowPlowRight[1] = spriteSheetFrogger.getSubimage(42, 301, 23, 21);
        snowPlowRight[2] = spriteSheetFrogger.getSubimage(73, 301, 23, 21);
        snakeLeft = new BufferedImage[4];
        snakeLeft[0] = spriteSheetFrogger.getSubimage(184, 226, 38, 10);
        snakeLeft[1] = spriteSheetFrogger.getSubimage(185, 251, 37, 13);
        snakeLeft[2] = spriteSheetFrogger.getSubimage(184, 276, 38, 16);
        snakeLeft[3] = spriteSheetFrogger.getSubimage(185, 304, 37, 13);
        frogNPCRight = new BufferedImage[2];
        frogNPCRight[0] = spriteSheetFrogger.getSubimage(236, 407, 20, 24);
        frogNPCRight[1] = spriteSheetFrogger.getSubimage(270, 409, 27, 24);
        frogNPCLeft = new BufferedImage[2];
        frogNPCLeft[0] = spriteSheetFrogger.getSubimage(315, 407, 19, 24);
        frogNPCLeft[1] = spriteSheetFrogger.getSubimage(348, 409, 28, 23);


        // ENTITIES.MOVEABLE - Chapter 1: FISH HEAD (AND ATTACHMENTS)
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

        // ENTITIES.MOVEABLE - Chapter 1: FISH BODY (AND ATTACHMENTS)
        tailOriginal = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.FinPectoral.values().length]
                //[FishStateManager.Tail.values().length]
                [3];

        tailCoelafish = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.FinPectoral.values().length]
                //[FishStateManager.Tail.values().length]
                [3];

        tailTeratisu = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.FinPectoral.values().length]
                //[FishStateManager.Tail.values().length]
                [3];

        tailZinichthy = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.FinPectoral.values().length]
                //[FishStateManager.Tail.values().length]
                [3];

        tailKuraselache = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.FinPectoral.values().length]
                //[FishStateManager.Tail.values().length]
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




        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // ENTITIES.MOVEABLE - Chapter 1: FISH BODY (AND ATTACHMENTS)
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //DECREASE-SLICK-ORIGINAL
        pullFishBodyImageSubsetDecreaseOriginal(FishStateManager.BodyTexture.SLICK, 6, 358);
        //DECREASE-SLICK-COELAFISH
        pullFishBodyImageSubsetDecreaseCoelafish(FishStateManager.BodyTexture.SLICK, 2, 457);
        //DECREASE-SLICK-TACKLE
        pullFishBodyImageSubsetDecreaseTackle(FishStateManager.BodyTexture.SLICK, 6, 566);

        //DECREASE-SCALY-ORIGINAL
        pullFishBodyImageSubsetDecreaseOriginal(FishStateManager.BodyTexture.SCALY, 84, 358);
        //DECREASE-SCALY-COELAFISH
        pullFishBodyImageSubsetDecreaseCoelafish(FishStateManager.BodyTexture.SCALY, 80, 457);
        //DECREASE-SCALY-TACKLE
        pullFishBodyImageSubsetDecreaseTackle(FishStateManager.BodyTexture.SCALY, 84, 566);

        //DECREASE-SHELL-ORIGINAL
        pullFishBodyImageSubsetDecreaseOriginal(FishStateManager.BodyTexture.SHELL, 163, 358);
        //DECREASE-SHELL-COELAFISH
        pullFishBodyImageSubsetDecreaseCoelafish(FishStateManager.BodyTexture.SHELL, 159, 457);
        //DECREASE-SHELL-TACKLE
        pullFishBodyImageSubsetDecreaseTackle(FishStateManager.BodyTexture.SHELL, 163, 566);


        //INCREASE-SLICK-ORIGINAL
        pullFishBodyImageSubsetIncreaseOriginal(FishStateManager.BodyTexture.SLICK, 237, 298);
        //INCREASE-SLICK-COELAFISH
        pullFishBodyImageSubsetIncreaseCoelafish(FishStateManager.BodyTexture.SLICK, 237, 412);
        //INCREASE-SLICK-TACKLE
        pullFishBodyImageSubsetIncreaseTackle(FishStateManager.BodyTexture.SLICK, 237, 541);

        //INCREASE-SCALY-ORIGINAL
        pullFishBodyImageSubsetIncreaseOriginal(FishStateManager.BodyTexture.SCALY, 339, 298);
        //INCREASE-SCALY-COELAFISH
        pullFishBodyImageSubsetIncreaseCoelafish(FishStateManager.BodyTexture.SCALY, 339, 412);
        //INCREASE-SCALY-TACKLE
        pullFishBodyImageSubsetIncreaseTackle(FishStateManager.BodyTexture.SCALY, 339, 541);

        //INCREASE-SHELL-ORIGINAL
        pullFishBodyImageSubsetIncreaseOriginal(FishStateManager.BodyTexture.SHELL, 441, 298);
        //INCREASE-SHELL-COELAFISH
        pullFishBodyImageSubsetIncreaseCoelafish(FishStateManager.BodyTexture.SHELL, 441, 412);
        //INCREASE-SHELL-TACKLE
        pullFishBodyImageSubsetIncreaseTackle(FishStateManager.BodyTexture.SHELL, 441, 541);



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

        waveAnimationArray = new BufferedImage[8];
        waveAnimationArray[0] = chapter1Wave;
        waveAnimationArray[1] = chapter2Wave;
        waveAnimationArray[2] = chapter3Wave;
        waveAnimationArray[3] = chapter4Wave;
        waveAnimationArray[4] = chapter5Wave;
        waveAnimationArray[5] = chapter4Wave;
        waveAnimationArray[6] = chapter3Wave;
        waveAnimationArray[7] = chapter2Wave;

        // OVERWORLD - TOKEN
        upOverworld0 = spriteSheetChapter1Creatures.getSubimage(149, 113, 9, 16);
        upOverworld1 = spriteSheetChapter1Creatures.getSubimage(158, 113, 9, 16);
        downOverworld0 = spriteSheetChapter1Creatures.getSubimage(148, 129, 9, 16);
        downOverworld1 = spriteSheetChapter1Creatures.getSubimage(158, 129, 9, 16);
        leftOverworld0 = spriteSheetChapter1Creatures.getSubimage(182, 113, 16, 9);
        leftOverworld1 = spriteSheetChapter1Creatures.getSubimage(199, 113, 16, 9);
        rightOverworld0 = spriteSheetChapter1Creatures.getSubimage(182, 123, 16, 9);
        rightOverworld1 = spriteSheetChapter1Creatures.getSubimage(199, 123, 16, 9);



        // BACKGROUND - MainMenuState
        mainMenu = spriteSheetStartMenu.getSubimage(6, 11, 128, 52);
        mainMenuCapability = Utils.loadImage("/MainMenuState's CAPABILITY.png");


        // BACKGROUND - GameStageState
        chapter1GameStage = Utils.loadImage("/mario-7-2.gif");
        brickGreen = chapter1GameStage.getSubimage(0, 200, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        coralPink1 = chapter1GameStage.getSubimage(176, 184, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        coralPink2 = chapter1GameStage.getSubimage(1632, 120, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        coralPink3 = chapter1GameStage.getSubimage(2384, 184, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        coinGameObject = chapter1GameStage.getSubimage(224, 184, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);


        // ENTITIES.NON_MOVEABLE - KELP
        kelpSolid = new BufferedImage[4];
        kelpSolid[0] = spriteSheetChapter1Creatures.getSubimage(557, 718, 12, 32);
        kelpSolid[1] = spriteSheetChapter1Creatures.getSubimage(569, 718, 12, 32);
        kelpSolid[2] = spriteSheetChapter1Creatures.getSubimage(582, 718, 12, 32);
        kelpSolid[3] = spriteSheetChapter1Creatures.getSubimage(594, 718, 12, 32);

        // ENTITIES.MOVEABLE - ENEMIES
        seaJelly = new BufferedImage[8];
        seaJelly[0] = spriteSheetChapter1Creatures.getSubimage(552, 29, 16, 32); //IDLE0 (forward-facing)
        seaJelly[1] = spriteSheetChapter1Creatures.getSubimage(568, 29, 16, 32); //IDLE1 (forward-facing)
        seaJelly[2] = spriteSheetChapter1Creatures.getSubimage(584, 29, 16, 32); //IDLE2 (forward-facing)
        seaJelly[3] = spriteSheetChapter1Creatures.getSubimage(599, 27, 16, 32); //ATTACK0 (forward-facing)
        seaJelly[4] = spriteSheetChapter1Creatures.getSubimage(619, 29, 16, 32); //ATTACK1 (left)
        seaJelly[5] = spriteSheetChapter1Creatures.getSubimage(639, 34, 32, 32); //ATTACK2 (left)
        seaJelly[6] = spriteSheetChapter1Creatures.getSubimage(674, 32, 32, 32); //ATTACK3 (left)
        seaJelly[7] = spriteSheetChapter1Creatures.getSubimage(714, 28, 16, 32); //HURT0 (forward-facing)

        eel = new BufferedImage[7];
        eel[0] = spriteSheetChapter1Creatures.getSubimage(554, 70, 32, 16); //IDLE0 (left)
        eel[1] = spriteSheetChapter1Creatures.getSubimage(587, 70, 32, 16); //IDLE1 (left)
        eel[2] = spriteSheetChapter1Creatures.getSubimage(620, 70, 32, 16); //IDLE2 (left)
        eel[3] = spriteSheetChapter1Creatures.getSubimage(654, 70, 32, 16); //IDLE3 (left)
        eel[4] = spriteSheetChapter1Creatures.getSubimage(687, 70, 28, 16); //TURN (right-turning-left)
        eel[5] = spriteSheetChapter1Creatures.getSubimage(716, 62, 16, 32); //ATTACK0 (left)
        eel[6] = spriteSheetChapter1Creatures.getSubimage(732, 69, 32, 16); //HURT0 (left)

        // ITEMS - MEAT
        meat = spriteSheetChapter1Creatures.getSubimage(594, 697, 16, 16);


        // FONT (from pokemon yellow sprite sheet for battle)
        fontSpriteSheet = Utils.loadImage("/Game Boy GBC - Pokemon Yellow - Battle Interface.png");
        pokeballToken = fontSpriteSheet.getSubimage(324, 269, 7, 7);
        fontHashMap = FontGrabber.initFont();
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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

    public static void pullFishBodyImageSubsetDecreaseOriginal(FishStateManager.BodyTexture bodyTexture,
                                                               int xStart, int yStart) {

        FishStateManager.Tail[] tailsArray = FishStateManager.Tail.values();

        for (int row = 0; row < FishStateManager.Tail.values().length; row++) {

            int xStartLocal = xStart;
            int yStartLocal = yStart;
            int width = 17;
            int height = 14;

            switch (tailsArray[row]) {

                case ORIGINAL:
                    for (int col = 0; col < 3; col++) {
                        tailOriginal[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.ORIGINAL.ordinal()]
                                //[FishStateManager.Tail.ORIGINAL.ordinal()]
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

                        tailCoelafish[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.ORIGINAL.ordinal()]
                                //[FishStateManager.Tail.COELAFISH.ordinal()]
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

                        tailTeratisu[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.ORIGINAL.ordinal()]
                                //[FishStateManager.Tail.TERATISU.ordinal()]
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

                        tailZinichthy[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.ORIGINAL.ordinal()]
                                //[FishStateManager.Tail.ZINICHTHY.ordinal()]
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

                        tailKuraselache[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.ORIGINAL.ordinal()]
                                //[FishStateManager.Tail.KURASELACHE.ordinal()]
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

    public static void pullFishBodyImageSubsetDecreaseCoelafish(FishStateManager.BodyTexture bodyTexture,
                                                               int xStart, int yStart) {

        FishStateManager.Tail[] tailsArray = FishStateManager.Tail.values();

        for (int row = 0; row < FishStateManager.Tail.values().length; row++) {

            int xStartLocal = xStart;
            int yStartLocal = yStart;
            int width = 21;
            int height = 16;

            switch (tailsArray[row]) {

                case ORIGINAL:
                    for (int col = 0; col < 3; col++) {
                        tailOriginal[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.COELAFISH.ordinal()]
                                //[FishStateManager.Tail.ORIGINAL.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 22;
                            width = width + 3;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 26;
                            width = width - 6;
                            height = height + 2;
                        }
                    }

                    break;

                case COELAFISH:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 0;
                            yStartLocal = yStartLocal + 19;
                            width = width + 0;
                            height = height + 1;
                        }

                        tailCoelafish[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.COELAFISH.ordinal()]
                                //[FishStateManager.Tail.COELAFISH.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 26;
                            width = width - 1;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 24;
                            width = width - 4;
                            height = height + 2;
                        }
                    }

                    break;

                case TERATISU:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 0;
                            yStartLocal = yStartLocal + 43;
                            width = width + 0;
                            height = height + 1;
                        }

                        tailTeratisu[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.COELAFISH.ordinal()]
                                //[FishStateManager.Tail.TERATISU.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 26;
                            width = width - 1;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 23;
                            width = width - 3;
                            height = height + 2;
                        }
                    }

                    break;

                case ZINICHTHY:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 0;
                            yStartLocal = yStartLocal + 65;
                            width = width + 0;
                            height = height + 1;
                        }

                        tailZinichthy[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.COELAFISH.ordinal()]
                                //[FishStateManager.Tail.ZINICHTHY.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 22;
                            width = width + 3;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 25;
                            width = width - 5;
                            height = height + 2;
                        }
                    }

                    break;

                case KURASELACHE:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 0;
                            yStartLocal = yStartLocal + 87;
                            width = width + 0;
                            height = height + 1;
                        }

                        tailKuraselache[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.COELAFISH.ordinal()]
                                //[FishStateManager.Tail.KURASELACHE.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 24;
                            width = width + 1;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 24;
                            width = width - 4;
                            height = height + 2;
                        }
                    }

                    break;

                default:
                    break;

            }

        }

    }

    public static void pullFishBodyImageSubsetDecreaseTackle(FishStateManager.BodyTexture bodyTexture,
                                                               int xStart, int yStart) {

        FishStateManager.Tail[] tailsArray = FishStateManager.Tail.values();

        for (int row = 0; row < FishStateManager.Tail.values().length; row++) {

            int xStartLocal = xStart;
            int yStartLocal = yStart;
            int width = 17;
            int height = 13;

            switch (tailsArray[row]) {

                case ORIGINAL:
                    for (int col = 0; col < 3; col++) {
                        tailOriginal[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.TACKLE.ordinal()]
                                //[FishStateManager.Tail.ORIGINAL.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 18;
                            width = width + 7;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 26;
                            width = width - 5;
                            height = height + 0;
                        }
                    }

                    break;

                case COELAFISH:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 1;
                            yStartLocal = yStartLocal + 15;
                            width = width - 1;
                            height = height + 2;
                        }

                        tailCoelafish[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.TACKLE.ordinal()]
                                //[FishStateManager.Tail.COELAFISH.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 21;
                            width = width + 4;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 24;
                            width = width - 3;
                            height = height - 1;
                        }
                    }

                    break;

                case TERATISU:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 1;
                            yStartLocal = yStartLocal + 32;
                            width = width - 1;
                            height = height + 2;
                        }

                        tailTeratisu[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.TACKLE.ordinal()]
                                //[FishStateManager.Tail.TERATISU.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 21;
                            width = width + 4;
                            height = height + 0;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 23;
                            width = width - 2;
                            height = height + 0;
                        }
                    }

                    break;

                case ZINICHTHY:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal - 1;
                            yStartLocal = yStartLocal + 48;
                            width = width + 1;
                            height = height + 1;
                        }

                        tailZinichthy[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.TACKLE.ordinal()]
                                //[FishStateManager.Tail.ZINICHTHY.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 19;
                            width = width + 6;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 25;
                            width = width - 4;
                            height = height + 0;
                        }
                    }

                    break;

                case KURASELACHE:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 0;
                            yStartLocal = yStartLocal + 64;
                            width = width + 0;
                            height = height + 3;
                        }

                        tailKuraselache[FishStateManager.BodySize.DECREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.TACKLE.ordinal()]
                                //[FishStateManager.Tail.KURASELACHE.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 20;
                            width = width + 5;
                            height = height + 0;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 24;
                            width = width - 3;
                            height = height + 0;
                        }
                    }

                    break;

                default:
                    break;

            }

        }

    }


    public static void pullFishBodyImageSubsetIncreaseOriginal(FishStateManager.BodyTexture bodyTexture,
                                                               int xStart, int yStart) {

        FishStateManager.Tail[] tailsArray = FishStateManager.Tail.values();

        for (int row = 0; row < FishStateManager.Tail.values().length; row++) {

            int xStartLocal = xStart;
            int yStartLocal = yStart;
            int width = 25;
            int height = 17;

            switch (tailsArray[row]) {

                case ORIGINAL:
                    for (int col = 0; col < 3; col++) {
                        tailOriginal[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.ORIGINAL.ordinal()]
                                //[FishStateManager.Tail.ORIGINAL.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 26;
                            width = width + 7;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 34;
                            width = width - 7;
                            height = height + 3;
                        }
                    }

                    break;

                case COELAFISH:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 2;
                            yStartLocal = yStartLocal + 22;
                            width = width - 2;
                            height = height + 1;
                        }

                        tailCoelafish[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.ORIGINAL.ordinal()]
                                //[FishStateManager.Tail.COELAFISH.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 28;
                            width = width + 5;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 32;
                            width = width - 5;
                            height = height + 3;
                        }
                    }

                    break;

                case TERATISU:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 1;
                            yStartLocal = yStartLocal + 45;
                            width = width - 1;
                            height = height + 1;
                        }

                        tailTeratisu[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.ORIGINAL.ordinal()]
                                //[FishStateManager.Tail.TERATISU.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 29;
                            width = width + 4;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 31;
                            width = width - 4;
                            height = height + 3;
                        }
                    }

                    break;

                case ZINICHTHY:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal - 1;
                            yStartLocal = yStartLocal + 68;
                            width = width + 1;
                            height = height + 1;
                        }

                        tailZinichthy[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.ORIGINAL.ordinal()]
                                //[FishStateManager.Tail.ZINICHTHY.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 27;
                            width = width + 6;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 33;
                            width = width - 6;
                            height = height + 3;
                        }
                    }

                    break;

                case KURASELACHE:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 0;
                            yStartLocal = yStartLocal + 91;
                            width = width + 0;
                            height = height + 1;
                        }

                        tailKuraselache[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.ORIGINAL.ordinal()]
                                //[FishStateManager.Tail.KURASELACHE.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 28;
                            width = width + 5;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 32;
                            width = width - 5;
                            height = height + 3;
                        }
                    }

                    break;

                default:
                    break;

            }

        }

    }


    public static void pullFishBodyImageSubsetIncreaseCoelafish(FishStateManager.BodyTexture bodyTexture,
                                                               int xStart, int yStart) {

        FishStateManager.Tail[] tailsArray = FishStateManager.Tail.values();

        for (int row = 0; row < FishStateManager.Tail.values().length; row++) {

            int xStartLocal = xStart;
            int yStartLocal = yStart;
            int width = 25;
            int height = 20;

            switch (tailsArray[row]) {

                case ORIGINAL:
                    for (int col = 0; col < 3; col++) {
                        tailOriginal[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.COELAFISH.ordinal()]
                                //[FishStateManager.Tail.ORIGINAL.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 26;
                            width = width + 7;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 34;
                            width = width - 6;
                            height = height + 2;
                        }
                    }

                    break;

                case COELAFISH:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 2;
                            yStartLocal = yStartLocal + 25;
                            width = width - 2;
                            height = height + 1;
                        }

                        tailCoelafish[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.COELAFISH.ordinal()]
                                //[FishStateManager.Tail.COELAFISH.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 28;
                            width = width + 5;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 32;
                            width = width - 4;
                            height = height + 2;
                        }
                    }

                    break;

                case TERATISU:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 1;
                            yStartLocal = yStartLocal + 51;
                            width = width - 1;
                            height = height + 1;
                        }

                        tailTeratisu[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.COELAFISH.ordinal()]
                                //[FishStateManager.Tail.TERATISU.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 29;
                            width = width + 4;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 31;
                            width = width - 3;
                            height = height + 2;
                        }
                    }

                    break;

                case ZINICHTHY:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal - 1;
                            yStartLocal = yStartLocal + 77;
                            width = width + 1;
                            height = height + 1;
                        }

                        tailZinichthy[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.COELAFISH.ordinal()]
                                //[FishStateManager.Tail.ZINICHTHY.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 27;
                            width = width + 6;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 33;
                            width = width - 5;
                            height = height + 2;
                        }
                    }

                    break;

                case KURASELACHE:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 0;
                            yStartLocal = yStartLocal + 103;
                            width = width + 0;
                            height = height + 1;
                        }

                        tailKuraselache[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.COELAFISH.ordinal()]
                                //[FishStateManager.Tail.KURASELACHE.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 28;
                            width = width + 5;
                            height = height + 2;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 32;
                            width = width - 4;
                            height = height + 2;
                        }
                    }

                    break;

                default:
                    break;

            }

        }

    }

    public static void pullFishBodyImageSubsetIncreaseTackle(FishStateManager.BodyTexture bodyTexture,
                                                               int xStart, int yStart) {

        FishStateManager.Tail[] tailsArray = FishStateManager.Tail.values();

        for (int row = 0; row < FishStateManager.Tail.values().length; row++) {

            int xStartLocal = xStart;
            int yStartLocal = yStart;
            int width = 25;
            int height = 16;

            switch (tailsArray[row]) {

                case ORIGINAL:
                    for (int col = 0; col < 3; col++) {
                        tailOriginal[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.TACKLE.ordinal()]
                                //[FishStateManager.Tail.ORIGINAL.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 26;
                            width = width + 7;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 34;
                            width = width - 5;
                            height = height + 2;
                        }
                    }

                    break;

                case COELAFISH:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 2;
                            yStartLocal = yStartLocal + 20;
                            width = width - 2;
                            height = height + 1;
                        }

                        tailCoelafish[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.TACKLE.ordinal()]
                                //[FishStateManager.Tail.COELAFISH.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 28;
                            width = width + 5;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 32;
                            width = width - 3;
                            height = height + 2;
                        }
                    }

                    break;

                case TERATISU:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 1;
                            yStartLocal = yStartLocal + 41;
                            width = width - 1;
                            height = height + 1;
                        }

                        tailTeratisu[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.TACKLE.ordinal()]
                                //[FishStateManager.Tail.TERATISU.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 29;
                            width = width + 4;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 31;
                            width = width - 2;
                            height = height + 2;
                        }
                    }

                    break;

                case ZINICHTHY:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal - 1;
                            yStartLocal = yStartLocal + 62;
                            width = width + 1;
                            height = height + 1;
                        }

                        tailZinichthy[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.TACKLE.ordinal()]
                                //[FishStateManager.Tail.ZINICHTHY.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 27;
                            width = width + 6;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 33;
                            width = width - 4;
                            height = height + 2;
                        }
                    }

                    break;

                case KURASELACHE:
                    for (int col = 0; col < 3; col++) {
                        if (col == 0) {
                            xStartLocal = xStartLocal + 0;
                            yStartLocal = yStartLocal + 83;
                            width = width + 0;
                            height = height + 1;
                        }

                        tailKuraselache[FishStateManager.BodySize.INCREASE.ordinal()]
                                [bodyTexture.ordinal()]
                                [FishStateManager.FinPectoral.TACKLE.ordinal()]
                                //[FishStateManager.Tail.KURASELACHE.ordinal()]
                                [col] = spriteSheetChapter1Creatures.getSubimage(xStartLocal, yStartLocal, width, height);

                        //setting up for next col
                        if (col == 0) {
                            xStartLocal = xStartLocal + 28;
                            width = width + 5;
                            height = height + 1;
                        } else if (col == 1) {
                            xStartLocal = xStartLocal + 32;
                            width = width - 3;
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
