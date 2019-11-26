package lobotomyMod.monster.Ordeal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.animation.clawRedAttackAction;
import lobotomyMod.action.animation.ultiAttackAction;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.vfx.action.LatterEffect;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

import java.io.IOException;

/**
 * @author hoykj
 */
public class Claw extends AbstractOrdealMonster {
    public static final String ID = "Claw";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Claw");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int injectCool;
    private int greenDamage;
    private int stopDamage;
    private int ultiCool;
    private int ultiDamage;
    private boolean injecting;
    private boolean firstUlti;
    private boolean groggy;
    private float duration;

    public Claw(float x, float y) {
        super(NAME, "Claw", 1000, 0.0F, -20.0F, 250.0F, 400.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Claw/skeleton.atlas", "lobotomyMod/images/monsters/Ordeal/Claw/skeleton.json", 1.6F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "0_Default", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("0_Default", "1_normal_attack_1", 0.1F);
        this.stateData.setMix("0_Default", "1_normal_attack_2", 0.1F);
        this.stateData.setMix("0_Default", "1_strong_attack", 0.1F);
        this.stateData.setMix("0_Default", "1_injection_blue", 0.1F);
        this.stateData.setMix("0_Default", "2_injection_blue_casting", 0.1F);
        this.stateData.setMix("0_Default", "1_injection_green", 0.1F);
        this.stateData.setMix("0_Default", "2_injection_green_Casting_also_healing", 0.1F);
        this.stateData.setMix("2_injection_green_Casting_also_healing", "3_injection_green_end", 0.1F);
        this.stateData.setMix("0_Default", "1_injection_red", 0.1F);
        this.stateData.setMix("0_Default", "2_injection_red_first", 0.1F);
        this.stateData.setMix("0_Default", "1_ulti_go_to_casting", 0.1F);
        this.stateData.setMix("2_ulti_casting_loop", "3_ulti_start", 0.1F);

        this.stateData.setMix("0_Default", "1_groggy", 0.1F);
        this.stateData.setMix("2_injection_blue_casting", "1_groggy", 0.1F);
        this.stateData.setMix("2_injection_green_Casting_also_healing", "1_groggy", 0.1F);
        this.stateData.setMix("2_groggy_loop", "3_groggy_to_default", 0.1F);
        this.damage.add(new DamageInfo(this, 25));
        this.damage.add(new DamageInfo(this, 40));
        this.damage.add(new DamageInfo(this, 100));
        this.damage.add(new DamageInfo(this, 12));
        this.damage.add(new DamageInfo(this, (int)(AbstractDungeon.player.maxHealth * 0.4F)));
        this.injectCool = 2;
        this.ultiCool = 0;
        this.firstUlti = true;
        this.groggy = false;
        this.type = EnemyType.BOSS;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        AbstractDungeon.getCurrRoom().playBgmInstantly("emergency03_mast.mp3");
        AbstractDungeon.effectsQueue.add(new LatterEffect(()->{
            AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(6, 4, false));
        }, 4.2F));
    }

