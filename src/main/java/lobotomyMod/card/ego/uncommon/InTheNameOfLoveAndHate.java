package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.helper.LobotomyUtils;

/**
 * @author hoykj
 */
public class InTheNameOfLoveAndHate extends AbstractEgoCard {
    public static final String ID = "InTheNameOfLoveAndHate";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public InTheNameOfLoveAndHate() {
        super("InTheNameOfLoveAndHate", InTheNameOfLoveAndHate.NAME, 2, InTheNameOfLoveAndHate.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF_AND_ENEMY);
        this.baseBlock = 32;
        this.isEthereal = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(LobotomyUtils.isAttack(m) && AbstractDungeon.player.currentBlock == 0){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(10);
    }

    public AbstractCard makeCopy() {
        return new InTheNameOfLoveAndHate();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("InTheNameOfLoveAndHate");
        NAME = InTheNameOfLoveAndHate.cardStrings.NAME;
        DESCRIPTION = InTheNameOfLoveAndHate.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = InTheNameOfLoveAndHate.cardStrings.EXTENDED_DESCRIPTION;
    }
}
