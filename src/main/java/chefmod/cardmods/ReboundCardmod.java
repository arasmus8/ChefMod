package chefmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static chefmod.ChefMod.makeID;

public class ReboundCardmod extends AbstractChefCardmod {
    public static String ID = makeID(ReboundCardmod.class.getSimpleName());

    public ReboundCardmod() {
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (card.type != AbstractCard.CardType.POWER) {
            action.reboundCard = true;
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ReboundCardmod();
    }
}
