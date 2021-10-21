package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
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
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.power.BleedPower;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class Mercenary extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "Mercenary";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private int counter;

    public Mercenary() {
        super("Mercenary", Mercenary.NAME, Mercenary.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 57, 3, 1, CardTarget.NONE);
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        for(AbstractCard card : AbstractDungeon.player.drawPile.group){
            if(card instanceof BigBadWolf){
                ((BigBadWolf) card).kill();
            }
        }
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof BigBadWolf){
                ((BigBadWolf) card).kill();
            }
        }
        for(AbstractCard card : AbstractDungeon.player.discardPile.group){
            if(card instanceof BigBadWolf){
                ((BigBadWolf) card).kill();
            }
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
    public void atBattleStart() {
        super.atBattleStart();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        this.counter = 0;
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            if(AbstractDungeon.player.hand.findCardById(BigBadWolf.ID) != null){
                AbstractCard card = AbstractDungeon.player.hand.findCardById(BigBadWolf.ID);
                ((BigBadWolf)card).kill();
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            }
        }));
    }

    @Override
    public void ExhaustCard(AbstractCard card, boolean hand) {
        super.ExhaustCard(card, hand);
        this.counter ++;
        if(this.counter >= 3){
            this.counter -= 3;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BleedPower(AbstractDungeon.player, 3), 3));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        for(AbstractCard card : AbstractDungeon.player.drawPile.group){
            if(card instanceof BigBadWolf){
                if(((BigBadWolf) card).sheep.size() > 0){
                    return super.canUse(p, m);
                }
            }
        }
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card instanceof BigBadWolf){
                if(((BigBadWolf) card).sheep.size() > 0){
                    return super.canUse(p, m);
                }
            }
        }
        for(AbstractCard card : AbstractDungeon.player.discardPile.group){
            if(card instanceof BigBadWolf){
                if(((BigBadWolf) card).sheep.size() > 0){
                    return super.canUse(p, m);
                }
            }
        }
        return false;
    }

    public AbstractCard makeCopy() {
        return new Mercenary();
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
        this.name = Mercenary.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = Mercenary.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Mercenary");
        NAME = Mercenary.cardStrings.NAME;
        DESCRIPTION = Mercenary.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Mercenary.cardStrings.EXTENDED_DESCRIPTION;
    }
}
