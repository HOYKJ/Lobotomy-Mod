package lobotomyMod.card.deriveCard;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.uncommonCard.BlackSwan;

/**
 * @author hoykj
 */
public class Elijah extends AbstractDeriveCard{
    public static final String ID = "Elijah";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public Elijah() {
        super("Elijah", Elijah.NAME, 2, Elijah.DESCRIPTION, CardColor.COLORLESS, CardType.SKILL, CardTarget.NONE);
        this.purgeOnUse = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for(AbstractCard card : p.drawPile.group){
            if(card instanceof BlackSwan){
                card.baseMagicNumber = 5;
                card.magicNumber = card.baseMagicNumber;
                for (int i = 0; i < ((BlackSwan) card).factor.length; i ++) {
                    ((BlackSwan) card).factor[i] = false;
                }
                return;
            }
        }
        for(AbstractCard card : p.hand.group){
            if(card instanceof BlackSwan){
                card.baseMagicNumber = 5;
                card.magicNumber = card.baseMagicNumber;
                for (int i = 0; i < ((BlackSwan) card).factor.length; i ++) {
                    ((BlackSwan) card).factor[i] = false;
                }
                return;
            }
        }
        for(AbstractCard card : p.discardPile.group){
            if(card instanceof BlackSwan){
                card.baseMagicNumber = 5;
                card.magicNumber = card.baseMagicNumber;
                for (int i = 0; i < ((BlackSwan) card).factor.length; i ++) {
                    ((BlackSwan) card).factor[i] = false;
                }
                return;
            }
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        if(AbstractDungeon.player.hand.size() > 1){
            AbstractCard card = AbstractDungeon.player.hand.getRandomCard(true);
            while (card == this){
                card = AbstractDungeon.player.hand.getRandomCard(true);
            }
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
        }
        else if(AbstractDungeon.player.drawPile.size() > 0){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.drawPile.getRandomCard(true), AbstractDungeon.player.drawPile));
        }
        else if(AbstractDungeon.player.discardPile.size() > 0){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.discardPile.getRandomCard(true), AbstractDungeon.player.discardPile));
        }
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target, boolean hand) {
        super.onAttack(info, damageAmount, target, hand);
        if(!hand){
            return;
        }
        if(target == AbstractDungeon.player){
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, info));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Elijah();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Elijah");
        NAME = Elijah.cardStrings.NAME;
        DESCRIPTION = Elijah.cardStrings.DESCRIPTION;
    }
}
