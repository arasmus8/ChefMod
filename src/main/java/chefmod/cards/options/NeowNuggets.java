package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractOptionCard;
import chefmod.recipe.NeowNuggetsRecipe;

import static chefmod.ChefMod.makeID;

public class NeowNuggets extends AbstractOptionCard {
    public static String ID = makeID(NeowNuggets.class.getSimpleName());

    public NeowNuggets() {
        super(ID);
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new NeowNuggetsRecipe());
    }
}
