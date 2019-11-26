package lobotomyMod.monster.Ordeal.Circus;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

/**
 * @author hoykj
 */
public class CircusDawn extends AbstractOrdealMonster {
    public static final String ID = "CircusDawn";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("CircusDawn");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public CircusDawn(float x, float y) {
        super(NAME, "CircusDawn", 20, 0.0F, -16.0F, 200.0F, 200.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/Circus/Dawn/tjzjtm.atlas", "lobotomyMod/images/monsters/Ordeal/Circus/Dawn/tjzjtm.json", 3.2F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Default", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Default", "Trick", 0.9F);
        this.stateData.setMix("Default", "Dead", 0.9F);
        this.damage.add(new DamageInfo(this, 3));
        this.flipHorizontal = true;
        this.setHp(20, 24);
    }

    protected void getMove(int num) {
        setMove((byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToTop(new VampireDamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }, 0.2F));

            AbstractCard card = LobotomyUtils.getRandomCard();
            if(card != null) {
                if (AbstractDungeon.player.drawPile.contains(card)) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
                } else if (AbstractDungeon.player.hand.contains(card)) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                } else if (AbstractDungeon.player.discardPile.contains(card)) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void die() {
        super.die();
        this.changeState("DIE");
        this.deathTimer += 1.5F;
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(this.hb.cX, this.hb.cY), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, 10, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            this.dispose();
        }, 1.0F));
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
            AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(4, 1, true));
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "ATTACK":
                this.state.setAnimation(0, "Trick", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "DIE":
                this.state.setAnimation(0, "Dead", false);
                this.state.setTimeScale(1F);
                break;
        }
    }
}
