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
import lobotomyMod.relic.toolAbnormality.BehaviorAdjustment;

/**
 * @author hoykj
 */
public class BehaviorAdjustmentRelic extends AbstractLobotomyRelicCard implements CustomSavable<int[]> {
    public static final String ID = "BehaviorAdjustmentRelic";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public BehaviorAdjustmentRelic() {
        super("BehaviorAdjustmentRelic", BehaviorAdjustmentRelic.NAME, BehaviorAdjustmentRelic.DESCRIPTION, CardRarity.COMMON, 96);
        initInfo();
    }

    @Override
    public AbstractLobotomyAbnRelic getRelic() {
        return new BehaviorAdjustment();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public AbstractCard makeCopy() {
        return new BehaviorAdjustmentRelic();
    }


    public void initInfo(){
        if(CogitoBucket.level[this.AbnormalityID] < 1) {
            return;
        }
        this.i[0] = CogitoBucket.level[this.AbnormalityID];
        loadImg();
        this.name = BehaviorAdjustmentRelic.EXTENDED_DESCRIPTION[0];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BehaviorAdjustmentRelic");
        NAME = BehaviorAdjustmentRelic.cardStrings.NAME;
        DESCRIPTION = BehaviorAdjustmentRelic.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = BehaviorAdjustmentRelic.cardStrings.EXTENDED_DESCRIPTION;
    }
}
