package chefmod.cardmods;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static chefmod.ChefMod.makeID;

public class TrustyPotBlock extends AbstractChefCardmod {
    public TrustyPotBlock (int blockAmount) {
        ID = makeID(TrustyPotBlock.class.getSimpleName());
        block = blockAmount;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        gainBlock();
    }
}
