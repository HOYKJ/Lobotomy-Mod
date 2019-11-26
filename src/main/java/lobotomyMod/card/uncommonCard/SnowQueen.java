package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.MakeCardInHandAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.Duel;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class SnowQueen extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "SnowQueen";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public SnowQueen() {
        super("SnowQueen", SnowQueen.NAME, SnowQueen.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 37, 4, 0);
        this.exhaust = true;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SlowPower(m, 1), 1));
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        AbstractCard card;
        if(AbstractDungeon.player.hand.size() > 0){
            card = AbstractDungeon.player.hand.getRandomCard(true);
        }
        else if(AbstractDungeon.player.drawPile.size() > 0){
            card = AbstractDungeon.player.drawPile.getRandomCard(true);
        }
        else if(AbstractDungeon.player.discardPile.size() > 0){
            card = AbstractDungeon.player.discardPile.getRandomCard(true);
        }
        else {
            return;
        }

        card.costForTurn = 1;
        card.modifyCostForCombat(9);
        AbstractDungeon.actionManager.addToTop(new MakeCardInHandAction(new Duel(card)));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new SnowQueen();
    }


    @Override
    public void unlockSuccess() {
        super.unlockSuccess();
        initInfo();
    }

    public void initInfo(){
        if(CogitoBucket.level[this.AbnormalityID] < 1) {
            return;
        }
        this.i[0] = CogitoBucket.level[this.AbnormalityID];
        loadImg();
        this.name = SnowQueen.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = SnowQueen.EXTENDED_DESCRIPTION[this.infoStage];
        this.initializeDescription();
        super.initInfo();
    }

    @Override
    public void loadImg() {
        if(CogitoBucket.level[this.AbnormalityID] < 1) {
            return;
        }
        this.textureImg = LobotomyHandler.lobotomyCardImage(this.cardID);
        loadCardImage(this.textureImg);
    }

    @Override
    public int[] onSave() {
        this.i[0] = CogitoBucket.level[this.AbnormalityID];
        return this.i;
    }

    @Override
    public void onLoad(int[] arg0)
    {
        if (arg0 == null) {
            return;
        }
        if(arg0[0] > 0){
            CogitoBucket.level[this.AbnormalityID] = arg0[0];
        }

        initInfo();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SnowQueen");
        NAME = SnowQueen.cardStrings.NAME;
        DESCRIPTION = SnowQueen.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = SnowQueen.cardStrings.EXTENDED_DESCRIPTION;
    }
}
