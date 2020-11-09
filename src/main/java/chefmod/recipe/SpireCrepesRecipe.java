package chefmod.recipe;

import chefmod.actions.FunctionalAction;
import chefmod.cardmods.PlayTwiceCardmod;
import chefmod.cards.options.SpireCrepes;
import chefmod.cards.skills.TodaysSpecial;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import static chefmod.ChefMod.makeID;

public class SpireCrepesRecipe extends AbstractRecipe {
    public static final String ID = makeID(SpireCrepesRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    private final AbstractCard todaysSpecial;

    public SpireCrepesRecipe() {
       tipHeader =  TEXT[0];
       tipBody = TEXT[1];
       ingredientCount = 2;
       card = new SpireCrepes();
       todaysSpecial = AbstractDungeon.player.hand.group.stream()
               .filter(c -> c.cardID.equals(TodaysSpecial.ID))
               .findFirst()
               .orElse(null);
    }

    @Override
    public void onActivate() {
        if (todaysSpecial != null) {
            todaysSpecial.superFlash();
            qAction(new FunctionalAction(firstUpdate -> {
                PlayTwiceCardmod.addToCard(todaysSpecial, true);
                return true;
            }));
        }
    }
}
