package lobotomyMod.reward;

import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.uncommonCard.Yin;
import lobotomyMod.character.LobotomyCardPool;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.patch.RewardItemEnum;
import lobotomyMod.relic.ApostleMask;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.relic.Yang;
import lobotomyMod.ui.LobotomyFtue;
import lobotomyMod.vfx.action.ChooseCardEffect;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRng;

/**
 * @author hoykj
 */
public class CogitoReward extends CustomReward {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RewardItem_Lob");
    public static final String[] TEXT = uiStrings.TEXT;
    private boolean tool;

    public CogitoReward()
    {
        super(ImageMaster.loadImage("images/ui/run_mods/uncertain_future.png"), TEXT[0], RewardItemEnum.SPECIAL);
        this.tool = false;
    }

    public CogitoReward(boolean tool)
    {
        super(ImageMaster.loadImage("images/ui/run_mods/uncertain_future.png"), TEXT[0], RewardItemEnum.SPECIAL);
        this.tool = tool;
    }

    public boolean claimReward()
    {
        if(LobotomyMod.activeTutorials[1]) {
            AbstractDungeon.ftue = new LobotomyFtue(1, this);
            LobotomyMod.activeTutorials[1] = false;
            return true;
        }

        if((AbstractDungeon.player.masterDeck.findCardById(Yin.ID) != null) && (!AbstractDungeon.player.hasRelic(Yang.ID))){
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,  new Yang());
            return true;
        }

        if(this.tool){
            getAbnormalityTool();
        }
        else {
            getAbnormality();
        }

        return true;
    }

    public void getAbnormality(){
        LobotomyCardPool.reloadCardPool();
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            AbstractCard.CardRarity rarity = rollRarity();
            AbstractCard card = null;
            boolean containsDupe = true;
            while (containsDupe)
            {
                containsDupe = false;
                switch (rarity)
                {
                    case RARE:
                        card = LobotomyCardPool.abnormalityPoolRare.getRandomCard(true);
                        break;
                    case UNCOMMON:
                        card = LobotomyCardPool.abnormalityPoolUncommon.getRandomCard(true);
                        break;
                    case COMMON:
                        card = LobotomyCardPool.abnormalityPoolCommon.getRandomCard(true);
                        break;
                }
                for (AbstractCard c : retVal) {
                    if (c.cardID.equals(card != null ? card.cardID : null))
                    {
                        containsDupe = true;
                        LobotomyMod.logger.info("test: " + rarity.toString());
                        break;
                    }
                }
            }
            if (card != null) {
                retVal.add(card);
            }
        }
        ArrayList<AbstractCard> retVal2 = new ArrayList<>();
        for (AbstractCard c : retVal) {
            retVal2.add(c.makeCopy());
        }

        AbstractDungeon.cardRewardScreen.open(retVal2, this, TEXT[2]);
        AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
    }

    public void getAbnormalityTool(){
        LobotomyCardPool.reloadCardPool();
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        if(LobotomyCardPool.abnormalityPoolRelic.size() < 3){
            return;
        }
        for (int i = 0; i < 3; i++)
        {
            AbstractCard card = null;
            boolean containsDupe = true;
            while (containsDupe)
            {
                containsDupe = false;
                card = LobotomyCardPool.abnormalityPoolRelic.getRandomCard(true);
                for (AbstractCard c : retVal) {
                    if (c.cardID.equals(card != null ? card.cardID : null))
                    {
                        containsDupe = true;
                        break;
                    }
                }
            }
            if (card != null) {
                retVal.add(card);
            }
        }
        CardGroup retVal2 = new CardGroup(UNSPECIFIED);
        for (AbstractCard c : retVal) {
            retVal2.addToRandomSpot(c.makeCopy());
        }

        AbstractDungeon.effectList.add(new ChooseCardEffect(retVal2, TEXT[2]));
        AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
    }

    private AbstractCard.CardRarity rollRarity(){
        int roll = cardRng.random(100);
        float rare = AbstractDungeon.floorNum;
        for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
            if((card.rarity == AbstractCard.CardRarity.RARE) && (card instanceof AbstractLobotomyCard)){
                rare -= 5;
            }
        }
        if(rare < 0){
            rare = 0;
        }
        float uncommon = AbstractDungeon.floorNum + 25;
        rare /= 100;
        rare = rare * rare * 100;
        uncommon += rare;
        LobotomyMod.logger.info("roll: " + roll + "   " + rare + "   " + uncommon);

        if(roll < rare){
            return AbstractCard.CardRarity.RARE;
        }
        else if(roll < uncommon){
            return AbstractCard.CardRarity.UNCOMMON;
        }
        else {
            return AbstractCard.CardRarity.COMMON;
        }
    }
}
