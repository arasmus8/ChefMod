package chefmod.recipe;

import chefmod.actions.FunctionalAction;
import chefmod.cardmods.PermanentDamageBonusCardmod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import static chefmod.ChefMod.makeID;

public class GiantMarbleCakeRecipe extends AbstractRecipe {
    public static final String ID = makeID(GiantMarbleCakeRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    public GiantMarbleCakeRecipe() {
        tipHeader = TEXT[0];
        tipBody = TEXT[1];
        ingredientCount = 4;
    }

    @Override
    public void onActivate() {
        qAction(new DrawCardAction(AbstractDungeon.player, 2));
        qAction(new FunctionalAction(firstUpdate -> {
            AbstractDungeon.player.hand.getCardsOfType(AbstractCard.CardType.ATTACK)
                    .group.forEach(c -> PermanentDamageBonusCardmod.add(c, 5));
            return true;
        }));
    }
}
