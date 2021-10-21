package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.deriveCard.NestCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class NakedNest extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "NakedNest";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private int counter;
    public ArrayList<AbstractCard> targets = new ArrayList<>();

    public NakedNest() {
        super("NakedNest", NakedNest.NAME, NakedNest.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 74, 4, -2, CardTarget.NONE);
        this.counter = 0;
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, 1), 1));
        for(AbstractCard card : this.targets){
            for(int i = 0; i < AbstractDungeon.player.drawPile.size(); i ++){
                if(AbstractDungeon.player.drawPile.group.get(i) == card){
                    AbstractDungeon.player.drawPile.group.set(i, new NestCard(card));
                    break;
                }
            }
            for(int i = 0; i < AbstractDungeon.player.hand.size(); i ++){
                if(AbstractDungeon.player.hand.group.get(i) == card){
                    AbstractDungeon.player.hand.group.set(i, new NestCard(card));
                    break;
                }
            }
            for(int i = 0; i < AbstractDungeon.player.discardPile.size(); i ++){
                if(AbstractDungeon.player.discardPile.group.get(i) == card){
                    AbstractDungeon.player.discardPile.group.set(i, new NestCard(card));
                    break;
                }
            }
        }
        if(!hand){
            return;
        }
        if(AbstractDungeon.player.hand.size() > 1){
            AbstractCard card = AbstractDungeon.player.hand.getRandomCard(true);
            while (card == this){
                card = AbstractDungeon.player.hand.getRandomCard(true);
            }

            this.targets.add(card);
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public AbstractCard makeCopy() {
        return new NakedNest();
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
        this.name = NakedNest.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = NakedNest.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("NakedNest");
        NAME = NakedNest.cardStrings.NAME;
        DESCRIPTION = NakedNest.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = NakedNest.cardStrings.EXTENDED_DESCRIPTION;
    }
}
