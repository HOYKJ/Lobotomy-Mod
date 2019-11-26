package lobotomyMod;

import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.vfx.RestartForChangesEffect;
import lobotomyMod.card.rareCard.PlagueDoctor;
import lobotomyMod.character.Angela;
import lobotomyMod.character.LobotomyCardPool;
import lobotomyMod.character.LobotomyHandler;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import lobotomyMod.helper.LobotomyFontHelper;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.patch.CharacterEnum;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.rabbitTeam.order.RabbitOrderScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * @author hoykj
 */
@SpireInitializer
public class LobotomyMod implements StartGameSubscriber, PostInitializeSubscriber, PostDungeonInitializeSubscriber, EditRelicsSubscriber
        , EditCardsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber {
    private static final String MODNAME = "Lobotomy Mod";
    private static final String AUTHOR = "hoykj";
    private static final String DESCRIPTION = "nothing";
    public static final Logger logger = LogManager.getLogger(LobotomyMod.class.getName());
    public static int PE;
    public static int[] levelSave = new int[120];
    public static int apostles;
    public static int usedFixer;
    public static boolean enableOrdeal;
    public static boolean hasYin;
    public static boolean hasBackward;
    public static boolean activeFixer;
    public static boolean defeatFixer;
    public static boolean activeRabbit;
    public static boolean activeAngela;
    public static boolean[] activeTutorials = new boolean[]{true, true, true, true, true};
    public static RabbitOrderScreen rabbitOrderScreen;
    public static int departments[] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static void initialize()  {
        logger.info("========================= 初始化Lobotomy Mod所有数据 =========================");

        LobotomyMod lobotomyMod = new LobotomyMod();

        try {
            final Properties defaults = new Properties();
            defaults.setProperty("PE", "0");
            defaults.setProperty("apostles", "0");
            defaults.setProperty("usedFixer", "0");
            defaults.setProperty("enableOrdeal", "true");
            defaults.setProperty("hasYin", "false");
            defaults.setProperty("hasBackward", "false");
            defaults.setProperty("activeFixer", "false");
            defaults.setProperty("defeatFixer", "false");
            defaults.setProperty("activeRabbit", "false");
            defaults.setProperty("activeAngela", "false");
            for(int i = 0; i < levelSave.length; i ++) {
                defaults.setProperty("levelSave" + i, "0");
            }
            for(int i = 0; i < departments.length; i ++) {
                defaults.setProperty("departments" + i, "0");
            }
            for(int i = 0; i < activeTutorials.length; i ++) {
                defaults.setProperty("activeTutorials" + i, "true");
            }
            final SpireConfig config = new SpireConfig("LobotomyMod", "Common", defaults);
            LobotomyMod.PE = config.getInt("PE");
            LobotomyMod.apostles = config.getInt("apostles");
            LobotomyMod.usedFixer = config.getInt("usedFixer");
            LobotomyMod.enableOrdeal = config.getBool("enableOrdeal");
            LobotomyMod.hasYin = config.getBool("hasYin");
            LobotomyMod.hasBackward = config.getBool("hasBackward");
            LobotomyMod.activeFixer = config.getBool("activeFixer");
            LobotomyMod.defeatFixer = config.getBool("defeatFixer");
            LobotomyMod.activeRabbit = config.getBool("activeRabbit");
            LobotomyMod.activeAngela = config.getBool("activeAngela");
            for(int i = 0; i < levelSave.length; i ++) {
                LobotomyMod.levelSave[i] = config.getInt("levelSave" + i);
            }
            for(int i = 0; i < departments.length; i ++) {
                departments[i] = config.getInt("departments" + i);
            }
            for(int i = 0; i < activeTutorials.length; i ++) {
                activeTutorials[i] = config.getBool("activeTutorials" + i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("=========================== 初始化Lobotomy Mod成功 ===========================");
        logger.info("========================正在注入新卡片相关信息========================");

        LobotomyHandler.addNewCardColor();

        logger.info("========================注入新卡片相关信息成功========================");
    }

    public LobotomyMod(){
        BaseMod.subscribe(this);
    }

    public void receiveStartGame() {
        final String filepath = "saves" + File.separator + "Lobotomy.autosave";
        final boolean fileExists = Gdx.files.local(filepath).exists();
        Settings.hideCombatElements = false;
        if (!CardCrawlGame.loadingSave) {
            logger.info("==========================第一次载入初始化==========================");

            LobotomyMod.usedFixer = 0;
            LobotomyMod.hasYin = false;
            LobotomyMod.hasBackward = false;
            LobotomyMod.activeFixer = false;
            System.arraycopy(LobotomyMod.levelSave, 0, CogitoBucket.level, 0, LobotomyMod.levelSave.length);
            LobotomyCardPool.reload = false;
            LobotomyCardPool.addCardPool();
            CogitoBucket.level[0] = 1;
            for(int i = 0; i < Angela.departments.length; i ++) {
                departments[i] = 0;
                Angela.departments[i] = 0;
            }

            logger.info("==========================第一次载入初始化o了==========================");
        }
        else {
            LobotomyCardPool.reload = true;
            LobotomyCardPool.reloadCardPool();

//            if(AbstractDungeon.player instanceof Angela){
//                ((Angela) AbstractDungeon.player).bless = true;
//            }

            if(DungeonMap.boss == null){
                DungeonMap.boss = ImageMaster.loadImage("lobotomyMod/images/ui/map/boss/claw.png");
                DungeonMap.bossOutline = ImageMaster.loadImage("lobotomyMod/images/ui/map/bossOutline/claw.png");
            }
        }
        logger.info("=========================数据载入=========================");

        logger.info("=========================数据载入也o了=========================");

    }

    public void receivePostInitialize() {
        logger.info("========================= receivePostInitialize =========================");

        try {
            this.CreatePanel();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        LobotomyImageMaster.initialize();
        try {
            LobotomyFontHelper.initialize();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        logger.info("========================= receivePostInitializeDone =========================");
    }

    public void receivePostDungeonInitialize() {

    }

    public void receiveEditRelics() {
        logger.info("=========================正在加载新的遗物内容=========================");

        LobotomyHandler.addRelics();

        logger.info("=========================加载新的遗物内容成功=========================");

        logger.info("=========================正在加载新的药水内容=========================");

        LobotomyHandler.addPotions();

        logger.info("=========================加载新的药水内容成功=========================");
    }

    public void receiveEditCards() {
        logger.info("=========================正在加载新的卡牌内容=========================");

        LobotomyHandler.addCards();

        logger.info("=========================加载新的卡牌内容成功=========================");
    }

    public void receiveEditStrings() {
        logger.info("========================= 正在加载文本信息 =========================");

        LobotomyHandler.addStrings();

        logger.info("========================= 加载文本信息成功 =========================");
    }

    public void receiveEditKeywords() {
        logger.info("========================= 正在加载特性文本信息 =========================");

        LobotomyHandler.addKeyWords();

        logger.info("========================= 加载特性文本信息成功 =========================");
    }

    public void receiveEditCharacters() {
        logger.info("========================正在注入Mod人物信息========================");

        if(LobotomyMod.activeAngela) {
            logger.info("add " + CharacterEnum.Angela.toString());

            BaseMod.addCharacter(
                    new Angela("Angela"), "lobotomyMod/images/ui/charSelect/angela/AngelaButton.png", "lobotomyMod/images/ui/charSelect/angela/AngelaPortrait.jpg",
                    CharacterEnum.Angela);
        }

        logger.info("========================注入Mod人物信息成功========================");
    }

    private void CreatePanel() throws IOException {
        final SpireConfig spireConfig = new SpireConfig("LobotomyMod", "Common");
        final ModPanel settingsPanel = new ModPanel();
        final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CreatePanelLobotomy");
        final String[] TEXT = uiStrings.TEXT;

        final ModLabeledToggleButton SoundOpen = new ModLabeledToggleButton(TEXT[0], 500.0f, 600.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, LobotomyMod.enableOrdeal, settingsPanel, label->{}, button->{
            spireConfig.setBool("SoundOpen", LobotomyMod.enableOrdeal = button.enabled);
            CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();
            CardCrawlGame.mainMenuScreen.optionPanel.effects.add(new RestartForChangesEffect());

            try {
                spireConfig.save();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return;
        });
        settingsPanel.addUIElement(SoundOpen);

        Texture badgeTexture = new Texture(Gdx.files.internal("lobotomyMod/images/Lobotomy.png"));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
    }

    public static void saveData() throws IOException {
        final SpireConfig config = new SpireConfig("LobotomyMod", "Common");
        config.setInt("PE", LobotomyMod.PE);
        config.setInt("apostles", LobotomyMod.apostles);
        config.setInt("usedFixer", LobotomyMod.usedFixer);
        config.setBool("enableOrdeal", LobotomyMod.enableOrdeal);
        config.setBool("hasYin", LobotomyMod.hasYin);
        config.setBool("hasBackward", LobotomyMod.hasBackward);
        config.setBool("activeFixer", LobotomyMod.activeFixer);
        config.setBool("defeatFixer", LobotomyMod.defeatFixer);
        config.setBool("activeRabbit", LobotomyMod.activeRabbit);
        config.setBool("activeAngela", LobotomyMod.activeAngela);
        for(int i = 0; i < levelSave.length; i ++) {
            config.setInt("levelSave" +  i, LobotomyMod.levelSave[i]);
        }
        for(int i = 0; i < Angela.departments.length; i ++) {
            config.setInt("departments" +  i, Angela.departments[i]);
        }
        for(int i = 0; i < activeTutorials.length; i ++) {
            config.setBool("activeTutorials" +  i, activeTutorials[i]);
        }

        config.save();
    }
}
