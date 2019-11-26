package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.helper.LobotomyUtils;

/**
 * @author hoykj
 */
public class Hornet extends AbstractEgoCard {
    public static final String ID = "Hornet";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Hornet() {
        super("Hornet", Hornet.NAME, 1, Hornet.DESCRIPTION, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 6;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        for(AbstractPower p : AbstractDungeon.player.powers){
            if(p.type == AbstractPower.PowerType.DEBUFF){
                return;
            }
        }
        this.damage *= 2;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        for(AbstractPower p : AbstractDungeon.player.powers){
            if(p.type == AbstractPower.PowerType.DEBUFF){
                return;
            }
        }
        this.damage *= 2;
        this.initializeDescription();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(3);
    }

    public AbstractCard makeCopy() {
        return new Hornet();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Hornet");
        NAME = Hornet.cardStrings.NAME;
        DESCRIPTION = Hornet.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Hornet.cardStrings.EXTENDED_DESCRIPTION;
    }
}
