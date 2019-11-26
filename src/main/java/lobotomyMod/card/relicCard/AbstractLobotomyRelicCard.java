package lobotomyMod.card.relicCard;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.toolAbnormality.AbstractLobotomyAbnRelic;
import lobotomyMod.vfx.action.LatterEffect;

/**
 * @author hoykj
 */
public abstract class AbstractLobotomyRelicCard extends AbstractLobotomyCard {

    public AbstractLobotomyRelicCard(final String id, final String name, final String description, final CardRarity rarity, final int AbnormalityID) {
        super(id, name, description, rarity, null, AbnormalityID, 1, -2);
    }

    public AbstractLobotomyAbnRelic getRelic(){
        return null;
    }

    @Override
    public void obtain() {
        super.obtain();
        CogitoBucket.level[this.AbnormalityID] = 1;
        AbstractDungeon.effectList.add(new LatterEffect(()->{
            AbstractDungeon.player.masterDeck.removeCard(this.cardID);
        }));
    }

    @Override
    public boolean canUnlockInfo() {
        return false;
    }
}
