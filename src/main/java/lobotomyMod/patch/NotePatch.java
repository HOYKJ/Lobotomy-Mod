package lobotomyMod.patch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.mainMenu.PatchNotesScreen;

import java.io.File;

/**
 * @author hoykj
 */
public class NotePatch {
    @SpirePatch(cls="com.megacrit.cardcrawl.screens.mainMenu.PatchNotesScreen", method="open")
    public static class CustomPatchNote
    {
        @SpireInsertPatch(rloc=13, localvars={"log"})
        public static void Insert(PatchNotesScreen _inst, @ByRef FileHandle[] log)
        {
            String str = "eng";
            if(Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT){
                str = "zhs";
            }
            log[0] = Gdx.files.internal("lobotomyMod" + File.separator + "changelog" + File.separator + str + File.separator + "changes.txt");
        }
    }
}
