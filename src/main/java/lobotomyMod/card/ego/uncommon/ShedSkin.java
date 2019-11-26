package lobotomyMod.card.ego.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.card.ego.AbstractEgoCard;
import lobotomyMod.helper.LobotomyUtils;

/**
 * @author hoykj
 */
public class ShedSkin extends AbstractEgoCard {
    public static final String ID = "ShedSkin";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public ShedSkin() {
        super("ShedSkin", ShedSkin.NAME, -1, ShedSkin.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        int e = this.energyOnUse;
        if (p.hasRelic("Chemical X"))
        {
            e += 2;
            p.getRelic("Chemical X").flash();
        }
        int tmp = Math.min(e, p.hand.size() - 1);
        if(tmp <= 1){
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, tmp, false));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, tmp * this.magicNumber), tmp * this.magicNumber));
        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeMagicNumber(1);
    }

    public AbstractCard makeCopy() {
        return new ShedSkin();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ShedSkin");
        NAME = ShedSkin.cardStrings.NAME;
        DESCRIPTION = ShedSkin.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = ShedSkin.cardStrings.EXTENDED_DESCRIPTION;
    }
}
