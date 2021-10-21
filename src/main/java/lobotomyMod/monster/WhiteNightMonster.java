package lobotomyMod.monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import lobotomyMod.action.common.LatterAction;
import lobotomyMod.card.deriveCard.Apostles.*;
import lobotomyMod.card.rareCard.WhiteNight;
import lobotomyMod.vfx.action.LatterEffect;
import lobotomyMod.vfx.whiteNight.RemissionEffect;
import lobotomyMod.vfx.whiteNight.WhiteNightEffectCore;

/**
 * @author hoykj
 */
public class WhiteNightMonster extends AbstractMonster {
    public static final String ID = "WhiteNightMonster";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("WhiteNightMonster");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final TextureAtlas.AtlasRegion halo, light, line;
    private boolean first = true;

    public WhiteNightMonster(float x, float y) {
        super(NAME, "WhiteNightMonster", 999, 0.0F, 0.0F, 500.0F, 600.0F, null, x, y);
        this.damage.add(new DamageInfo(this, 40));
        loadAnimation("lobotomyMod/images/monsters/WhiteNight/WhiteNight.atlas", "lobotomyMod/images/monsters/WhiteNight/WhiteNight.json", 1.5F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "normal", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.setTimeScale(1.0F);
        this.flipHorizontal = true;
        this.type = EnemyType.BOSS;
    }

    public void usePreBattleAction() {
        this.flipHorizontal = true;
        this.first = true;
        AbstractDungeon.effectsQueue.add(new WhiteNightEffectCore(this.hb));
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        AbstractDungeon.getCurrRoom().playBgmInstantly("Lucifer_standbg0.mp3");
    }

    protected void getMove(int num) {
        if(this.first) {
            setMove((byte) 0, Intent.UNKNOWN);
        }
        else if (lastMove((byte)2)){
            setMove(MOVES[0], (byte)1, Intent.ATTACK_BUFF,(this.damage.get(0)).base);

        }
        else {
            setMove((byte)2, Intent.UNKNOWN);
        }
    }

    public void takeTurn() {
        switch (this.nextMove){
            case 0:
                this.first = false;
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new GuardApostle(), 2, true, false));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new ScytheApostle(), 2, true, false));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new WandApostle(), 3, true, false));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new SpearApostle(), 4, true, false));
                for(AbstractCard card : AbstractDungeon.player.masterDeck.group){
                    if(card instanceof HereticApostle){
                        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                        return;
                    }
                }
                if(AbstractDungeon.player.masterDeck.size() > 1) {
                    int i = AbstractDungeon.cardRng.random(AbstractDungeon.player.masterDeck.size() - 1);
                    while (AbstractDungeon.player.masterDeck.group.get(i) instanceof WhiteNight){
                        i = AbstractDungeon.cardRng.random(AbstractDungeon.player.masterDeck.size() - 1);
                    }
                    for(int i1 = 0; i1 < AbstractDungeon.player.drawPile.size(); i1 ++){
                        if(AbstractDungeon.player.drawPile.group.get(i1).uuid.equals(AbstractDungeon.player.masterDeck.group.get(i).uuid)){
                            AbstractDungeon.player.drawPile.group.set(i1, new HereticApostle());
                        }
                    }
                    for(int i1 = 0; i1 < AbstractDungeon.player.hand.size(); i1 ++){
                        if(AbstractDungeon.player.hand.group.get(i1).uuid.equals(AbstractDungeon.player.masterDeck.group.get(i).uuid)){
                            AbstractDungeon.player.hand.group.set(i1, new HereticApostle());
                        }
                    }
                    for(int i1 = 0; i1 < AbstractDungeon.player.discardPile.size(); i1 ++){
                        if(AbstractDungeon.player.discardPile.group.get(i1).uuid.equals(AbstractDungeon.player.masterDeck.group.get(i).uuid)){
                            AbstractDungeon.player.discardPile.group.set(i1, new HereticApostle());
                        }
                    }
                    AbstractDungeon.player.masterDeck.group.set(i, new HereticApostle());
                }
                else {
                    AbstractDungeon.player.masterDeck.group.add(new HereticApostle());
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new HereticApostle(), 1, true, false));
                }
                break;
            case 1:
                AbstractDungeon.effectList.add(new RemissionEffect(this.hb));
                AbstractDungeon.effectList.add(new LatterEffect(()->{

                }, 0.6F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0)));
                for(AbstractCard card : AbstractDungeon.player.exhaustPile.group){
                    if(card instanceof AbstractApostleCard){
                        AbstractDungeon.actionManager.addToBottom(new LatterAction(()->{
                            AbstractDungeon.player.discardPile.addToRandomSpot(card.makeCopy());
                            AbstractDungeon.player.exhaustPile.removeCard(card);
                        }));
                    }
                }
                break;
            case 2:
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void die()
    {
        super.die();
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            for(AbstractGameEffect effect : AbstractDungeon.effectList){
                if(effect instanceof WhiteNightEffectCore){
                    effect.isDone = true;
                }
            }
            AbstractDungeon.player.masterDeck.addToBottom(new WhiteNight());
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            this.deathTimer += 1.5F;
            onBossVictoryLogic();
        }
    }

    static {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("lobotomyMod/images/texture/backEffect.atlas"));
        halo = atlas.findRegion("0407");
        light = atlas.findRegion("0408");
        line = atlas.findRegion("0400");
    }
}
