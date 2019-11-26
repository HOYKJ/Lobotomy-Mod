package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.helper.LobotomyUtils;

/**
 * @author hoykj
 */
public class Pleasure extends AbstractEgoCard {
    public static final String ID = "Pleasure";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Pleasure() {
        super("Pleasure", Pleasure.NAME, 0, Pleasure.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WeakPower(p, 1, false), 1));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 2));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.makeStatEquivalentCopy()));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        for(AbstractCard card : p.hand.group){
            if(card instanceof Pleasure){
                count ++;
            }
            if(count > 1){
                return false;
            }
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.isEthereal = true;
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new Pleasure();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Pleasure");
        NAME = Pleasure.cardStrings.NAME;
        DESCRIPTION = Pleasure.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Pleasure.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = Pleasure.cardStrings.EXTENDED_DESCRIPTION;
    }
}
