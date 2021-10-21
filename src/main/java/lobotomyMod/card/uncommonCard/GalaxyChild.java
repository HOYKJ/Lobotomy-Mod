package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.action.common.MakeCardInHandAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.Duel;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.action.LatterEffect;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class GalaxyChild extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "GalaxyChild";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private boolean used;
    private ArrayList<AbstractCard> friends = new ArrayList<>();

    public GalaxyChild() {
        super("GalaxyChild", GalaxyChild.NAME, GalaxyChild.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 55, 3, 3, CardTarget.NONE);
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        this.used = true;
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            this.changeCost(this.realCost - 1);
        }));

        for (AbstractCard c : p.hand.group)
        {
            if(c == this){
                continue;
            }
            if (c.costForTurn > 0)
            {
                c.costForTurn -= 1;
                c.isCostModifiedForTurn = true;
                this.friends.add(c);
            }
            if (c.cost > 0)
            {
                c.cost -= 1;
                c.isCostModified = true;
            }
        }
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.used = false;
        this.friends.clear();
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(this.used){
            this.used = false;
        }
        else {
            if(this.realCost < 5) {
                this.changeCost(this.realCost + 1);
            }
            else {
                for(AbstractCard card : this.friends){
                    if(AbstractDungeon.player.drawPile.contains(card)) {
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
                    }
                    if(AbstractDungeon.player.hand.contains(card)) {
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                    }
                    if(AbstractDungeon.player.discardPile.contains(card)) {
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
                    }
                    AbstractDungeon.effectList.add(new LatterEffect(()->{
                        this.friends.remove(card);
                    }));
                }
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
        return new GalaxyChild();
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
        this.name = GalaxyChild.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = GalaxyChild.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("GalaxyChild");
        NAME = GalaxyChild.cardStrings.NAME;
        DESCRIPTION = GalaxyChild.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = GalaxyChild.cardStrings.EXTENDED_DESCRIPTION;
    }
}
