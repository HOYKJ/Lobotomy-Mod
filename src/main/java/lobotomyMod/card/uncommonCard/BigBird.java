package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class BigBird extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "BigBird";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private int counter, counter2;
    public ArrayList<AbstractCard> focus1 = new ArrayList<>();
    public ArrayList<AbstractCard> focus2 = new ArrayList<>();

    public BigBird() {
        super("BigBird", BigBird.NAME, BigBird.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 40, 4, -2);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.counter = 0;
        this.counter2 = 0;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
    }

    @Override
    public void onUsedCard(AbstractCard card, boolean hand, AbstractCreature target) {
        super.onUsedCard(card, hand, target);
        this.focus2.remove(card);
        this.focus1.remove(card);

        if(card.type == CardType.ATTACK){
            this.counter ++;
            if(this.counter >= 3){
                this.counter = 0;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, 1, false), 1));
            }
        }

        this.counter2 ++;
        if(this.counter2 >= this.magicNumber){
            this.counter2 = 0;
            if((AbstractDungeon.player.drawPile.contains(this)) && (AbstractDungeon.player.drawPile.size() > 1)){
                AbstractCard c = AbstractDungeon.player.drawPile.getRandomCard(true);
                while (c == this){
                    c = AbstractDungeon.player.drawPile.getRandomCard(true);
                }
                if((!this.focus1.contains(c)) && (!this.focus2.contains(c))){
                    this.focus1.add(c);
                    LobotomyMod.logger.info(c.cardID);
                }
            }
            if((AbstractDungeon.player.hand.contains(this)) && (AbstractDungeon.player.hand.size() > 1)){
                AbstractCard c = AbstractDungeon.player.hand.getRandomCard(true);
                while (c == this){
                    c = AbstractDungeon.player.hand.getRandomCard(true);
                }
                if((!this.focus1.contains(c)) && (!this.focus2.contains(c))){
                    this.focus1.add(c);
                    LobotomyMod.logger.info(c.cardID);
                }
            }
            if((AbstractDungeon.player.discardPile.contains(this)) && (AbstractDungeon.player.discardPile.size() > 1)){
                AbstractCard c = AbstractDungeon.player.discardPile.getRandomCard(true);
                while (c == this){
                    c = AbstractDungeon.player.discardPile.getRandomCard(true);
                }
                if((!this.focus1.contains(c)) && (!this.focus2.contains(c))){
                    this.focus1.add(c);
                    LobotomyMod.logger.info(c.cardID);
                }
            }
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        for(AbstractCard card : this.focus2){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
        }
        this.focus2.clear();

        this.focus2.addAll(this.focus1);
        this.focus1.clear();
    }

    @Override
    public void ExhaustCard(AbstractCard card, boolean hand) {
        super.ExhaustCard(card, hand);
        this.focus2.remove(card);
        this.focus1.remove(card);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new BigBird();
    }


    @Override
    public void unlockSuccess() {
        super.unlockSuccess();
        initInfo();
    }

    @Override
    public void initInfo(){
        if(CogitoBucket.level[this.AbnormalityID] < 1) {
            return;
        }
        this.i[0] = CogitoBucket.level[this.AbnormalityID];
        loadImg();
        this.name = BigBird.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = BigBird.EXTENDED_DESCRIPTION[this.infoStage];
        this.initializeDescription();
        super.initInfo();
    }

    @Override
    public void loadImg() {
        if(CogitoBucket.level[this.AbnormalityID] < 1) {
            return;
        }
        this.textureImg = LobotomyHandler.lobotomyCardImage(this.cardID);
        loadCardImage(this.textureImg);
    }

    @Override
    public int[] onSave() {
        this.i[0] = CogitoBucket.level[this.AbnormalityID];
        return this.i;
    }

    @Override
    public void onLoad(int[] arg0)
    {
        if (arg0 == null) {
            return;
        }
        if(arg0[0] > 0){
            CogitoBucket.level[this.AbnormalityID] = arg0[0];
        }

        initInfo();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BigBird");
        NAME = BigBird.cardStrings.NAME;
        DESCRIPTION = BigBird.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = BigBird.cardStrings.EXTENDED_DESCRIPTION;
    }
}
