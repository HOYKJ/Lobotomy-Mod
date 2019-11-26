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
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.PrinceOne;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class LittlePrince extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "LittlePrince";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private int counter, counter2;
    public ArrayList<AbstractCard> prince = new ArrayList<>();

    public LittlePrince() {
        super("LittlePrince", LittlePrince.NAME, LittlePrince.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 66, 4, -2);
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ConfusionPower(AbstractDungeon.player)));
        ArrayList<AbstractCard> tmp = new ArrayList<>(AbstractDungeon.player.drawPile.group);
        tmp.remove(this);
        for(int i = 0; i < 4; i ++) {
            AbstractCard card = tmp.get(AbstractDungeon.cardRng.random(tmp.size() - 1));
            this.prince.add(card);
            tmp.remove(card);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            for(AbstractCard card : AbstractDungeon.player.hand.group){
                card.modifyCostForTurn(-1);
            }
        }));
    }

    //    @Override
//    public void atBattleStart() {
//        super.atBattleStart();
//        ArrayList<AbstractCard> tmp = new ArrayList<>(AbstractDungeon.player.drawPile.group);
//        tmp.remove(this);
//        for(int i = 0; i < 4; i ++) {
//            AbstractCard card = tmp.get(AbstractDungeon.cardRng.random(tmp.size() - 1));
//            this.prince.add(card);
//            tmp.remove(card);
//        }
//    }
//
    @Override
    public void onUsedCard(AbstractCard card, boolean hand, AbstractCreature target) {
        super.onUsedCard(card, hand, target);
        this.prince.remove(card);

//        if(card.type == CardType.ATTACK){
//            this.counter ++;
//            if(this.counter >= 4){
//                this.counter = 0;
//                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, 1, false), 1));
//            }
//        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(this.counter2 == 3){
            this.counter2 = 99;
            if(this.prince.size() > 0) {
                AbstractCard card = this.prince.get(AbstractDungeon.cardRng.random(this.prince.size() - 1));
                this.prince.remove(card);
                if(AbstractDungeon.player.drawPile.contains(card)){
                    for(int i = 0; i < AbstractDungeon.player.drawPile.group.size(); i ++){
                        if(AbstractDungeon.player.drawPile.group.get(i) == card){
                            AbstractDungeon.player.drawPile.group.set(i, new PrinceOne());
                            break;
                        }
                    }
                }
                else if(AbstractDungeon.player.hand.contains(card)){
                    for(int i = 0; i < AbstractDungeon.player.hand.group.size(); i ++){
                        if(AbstractDungeon.player.hand.group.get(i) == card){
                            AbstractDungeon.player.hand.group.set(i, new PrinceOne());
                            break;
                        }
                    }
                }
                else if(AbstractDungeon.player.discardPile.contains(card)){
                    for(int i = 0; i < AbstractDungeon.player.discardPile.group.size(); i ++){
                        if(AbstractDungeon.player.discardPile.group.get(i) == card){
                            AbstractDungeon.player.discardPile.group.set(i, new PrinceOne());
                            break;
                        }
                    }
                }
                for(AbstractCard c : this.prince){
                    if(AbstractDungeon.player.drawPile.contains(c)){
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
                    }
                    else if(AbstractDungeon.player.hand.contains(c)){
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                    }
                    else if(AbstractDungeon.player.discardPile.contains(card)){
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile));
                    }
                }
                this.prince.clear();
            }
        }
        else if(this.counter2 < 3){
            this.counter2 ++;
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
        return new LittlePrince();
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
        if(CogitoBucket.level[this.AbnormalityID] > 3){
            this.name = LittlePrince.EXTENDED_DESCRIPTION[1];
        }
        else {
            this.name = LittlePrince.EXTENDED_DESCRIPTION[0];
        }
        this.initializeTitle();
        this.rawDescription = LittlePrince.EXTENDED_DESCRIPTION[this.infoStage + 1];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("LittlePrince");
        NAME = LittlePrince.cardStrings.NAME;
        DESCRIPTION = LittlePrince.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = LittlePrince.cardStrings.EXTENDED_DESCRIPTION;
    }
}
