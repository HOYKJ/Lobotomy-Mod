package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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
public class SwordSharpenedByTears extends AbstractEgoCard {
    public static final String ID = "SwordSharpenedByTears";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public SwordSharpenedByTears() {
        super("SwordSharpenedByTears", SwordSharpenedByTears.NAME, 3, SwordSharpenedByTears.DESCRIPTION, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 9;
        this.isMultiDamage = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for(AbstractCard card : p.hand.group){
            if(card.type == CardType.SKILL){
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(3);
    }

    public AbstractCard makeCopy() {
        return new SwordSharpenedByTears();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SwordSharpenedByTears");
        NAME = SwordSharpenedByTears.cardStrings.NAME;
        DESCRIPTION = SwordSharpenedByTears.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = SwordSharpenedByTears.cardStrings.EXTENDED_DESCRIPTION;
    }
}
