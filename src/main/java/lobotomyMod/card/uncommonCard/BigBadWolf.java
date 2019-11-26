package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.Sheep;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.action.LatterEffect;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class BigBadWolf extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "BigBadWolf";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public ArrayList<AbstractCard> sheep = new ArrayList<>();

    public BigBadWolf() {
        super("BigBadWolf", BigBadWolf.NAME, BigBadWolf.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 58, 4, 0);
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        if(this.sheep.size() > 0){
            AbstractCard card = this.sheep.get(this.sheep.size() - 1);
            this.sheep.remove(card);
            p.hand.addToHand(card);
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth / 2){
            return damage * 1.4F;
        }
        return super.atDamageGive(damage, type);
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(!hand){
            return;
        }
        if(AbstractDungeon.player.drawPile.size() > 1){
            AbstractCard card = AbstractDungeon.player.drawPile.getRandomCard(true);
            while (card == this){
                card = AbstractDungeon.player.drawPile.getRandomCard(true);
            }
            AbstractCard finalCard = card;
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.player.drawPile.removeCard(finalCard);
                //AbstractDungeon.player.discardPile.removeCard(finalCard);
                this.sheep.add(finalCard);
            }));
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        for(AbstractCard card : this.sheep){
            for(AbstractCard card1 : AbstractDungeon.player.masterDeck.group){
                if(card.uuid.equals(card1.uuid)){
                    AbstractDungeon.effectList.add(new LatterEffect(()->{
                        AbstractDungeon.player.masterDeck.removeCard(card1);
                    }));
                }
            }
        }
    }

    public void kill(){
        for(AbstractCard card : this.sheep){
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                this.sheep.remove(card);
                AbstractDungeon.player.hand.addToHand(card);
            }));
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Sheep(), 1));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new BigBadWolf();
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
        this.name = BigBadWolf.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = BigBadWolf.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BigBadWolf");
        NAME = BigBadWolf.cardStrings.NAME;
        DESCRIPTION = BigBadWolf.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = BigBadWolf.cardStrings.EXTENDED_DESCRIPTION;
    }
}
