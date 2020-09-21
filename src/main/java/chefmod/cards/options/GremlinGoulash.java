package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import chefmod.recipe.GremlinGoulashRecipe;

import static chefmod.ChefMod.makeID;

public class GremlinGoulash extends AbstractChefCard {
    public static String ID = makeID(GremlinGoulash.class.getSimpleName());

    public GremlinGoulash() {
        super(ID,
                -2,
                CardType.SKILL,
                CardRarity.SPECIAL,
                CardTarget.SELF
        );
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new GremlinGoulashRecipe());
    }
}
