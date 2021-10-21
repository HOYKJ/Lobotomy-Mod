package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.WorkerBee;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.action.LatterEffect;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class QueenBee extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "QueenBee";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public ArrayList<AbstractCard> list = new ArrayList<>();
    public ArrayList<AbstractCard> list2 = new ArrayList<>();

    public QueenBee() {
        super("QueenBee", QueenBee.NAME, QueenBee.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 50, 4, 0, CardTarget.ALL_ENEMY);
        this.baseDamage = 10;
        this.isMultiDamage = true;
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
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
        infect();
    }

    private void infect(){
        ArrayList<AbstractCard> draw = new ArrayList<>(AbstractDungeon.player.drawPile.group);
        ArrayList<AbstractCard> hand = new ArrayList<>(AbstractDungeon.player.hand.group);
        ArrayList<AbstractCard> discard = new ArrayList<>(AbstractDungeon.player.discardPile.group);
        hand.remove(this);
        discard.remove(this);

        for(AbstractCard card : AbstractDungeon.player.drawPile.group){
            if(card instanceof WorkerBee){
                draw.remove(card);
            }
        }
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof WorkerBee){
                hand.remove(card);
            }
        }
        for(AbstractCard card : AbstractDungeon.player.discardPile.group){
            if(card instanceof WorkerBee){
                discard.remove(card);
            }
        }

        for(int i = 0; i < 2; i ++){
            AbstractCard card;
            if(draw.size() > 0){
                card = draw.get(AbstractDungeon.cardRng.random(draw.size() - 1));
                draw.remove(card);
                this.list.add(card);
            }
            else if(hand.size() > 0){
                card = hand.get(AbstractDungeon.cardRng.random(hand.size() - 1));
                hand.remove(card);
                this.list.add(card);
            }
            else if(discard.size() > 0){
                card = discard.get(AbstractDungeon.cardRng.random(discard.size() - 1));
                discard.remove(card);
                this.list.add(card);
            }
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        for(AbstractCard card : this.list2){
            if(AbstractDungeon.player.drawPile.contains(card)) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
            }
            else if(AbstractDungeon.player.hand.contains(card)) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            }
            else if(AbstractDungeon.player.discardPile.contains(card)) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
            }
            else {
                return;
            }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new WorkerBee(), 1, true, false));
        }

        this.list2.clear();
        this.list2.addAll(this.list);
        this.list.clear();
    }

    @Override
    public void onUsedCard(AbstractCard card, boolean hand) {
        super.onUsedCard(card, hand);
        this.list.remove(card);
        this.list2.remove(card);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new QueenBee();
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
        this.name = QueenBee.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = QueenBee.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("QueenBee");
        NAME = QueenBee.cardStrings.NAME;
        DESCRIPTION = QueenBee.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = QueenBee.cardStrings.EXTENDED_DESCRIPTION;
    }
}
