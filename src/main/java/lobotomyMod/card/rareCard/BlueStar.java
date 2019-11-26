package lobotomyMod.card.rareCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.action.common.ExhaustAnotherCardAction;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.action.common.RandomAttackAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.*;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.animation.CurtainEffect;

/**
 * @author hoykj
 */
public class BlueStar extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "BlueStar";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public BlueStar() {
        super("BlueStar", BlueStar.NAME, BlueStar.DESCRIPTION, CardRarity.RARE, CardTarget.ENEMY, 93, 4, 0);
        this.baseDamage = 6;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RandomAttackAction(m, new DamageInfo(p, this.baseDamage), EnergyPanel.totalCount, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        //AbstractDungeon.player.gainEnergy(1);
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(EnergyPanel.totalCount <= 0){
            AbstractDungeon.actionManager.addToBottom(new ExhaustAnotherCardAction(AbstractDungeon.player.drawPile, AbstractDungeon.player.hand, AbstractDungeon.player.discardPile, this));
            AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                AbstractDungeon.actionManager.addToBottom(new ExhaustAnotherCardAction(AbstractDungeon.player.drawPile, AbstractDungeon.player.hand, AbstractDungeon.player.discardPile, this));
            }));
        }
        if(AbstractDungeon.player.hand.isEmpty()){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 12)));
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
        return new BlueStar();
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
        this.name = BlueStar.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = BlueStar.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BlueStar");
        NAME = BlueStar.cardStrings.NAME;
        DESCRIPTION = BlueStar.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = BlueStar.cardStrings.EXTENDED_DESCRIPTION;
    }
}
