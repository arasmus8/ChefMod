package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractOptionCard;
import chefmod.recipe.NemesisSouffleRecipe;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class NemesisSouffle extends AbstractOptionCard {
    public static String ID = makeID(NemesisSouffle.class.getSimpleName());

    public NemesisSouffle() {
        super(ID);
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new NemesisSouffleRecipe());
    }
}
