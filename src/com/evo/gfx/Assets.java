package com.evo.gfx;

import com.evo.Utils;
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

    // Initialization
    public static void init() {
        // SPRITE SHEETS
        spriteSheetChapterIntroAndWorldMap = Utils.loadImage("/SNES - EVO Search for Eden - Maps & Chapter Images.png");
        spriteSheetChapter1Creatures = Utils.loadImage("/SNES - EVO Search for Eden - Chapter 1 Creatures.png");

        // ENTITY - Chapter 1: FISH HEAD (AND ATTACHMENTS)
        eatFrames = new BufferedImage[FishStateManager.BodySize.values().length]
                [FishStateManager.BodyTexture.values().length]
                [FishStateManager.Jaws.values().length]
                [FishStateManager.ActionState.values().length-1] //minus 1 because NONE doesn't count.
                [3];
        biteFrames = new BufferedImage[FishStateManager.BodySize.values().length][FishStateManager.BodyTexture.values().length][FishStateManager.Jaws.values().length][FishStateManager.ActionState.values().length-1][3];
        hurtFrames = new BufferedImage[FishStateManager.BodySize.values().length][FishStateManager.BodyTexture.values().length][FishStateManager.Jaws.values().length][FishStateManager.ActionState.values().length-1][2];



        // ENTITY - Chapter 1: FISH BODY (AND ATTACHMENTS)


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

}
