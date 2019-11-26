package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.helper.LobotomyUtils;

/**
 * @author hoykj
 */
public class LifeForTheDareDevil extends AbstractEgoCard {
    public static final String ID = "LifeForTheDareDevil";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public LifeForTheDareDevil() {
        super("LifeForTheDareDevil", LifeForTheDareDevil.NAME, 1, LifeForTheDareDevil.DESCRIPTION, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.isInnate = true;
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        boolean flag = false;
        for(AbstractCard card : p.drawPile.getCardsOfType(CardType.SKILL).group){
            if(card instanceof AbstractLobotomyCard){
                continue;
            }
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, p.drawPile));
            flag = true;
        }
        for(AbstractCard card : p.hand.getCardsOfType(CardType.SKILL).group){
            if(card instanceof AbstractLobotomyCard){
                continue;
            }
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, p.hand));
            flag = true;
        }
        for(AbstractCard card : p.discardPile.getCardsOfType(CardType.SKILL).group){
            if(card instanceof AbstractLobotomyCard){
                continue;
            }
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, p.discardPile));
            flag = true;
        }
        if (flag) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p, this.magicNumber), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeMagicNumber(2);
    }

    public AbstractCard makeCopy() {
        return new LifeForTheDareDevil();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("LifeForTheDareDevil");
        NAME = LifeForTheDareDevil.cardStrings.NAME;
        DESCRIPTION = LifeForTheDareDevil.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = LifeForTheDareDevil.cardStrings.EXTENDED_DESCRIPTION;
    }
}
