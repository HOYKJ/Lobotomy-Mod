package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import lobotomyMod.card.angelaCard.bullets.SpecialBullet;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class ScreamingWedge extends AbstractEgoCard {
    public static final String ID = "ScreamingWedge";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public ScreamingWedge() {
        super("ScreamingWedge", ScreamingWedge.NAME, 2, ScreamingWedge.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL);
        this.baseBlock = 6;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!(mo.isDeadOrEscaped())){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.magicNumber), -this.magicNumber));
            }
        }
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(3);
    }

    public AbstractCard makeCopy() {
        return new ScreamingWedge();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ScreamingWedge");
        NAME = ScreamingWedge.cardStrings.NAME;
        DESCRIPTION = ScreamingWedge.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = ScreamingWedge.cardStrings.EXTENDED_DESCRIPTION;
    }
}
