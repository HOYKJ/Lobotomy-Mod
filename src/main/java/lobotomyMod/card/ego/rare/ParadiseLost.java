package lobotomyMod.card.ego.rare;

import basemod.abstracts.CustomSavable;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import lobotomyMod.action.common.RemoveRandomDebuffAction;
import lobotomyMod.action.unique.ParadiseLostBuffAction;
import lobotomyMod.card.ego.AbstractEgoCard;

/**
 * @author hoykj
 */
public class ParadiseLost extends AbstractEgoCard implements CustomSavable<int[]> {
    public static final String ID = "ParadiseLost";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public int buffs[];
    public int spID;

    public ParadiseLost(){
        this(0);
    }

    public ParadiseLost(int upgrade) {
        super("ParadiseLost", ParadiseLost.NAME, 3, ParadiseLost.DESCRIPTION, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 16;
        this.baseBlock = 10;
        for(int i = 0; i < upgrade; i ++){
            this.upgrade();
        }
        this.buffs = new int[17];
        this.addTips();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                (this.buffs[1] > 0? DamageInfo.DamageType.HP_LOSS: this.damageTypeForTurn)), AbstractGameAction.AttackEffect.FIRE));
        if(this.buffs[0] > 0){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
        if(this.buffs[2] > 0){
            AbstractDungeon.actionManager.addToBottom(new RemoveRandomDebuffAction(p));
        }
        if(this.buffs[3] > 0){
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if ((c.type == CardType.STATUS) || (c.type == CardType.CURSE)) {
                    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if ((c.type == CardType.STATUS) || (c.type == CardType.CURSE)) {
                    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile));
                }
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if ((c.type == CardType.STATUS) || (c.type == CardType.CURSE)) {
                    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
                }
            }
        }
        if(this.buffs[4] > 0){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 2));
        }
        if(this.buffs[5] > 0){
            AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(true));
        }
        if(this.buffs[6] > 0){
            boolean flag = false;
            for(AbstractCard card : AbstractDungeon.player.hand.group){
                if(card.cost > 0){
                    flag = true;
                    break;
                }
            }
            if(flag) {
                AbstractCard card = AbstractDungeon.player.hand.getRandomCard(true);
                while (card.cost < 1){
                    card = AbstractDungeon.player.hand.getRandomCard(true);
                }
                card.modifyCostForCombat(-1);
            }
        }
        if(this.buffs[8] > 0){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, 2, false), 2));
        }
        if(this.buffs[10] > 0){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 2, false), 2));
        }
        if(this.buffs[11] > 0){
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, 4));
        }
        if(this.buffs[12] > 0){
            AbstractDungeon.getCurrRoom().addGoldToRewards(15);
        }
        if(this.buffs[13] > 0){
            AbstractDungeon.getCurrRoom().addPotionToRewards(AbstractDungeon.returnRandomPotion());
        }
        if(this.buffs[14] > 0){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, 1), 1));
        }
        if(this.buffs[15] > 0){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -2), -2));
        }

        AbstractDungeon.actionManager.addToBottom(new ParadiseLostBuffAction(this));
    }

    @Override
    public void update() {
        super.update();
        if(this.buffs[9] > 0 && !this.retain){
            this.retain = true;
        }
        if(this.buffs[7] > 0 && this.cost > 2){
            this.modifyCostForCombat(2 - this.cost);
        }
    }

    @Override
    public void upgrade()
    {
        this.upgradeDamage(2);
        this.upgradeBlock(2);
        this.timesUpgraded ++;
        this.upgraded = true;
        this.name = (NAME + "+" + this.timesUpgraded);
        this.initializeTitle();
    }

    public boolean canUpgrade()
    {
        return true;
    }

    public AbstractCard makeCopy() {
        ParadiseLost tmp = new ParadiseLost(this.timesUpgraded);
        System.arraycopy(this.buffs, 0, tmp.buffs, 0, 17);
        tmp.refresh();
        return tmp;
    }

    @Override
    public void addTips() {
        if(this.buffs == null){
            return;
        }
        this.tips.clear();
        this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[1], EXTENDED_DESCRIPTION[2]));
        for(int i = 0; i < 17; i ++){
            if(this.buffs[i] > 0) {
                this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[2 * i + 3], EXTENDED_DESCRIPTION[2 * i + 4]));
            }
        }
        this.tips.add(new TooltipInfo(NAME, EXTENDED_DESCRIPTION[0]));
    }

    public void refresh(){
        if(this.buffs[0] > 0){
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
        if(this.buffs[7] > 0 && this.cost > 2){
            this.modifyCostForCombat(2 - this.cost);
        }
        if(this.buffs[16] > 0){
            this.isInnate = true;
        }
        this.addTips();
    }

    public void onLoad(int[] arg0)
    {
        if (arg0 == null) {
            return;
        }
        System.arraycopy(arg0, 0, this.buffs, 0, 17);
        refresh();
    }

    public int[] onSave()
    {
        return this.buffs;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ParadiseLost");
        NAME = ParadiseLost.cardStrings.NAME;
        DESCRIPTION = ParadiseLost.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ParadiseLost.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = ParadiseLost.cardStrings.EXTENDED_DESCRIPTION;
    }
}
