package lobotomyMod.card.deriveCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.ChooseAction;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.action.unique.BlackArmyBackAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.rareCard.BlackArmy;

/**
 * @author hoykj
 */
public class BlackArmyDerive  extends AbstractDeriveCard{
    public static final String ID = "BlackArmyDerive";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public BlackArmyDerive() {
        super("BlackArmyDerive", BlackArmyDerive.NAME, -2, BlackArmyDerive.DESCRIPTION, CardColor.COLORLESS, CardType.SKILL, CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 10)));
        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        for(AbstractGameAction a : AbstractDungeon.actionManager.actions){
            if(a instanceof BlackArmyBackAction){
                return;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new BlackArmyBackAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new BlackArmyDerive();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BlackArmyDerive");
        NAME = BlackArmyDerive.cardStrings.NAME;
        DESCRIPTION = BlackArmyDerive.cardStrings.DESCRIPTION;
    }
}
