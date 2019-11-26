package lobotomyMod.card.commonCard;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class ShyLook extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "ShyLook";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private float timer;
    public int face;

    public ShyLook() {
        super("ShyLook", ShyLook.NAME, ShyLook.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 92, 3, 0);
        this.baseBlock = 0;
        this.timer = 0;
        this.face = 0;
        initInfo();
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.face = 3;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        switch (this.face){
            case 1:
                this.block += 12;
                break;
            case 2:
                this.block += 6;
                break;
            case 3:
                this.block += 1;
                break;
            case 4:
                this.block -= 6;
                break;
            case 5:
                this.block -= 12;
                break;
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        switch (this.face){
            case 1:
                this.block += 12;
                break;
            case 2:
                this.block += 6;
                break;
            case 3:
                this.block += 1;
                break;
            case 4:
                this.block -= 6;
                break;
            case 5:
                this.block -= 12;
                break;
        }
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        if(this.block > 0){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, -this.block)));
        }
    }

    @Override
    public void update() {
        super.update();
        this.timer -= Gdx.graphics.getDeltaTime();

        if(this.timer <= 0){
            this.timer = MathUtils.random(3, 6);
            if(MathUtils.randomBoolean()){
                this.face --;
            }
            else {
                this.face ++;
            }
            if(this.face < 1){
                this.face = 1;
            }
            else if(this.face > 5){
                this.face = 5;
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
        return new ShyLook();
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
        this.name = ShyLook.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = ShyLook.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ShyLook");
        NAME = ShyLook.cardStrings.NAME;
        DESCRIPTION = ShyLook.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = ShyLook.cardStrings.EXTENDED_DESCRIPTION;
    }
}
