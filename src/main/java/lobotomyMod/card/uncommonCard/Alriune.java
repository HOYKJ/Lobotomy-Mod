package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
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

/**
 * @author hoykj
 */
public class Alriune extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "Alriune";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private boolean active;
    private int turns;

    public Alriune() {
        super("Alriune", Alriune.NAME, Alriune.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 53, 3, -2);
        this.baseDamage = 20;
        this.isMultiDamage = true;
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
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(this));
        if(this.active){
            this.active = false;
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        }
        else {
            this.active = true;
            this.turns = 0;
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(!this.active){
            return;
        }
        if(this.turns < 3){
            this.turns ++;
        }
        else {
            this.active = false;
            if(AbstractDungeon.player.drawPile.contains(this)) {
                for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
                }
            }
            else if(AbstractDungeon.player.discardPile.contains(this)) {
                for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
                }
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
        return new Alriune();
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
        this.name = Alriune.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = Alriune.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Alriune");
        NAME = Alriune.cardStrings.NAME;
        DESCRIPTION = Alriune.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Alriune.cardStrings.EXTENDED_DESCRIPTION;
    }
}
