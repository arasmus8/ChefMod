package chefmod.cardmods;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Optional;

import static chefmod.ChefMod.makeID;

public class PermanentDamageBonusCardmod extends AbstractChefCardmod {
    public static final String CARDMOD_ID = makeID(PermanentDamageBonusCardmod.class.getSimpleName());

    public PermanentDamageBonusCardmod(int damageAmount) {
        ID = CARDMOD_ID;
        damage = damageAmount;
    }

    public static void add(AbstractCard card, int damageAmount) {
        Optional<AbstractChefCardmod> current = getForCard(card, CARDMOD_ID);
        if (current.isPresent()) {
            current.get().damage += damageAmount;
        } else {
            CardModifierManager.addModifier(card, new PermanentDamageBonusCardmod(damageAmount));
        }
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public float modifyDamage(float current, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + current;
    }
}
