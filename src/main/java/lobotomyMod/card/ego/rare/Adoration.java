package lobotomyMod.card.ego.rare;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.ChooseAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class Adoration extends AbstractEgoCard {
    public static final String ID = "Adoration";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Adoration() {
        super("Adoration", Adoration.NAME, 2, Adoration.DESCRIPTION, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        final ChooseAction choice = new ChooseAction(null, null, AbstractLobotomyCard.EXTENDED_DESCRIPTION[2],true, 1, true);
        for(AbstractCard card : AbstractDungeon.player.hand.group) {
            if(card == this){
                continue;
            }
            choice.add(card, ()->{
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card.makeStatEquivalentCopy(), 2));
            });
        }
        if(this.upgraded){
            for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
                choice.add(card, ()->{
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card.makeStatEquivalentCopy(), 2));
                });
            }
            for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
                choice.add(card, ()->{
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card.makeStatEquivalentCopy(), 2));
                });
            }
        }
        AbstractDungeon.actionManager.addToBottom(choice);
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new Adoration();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Adoration");
        NAME = Adoration.cardStrings.NAME;
        DESCRIPTION = Adoration.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Adoration.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = Adoration.cardStrings.EXTENDED_DESCRIPTION;
    }
}
