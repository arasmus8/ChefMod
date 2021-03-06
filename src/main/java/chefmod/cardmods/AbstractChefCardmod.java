package chefmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import chefmod.util.ActionUnit;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Optional;

import static chefmod.ChefMod.makeID;

public abstract class AbstractChefCardmod extends AbstractCardModifier implements ActionUnit {
    protected int damage = 0;
    protected int block = 0;

    public static Optional<AbstractChefCardmod> getForCard(AbstractCard card, String cardmodID) {
        ArrayList<AbstractCardModifier> modifiers = CardModifierManager.getModifiers(card, cardmodID);
        if (modifiers.size() > 0) {
            AbstractChefCardmod m = (AbstractChefCardmod) modifiers.get(0);
            return Optional.of(m);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public String identifier(AbstractCard card) {
        return makeID(this.getClass().getSimpleName());
    }

    @Override
    public AbstractCardModifier makeCopy() {
        try {
            AbstractChefCardmod copy = this.getClass().newInstance();
            copy.damage = damage;
            copy.block = block;
            return copy;
        } catch (IllegalAccessException | InstantiationException var2) {
            throw new RuntimeException("Failed to auto-generate makeCopy for card_modifier: " + identifier(null));
        }
    }

    @Override
    public DamageInfo makeDamageInfo(DamageInfo.DamageType type) {
        return new DamageInfo(AbstractDungeon.player, damage, type);
    }

    @Override
    public void dealDamage(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        qAction(new DamageAction(m, makeDamageInfo(DamageInfo.DamageType.NORMAL), fx));
    }

    @Override
    public void dealAoeDamage(AbstractGameAction.AttackEffect fx) {
        qAction(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(damage), DamageInfo.DamageType.NORMAL, fx));
    }

    @Override
    public void gainBlock() {
        qAction(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }
}
