package lobotomyMod.card.angelaCard.department;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.angelaCard.code.ControlCode;
import lobotomyMod.card.angelaCard.code.RecordCode;
import lobotomyMod.card.angelaCard.code.other.SummonCall;
import lobotomyMod.card.angelaCard.code.other.TT2Protocol;
import lobotomyMod.character.Angela;

/**
 * @author hoykj
 */
public class Hokma extends AbstractDepartmentCard {
    public static final String ID = "Hokma";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static int departmentCode[] = new int[]{7, 16};

    public Hokma(boolean e) {
        super("Hokma", Hokma.NAME, Hokma.DESCRIPTION);
        departmentCode[0] = 7;
        departmentCode[1] = 16;
        if(!(AbstractDungeon.player instanceof Angela)){
            return;
        }
        this.level = Angela.departments[departmentCode[0]];
        this.agents = Angela.departments[departmentCode[1]];
        if(this.level > 0 && e){
            this.rawDescription = EXTENDED_DESCRIPTION[this.level * 2 - 1];
            initializeDescription();
        }
        this.e = e;
        if(e){
            this.name += "+" + this.level;
            if(this.level == 0){
                this.cardsToPreview = new RecordCode();
            }
        }
        else {
            this.name += ":" + this.agents + "/" + (this.agents >= 3? "5": "3");
            this.rawDescription = AbstractDepartmentCard.DESCRIPTION;
            initializeDescription();
        }
        initializeTitle();
        this.addTips();
    }

    @Override
    public void tackAction() {
        if(!(AbstractDungeon.player instanceof Angela)){
            return;
        }
        if(this.e){
            Angela.departments[departmentCode[0]] ++;
            this.level = Angela.departments[departmentCode[0]];
            if(Angela.departments[18] >= 5 && !(AbstractDungeon.getCurrRoom() instanceof RestRoom)) {
                Angela.departments[18] -= 5;
            }
            switch (this.level){
                case 1:
                    AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(this.cardsToPreview.makeCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    break;
                case 2:
                    AbstractDungeon.player.potionSlots += 1;
                    AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
                    break;
                case 3:
                    break;
                case 4:
                    //AbstractDungeon.player.masterDeck.addToBottom(new SummonCall());
                    break;
                case 5:
                    AbstractDungeon.player.maxHealth *= 2;
                    AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                    break;
            }
        }
        else {
            Angela.departments[departmentCode[1]] ++;
            this.agents = Angela.departments[departmentCode[1]];
            Angela.departments[18] --;
            if(this.agents == 3 || this.agents == 5){
                for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
                    if(card instanceof RecordCode){
                        card.upgrade();
                        break;
                    }
                }
            }
        }
        super.tackAction();
    }

    public AbstractCard makeCopy() {
        return new Hokma(this.e);
    }

    @Override
    public void addTips() {
        this.tips.clear();
        if(!this.e){
            return;
        }
        switch (this.level){
            case 0:
                this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]));
            case 1:
                this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[2], EXTENDED_DESCRIPTION[3]));
            case 2:
                this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[4], EXTENDED_DESCRIPTION[5]));
            case 3:
                if(LobotomyMod.useBlackAngela) {
                    this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[6], EXTENDED_DESCRIPTION[7]));
                }
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Hokma");
        NAME = Hokma.cardStrings.NAME;
        DESCRIPTION = Hokma.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Hokma.cardStrings.EXTENDED_DESCRIPTION;
    }

    @SpirePatch(
            clz= RewardItem.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    int.class
            }
    )
    public static class CONSTRUCTOR1 {
        @SpireInsertPatch(rloc=2)
        public static void prefix(RewardItem _inst, int gold){
            if(!(AbstractDungeon.player instanceof Angela)){
                return;
            }
            if(Angela.departments[7] < 3){
                return;
            }
            _inst.goldAmt *= 1.5F;
        }
    }

    @SpirePatch(
            clz= RewardItem.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    int.class,
                    boolean.class
            }
    )
    public static class CONSTRUCTOR2 {
        @SpireInsertPatch(rloc=2)
        public static void prefix(RewardItem _inst, int gold, boolean theft){
            if(!(AbstractDungeon.player instanceof Angela)){
                return;
            }
            if(Angela.departments[7] < 3){
                return;
            }
            _inst.goldAmt *= 1.5F;
        }
    }

    @SpirePatch(
            clz= CombatRewardScreen.class,
            method = "setupItemReward"
    )
    public static class setupItemReward {
        @SpireInsertPatch(rloc=21)
        public static void prefix(CombatRewardScreen _inst){
            if(AbstractDungeon.player instanceof Angela && Angela.departments[7] > 3){
                RewardItem cardReward = new RewardItem();
                if (cardReward.cards.size() > 0) {
                    _inst.rewards.add(cardReward);
                }
            }
        }
    }
}
