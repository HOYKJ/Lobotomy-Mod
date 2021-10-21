package lobotomyMod.character;

import basemod.abstracts.CustomPlayer;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.green.Burst;
import com.megacrit.cardcrawl.cards.red.DoubleTap;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BurstPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.DoubleTapPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.PandorasBox;
import com.megacrit.cardcrawl.relics.PrismaticShard;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.ChooseAction;
import lobotomyMod.action.unique.RecordCodeAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.angelaCard.bullets.SpecialBullet;
import lobotomyMod.card.angelaCard.code.AbstractCodeCard;
import lobotomyMod.card.angelaCard.department.*;
import lobotomyMod.patch.AbstractCardEnum;
import lobotomyMod.patch.CharacterEnum;
import lobotomyMod.power.TurnEchoPower;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.RabbitCall;
import lobotomyMod.reward.CogitoReward;
import lobotomyMod.reward.LobPoint;
import lobotomyMod.ui.LobotomyFtue;
import lobotomyMod.vfx.action.ChooseEffect;
import lobotomyMod.vfx.action.LatterEffect;
import lobotomyMod.vfx.aimSelect.AimSelectBack;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class Angela extends CustomPlayer {
    public static final String[] orbTextures;
    public static final String orbVfx;
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("Angela");
    public static final String[] NAMES = characterStrings.NAMES;
    public static final String[] TEXT = characterStrings.TEXT;
    public AimSelectBack aimSelectBack;
    public boolean bless;

    public enum AimType
    {
        NORMAL, HP, SP, RED, WHITE, BLACK, PALE, SLOW, EXECUTE
    }
    public AimType aimType = AimType.NORMAL;
    public static int departments[] = LobotomyMod.departments.clone(), tmpD[];

    public Angela(final String name) {
        super(name, CharacterEnum.Angela, orbTextures, orbVfx, (String) null, null);
        this.drawY -= 30;

        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 220.0F * Settings.scale);

        if(LobotomyMod.useBlackAngela){
            initializeClass(null, "lobotomyMod/images/characters/angela/angela_black/shoulder.png",
                    "lobotomyMod/images/characters/angela/angela_black/shoulder2.png", "lobotomyMod/images/characters/angela/angela_black/corpse.png",
                    getLoadout(), 0.0F, 0.0F, 240.0F, 400.0F, new EnergyManager(3));

            loadAnimation("lobotomyMod/images/characters/angela/angela_black/idle/angela_black.atlas", "lobotomyMod/images/characters/angela/angela_black/idle/angela_black.json", 2.8F);

            AnimationState.TrackEntry e = this.state.setAnimation(0, "newAnimation", true);
            e.setTimeScale(1.0F);
        }
        else {
            initializeClass(null, "lobotomyMod/images/characters/angela/angela_white/shoulder.png",
                    "lobotomyMod/images/characters/angela/angela_white/shoulder2.png", "lobotomyMod/images/characters/angela/angela_white/corpse.png",
                    getLoadout(), 0.0F, 0.0F, 240.0F, 400.0F, new EnergyManager(3));

            loadAnimation("lobotomyMod/images/characters/angela/angela_white/idle/angela_white.atlas", "lobotomyMod/images/characters/angela/angela_white/idle/angela_white.json", 2.8F);

            AnimationState.TrackEntry e = this.state.setAnimation(0, "newAnimation", true);
            e.setTimeScale(0.6F);
        }
        this.bless = true;
    }

    @Override
    public void movePosition(float x, float y) {
        super.movePosition(x, y);
        if (y >= AbstractDungeon.floorY) {
            this.drawY -= 30;
        }
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(SpecialBullet.ID);
        retVal.add(SpecialBullet.ID);
        retVal.add(SpecialBullet.ID);
        retVal.add(SpecialBullet.ID);
        retVal.add(SpecialBullet.ID);
        retVal.add(SpecialBullet.ID);
        retVal.add(SpecialBullet.ID);
        retVal.add(SpecialBullet.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(CogitoBucket.ID);
        UnlockTracker.markRelicAsSeen(CogitoBucket.ID);
//        retVal.add(PrismaticShard.ID);
//        UnlockTracker.markRelicAsSeen(PrismaticShard.ID);
        if(LobotomyMod.activeRabbit){
            retVal.add(RabbitCall.ID);
            UnlockTracker.markRelicAsSeen(RabbitCall.ID);
        }
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout(){
        return new CharSelectInfo(NAMES[0], TEXT[0],
                70, 70, 0, 100, 5,
                this, getStartingRelics(), getStartingDeck(), false);
    }

    public String getLocalizedCharacterName()
    {
        return NAMES[0];
    }

    public AbstractCard.CardColor getCardColor()
    {
        return AbstractCardEnum.Angela;
    }

    public Color getCardTrailColor()
    {
        return Color.WHITE.cpy();
    }

    public AbstractPlayer newInstance()
    {
        return new Angela(this.name);
    }

    public int getAscensionMaxHPLoss()
    {
        return 0;
    }

    public AbstractCard getStartCardForEvent()
    {
        return new Strike_Red();
    }

    public String getTitle(AbstractPlayer.PlayerClass playerClass)
    {
        return NAMES[0];
    }

    public void doCharSelectScreenSelectEffect()
    {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_2", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    public String getCustomModeCharacterButtonSoundKey()
    {
        return "";
    }

    public BitmapFont getEnergyNumFont()
    {
        return FontHelper.energyNumFontBlue;
    }

    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect()
    {
        return new AbstractGameAction.AttackEffect[] { AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL };
    }

    public String getVampireText()
    {
        return com.megacrit.cardcrawl.events.city.Vampires.DESCRIPTIONS[1];
    }

    public Color getCardRenderColor()
    {
        return Color.WHITE.cpy();
    }

    public void updateOrb(int orbCount)
    {
        this.energyOrb.updateOrb(orbCount);
    }

    public Color getSlashAttackColor()
    {
        return Color.WHITE.cpy();
    }

    public String getSpireHeartText()
    {
        return TEXT[1];
    }

    @Override
    public void applyStartOfCombatLogic() {
        super.applyStartOfCombatLogic();
        if(departments[3] > 3){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 3), 3));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 3), 3));
        }
        else if(departments[3] > 1){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
        }
        if(departments[Yesod.departmentCode[0]] == 5){
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                m.maxHealth *= 0.75F;
                if(m.currentHealth > m.maxHealth){
                    m.currentHealth = m.maxHealth;
                }
                m.healthBarUpdatedEvent();
            }
        }
    }

    @Override
    public void applyStartOfCombatPreDrawLogic() {
        super.applyStartOfCombatPreDrawLogic();
        if(departments[Hod.departmentCode[0]] == 5){
            for (AbstractCard card : AbstractDungeon.player.drawPile.group){
                if(card.baseDamage > 0){
                    card.baseDamage *= 1.25F;
                }
                if(card.baseBlock > 0){
                    card.baseBlock *= 1.25F;
                }
            }
        }
    }

    @Override
    public void applyStartOfTurnRelics() {
        super.applyStartOfTurnRelics();
        if(departments[Chesed.departmentCode[0]] == 5){
            AbstractDungeon.actionManager.addToBottom(new RecordCodeAction(1));
        }
        if(departments[Geburah.departmentCode[0]] == 5){
            final ChooseAction choice = new ChooseAction(null, null, AbstractLobotomyCard.EXTENDED_DESCRIPTION[2],true, 1, true);
            choice.add(new DoubleTap(), ()->{
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new DoubleTapPower(this, 1), 1));
            });
            choice.add(new Burst(), ()->{
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BurstPower(this, 1), 1));
            });
            choice.add(new EchoForm(), Geburah.EXTENDED_DESCRIPTION[8], ()->{
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new TurnEchoPower(this, 1), 1));
            });
            AbstractDungeon.actionManager.addToBottom(choice);
        }
    }

    @Override
    public void update() {
        super.update();

        if(this.aimSelectBack == null){
            this.aimSelectBack = new AimSelectBack();
        }
        if(AbstractDungeon.player instanceof Angela) {
            Settings.hideCombatElements = Angela.departments[1] < 2;
        }
        for(int i : departments){
            if(i > 0){
                return;
            }
        }
        if(this.bless){
            for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
                if(card instanceof AbstractCodeCard){
                    AbstractDungeon.player.masterDeck.removeCard(card);
                }
            }
            this.bless = false;
            if(LobotomyMod.activeTutorials[3]) {
                AbstractDungeon.ftue = new LobotomyFtue(3);
            }
            else {
                this.bless();
            }
        }
    }

    @Override
    public void onVictory() {
        AbstractDungeon.getCurrRoom().rewards.add(new LobPoint());
        super.onVictory();
    }

    public void bless(){
        AbstractDungeon.effectList.add(new LatterEffect(()->{
//            if(!this.bless){
//                return;
//            }
//            this.bless = false;
            final ChooseEffect choice = new ChooseEffect(null, null, TEXT[2], false, 3);
            AbstractDepartmentCard tmp = new Malkuth(true);
            choice.add(tmp, tmp::tackAction);
            tmp = new Yesod(true);
            choice.add(tmp, tmp::tackAction);
            tmp = new Netzach(true);
            choice.add(tmp, tmp::tackAction);
            tmp = new Hod(true);
            choice.add(tmp, tmp::tackAction);
            tmp = new Tiphereth(true);
            choice.add(tmp, tmp::tackAction);
            tmp = new Chesed(true);
            choice.add(tmp, tmp::tackAction);
            tmp = new Geburah(true);
            choice.add(tmp, tmp::tackAction);
            tmp = new Hokma(true);
            choice.add(tmp, tmp::tackAction);
            tmp = new Binah(true);
            choice.add(tmp, tmp::tackAction);
            AbstractDungeon.effectsQueue.add(choice);
        }));
    }

    public static void reset(){
        if(tmpD != null){
            departments = tmpD.clone();
        }
    }

    @Override
    public boolean hasRelic(String targetID) {
        if(departments[1] < 3 && targetID.equals("Runic Dome")){
            return true;
        }
        else if(departments[Binah.departmentCode[0]] > 3 && (targetID.equals("Molten Egg 2") || targetID.equals("Toxic Egg 2") || targetID.equals("Frozen Egg 2"))){
            return true;
        }
        return super.hasRelic(targetID);
    }

    public void changeAim(AimType aimType){
        this.aimType = aimType;
    }

    @SpirePatch(
            clz= AbstractDungeon.class,
            method="initializeRelicList"
    )
    public static class initializeRelicList {
        @SpirePrefixPatch
        public static void prefix(){
            if(AbstractDungeon.player instanceof Angela) {
                AbstractDungeon.relicsToRemoveOnStart.add(PandorasBox.ID);
            }
        }
    }

    static {
        orbTextures = new String[] {
                "lobotomyMod/images/ui/topPanel/Angela/layer1.png","lobotomyMod/images/ui/topPanel/Angela/layer2.png","lobotomyMod/images/ui/topPanel/Angela/layer3.png","lobotomyMod/images/ui/topPanel/Angela/layer4.png","lobotomyMod/images/ui/topPanel/Angela/layer5.png","lobotomyMod/images/ui/topPanel/Angela/layer6.png",
                "lobotomyMod/images/ui/topPanel/Angela/layer1d.png","lobotomyMod/images/ui/topPanel/Angela/layer2d.png","lobotomyMod/images/ui/topPanel/Angela/layer3d.png","lobotomyMod/images/ui/topPanel/Angela/layer4d.png","lobotomyMod/images/ui/topPanel/Angela/layer5d.png",
        };
        orbVfx = "lobotomyMod/images/ui/topPanel/energyAngelaVFX.png";
    }
}
