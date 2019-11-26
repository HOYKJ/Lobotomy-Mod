package lobotomyMod.card.rareCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.ChooseAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.MeltingSlime;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class MeltingLove extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "MeltingLove";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public AbstractCard heart;
    private int counter;

    public MeltingLove() {
        super("MeltingLove", MeltingLove.NAME, MeltingLove.DESCRIPTION, CardRarity.RARE, CardTarget.ENEMY, 109, 5, 1);
        this.heart = null;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        this.counter = 2;
        if(this.heart != null){
            return;
        }
        final ChooseAction choice = new ChooseAction(this, null, AbstractLobotomyCard.EXTENDED_DESCRIPTION[0],false, 1, true);
        for(AbstractCard card : p.hand.group) {
            if((!(card instanceof AbstractLobotomyCard)) && (card.type != CardType.POWER)){
                choice.add(card, ()->{
                    this.heart = card;
                    card.modifyCostForCombat(-99);
                });
            }
        }
        AbstractDungeon.actionManager.addToBottom(choice);
    }

    @Override
    public void ExhaustCard(AbstractCard card, boolean hand) {
        super.ExhaustCard(card, hand);
        if(this.heart == null){
            return;
        }
        if(card == this.heart){
            for(AbstractCard card1 : AbstractDungeon.player.drawPile.group){
                if(card1 instanceof MeltingSlime){
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card1, AbstractDungeon.player.drawPile));
                }
            }
            for(AbstractCard card1 : AbstractDungeon.player.hand.group){
                if(card1 instanceof MeltingSlime){
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card1, AbstractDungeon.player.hand));
                }
            }
            for(AbstractCard card1 : AbstractDungeon.player.discardPile.group){
                if(card1 instanceof MeltingSlime){
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card1, AbstractDungeon.player.discardPile));
                }
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 15, DamageInfo.DamageType.HP_LOSS)));
        }
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        for(AbstractCard card1 : AbstractDungeon.player.drawPile.group){
            if(card1 == this.heart){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card1, AbstractDungeon.player.drawPile));
            }
            if(card1 instanceof MeltingSlime){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card1, AbstractDungeon.player.drawPile));
            }
        }
        for(AbstractCard card1 : AbstractDungeon.player.hand.group){
            if(card1 == this.heart){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card1, AbstractDungeon.player.hand));
            }
            if(card1 instanceof MeltingSlime){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card1, AbstractDungeon.player.hand));
            }
        }
        for(AbstractCard card1 : AbstractDungeon.player.discardPile.group){
            if(card1 == this.heart){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card1, AbstractDungeon.player.discardPile));
            }
            if(card1 instanceof MeltingSlime){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card1, AbstractDungeon.player.discardPile));
            }
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(this.heart == null){
            return;
        }
        if(this.counter == 0){
            if(AbstractDungeon.player.drawPile.contains(this.heart)){
                int card = LobotomyUtils.getRandomCard(AbstractDungeon.player.drawPile, this.heart, true);
                AbstractDungeon.player.drawPile.group.set(card, new MeltingSlime());
            }
            else if(AbstractDungeon.player.hand.contains(this.heart)){
                int card = LobotomyUtils.getRandomCard(AbstractDungeon.player.hand, this.heart, true);
                AbstractDungeon.player.hand.group.set(card, new MeltingSlime());
            }
            else if(AbstractDungeon.player.discardPile.contains(this.heart)){
                int card = LobotomyUtils.getRandomCard(AbstractDungeon.player.discardPile, this.heart, true);
                AbstractDungeon.player.discardPile.group.set(card, new MeltingSlime());
            }
        }
        else {
            this.counter --;
        }
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount, boolean hand) {
        if(this.heart == null){
            return super.onAttackedToChangeDamage(info, damageAmount, hand);
        }
        int tmp = damageAmount;
        if(AbstractDungeon.player.currentHealth / (float)AbstractDungeon.player.maxHealth <= 0.5F){
            tmp -= 10;
        }
        return tmp;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new MeltingLove();
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
        this.name = MeltingLove.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = MeltingLove.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("MeltingLove");
        NAME = MeltingLove.cardStrings.NAME;
        DESCRIPTION = MeltingLove.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = MeltingLove.cardStrings.EXTENDED_DESCRIPTION;
    }
}
