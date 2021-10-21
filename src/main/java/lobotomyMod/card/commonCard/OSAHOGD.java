package lobotomyMod.card.commonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.RemoveRandomDebuffAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class OSAHOGD extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "OSAHOGD";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public OSAHOGD() {
        super("OSAHOGD", OSAHOGD.NAME, OSAHOGD.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 3, 1, -2, CardTarget.NONE);
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AbstractDungeon.actionManager.addToBottom(new RemoveRandomDebuffAction(AbstractDungeon.player));
    }

    public AbstractCard makeCopy() {
        return new OSAHOGD();
    }

    public void upgrade() {
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
        this.name = OSAHOGD.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = OSAHOGD.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("OSAHOGD");
        NAME = OSAHOGD.cardStrings.NAME;
        DESCRIPTION = OSAHOGD.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = OSAHOGD.cardStrings.EXTENDED_DESCRIPTION;
    }
}
