package lobotomyMod.card.angelaCard.code;

import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.angelaCard.bullets.AbstractBulletCard;
import lobotomyMod.card.angelaCard.department.Tiphereth;

/**
 * @author hoykj
 */
public class CentralCode extends AbstractCodeCard {
    public static final String ID = "CentralCode";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public CentralCode() {
        super("CentralCode", CentralCode.NAME, Tiphereth.ID, 1, CentralCode.DESCRIPTION, CardTarget.SELF);
        this.baseBlock = 6 + this.timesUpgraded * 3;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseBlock = 6 + this.timesUpgraded * 3;
        if(this.timesUpgraded < 1){
            return;
        }
        this.name = NAME + "+" + this.timesUpgraded;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof AbstractBulletCard){
                AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(card));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
            }
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.timesUpgraded ++;
        this.name = NAME + "+" + this.timesUpgraded;
        this.baseBlock = 6 + this.timesUpgraded * 3;
    }

    public AbstractCard makeCopy() {
        return new CentralCode();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("CentralCode");
        NAME = CentralCode.cardStrings.NAME;
        DESCRIPTION = CentralCode.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = CentralCode.cardStrings.EXTENDED_DESCRIPTION;
    }
}
