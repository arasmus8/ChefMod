package chefmod.cardmods;

import com.megacrit.cardcrawl.cards.AbstractCard;

import static chefmod.ChefMod.makeID;

public class NeowNuggetsCardmod extends AbstractChefCardmod {
    public static final String ID = makeID(NeowNuggetsCardmod.class.getSimpleName());

    public NeowNuggetsCardmod() {
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.exhaust = true;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL Exhaust";
    }
}
