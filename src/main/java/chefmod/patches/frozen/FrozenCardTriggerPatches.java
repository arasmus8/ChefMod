package chefmod.patches.frozen;

import chefmod.ChefMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class FrozenCardTriggerPatches {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnCards"
    )
    public static class TriggerAtTurnStartForFrozenPatch {
        public static void Postfix(AbstractPlayer _instance) {
            ChefMod.frozenPile.group.forEach(c -> {
                if (c != null) {
                    c.atTurnStart();
                }
            });
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "switchedStance"
    )
    public static class TriggerSwitchedStancePatch {
        public static void Postfix(AbstractPlayer _instance) {
            ChefMod.frozenPile.group.forEach(AbstractCard::switchedStance);
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "updateCardsOnDamage"
    )
    public static class TriggerCardsOnDamagePatch {
        public static void Postfix(AbstractPlayer _instance) {
            ChefMod.frozenPile.group.forEach(AbstractCard::tookDamage);
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "updateCardsOnDiscard"
    )
    public static class TriggerCardsOnDiscardPatch {
        public static void Postfix(AbstractPlayer _instance) {
            ChefMod.frozenPile.group.forEach(AbstractCard::didDiscard);
        }
    }
}
