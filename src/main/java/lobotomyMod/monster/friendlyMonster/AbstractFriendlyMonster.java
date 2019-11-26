package lobotomyMod.monster.friendlyMonster;

import basemod.helpers.SuperclassFinder;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author hoykj
 */
public abstract class AbstractFriendlyMonster extends AbstractMonster {
    protected ArrayList<AbstractCreature> targets;

    public AbstractFriendlyMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY, ArrayList<AbstractCreature> targets) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
        this.targets = targets;
    }

    @SpirePatch(
            clz= DamageAction.class,
            method = "update"
    )
    public static class update {
        @SpireInsertPatch(rloc=0)
        public static void Insert(DamageAction _inst) throws NoSuchFieldException, IllegalAccessException {
            Field info = SuperclassFinder.getSuperclassField(_inst.getClass(), "info");
            info.setAccessible(true);
            if(((DamageInfo)info.get(_inst)).owner == AbstractDungeon.player || ((DamageInfo)info.get(_inst)).owner instanceof AbstractFriendlyMonster || _inst.target != AbstractDungeon.player){
                return;
            }
            ArrayList<AbstractCreature> monsters = new ArrayList<>();
            for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
                if(monster instanceof AbstractFriendlyMonster){
                    monsters.add(monster);
                }
            }
            if(monsters.size() < 1){
                return;
            }

            int roll = AbstractDungeon.aiRng.random(monsters.size());
            if(roll == 0){
                return;
            }
            _inst.target = monsters.get(roll - 1);
        }
    }

    @SpirePatch(
            clz= ApplyPowerAction.class,
            method = "update"
    )
    public static class update2 {
        @SpireInsertPatch(rloc=0)
        public static void Insert(ApplyPowerAction _inst) throws NoSuchFieldException, IllegalAccessException {
            if(_inst.source == AbstractDungeon.player || _inst.source instanceof AbstractFriendlyMonster || _inst.target != AbstractDungeon.player){
                return;
            }
            ArrayList<AbstractCreature> monsters = new ArrayList<>();
            for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
                if(monster instanceof AbstractFriendlyMonster){
                    monsters.add(monster);
                }
            }
            if(monsters.size() < 1){
                return;
            }

            int roll = AbstractDungeon.aiRng.random(monsters.size());
            if(roll == 0){
                return;
            }
            _inst.target = monsters.get(roll - 1);
        }
    }
}
