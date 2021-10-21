package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
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
import lobotomyMod.card.deriveCard.NestCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class RedShoes extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "RedShoes";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private boolean active;
    private AbstractCard targetCard;

    public RedShoes() {
        super("RedShoes", RedShoes.NAME, RedShoes.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 8, 3, 0, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.active = false;
        this.targetCard = null;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        this.active = true;
    }

    @Override
    public void onUsedCard(AbstractCard card, boolean hand) {
        super.onUsedCard(card, hand);
        if((this.active) && (card.type == CardType.ATTACK)){
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
        }
        if(card == this.targetCard){
            this.targetCard.exhaust = true;
        }
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        super.triggerOnEndOfTurnForPlayingCard();
        this.targetCard = AbstractDungeon.player.hand.getRandomCard(CardType.ATTACK, true);
        if(this.targetCard == null){
            this.targetCard = AbstractDungeon.player.drawPile.getRandomCard(CardType.ATTACK, true);
        }
        if(this.targetCard == null){
            this.targetCard = AbstractDungeon.player.discardPile.getRandomCard(CardType.ATTACK, true);
        }
        if(this.targetCard == null){
            return;
        }
        this.targetCard.exhaust = true;
        this.targetCard.rawDescription += NestCard.DESCRIPTION;
    }

    @Override
    public void ExhaustCard(AbstractCard card, boolean hand) {
        super.ExhaustCard(card, hand);
        if(card == this.targetCard){
            this.targetCard = null;
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        this.active = false;
        if(this.targetCard != null){
            this.targetCard.exhaust = true;
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player,
                    this.targetCard.baseDamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        if(this.targetCard != null){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new RedShoes();
    }


    @Override
    public void unlockSuccess() {
        super.unlockSuccess();
        initInfo();
    }

    @Override
    public void initInfo(){
        if(CogitoBucket.level[this.AbnormalityID] < 1) {
            return;
        }
        this.i[0] = CogitoBucket.level[this.AbnormalityID];
        loadImg();
        this.name = RedShoes.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = RedShoes.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("RedShoes");
        NAME = RedShoes.cardStrings.NAME;
        DESCRIPTION = RedShoes.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = RedShoes.cardStrings.EXTENDED_DESCRIPTION;
    }
}
