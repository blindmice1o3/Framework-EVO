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
        chapter1Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(146, 6, 255, 221);
        chapter2Intro = spriteSheetChapterIntroAndWorldMap.getSubimage(146, 232, 255, 211);

    }
}
