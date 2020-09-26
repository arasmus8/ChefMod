package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractOptionCard;
import chefmod.recipe.StabKabobRecipe;

import static chefmod.ChefMod.makeID;

public class StabKabob extends AbstractOptionCard {
    public static String ID = makeID(StabKabob.class.getSimpleName());

    public StabKabob() {
        super(ID);
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new StabKabobRecipe());
    }
}
