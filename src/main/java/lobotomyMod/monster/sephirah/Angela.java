package lobotomyMod.monster.sephirah;

import basemod.helpers.SuperclassFinder;
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
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import lobotomyMod.LobotomyMod;
import lobotomyMod.action.common.RemoveRandomDebuffAction;
import lobotomyMod.helper.LobotomyImageMaster;
import lobotomyMod.monster.Ordeal.AbstractOrdealMonster;
import lobotomyMod.monster.sephirah.Binah.Binah;
import lobotomyMod.power.MidShieldPower;
import lobotomyMod.power.ShiftingPower2;
import lobotomyMod.power.SmallShieldPower;
import lobotomyMod.power.SoulShieldPower;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author hoykj
 */
public class Angela extends AbstractOrdealMonster {
    public static final String ID = "Angela";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Angela");
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public int nextSmall = 0, nextMid = 0;
    private AbstractPower ss, ms;
    private int loseHP;

    public Angela(float x, float y) {
        super(NAME, "Angela", 1000, 0.0F, 0.0F, 240.0F, 380.0F, null, x, y);
        loadAnimation("lobotomyMod/images/characters/angela/angela_black/idle/angela_black.atlas", "lobotomyMod/images/characters/angela/angela_black/idle/angela_black.json", 2.8F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "newAnimation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.damage.add(new DamageInfo(this, 50));
        this.type = EnemyType.BOSS;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        this.flipHorizontal = true;
        this.drawY -= 66;
        AbstractDungeon.player.drawY -= 54;
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGMInstantly();
        AbstractDungeon.getCurrRoom().playBgmInstantly("Maya.mp3");
        this.nextSmall = AbstractDungeon.aiRng.random(2);
        this.nextMid = AbstractDungeon.aiRng.random(3);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ShiftingPower2(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SoulShieldPower(this, 4), 4));
        this.ss = new SmallShieldPower(this);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, this.ss));
        this.ms = new MidShieldPower(this);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, this.ms));

        try {
            Field current_y = SuperclassFinder.getSuperclassField(AbstractDungeon.overlayMenu.endTurnButton.getClass(), "current_y");
            current_y.setAccessible(true);
            current_y.set(AbstractDungeon.overlayMenu.endTurnButton, Settings.HEIGHT - 210.0F * Settings.scale);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        LobotomyMod.deadTime = 0;
    }

    protected void getMove(int num) {
        setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
    }

    public void takeTurn() {
        if(this.nextMove == 1){
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, this.damage.get(0), (this.damage.get(0).base > 30? AbstractGameAction.AttackEffect.BLUNT_HEAVY: AbstractGameAction.AttackEffect.BLUNT_LIGHT)));
        }
        this.damage.get(0).base += 10;
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void damage(DamageInfo info) {
        int tmp = this.currentHealth;
        super.damage(info);
        tmp -= this.currentHealth;
        this.loseHP += tmp;
        if(tmp > 10){
            switch (this.nextSmall){
                case 0:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, 1), 1));
                    break;
                case 1:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, 1), 1));
                    break;
                case 2:
                    AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, 9));
                    break;
            }
            this.nextSmall = AbstractDungeon.aiRng.random(2);
            this.ss.updateDescription();
        }
        if(this.loseHP >= this.maxHealth * 0.2F){
            switch (this.nextSmall){
                case 0:
                    AbstractDungeon.actionManager.addToBottom(new RemoveRandomDebuffAction(this));
                    AbstractDungeon.actionManager.addToBottom(new RemoveRandomDebuffAction(this));
                    break;
                case 1:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                            new WeakPower(AbstractDungeon.player, 2, true), 2));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                            new FrailPower(AbstractDungeon.player, 2, true), 2));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this,
                            new VulnerablePower(AbstractDungeon.player, 2, true), 2));
                    break;
                case 2:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 5), 5));
                    break;
                case 3:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePlayerPower(this, 2), 2));
                    break;
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SoulShieldPower(this, 1), 1));
            this.nextMid = AbstractDungeon.aiRng.random(3);
            this.ms.updateDescription();
            this.loseHP -= this.maxHealth * 0.2F;
        }
    }

    public void die() {
        super.die();
        this.deathTimer += 1.5F;
        try {
            Field current_y = SuperclassFinder.getSuperclassField(AbstractDungeon.overlayMenu.endTurnButton.getClass(), "current_y");
            current_y.setAccessible(true);
            current_y.set(AbstractDungeon.overlayMenu.endTurnButton, 210.0F * Settings.scale);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        LobotomyMod.activeBlackAngela = true;
        try {
            LobotomyMod.saveData();
        } catch (IOException e) {
            e.printStackTrace();
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
                if(m instanceof Angela){
                    Texture img = LobotomyImageMaster.ANGELA_BOSS_BACKGROUND;
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
                    if(m instanceof Angela){
                        return SpireReturn.Return(null);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }
}
