package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;

import static chefmod.ChefMod.makeID;

public class FriedLagavulin extends AbstractChefCard {
    public static String ID = makeID(FriedLagavulin.class.getSimpleName());

    public FriedLagavulin() {
        super(ID,
                -2,
                CardType.SKILL,
                CardRarity.SPECIAL,
                CardTarget.SELF
        );
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new chefmod.recipe.FriedLagavulin());
    }
}
