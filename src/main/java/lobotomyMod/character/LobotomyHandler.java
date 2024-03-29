package lobotomyMod.character;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.unique.RecallAbnormalityAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.angelaCard.bullets.*;
import lobotomyMod.card.angelaCard.code.*;
import lobotomyMod.card.angelaCard.code.other.SummonCall;
import lobotomyMod.card.angelaCard.code.other.TT2Protocol;
import lobotomyMod.card.commonCard.*;
import lobotomyMod.card.deriveCard.ApostleCardGroup.*;
import lobotomyMod.card.deriveCard.Apostles.HereticApostle;
import lobotomyMod.card.deriveCard.Ordeal;
import lobotomyMod.card.deriveCard.test;
import lobotomyMod.card.ego.common.*;
import lobotomyMod.card.ego.rare.*;
import lobotomyMod.card.ego.uncommon.*;
import lobotomyMod.card.rareCard.*;
import lobotomyMod.card.uncommonCard.*;
import lobotomyMod.event.WhiteNightEvent;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.patch.AbstractCardEnum;
import lobotomyMod.relic.*;
import lobotomyMod.relic.Twilight;
import lobotomyMod.relic.toolAbnormality.*;
import lobotomyMod.vfx.action.LatterEffect;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static lobotomyMod.LobotomyMod.departments;

/**
 * @author hoykj
 */
public class LobotomyHandler {
    public static ArrayList<AbstractLobotomyCard> abnormalityListCommon = new ArrayList<>();
    public static ArrayList<AbstractLobotomyCard> abnormalityListUncommon = new ArrayList<>();
    public static ArrayList<AbstractLobotomyCard> abnormalityListRare = new ArrayList<>();
    public static ArrayList<AbstractLobotomyAbnRelic> abnormalityListRelic = new ArrayList<>();

    public static void addNewCardColor(){
        BaseMod.addColor(AbstractCardEnum.Lobotomy,
                Color.BLACK.cpy(), Color.BLACK.cpy(), Color.BLACK.cpy(), Color.BLACK.cpy(), Color.BLACK.cpy(), Color.BLACK.cpy(), Color.BLACK.cpy(),
                "lobotomyMod/images/cardui/512/bg_attack_lobotomy.png", "lobotomyMod/images/cardui/512/bg_skill_lobotomy.png",
                "lobotomyMod/images/cardui/512/bg_power_lobotomy.png", "lobotomyMod/images/cardui/512/card_lobotomy_orb.png",
                "lobotomyMod/images/cardui/1024/bg_attack_lobotomy.png", "lobotomyMod/images/cardui/1024/bg_skill_lobotomy.png",
                "lobotomyMod/images/cardui/1024/bg_power_lobotomy.png", "lobotomyMod/images/cardui/1024/card_lobotomy_orb.png");
        BaseMod.addColor(AbstractCardEnum.Angela,
                Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(),
                "lobotomyMod/images/cardui/512/bg_attack_angela.png", "lobotomyMod/images/cardui/512/bg_skill_angela.png",
                "lobotomyMod/images/cardui/512/bg_power_angela.png", "lobotomyMod/images/cardui/512/card_angela_orb.png",
                "lobotomyMod/images/cardui/1024/bg_attack_angela.png", "lobotomyMod/images/cardui/1024/bg_skill_angela.png",
                "lobotomyMod/images/cardui/1024/bg_power_angela.png", "lobotomyMod/images/cardui/1024/card_angela_orb.png",
                "lobotomyMod/images/orbs/Angela/orb.png");
    }

