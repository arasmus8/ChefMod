package chefmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Optional;

import static chefmod.ChefMod.makeID;

public class ChiliCardmod extends AbstractChefCardmod {
    public static final String ID = makeID(ChiliCardmod.class.getSimpleName());

    public ChiliCardmod(int damage) {
        this.damage = damage;
    }

    public static void addToCard(AbstractCard card, int damageAmount) {
        Optional<AbstractChefCardmod> current = getForCard(card, ID);
        if (current.isPresent()) {
            current.get().damage += damageAmount;
        } else {
            CardModifierManager.addModifier(card, new ChiliCardmod(damageAmount));
        }
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        qAction(new DamageAllEnemiesAction(AbstractDungeon.player,
                DamageInfo.createDamageMatrix(damage),
                DamageInfo.DamageType.THORNS,
                AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL Deal " + damage + " Damage to ALL Enemies.";
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ChiliCardmod(damage);
    }
}
