package lobotomyMod.card.ego.rare;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class GoldRush extends AbstractEgoCard {
    public static final String ID = "GoldRush";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public GoldRush() {
        super("GoldRush", GoldRush.NAME, 0, GoldRush.DESCRIPTION, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 6;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            if(!AbstractDungeon.player.hand.contains(this)) {
                this.unhover();
                this.lighten(true);
                this.setAngle(0.0F);
                this.drawScale = 0.12F;
                this.targetDrawScale = 0.75F;
                AbstractDungeon.player.drawPile.removeCard(this);
                AbstractDungeon.player.discardPile.removeCard(this);
                AbstractDungeon.player.hand.addToTop(this);
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.player.hand.applyPowers();
            }
        }));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(3);
    }

    public AbstractCard makeCopy() {
        return new GoldRush();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("GoldRush");
        NAME = GoldRush.cardStrings.NAME;
        DESCRIPTION = GoldRush.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = GoldRush.cardStrings.EXTENDED_DESCRIPTION;
    }
}
