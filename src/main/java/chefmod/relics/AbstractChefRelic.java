package chefmod.relics;

import basemod.abstracts.CustomRelic;
import chefmod.util.ActionUnit;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static chefmod.ChefMod.*;

public abstract class AbstractChefRelic extends CustomRelic implements ActionUnit {
    public AbstractCard.CardColor color;

    public AbstractChefRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        this(setId, tier, sfx, null);
    }

    public AbstractChefRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx, AbstractCard.CardColor color) {
        super(setId, "", tier, sfx);

        this.color = color;

        String imgName = getBaseImagePath();
        System.out.println(imgName);

        loadImages((getModID() + "Resources/"), imgName);
        if (img == null || outlineImg == null) {
            loadImages("", "default.png");
        }
    }

    protected String getBaseImagePath() {
        String id = relicId.replaceFirst(getModID() + ":", "");
        return id + ".png";
    }

    protected void loadImages(String basePath, String imgName) {
        img = ImageMaster.loadImage(makeRelicPath(imgName));
        outlineImg = ImageMaster.loadImage(makeRelicOutlinePath(imgName));
    }

    protected int getDamage() {
        return 0;
    }

    protected int getBlock() {
        return 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public DamageInfo makeDamageInfo(DamageInfo.DamageType type) {
        return new DamageInfo(AbstractDungeon.player, getDamage(), type);
    }

    @Override
    public void dealDamage(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        qAction(new DamageAction(m, makeDamageInfo(DamageInfo.DamageType.NORMAL), fx));
    }

    @Override
    public void dealAoeDamage(AbstractGameAction.AttackEffect fx) {
        qAction(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(getDamage()), DamageInfo.DamageType.NORMAL, fx));
    }

    @Override
    public void gainBlock() {
        qAction(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, getBlock()));
    }
}
