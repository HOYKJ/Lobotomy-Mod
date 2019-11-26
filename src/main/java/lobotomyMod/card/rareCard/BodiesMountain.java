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
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.*;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.animation.CurtainEffect;

/**
 * @author hoykj
 */
public class BodiesMountain extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "BodiesMountain";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private int counter;

    public BodiesMountain() {
        super("BodiesMountain", BodiesMountain.NAME, BodiesMountain.DESCRIPTION, CardRarity.RARE, CardTarget.ENEMY, 75, 4, 1);
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        if(this.realCost > 1){
            this.changeCost(this.realCost - 1);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        switch (this.realCost){
            case 2:
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 4, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                break;
            case 3:
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 10, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                break;
            case 4:
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 50, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
        }
    }

    @Override
    public void ExhaustCard(AbstractCard card, boolean hand) {
        super.ExhaustCard(card, hand);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 1));

        this.counter ++;
        if(this.counter >= 3){
            this.counter -= 3;
            if(this.realCost < 4){
                this.changeCost(this.realCost + 1);
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
        return new BodiesMountain();
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
        this.name = BodiesMountain.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = BodiesMountain.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BodiesMountain");
        NAME = BodiesMountain.cardStrings.NAME;
        DESCRIPTION = BodiesMountain.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = BodiesMountain.cardStrings.EXTENDED_DESCRIPTION;
    }
}
