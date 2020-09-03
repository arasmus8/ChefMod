package chefmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Optional;

import static chefmod.ChefMod.makeID;

public class PermanentDamageBonusCardmod extends AbstractChefCardmod {
    public static final String ID = makeID(PermanentDamageBonusCardmod.class.getSimpleName());

    public PermanentDamageBonusCardmod(int damageAmount) {
        damage = damageAmount;
    }

    public static void add(AbstractCard card, int damageAmount) {
        Optional<AbstractChefCardmod> current = getForCard(card, ID);
        if (current.isPresent()) {
            current.get().damage += damageAmount;
        } else {
            CardModifierManager.addModifier(card, new PermanentDamageBonusCardmod(damageAmount));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PermanentDamageBonusCardmod(damage);
    }

    @Override
    public float modifyDamage(float current, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + current;
    }
}
