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
import lobotomyMod.card.angelaCard.code.TrainingCode;
import lobotomyMod.card.angelaCard.code.other.SummonCall;
import lobotomyMod.card.angelaCard.code.other.TT2Protocol;
import lobotomyMod.character.Angela;

/**
 * @author hoykj
 */
public class Hod extends AbstractDepartmentCard {
    public static final String ID = "Hod";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static int departmentCode[] = new int[]{3, 12};

    public Hod(boolean e) {
        super("Hod", Hod.NAME, Hod.DESCRIPTION);
        departmentCode[0] = 3;
        departmentCode[1] = 12;
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
                this.cardsToPreview = new TrainingCode();
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
            }
        }
        else {
            Angela.departments[departmentCode[1]] ++;
            this.agents = Angela.departments[departmentCode[1]];
            Angela.departments[18] --;
            if(this.agents == 3 || this.agents == 5){
                for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
                    if(card instanceof TrainingCode){
                        card.upgrade();
                        break;
                    }
                }
            }
        }
        super.tackAction();
    }

    public AbstractCard makeCopy() {
        return new Hod(this.e);
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Hod");
        NAME = Hod.cardStrings.NAME;
        DESCRIPTION = Hod.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Hod.cardStrings.EXTENDED_DESCRIPTION;
    }
}
