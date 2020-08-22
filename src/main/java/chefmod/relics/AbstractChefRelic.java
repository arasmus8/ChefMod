package chefmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static chefmod.ChefMod.*;

public abstract class AbstractChefRelic extends CustomRelic {
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

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
