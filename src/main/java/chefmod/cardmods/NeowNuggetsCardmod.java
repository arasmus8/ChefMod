package chefmod.cardmods;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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
    public float modifyDamageFinal(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage * 2f;
    }

    @Override
    public float modifyBlockFinal(float block, AbstractCard card) {
        return block * 2f;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL Exhaust";
    }
}
