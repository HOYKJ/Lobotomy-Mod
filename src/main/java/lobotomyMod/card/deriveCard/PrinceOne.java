package lobotomyMod.card.deriveCard;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * @author hoykj
 */
public class PrinceOne extends AbstractDeriveCard{
    public static final String ID = "PrinceOne";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public PrinceOne() {
        super("PrinceOne", PrinceOne.NAME, 2, PrinceOne.DESCRIPTION, CardColor.CURSE, CardType.SKILL, CardTarget.NONE);
        this.purgeOnUse = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 5, this.damageTypeForTurn)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PrinceOne();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("PrinceOne");
        NAME = PrinceOne.cardStrings.NAME;
        DESCRIPTION = PrinceOne.cardStrings.DESCRIPTION;
    }
}
