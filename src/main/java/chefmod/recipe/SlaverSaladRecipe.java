package chefmod.recipe;

import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.cards.colorless.Blind;
import com.megacrit.cardcrawl.cards.colorless.Trip;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import static chefmod.ChefMod.makeID;

public class SlaverSaladRecipe extends AbstractRecipe {
    public static final String ID = makeID(SlaverSaladRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    public SlaverSaladRecipe() {
        tipHeader = TEXT[0];
        tipBody = TEXT[1];
        ingredientCount = 4;
    }

    @Override
    public void onActivate() {
        makeInHand(new BandageUp());
        makeInHand(new Blind());
        makeInHand(new Trip());
    }
}
