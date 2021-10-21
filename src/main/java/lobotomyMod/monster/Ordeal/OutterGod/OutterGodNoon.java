package lobotomyMod.monster.Ordeal.OutterGod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import lobotomyMod.action.common.DelayAction;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.vfx.ordeal.OrdealTitleBack;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class OutterGodNoon extends AbstractOrdealMonster {
    public static final String ID = "OutterGodNoon";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("OutterGodNoon");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private boolean fall, start;
    private float timer;
    private DelayAction action;

    public OutterGodNoon(float x, float y) {
        super(NAME, "OutterGodNoon", 160, 0.0F, -20.0F, 350.0F, 450.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Ordeal/OutterGod/Noon/stone.atlas", "lobotomyMod/images/monsters/Ordeal/OutterGod/Noon/stone.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Falling", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Falling", "Down", 0.9F);
        this.stateData.setMix("Down", "Default", 0.9F);
        this.stateData.setMix("Default", "Casting", 0.9F);
        this.stateData.setMix("Default", "Dead", 0.9F);
        this.flipHorizontal = true;
        this.fall = true;
        this.start = false;
        this.timer = 4;
    }

    protected void getMove(int num) {
        if(this.fall){
            setMove((byte) 0, Intent.UNKNOWN);
        }
        else {
            setMove((byte) 1, Intent.UNKNOWN);
        }
    }

    public void takeTurn() {
        if(this.nextMove == 0){
            this.start = true;
            this.action = new DelayAction();
            AbstractDungeon.actionManager.addToBottom(this.action);
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "CAST"));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                ArrayList<AbstractCard> list = LobotomyUtils.getRandomCard(3);

                for(AbstractCard card : list){
                    if(AbstractDungeon.player.drawPile.contains(card)){
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
                    }
                    else if(AbstractDungeon.player.hand.contains(card)){
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                    }
                    else if(AbstractDungeon.player.discardPile.contains(card)){
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
                    }
                }
            }, 4.0F));
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void update() {
        super.update();
        if(this.fall){
            if(this.start) {
                this.drawY -= Gdx.graphics.getDeltaTime() * 400;
                if(this.drawY <= AbstractDungeon.floorY){
                    this.changeState("FALL");
                    this.action.end();
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, 30, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    this.fall = false;
                }
            }
        }
        else {
            if(this.currentHealth <= 0){
                return;
            }
            if (!(this.state.getTracks().get(0).getAnimation().getName().equals("Default"))){
                return;
            }
            this.timer -= Gdx.graphics.getDeltaTime();
            if(this.timer <= 0){
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, 2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                if(Settings.FAST_MODE){
                    this.timer = 2.67F;
                }
                else {
                    this.timer = 5.34F;
                }
            }
        }
    }

    public void die(boolean triggerRelics) {
        super.die(triggerRelics);
        this.changeState("DIE");
        this.deathTimer += 1.5F;
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
            AbstractDungeon.topLevelEffects.add(new OrdealTitleBack(3, 2, true));
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "FALL":
                this.state.setAnimation(0, "Down", false);
                this.state.setTimeScale(1F);
                this.state.addAnimation(0, "Default", true, 0.0F);
                break;
            case "CAST":
                this.state.setAnimation(0, "Casting", false);
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
