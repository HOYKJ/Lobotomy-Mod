package lobotomyMod.card.relicCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.toolAbnormality.AbstractLobotomyAbnRelic;
import lobotomyMod.relic.toolAbnormality.AspirationHeart;
import lobotomyMod.relic.toolAbnormality.Theresia;

/**
 * @author hoykj
 */
public class AspirationHeartRelic extends AbstractLobotomyRelicCard implements CustomSavable<int[]> {
    public static final String ID = "AspirationHeartRelic";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public AspirationHeartRelic() {
        super("AspirationHeartRelic", AspirationHeartRelic.NAME, AspirationHeartRelic.DESCRIPTION, CardRarity.COMMON, 77);
        initInfo();
    }

    @Override
    public AbstractLobotomyAbnRelic getRelic() {
        return new AspirationHeart();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public AbstractCard makeCopy() {
        return new AspirationHeartRelic();
    }


    public void initInfo(){
        if(CogitoBucket.level[this.AbnormalityID] < 1) {
            return;
        }
        this.i[0] = CogitoBucket.level[this.AbnormalityID];
        loadImg();
        this.name = AspirationHeartRelic.EXTENDED_DESCRIPTION[0];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("AspirationHeartRelic");
        NAME = AspirationHeartRelic.cardStrings.NAME;
        DESCRIPTION = AspirationHeartRelic.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = AspirationHeartRelic.cardStrings.EXTENDED_DESCRIPTION;
    }
}
