package lobotomyMod.monster.sephirah;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.attachments.Attachment;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.scenes.TheCityScene;
import com.megacrit.cardcrawl.scenes.TheEndingScene;
import lobotomyMod.action.animation.GeburahDash;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.monster.sephirah.Binah.Binah;
import lobotomyMod.power.ResistantPower;
import lobotomyMod.vfx.GeburahDaCapo;
import lobotomyMod.vfx.GeburahGreed;
import lobotomyMod.vfx.GeburahSpear;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author hoykj
 */
public class Geburah extends AbstractOrdealMonster {
    public static final String ID = "Geburah";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Geburah");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int stage, turns, loseHP;
    public float resistance;

    public Geburah(float x, float y) {
        super(NAME, "Geburah", 400, 0.0F, -12.0F, 240.0F, 360.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Sephirah/Geburah/gebura.atlas", "lobotomyMod/images/monsters/Sephirah/Geburah/gebura.json", 3.2F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Phase_01_Default", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Phase_01_Default", "Phase_01_Attack_01", 0.1F);
        this.stateData.setMix("Phase_01_Default", "Phase_02_Attack_01", 0.1F);
        this.stateData.setMix("Phase_01_Default", "Phase_03_Attack_01", 0.1F);

        this.stateData.setMix("Phase_02_Default", "Phase_02_Attack_01", 0.1F);
        this.stateData.setMix("Phase_02_Default", "Phase_02_Attack_02", 0.1F);
        this.stateData.setMix("Phase_02_Default", "Phase_02_Attack_03_Cast", 0.1F);
        this.stateData.setMix("Phase_02_Attack_03_End", "Phase_02_Default", 0.1F);
        this.stateData.setMix("Phase_02_Default", "Phase_02_03_Spear", 0.1F);
        this.stateData.setMix("Phase_02_03_Spear", "Phase_02_Default", 0.1F);

        this.stateData.setMix("Phase_03_Default", "Phase_03_Attack_01", 0.1F);
        this.stateData.setMix("Phase_03_Default", "Phase_03_Attack_02", 0.1F);
        this.stateData.setMix("Phase_03_Default", "Phase_02_03_Spear", 0.1F);
        this.stateData.setMix("Phase_02_03_Spear", "Phase_03_Default", 0.1F);

        this.stateData.setMix("Phase_01_Default", "Phase_All_Teleport_01", 0.2F);
        this.stateData.setMix("Phase_03_Default", "Phase_All_Teleport_01", 0.2F);
        this.stateData.setMix("Phase_All_Teleport_03", "Phase_01_Default", 0.2F);
        this.stateData.setMix("Phase_All_Teleport_03", "Phase_03_Default", 0.2F);
        this.damage.add(new DamageInfo(this, 16));
        this.damage.add(new DamageInfo(this, 45));
        this.damage.add(new DamageInfo(this, 15));
        this.damage.add(new DamageInfo(this, 50));
        this.damage.add(new DamageInfo(this, 40));
        this.damage.add(new DamageInfo(this, 70));
        this.damage.add(new DamageInfo(this, 55));
        this.damage.add(new DamageInfo(this, (int)(AbstractDungeon.player.maxHealth * 0.5F)));
        this.damage.add(new DamageInfo(this, 100));
        this.damage.add(new DamageInfo(this, (int)(AbstractDungeon.player.maxHealth * 0.9F)));
        this.turns = 0;
        this.stage = 1;
        this.resistance = 0.95F;
        this.dialogX = -70;
        this.dialogY = 90;
        this.type = EnemyType.BOSS;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        AbstractDungeon.getCurrRoom().playBgmInstantly("Geburah.mp3");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ResistantPower(this)));
        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, this.getWord(0)));
    }

    protected void getMove(int num) {
        switch (this.stage){
            case 1:
                this.resistance = 0.95F;
                switch (this.turns){
                    case 0:
                        setMove(MOVES[0], (byte) 1, Intent.ATTACK, this.damage.get(0).base);
                        break;
                    case 1:
                        setMove(MOVES[1], (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
                        break;
                    case 2:
                        setMove(MOVES[2], (byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(0).base, 2, true);
                        break;
                }
                break;
            case 2:
                this.resistance = 0.8F;
                switch (this.turns){
                    case 0:
                        setMove(MOVES[3], (byte) 11, Intent.ATTACK, this.damage.get(1).base);
                        break;
                    case 1:
                        setMove(MOVES[4], (byte) 12, Intent.ATTACK_DEBUFF, this.damage.get(2).base, 3, true);
                        break;
                    case 2:
                        setMove((byte) 13, Intent.ATTACK_DEBUFF, this.damage.get(3).base);
                        break;
                }
                break;
            case 3:
                this.resistance = 0.6F;
                switch (this.turns){
                    case 0:
                        setMove(MOVES[6], (byte) 21, Intent.ATTACK_DEBUFF, this.damage.get(6).base);
                        break;
                    case 1:
                        this.damage.get(7).base = (int)(AbstractDungeon.player.maxHealth * 0.5F);
                        setMove(MOVES[7], (byte) 22, Intent.ATTACK, this.damage.get(7).base);
                        break;
                    case 2:
                        setMove((byte) 23, Intent.ATTACK_DEBUFF, this.damage.get(3).base);
                        break;
                }
                break;
            case 4:
                this.resistance = 1.2F;
                switch (this.turns){
                    case 0: case 1:
                        switch (AbstractDungeon.aiRng.random(3)){
                            case 0:
                                setMove((byte) 31, Intent.ATTACK, this.damage.get(6).base, 2, true);
                                break;
                            case 1:
                                setMove((byte) 32, Intent.ATTACK_DEBUFF, this.damage.get(6).base, 2, true);
                                break;
                            case 2:
                                setMove((byte) 33, Intent.ATTACK_DEBUFF, this.damage.get(6).base, 2, true);
                                break;
                            case 3:
                                this.damage.get(9).base = (int)(AbstractDungeon.player.maxHealth * 0.9F);
                                setMove((byte) 34, Intent.ATTACK, this.damage.get(9).base, 2, true);
                                break;
                        }
                        break;
                    case 2:
                        switch (AbstractDungeon.aiRng.random(3)){
                            case 0:
                                setMove((byte) 35, Intent.ATTACK, this.damage.get(6).base, 3, true);
                                break;
                            case 1:
                                setMove((byte) 36, Intent.ATTACK_DEBUFF, this.damage.get(6).base, 3, true);
                                break;
                            case 2:
                                setMove((byte) 37, Intent.ATTACK_DEBUFF, this.damage.get(6).base, 3, true);
                                break;
                            case 3:
                                this.damage.get(9).base = (int)(AbstractDungeon.player.maxHealth * 0.9F);
                                setMove((byte) 38, Intent.ATTACK, this.damage.get(9).base, 3, true);
                                break;
                        }
                        break;
                }
                break;
        }
        this.turns ++;
        if(stage == 4){
            if(this.turns > 3){
                this.turns = 0;
            }
        }
        else {
            if(this.turns > 2){
                this.turns = 0;
            }
        }
        //setMove(MOVES[1], (byte) 35, Intent.ATTACK, this.damage.get(6).base, 3, true);
    }

    public void takeTurn() {
        switch (this.nextMove){
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK0101"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                }, 0.8F));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK0102"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                            new WeakPower(AbstractDungeon.player, 1, true), 1));
                }, 0.8F));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK0103"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                            new WeakPower(AbstractDungeon.player, 1, true), 1));
                }, 1.3F));
                break;

            case 11:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK0201"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                }, 1.0F));
                break;
            case 12:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK0202"));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }, 0.5F));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                            new WeakPower(AbstractDungeon.player, 1, true), 1));
                }, 0.5F));
                break;
            case 13:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "SPEAR02"));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getWord(5)));
                AbstractDungeon.effectList.add(new GeburahSpear(this, this.damage.get(3)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                        new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                break;

            case 21:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK0301"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(6), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                }, 3.0F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                        new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                break;
            case 22:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK0302"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(7), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                }, 1.1F));
                break;
            case 23:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "SPEAR03"));
                AbstractDungeon.effectList.add(new GeburahSpear(this, this.damage.get(3)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                        new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                break;

            case 31:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "RUN01"));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getWord(6)));
                AbstractDungeon.actionManager.addToBottom(new GeburahDash(this, 4, this.damage.get(6)));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(6), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                }, 1.0F));
                break;
            case 32:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "RUN01"));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getWord(6)));
                AbstractDungeon.actionManager.addToBottom(new GeburahDash(this, 4, this.damage.get(6)));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(6), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                }, 1.0F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                        new WeakPower(AbstractDungeon.player, 1, true), 1));
                break;
            case 33:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "RUN01"));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getWord(6)));
                AbstractDungeon.actionManager.addToBottom(new GeburahDash(this, 4, this.damage.get(6)));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(6), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                }, 1.0F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                        new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                break;
            case 34:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "RUN01"));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getWord(6)));
                AbstractDungeon.actionManager.addToBottom(new GeburahDash(this, 4, this.damage.get(9)));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(9), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                }, 1.0F));
                break;
            case 35:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "RUN02"));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getWord(7)));
                AbstractDungeon.effectList.add(new GeburahGreed(this, this.damage.get(6)));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(6), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                }, 1.0F));
                break;
            case 36:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "RUN02"));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getWord(7)));
                AbstractDungeon.effectList.add(new GeburahGreed(this, this.damage.get(6)));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(6), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                }, 1.0F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                        new WeakPower(AbstractDungeon.player, 1, true), 1));
                break;
            case 37:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "RUN02"));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getWord(7)));
                AbstractDungeon.effectList.add(new GeburahGreed(this, this.damage.get(6)));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(6), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                }, 1.0F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                        new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                break;
            case 38:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "RUN02"));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getWord(7)));
                AbstractDungeon.effectList.add(new GeburahGreed(this, this.damage.get(9)));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(9), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                }, 1.0F));
                break;

            case 41:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK_TELEPORT_START"));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getWord(4)));
                AbstractDungeon.actionManager.addToBottom(new GeburahDash(this, 1, this.damage.get(3)));
                this.loseHP -= this.maxHealth * 0.33F;
                break;
            case 42:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK0203"));
                AbstractDungeon.effectList.add(new GeburahDaCapo(this, this.damage.get(4), false));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                        new WeakPower(AbstractDungeon.player, 1, true), 1));
                this.loseHP -= this.maxHealth * 0.33F;
                break;
            case 43:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK_TELEPORT_START"));
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getWord(4)));
                AbstractDungeon.actionManager.addToBottom(new GeburahDash(this, 3, this.damage.get(8)));
                this.loseHP -= this.maxHealth * 0.33F;
                break;
            case 44:
                this.halfDead = false;
                if (this.maxHealth < 1){
                    this.maxHealth = 1;
                    this.showHealthBar();
                }
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "01TO02"));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, this.getWord(1)));
                break;
            case 45:
                this.halfDead = false;
                if (this.maxHealth < 1){
                    this.maxHealth = 1;
                    this.showHealthBar();
                }
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "02TO03"));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, this.getWord(2)));
                AbstractDungeon.effectList.add(new GeburahDaCapo(this, this.damage.get(5), true));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                        new WeakPower(AbstractDungeon.player, 1, true), 1));
                break;
            case 46:
                this.halfDead = false;
                if (this.maxHealth < 1){
                    this.maxHealth = 1;
                    this.showHealthBar();
                }
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "03TO04"));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, this.getWord(3)));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(6)));
                }, 2.1F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                        new VulnerablePower(AbstractDungeon.player, 1, true), 1));
                break;
        }
        if(!this.hasPower(ResistantPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ResistantPower(this)));
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void damage(DamageInfo info) {
        int tmp = this.currentHealth;
        super.damage(info);
        if(this.halfDead){
            return;
        }
        this.loseHP += tmp - this.currentHealth;
        if(this.loseHP >= this.maxHealth * 0.33F){
            switch (this.stage){
                case 1:
                    setMove((byte) 41, Intent.ATTACK, this.damage.get(3).base);
                    createIntent();
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 41, Intent.ATTACK, this.damage.get(3).base));
                    break;
                case 2:
                    setMove(MOVES[5], (byte) 42, Intent.ATTACK_DEBUFF, this.damage.get(3).base, 2, true);
                    createIntent();
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 42, Intent.ATTACK_DEBUFF, this.damage.get(4).base, 2, true));
                    break;
                case 3:
                    setMove((byte) 43, Intent.ATTACK, this.damage.get(8).base);
                    createIntent();
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 43, Intent.ATTACK, this.damage.get(8).base));
                    break;
            }
        }
    }

    public void die() {
        if(this.halfDead){
            return;
        }
        this.loseHP = 0;
        this.turns = 0;
        switch (this.stage){
            case 1:
                this.halfDead = true;
                this.stage ++;
                setMove((byte)44, Intent.UNKNOWN);
                createIntent();
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)44, Intent.UNKNOWN));
                break;
            case 2:
                this.halfDead = true;
                this.stage ++;
                setMove((byte) 45, Intent.ATTACK_DEBUFF, this.damage.get(5).base);
                createIntent();
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 45, Intent.ATTACK_DEBUFF, this.damage.get(5).base));
                break;
            case 3:
                this.halfDead = true;
                this.stage ++;
                setMove((byte) 46, Intent.ATTACK_DEBUFF, this.damage.get(6).base);
                createIntent();
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 46, Intent.ATTACK_DEBUFF, this.damage.get(6).base));
                break;
            case 4:
                super.die();
                this.changeState("DIE");
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, this.getWord(8)));
                this.deathTimer += 3.8F;
                break;
        }
        for (Object s = this.powers.iterator(); ((Iterator)s).hasNext();)
        {
            AbstractPower p = (AbstractPower)((Iterator)s).next();
            if (p.type == AbstractPower.PowerType.DEBUFF) {
                ((Iterator)s).remove();
            }
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "ATTACK0101":
                this.state.setAnimation(0, "Phase_01_Attack_01", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_01_Default", true, 0.0F);
                break;
            case "ATTACK0102":
                this.state.setAnimation(0, "Phase_01_Attack_02", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_01_Default", true, 0.0F);
                break;
            case "ATTACK0103":
                this.state.setAnimation(0, "Phase_01_Attack_03", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_01_Default", true, 0.0F);
                break;
            case "01TO02":
                this.state.setAnimation(0, "Phase_02_Default", true);
                this.state.setTimeScale(1F);
                break;

            case "ATTACK0201":
                this.state.setAnimation(0, "Phase_02_Attack_01", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_02_Default", true, 0.0F);
                break;
            case "ATTACK0202":
                this.state.setAnimation(0, "Phase_02_Attack_02", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_02_Default", true, 0.0F);
                break;
            case "SPEAR02":
                this.state.setAnimation(0, "Phase_02_03_Spear", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_02_Default", true, 0.0F);
                break;
            case "ATTACK0203":
                this.state.setAnimation(0, "Phase_02_Attack_03_Cast", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_02_Attack_03_End", false, 0.0F);
                this.state.addAnimation(0, "Phase_02_Default", true, 0.0F);
                break;
            case "02TO03":
                this.state.setAnimation(0, "Phase_02 to 03", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_03_Default", true, 0.0F);
                break;

            case "ATTACK0301":
                this.state.setAnimation(0, "Phase_03_Attack_01", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_03_Default", true, 0.0F);
                break;
            case "ATTACK0302":
                this.state.setAnimation(0, "Phase_03_Attack_02", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_03_Default", true, 0.0F);
                break;
            case "SPEAR03":
                this.state.setAnimation(0, "Phase_02_03_Spear", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_03_Default", true, 0.0F);
                break;
            case "03TO04":
                this.state.setAnimation(0, "Phase_03 to 04", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_04_Default", true, 0.0F);
                break;

            case "RUN01":
                this.state.setAnimation(0, "Phase_04_Attack_Run", true);
                this.state.setTimeScale(1F);
                break;
            case "RUN02":
                this.state.setAnimation(0, "Phase_04_Attack_Start", true);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_04_Attack_Run", true, 0.0F);
                break;
            case "RUN_ATTACK":
                this.state.setAnimation(0, "Phase_04_Attack_Run_Attack", true);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_04_Attack_Run", true, 0.0F);
                break;
            case "RUN_END":
                this.state.setAnimation(0, "Phase_04_Attack_End", true);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_04_Default", true, 0.0F);
                break;

            case "ATTACK_TELEPORT_START":
                this.state.setAnimation(0, "Phase_All_Teleport_01", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_All_Teleport_02", true, 0.0F);
                break;
            case "ATTACK_TELEPORT_END1":
                this.state.setAnimation(0, "Phase_All_Teleport_03", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_01_Default", true, 0.0F);
                break;
            case "ATTACK_TELEPORT_END3":
                this.state.setAnimation(0, "Phase_All_Teleport_03", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Phase_03_Default", true, 0.0F);
                break;

            case "DIE":
                this.state.setAnimation(0, "Phase_04_Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }

    private String getWord(int i){
        ArrayList<String> derp = new ArrayList<>();
        switch (i){
            case 0:
                derp.add(DIALOG[1]);
                derp.add(DIALOG[2]);
                break;
            case 1:
                derp.add(DIALOG[3]);
                derp.add(DIALOG[4]);
                break;
            case 2:
                derp.add(DIALOG[5]);
                derp.add(DIALOG[6]);
                break;
            case 3:
                derp.add(DIALOG[7]);
                break;

            case 4:
                derp.add(DIALOG[8]);
                break;
            case 5:
                derp.add(DIALOG[9]);
                break;
            case 6:
                derp.add(DIALOG[10]);
                derp.add(DIALOG[11]);
                derp.add(DIALOG[12]);
                break;
            case 7:
                derp.add(DIALOG[13]);
                derp.add(DIALOG[14]);
                break;

            case 8:
                derp.add(DIALOG[15]);
                derp.add(DIALOG[16]);
                break;
        }
        return derp.get(MathUtils.random(derp.size() - 1));
    }

    public Attachment getAttachment(String name){
        return this.skeleton.findSlot(name).getAttachment();
    }

    public Bone getBone(String name){
        return this.skeleton.findBone(name);
    }

    public Skeleton getSkeleton(){
        return this.skeleton;
    }

    @SpirePatch(
            clz= TheBottomScene.class,
            method="renderCombatRoomBg"
    )
    public static class renderCombatRoomBg {
        @SpirePostfixPatch
        public static void postfix(TheBottomScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters.size() == 0){
                return;
            }
            if(!(AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof Geburah)){
                return;
            }
            Texture img = LobotomyImageMaster.GEBURAH_BACKGROUND;
            sb.setColor(Color.WHITE.cpy());
            sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }
    }

    @SpirePatch(
            clz= TheCityScene.class,
            method="renderCombatRoomBg"
    )
    public static class renderCombatRoomBg2 {
        @SpirePostfixPatch
        public static void postfix(TheCityScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters.size() == 0){
                return;
            }
            if(!(AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof Geburah)){
                return;
            }
            Texture img = LobotomyImageMaster.GEBURAH_BACKGROUND;
            sb.setColor(Color.WHITE.cpy());
            sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }
    }

    @SpirePatch(
            clz= TheBeyondScene.class,
            method="renderCombatRoomBg"
    )
    public static class renderCombatRoomBg3 {
        @SpirePostfixPatch
        public static void postfix(TheBeyondScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters.size() == 0){
                return;
            }
            for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                if(m instanceof Geburah){
                    Texture img = LobotomyImageMaster.GEBURAH_BACKGROUND;
                    sb.setColor(Color.WHITE.cpy());
                    sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
                    return;
                }
            }
        }
    }

    @SpirePatch(
            clz= TheEndingScene.class,
            method="renderCombatRoomBg"
    )
    public static class renderCombatRoomBg4 {
        @SpirePostfixPatch
        public static void postfix(TheEndingScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters.size() == 0){
                return;
            }
            if(!(AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof Geburah)){
                return;
            }
            Texture img = LobotomyImageMaster.GEBURAH_BACKGROUND;
            sb.setColor(Color.WHITE.cpy());
            sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }
    }

    @SpirePatch(
            clz= TheBottomScene.class,
            method="renderCombatRoomFg"
    )
    public static class renderCombatRoomFg {
        @SpirePrefixPatch
        public static SpireReturn prefix(TheBottomScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null
                    && AbstractDungeon.getCurrRoom().monsters.monsters.size() > 0 && AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof Geburah){
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= TheCityScene.class,
            method="renderCombatRoomFg"
    )
    public static class renderCombatRoomFg2 {
        @SpirePrefixPatch
        public static SpireReturn prefix(TheCityScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null
                    && AbstractDungeon.getCurrRoom().monsters.monsters.size() > 0 && AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof Geburah){
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= TheBeyondScene.class,
            method="renderCombatRoomFg"
    )
    public static class renderCombatRoomFg3 {
        @SpirePrefixPatch
        public static SpireReturn prefix(TheBeyondScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null
                    && AbstractDungeon.getCurrRoom().monsters.monsters.size() > 0){
                for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                    if(m instanceof Geburah){
                        return SpireReturn.Return(null);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz= TheEndingScene.class,
            method="renderCombatRoomFg"
    )
    public static class renderCombatRoomFg4 {
        @SpirePrefixPatch
        public static SpireReturn prefix(TheEndingScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null
                    && AbstractDungeon.getCurrRoom().monsters.monsters.size() > 0 && AbstractDungeon.getCurrRoom().monsters.monsters.get(0) instanceof Geburah){
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
