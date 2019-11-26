package lobotomyMod.card.angelaCard.bullets;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.character.Angela;

/**
 * @author hoykj
 */
public class HpAim extends AbstractBulletCard {
    public static final String ID = "HpAim";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public HpAim() {
        super("HpAim", HpAim.NAME, 1, HpAim.DESCRIPTION, CardType.SKILL, CardTarget.SELF);
        this.baseMagicNumber = 4;
        if(AbstractDungeon.player instanceof Angela && ((Angela) AbstractDungeon.player).departments[5] > 3){
            this.baseMagicNumber += 2;
        }
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = 4;
        if(AbstractDungeon.player instanceof Angela && ((Angela) AbstractDungeon.player).departments[5] > 3){
            this.baseMagicNumber += 2;
        }
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new HpAim();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("HpAim");
        NAME = HpAim.cardStrings.NAME;
        DESCRIPTION = HpAim.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = HpAim.cardStrings.EXTENDED_DESCRIPTION;
    }
}
