package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractOptionCard;
import chefmod.cards.attacks.NuggetStrike;
import chefmod.recipe.NeowNuggetsRecipe;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class NeowNuggets extends AbstractOptionCard {
    public static String ID = makeID(NeowNuggets.class.getSimpleName());

    public NeowNuggets() {
        super(ID);
        cardsToPreview = new NuggetStrike();
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new NeowNuggetsRecipe());
    }
}
