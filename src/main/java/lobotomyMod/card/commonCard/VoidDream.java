package lobotomyMod.card.commonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class VoidDream extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "VoidDream";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public VoidDream() {
        super("VoidDream", VoidDream.NAME, VoidDream.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 99, 3, 1);
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
        AbstractCard card = LobotomyUtils.getRandomCard(p.discardPile, p.drawPile, p.hand, this);
        if((card != null) && (!card.isEthereal)){
            card.isEthereal = true;
            card.rawDescription += EXTENDED_DESCRIPTION[this.maxInfo];
            card.initializeDescription();
        }
    }

    @Override
    public void onUsedCard(AbstractCard card, boolean hand, AbstractCreature target) {
        super.onUsedCard(card, hand, target);
        if(card.isEthereal){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new VoidDream();
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
        this.name = VoidDream.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = VoidDream.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("VoidDream");
        NAME = VoidDream.cardStrings.NAME;
        DESCRIPTION = VoidDream.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = VoidDream.cardStrings.EXTENDED_DESCRIPTION;
    }
}
