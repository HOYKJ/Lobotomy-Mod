package lobotomyMod.relic.toolAbnormality;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.relicCard.AbstractLobotomyRelicCard;
import lobotomyMod.card.relicCard.BackwardClockRelic;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.vfx.BackwardEffect;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public class BackwardClock extends AbstractLobotomyAbnRelic {
    public static final String ID = "BackwardClock";
    private boolean active;
    private ArrayList<AbstractCard> draw = new ArrayList<>(), hand = new ArrayList<>(), discard = new ArrayList<>(), exhaust = new ArrayList<>();
    private ArrayList<AbstractPower> powers = new ArrayList<>();
    private ArrayList<Integer> powerStatus = new ArrayList<>();
    private ArrayList<AbstractPotion> potions = new ArrayList<>();
    private ArrayList<AbstractRelic> relics = new ArrayList<>();
    private int maxHealth, health, gold, block;

    public BackwardClock()
    {
        super("BackwardClock",  RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void obtain() {
        super.obtain();
        LobotomyMod.hasBackward = true;
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        if(this.active){
            this.getRecord();
            this.active = false;
            AbstractDungeon.player.loseRelic(this.relicId);
        }
        else {
            this.active = true;
            this.img = ImageMaster.loadImage(LobotomyHandler.lobotomyRelicImage("BackwardClock_1"));
            this.setRecord();
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.active = false;
        this.img = ImageMaster.loadImage(LobotomyHandler.lobotomyRelicImage("BackwardClock"));
    }

    private void setRecord(){
        for(AbstractCard card : AbstractDungeon.player.drawPile.group){
            this.draw.add(card.makeStatEquivalentCopy());
        }
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            this.hand.add(card.makeStatEquivalentCopy());
        }
        for(AbstractCard card : AbstractDungeon.player.discardPile.group){
            this.discard.add(card.makeStatEquivalentCopy());
        }
        for(AbstractCard card : AbstractDungeon.player.exhaustPile.group){
            this.exhaust.add(card.makeStatEquivalentCopy());
        }

//        for(AbstractPower power : AbstractDungeon.player.powers){
//            try {
////                AbstractPower obj = power.getClass().newInstance();
//                Field fu = Unsafe.class.getDeclaredField("theUnsafe");
//                fu.setAccessible(true);
//                Unsafe us = (Unsafe) fu.get(null);
//                Object obj = us.allocateInstance(power.getClass());
//                for(Field f : power.getClass().getDeclaredFields()){
//                    if(Modifier.isFinal(f.getModifiers())){
//                        continue;
//                    }
//                    f.setAccessible(true);
//                    f.set(obj, f.get(power));
//                }
//                this.powers.add((AbstractPower) obj);
//            } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
//                e.printStackTrace();
//            }
//        }

        this.powers.addAll(AbstractDungeon.player.powers);
        for(AbstractPower power : this.powers){
//            this.powerStatus.add(new ArrayList<>());
//            for(Field f : power.getClass().getDeclaredFields()){
//                if(Modifier.isFinal(f.getModifiers())){
//                    continue;
//                }
//                f.setAccessible(true);
//                try {
//                    this.powerStatus.get(this.powerStatus.size() - 1).add(f.get(power));
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
            this.powerStatus.add(power.amount);
        }

        this.potions.addAll(AbstractDungeon.player.potions);

        for(AbstractRelic relic : AbstractDungeon.player.relics){
            try {
                AbstractRelic obj = relic.getClass().newInstance();
                for(Field f : relic.getClass().getDeclaredFields()){
                    if(Modifier.isFinal(f.getModifiers())){
                        continue;
                    }
                    f.setAccessible(true);
                    f.set(obj, f.get(relic));
                }
                this.relics.add(obj);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        this.maxHealth = AbstractDungeon.player.maxHealth;
        this.health = AbstractDungeon.player.currentHealth;
        this.gold = AbstractDungeon.player.gold;
        this.block = AbstractDungeon.player.currentBlock;
    }

    private void getRecord(){
        CardCrawlGame.sound.play("WarpClock_Skill_Start");
        AbstractDungeon.topLevelEffects.add(new BackwardEffect());
        AbstractDungeon.player.drawPile.group = this.draw;
        AbstractDungeon.player.hand.group = this.hand;
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.discardPile.group = this.discard;
        AbstractDungeon.player.exhaustPile.group = this.exhaust;

        AbstractDungeon.player.powers = this.powers;
        int index = 0;
        for(AbstractPower power : this.powers){
//            int i = 0;
//            for(Field f : power.getClass().getDeclaredFields()){
//                if(Modifier.isFinal(f.getModifiers())){
//                    continue;
//                }
//                f.setAccessible(true);
//                try {
//                    f.set(power, this.powerStatus.get(index).get(i));
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                i ++;
//            }
            power.amount = this.powerStatus.get(index);
            index ++;
        }

        AbstractDungeon.player.potions = this.potions;
        AbstractDungeon.player.relics = this.relics;

        AbstractDungeon.player.maxHealth = this.maxHealth;
        AbstractDungeon.player.currentHealth = this.health;
        AbstractDungeon.player.healthBarUpdatedEvent();
        AbstractDungeon.player.gold = this.gold;
        AbstractDungeon.player.currentBlock = this.block;
    }

    @Override
    public AbstractLobotomyRelicCard getCard() {
        return new BackwardClockRelic();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new BackwardClock();
    }
}
