package lobotomyMod.card.relicCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.toolAbnormality.AbstractLobotomyAbnRelic;
import lobotomyMod.relic.toolAbnormality.AspirationHeart;
import lobotomyMod.relic.toolAbnormality.HellTrain;

/**
 * @author hoykj
 */
public class HellTrainRelic extends AbstractLobotomyRelicCard implements CustomSavable<int[]> {
    public static final String ID = "HellTrainRelic";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public HellTrainRelic() {
        super("HellTrainRelic", HellTrainRelic.NAME, HellTrainRelic.DESCRIPTION, CardRarity.RARE, 86);
        initInfo();
    }

    @Override
    public AbstractLobotomyAbnRelic getRelic() {
        return new HellTrain();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public AbstractCard makeCopy() {
        return new HellTrainRelic();
    }


    public void initInfo(){
        if(CogitoBucket.level[this.AbnormalityID] < 1) {
            return;
        }
        this.i[0] = CogitoBucket.level[this.AbnormalityID];
        loadImg();
        this.name = HellTrainRelic.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("HellTrainRelic");
        NAME = HellTrainRelic.cardStrings.NAME;
        DESCRIPTION = HellTrainRelic.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = HellTrainRelic.cardStrings.EXTENDED_DESCRIPTION;
    }
}
