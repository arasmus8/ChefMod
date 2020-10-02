package chefmod.recipe;

import chefmod.powers.NobStewPower;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import static chefmod.ChefMod.makeID;

public class NobStewRecipe extends AbstractRecipe {
    public static final String ID = makeID(NobStewRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    public NobStewRecipe() {
        tipHeader = TEXT[0];
        tipBody = TEXT[1];
        ingredientCount = 3;
    }

    @Override
    public void onActivate() {
        applyToSelf(new NobStewPower(AbstractDungeon.player, 2));
    }
}