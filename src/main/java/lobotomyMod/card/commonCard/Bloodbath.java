package lobotomyMod.card.commonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class Bloodbath extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "Bloodbath";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private int BloodHand;

    public Bloodbath() {
        super("Bloodbath", Bloodbath.NAME, Bloodbath.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 51, 3, 0);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
        if(this.BloodHand >= 3){
            this.baseMagicNumber -= this.BloodHand;
            this.magicNumber = this.baseMagicNumber;
            this.BloodHand = 0;
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if((this.BloodHand >= 3) || (!hand)){
            return;
        }
        if(AbstractDungeon.player.hand.size() > 1){
            AbstractCard card = AbstractDungeon.player.hand.getRandomCard(true);
            while (card == this){
                card = AbstractDungeon.player.hand.getRandomCard(true);
            }
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
            this.BloodHand ++;
            this.baseMagicNumber ++;
            this.magicNumber = this.baseMagicNumber;
        }
    }

    public AbstractCard makeCopy() {
        return new Bloodbath();
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
        this.name = Bloodbath.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = Bloodbath.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Bloodbath");
        NAME = Bloodbath.cardStrings.NAME;
        DESCRIPTION = Bloodbath.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Bloodbath.cardStrings.EXTENDED_DESCRIPTION;
    }
}
