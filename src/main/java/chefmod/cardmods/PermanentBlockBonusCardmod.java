package chefmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.Optional;

import static chefmod.ChefMod.makeID;

public class PermanentBlockBonusCardmod extends AbstractChefCardmod {
    public static final String ID = makeID(PermanentBlockBonusCardmod.class.getSimpleName());

    public PermanentBlockBonusCardmod(int blockAmount) {
        block = blockAmount;
    }

    public static void add(AbstractCard card, int blockAmount) {
        Optional<AbstractChefCardmod> current = getForCard(card, ID);
        if (current.isPresent()) {
            current.get().block += blockAmount;
        } else {
            CardModifierManager.addModifier(card, new PermanentBlockBonusCardmod(blockAmount));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PermanentBlockBonusCardmod(block);
    }

    @Override
    public float modifyBlock(float current, AbstractCard card) {
        return block + current;
    }
}
