package chefmod.recipe;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.powers.PhantasmalPower;

import static chefmod.ChefMod.makeID;

public class SushiPlatterRecipe extends AbstractRecipe {
    public static final String ID = makeID(SushiPlatterRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    private final boolean nextTurnAsWell;

    public SushiPlatterRecipe(int ingredientCount, boolean nextTurnAsWell) {
        tipHeader = TEXT[0];
        tipBody = nextTurnAsWell ? TEXT[2] : TEXT[1];
        this.ingredientCount = ingredientCount;
        this.nextTurnAsWell = nextTurnAsWell;
    }

    @Override
    public void onActivate() {
        applyToSelf(new DoubleDamagePower(AbstractDungeon.player, 1, false));
        if (nextTurnAsWell) {
            applyToSelf(new PhantasmalPower(AbstractDungeon.player, 1));
        }
    }
}
