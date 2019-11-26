package lobotomyMod.card.commonCard;

import basemod.abstracts.CustomSavable;
import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.AbstractLobotomyCard;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.relic.CogitoBucket;
import lobotomyMod.vfx.MHzNoiseEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public class MHz extends AbstractLobotomyCard implements CustomSavable<int[]> {
    public static final String ID = "MHz";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public ArrayList<AbstractCard> noiseTargets = new ArrayList<>();

    public MHz() {
        super("MHz", MHz.NAME, MHz.DESCRIPTION, CardRarity.COMMON, CardTarget.ENEMY, 27, 3, 1);
        this.noiseTargets.add(this);
        initInfo();
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(this.cost == -1){
            if(this.realCost >= 0) {
                p.energy.use(this.realCost);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
            this.changeCost(this.realCost + 2);
            if(this.realCost > 2){
                AbstractCard target;
                if(AbstractDungeon.player.drawPile.size() > 0){
                    target = AbstractDungeon.player.drawPile.getRandomCard(false);
                }
                else if(AbstractDungeon.player.hand.size() > 0){
                    target = AbstractDungeon.player.hand.getRandomCard(false);
                }
                else if(AbstractDungeon.player.discardPile.size() > 0){
                    target = AbstractDungeon.player.discardPile.getRandomCard(false);
                }
                else {
                    return;
                }

                this.noiseTargets.add(target);
            }
        }));
        AbstractDungeon.actionManager.addToBottom(new ExhumeAction(false));
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        if(!this.noiseTargets.contains(this)){
            this.noiseTargets.add(this);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        if(this.realCost == 3){
            this.noiseTargets.clear();
            this.noiseTargets.add(this);
        }
        if(this.realCost > 0){
            this.changeCost(this.realCost - 1);
        }
    }

    @Override
    public void endOfTurn(boolean hand) {
        super.endOfTurn(hand);
        if(this.realCost > 2){
            AbstractCard target;
            if(AbstractDungeon.player.drawPile.size() > 0){
                target = AbstractDungeon.player.drawPile.getRandomCard(false);
            }
            else if(AbstractDungeon.player.hand.size() > 0){
                target = AbstractDungeon.player.hand.getRandomCard(false);
            }
            else if(AbstractDungeon.player.discardPile.size() > 0){
                target = AbstractDungeon.player.discardPile.getRandomCard(false);
            }
            else {
                return;
            }

            this.noiseTargets.add(target);
        }
    }

    @Override
    public void hover() {
        try {
            Field hovered = SuperclassFinder.getSuperclassField(this.getClass(), "hovered");
            hovered.setAccessible(true);
            if (!(boolean)hovered.get(this)) {
                if(MathUtils.randomBoolean()) {
                    CardCrawlGame.sound.play("MHz");
                }
                else {
                    CardCrawlGame.sound.play("MHz2");
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        super.hover();
    }

    @Override
    public void changeCost(int cost) {
        super.changeCost(cost);
        if(this.realCost > 2){
            AbstractDungeon.effectList.add(new MHzNoiseEffect());
        }
        if(this.realCost <= 2){
            for(AbstractGameEffect effect : AbstractDungeon.effectList){
                if(effect instanceof MHzNoiseEffect){
                    ((MHzNoiseEffect) effect).end();
                }
            }
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        for(AbstractGameEffect effect : AbstractDungeon.effectList){
            if(effect instanceof MHzNoiseEffect){
                ((MHzNoiseEffect) effect).end();
            }
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if((this.cost == -1) && (EnergyPanel.totalCount < this.realCost)){
            return false;
        }
        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new MHz();
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
        this.name = MHz.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
        this.rawDescription = MHz.EXTENDED_DESCRIPTION[this.infoStage];
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
        cardStrings = CardCrawlGame.languagePack.getCardStrings("MHz");
        NAME = MHz.cardStrings.NAME;
        DESCRIPTION = MHz.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = MHz.cardStrings.EXTENDED_DESCRIPTION;
    }
}
