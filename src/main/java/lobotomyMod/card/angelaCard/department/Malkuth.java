package lobotomyMod.card.angelaCard.department;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.angelaCard.code.ControlCode;
import lobotomyMod.card.angelaCard.code.other.SummonCall;
import lobotomyMod.card.angelaCard.code.other.TT2Protocol;
import lobotomyMod.character.Angela;

/**
 * @author hoykj
 */
public class Malkuth extends AbstractDepartmentCard {
    public static final String ID = "Malkuth";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static int departmentCode[] = new int[]{0, 9};

    public Malkuth(boolean e) {
        super("Malkuth", Malkuth.NAME, Malkuth.DESCRIPTION);
        departmentCode[0] = 0;
        departmentCode[1] = 9;
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
                this.cardsToPreview = new ControlCode();
            }
            else if(this.level == 1){
                this.cardsToPreview = new TT2Protocol();
            }
            else if(this.level == 3){
                this.cardsToPreview = new SummonCall();
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
                    AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new TT2Protocol(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    break;
                case 3:
                    break;
                case 4:
                    AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new SummonCall(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    break;
                case 5:
                    AbstractDungeon.player.energy.energyMaster += 1;
                    break;
            }
        }
        else {
            Angela.departments[departmentCode[1]] ++;
            this.agents = Angela.departments[departmentCode[1]];
            Angela.departments[18] --;
            if(this.agents == 3 || this.agents == 5){
                for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
                    if(card instanceof ControlCode){
                        card.upgrade();
                        break;
                    }
                }
            }
        }
        super.tackAction();
    }

    public AbstractCard makeCopy() {
        return new Malkuth(this.e);
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Malkuth");
        NAME = Malkuth.cardStrings.NAME;
        DESCRIPTION = Malkuth.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Malkuth.cardStrings.EXTENDED_DESCRIPTION;
    }
}
