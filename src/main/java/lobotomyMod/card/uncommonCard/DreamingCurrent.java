package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.action.LatterEffect;

/**
 * @author hoykj
 */
public class DreamingCurrent extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "DreamingCurrent";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public DreamingCurrent() {
        super("DreamingCurrent", DreamingCurrent.NAME, DreamingCurrent.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 71, 3, 1);
        this.baseMagicNumber = 5;
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
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if(AbstractDungeon.player.drawPile.contains(this)){
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                AbstractDungeon.player.drawPile.removeCard(this);
                AbstractDungeon.player.hand.addToHand(this);
            }));
        }
        else if(AbstractDungeon.player.discardPile.contains(this)){
            AbstractDungeon.effectList.add(new LatterEffect(()->{
                AbstractDungeon.player.discardPile.removeCard(this);
                AbstractDungeon.player.hand.addToHand(this);
            }));
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(hand){
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 15, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
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
        return new DreamingCurrent();
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
        this.name = DreamingCurrent.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = DreamingCurrent.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("DreamingCurrent");
        NAME = DreamingCurrent.cardStrings.NAME;
        DESCRIPTION = DreamingCurrent.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = DreamingCurrent.cardStrings.EXTENDED_DESCRIPTION;
    }
}
