package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractOptionCard;
import chefmod.recipe.SentryBrittleRecipe;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class SentryBrittle extends AbstractOptionCard {
    public static String ID = makeID(SentryBrittle.class.getSimpleName());

    public SentryBrittle() {
        super(ID);
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new SentryBrittleRecipe());
    }
}
