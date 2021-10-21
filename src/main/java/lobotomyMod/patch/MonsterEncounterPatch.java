package lobotomyMod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import lobotomyMod.LobotomyMod;
import lobotomyMod.monster.*;
import lobotomyMod.monster.Ordeal.Bug.BugDawn;
import lobotomyMod.monster.Ordeal.Bug.BugMidnight;
import lobotomyMod.monster.Ordeal.Bug.BugNight;
import lobotomyMod.monster.Ordeal.Circus.CircusDawn;
import lobotomyMod.monster.Ordeal.Claw;
import lobotomyMod.monster.Ordeal.Cleaner;
import lobotomyMod.monster.Ordeal.Fixer.BlackFixer;
import lobotomyMod.monster.Ordeal.Fixer.PaleFixer;
import lobotomyMod.monster.Ordeal.Fixer.RedFixer;
import lobotomyMod.monster.Ordeal.Fixer.WhiteFixer;
import lobotomyMod.monster.Ordeal.Machine.MachineDawn;
import lobotomyMod.monster.Ordeal.Machine.MachineMidnight;
import lobotomyMod.monster.Ordeal.Machine.MachineNight;
import lobotomyMod.monster.Ordeal.Machine.MachineNoon;
import lobotomyMod.monster.Ordeal.OutterGod.OutterGodDawn;
import lobotomyMod.monster.Ordeal.OutterGod.OutterGodMidnight.*;
import lobotomyMod.monster.Ordeal.OutterGod.OutterGodNoon;
import lobotomyMod.monster.sephirah.*;
import lobotomyMod.monster.sephirah.Binah.Binah;

/**
 * @author hoykj
 */
