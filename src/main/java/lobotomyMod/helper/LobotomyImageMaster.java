package lobotomyMod.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.helpers.ImageMaster;

/**
 * @author hoykj
 */
public class LobotomyImageMaster {
    //    public static TextureAtlas.AtlasRegion S_CHEST_OPEN;
    public static Texture CLOCK_SHADER;
    public static Texture WITCH_GIFT;
    public static Texture WITCH_GIFT_2;
    public static Texture FREISCHUTZ_CURSOR;
    public static Texture LOBOTOMY_CURSOR;
    public static Texture LOBOTOMY_CURSOR_DOWN;
    public static Texture FREISCHUTZ_FILTER;
    public static Texture[] BLACK_FOREST_BACK = new Texture[8];
    public static Texture DESPAIR_BLESS;
    public static Texture LANTERN_FLOWER;
    public static Texture CENSORED_IMG;
    public static Texture[] SHY_FACE = new Texture[5];
    public static Texture FIERY_CARD;
    public static Texture YIN_YANG_MERGE;
    public static Texture YIN_YANG_DRAGON;
    public static Texture PINK_HEART;
    public static Texture MELTING_HEART;
    public static Texture ORDEAL_FRAME;
    public static Texture ORDEAL_PARTING_LINE;
    public static Texture ORDEAL_TEXT_LINE;
    public static Texture HELL_TRAIN;
    public static Texture BACKWARD_BACK;
    public static Texture ANGELA_BACKGROUND;
    public static Texture RABBIT_HALMET;
    public static Texture[] RABBIT_TEAM_UI = new Texture[14];
    public static Texture[] AIM_SELECT_UI = new Texture[12];

