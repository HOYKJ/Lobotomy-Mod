package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
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

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class SingingMachine extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "SingingMachine";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private ArrayList<AbstractCard> targetCard = new ArrayList<>();

    public SingingMachine() {
        super("SingingMachine", SingingMachine.NAME, SingingMachine.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 30, 3, 2);
        this.baseDamage = 22;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        if(p.currentHealth > p.maxHealth * 0.2F){
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, 4));
            AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage / 2, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        }
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void ExhaustCard(AbstractCard card, boolean hand) {
        super.ExhaustCard(card, hand);
        if((hand) && (!this.targetCard.contains(card))){
            AbstractCard tmp;
            if(AbstractDungeon.player.drawPile.size() > 0){
                tmp = AbstractDungeon.player.drawPile.getRandomCard(false);
            }
            else if(AbstractDungeon.player.hand.size() > 0){
                tmp = AbstractDungeon.player.hand.getRandomCard(false);
            }
            else if(AbstractDungeon.player.discardPile.size() > 0){
                tmp = AbstractDungeon.player.discardPile.getRandomCard(false);
            }
            else {
                return;
            }
            tmp.exhaust = true;
            tmp.rawDescription += NestCard.DESCRIPTION;
            this.targetCard.add(tmp);
        }
        else this.targetCard.remove(card);
    }

    @Override
    public void onUsedCard(AbstractCard card, boolean hand) {
        super.onUsedCard(card, hand);
        if(this.targetCard.contains(card)){
            card.exhaust = true;
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        for(AbstractCard card : this.targetCard){
            card.exhaust = true;
            AbstractCard tmp = null;
            if(AbstractDungeon.player.drawPile.contains(card)){
                if(AbstractDungeon.player.drawPile.group.size() > 1) {
                    tmp = AbstractDungeon.player.drawPile.getRandomCard(true);
                    while (tmp == this){
                        tmp = AbstractDungeon.player.drawPile.getRandomCard(true);
                    }
                }
                else if(AbstractDungeon.player.hand.group.size() > 0){
                    tmp = AbstractDungeon.player.hand.getRandomCard(true);
                }
                else if(AbstractDungeon.player.discardPile.group.size() > 0){
                    tmp = AbstractDungeon.player.discardPile.getRandomCard(true);
                }
            }
            else if(AbstractDungeon.player.hand.contains(card)){
                if(AbstractDungeon.player.hand.group.size() > 1) {
                    tmp = AbstractDungeon.player.hand.getRandomCard(true);
                    while (tmp == this){
                        tmp = AbstractDungeon.player.hand.getRandomCard(true);
                    }
                }
                else if(AbstractDungeon.player.drawPile.group.size() > 0){
                    tmp = AbstractDungeon.player.drawPile.getRandomCard(true);
                }
                else if(AbstractDungeon.player.discardPile.group.size() > 0){
                    tmp = AbstractDungeon.player.discardPile.getRandomCard(true);
                }
            }
            else if(AbstractDungeon.player.discardPile.contains(card)){
                if(AbstractDungeon.player.discardPile.group.size() > 1) {
                    tmp = AbstractDungeon.player.discardPile.getRandomCard(true);
                    while (tmp == this){
                        tmp = AbstractDungeon.player.discardPile.getRandomCard(true);
                    }
                }
                else if(AbstractDungeon.player.drawPile.group.size() > 0){
                    tmp = AbstractDungeon.player.drawPile.getRandomCard(true);
                }
                else if(AbstractDungeon.player.hand.group.size() > 0){
                    tmp = AbstractDungeon.player.hand.getRandomCard(true);
                }
            }

            if(tmp != null){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(tmp, AbstractDungeon.player.drawPile));
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(tmp, AbstractDungeon.player.hand));
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(tmp, AbstractDungeon.player.discardPile));
            }
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
        return new SingingMachine();
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
        this.name = SingingMachine.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = SingingMachine.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SingingMachine");
        NAME = SingingMachine.cardStrings.NAME;
        DESCRIPTION = SingingMachine.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = SingingMachine.cardStrings.EXTENDED_DESCRIPTION;
    }
}
