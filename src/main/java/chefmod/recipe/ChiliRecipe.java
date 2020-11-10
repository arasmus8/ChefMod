package chefmod.recipe;

import chefmod.ChefMod;
import chefmod.actions.FunctionalAction;
import chefmod.actions.GridSelectAndPerformAction;
import chefmod.cardmods.ChiliCardmod;
import chefmod.util.VfxMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Optional;

import static chefmod.ChefMod.makeID;

public class ChiliRecipe extends AbstractRecipe {
    public static final String ID = makeID(ChiliRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    private final int damage;

    public ChiliRecipe(int damage, AbstractCard card) {
        this.damage = damage;
        tipHeader = TEXT[0];
        tipBody = TEXT[1] + damage + TEXT[2];
        ingredientCount = 3;
        this.card = card.makeStatEquivalentCopy();
    }

    @Override
    public void onActivate() {
        CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        cardGroup.group.addAll(AbstractDungeon.player.drawPile.group);
        cardGroup.group.addAll(AbstractDungeon.player.discardPile.group);
        cardGroup.group.addAll(AbstractDungeon.player.hand.group);
        cardGroup.group.addAll(ChefMod.frozenPile.group);
        Optional.ofNullable(AbstractDungeon.player.cardInUse).ifPresent(cardGroup::addToBottom);
        cardGroup.sortByRarity(true);
        qAction(new GridSelectAndPerformAction(1,
                false,
                TEXT[3] + damage + TEXT[2],
                cardGroup,
                c -> c.type == AbstractCard.CardType.SKILL,
                new FunctionalAction(firstUpdate -> {
                    GridSelectAndPerformAction.selectedCards.forEach(c -> ChiliCardmod.addToCard(c, damage));
                    return true;
                })
        ));
        super.renderFood(VfxMaster.CHILI);
    }
}
