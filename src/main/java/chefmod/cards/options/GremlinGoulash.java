package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractOptionCard;
import chefmod.recipe.GremlinGoulashRecipe;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class GremlinGoulash extends AbstractOptionCard {
    public static String ID = makeID(GremlinGoulash.class.getSimpleName());

    public GremlinGoulash() {
        super(ID);
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new GremlinGoulashRecipe());
    }
}
