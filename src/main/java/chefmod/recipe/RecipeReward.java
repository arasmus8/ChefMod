package chefmod.recipe;

import basemod.abstracts.CustomReward;
import chefmod.patches.enums.RecipeRewardType;
import chefmod.util.TextureHelper;

import static chefmod.ChefMod.makeImagePath;

public class RecipeReward extends CustomReward {
    private static final String imagePath = makeImagePath("recipeReward.png");

    public RecipeReward(String recipeId) {
        super(TextureHelper.getTexture(imagePath), "", RecipeRewardType.RECIPE_REWARD);
    }

    @Override
    public boolean claimReward() {
        return false;
    }
}
