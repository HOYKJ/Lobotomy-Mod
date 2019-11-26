package lobotomyMod.patch.musicPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.Settings;

import java.util.HashMap;

@SpirePatch(cls="com.megacrit.cardcrawl.audio.SoundMaster", method="playA", paramtypes={"java.lang.String", "float"})
public class SoundMasterplayPatchA
{
    public static HashMap<String, Sfx> map = new HashMap<>();

    public static long Postfix(long res, SoundMaster _inst, String key, float pitchAdjust)
    {
        if (map.containsKey(key))
            return (map.get(key)).play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME, 1F + pitchAdjust, 0F);


        return 0L;
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
    }
}
