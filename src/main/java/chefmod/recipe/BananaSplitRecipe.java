package chefmod.recipe;

import chefmod.ChefMod;
import chefmod.powers.HungerDamageUpPower;
import chefmod.util.VfxMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import static chefmod.ChefMod.makeID;

public class BananaSplitRecipe extends AbstractRecipe {
    public static final String ID = makeID(BananaSplitRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    private final int damageBonus;
    private final boolean replayRecipe;

    public BananaSplitRecipe(int damageBonus, boolean replayRecipe, AbstractCard card) {
        tipHeader = TEXT[0];
        tipBody = TEXT[1] + damageBonus + (replayRecipe ? (TEXT[2] + TEXT[3]) : TEXT[2]);
        this.damageBonus = damageBonus;
        this.ingredientCount = 2;
        this.replayRecipe = replayRecipe;
        this.card = card.makeStatEquivalentCopy();
    }

    @Override
    public void onActivate() {
        applyToSelf(new HungerDamageUpPower(AbstractDungeon.player, damageBonus));
        if (replayRecipe) {
            ChefMod.recipeManager.startRecipe(new BananaSplitRecipe(damageBonus, true, card));
        }
        super.renderFood(VfxMaster.BANANA_SPLIT);
    }
}
