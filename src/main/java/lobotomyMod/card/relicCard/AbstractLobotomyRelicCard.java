package lobotomyMod.card.relicCard;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.toolAbnormality.AbstractLobotomyAbnRelic;
import lobotomyMod.vfx.action.LatterEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hoykj
 */
public abstract class AbstractLobotomyRelicCard extends AbstractLobotomyCard {
    public List<TooltipInfo> tips = new ArrayList<>();

    public AbstractLobotomyRelicCard(final String id, final String name, final String description, final CardRarity rarity, final int AbnormalityID) {
        super(id, name, description, rarity, null, AbnormalityID, 1, -2);
    }

    public AbstractLobotomyAbnRelic getRelic(){
        return null;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        return this.tips;
    }

    public void addTips() {
        this.tips.clear();
        AbstractRelic relic = this.getRelic();
        if (relic != null) {
            this.tips.add(new TooltipInfo(relic.name, relic.description));
        }
    }

    @Override
    public void initInfo() {
        super.initInfo();
        addTips();
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
