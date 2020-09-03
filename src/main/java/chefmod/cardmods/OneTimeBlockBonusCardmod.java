package chefmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.Optional;

import static chefmod.ChefMod.makeID;

public class OneTimeBlockBonusCardmod extends AbstractChefCardmod {
    public static final String ID = makeID(OneTimeBlockBonusCardmod.class.getSimpleName());

    public OneTimeBlockBonusCardmod(int blockAmount) {
        block = blockAmount;
    }

    public static void add(AbstractCard card, int blockAmount) {
        Optional<AbstractChefCardmod> current = getForCard(card, ID);
        if (current.isPresent()) {
            current.get().block += blockAmount;
        } else {
            CardModifierManager.addModifier(card, new OneTimeBlockBonusCardmod(blockAmount));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new OneTimeBlockBonusCardmod(block);
    }

    @Override
    public float modifyBlock(float current, AbstractCard card) {
        return block + current;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }
}
