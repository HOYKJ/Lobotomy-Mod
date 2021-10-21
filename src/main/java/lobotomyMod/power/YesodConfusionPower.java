package lobotomyMod.power;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.monster.sephirah.Yesod;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hoykj
 */
public class YesodConfusionPower extends AbstractPower
{
    public static final String POWER_ID = "YesodConfusionPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("YesodConfusionPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private Yesod yesod;

    public YesodConfusionPower(AbstractCreature owner, Yesod yesod) {
        this.name = NAME;
        this.ID = "YesodConfusionPower";
        this.owner = owner;
        this.type = AbstractPower.PowerType.DEBUFF;
        this.yesod = yesod;
        loadRegion("confusion");
        updateDescription();
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_CONFUSION", 0.05F);
    }

    public void onCardDraw(AbstractCard card) {
        switch (this.yesod.stage){
            case 3:
                card.rawDescription = card.rawDescription.replaceAll("[^ +,.，。]", "?");
                card.initializeDescription();
            case 2:
                card.name = card.name.replaceAll("[^ +]", "?");
                try {
                    Method initializeTitle = SuperclassFinder.getSuperClassMethod(card.getClass(), "initializeTitle");
                    initializeTitle.setAccessible(true);
                    initializeTitle.invoke(card);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            case 1:
                try {
                    Field portraitImg = SuperclassFinder.getSuperclassField(card.getClass(), "portrait");
                    portraitImg.setAccessible(true);
                    Texture tmp;
                    switch (card.type){
                        case ATTACK:
                            tmp = ImageMaster.CARD_LOCKED_ATTACK;
                            break;
                        case POWER:
                            tmp = ImageMaster.CARD_LOCKED_POWER;
                            break;
                        default:
                            tmp = ImageMaster.CARD_LOCKED_SKILL;
                    }
                    portraitImg.set(card, new TextureAtlas.AtlasRegion(tmp, 0, 0, tmp.getWidth(), tmp.getHeight()));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
