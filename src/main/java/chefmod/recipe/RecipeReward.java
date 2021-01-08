package chefmod.recipe;

import basemod.abstracts.CustomReward;
import basemod.helpers.CardPowerTip;
import chefmod.ChefMod;
import chefmod.patches.enums.RecipeRewardType;
import chefmod.util.TextureHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

import static chefmod.ChefMod.makeID;
import static chefmod.ChefMod.makeImagePath;

public class RecipeReward extends CustomReward {
    private static final String imagePath = makeImagePath("recipeReward.png");
    private final float offsetX;
    private static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(makeID(RecipeReward.class.getSimpleName()));
    public String recipeId;
    private ArrayList<PowerTip> tips;

    private static String buildRewardDescription(String recipeId) {
        String recipeName = UI_STRINGS.TEXT_DICT.getOrDefault(recipeId, TEXT[1]);
        return UI_STRINGS.TEXT[0] + recipeName;
    }

    public RecipeReward(String recipeId) {
        super(TextureHelper.getTexture(imagePath), buildRewardDescription(recipeId), RecipeRewardType.RECIPE_REWARD);
        this.recipeId = recipeId;
        tips = new ArrayList<>(1);
        AbstractCard previewCard = RecipeManager.cardFromId(recipeId);
        offsetX = previewCard.hb.width + 50f * Settings.scale;
        tips.add(new CardPowerTip(previewCard));
    }

    @Override
    public boolean claimReward() {
        ChefMod.recipeManager.unlock(recipeId);
        return true;
    }

    @Override
    public void update() {
        super.update();
        if (hb.hovered) {
            TipHelper.queuePowerTips(hb.x - offsetX, hb.y + hb.height, tips);
        }
    }
}
