package lobotomyMod.monster.sephirah;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.monster.sephirah.Binah.Binah;
import lobotomyMod.power.HokmaTimeWarpPower;
import lobotomyMod.power.ResistantPower;

/**
 * @author hoykj
 */
public class Hokma extends AbstractOrdealMonster {
    public static final String ID = "Hokma";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Hokma");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private int turns;
    public int limit, price;

    public Hokma(float x, float y) {
        super(NAME, "Hokma", 1500, 0.0F, -12.0F, 520.0F, 500.0F, null, x, y);
        loadAnimation("lobotomyMod/images/monsters/Sephirah/Hokma/chokma.atlas", "lobotomyMod/images/monsters/Sephirah/Hokma/chokma.json", 2.1F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Phase_01", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Phase_01", "Phase_02", 0.2F);
        this.damage.add(new DamageInfo(this, 12));
        this.turns = 1;
        this.limit = 12;
        this.price = 2;
        this.dialogX = -130;
        this.dialogY = 110;
        this.type = EnemyType.BOSS;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        AbstractDungeon.getCurrRoom().playBgmInstantly("Hokma.mp3");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new HokmaTimeWarpPower(this)));
    }

    protected void getMove(int num) {
        if(this.turns > 4){
            setMove((byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(0).base, 3, true);
        }
        else {
            setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base, 3, true);
        }
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        else if(this.nextMove == 2){
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DrawReductionPower(AbstractDungeon.player, 99)));
        }
        if(this.turns == 3){
            this.limit = 9;
            this.price = 4;
        }
        else if(this.turns == 6){
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "CHANGE"));
            this.limit = 6;
            this.price = 6;
        }
        this.turns ++;
        if(!this.hasPower(HokmaTimeWarpPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new HokmaTimeWarpPower(this)));
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void die() {
        super.die();
        this.deathTimer += 1.5F;
    }

    public void priceForStop(){
        if(MathUtils.randomBoolean()) {
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[0]));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[1]));
        }
        if(AbstractDungeon.player.hand.size() > 0){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.hand.getRandomCard(true), AbstractDungeon.player.hand));
        }
        else if(AbstractDungeon.player.drawPile.size() > 0){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.drawPile.getRandomCard(true), AbstractDungeon.player.drawPile));
        }
        else if(AbstractDungeon.player.discardPile.size() > 0){
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.discardPile.getRandomCard(true), AbstractDungeon.player.discardPile));
        }
    }

    public void changeState(String key)
    {
        switch (key)
        {
            case "CHANGE":
                this.state.setAnimation(0, "Phase_02", true);
                this.state.setTimeScale(1F);
                break;
        }
    }

    @SpirePatch(
            clz= TheBeyondScene.class,
            method="renderCombatRoomBg"
    )
    public static class renderCombatRoomBg {
        @SpirePostfixPatch
        public static void postfix(TheBeyondScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters == null || AbstractDungeon.getCurrRoom().monsters.monsters.size() == 0){
                return;
            }
            for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                if(m instanceof Hokma){
                    Texture img = LobotomyImageMaster.HOKMA_BACKGROUND;
                    sb.setColor(Color.WHITE.cpy());
                    sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
                    return;
                }
            }
        }
    }

    @SpirePatch(
            clz= TheBeyondScene.class,
            method="renderCombatRoomFg"
    )
    public static class renderCombatRoomFg {
        @SpirePrefixPatch
        public static SpireReturn prefix(TheBeyondScene _inst, SpriteBatch sb){
            if(AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.monsters != null
                    && AbstractDungeon.getCurrRoom().monsters.monsters.size() > 0){
                for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                    if(m instanceof Hokma){
                        return SpireReturn.Return(null);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }
}
