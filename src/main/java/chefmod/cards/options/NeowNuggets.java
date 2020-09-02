package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;

import static chefmod.ChefMod.makeID;

public class NeowNuggets extends AbstractChefCard {
    public static String ID = makeID(NeowNuggets.class.getSimpleName());

    public NeowNuggets() {
        super(ID,
                -2,
                CardType.SKILL,
                CardRarity.SPECIAL,
                CardTarget.SELF
        );
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new chefmod.recipe.NeowNuggets());
    }
}