@SpirePatch(cls="com.megacrit.cardcrawl.helpers.MonsterHelper", method="getEncounter")
public class MonsterEncounterPatch {
    public static MonsterGroup Postfix(MonsterGroup __result, String key)
    {
        switch (key) {
            case "WhiteNightMonster":
                return new MonsterGroup(new AbstractMonster[]{new WhiteNightMonster(0.0F, 0.0F)});
            case "ApocalypseBird":
                return new MonsterGroup(new AbstractMonster[]{
                        new ApocalypseBirdMonster(150.0F, 0.0F), new BigEyes(-520.0F, -20.0F), new LongArm(-300.0F, -20.0F), new SmallBeak(-80.0F, -20.0F)
                });
            case "MachineDawn":
                return new MonsterGroup(new AbstractMonster[]{new MachineDawn(-300.0F, -20.0F), new MachineDawn(-50.0F, 0.0F), new MachineDawn(200.0F, -20.0F)});
            case "MachineNoon":
                return new MonsterGroup(new AbstractMonster[]{new MachineNoon(-360.0F, -20.0F), new MachineNoon(-80.0F, 0.0F), new MachineNoon(200.0F, -20.0F)});
            case "MachineNight":
                return new MonsterGroup(new AbstractMonster[]{new MachineNight(160.0F, -20.0F)});
            case "MachineMidnight":
                return new MonsterGroup(new AbstractMonster[]{new MachineMidnight(60.0F, 0.0F)});
            case "BugDawn":
                return new MonsterGroup(new AbstractMonster[]{new BugDawn(-430.0F, -20.0F), new BugDawn(-220.0F, 0.0F), new BugDawn(-10.0F, -20.0F), new BugDawn(200.0F, 0.0F)});
            case "BugNight":
                return new MonsterGroup(new AbstractMonster[]{new BugNight(180.0F, 0.0F)});
            case "BugMidnight":
                return new MonsterGroup(new AbstractMonster[]{new BugMidnight(-(Settings.WIDTH * 0.75F / Settings.scale) + 240, 0.0F, 1), new BugMidnight(Settings.WIDTH * 0.25F / Settings.scale - 240, 0.0F, 2)});
            case "OutterGodDawn":
                return new MonsterGroup(new AbstractMonster[]{new OutterGodDawn(-400.0F, -20.0F), new OutterGodDawn(-100.0F, 0.0F), new OutterGodDawn(200.0F, -20.0F)});
            case "OutterGodNoon":
                return new MonsterGroup(new AbstractMonster[]{new OutterGodNoon(-100.0F, 1000.0F)});
            case "OutterGodMidnight":
                RedAttacker a1 = new RedAttacker(0, 0, 1);
                RedAttacker a2 = new RedAttacker(0, 0, 2);
                WhiteAttacker a3 = new WhiteAttacker(0, 0, 1);
                WhiteAttacker a4 = new WhiteAttacker(0, 0, 2);
                BlackAttacker a5 = new BlackAttacker(0, 0, 1);
                BlackAttacker a6 = new BlackAttacker(0, 0, 2);
                PaleAttacker a8 = new PaleAttacker(0, 0);
                PaleAltar a7 = new PaleAltar(Settings.WIDTH / Settings.scale - 840, 0, a8);
                a8.addParent(a7);
                return new MonsterGroup(new AbstractMonster[]{new WhiteAltar(300.0F, 0.0F, a3, a4), new WhiteAttacker(0, 0, 0),
                        new RedAltar(-20.0F, -20.0F, a1, a2), new RedAttacker(0, 0, 0), a7,
                        new BlackAltar(Settings.WIDTH / Settings.scale - 520, -20.0F, a5, a6), new BlackAttacker(0, 0, 0), a1, a2, a3, a4, a5, a6, a8
                });
            case "CircusDawn":
                return new MonsterGroup(new AbstractMonster[]{new CircusDawn(-300.0F, -20.0F), new CircusDawn(-50.0F, 0.0F), new CircusDawn(200.0F, -20.0F)});
            case "FixerDawn":
                return new MonsterGroup(new AbstractMonster[]{getRandomFixer()});
            case "FixerNoon":
                return new MonsterGroup(getRandomFixerGroup());
            case "FixerNight":
                return new MonsterGroup(new AbstractMonster[]{new RedFixer(-550.0F, -20.0F), new BlackFixer(-300.0F, 0.0F), new WhiteFixer(-50.0F, -20.0F), new PaleFixer(200.0F, 0.0F)});
            case "FixerMidnight":
                return new MonsterGroup(new AbstractMonster[]{new Claw(-50.0F, 0.0F)});
            case "Cleaner":
                return new MonsterGroup(new AbstractMonster[]{new Cleaner(-550.0F, -20.0F), new Cleaner(-300.0F, 0.0F), new Cleaner(-50.0F, -20.0F), new Cleaner(200.0F, 0.0F)});
            case "Binah":
                return new MonsterGroup(new AbstractMonster[]{new Binah(-50.0F, 0.0F)});
            case "Chesed":
                return new MonsterGroup(new AbstractMonster[]{new Chesed(-50.0F, 0.0F)});
            case "Hokma":
                return new MonsterGroup(new AbstractMonster[]{new Hokma(-50.0F, 0.0F)});
            case "Geburah":
                return new MonsterGroup(new AbstractMonster[]{new Geburah(-50.0F, 0.0F)});
            case "Hod":
                return new MonsterGroup(new AbstractMonster[]{new Hod(-50.0F, 0.0F)});
            case "Malkuth":
                return new MonsterGroup(new AbstractMonster[]{new Malkuth(-50.0F, 0.0F)});
            case "Netzach":
                return new MonsterGroup(new AbstractMonster[]{new Netzach(-50.0F, 0.0F)});
            case "Tiphereth":
                return new MonsterGroup(new AbstractMonster[]{new Tiphereth(-50.0F, 0.0F)});
            case "Yesod":
                return new MonsterGroup(new AbstractMonster[]{new Yesod(-50.0F, 0.0F)});
            case "Angela":
                return new MonsterGroup(new AbstractMonster[]{new Angela(-50.0F, 0.0F)});
        }
        return __result;
    }

    private static AbstractMonster getRandomFixer(){
        switch (AbstractDungeon.monsterRng.random(2)){
            case 0:
                LobotomyMod.usedFixer = 1;
                return new RedFixer(-50.0F, 0.0F);
            case 1:
                LobotomyMod.usedFixer = 2;
                return new BlackFixer(-50.0F, 0.0F);
            case 2:
                LobotomyMod.usedFixer = 3;
                return new WhiteFixer(-50.0F, 0.0F);
        }
        return new RedFixer(-50.0F, 0.0F);
    }

    private static AbstractMonster[] getRandomFixerGroup(){
        AbstractMonster m1 = null, m2 = null;
        switch (LobotomyMod.usedFixer){
            case 1:
                m1 = new BlackFixer(-150.0F, 0.0F);
                m2 = new WhiteFixer(100.0F, -20.0F);
                break;
            case 2:
                m1 = new RedFixer(-150.0F, 0.0F);
                m2 = new WhiteFixer(100.0F, -20.0F);
                break;
            case 3:
                m1 = new RedFixer(-150.0F, 0.0F);
                m2 = new BlackFixer(100.0F, -20.0F);
                break;
        }
        return new AbstractMonster[]{m1, m2};
    }
}
