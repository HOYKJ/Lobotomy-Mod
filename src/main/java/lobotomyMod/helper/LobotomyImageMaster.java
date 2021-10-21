package lobotomyMod.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.helpers.ImageMaster;

/**
 * @author hoykj
 */
public class LobotomyImageMaster {
    //    public static TextureAtlas.AtlasRegion S_CHEST_OPEN;
    public static TextureAtlas.AtlasRegion GEBURAH_SPEAR;
    public static TextureAtlas.AtlasRegion GEBURAH_DACAPO;
    public static TextureAtlas.AtlasRegion GEBURAH_MIMICRY;
    public static TextureAtlas.AtlasRegion GEBURAH_GREED;
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
    public static Texture TOMB_STONE;
    public static Texture BINAH_BACKGROUND;
    public static Texture CHESED_BACKGROUND;
    public static Texture HOKMA_BACKGROUND;
    public static Texture GEBURAH_BACKGROUND;
    public static Texture HOD_BACKGROUND;
    public static Texture MALKUTH_BACKGROUND;
    public static Texture NETZACH_BACKGROUND;
    public static Texture TIPHERETH_BACKGROUND;
    public static Texture YESOD_BACKGROUND;
    public static Texture ANGELA_BOSS_BACKGROUND;
    public static Texture[] SMALL_SHIELD = new Texture[3];
    public static Texture[] MID_SHIELD = new Texture[4];
//    public static Texture[] BOSS_RUSH = new Texture[11];
//    public static Texture[] BOSS_RUSH_P = new Texture[9];
    public static Texture[] FEAR_LEVEL = new Texture[7];
    public static Color[] FEAR_LEVEL_COLOR = new Color[]{new Color(31 / 255.0F, 1, 174 / 255.0F, 1), new Color(31 / 255.0F, 1, 174 / 255.0F, 1),
            new Color(1, 1, 161 / 255.0F, 1), new Color(252 / 255.0F, 201 / 255.0F, 58 / 255.0F, 1),
            new Color(252 / 255.0F, 119 / 255.0F, 58 / 255.0F, 1), new Color(252 / 255.0F, 58 / 255.0F, 58 / 255.0F, 1), Color.WHITE.cpy()};

    public static void initialize(){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("lobotomyMod/images/monsters/Sephirah/Geburah/gebura.atlas"));
        GEBURAH_SPEAR = atlas.findRegion("spear");
        GEBURAH_DACAPO = atlas.findRegion("Weapon_Unique_Silent");
        GEBURAH_MIMICRY = atlas.findRegion("nullthing");
        GEBURAH_GREED = atlas.findRegion("Weapon_Unique_Greed");
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
        TOMB_STONE = ImageMaster.loadImage("lobotomyMod/images/monsters/Sephirah/Binah/TombStone.png");
        BINAH_BACKGROUND = ImageMaster.loadImage("lobotomyMod/images/monsters/Sephirah/Binah/Binah_Main.png");
        CHESED_BACKGROUND = ImageMaster.loadImage("lobotomyMod/images/monsters/Sephirah/Chesed/chesedBK.png");
        HOKMA_BACKGROUND = ImageMaster.loadImage("lobotomyMod/images/monsters/Sephirah/Hokma/Chokhmah_Main.png");
        GEBURAH_BACKGROUND = ImageMaster.loadImage("lobotomyMod/images/monsters/Sephirah/Geburah/geburaBK.png");
        HOD_BACKGROUND = ImageMaster.loadImage("lobotomyMod/images/monsters/Sephirah/Hod/hodBK.png");
        MALKUTH_BACKGROUND = ImageMaster.loadImage("lobotomyMod/images/monsters/Sephirah/Malkuth/malkuthBK.png");
        NETZACH_BACKGROUND = ImageMaster.loadImage("lobotomyMod/images/monsters/Sephirah/Netzach/netzachBK.png");
        TIPHERETH_BACKGROUND = ImageMaster.loadImage("lobotomyMod/images/monsters/Sephirah/Tiphereth/tipherethBK.png");
        YESOD_BACKGROUND = ImageMaster.loadImage("lobotomyMod/images/monsters/Sephirah/Yesod/yesodBK.png");
        ANGELA_BOSS_BACKGROUND = ImageMaster.loadImage("lobotomyMod/images/characters/angela/angela_black/carmenBK1.png");
        SMALL_SHIELD[0] = ImageMaster.loadImage("lobotomyMod/images/powers/32/RedShield.png");
        SMALL_SHIELD[1] = ImageMaster.loadImage("lobotomyMod/images/powers/32/BlackShield.png");
        SMALL_SHIELD[2] = ImageMaster.loadImage("lobotomyMod/images/powers/32/HPRecovery.png");
        MID_SHIELD[0] = ImageMaster.loadImage("lobotomyMod/images/powers/32/MPRecovery.png");
        MID_SHIELD[1] = ImageMaster.loadImage("lobotomyMod/images/powers/32/SlowAim.png");
        MID_SHIELD[2] = ImageMaster.loadImage("lobotomyMod/images/powers/32/WhiteShield.png");
        MID_SHIELD[3] = ImageMaster.loadImage("lobotomyMod/images/powers/32/PaleShield.png");
//        for(int i = 0; i < 11; i ++) {
//            BOSS_RUSH[i] = ImageMaster.loadImage("lobotomyMod/images/events/bossRush_" + i + ".png");
//        }
//        for(int i = 0; i < 9; i ++) {
//            BOSS_RUSH_P[i] = ImageMaster.loadImage("lobotomyMod/images/events/bossRush_" + (i + 2) + ".png");
//        }
        FEAR_LEVEL[0] = ImageMaster.loadImage("lobotomyMod/images/texture/FearLevel_1.png");
        FEAR_LEVEL[1] = ImageMaster.loadImage("lobotomyMod/images/texture/FearLevel_1.png");
        for(int i = 2; i < 7; i ++) {
            FEAR_LEVEL[i] = ImageMaster.loadImage("lobotomyMod/images/texture/FearLevel_" + (i - 1) + ".png");
        }
    }
}
