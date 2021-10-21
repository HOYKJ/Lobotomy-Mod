package lobotomyMod.card.uncommonCard;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;

/**
 * @author hoykj
 */
public class Funeral extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "Funeral";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public Funeral() {
        super("Funeral", Funeral.NAME, Funeral.DESCRIPTION, CardRarity.UNCOMMON, CardTarget.ENEMY, 68, 3, -2, CardTarget.NONE);
        this.baseDamage = 1;
        this.isMultiDamage = true;
        this.baseBlock = 1;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
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
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        this.applyPowers();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void ExhaustCard(AbstractCard card, boolean hand) {
        super.ExhaustCard(card, hand);
        if(!hand){
            return;
        }
        for(AbstractCard card1 : AbstractDungeon.player.masterDeck.group){
            if(card.uuid.equals(card1.uuid)){
                AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                    AbstractDungeon.player.masterDeck.removeCard(card1);
                    switch (card.rarity){
                        case COMMON:
                            AbstractDungeon.commonCardPool.removeCard(card.cardID);
                            break;
                        case UNCOMMON:
                            AbstractDungeon.uncommonCardPool.removeCard(card.cardID);
                            break;
                        case RARE:
                            AbstractDungeon.rareCardPool.removeCard(card.cardID);
                            break;
                        case CURSE:
                            AbstractDungeon.curseCardPool.removeCard(card.cardID);
                            break;
                    }
                }));
            }
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public AbstractCard makeCopy() {
        return new Funeral();
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
        this.name = Funeral.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = Funeral.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Funeral");
        NAME = Funeral.cardStrings.NAME;
        DESCRIPTION = Funeral.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Funeral.cardStrings.EXTENDED_DESCRIPTION;
    }
}
