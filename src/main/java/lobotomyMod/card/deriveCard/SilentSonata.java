package lobotomyMod.card.deriveCard;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import lobotomyMod.action.animation.SilentMovementAction;

/**
 * @author hoykj
 */
public class SilentSonata extends AbstractDeriveCard{
    public static final String ID = "SilentSonata";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public SilentSonata() {
        super("SilentSonata", SilentSonata.NAME, -2, SilentSonata.DESCRIPTION, CardColor.COLORLESS, CardType.SKILL, CardTarget.NONE);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToTop(new SilentMovementAction(1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, this.magicNumber, false), this.magicNumber));
        for (int i = (AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1); i >= 0; i--) {
            AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
            if ((!(target.isDying)) && (target.currentHealth > 0) && (!(target.isEscaping))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new WeakPower(target, this.magicNumber, false), this.magicNumber));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new SilentAdagio(), 1));
        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new SilentSonata();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SilentSonata");
        NAME = SilentSonata.cardStrings.NAME;
        DESCRIPTION = SilentSonata.cardStrings.DESCRIPTION;
    }
}
