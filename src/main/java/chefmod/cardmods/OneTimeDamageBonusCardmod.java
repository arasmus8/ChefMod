package chefmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Optional;

import static chefmod.ChefMod.makeID;

public class OneTimeDamageBonusCardmod extends AbstractChefCardmod {
    public static final String ID = makeID(OneTimeDamageBonusCardmod.class.getSimpleName());

    public OneTimeDamageBonusCardmod(int damageAmount) {
        damage = damageAmount;
    }

    public static void add(AbstractCard card, int damageAmount) {
        Optional<AbstractChefCardmod> current = getForCard(card, ID);
        if (current.isPresent()) {
            current.get().damage += damageAmount;
        } else {
            CardModifierManager.addModifier(card, new OneTimeDamageBonusCardmod(damageAmount));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new OneTimeDamageBonusCardmod(damage);
    }

    @Override
    public float modifyDamage(float current, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return current + damage;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }
}