    protected void getMove(int num) {
        if(this.currentHealth <= 0){
            this.ultiCool = 1;
            this.die();
            return;
        }
        if(this.groggy){
            this.groggy = false;
            this.changeState("GROGGY_END");
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new RollMoveAction(this));
            }, 3.0F));
            return;
        }
        this.injectCool --;
        if(this.currentHealth <= this.maxHealth * 0.25F){
            this.ultiCool --;
            if(this.ultiCool <= 0){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));
                this.ultiCool = 2;
                this.damage.get(4).base = (int)(AbstractDungeon.player.maxHealth * 0.4F);
                setMove((byte) 6, Intent.ATTACK, this.damage.get(4).base, 3, true);
                createIntent();
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 6, Intent.ATTACK, this.damage.get(4).base, 3, true));
                this.changeState("ULTI_READY");
                return;
            }
        }
        if(this.injectCool <= 0){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
            this.injectCool = 2;
            if(AbstractDungeon.aiRng.randomBoolean(0.6F)){
                setMove((byte) 3, Intent.ATTACK, this.damage.get(2).base);
                this.changeState("RED_START");
            }
            else {
                setMove((byte) 4, Intent.ATTACK_DEBUFF, this.damage.get(3).base, 7, true);
                this.changeState("BLUE_START");
                this.injecting = true;
            }
        }
        else if(this.greenDamage >= 120){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
            this.greenDamage = 0;
            setMove((byte) 5, Intent.UNKNOWN);
            this.changeState("GREEN_START");
            this.injecting = true;
        }
        else {
            if(AbstractDungeon.aiRng.randomBoolean(0.75F)){
                setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
            }
            else {
                setMove((byte) 2, Intent.ATTACK, this.damage.get(1).base);
            }
        }
        //setMove((byte) 0, Intent.UNKNOWN);
    }

    public void takeTurn() {
        //this.changeState("ULTI_START");
        //AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "RED_ATTACK"));

//        AbstractDungeon.actionManager.addToTop(new LatterAction(()->{
//            AbstractDungeon.actionManager.addToTop(new clawRedAttackAction(this,
//                    new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.NONE)));
//        }, 5.0F));

        switch (this.nextMove) {
            case 1:
                if (MathUtils.randomBoolean()) {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK1"));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK2"));
                }
                AbstractDungeon.actionManager.addToBottom(new LatterAction(() -> {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }, 0.8F));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK3"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(() -> {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }, 2.6F));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "RED_ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(() -> {
                    AbstractDungeon.actionManager.addToTop(new clawRedAttackAction(this,
                            new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.NONE)));
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Wound(), 1));
                }, 5.0F));
                break;
            case 4:
                this.injecting = false;
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "BLUE_ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(() -> {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }, 0.3F));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(() -> {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }, 0.6F));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(() -> {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                }, 0.63F));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(() -> {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                }, 0.63F));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(() -> {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }, 0.6F));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(() -> {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }, 0.6F));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(() -> {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }, 0.6F));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(() -> {
                    if(AbstractDungeon.player.drawPile.size() > 0){
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.drawPile.getRandomCard(true), AbstractDungeon.player.drawPile));
                    }
                    if(AbstractDungeon.player.discardPile.size() > 0){
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.discardPile.getRandomCard(true), AbstractDungeon.player.discardPile));
                    }
                }, 0.9F));
                break;
            case 5:
                this.injecting = false;
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, 150));
                AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(this));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "GREEN_END"));
                break;
            case 6:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ULTI_START"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToTop(new ultiAttackAction(this,
                            AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                }, 2.83F));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        this.greenDamage += info.output;
