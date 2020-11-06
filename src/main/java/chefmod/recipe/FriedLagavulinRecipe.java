package chefmod.recipe;

import chefmod.cards.options.FriedLagavulin;
import chefmod.powers.FriedLagavulinPower;
import chefmod.util.VfxMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import static chefmod.ChefMod.makeID;

public class FriedLagavulinRecipe extends AbstractRecipe {
    public static final String ID = makeID(FriedLagavulinRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    public FriedLagavulinRecipe() {
        tipHeader = TEXT[0];
        tipBody = TEXT[1];
        ingredientCount = 3;
        card = new FriedLagavulin();
    }

    @Override
    public void onActivate() {
        applyToSelf(new FriedLagavulinPower(AbstractDungeon.player));
        super.renderFood(VfxMaster.FRIED_LAGAVULIN);
    }
}