    public static void addStrings(){
        if(Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            String characterStrings = Gdx.files.internal("lobotomyMod/localization/zhs/character.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(CharacterStrings.class, characterStrings);
            String cardStrings = Gdx.files.internal("lobotomyMod/localization/zhs/card.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
            String powerStrings = Gdx.files.internal("lobotomyMod/localization/zhs/power.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
            String relicStrings = Gdx.files.internal("lobotomyMod/localization/zhs/relic.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
            String potionStrings = Gdx.files.internal("lobotomyMod/localization/zhs/potion.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
            String uiStrings = Gdx.files.internal("lobotomyMod/localization/zhs/UI.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
            String monsterStrings = Gdx.files.internal("lobotomyMod/localization/zhs/monster.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(MonsterStrings.class, monsterStrings);
            String eventStrings = Gdx.files.internal("lobotomyMod/localization/zhs/event.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
            String tutorialStrings = Gdx.files.internal("lobotomyMod/localization/zhs/tutorials.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(TutorialStrings.class, tutorialStrings);
        }
        else if(Settings.language == Settings.GameLanguage.KOR) {
            String characterStrings = Gdx.files.internal("lobotomyMod/localization/kor/character.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(CharacterStrings.class, characterStrings);
            String cardStrings = Gdx.files.internal("lobotomyMod/localization/kor/card.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
            String powerStrings = Gdx.files.internal("lobotomyMod/localization/kor/power.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
            String relicStrings = Gdx.files.internal("lobotomyMod/localization/kor/relic.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
            String potionStrings = Gdx.files.internal("lobotomyMod/localization/kor/potion.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
            String uiStrings = Gdx.files.internal("lobotomyMod/localization/kor/UI.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
            String monsterStrings = Gdx.files.internal("lobotomyMod/localization/kor/monster.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(MonsterStrings.class, monsterStrings);
            String eventStrings = Gdx.files.internal("lobotomyMod/localization/kor/event.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
            String tutorialStrings = Gdx.files.internal("lobotomyMod/localization/kor/tutorials.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(TutorialStrings.class, tutorialStrings);
        }
        else if(Settings.language == Settings.GameLanguage.JPN) {
            String characterStrings = Gdx.files.internal("lobotomyMod/localization/jpn/character.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(CharacterStrings.class, characterStrings);
            String cardStrings = Gdx.files.internal("lobotomyMod/localization/jpn/card.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
            String powerStrings = Gdx.files.internal("lobotomyMod/localization/jpn/power.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
            String relicStrings = Gdx.files.internal("lobotomyMod/localization/jpn/relic.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
            String potionStrings = Gdx.files.internal("lobotomyMod/localization/jpn/potion.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
            String uiStrings = Gdx.files.internal("lobotomyMod/localization/jpn/UI.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
            String monsterStrings = Gdx.files.internal("lobotomyMod/localization/jpn/monster.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(MonsterStrings.class, monsterStrings);
            String eventStrings = Gdx.files.internal("lobotomyMod/localization/jpn/event.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
            String tutorialStrings = Gdx.files.internal("lobotomyMod/localization/jpn/tutorials.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(TutorialStrings.class, tutorialStrings);
        }
        else if(Settings.language == Settings.GameLanguage.RUS) {
            String characterStrings = Gdx.files.internal("lobotomyMod/localization/rus/character.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(CharacterStrings.class, characterStrings);
            String cardStrings = Gdx.files.internal("lobotomyMod/localization/rus/card.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
            String powerStrings = Gdx.files.internal("lobotomyMod/localization/rus/power.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
            String relicStrings = Gdx.files.internal("lobotomyMod/localization/rus/relic.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
            String potionStrings = Gdx.files.internal("lobotomyMod/localization/rus/potion.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
            String uiStrings = Gdx.files.internal("lobotomyMod/localization/rus/UI.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
            String monsterStrings = Gdx.files.internal("lobotomyMod/localization/rus/monster.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(MonsterStrings.class, monsterStrings);
            String eventStrings = Gdx.files.internal("lobotomyMod/localization/rus/event.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
            String tutorialStrings = Gdx.files.internal("lobotomyMod/localization/rus/tutorials.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(TutorialStrings.class, tutorialStrings);
        }
        else {
            String characterStrings = Gdx.files.internal("lobotomyMod/localization/eng/character.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(CharacterStrings.class, characterStrings);
            String cardStrings = Gdx.files.internal("lobotomyMod/localization/eng/card.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
            String powerStrings = Gdx.files.internal("lobotomyMod/localization/eng/power.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
            String relicStrings = Gdx.files.internal("lobotomyMod/localization/eng/relic.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
            String potionStrings = Gdx.files.internal("lobotomyMod/localization/eng/potion.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
            String uiStrings = Gdx.files.internal("lobotomyMod/localization/eng/UI.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
            String monsterStrings = Gdx.files.internal("lobotomyMod/localization/eng/monster.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(MonsterStrings.class, monsterStrings);
            String eventStrings = Gdx.files.internal("lobotomyMod/localization/eng/event.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
            String tutorialStrings = Gdx.files.internal("lobotomyMod/localization/eng/tutorials.json").readString(String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(TutorialStrings.class, tutorialStrings);
        }
    }

    class Keywords{
        Keyword[] Keyword;
    }

    public static void addKeyWords(){
        Gson gson = new Gson();
        Keywords keywords;
        if(Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            keywords = gson.fromJson(Gdx.files.internal("lobotomyMod/localization/zhs/keyword.json").readString(String.valueOf(StandardCharsets.UTF_8)), Keywords.class);
        }
        else if(Settings.language == Settings.GameLanguage.KOR) {
            keywords = gson.fromJson(Gdx.files.internal("lobotomyMod/localization/kor/keyword.json").readString(String.valueOf(StandardCharsets.UTF_8)), Keywords.class);
        }
        else if(Settings.language == Settings.GameLanguage.JPN) {
            keywords = gson.fromJson(Gdx.files.internal("lobotomyMod/localization/jpn/keyword.json").readString(String.valueOf(StandardCharsets.UTF_8)), Keywords.class);
        }
        else {
            keywords = gson.fromJson(Gdx.files.internal("lobotomyMod/localization/eng/keyword.json").readString(String.valueOf(StandardCharsets.UTF_8)), Keywords.class);
        }

        if(keywords != null) {
            for (Keyword key : keywords.Keyword) {
                BaseMod.addKeyword(key.NAMES, key.DESCRIPTION);
            }
        }
    }

    public static String lobotomyLockCardImage() {
        return "lobotomyMod/images/cards/normal/Hidden.png";
    }

    public static String lobotomyCardImage(final String id) {
        return "lobotomyMod/images/cards/normal/" + id + ".png";
    }

    public static String deriveCardImage(final String id) {
        return "lobotomyMod/images/cards/derive/" + id + ".png";
    }

    public static String angelaCardImage(final String id) {
        return "lobotomyMod/images/cards/angela/" + id + ".png";
    }

    public static String angelaBlackCardImage(final String id) {
        return "lobotomyMod/images/cards/angelaBlack/" + id + ".png";
    }

    public static String egoCardImage(final String id) {
        return "lobotomyMod/images/cards/ego/" + id + ".png";
    }

    public static String lobotomyRelicImage(final String id) {
        return "lobotomyMod/images/relics/" + id + ".png";
    }

    public static String lobotomyRelicOutlineImage(final String id) {
        return "lobotomyMod/images/relics/outline/" + id + ".png";
    }

    public static void addCards(){
        //BaseMod.addCard(new Dummy());
        abnormalityListCommon.add(new BeautyBeast());
        abnormalityListCommon.add(new Bloodbath());
        abnormalityListCommon.add(new CherryBlossoms());
        abnormalityListCommon.add(new CrumblingArmor());
        abnormalityListCommon.add(new FairyFestival());
        abnormalityListCommon.add(new MeatLantern());
        abnormalityListCommon.add(new MHz());
        abnormalityListCommon.add(new Murderer());
        abnormalityListCommon.add(new OldLady());
        abnormalityListCommon.add(new OSAHOGD());
        abnormalityListCommon.add(new PunishingBird());
        abnormalityListCommon.add(new Scorched());
        abnormalityListCommon.add(new ShyLook());
        abnormalityListCommon.add(new SpiderBud());
        abnormalityListCommon.add(new UniverseFragment());
        abnormalityListCommon.add(new VoidDream());
        abnormalityListCommon.add(new WallGazer());
        abnormalityListCommon.add(new Wellcheers());

        abnormalityListUncommon.add(new Alriune());
        abnormalityListUncommon.add(new BigBadWolf());
        abnormalityListUncommon.add(new BigBird());
        abnormalityListUncommon.add(new BlackSwan());
        abnormalityListUncommon.add(new DespairKnight());
        abnormalityListUncommon.add(new DreamingCurrent());
        abnormalityListUncommon.add(new FieryBird());
        abnormalityListUncommon.add(new Freischutz());
        abnormalityListUncommon.add(new Funeral());
        abnormalityListUncommon.add(new GalaxyChild());
        abnormalityListUncommon.add(new GreedKing());
        abnormalityListUncommon.add(new HappyTeddy());
        abnormalityListUncommon.add(new HeroicMonk());
        abnormalityListUncommon.add(new Laetitia());
        abnormalityListUncommon.add(new LaLuna());
        abnormalityListUncommon.add(new LittleHelper());
        abnormalityListUncommon.add(new LittlePrince());
        abnormalityListUncommon.add(new LongBird());
        abnormalityListUncommon.add(new Mercenary());
        abnormalityListUncommon.add(new NakedNest());
        abnormalityListUncommon.add(new Porccubus());
        abnormalityListUncommon.add(new QueenBee());
        abnormalityListUncommon.add(new QueenOfHatred());
        abnormalityListUncommon.add(new RedShoes());
        abnormalityListUncommon.add(new Rudolta());
        abnormalityListUncommon.add(new Scarecrow());
        abnormalityListUncommon.add(new SingingMachine());
        abnormalityListUncommon.add(new SnowQueen());
        abnormalityListUncommon.add(new Variant());
        abnormalityListUncommon.add(new WhitesApple());
        abnormalityListUncommon.add(new Woodsman());
        abnormalityListUncommon.add(new Yin());

        abnormalityListRare.add(new BlackArmy());
        abnormalityListRare.add(new BlueStar());
        abnormalityListRare.add(new BodiesMountain());
        abnormalityListRare.add(new CENSORED());
        abnormalityListRare.add(new MeltingLove());
        abnormalityListRare.add(new NothingThere());
        abnormalityListRare.add(new PlagueDoctor());
        abnormalityListRare.add(new SilentOrchestra());
        abnormalityListRare.add(new WhiteNight());

        for (AbstractCard card : abnormalityListCommon) {
            BaseMod.addCard(card.makeCopy());
        }
        for (AbstractCard card : abnormalityListUncommon) {
            BaseMod.addCard(card.makeCopy());
        }
        for (AbstractCard card : abnormalityListRare) {
            BaseMod.addCard(card.makeCopy());
        }

        BaseMod.addCard(new ApocalypseBird());

        BaseMod.addCard(new HereticApostle());
        BaseMod.addCard(new Apo_Beam());
        BaseMod.addCard(new Apo_Charge());
        BaseMod.addCard(new Apo_Execute());
        BaseMod.addCard(new Apo_Pray());
        BaseMod.addCard(new Apo_Slash());
        BaseMod.addCard(new Apo_SoulSlash());
        BaseMod.addCard(new test());
        BaseMod.addCard(new Ordeal());

        BaseMod.addCard(new SpecialBullet());
        BaseMod.addCard(new HpAim());
        BaseMod.addCard(new SpAim());
        BaseMod.addCard(new RedAim());
        BaseMod.addCard(new WhiteAim());
        BaseMod.addCard(new BlackAim());
        BaseMod.addCard(new PaleAim());
        BaseMod.addCard(new SlowAim());
        BaseMod.addCard(new ExecuteAim());

        BaseMod.addCard(new ControlCode());
        BaseMod.addCard(new InformationCode());
        BaseMod.addCard(new SecurityCode());
        BaseMod.addCard(new TrainingCode());
        BaseMod.addCard(new CentralCode());
        BaseMod.addCard(new WelfareCode());
        BaseMod.addCard(new DisciplinaryCode());
        BaseMod.addCard(new RecordCode());
        BaseMod.addCard(new ExtractionCode());

        UnlockTracker.unlockCard(HpAim.ID);
        UnlockTracker.unlockCard(SpAim.ID);
        UnlockTracker.unlockCard(RedAim.ID);
        UnlockTracker.unlockCard(WhiteAim.ID);
        UnlockTracker.unlockCard(BlackAim.ID);
        UnlockTracker.unlockCard(PaleAim.ID);
        UnlockTracker.unlockCard(SlowAim.ID);
        UnlockTracker.unlockCard(ExecuteAim.ID);

        BaseMod.addCard(new TT2Protocol());
        BaseMod.addCard(new SummonCall());

        BaseMod.addCard(new Beak());
        BaseMod.addCard(new CherryBlossom());
        BaseMod.addCard(new CUTE());
        BaseMod.addCard(new FourthMatchFlame());
        BaseMod.addCard(new Horn());
        BaseMod.addCard(new Lantern());
        BaseMod.addCard(new Loneliness());
        BaseMod.addCard(new Noise());
        BaseMod.addCard(new Penitence());
        BaseMod.addCard(new RapturousDream());
        BaseMod.addCard(new RedEyes());
        BaseMod.addCard(new Regret_ego());
        BaseMod.addCard(new Soda());
        BaseMod.addCard(new SomewhereSpear());
        BaseMod.addCard(new TodaysExpression());
        BaseMod.addCard(new Tough());
        BaseMod.addCard(new Wingbeat());
        BaseMod.addCard(new WristCuter());

        BaseMod.addCard(new Amita());
        BaseMod.addCard(new BearPaw());
        BaseMod.addCard(new BlackSwan_ego());
        BaseMod.addCard(new BloodyDesire());
        BaseMod.addCard(new BlueScar());
        BaseMod.addCard(new Christmas());
        BaseMod.addCard(new CrimsonScar());
        BaseMod.addCard(new Diffraction());
        BaseMod.addCard(new Discord());
        BaseMod.addCard(new Ecstasy());
        BaseMod.addCard(new FeatherOfHonor());
        BaseMod.addCard(new FrostShard());
        BaseMod.addCard(new Galaxy());
        BaseMod.addCard(new Gaze());
        BaseMod.addCard(new GreenStem());
        BaseMod.addCard(new GrinderMk4());
        BaseMod.addCard(new Harmony());
        BaseMod.addCard(new Harvest());
        BaseMod.addCard(new Heaven());
        BaseMod.addCard(new Hornet());
        BaseMod.addCard(new Hypocrisy());
        BaseMod.addCard(new InTheNameOfLoveAndHate());
        BaseMod.addCard(new Laetitia_ego());
        BaseMod.addCard(new Lamp());
        BaseMod.addCard(new LifeForTheDareDevil());
        BaseMod.addCard(new Logging());
        BaseMod.addCard(new MagicBullet());
        BaseMod.addCard(new Moonlight());
        BaseMod.addCard(new Pleasure());
        BaseMod.addCard(new Reverberation());
        BaseMod.addCard(new ScreamingWedge());
        BaseMod.addCard(new ShedSkin());
        BaseMod.addCard(new SolemnVow());
        BaseMod.addCard(new Spore());
        BaseMod.addCard(new SwordSharpenedByTears());

        BaseMod.addCard(new Adoration());
        BaseMod.addCard(new CENSORED_ego());
        BaseMod.addCard(new DaCapo());
        BaseMod.addCard(new GoldRush());
        BaseMod.addCard(new Justitia());
        BaseMod.addCard(new Mimicry());
        BaseMod.addCard(new ParadiseLost());
        BaseMod.addCard(new Pinks());
        BaseMod.addCard(new SoundOfStar());
        BaseMod.addCard(new TheSmile());
        BaseMod.addCard(new lobotomyMod.card.ego.rare.Twilight());
    }

    public static void addRelics(){
        RelicLibrary.add(new CogitoBucket());
//        RelicLibrary.add(new ApostleMask());
//        RelicLibrary.add(new DeathAngel());
//        RelicLibrary.add(new Twilight());
//        RelicLibrary.add(new HonorFeather());
        RelicLibrary.add(new RabbitCall());
        RelicLibrary.add(new AtMidnight());

        LobotomyUtils.addEgoRelic(new ApostleMask());
        LobotomyUtils.addEgoRelic(new DeathAngel());
        LobotomyUtils.addEgoRelic(new Twilight());
        LobotomyUtils.addEgoRelic(new HonorFeather());

        abnormalityListRelic.add(new Theresia());
        abnormalityListRelic.add(new AspirationHeart());
        abnormalityListRelic.add(new ResearcherNote());
        abnormalityListRelic.add(new FleshIdol());
        abnormalityListRelic.add(new TreeSap());
        abnormalityListRelic.add(new AdjustmentMirror());
        abnormalityListRelic.add(new ChangeAnything());
        abnormalityListRelic.add(new HellTrain());
        abnormalityListRelic.add(new SkinProphecy());
        abnormalityListRelic.add(new LuminousBracelet());
        abnormalityListRelic.add(new BehaviorAdjustment());
        abnormalityListRelic.add(new BackwardClock());

        for (AbstractLobotomyAbnRelic relic : abnormalityListRelic) {
            LobotomyUtils.addAbnormalityRelic(relic.makeCopy());
        }
        //RelicLibrary.add(new Yang());
        LobotomyUtils.addAbnormalityRelic(new Yang());
    }

    public static void addPotions(){
//        BaseMod.addPotion(TestPotion.class, Color.SKY.cpy(),Color.NAVY.cpy(),null,TestPotion.POTION_ID);
    }

    public static void firstStart(){
        LobotomyMod.usedFixer = 0;
        LobotomyMod.meltdownCode = 0;
        LobotomyMod.hasYin = false;
        LobotomyMod.hasBackward = false;
        LobotomyMod.activeFixer = false;
        System.arraycopy(LobotomyMod.levelSave, 0, CogitoBucket.level, 0, LobotomyMod.levelSave.length);
        LobotomyCardPool.reload = false;
        LobotomyCardPool.addCardPool();
        CogitoBucket.level[0] = 1;
        CogitoBucket.npcs_t.clear();
        for(int i = 0; i < Angela.departments.length; i ++) {
            LobotomyMod.departments[i] = 0;
            Angela.departments[i] = 0;
        }
        try {
            LobotomyMod.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadStart(){
        LobotomyCardPool.reload = true;
        LobotomyCardPool.reloadCardPool();

        Freischutz.reset();
        Angela.reset();

        if(DungeonMap.boss == null){
            DungeonMap.boss = ImageMaster.loadImage("lobotomyMod/images/ui/map/boss/claw.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("lobotomyMod/images/ui/map/bossOutline/claw.png");
        }

        CogitoBucket relic = (CogitoBucket) AbstractDungeon.player.getRelic(CogitoBucket.ID);
        if(relic != null){
            CogitoBucket.reEnter = true;
        }
    }

    public static void addRecallAbnormality(){
        RecallAbnormalityAction.recallMap.put(WhiteNight.ID, new RecallAbnormalityAction.relicAndRunnable(()->{
            AbstractDungeon.player.masterDeck.removeCard(WhiteNight.ID);
            AbstractDungeon.effectList.add(new LatterEffect(() -> {
                AbstractDungeon.currMapNode.room = new EventRoom();
                AbstractDungeon.getCurrRoom().event = new WhiteNightEvent(true);
                AbstractDungeon.getCurrRoom().event.onEnterRoom();
                CardCrawlGame.fadeIn(1.5F);
                AbstractDungeon.rs = AbstractDungeon.RenderScene.NORMAL;
                AbstractDungeon.overlayMenu.hideCombatPanels();
            }, 0.1F));
        }){
            @Override
            public boolean canUse(AbstractRelic relic) {
                if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss || AbstractDungeon.getCurrRoom() instanceof VictoryRoom){
                    return false;
                }
                return (!AbstractDungeon.player.hasRelic(DeathAngel.ID) && relic.counter >= 100);
            }

            @Override
            public void cost(AbstractRelic relic) {
                relic.counter -= 100;
            }
        });
    }
}