//        if(this.greenDamage >= 120 && this.injectCool != 2 && !this.groggy){
//            AbstractDungeon.actionManager.addToTop(new RollMoveAction(this));
//        }
        if(this.ultiCool > 0){
            this.ultiDamage += info.output;
            if(this.ultiDamage >= this.maxHealth * 0.25){
                this.ultiDamage = 0;
                this.ultiCool = 0;
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            }
        }

        if(this.firstUlti &&  this.currentHealth <= this.maxHealth * 0.25F){
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            this.firstUlti = false;
            return;
        }

        if(this.injecting){
            this.stopDamage += info.output;
            if(this.stopDamage >= 200){
                this.stopDamage = 0;
                this.injecting = false;
                setMove((byte)99, Intent.STUN);
                createIntent();
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.STUN));
                this.duration = 0;
                this.changeState("GROGGY");
                this.groggy = true;
            }
        }
        else {
            this.stopDamage = 0;
        }
    }

    public void die() {
        if(this.ultiCool == 2){
            return;
        }
        super.die();
        LobotomyMod.defeatFixer = true;
        try {
            LobotomyMod.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.changeState("DIE");
        this.deathTimer += 9.0F;
        //useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
            AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(6, 4, true));
        }
        //AbstractDungeon.bossList.clear();
        //AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        onBossVictoryLogic();
        onFinalBossVictoryLogic();
    }

    @Override
    public void update() {
        super.update();
        this.duration -= Gdx.graphics.getDeltaTime();
    }

    public void changeState(String key)
    {
        if(this.duration > 0){
            AbstractDungeon.actionManager.addToTop(new LatterAction(()->{
                this.changeState(key);
            }, this.duration + 0.1F));
            return;
        }
        switch (key)
        {
            case "ATTACK1":
                this.state.setAnimation(0, "1_normal_attack_1", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "0_Default", true, 0.0F);
                this.duration = 2.0F;
                break;
            case "ATTACK2":
                this.state.setAnimation(0, "1_normal_attack_2", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "0_Default", true, 0.0F);
                this.duration = 2.0F;
                break;
            case "ATTACK3":
                this.state.setAnimation(0, "1_strong_attack", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "0_Default", true, 0.0F);
                this.duration = 4.83F;
                break;

            case "BLUE_START":
                this.state.setAnimation(0, "1_injection_blue", false);
                this.state.setTimeScale(1F);
                AnimationState.TrackEntry entry = this.state.addAnimation(0, "2_injection_blue_casting", false, 0.0F);
                entry.setEndTime(19.17F);
                this.state.addAnimation(0, "0_Default", true, 0.0F);
                this.duration = 19.17F;
                break;
            case "BLUE_ATTACK":
                entry = this.state.setAnimation(0, "2_injection_blue_casting", false);
                this.state.setTimeScale(1F);
                entry.setTime(19.17F);
                AbstractDungeon.effectList.add(new LatterEffect(()->{
                    this.animX = AbstractDungeon.player.drawX - this.drawX + 100.0F * Settings.scale;
                    this.animY = AbstractDungeon.player.drawY - this.drawY;
                    this.state.setAnimation(0, "Example_Blue", false);
                    entry.setTime(25.07F);
                    entry.setEndTime(30.03F);
                    AbstractDungeon.effectsQueue.add(new LatterEffect(()->{
                        this.animX = 0;
                        this.animY = 0;
                        this.state.setAnimation(0, "5_injection_blue_end", false);
                        this.state.addAnimation(0, "0_Default", true, 0.0F);
                    }, 4.96F));
                }, 2.53F));
                this.duration = 12.33F;
                break;

            case "GREEN_START":
                this.state.setAnimation(0, "1_injection_green", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "2_injection_green_Casting_also_healing", true, 0.0F);
                this.duration = 5.33F;
                break;
            case "GREEN_END":
                this.state.setAnimation(0, "3_injection_green_end", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "0_Default", true, 0.0F);
                this.duration = 2.0F;
                break;

            case "RED_START":
                this.state.setAnimation(0, "1_injection_red", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "0_Default", true, 0.0F);
                this.duration = 3.3F;
                break;
            case "RED_ATTACK":
                this.state.setAnimation(0, "2_injection_red_first", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "3_injection_red_middle_loop", true, 0.0F);
                this.duration = 5.0F;
                break;
            case "RED_END":
                this.state.setAnimation(0, "4_injection_red_end", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "0_Default", true, 0.0F);
                this.duration = 5.0F;
                break;

            case "ULTI_READY":
                this.state.setAnimation(0, "1_ulti_go_to_casting", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "2_ulti_casting_loop", true, 0.0F);
                this.duration = 3.0F;
                break;
            case "ULTI_START":
                this.state.setAnimation(0, "3_ulti_start", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "4_ulti_middle_loop", true, 0.0F);
                this.duration = 2.83F;
                break;
            case "ULTI_ATTACK1":
                this.state.setAnimation(0, "5_ulti_term_1_nodelay", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "4_ulti_middle_loop", true, 0.0F);
                break;
            case "ULTI_ATTACK2":
                if(MathUtils.randomBoolean()) {
                    this.state.setAnimation(0, "5_ulti_term_2_nodelay", false);
                }
                else {
                    this.state.setAnimation(0, "5_ulti_term_3_nodelay", false);
                }
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "4_ulti_middle_loop", true, 0.0F);
                break;
            case "ULTI_END":
                this.state.setAnimation(0, "6_ulti_end", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "0_Default", true, 0.0F);
                this.duration = 5.0F;
                break;

            case "GROGGY":
                this.state.setAnimation(0, "1_groggy", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "2_groggy_loop", true, 0.0F);
                break;
            case "GROGGY_END":
                this.state.setAnimation(0, "3_groggy_to_default", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "0_Default", true, 0.0F);
                this.duration = 3.0F;
                break;
            case "DIE":
                this.state.setAnimation(0, "0_Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }
}
