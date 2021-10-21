package lobotomyMod.card.angelaCard.department;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.card.angelaCard.code.InformationCode;
import lobotomyMod.character.Angela;

import java.lang.reflect.Field;

/**
 * @author hoykj
 */
public class Yesod extends AbstractDepartmentCard {
    public static final String ID = "Yesod";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static int departmentCode[] = new int[]{1, 10};

    public Yesod(boolean e) {
        super("Yesod", Yesod.NAME, Yesod.DESCRIPTION);
        departmentCode[0] = 1;
        departmentCode[1] = 10;
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
                this.cardsToPreview = new InformationCode();
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
            if(this.level == 1){
                AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(this.cardsToPreview.makeCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                //AbstractDungeon.player.masterDeck.addToBottom(this.cardsToPreview.makeCopy());
            }
        }
        else {
            Angela.departments[departmentCode[1]] ++;
            this.agents = Angela.departments[departmentCode[1]];
            Angela.departments[18] --;
            if(this.agents == 3 || this.agents == 5){
                for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
                    if(card instanceof InformationCode){
                        card.upgrade();
                        break;
                    }
                }
            }
        }
        super.tackAction();
    }

    public AbstractCard makeCopy() {
        return new Yesod(this.e);
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Yesod");
        NAME = Yesod.cardStrings.NAME;
        DESCRIPTION = Yesod.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Yesod.cardStrings.EXTENDED_DESCRIPTION;
    }

    @SpirePatch(
            clz = ShowCardAndObtainEffect.class,
            method = "update"
    )
    public static class update {
        @SpireInsertPatch(rloc=10)
        public static void Insert(ShowCardAndObtainEffect _inst) throws NoSuchFieldException, IllegalAccessException {
            if(!(AbstractDungeon.player instanceof Angela)){
                return;
            }
            if(Angela.departments[1] < 4){
                return;
            }
            Field card = _inst.getClass().getDeclaredField("card");
            card.setAccessible(true);
            if(card.get(_inst) instanceof AbstractLobotomyCard){
                AbstractDungeon.player.increaseMaxHp(4, true);
            }
        }
    }

    @SpirePatch(
            clz = FastCardObtainEffect.class,
            method = "update"
    )
    public static class update2 {
        @SpireInsertPatch(rloc=10)
        public static void Insert(FastCardObtainEffect _inst) throws NoSuchFieldException, IllegalAccessException {
            if(!(AbstractDungeon.player instanceof Angela)){
                return;
            }
            if(Angela.departments[1] < 4){
                return;
            }
            Field card = _inst.getClass().getDeclaredField("card");
            card.setAccessible(true);
            if(card.get(_inst) instanceof AbstractLobotomyCard){
                AbstractDungeon.player.increaseMaxHp(4, true);
            }
        }
    }
}
