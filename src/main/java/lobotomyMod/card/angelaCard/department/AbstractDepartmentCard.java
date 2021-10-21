package lobotomyMod.card.angelaCard.department;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import lobotomyMod.LobotomyMod;
import lobotomyMod.card.angelaCard.code.AbstractCodeCard;
import lobotomyMod.card.angelaCard.code.ControlCode;
import lobotomyMod.card.deriveCard.test;
import lobotomyMod.character.Angela;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.patch.AbstractCardEnum;
import lobotomyMod.vfx.action.LatterEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hoykj
 */
public abstract class AbstractDepartmentCard extends CustomCard {
    public static final String ID = "AbstractDepartmentCard";
    private static final CardStrings cardStrings;
    public static final String DESCRIPTION;
    public List<TooltipInfo> tips = new ArrayList<>();
    public int level, agents;
    protected boolean e;

    public AbstractDepartmentCard(final String id, final String name, final String description) {
        super(id, name, (LobotomyMod.useBlackAngela? LobotomyHandler.angelaBlackCardImage(id): LobotomyHandler.angelaCardImage(id)), -2, description, CardType.SKILL, AbstractCardEnum.Angela, CardRarity.SPECIAL, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }

    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        return this.tips;
    }

//    @Override
//    public void renderCardTip(SpriteBatch sb) {
//        super.renderCardTip(sb);
//        if (this.cardsToPreview != null) {
//            renderCardPreview(sb);
//        }
//    }

//    public void renderCardPreviewInSingleView(SpriteBatch sb)
//    {
//        this.cardsToPreview.current_x = (1435.0F * Settings.scale);
//        this.cardsToPreview.current_y = (795.0F * Settings.scale);
//        this.cardsToPreview.drawScale = 0.8F;
//        this.cardsToPreview.render(sb);
//    }
//
//    public void renderCardPreview(SpriteBatch sb)
//    {
//        if ((AbstractDungeon.player != null) && (AbstractDungeon.player.isDraggingCard)) {
//            return;
//        }
//        float tmpScale = this.drawScale * 0.8F;
//        if (this.current_x > Settings.WIDTH * 0.75F) {
//            this.cardsToPreview.current_x = (this.current_x + (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.8F + 16.0F) * this.drawScale);
//        } else {
//            this.cardsToPreview.current_x = (this.current_x - (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.8F + 16.0F) * this.drawScale);
//        }
//        this.cardsToPreview.current_y = (this.current_y + (IMG_HEIGHT / 2.0F - IMG_HEIGHT / 2.0F * 0.8F) * this.drawScale);
//
//        this.cardsToPreview.drawScale = tmpScale;
//        this.cardsToPreview.render(sb);
//    }

    public void tackAction(){
        for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
            if(card instanceof AbstractCodeCard && ((AbstractCodeCard) card).dep){
                ((AbstractCodeCard) card).expand();
            }
        }
//        if(!this.e && Angela.departments[18] > 0){
//            LobotomyMod.logger.info("----------LP: " + Angela.departments[18]);
//            AbstractDungeon.effectsQueue.add(new LatterEffect(LobotomyUtils::hireDepartment, 0.5F));
//        }
    }

    public abstract void addTips();

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("AbstractDepartmentCard");
        DESCRIPTION = AbstractDepartmentCard.cardStrings.DESCRIPTION;
    }

//    @SpirePatch(
//            clz= SingleCardViewPopup.class,
//            method="renderTips"
//    )
//    public static class renderTips {
//        @SpirePostfixPatch
//        public static void postfix(SingleCardViewPopup _inst, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException {
//            Field card = _inst.getClass().getDeclaredField("card");
//            card.setAccessible(true);
//            if(!(card.get(_inst) instanceof AbstractDepartmentCard)){
//                return;
//            }
//            if (((AbstractDepartmentCard)card.get(_inst)).cardsToPreview != null) {
//                ((AbstractDepartmentCard)card.get(_inst)).renderCardPreviewInSingleView(sb);
//            }
//        }
//    }
}
