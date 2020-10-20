package chefmod.recipe;

import chefmod.ChefMod;
import chefmod.actions.FunctionalAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

import static chefmod.ChefMod.makeID;

public class EscargotRecipe extends AbstractRecipe {
    public static final String ID = makeID(EscargotRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    private final boolean upgradeNonFrozen;

    public EscargotRecipe(boolean upgradeNonFrozen, AbstractCard card) {
        this.upgradeNonFrozen = upgradeNonFrozen;
        tipHeader = TEXT[0];
        tipBody = upgradeNonFrozen ? TEXT[2] : TEXT[1];
        ingredientCount = 2;
        this.card = card.makeStatEquivalentCopy();
    }

    @Override
    public void onActivate() {
        Function<Boolean, Boolean> upgradeAction = firstUpdate -> {
            Consumer<AbstractCard> upgradeCard = c -> {
                if (c.canUpgrade()) {
                    c.upgrade();
                    c.applyPowers();
                }
            };
            Consumer<AbstractCard> upgradeCardAndFlash = c -> {
                if (c.canUpgrade()) {
                    c.upgrade();
                    c.superFlash();
                    c.applyPowers();
                }
            };
            Consumer<CardGroup> upgradeCardsInGroup = g -> g.group.forEach(g.type == CardGroup.CardGroupType.HAND ? upgradeCardAndFlash : upgradeCard);

            ArrayList<CardGroup> groupsToUpgrade = new ArrayList<>();
            AbstractPlayer p = AbstractDungeon.player;
            groupsToUpgrade.add(ChefMod.frozenPile);
            if (upgradeNonFrozen) {
                groupsToUpgrade.add(p.hand);
                groupsToUpgrade.add(p.drawPile);
                groupsToUpgrade.add(p.discardPile);
                groupsToUpgrade.add(p.exhaustPile);
            }

            groupsToUpgrade.forEach(upgradeCardsInGroup);

            return true;
        };

        qAction(new FunctionalAction(upgradeAction));
    }
}
