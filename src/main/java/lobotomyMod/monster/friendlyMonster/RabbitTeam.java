package lobotomyMod.monster.friendlyMonster;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.vfx.action.LatterEffect;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author hoykj
 */
public class RabbitTeam extends AbstractFriendlyMonster {
    public static final String ID = "RabbitTeam";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("RabbitTeam");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int counter;

    public RabbitTeam(float x, float y, ArrayList<AbstractCreature> targets) {
        super(NAME, "RabbitTeam", 150, 0.0F, 0.0F, 180.0F, 240.0F, null, x, y, targets);
        loadAnimation("lobotomyMod/images/monsters/RabbitTeam/rabbitTeam.atlas", "lobotomyMod/images/monsters/RabbitTeam/rabbitTeam.json", 2.2F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Default", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Default", "Attack_Sword_01", 0.1F);
        this.stateData.setMix("Default", "Attack_Sword_02", 0.1F);
        this.stateData.setMix("Default", "Attack_Sword_03", 0.1F);
        this.stateData.setMix("Default", "Attack_Gun_01", 0.1F);
        this.stateData.setMix("Default", "Dead_Physical_01", 0.1F);
        this.stateData.setMix("Default", "Dead_Physical_02", 0.1F);
        this.stateData.setMix("Default", "Clear", 0.1F);
        this.damage.add(new DamageInfo(this, 24));
        this.damage.add(new DamageInfo(this, 9));
        this.damage.add(new DamageInfo(this, 3));
        this.flipHorizontal = this.targets.get(0).drawX > this.drawX;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
    }

    protected void getMove(int num) {
        switch (this.counter){
            case 0:
                setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
                break;
            case 1:
                setMove((byte) 2, Intent.ATTACK, this.damage.get(1).base, 5, true);
                break;
            case 2:
                setMove((byte) 3, Intent.ATTACK, this.damage.get(2).base, 20, true);
                break;
        }
        this.counter ++;
        if(this.counter > 2){
            this.counter = 0;
        }
    }

    public void takeTurn() {
        if(this.targets.size() < 1){
            return;
        }
        AbstractCreature target = this.targets.get(0);
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK1"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }, 0.88F));
        }
        else if(this.nextMove == 2){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK2"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }, 0.63F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }, 0.14F));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }, 0.3F));
        }
        else if(this.nextMove == 3){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "GUN"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 0.86F));
            for(int i = 0; i < 19; i ++){
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.actionManager.addToTop(new DamageAction(target, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                }, 0.1F));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void update() {
        super.update();
        ArrayList<AbstractCreature> remove = new ArrayList<>();
        for(AbstractCreature creature : this.targets){
            if(creature.isDeadOrEscaped()){
                remove.add(creature);
            }
        }
        for(AbstractCreature creature : remove){
            if(creature.isDeadOrEscaped()){
                this.targets.remove(creature);
            }
        }

        if(this.targets.size() < 1){
            if(this.isEscaping){
                return;
            }
            this.hideHealthBar();
            this.isEscaping = true;
            this.changeState("CLEAR");
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                this.escaped = true;
                Iterator var2;
                if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    AbstractDungeon.overlayMenu.endTurnButton.disable();
                    var2 = AbstractDungeon.player.limbo.group.iterator();

                    while(var2.hasNext()) {
                        AbstractCard c = (AbstractCard)var2.next();
                        AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                    }

                    AbstractDungeon.player.limbo.clear();
                }
            }, 1.5F));
            return;
        }
        this.flipHorizontal = this.targets.get(0).drawX > this.drawX;
    }

    public void die() {
        super.die(false);
        this.changeState("DIE");
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "ATTACK1":
                if(MathUtils.randomBoolean()) {
                    this.state.setAnimation(0, "Attack_Sword_01", false);
                }
                else {
                    this.state.setAnimation(0, "Attack_Sword_03", false);
                }
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "ATTACK2":
                this.state.setAnimation(0, "Attack_Sword_02", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "GUN":
                this.state.setAnimation(0, "Attack_Gun_01", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Attack_Gun_02", false, 0.0F);
                this.state.addAnimation(0, "Attack_Gun_Reload", false, 0.0F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "CLEAR":
                this.state.setAnimation(0, "Clear", false);
                this.state.setTimeScale(1F);
                break;
            case "DIE":
                if(MathUtils.randomBoolean()) {
                    this.state.setAnimation(0, "Dead_Physical_01", false);
                }
                else {
                    this.state.setAnimation(0, "Dead_Physical_02", false);
                }
                this.state.setTimeScale(1F);
                break;
        }
    }
}
