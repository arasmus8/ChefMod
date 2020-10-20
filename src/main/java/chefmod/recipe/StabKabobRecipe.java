package chefmod.recipe;

import chefmod.cards.options.StabKabob;
import chefmod.powers.StabKabobPower;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import static chefmod.ChefMod.makeID;

public class StabKabobRecipe extends AbstractRecipe {
    public static final String ID = makeID(StabKabobRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    public StabKabobRecipe() {
        tipHeader = TEXT[0];
        tipBody = TEXT[1];
        ingredientCount = 4;
        card = new StabKabob();
    }

    @Override
    public void onActivate() {
        applyToSelf(new StabKabobPower(AbstractDungeon.player));
    }
}
