package lobotomyMod.card.deriveCard;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
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
public class MeltingSlime extends AbstractDeriveCard{
    public static final String ID = "MeltingSlime";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public MeltingSlime() {
        super("MeltingSlime", MeltingSlime.NAME, 1, MeltingSlime.DESCRIPTION, CardColor.COLORLESS, CardType.SKILL, CardTarget.NONE);
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
        this.purgeOnUse = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for (int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); i++) {
            AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
            if ((!(target.isDying)) && (target.currentHealth > 0) && (!(target.isEscaping))) {
                AbstractDungeon.actionManager.addToBottom(new HealAction(target, p, this.magicNumber));
            }
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 2)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MeltingSlime();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("MeltingSlime");
        NAME = MeltingSlime.cardStrings.NAME;
        DESCRIPTION = MeltingSlime.cardStrings.DESCRIPTION;
    }
}