    public static void initialize(){
//        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("lobotomyMod/images/texture/backEffect.atlas"));
//        halo = atlas.findRegion("0407");
        CLOCK_SHADER = ImageMaster.loadImage("lobotomyMod/images/texture/ClockShader.png");
        WITCH_GIFT = ImageMaster.loadImage("lobotomyMod/images/texture/Heart1.png");
        WITCH_GIFT_2 = ImageMaster.loadImage("lobotomyMod/images/texture/Heart2.png");
        FREISCHUTZ_CURSOR = ImageMaster.loadImage("lobotomyMod/images/texture/CursorScope_Freischutz.png");
        LOBOTOMY_CURSOR = ImageMaster.loadImage("lobotomyMod/images/texture/CursorNormal_Default.png");
        LOBOTOMY_CURSOR_DOWN = ImageMaster.loadImage("lobotomyMod/images/texture/CursorNormal_Supress.png");
        FREISCHUTZ_FILTER = ImageMaster.loadImage("lobotomyMod/images/texture/FreischutzSnipingFilter.png");
        BLACK_FOREST_BACK[0] = ImageMaster.loadImage("lobotomyMod/images/texture/BigBirdArrived.png");
        BLACK_FOREST_BACK[1] = ImageMaster.loadImage("lobotomyMod/images/texture/BigBirdDead.png");
        BLACK_FOREST_BACK[2] = ImageMaster.loadImage("lobotomyMod/images/texture/LongBirdArrived.png");
        BLACK_FOREST_BACK[3] = ImageMaster.loadImage("lobotomyMod/images/texture/LongBirdDead.png");
        BLACK_FOREST_BACK[4] = ImageMaster.loadImage("lobotomyMod/images/texture/SmallBirdArrived.png");
        BLACK_FOREST_BACK[5] = ImageMaster.loadImage("lobotomyMod/images/texture/SmallBirdDead.png");
        BLACK_FOREST_BACK[6] = ImageMaster.loadImage("lobotomyMod/images/texture/BossBirdAppear.png");
        BLACK_FOREST_BACK[7] = ImageMaster.loadImage("lobotomyMod/images/texture/BossBirdDead.png");
        DESPAIR_BLESS = ImageMaster.loadImage("lobotomyMod/images/texture/knightOfDespairBless.png");
        LANTERN_FLOWER = ImageMaster.loadImage("lobotomyMod/images/texture/MeatLanternFlower.png");
        CENSORED_IMG = ImageMaster.loadImage("lobotomyMod/images/texture/CENSORED.png");
        SHY_FACE[0] = ImageMaster.loadImage("lobotomyMod/images/texture/ShyLook_0.png");
        SHY_FACE[1] = ImageMaster.loadImage("lobotomyMod/images/texture/ShyLook_1.png");
        SHY_FACE[2] = ImageMaster.loadImage("lobotomyMod/images/texture/ShyLook_2.png");
        SHY_FACE[3] = ImageMaster.loadImage("lobotomyMod/images/texture/ShyLook_3.png");
        SHY_FACE[4] = ImageMaster.loadImage("lobotomyMod/images/texture/ShyLook_4.png");
        FIERY_CARD = ImageMaster.loadImage("lobotomyMod/images/texture/FireCard.png");
        YIN_YANG_MERGE = ImageMaster.loadImage("lobotomyMod/images/texture/YinYangMerge.png");
        YIN_YANG_DRAGON = ImageMaster.loadImage("lobotomyMod/images/texture/YinYangDragon.png");
        PINK_HEART = ImageMaster.loadImage("lobotomyMod/images/texture/PinkHeart.png");
        MELTING_HEART = ImageMaster.loadImage("lobotomyMod/images/texture/SlimeLoverBuf.png");
        ORDEAL_FRAME = ImageMaster.loadImage("lobotomyMod/images/texture/Ordeal_TimeColor_Frame.png");
        ORDEAL_PARTING_LINE = ImageMaster.loadImage("lobotomyMod/images/texture/Ordeal_TimeColor_PartingLine.png");
        ORDEAL_TEXT_LINE = ImageMaster.loadImage("lobotomyMod/images/texture/Ordeal_TimeColor_TextHighlightLine.png");
        HELL_TRAIN = ImageMaster.loadImage("lobotomyMod/images/texture/train.png");
        BACKWARD_BACK = ImageMaster.loadImage("lobotomyMod/images/texture/300110.png");
        ANGELA_BACKGROUND = ImageMaster.loadImage("lobotomyMod/images/texture/AngelaBackground.png");
        RABBIT_HALMET = ImageMaster.loadImage("lobotomyMod/images/texture/rabbitHalmet.png");
        for(int i = 0; i < 14; i ++) {
            RABBIT_TEAM_UI[i] = ImageMaster.loadImage("lobotomyMod/images/texture/RabbitTeamUI_" + i + ".png");
        }
        AIM_SELECT_UI[0] = ImageMaster.loadImage("lobotomyMod/images/texture/aim/frame.png");
        AIM_SELECT_UI[1] = ImageMaster.loadImage("lobotomyMod/images/texture/aim/hp.png");
        AIM_SELECT_UI[2] = ImageMaster.loadImage("lobotomyMod/images/texture/aim/sp.png");
        AIM_SELECT_UI[3] = ImageMaster.loadImage("lobotomyMod/images/texture/aim/red.png");
        AIM_SELECT_UI[4] = ImageMaster.loadImage("lobotomyMod/images/texture/aim/white.png");
        AIM_SELECT_UI[5] = ImageMaster.loadImage("lobotomyMod/images/texture/aim/black.png");
        AIM_SELECT_UI[6] = ImageMaster.loadImage("lobotomyMod/images/texture/aim/pale.png");
        AIM_SELECT_UI[7] = ImageMaster.loadImage("lobotomyMod/images/texture/aim/slow.png");
        AIM_SELECT_UI[8] = ImageMaster.loadImage("lobotomyMod/images/texture/aim/execute.png");
        AIM_SELECT_UI[9] = ImageMaster.loadImage("lobotomyMod/images/texture/aim/lock.png");
        AIM_SELECT_UI[10] = ImageMaster.loadImage("lobotomyMod/images/texture/aim/ArrowDefault.png");
        AIM_SELECT_UI[11] = ImageMaster.loadImage("lobotomyMod/images/texture/aim/ArrowGlow.png");
    }
}
