package lobotomyMod.monster;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.commonCard.PunishingBird;
import lobotomyMod.card.rareCard.ApocalypseBird;
import lobotomyMod.card.rareCard.WhiteNight;
import lobotomyMod.card.uncommonCard.BigBird;
import lobotomyMod.card.uncommonCard.LongBird;
import lobotomyMod.relic.DeathAngel;
import lobotomyMod.relic.Twilight;

/**
 * @author hoykj
 */
public class ApocalypseBirdMonster extends AbstractMonster {
    public static final String ID = "ApocalypseBirdMonster";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("ApocalypseBirdMonster");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private boolean flag;

    public ApocalypseBirdMonster(float x, float y) {
        super(NAME, "ApocalypseBirdMonster", 330000, -150.0F, 0.0F, 0.0F, 600.0F, "lobotomyMod/images/monsters/ApocalypseBird/ApocalypseBird.png", x, y);
        loadAnimation("lobotomyMod/images/monsters/ApocalypseBird/GIANTB~1.atlas", "lobotomyMod/images/monsters/ApocalypseBird/GIANTB~1.json", 1.75F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Standing", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Standing", "Attack1", 0.9F);
        this.stateData.setMix("Standing", "Attack2", 0.9F);
        this.stateData.setMix("Standing", "Casting", 0.9F);
        this.stateData.setMix("Standing", "Ultimate", 0.9F);
        this.stateData.setMix("Standing", "WingLaserStart", 0.9F);
        this.stateData.setMix("WingLaserEnd", "Standing", 0.9F);
        this.stateData.setMix("Standing", "Dead", 0.9F);
        this.state.setTimeScale(1.0F);
        this.damage.add(new DamageInfo(this, 20));
        this.damage.add(new DamageInfo(this, 5));
        this.damage.add(new DamageInfo(this, 10, DamageInfo.DamageType.HP_LOSS));
        this.damage.add(new DamageInfo(this, 50));
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        //this.halfDead = true;
        this.hideHealthBar();
        AbstractDungeon.getCurrRoom().cannotLose = true;
    }

    @Override
    public void update() {
        super.update();
        this.hideHealthBar();
    }

    protected void getMove(int num) {
        this.flag = !this.flag;
        if(this.flag) {
            setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base);
        }
        else {
            int tmp = 0;
            if(!AbstractDungeon.getCurrRoom().monsters.monsters.get(1).halfDead){
                tmp ++;
            }
            if(!AbstractDungeon.getCurrRoom().monsters.monsters.get(2).halfDead){
                tmp ++;
            }
            if(!AbstractDungeon.getCurrRoom().monsters.monsters.get(3).halfDead){
                tmp ++;
            }

            int roll = AbstractDungeon.monsterRng.random(tmp);
            if(roll == 3){
                setMove((byte) 3, Intent.ATTACK, this.damage.get(3).base);
            }
            else if(roll == 2){
                if(AbstractDungeon.getCurrRoom().monsters.monsters.get(2).halfDead){
                    setMove((byte) 3, Intent.ATTACK, this.damage.get(3).base);
                }
                else {
                    setMove((byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(2).base);
                }
            }
            else {
                if(AbstractDungeon.getCurrRoom().monsters.monsters.get(1).halfDead){
                    if(AbstractDungeon.getCurrRoom().monsters.monsters.get(2).halfDead){
                        setMove((byte) 3, Intent.ATTACK, this.damage.get(3).base);
                    }
                    else {
                        setMove((byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(2).base);
                    }
                }
                else {
                    setMove((byte) 1, Intent.ATTACK, this.damage.get(1).base, 6, true);
                }
            }
        }
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                if(MathUtils.randomBoolean()) {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK1"));
                    AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                    }, 2.5F));
                }
                else {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK2"));
                    AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                    }, 2.0F));
                }
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "WINGLASER"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                }, 5.0F));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "CASTING"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.FIRE));
                }, 3.5F));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ULTIMATE"));
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.SMASH));
                }, 4.0F));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void damage(DamageInfo info) {
    }

    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();
            this.changeState("DEAD");
            this.deathTimer += 1.5F;
            onBossVictoryLogic();
            if(AbstractDungeon.player.masterDeck.findCardById(LongBird.ID) == null) {
                AbstractDungeon.player.masterDeck.addToBottom(new BigBird());
                AbstractDungeon.player.masterDeck.addToBottom(new LongBird());
                AbstractDungeon.player.masterDeck.addToBottom(new PunishingBird());
                AbstractDungeon.player.masterDeck.addToBottom(new ApocalypseBird());
            }
            if(!AbstractDungeon.player.hasRelic(Twilight.ID)) {
                AbstractDungeon.getCurrRoom().addRelicToRewards(new Twilight());
            }
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "ATTACK1":
                this.state.setAnimation(0, "Attack1", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Standing", true, 0.0F);
                break;
            case "ATTACK2":
                this.state.setAnimation(0, "Attack2", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Standing", true, 0.0F);
                break;
            case "CASTING":
                this.state.setAnimation(0, "Casting", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Standing", true, 0.0F);
                break;
            case "ULTIMATE":
                this.state.setAnimation(0, "Ultimate", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Standing", true, 0.0F);
                break;
            case "WINGLASER":
                this.state.setAnimation(0, "WingLaserStart", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "WingLaserFire", false, 0.0F);
                this.state.addAnimation(0, "WingLaserEnd", false, 0.0F);
                this.state.addAnimation(0, "Standing", true, 0.0F);
                break;
            case "DEAD":
                this.state.setAnimation(0, "Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }

//    @SpirePatch(
//            clz= AbstractMonster.class,
//            method="render"
//    )
//    public static class renderPatch {
//        @SpirePrefixPatch
//        public static void prefix(AbstractMonster _inst, SpriteBatch sb){
//            if((_inst instanceof ApocalypseBirdMonster) && (!_inst.isDead && !_inst.escaped)) {
//                Settings.scale /= 1.5F;
//            }
//        }
//
//        @SpireInsertPatch(rloc=54)
//        public static void Insert(AbstractMonster _inst, SpriteBatch sb) {
//            if(_inst instanceof ApocalypseBirdMonster) {
//                Settings.scale *= 1.5F;
//            }
//        }
//    }
}
