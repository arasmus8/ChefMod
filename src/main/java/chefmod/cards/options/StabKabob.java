package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import chefmod.recipe.FriedLagavulinRecipe;
import chefmod.recipe.StabKabobRecipe;

import static chefmod.ChefMod.makeID;

public class StabKabob extends AbstractChefCard {
    public static String ID = makeID(StabKabob.class.getSimpleName());

    public StabKabob() {
        super(ID,
                -2,
                CardType.SKILL,
                CardRarity.SPECIAL,
                CardTarget.SELF
        );
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new StabKabobRecipe());
    }
}
