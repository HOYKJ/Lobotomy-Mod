package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class GreedKing extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "GreedKing";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private int counter;

    public GreedKing() {
        super("GreedKing", GreedKing.NAME, GreedKing.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 64, 2, -2);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.counter = 0;
    }

    @Override
    public void onUsedCard(AbstractCard card, boolean hand) {
        super.onUsedCard(card, hand);
        if(card.type == CardType.ATTACK){
            this.counter ++;
            if(this.counter >= 8){
                this.counter = 0;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
            }
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(!hand){
            return;
        }
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card == this){
                return;
            }
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public AbstractCard makeCopy() {
        return new GreedKing();
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
        if(CogitoBucket.level[this.AbnormalityID] > 1){
            this.name = GreedKing.EXTENDED_DESCRIPTION[1];
        }
        else {
            this.name = GreedKing.EXTENDED_DESCRIPTION[0];
        }
        this.initializeTitle();
        this.rawDescription = GreedKing.EXTENDED_DESCRIPTION[this.infoStage + 1];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("GreedKing");
        NAME = GreedKing.cardStrings.NAME;
        DESCRIPTION = GreedKing.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = GreedKing.cardStrings.EXTENDED_DESCRIPTION;
    }
}
