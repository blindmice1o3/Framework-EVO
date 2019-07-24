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

    // Initialization
    public static void init() {
        // SPRITE SHEETS
        spriteSheetChapterIntroAndWorldMap = Utils.loadImage("/SNES - EVO Search for Eden - Maps & Chapter Images.png");

        // INTRO
        chapter1Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(6, 6, 133, 85);
        chapter2Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(6, 232, 133, 85);

        // CHAPTER
        chapter1Chapter = spriteSheetChapterIntroAndWorldMap.getSubimage(146, 6, 255, 221);
        chapter2Chapter = spriteSheetChapterIntroAndWorldMap.getSubimage(146, 232, 255, 211);

        // WORLD MAP
        chapter1WorldMap = spriteSheetChapterIntroAndWorldMap.getSubimage(407, 6, 255, 221);
    }

}
