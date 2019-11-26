package lobotomyMod.character;

import basemod.abstracts.CustomPlayer;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.PrismaticShard;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.angelaCard.bullets.SpecialBullet;
import lobotomyMod.card.angelaCard.department.*;
import lobotomyMod.patch.AbstractCardEnum;
import lobotomyMod.patch.CharacterEnum;
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
    public static int departments[] = LobotomyMod.departments.clone();

    public Angela(final String name) {
        super(name, CharacterEnum.Angela, orbTextures, orbVfx, (String) null, null);
        this.drawY -= 30;

        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 220.0F * Settings.scale);

        initializeClass(null, "lobotomyMod/images/characters/angela/angela_white/shoulder.png", "lobotomyMod/images/characters/angela/angela_white/shoulder2.png", "lobotomyMod/images/characters/angela/angela_white/corpse.png",

                getLoadout(), 0.0F, 0.0F, 240.0F, 400.0F, new EnergyManager(3));

        loadAnimation("lobotomyMod/images/characters/angela/angela_white/idle/angela_white.atlas", "lobotomyMod/images/characters/angela/angela_white/idle/angela_white.json", 2.8F);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "newAnimation", true);
        e.setTimeScale(0.6F);
        this.bless = true;
    }

    @Override
    public void movePosition(float x, float y) {
        super.movePosition(x, y);
        this.drawY -= 30;
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
            this.bless = false;
            if(LobotomyMod.activeTutorials[3]) {
                AbstractDungeon.ftue = new LobotomyFtue(3);
            }
            else {
                this.bless();
            }
        }
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

    @Override
    public void onVictory() {
        AbstractDungeon.getCurrRoom().rewards.add(new LobPoint());
        super.onVictory();
    }

    public void changeAim(AimType aimType){
        this.aimType = aimType;
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
    }

    static {
        orbTextures = new String[] {
                "lobotomyMod/images/ui/topPanel/Angela/layer1.png","lobotomyMod/images/ui/topPanel/Angela/layer2.png","lobotomyMod/images/ui/topPanel/Angela/layer3.png","lobotomyMod/images/ui/topPanel/Angela/layer4.png","lobotomyMod/images/ui/topPanel/Angela/layer5.png","lobotomyMod/images/ui/topPanel/Angela/layer6.png",
                "lobotomyMod/images/ui/topPanel/Angela/layer1d.png","lobotomyMod/images/ui/topPanel/Angela/layer2d.png","lobotomyMod/images/ui/topPanel/Angela/layer3d.png","lobotomyMod/images/ui/topPanel/Angela/layer4d.png","lobotomyMod/images/ui/topPanel/Angela/layer5d.png",
        };
        orbVfx = "lobotomyMod/images/ui/topPanel/energyAngelaVFX.png";
    }
}
