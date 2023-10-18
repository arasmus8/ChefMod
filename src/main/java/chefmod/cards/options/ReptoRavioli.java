package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractOptionCard;
import chefmod.cards.attacks.ReptoDagger;
import chefmod.recipe.ReptoRavioliRecipe;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class ReptoRavioli extends AbstractOptionCard {
    public static String ID = makeID(ReptoRavioli.class.getSimpleName());

    public ReptoRavioli() {
        super(ID);
        cardsToPreview = new ReptoDagger();
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new ReptoRavioliRecipe());
    }
}
