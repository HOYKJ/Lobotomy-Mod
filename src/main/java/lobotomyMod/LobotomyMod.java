package lobotomyMod;

import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.vfx.RestartForChangesEffect;
import lobotomyMod.card.rareCard.PlagueDoctor;
import lobotomyMod.card.uncommonCard.Freischutz;
import lobotomyMod.character.Angela;
import lobotomyMod.character.LobotomyCardPool;
import lobotomyMod.character.LobotomyHandler;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import lobotomyMod.event.BossRushEvent;
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
    public static int deadTime;
    public static int meltdownCode;
    public static boolean enableOrdeal;
    public static boolean hasYin;
    public static boolean hasBackward;
    public static boolean activeFixer;
    public static boolean defeatFixer;
    public static boolean activeRabbit;
    public static boolean activeAngela;
    public static boolean activeChampagne;
    public static boolean activeBlackAngela;
    public static boolean useBlackAngela;
    public static boolean[] activeTutorials = new boolean[]{true, true, true, true, true, true};
    public static boolean deleteSave;
    public static boolean challengeEvent;
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
            defaults.setProperty("deadTime", "0");
            defaults.setProperty("meltdownCode", "0");
            defaults.setProperty("enableOrdeal", "true");
            defaults.setProperty("hasYin", "false");
            defaults.setProperty("hasBackward", "false");
            defaults.setProperty("activeFixer", "false");
            defaults.setProperty("defeatFixer", "false");
            defaults.setProperty("activeRabbit", "false");
            defaults.setProperty("activeAngela", "false");
            defaults.setProperty("activeChampagne", "false");
            defaults.setProperty("activeBlackAngela", "false");
            defaults.setProperty("useBlackAngela", "false");
            defaults.setProperty("challengeEvent", "true");
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
            LobotomyMod.deadTime = config.getInt("deadTime");
            LobotomyMod.meltdownCode = config.getInt("meltdownCode");
            LobotomyMod.enableOrdeal = config.getBool("enableOrdeal");
            LobotomyMod.hasYin = config.getBool("hasYin");
            LobotomyMod.hasBackward = config.getBool("hasBackward");
            LobotomyMod.activeFixer = config.getBool("activeFixer");
            LobotomyMod.defeatFixer = config.getBool("defeatFixer");
            LobotomyMod.activeRabbit = config.getBool("activeRabbit");
            LobotomyMod.activeAngela = config.getBool("activeAngela");
            LobotomyMod.activeChampagne = config.getBool("activeChampagne");
            LobotomyMod.activeBlackAngela = config.getBool("activeBlackAngela");
            LobotomyMod.useBlackAngela = config.getBool("useBlackAngela");
            LobotomyMod.challengeEvent = config.getBool("challengeEvent");
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

        LobotomyHandler.addRecallAbnormality();

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

            LobotomyHandler.firstStart();

            logger.info("==========================第一次载入初始化o了==========================");
        }
        else {
            logger.info("=========================数据载入=========================");

            LobotomyHandler.loadStart();

            logger.info("=========================数据载入也o了=========================");
        }
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

        //BaseMod.addEvent(BossRushEvent.ID, BossRushEvent.class, Exordium.ID);

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

            BaseMod.addCharacter(new Angela("Angela"), "lobotomyMod/images/ui/charSelect/angela/AngelaButton.png",
                    (LobotomyMod.useBlackAngela?"lobotomyMod/images/ui/charSelect/angela/angelaCG3.jpg": "lobotomyMod/images/ui/charSelect/angela/AngelaPortrait.jpg"),
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
        });
        settingsPanel.addUIElement(SoundOpen);

        if(LobotomyMod.activeBlackAngela) {
            final ModLabeledToggleButton BlackAngelaOpen = new ModLabeledToggleButton(TEXT[1], 500.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, LobotomyMod.useBlackAngela, settingsPanel, label -> {
            }, button -> {
                spireConfig.setBool("useBlackAngela", LobotomyMod.useBlackAngela = button.enabled);
                CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();
                CardCrawlGame.mainMenuScreen.optionPanel.effects.add(new RestartForChangesEffect());

                try {
                    spireConfig.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            settingsPanel.addUIElement(BlackAngelaOpen);
        }

        final ModLabeledToggleButton tutorialOpen = new ModLabeledToggleButton(TEXT[2], 500.0f, 550.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, LobotomyMod.activeTutorials[0], settingsPanel, label -> {
        }, button -> {
            for(int i = 0; i < LobotomyMod.activeTutorials.length; i ++) {
                spireConfig.setBool("activeTutorials", LobotomyMod.activeTutorials[i] = button.enabled);
            }
            CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();

            try {
                spireConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(tutorialOpen);

        final ModLabeledToggleButton deleteSaveOpen = new ModLabeledToggleButton(TEXT[3], 500.0f, 450.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, LobotomyMod.deleteSave, settingsPanel, label -> {
        }, button -> {
            spireConfig.setBool("deleteSave", LobotomyMod.deleteSave = button.enabled);
            CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();
            CardCrawlGame.mainMenuScreen.optionPanel.effects.add(new RestartForChangesEffect());

            try {
                spireConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(deleteSaveOpen);

        final ModLabeledToggleButton challengeEvent = new ModLabeledToggleButton("show Core Suppression", 500.0f, 350.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, LobotomyMod.challengeEvent, settingsPanel, label -> {
        }, button -> {
            spireConfig.setBool("challengeEvent", LobotomyMod.challengeEvent = button.enabled);
            CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();

            try {
                spireConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(challengeEvent);

        Texture badgeTexture = new Texture(Gdx.files.internal("lobotomyMod/images/Lobotomy.png"));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
    }

    public static void saveData() throws IOException {
        final SpireConfig config = new SpireConfig("LobotomyMod", "Common");
        config.setInt("PE", LobotomyMod.PE);
        config.setInt("apostles", LobotomyMod.apostles);
        config.setInt("usedFixer", LobotomyMod.usedFixer);
        config.setInt("deadTime", LobotomyMod.deadTime);
        config.setInt("meltdownCode", LobotomyMod.meltdownCode);
        config.setBool("enableOrdeal", LobotomyMod.enableOrdeal);
        config.setBool("hasYin", LobotomyMod.hasYin);
        config.setBool("hasBackward", LobotomyMod.hasBackward);
        config.setBool("activeFixer", LobotomyMod.activeFixer);
        config.setBool("defeatFixer", LobotomyMod.defeatFixer);
        config.setBool("activeRabbit", LobotomyMod.activeRabbit);
        config.setBool("activeAngela", LobotomyMod.activeAngela);
        config.setBool("activeChampagne", LobotomyMod.activeChampagne);
        config.setBool("activeBlackAngela", LobotomyMod.activeBlackAngela);
        config.setBool("useBlackAngela", LobotomyMod.useBlackAngela);
        config.setBool("challengeEvent", LobotomyMod.challengeEvent);
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
