package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractOptionCard;
import chefmod.recipe.NobStewRecipe;

import static chefmod.ChefMod.makeID;

public class NobStew extends AbstractOptionCard {
    public static String ID = makeID(NobStew.class.getSimpleName());

    public NobStew() {
        super(ID);
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new NobStewRecipe());
    }
}
