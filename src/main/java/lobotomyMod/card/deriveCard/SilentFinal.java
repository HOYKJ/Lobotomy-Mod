package lobotomyMod.card.deriveCard;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.animation.SilentMovementAction;

/**
 * @author hoykj
 */
public class SilentFinal extends AbstractDeriveCard{
    public static final String ID = "SilentFinal";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public SilentFinal() {
        super("SilentFinal", SilentFinal.NAME, -2, SilentFinal.DESCRIPTION, CardColor.COLORLESS, CardType.SKILL, CardTarget.NONE);
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToTop(new SilentMovementAction(5));
        for (int i = (AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1); i >= 0; i--) {
            AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
            if ((!(target.isDying)) && (target.currentHealth > 0) && (!(target.isEscaping))) {
                AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(target));
                AbstractDungeon.actionManager.addToBottom(new HealAction(target, AbstractDungeon.player, target.maxHealth));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new SilentSonata(), 1, true, false));
        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new SilentFinal();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SilentFinal");
        NAME = SilentFinal.cardStrings.NAME;
        DESCRIPTION = SilentFinal.cardStrings.DESCRIPTION;
    }
}
