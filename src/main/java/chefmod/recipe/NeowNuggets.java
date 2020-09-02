package chefmod.recipe;

import basemod.helpers.CardModifierManager;
import chefmod.ChefMod;
import chefmod.cardmods.NeowNuggetsCardmod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import static chefmod.ChefMod.makeID;

public class NeowNuggets extends AbstractRecipe {
    public static final String ID = makeID(NeowNuggets.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    public NeowNuggets() {
        tipHeader = TEXT[0];
        tipBody = TEXT[1];
        ingredientCount = 2;
    }

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
                cg = p.drawPile;
                applyToGroup(cg);
                cg = p.discardPile;
                applyToGroup(cg);
                cg = p.exhaustPile;
                applyToGroup(cg);
                cg = ChefMod.frozenPile;
                applyToGroup(cg);
                AbstractCard cardInPlay = p.cardInUse;
                if (cardInPlay.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || cardInPlay.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                    CardModifierManager.addModifier(cardInPlay, new NeowNuggetsCardmod());
                }
            }
        });
    }
}
