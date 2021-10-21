package lobotomyMod.card.rareCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.ExhaustOtherAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class CENSORED extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "CENSORED";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public CENSORED() {
        super("CENSORED", CENSORED.NAME, CENSORED.DESCRIPTION, CardRarity.RARE, CardTarget.ENEMY, 89, 4, 1, CardTarget.NONE);
        this.baseMagicNumber = 12;
        this.magicNumber = this.baseMagicNumber;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        if(p.hand.size() > 1){
            AbstractCard card = p.hand.getRandomCard(true);
            while (card == this){
                card = p.hand.getRandomCard(true);
            }
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, p.hand));
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
        }
        else if(p.drawPile.size() > 0){
            AbstractCard card = p.drawPile.getRandomCard(true);
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, p.drawPile));
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
        }
        else if(p.discardPile.size() > 0){
            AbstractCard card = p.discardPile.getRandomCard(true);
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, p.discardPile));
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
        }
    }

    @Override
    public void onLoseHP(int damageAmount, boolean hand) {
        super.onLoseHP(damageAmount, hand);
        AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, (int) (damageAmount * 0.4F)));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        if(AbstractDungeon.cardRng.randomBoolean()){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 3, false), 3));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, 3, false), 3));
        }

        int counter = 0;
        for(AbstractPower power : AbstractDungeon.player.powers){
            if(power.type == AbstractPower.PowerType.DEBUFF){
                if(power.amount > 1){
                    counter += power.amount;
                }
                else {
                    counter ++;
                }

                if(counter >= 6){
                    AbstractDungeon.actionManager.addToBottom(new ExhaustOtherAction(this));
                    break;
                }
            }
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new CENSORED();
    }

    @Override
    public void unlockSuccess() {
        super.unlockSuccess();
        initInfo();
    }

    public void initInfo(){
        if(CogitoBucket.level[this.AbnormalityID] < 1) {
            return;
        }
        this.i[0] = CogitoBucket.level[this.AbnormalityID];
        loadImg();
        this.name = CENSORED.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = CENSORED.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("CENSORED");
        NAME = CENSORED.cardStrings.NAME;
        DESCRIPTION = CENSORED.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = CENSORED.cardStrings.EXTENDED_DESCRIPTION;
    }
}
