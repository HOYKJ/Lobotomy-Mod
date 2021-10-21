package lobotomyMod.relic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.character.LobotomyHandler;

/**
 * @author hoykj
 */
public abstract class AbstractLobotomyRelic extends CustomRelic {
    private boolean RclickStart;
    private boolean Rclick;
    public AbstractRelic.RelicTier hideTier;

    public AbstractLobotomyRelic(final String id, final AbstractRelic.RelicTier tier, final AbstractRelic.LandingSound landingSound){
        super(id, ImageMaster.loadImage(LobotomyHandler.lobotomyRelicImage(id)),ImageMaster.loadImage(LobotomyHandler.lobotomyRelicOutlineImage(id)), tier, landingSound);
        this.hideTier = null;
        this.Rclick=false;
        this.RclickStart=false;
    }

    public AbstractLobotomyRelic(final String id, final AbstractRelic.RelicTier tier, final AbstractRelic.RelicTier hideTier, final AbstractRelic.LandingSound landingSound){
        super(id, ImageMaster.loadImage(LobotomyHandler.lobotomyRelicImage(id)),ImageMaster.loadImage(LobotomyHandler.lobotomyRelicOutlineImage(id)), tier, landingSound);
        this.hideTier = hideTier;
        this.Rclick=false;
        this.RclickStart=false;
    }

    protected void onRightClick(){

    }

    @Override
    public void update() {
        super.update();
        if(this.RclickStart && InputHelper.justReleasedClickRight) {
            if(this.hb.hovered) {
                this.Rclick=true;
            }
            this.RclickStart=false;
        }
        if((this.isObtained) && (this.hb != null) && ((this.hb.hovered) && (InputHelper.justClickedRight))) {
            this.RclickStart=true;
        }
        if((this.Rclick)){
            this.Rclick=false;
            this.onRightClick();
        }
    }
}
