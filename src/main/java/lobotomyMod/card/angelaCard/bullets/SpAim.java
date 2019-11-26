package lobotomyMod.card.angelaCard.bullets;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.ChooseAction;
import lobotomyMod.action.common.RemoveRandomDebuffAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.Angela;

/**
 * @author hoykj
 */
public class SpAim extends AbstractBulletCard {
    public static final String ID = "SpAim";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public SpAim() {
        super("SpAim", SpAim.NAME, 1, SpAim.DESCRIPTION, CardType.SKILL, CardTarget.SELF);
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RemoveRandomDebuffAction(p));
        if(AbstractDungeon.player instanceof Angela && ((Angela) AbstractDungeon.player).departments[5] > 3) {
            final ChooseAction choice = new ChooseAction(null, null, AbstractLobotomyCard.EXTENDED_DESCRIPTION[2], true, 1, true);
            for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                choice.add(card, ()->{
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
                });
            }
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                choice.add(card, ()->{
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                });
            }
            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                choice.add(card, ()->{
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
                });
            }
            AbstractDungeon.actionManager.addToBottom(choice);
        }
    }

    public AbstractCard makeCopy() {
        return new SpAim();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SpAim");
        NAME = SpAim.cardStrings.NAME;
        DESCRIPTION = SpAim.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = SpAim.cardStrings.EXTENDED_DESCRIPTION;
    }
}
