package lobotomyMod.relic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import lobotomyMod.card.uncommonCard.Yin;
import lobotomyMod.character.LobotomyHandler;
import lobotomyMod.monster.YangMonster;
import lobotomyMod.power.YangHealPower;
import lobotomyMod.power.YinYangPower;
import lobotomyMod.relic.toolAbnormality.AbstractLobotomyAbnRelic;

/**
 * @author hoykj
 */
public class Yang extends AbstractLobotomyAbnRelic {
    public static final String ID = "Yang_Lob";
    public boolean active;

    public Yang()
    {
        super("Yang_Lob", RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.active = true;
        this.img = ImageMaster.loadImage(LobotomyHandler.lobotomyRelicImage("Yang_1"));
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        this.active = false;
        this.img = ImageMaster.loadImage(LobotomyHandler.lobotomyRelicImage("Yang_Lob"));
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        if(this.active) {
            if(AbstractDungeon.player.masterDeck.findCardById(Yin.ID) == null){
                return;
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, 1), 1));
        }
    }

    public void change(){
        AbstractMonster m = new YangMonster(-Settings.WIDTH * 0.8F, 0.0F);
        AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(m, false));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new YinYangPower(m)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new YangHealPower(m, 3), 3));
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + (CogitoBucket.level[102] < 1? Yin.NAME: Yin.EXTENDED_DESCRIPTION[0]) + this.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy() {
        return new Yang();
    }
}
