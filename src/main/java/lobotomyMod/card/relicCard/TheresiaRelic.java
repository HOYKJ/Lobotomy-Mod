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
import lobotomyMod.relic.toolAbnormality.Theresia;

/**
 * @author hoykj
 */
public class TheresiaRelic extends AbstractLobotomyRelicCard implements CustomSavable<int[]> {
    public static final String ID = "TheresiaRelic";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public TheresiaRelic() {
        super("TheresiaRelic", TheresiaRelic.NAME, TheresiaRelic.DESCRIPTION, CardRarity.COMMON, 9);
        initInfo();
    }

    @Override
    public void obtain() {
        super.obtain();
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.current_x, this.current_y,  new Theresia());
    }

    @Override
    public AbstractLobotomyAbnRelic getRelic() {
        return new Theresia();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public AbstractCard makeCopy() {
        return new TheresiaRelic();
    }


    public void initInfo(){
        if(CogitoBucket.level[this.AbnormalityID] < 1) {
            return;
        }
        this.i[0] = CogitoBucket.level[this.AbnormalityID];
        loadImg();
        this.name = TheresiaRelic.EXTENDED_DESCRIPTION[0];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("TheresiaRelic");
        NAME = TheresiaRelic.cardStrings.NAME;
        DESCRIPTION = TheresiaRelic.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = TheresiaRelic.cardStrings.EXTENDED_DESCRIPTION;
    }
}
