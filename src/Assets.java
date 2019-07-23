import java.awt.image.BufferedImage;

public class Assets {

    //SPRITE SHEETS
    public static BufferedImage spriteSheetChapterIntroAndWorldMap;

    //BACKGROUNDS
    public static BufferedImage chapter1Intro, chapter2Intro, chapter3Intro, chapter4Intro, chapter5Intro;

    //Initialization
    public static void init() {

        //SPRITE SHEETS
        spriteSheetChapterIntroAndWorldMap = Utils.loadImage("/SNES - EVO Search for Eden - Maps & Chapter Images.png");

        //BACKGROUNDS


    }
}
