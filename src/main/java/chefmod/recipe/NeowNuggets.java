package chefmod.recipe;

import basemod.helpers.CardModifierManager;
import chefmod.cardmods.NeowNuggetsCardmod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class NeowNuggets extends AbstractRecipe {
    private void applyToGroup(CardGroup cg) {
        cg.group.stream()
                .filter(c -> c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.hasTag(AbstractCard.CardTags.STARTER_STRIKE))
                .forEach(c -> CardModifierManager.addModifier(c, new NeowNuggetsCardmod()));
    }
    @Override
    public void onActivate() {
        qAction(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractPlayer p = AbstractDungeon.player;
                CardGroup cg = p.hand;
                applyToGroup(cg);
                cg = p.discardPile;
                applyToGroup(cg);
                cg = p.exhaustPile;
                applyToGroup(cg);
            }
        });
    }
}
