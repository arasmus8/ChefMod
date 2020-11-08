package chefmod.recipe;

import basemod.abstracts.CustomReward;
import chefmod.ChefMod;
import chefmod.patches.enums.RecipeRewardType;
import chefmod.util.TextureHelper;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Arrays;
import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;
import static chefmod.ChefMod.makeImagePath;

public class RecipeReward extends CustomReward {
    private static final String imagePath = makeImagePath("recipeReward.png");
    private static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(makeID(RecipeReward.class.getSimpleName()));
    public String recipeId;

    private static String buildRewardDescription(String recipeId) {
        String recipeName = UI_STRINGS.TEXT_DICT.getOrDefault(recipeId, TEXT[1]);
        return UI_STRINGS.TEXT[0] + recipeName;
    }

    public RecipeReward(String recipeId) {
        super(TextureHelper.getTexture(imagePath), buildRewardDescription(recipeId), RecipeRewardType.RECIPE_REWARD);
        this.recipeId = recipeId;
    }

    @Override
    public boolean claimReward() {
        ChefMod.recipeManager.unlock(recipeId);
        return true;
    }
}
