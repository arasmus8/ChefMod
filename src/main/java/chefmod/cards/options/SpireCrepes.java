package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractOptionCard;
import chefmod.cards.attacks.NuggetStrike;
import chefmod.cards.skills.TodaysSpecial;
import chefmod.recipe.NeowNuggetsRecipe;
import chefmod.recipe.SpireCrepesRecipe;

import static chefmod.ChefMod.makeID;

public class SpireCrepes extends AbstractOptionCard {
    public static String ID = makeID(SpireCrepes.class.getSimpleName());

    public SpireCrepes() {
        super(ID);
        cardsToPreview = new TodaysSpecial();
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new SpireCrepesRecipe());
    }
}
