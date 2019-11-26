package lobotomyMod.reward;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import lobotomyMod.LobotomyMod;
import lobotomyMod.character.Angela;
import lobotomyMod.helper.LobotomyUtils;
import lobotomyMod.patch.RewardItemEnum;
import lobotomyMod.ui.LobotomyFtue;

import java.util.ArrayList;

/**
 * @author hoykj
 */
public class LobPoint extends CustomReward {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RewardItem_Lob");
    public static final String[] TEXT = uiStrings.TEXT;
    private ArrayList<PowerTip> tips = new ArrayList<>();

    public LobPoint()
    {
        super(ImageMaster.loadImage("images/ui/run_mods/heirloom.png"), TEXT[3], RewardItemEnum.SPECIAL);
        Angela.departments[18] += 2;
    }

    public boolean claimReward(){
        if(Angela.departments[18] > 0){
            if(LobotomyMod.activeTutorials[4]) {
                AbstractDungeon.ftue = new LobotomyFtue(4);
                LobotomyMod.activeTutorials[4] = false;
                return false;
            }
            LobotomyUtils.hireDepartment();
        }
        return false;
    }

    @Override
    public void update() {
        this.text = TEXT[3] + ": " + Angela.departments[18];
        super.update();
        this.tips.clear();
        if (this.hb.hovered) {
            this.tips.add(new PowerTip(TEXT[3], TEXT[4]));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (!this.tips.isEmpty()) {
            TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0F + 20.0F * Settings.scale,
                    this.hb.cY + TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
        }
    }
}
