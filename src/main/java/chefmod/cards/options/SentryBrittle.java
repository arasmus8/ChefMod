package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import chefmod.recipe.SentryBrittleRecipe;

import static chefmod.ChefMod.makeID;

public class SentryBrittle extends AbstractChefCard {
    public static String ID = makeID(SentryBrittle.class.getSimpleName());

    public SentryBrittle() {
        super(ID,
                -2,
                CardType.SKILL,
                CardRarity.SPECIAL,
                CardTarget.SELF
        );
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new SentryBrittleRecipe());
    }
}
