package lobotomyMod.ui;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.angelaCard.department.*;
import lobotomyMod.character.Angela;
import lobotomyMod.vfx.BlackScreenEffect;
import lobotomyMod.vfx.action.ChooseEffect;

/**
 * @author hoykj
 */
public class ExpandOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExpandOption");
    public static final String[] TEXT = uiStrings.TEXT;

    public static Texture GetUpgradeOptionTexture()
    {
        return new Texture("lobotomyMod/images/ui/campfire/expand.png");
    }

    public ExpandOption(boolean active)
    {
        this.label = TEXT[0];
        this.usable = active;
        if (active) {
            this.description = TEXT[1];
            this.img = GetUpgradeOptionTexture();
        } else {
            this.description = TEXT[1];
            this.img = GetUpgradeOptionTexture();
        }
    }

    public void useOption()
    {
        Angela.tmpD = Angela.departments.clone();
        final ChooseEffect choice = new ChooseEffect(null, null, Angela.TEXT[3], false, 1);
        int max = LobotomyMod.useBlackAngela? 5: 4;
        if (Angela.departments[Malkuth.departmentCode[0]] < max) {
            AbstractDepartmentCard tmp = new Malkuth(true);
            choice.add(tmp, ()->{
                tmp.tackAction();
                this.end();
            });
        }

        if (Angela.departments[Yesod.departmentCode[0]] < max) {
            AbstractDepartmentCard tmp = new Yesod(true);
            choice.add(tmp, ()->{
                tmp.tackAction();
                this.end();
            });
        }

        if (Angela.departments[Netzach.departmentCode[0]] < max) {
            AbstractDepartmentCard tmp = new Netzach(true);
            choice.add(tmp, ()->{
                tmp.tackAction();
                this.end();
            });
        }

        if (Angela.departments[Hod.departmentCode[0]] < max) {
            AbstractDepartmentCard tmp = new Hod(true);
            choice.add(tmp, ()->{
                tmp.tackAction();
                this.end();
            });
        }

        if (Angela.departments[Tiphereth.departmentCode[0]] < max) {
            AbstractDepartmentCard tmp = new Tiphereth(true);
            choice.add(tmp, ()->{
                tmp.tackAction();
                this.end();
            });
        }

        if (Angela.departments[Chesed.departmentCode[0]] < max) {
            AbstractDepartmentCard tmp = new Chesed(true);
            choice.add(tmp, ()->{
                tmp.tackAction();
                this.end();
            });
        }

        if (Angela.departments[Geburah.departmentCode[0]] < max) {
            AbstractDepartmentCard tmp = new Geburah(true);
            choice.add(tmp, ()->{
                tmp.tackAction();
                this.end();
            });
        }

        if (Angela.departments[Hokma.departmentCode[0]] < max) {
            AbstractDepartmentCard tmp = new Hokma(true);
            choice.add(tmp, ()->{
                tmp.tackAction();
                this.end();
            });
        }

        if (Angela.departments[Binah.departmentCode[0]] < max) {
            AbstractDepartmentCard tmp = new Binah(true);
            choice.add(tmp, ()->{
                tmp.tackAction();
                this.end();
            });
        }

//                tmp = new Binah(true);
//                choice.add(tmp, tmp::tackAction);
        AbstractDungeon.effectsQueue.add(choice);
    }

    public void end(){
        AbstractDungeon.effectsQueue.add(new BlackScreenEffect());
        ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
        com.megacrit.cardcrawl.rooms.AbstractRoom.waitTimer = 0F;
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
    }
}
