package lobotomyMod.monster.Ordeal;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.monster.Ordeal.Machine.MachineNight;
import lobotomyMod.monster.YangMonster;
import lobotomyMod.relic.Yang;
import lobotomyMod.vfx.action.LatterEffect;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class Cleaner extends AbstractOrdealMonster {
    public static final String ID = "Cleaner";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Cleaner");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public boolean clear;

    public Cleaner(float x, float y) {
        super(NAME, "Cleaner", 60, 0.0F, 0.0F, 220.0F, 240.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Cleaner/cleaner.atlas", "lobotomyMod/images/monsters/Ordeal/Cleaner/cleaner.json", 1.5F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Default", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Default", "Attack_01", 0.9F);
        this.stateData.setMix("Default", "Attack_02", 0.9F);
        this.stateData.setMix("Default", "Attack_03", 0.9F);
//        this.stateData.setMix("Default", "Dead_01", 0.9F);
//        this.stateData.setMix("Default", "Dead_02", 0.9F);
        this.stateData.setMix("Default", "Eat", 0.9F);
        this.damage.add(new DamageInfo(this, 6));
        this.damage.add(new DamageInfo(this, 20));
        this.clear = false;
    }

    protected void getMove(int num) {
        if(this.halfDead){
            return;
        }
        if(AbstractDungeon.aiRng.randomBoolean(0.75F)) {
            setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
        }
        else {
            setMove((byte) 2, Intent.ATTACK, this.damage.get(1).base);
        }
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            if(MathUtils.randomBoolean()) {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK1"));
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK2"));
            }
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }, 0.7F));
        }
        else if(this.nextMove == 2){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK3"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SMASH));
            }, 1.1F));
        }
        else if(this.nextMove == 3){
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if ((m instanceof Cleaner) && (m.halfDead)) {
                    if(!m.isDying){
                        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "EAT"));
                        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->
                                AbstractDungeon.actionManager.addToTop(new HealAction(this, this, this.maxHealth)), 0.6F));
                        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                            ((Cleaner) m).clear = true;
                            m.die();
                        }));
                        break;
                    }
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if ((this.currentHealth <= 0) && (!this.halfDead))
        {
            this.halfDead = true;
            if(MathUtils.randomBoolean()){
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "DIE1"));
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "DIE2"));
            }

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
                if ((m.id.equals("Cleaner")) && (!m.halfDead)) {
                    allDead = false;
                }
            }
            if(allDead){
                this.clear = true;
                this.die();
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m instanceof Cleaner) {
                        ((Cleaner) m).clear = true;
                        m.die();
                    }
                }
            }
            else {
                ArrayList<Cleaner> list = new ArrayList<>();
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if ((m instanceof Cleaner) && (!m.halfDead) && (m.currentHealth < m.maxHealth)) {
                        list.add((Cleaner) m);
                    }
                }
                if(list.size() > 0) {
                    list.get(AbstractDungeon.cardRng.random(list.size() - 1)).eat();
                }
                setMove((byte)9, Intent.NONE);
                createIntent();
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)9, Intent.NONE));
            }
        }
    }

    private void eat(){
        setMove((byte)3, AbstractMonster.Intent.UNKNOWN);
        createIntent();
        AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, AbstractMonster.Intent.UNKNOWN));
    }

    @Override
    public void update() {
        super.update();
        if(this.halfDead){
            boolean allDead = true;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if ((m.id.equals("Cleaner")) && (!m.halfDead)) {
                    allDead = false;
                }
            }
            if(allDead){
                this.clear = true;
                this.die();
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m instanceof Cleaner) {
                        ((Cleaner) m).clear = true;
                        m.die();
                    }
                }
            }
        }
    }

    public void die() {
        if(this.clear) {
            super.die();
        }
        else{
            boolean allDead = true;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if ((m.id.equals("Cleaner")) && (!m.halfDead)) {
                    allDead = false;
                }
            }
            if(allDead){
                this.clear = true;
                this.die();
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m instanceof Cleaner) {
                        ((Cleaner) m).clear = true;
                        m.die();
                    }
                }
                AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(5, 2, true));
            }
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "ATTACK1":
                this.state.setAnimation(0, "Attack_01", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                LobotomyMod.logger.info("attack");
                break;
            case "ATTACK2":
                this.state.setAnimation(0, "Attack_02", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                LobotomyMod.logger.info("attack");
                break;
            case "ATTACK3":
                this.state.setAnimation(0, "Attack_03", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "EAT":
                this.state.setAnimation(0, "Eat", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "DIE1":
                this.state.setAnimation(0, "Dead_01", false);
                this.state.setTimeScale(1F);
                LobotomyMod.logger.info("die1");
                break;
            case "DIE2":
                this.state.setAnimation(0, "Dead_02", false);
                this.state.setTimeScale(1F);
                LobotomyMod.logger.info("die2");
                break;
        }
    }
}
