package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractOptionCard;
import chefmod.recipe.GiantMarbleCakeRecipe;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class GiantMarbleCake extends AbstractOptionCard {
    public static String ID = makeID(GiantMarbleCake.class.getSimpleName());

    public GiantMarbleCake() {
        super(ID);
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new GiantMarbleCakeRecipe());
    }
}
