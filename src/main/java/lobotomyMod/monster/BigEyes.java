package lobotomyMod.monster;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.ExhaustRandomCardAction;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.vfx.BlackForestStory;
import lobotomyMod.vfx.LoseSightEffect;
import lobotomyMod.vfx.action.LatterEffect;

/**
 * @author hoykj
 */
public class BigEyes extends AbstractMonster {
    public static final String ID = "BigEyes";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BigEyes");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int counter;

    public BigEyes(float x, float y) {
        super(NAME, "BigEyes", 250, 0.0F, 0.0F, 160.0F, 200.0F, "lobotomyMod/images/monsters/ApocalypseBird/Egg/BigEyes/BigEyes.png", x, y);
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.topLevelEffects.add(new LoseSightEffect());
    }

    protected void getMove(int num) {
        this.counter ++;
        if(this.halfDead){
            setMove((byte) 0, Intent.NONE);
        }
        else if(this.counter >= 3){
            this.counter = 0;
            setMove((byte) 1, Intent.UNKNOWN);
        }
        else {
            setMove((byte) 0, Intent.NONE);
        }
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new ExhaustRandomCardAction());
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if(this.currentHealth <= this.maxHealth / 2){
            this.img = ImageMaster.loadImage("lobotomyMod/images/monsters/ApocalypseBird/Egg/BigEyes/BigEyes_1.png");
        }
        if ((this.currentHealth <= 0) && (!this.halfDead)) {
            for(AbstractGameEffect effect : AbstractDungeon.topLevelEffects){
                if(effect instanceof LoseSightEffect){
                    ((LoseSightEffect) effect).end();
                }
            }
            AbstractDungeon.topLevelEffects.add(new BlackForestStory(9));
            AbstractDungeon.topLevelEffects.add(new BlackForestStory(10));
            this.img = ImageMaster.loadImage("lobotomyMod/images/monsters/ApocalypseBird/Egg/BigEyes/BigEyes_2.png");

            this.halfDead = true;
            setMove((byte) 0, Intent.NONE);
            for (AbstractPower p : this.powers) {
                p.onDeath();
            }
            for (AbstractRelic r : AbstractDungeon.player.relics)
            {
                r.onMonsterDeath(this);
            }
            this.powers.clear();
            boolean allDead = true;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if ((!m.halfDead) && !(m instanceof ApocalypseBirdMonster)) {
                    allDead = false;
                    break;
                }
            }
            LobotomyMod.logger.info("All dead: " + allDead);
            if (allDead) {
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.getCurrRoom().cannotLose = false;
                    this.halfDead = false;
                    for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                        m.die();
                    }
                }, 12.0F));
                AbstractDungeon.effectList.add(new LatterEffect(()->{
                    AbstractDungeon.topLevelEffects.add(new BlackForestStory(15));
                    AbstractDungeon.topLevelEffects.add(new BlackForestStory(16));
                    AbstractDungeon.topLevelEffects.add(new BlackForestStory(17));
                }, 6.0F));
            }
        }
    }

    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();
        }
    }

    @SpirePatch(
            clz= AbstractMonster.class,
            method="render"
    )
    public static class renderPatch {
        @SpirePrefixPatch
        public static void prefix(AbstractMonster _inst, SpriteBatch sb){
            if((_inst instanceof BigEyes) && (!_inst.isDead && !_inst.escaped)) {
                Settings.scale /= 3.0F;
            }
        }

        @SpireInsertPatch(rloc=54)
        public static void Insert(AbstractMonster _inst, SpriteBatch sb) {
            if(_inst instanceof BigEyes) {
                Settings.scale *= 3.0F;
            }
        }

        @SpirePostfixPatch
        public static void postfix(AbstractMonster _inst, SpriteBatch sb){

        }
    }
}
