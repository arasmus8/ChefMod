package chefmod.recipe;

import chefmod.actions.HandSelectFunctionalAction;
import chefmod.cardmods.PlayTwiceCardmod;
import chefmod.cards.options.GremlinGoulash;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import static chefmod.ChefMod.makeID;

public class GremlinGoulashRecipe extends AbstractRecipe {
    public static final String ID = makeID(GremlinGoulashRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    public GremlinGoulashRecipe() {
        tipHeader = TEXT[0];
        tipBody = TEXT[1];
        ingredientCount = 4;
        card = new GremlinGoulash();
    }

    @Override
    public void onActivate() {
        qAction(new HandSelectFunctionalAction(selectedCards -> selectedCards.forEach(c -> {
            c.superFlash();
            PlayTwiceCardmod.addToCard(c, true);
        }), TEXT[2]));
    }
}
