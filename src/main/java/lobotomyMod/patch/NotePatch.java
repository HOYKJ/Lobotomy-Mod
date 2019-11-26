package lobotomyMod.patch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
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
            log[0] = Gdx.files.internal("lobotomyMod" + File.separator + "changelog" + File.separator + "changes.txt");
        }
    }
}
