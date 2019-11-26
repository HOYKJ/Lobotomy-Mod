package lobotomyMod.patch.musicPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.Settings;

import java.util.HashMap;

@SpirePatch(cls="com.megacrit.cardcrawl.audio.SoundMaster", method="play", paramtypes={"java.lang.String", "boolean"})
public class SoundMasterplayPatch
{
    public static HashMap<String, Sfx> map = new HashMap<>();

    public static long Postfix(long res, SoundMaster _inst, String key, boolean useBgmVolume)
    {
        if (map.containsKey(key)) {
            if (useBgmVolume)
                return (map.get(key)).play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME);

            return (map.get(key)).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME);
        }
        return res;
    }

    private static Sfx load(String filename)
    {
        return new Sfx("audio/sound/" + filename, false);
    }

    static
    {
        map.put("movement_clap", load("Sym_movment_0_clap.ogg"));
        map.put("movement_1", load("Sym_movment_1_mov1.ogg"));
        map.put("movement_2", load("Sym_movment_2_mov2.ogg"));
        map.put("movement_3", load("Sym_movment_3_mov3.ogg"));
        map.put("movement_4", load("Sym_movment_4_mov4.ogg"));
        map.put("movement_final", load("Sym_movment_5_finale.ogg"));
        map.put("DoorClick", load("DoorClick.ogg"));
        map.put("DoorOn", load("DoorOn.ogg"));
        map.put("MHz", load("1.76_Defult.ogg"));
        map.put("MHz2", load("1.76_Defult2.ogg"));
        map.put("Lucifer_Bell0", load("Lucifer_Bell0.ogg"));
        map.put("Freischutz_Shot", load("Freischutz_Shot.ogg"));
        map.put("YinYang_Merge", load("YinYang_Merge.ogg"));
        map.put("YinYang_Dragon", load("YinYang_Dragon.ogg"));
        map.put("Train_End", load("Train_End.ogg"));
        map.put("Train_Move1", load("Train_Move1.ogg"));
        map.put("Train_Sell", load("Train_Sell.ogg"));
        map.put("WarpClock_Skill_Start", load("WarpClock_Skill_Start.ogg"));
        map.put("RabbitTeam_Alert", load("RabbitTeam_Alert.ogg"));

        map.put("Machine_Start", load("Machine_Start.ogg"));
        map.put("Machine_End", load("Machine_End.ogg"));
        map.put("Bug_Start", load("Bug_Start.ogg"));
        map.put("Bug_End", load("Bug_End.ogg"));
        map.put("OutterGod_Start", load("OutterGod_Start.ogg"));
        map.put("OutterGod_End", load("OutterGod_End.ogg"));
        map.put("Circus_Start", load("Circus_Start.ogg"));
        map.put("Circus_End", load("Circus_End.ogg"));
        map.put("Scavenger_Start", load("Scavenger_Start.ogg"));
        map.put("Scavenger_End", load("Scavenger_End.ogg"));
        map.put("WhiteOrdeal_Start", load("WhiteOrdeal_Start.ogg"));
        map.put("WhiteOrdeal_End", load("WhiteOrdeal_End.ogg"));
    }
}
