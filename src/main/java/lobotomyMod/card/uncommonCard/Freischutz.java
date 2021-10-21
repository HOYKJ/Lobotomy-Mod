package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.FreischutzSnipEffect;

/**
 * @author hoykj
 */
public class Freischutz extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "Freischutz";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static int counter = 0, tmpC = 0;

    public Freischutz() {
        super("Freischutz", Freischutz.NAME, Freischutz.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 69, 3, -2, CardTarget.NONE);
        this.baseMagicNumber = counter;
        this.magicNumber = this.baseMagicNumber;
        initInfo();
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.baseMagicNumber = counter;
        this.magicNumber = this.baseMagicNumber;
        this.isMagicNumberModified = true;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = counter;
        this.magicNumber = this.baseMagicNumber;
        this.isMagicNumberModified = true;
    }

    @Override
    public void onVictory() {
        super.onVictory();
        tmpC = counter;
        //tmpC = 0;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if(AbstractDungeon.player == null){
            return;
        }
        if (((InputHelper.justClickedRight) || (InputActionSet.confirm.isJustPressed()) || (CInputActionSet.select.isJustPressed())) && (AbstractDungeon.player.hoveredCard != null) && (AbstractDungeon.player.hoveredCard == this)) {
            InputHelper.justClickedRight = false;
            if(AbstractDungeon.player.gold < 10){
                return;
            }
            for(AbstractGameEffect e : AbstractDungeon.topLevelEffects){
                if(e instanceof FreischutzSnipEffect){
                    return;
                }
            }
            counter ++;
            this.baseMagicNumber = counter;
            this.magicNumber = this.baseMagicNumber;
            AbstractDungeon.player.loseGold((int) Math.max(AbstractDungeon.player.gold / 10.0F, 10));
            if(counter >= 7){
                counter = 0;
                this.baseMagicNumber = counter;
                this.magicNumber = this.baseMagicNumber;
                AbstractDungeon.topLevelEffects.add(new FreischutzSnipEffect(true));
            }
            else {
                AbstractDungeon.topLevelEffects.add(new FreischutzSnipEffect(false));
            }
        }
    }

    public static void reset(){
        counter = tmpC;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new Freischutz();
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
        this.name = Freischutz.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = Freischutz.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Freischutz");
        NAME = Freischutz.cardStrings.NAME;
        DESCRIPTION = Freischutz.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Freischutz.cardStrings.EXTENDED_DESCRIPTION;
    }
}
