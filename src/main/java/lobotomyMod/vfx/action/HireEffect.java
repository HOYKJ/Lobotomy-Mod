package lobotomyMod.vfx.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.angelaCard.bullets.SpecialBullet;
import lobotomyMod.card.angelaCard.department.*;
import lobotomyMod.card.deriveCard.AddBullet;
import lobotomyMod.card.deriveCard.ExpandDepartment;
import lobotomyMod.character.Angela;

/**
 * @author hoykj
 */
public class HireEffect extends AbstractGameEffect {
    private CardGroup choices;
    private boolean e;

    public HireEffect(){
        this.addOption();

        this.duration = Settings.ACTION_DUR_FASTER;
        this.e = false;
    }

    public void update() {
        if (this.duration != Settings.ACTION_DUR_FASTER) {
            for(AbstractGameEffect effect : AbstractDungeon.effectList){
                if(effect instanceof HireEffect && effect != this && !(effect.isDone)){
                    this.isDone = true;
                    return;
                }
            }

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                if(AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof AbstractDepartmentCard){
                    ((AbstractDepartmentCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0)).tackAction();
                    if(!this.e && Angela.departments[18] > 0){
                        this.addOption();
                        AbstractDungeon.gridSelectScreen.open(this.choices, 1, true, Angela.TEXT[4]);
                        AbstractDungeon.gridSelectScreen.selectedCards.clear();
                        return;
                    }
                }
                else if(AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof ExpandDepartment) {
                    this.choices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    int max = LobotomyMod.useBlackAngela? 5: 4;
                    if (Angela.departments[Malkuth.departmentCode[0]] < max) {
                        this.choices.addToTop(new Malkuth(true));
                    }
                    if (Angela.departments[Yesod.departmentCode[0]] < max) {
                        this.choices.addToTop(new Yesod(true));
                    }
                    if (Angela.departments[Netzach.departmentCode[0]] < max) {
                        this.choices.addToTop(new Netzach(true));
                    }
                    if (Angela.departments[Hod.departmentCode[0]] < max) {
                        this.choices.addToTop(new Hod(true));
                    }
                    if (Angela.departments[Tiphereth.departmentCode[0]] < max) {
                        this.choices.addToTop(new Tiphereth(true));
                    }
                    if (Angela.departments[Chesed.departmentCode[0]] < max) {
                        this.choices.addToTop(new Chesed(true));
                    }
                    if (Angela.departments[Geburah.departmentCode[0]] < max) {
                        this.choices.addToTop(new Geburah(true));
                    }
                    if (Angela.departments[Hokma.departmentCode[0]] < max) {
                        this.choices.addToTop(new Hokma(true));
                    }
                    if (Angela.departments[Binah.departmentCode[0]] < max) {
                        this.choices.addToTop(new Binah(true));
                    }

                    this.e = true;
                    AbstractDungeon.gridSelectScreen.open(this.choices, 1, true, Angela.TEXT[3]);
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    return;
                }
                else if (AbstractDungeon.gridSelectScreen.selectedCards.get(0) instanceof AddBullet) {
                    AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new SpecialBullet(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    Angela.departments[18] -= 2;
                }
                else{
                    return;
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.isDone = true;
            }
            return;
        }

        AbstractDungeon.gridSelectScreen.open(this.choices, 1, true, Angela.TEXT[4]);
        this.duration -= Gdx.graphics.getDeltaTime();
    }

    private void addOption(){
        this.choices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        if(Angela.departments[Malkuth.departmentCode[1]] < 5 && Angela.departments[Malkuth.departmentCode[0]] > 0) {
            this.choices.addToTop(new Malkuth(false));
        }
        if(Angela.departments[Yesod.departmentCode[1]] < 5 && Angela.departments[Yesod.departmentCode[0]] > 0) {
            this.choices.addToTop(new Yesod(false));
        }
        if(Angela.departments[Netzach.departmentCode[1]] < 5 && Angela.departments[Netzach.departmentCode[0]] > 0) {
            this.choices.addToTop(new Netzach(false));
        }
        if(Angela.departments[Hod.departmentCode[1]] < 5 && Angela.departments[Hod.departmentCode[0]] > 0) {
            this.choices.addToTop(new Hod(false));
        }
        if(Angela.departments[Tiphereth.departmentCode[1]] < 5 && Angela.departments[Tiphereth.departmentCode[0]] > 0) {
            this.choices.addToTop(new Tiphereth(false));
        }
        if(Angela.departments[Chesed.departmentCode[1]] < 5 && Angela.departments[Chesed.departmentCode[0]] > 0) {
            this.choices.addToTop(new Chesed(false));
        }
        if(Angela.departments[Geburah.departmentCode[1]] < 5 && Angela.departments[Geburah.departmentCode[0]] > 0) {
            this.choices.addToTop(new Geburah(false));
        }
        if(Angela.departments[Hokma.departmentCode[1]] < 5 && Angela.departments[Hokma.departmentCode[0]] > 0) {
            this.choices.addToTop(new Hokma(false));
        }
        if(Angela.departments[Binah.departmentCode[1]] < 5 && Angela.departments[Binah.departmentCode[0]] > 0) {
            this.choices.addToTop(new Binah(false));
        }
        if(Angela.departments[18] >= 2) {
            this.choices.addToTop(new AddBullet());
        }
        if(Angela.departments[18] >= 5) {
            this.choices.addToTop(new ExpandDepartment());
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
    }

    @Override
    public void dispose() {
    }
}
