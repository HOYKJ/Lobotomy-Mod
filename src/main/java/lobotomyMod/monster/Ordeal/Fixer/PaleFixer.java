package lobotomyMod.monster.Ordeal.Fixer;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.monster.Ordeal.Bug.BugNight;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

/**
 * @author hoykj
 */
public class PaleFixer extends AbstractFixer {
    public static final String ID = "PaleFixer";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("PaleFixer");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int turns;

    public PaleFixer(float x, float y) {
        super(NAME, "PaleFixer", 260, 0.0F, -20.0F, 180.0F, 240.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Fixer/Pale/body.atlas", "lobotomyMod/images/monsters/Ordeal/Fixer/Pale/body.json", 3.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Default", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Default", "Attack_01", 0.1F);
        this.stateData.setMix("Default", "Attack_02", 0.1F);
        //this.stateData.setMix("Default", "Attack_03", 0.9F);
        //this.stateData.setMix("Default", "Teleport", 0.9F);
        this.stateData.setMix("Default", "Dead", 0.9F);
        this.damage.add(new DamageInfo(this, (int) (AbstractDungeon.player.maxHealth * 0.09F)));
        this.damage.add(new DamageInfo(this, (int) (AbstractDungeon.player.maxHealth * 0.05F)));
        this.damage.add(new DamageInfo(this, (int) (AbstractDungeon.player.maxHealth * 0.25F)));
        this.turns = 1;
    }

    protected void getMove(int num) {
        if(this.halfDead){
            return;
        }
        switch (this.turns) {
            case 1:
                this.damage.get(0).base = (int) (AbstractDungeon.player.maxHealth * 0.09F);
                setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
                break;
            case 2:
                this.damage.get(1).base = (int) (AbstractDungeon.player.maxHealth * 0.05F);
                setMove((byte) 2, Intent.ATTACK, this.damage.get(1).base, 3, true);
                break;
            case 3:
                this.damage.get(2).base = (int) (AbstractDungeon.player.maxHealth * 0.25F);
                setMove((byte) 3, Intent.ATTACK, this.damage.get(2).base);
                break;
            case 4:
                int counter = 0;
                for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                    if(!m.isDeadOrEscaped()){
                        counter ++;
                    }
                }
                if(counter > 2) {
                    setMove((byte) 4, Intent.UNKNOWN);
                }
                else {
                    this.damage.get(1).base = (int) (AbstractDungeon.player.maxHealth * 0.05F);
                    setMove((byte) 2, Intent.ATTACK, this.damage.get(1).base, 3, true);
                }
                break;
//            case 5:
//                setMove((byte) 5, Intent.UNKNOWN);
//                break;
        }
        this.turns ++;
        if(this.turns > 4){
            this.turns = 1;
        }
        //setMove((byte) 0, Intent.UNKNOWN, this.damage.get(0).base);
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK2"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 0.37F));
        }
        else if(this.nextMove == 2){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK3"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }, 0.73F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SMASH));
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }, 0.6F));
        }
        else if(this.nextMove == 3){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }, 1.3F));
        }
        else if(this.nextMove == 4){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "TELEPORT"));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new NoDrawPower(AbstractDungeon.player)));
            this.hideHealthBar();
            this.isEscaping = true;
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                this.escaped = true;
                if (AbstractDungeon.getMonsters().areMonstersDead() && !AbstractDungeon.getCurrRoom().isBattleOver && !AbstractDungeon.getCurrRoom().cannotLose) {
                    AbstractDungeon.getCurrRoom().endBattle();
                }
            }, 2.67F));

        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "ATTACK":
                this.state.setAnimation(0, "Attack_01", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "ATTACK2":
                this.state.setAnimation(0, "Attack_02", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "ATTACK3":
                this.state.setAnimation(0, "Attack_03", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "TELEPORT":
                AnimationState.TrackEntry entry = this.state.setAnimation(0, "Teleport", false);
                //entry.setTime(2.66F);
                this.state.setTimeScale(1F);
                break;
            case "DIE":
                this.state.setAnimation(0, "Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }
}
