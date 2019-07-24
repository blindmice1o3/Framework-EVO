package com.evo.gfx;

import com.evo.Utils;

import java.awt.image.BufferedImage;

public class Assets {

    // SPRITE SHEETS
    public static BufferedImage spriteSheetChapterIntroAndWorldMap;

    // BACKGROUNDS - INTRO
    public static BufferedImage chapter1Intro, chapter2Intro, chapter3Intro, chapter4Intro, chapter5Intro;

    // BACKGROUNDS - CHAPTER
    public static BufferedImage chapter1Chapter, chapter2Chapter, chapter3Chapter, chapter4Chapter, chapter5Chapter;

    // BACKGROUNDS - WORLD MAP
    public static BufferedImage chapter1WorldMap, chapter2WorldMap, chapter3WorldMap, chapter4WorldMap, chapter5WorldMap;

    // BACKGROUND - WAVE
    public static BufferedImage chapter1Wave, chapter2Wave, chapter3Wave, chapter4Wave, chapter5Wave;

    // Initialization
    public static void init() {
        // SPRITE SHEETS
        spriteSheetChapterIntroAndWorldMap = Utils.loadImage("/SNES - EVO Search for Eden - Maps & Chapter Images.png");

        // INTRO
        chapter1Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(6, 6, 133+1, 85+1);
        chapter2Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(6, 232, 133+1, 85+1);
        chapter3Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(11, 756, 133+1, 85+1);
        chapter4Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(19, 1283, 133+1, 85+1);
        chapter5Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(12, 1933, 133+1, 85+1);

        // CHAPTER
        chapter1Chapter = spriteSheetChapterIntroAndWorldMap.getSubimage(146, 6, 255+1, 221+1);
        chapter2Chapter = spriteSheetChapterIntroAndWorldMap.getSubimage(146, 232, 255+1, 211+1);
        chapter3Chapter = spriteSheetChapterIntroAndWorldMap.getSubimage(152, 755, 255+1, 211+1);
        chapter4Chapter = spriteSheetChapterIntroAndWorldMap.getSubimage(159, 1283, 255+1, 221+1);
        chapter5Chapter = spriteSheetChapterIntroAndWorldMap.getSubimage(151, 1934, 255+1, 221+1);

        // WORLD MAP
        chapter1WorldMap = spriteSheetChapterIntroAndWorldMap.getSubimage(407, 6, 255+1, 221+1);
        chapter2WorldMap = spriteSheetChapterIntroAndWorldMap.getSubimage(408, 230, 243+1, 223+1);
        chapter3WorldMap = spriteSheetChapterIntroAndWorldMap.getSubimage(411, 755, 255+1, 221+1);
        chapter4WorldMap = spriteSheetChapterIntroAndWorldMap.getSubimage(420, 1283, 255+1, 221+1);
        chapter5WorldMap = spriteSheetChapterIntroAndWorldMap.getSubimage(411, 1934, 255+1, 221+1);

        // WAVE
        chapter1Wave = spriteSheetChapterIntroAndWorldMap.getSubimage(667, 4, 255+1, 223+1);
        chapter2Wave = spriteSheetChapterIntroAndWorldMap.getSubimage(657, 231, 255+1, 223+1);
        chapter3Wave = spriteSheetChapterIntroAndWorldMap.getSubimage(674, 754, 255+1, 223+1);
        chapter4Wave = spriteSheetChapterIntroAndWorldMap.getSubimage(682, 1282, 255+1, 223+1);
        chapter5Wave = spriteSheetChapterIntroAndWorldMap.getSubimage(671, 1932, 255+1, 223+1);

    }

}
