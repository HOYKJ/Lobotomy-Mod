package lobotomyMod.card.ego.rare;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.ChooseAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class SoundOfStar extends AbstractEgoCard {
    public static final String ID = "SoundOfStar";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public SoundOfStar() {
        super("SoundOfStar", SoundOfStar.NAME, -1, SoundOfStar.DESCRIPTION, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        int tmp = this.energyOnUse;
        if (p.hasRelic("Chemical X"))
        {
            tmp += 2;
            p.getRelic("Chemical X").flash();
        }
        if(tmp <= 0){
            return;
        }
        final ChooseAction choice = new ChooseAction(null, null, AbstractLobotomyCard.EXTENDED_DESCRIPTION[2],true,
                (this.upgraded? tmp + 1: tmp), true);
        for(AbstractCard card : AbstractDungeon.player.hand.group) {
            if(card == this){
                continue;
            }
            choice.add(card, ()->{
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(card.cost));
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
            });
        }
        AbstractDungeon.actionManager.addToBottom(choice);
        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new SoundOfStar();
    }

    @Override
    public void addTips() {
        this.tips.clear();
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SoundOfStar");
        NAME = SoundOfStar.cardStrings.NAME;
        DESCRIPTION = SoundOfStar.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = SoundOfStar.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = SoundOfStar.cardStrings.EXTENDED_DESCRIPTION;
    }
}
