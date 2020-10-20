package chefmod.recipe;

import chefmod.cards.attacks.ReptoDagger;
import chefmod.cards.options.ReptoRavioli;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import static chefmod.ChefMod.makeID;

public class ReptoRavioliRecipe extends AbstractRecipe {
    public static final String ID = makeID(ReptoRavioliRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    public ReptoRavioliRecipe() {
        tipHeader = TEXT[0];
        tipBody = TEXT[1];
        ingredientCount = 4;
        card = new ReptoRavioli();
    }

    @Override
    public void onActivate() {
        makeInHand(new ReptoDagger());
    }
}
