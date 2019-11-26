package lobotomyMod.card.commonCard;

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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.uncommonCard.HappyTeddy;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class PunishingBird extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "PunishingBird";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public PunishingBird() {
        super("PunishingBird", PunishingBird.NAME, PunishingBird.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 56, 3, 0);
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        if(this.realCost > 0){
            this.changeCost(this.realCost - 1);
        }
        AbstractDungeon.actionManager.addToTop(new DamageAction(p, new DamageInfo(p, 100, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        for(AbstractPower power : AbstractDungeon.player.powers){
            if(power.type == AbstractPower.PowerType.DEBUFF){
                AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 2));
                return;
            }
        }
        AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 1, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new PunishingBird();
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
        if(CogitoBucket.level[this.AbnormalityID] > 1){
            this.name = PunishingBird.EXTENDED_DESCRIPTION[1];
        }
        else {
            this.name = PunishingBird.EXTENDED_DESCRIPTION[0];
        }
        this.initializeTitle();
        this.rawDescription = PunishingBird.EXTENDED_DESCRIPTION[this.infoStage + 1];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("PunishingBird");
        NAME = PunishingBird.cardStrings.NAME;
        DESCRIPTION = PunishingBird.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = PunishingBird.cardStrings.EXTENDED_DESCRIPTION;
    }
}
