package lobotomyMod.monster.friendlyMonster;

import basemod.helpers.SuperclassFinder;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BackAttackPower;
import lobotomyMod.LobotomyMod;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author hoykj
 */
public abstract class AbstractFriendlyMonster extends AbstractMonster {
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("AbstractFriendlyMonster");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    protected ArrayList<AbstractCreature> targets;
    public boolean canBeTarget = true, friend = false;
    protected AbstractCreature target;
    private boolean Rclick;
    private float startX, startY;
    public String identifier = null;

    public AbstractFriendlyMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
    }

    public AbstractFriendlyMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY, ArrayList<AbstractCreature> targets) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
        this.targets = targets;
    }

    public void changeTarget(AbstractCreature target){
        this.target = target;
        this.applyPowers();
    }

    public boolean stateNormal(){
        return !this.isDying && this.state != null && this.state.getTracks() != null;
    }

    @Override
    public void update() {
        super.update();
        //this.applyPowers();
        if(this.friend) {
            if (this.hb != null && this.hb.hovered && InputHelper.justClickedRight) {
                this.Rclick = true;
                this.startX = InputHelper.mX;
                this.startY = InputHelper.mY;
            }
            if (InputHelper.justReleasedClickRight) {
                this.Rclick = false;
            }

            if (this.Rclick) {
                this.drawX += InputHelper.mX - this.startX;
                this.drawY += InputHelper.mY - this.startY;
                this.startX = InputHelper.mX;
                this.startY = InputHelper.mY;
            }

            if (this.hb != null) {
                if (this.drawX < this.hb.width / 2) {
                    this.drawX = this.hb.width / 2;
                } else if (this.drawX > Settings.WIDTH - this.hb.width / 2) {
                    this.drawX = Settings.WIDTH - this.hb.width / 2;
                }

                if (this.drawY < 0) {
                    this.drawY = 0;
                } else if (this.drawY > Settings.HEIGHT - this.hb.height) {
                    this.drawY = Settings.HEIGHT - this.hb.height;
                }
            }
        }

        this.flipHorizontal = this.target.drawX < this.drawX;

        if (this.intent == Intent.DEBUG){
            this.createIntent();
        }
    }

    public void randomTarget(){
        if(this.friend){
            if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead()){
                this.changeTarget(AbstractDungeon.getMonsters().getRandomMonster(this, true));
            }
        }
        else {
            ArrayList<AbstractCreature> tmp = new ArrayList<>();
            tmp.add(AbstractDungeon.player);
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                if (!m.halfDead && !m.isDying && !m.isEscaping && m != this) {
                    if (!(this.identifier != null && m instanceof AbstractFriendlyMonster && ((AbstractFriendlyMonster) m).identifier != null
                            && ((AbstractFriendlyMonster) m).identifier.equals(this.identifier))) {
                        tmp.add(m);
                    }
                }
            }
            this.changeTarget(tmp.get(AbstractDungeon.aiRng.random(0, tmp.size() - 1)));
        }
    }

    @Override
    public void applyPowers() {
        try {
            Field move = SuperclassFinder.getSuperclassField(this.getClass(), "move");
            Field intentImg = SuperclassFinder.getSuperclassField(this.getClass(), "intentImg");
            Method getIntentImg = SuperclassFinder.getSuperClassMethod(this.getClass(), "getIntentImg");
            Method updateIntentTip = SuperclassFinder.getSuperClassMethod(this.getClass(), "updateIntentTip");
            move.setAccessible(true);
            intentImg.setAccessible(true);
            getIntentImg.setAccessible(true);
            updateIntentTip.setAccessible(true);

            if(this.target == null){
                this.target = this;
            }

            boolean applyBackAttack = this.applyBackAttack();
            if (applyBackAttack && !this.hasPower("BackAttack")) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, null, new BackAttackPower(this)));
            }

            Iterator var2 = this.damage.iterator();

            while(var2.hasNext()) {
                DamageInfo dmg = (DamageInfo)var2.next();
                dmg.applyPowers(this, this.target);
                if (applyBackAttack) {
                    dmg.output = (int)((float)dmg.output * 1.5F);
                }
            }

            if (((EnemyMoveInfo)move.get(this)).baseDamage > -1) {
                this.calculateDamage(((EnemyMoveInfo)move.get(this)).baseDamage);
            }

            intentImg.set(this, getIntentImg.invoke(this));
            updateIntentTip.invoke(this);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void calculateDamage(int dmg) throws NoSuchFieldException, IllegalAccessException {
        Field intentDmg = SuperclassFinder.getSuperclassField(this.getClass(), "intentDmg");
        intentDmg.setAccessible(true);

        if(this.target == null){
            this.target = this;
        }
        float tmp = (float)dmg;
        if (Settings.isEndless && AbstractDungeon.player.hasBlight("DeadlyEnemies")) {
            float mod = AbstractDungeon.player.getBlight("DeadlyEnemies").effectFloat();
            tmp *= mod;
        }

        AbstractPower p;
        Iterator var6;
        for(var6 = this.powers.iterator(); var6.hasNext(); tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL)) {
            p = (AbstractPower)var6.next();
        }

        for(var6 = target.powers.iterator(); var6.hasNext(); tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)) {
            p = (AbstractPower)var6.next();
        }

        tmp = AbstractDungeon.player.stance.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL);
        if (this.applyBackAttack()) {
            tmp = (float)((int)(tmp * 1.5F));
        }

        for(var6 = this.powers.iterator(); var6.hasNext(); tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL)) {
            p = (AbstractPower)var6.next();
        }

        for(var6 = target.powers.iterator(); var6.hasNext(); tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL)) {
            p = (AbstractPower)var6.next();
        }

        dmg = MathUtils.floor(tmp);
        if (dmg < 0) {
            dmg = 0;
        }

        intentDmg.set(this, dmg);
    }

    private boolean applyBackAttack() {
        return (this.target == AbstractDungeon.player && AbstractDungeon.player.hasPower("Surrounded") && (AbstractDungeon.player.flipHorizontal && AbstractDungeon.player.drawX < this.drawX || !AbstractDungeon.player.flipHorizontal && AbstractDungeon.player.drawX > this.drawX));
    }

    @Override
    public void renderTip(SpriteBatch sb) {
        try {
            Field intentTip = SuperclassFinder.getSuperclassField(this.getClass(), "intentTip");
            intentTip.setAccessible(true);

            this.tips.clear();
            if (this.intentAlphaTarget == 1.0F && !AbstractDungeon.player.hasRelic("Runic Dome") && this.intent != AbstractMonster.Intent.NONE) {
                this.tips.add((PowerTip) intentTip.get(this));
            }

            if(this.friend) {
                this.tips.add(new PowerTip(DIALOG[0], DIALOG[1]));
            }

            Iterator var2 = this.powers.iterator();

            while(var2.hasNext()) {
                AbstractPower p = (AbstractPower)var2.next();
                if (p.region48 != null) {
                    this.tips.add(new PowerTip(p.name, p.description, p.region48));
                } else {
                    this.tips.add(new PowerTip(p.name, p.description, p.img));
                }
            }

            if (!this.tips.isEmpty()) {
                if (this.hb.cX + this.hb.width / 2.0F < TIP_X_THRESHOLD) {
                    TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0F + TIP_OFFSET_R_X, this.hb.cY + TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
                } else {
                    TipHelper.queuePowerTips(this.hb.cX - this.hb.width / 2.0F + TIP_OFFSET_L_X, this.hb.cY + TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @SpirePatch(
            clz= DamageAction.class,
            method = "update"
    )
    public static class update {
        @SpireInsertPatch(rloc=22)
        public static void Insert(DamageAction _inst) throws NoSuchFieldException, IllegalAccessException {
            if(!_inst.isDone){
                return;
            }
            Field info = SuperclassFinder.getSuperclassField(_inst.getClass(), "info");
            info.setAccessible(true);
            if(((DamageInfo)info.get(_inst)).owner == AbstractDungeon.player || ((DamageInfo)info.get(_inst)).owner instanceof AbstractFriendlyMonster || _inst.target != AbstractDungeon.player){
                return;
            }
            ArrayList<AbstractCreature> monsters = new ArrayList<>();
            for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
                if(monster instanceof AbstractFriendlyMonster && !monster.isDeadOrEscaped() && !monster.halfDead && ((AbstractFriendlyMonster) monster).canBeTarget){
                    monsters.add(monster);
                }
            }
            if(monsters.size() < 1){
                return;
            }

            int roll = AbstractDungeon.aiRng.random(monsters.size() + 1);
            LobotomyMod.logger.info("damage roll: " + roll);
            if(roll == 0 || roll == 1){
                return;
            }
            _inst.target = monsters.get(roll - 2);
        }
    }

    @SpirePatch(
            clz= ApplyPowerAction.class,
            method = "update"
    )
    public static class update2 {
        @SpireInsertPatch(rloc=0)
        public static void Insert(ApplyPowerAction _inst) {
            if(_inst.source == AbstractDungeon.player || _inst.source instanceof AbstractFriendlyMonster || _inst.target != AbstractDungeon.player){
                return;
            }
            ArrayList<AbstractCreature> monsters = new ArrayList<>();
            for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
                if(monster instanceof AbstractFriendlyMonster && !monster.isDeadOrEscaped() && !monster.halfDead && ((AbstractFriendlyMonster) monster).canBeTarget){
                    monsters.add(monster);
                }
            }
            if(monsters.size() < 1){
                return;
            }

            int roll = AbstractDungeon.aiRng.random(monsters.size() + 1);
            if(roll == 0 || roll == 1){
                return;
            }
            _inst.target = monsters.get(roll - 2);
        }
    }

//    @SpirePatch(
//            clz= AbstractMonster.class,
//            method = "renderTip"
//    )
//    public static class renderTip {
//        @SpireInsertPatch(rloc=1)
//        public static void Insert(AbstractMonster _inst, SpriteBatch sb) {
//            if(_inst instanceof AbstractFriendlyMonster){
//                _inst.tips.add()
//            }
//        }
//    }
}
