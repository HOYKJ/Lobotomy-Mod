package lobotomyMod.card.rareCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.*;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.animation.CurtainEffect;

/**
 * @author hoykj
 */
public class SilentOrchestra extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "SilentOrchestra";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public SilentOrchestra() {
        super("SilentOrchestra", SilentOrchestra.NAME, SilentOrchestra.DESCRIPTION, CardRarity.RARE, CardTarget.ENEMY, 31, 4, -2, CardTarget.NONE);
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
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new SilentSonata(), 1, true, false));
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(AbstractDungeon.player));
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if ((c.type == CardType.STATUS) || (c.type == CardType.CURSE)) {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if ((c.type == CardType.STATUS) || (c.type == CardType.CURSE)) {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile));
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if ((c.type == CardType.STATUS) || (c.type == CardType.CURSE)) {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
            }
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        for(AbstractGameEffect effect : AbstractDungeon.topLevelEffects){
            if(effect instanceof CurtainEffect){
                ((CurtainEffect) effect).end();
            }
        }
        for(AbstractCard card : AbstractDungeon.player.drawPile.group){
            if((card instanceof SilentSonata) || (card instanceof SilentAdagio) || (card instanceof SilentScherzo)
                    || (card instanceof SilentRondo) || (card instanceof SilentFinal)){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
            }
        }
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if((card instanceof SilentSonata) || (card instanceof SilentAdagio) || (card instanceof SilentScherzo)
                    || (card instanceof SilentRondo) || (card instanceof SilentFinal)){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            }
        }
        for(AbstractCard card : AbstractDungeon.player.discardPile.group){
            if((card instanceof SilentSonata) || (card instanceof SilentAdagio) || (card instanceof SilentScherzo)
                    || (card instanceof SilentRondo) || (card instanceof SilentFinal)){
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
            }
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        for(AbstractGameEffect effect : AbstractDungeon.topLevelEffects){
            if(effect instanceof CurtainEffect){
                ((CurtainEffect) effect).end();
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
        return new SilentOrchestra();
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
        this.name = SilentOrchestra.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = SilentOrchestra.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("SilentOrchestra");
        NAME = SilentOrchestra.cardStrings.NAME;
        DESCRIPTION = SilentOrchestra.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = SilentOrchestra.cardStrings.EXTENDED_DESCRIPTION;
    }
}
