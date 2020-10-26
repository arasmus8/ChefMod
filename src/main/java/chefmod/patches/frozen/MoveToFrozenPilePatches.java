package chefmod.patches.frozen;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import chefmod.powers.TriggerOnFrozenPower;
import chefmod.relics.TriggerOnFrozenRelic;
import chefmod.vfx.FrozenCardVfx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

public class MoveToFrozenPilePatches {
    @SpirePatch(
            clz = Soul.class,
            method = "onToDeck",
            paramtypez = {AbstractCard.class, boolean.class, boolean.class}
    )
    public static class OnToDeckSoulsPatch {

        @SpireInsertPatch(
                locator = OnToDeckSoulsPatchLocator.class
        )
        public static void Insert(Soul _instance, AbstractCard card) {
            if (ChefMod.cardsToFreeze.contains(card)) {
                _instance.group = ChefMod.frozenPile;
                ChefMod.frozenThisCombat.add(card.makeSameInstanceOf());
                if (card instanceof AbstractChefCard) {
                    ((AbstractChefCard) card).frozen = true;
                    ((AbstractChefCard) card).triggerWhenFrozen();
                }
                AbstractDungeon.effectList.add(new FrozenCardVfx(card));

                AbstractDungeon.player.relics.stream()
                        .filter(r -> r instanceof TriggerOnFrozenRelic)
                        .map(r -> (TriggerOnFrozenRelic) r)
                        .forEach(r -> r.triggerOnFrozen(card));

                AbstractDungeon.player.powers.stream()
                        .filter(p -> p instanceof TriggerOnFrozenPower)
                        .map(p -> (TriggerOnFrozenPower) p)
                        .forEach(p -> p.triggerOnFrozen(card));
            }
        }

        private static class OnToDeckSoulsPatchLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(CardGroup.class, "addToTop");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Soul.class,
            method = "discard",
            paramtypez = { AbstractCard.class, boolean.class }
    )
    public static class DiscardSoulsPatch {

        @SpireInsertPatch(
                locator = DiscardLocator.class
        )
        public static void Insert(Soul _instance, AbstractCard card, boolean visualOnly) {
            if (ChefMod.cardsToFreeze.contains(card)) {
                _instance.group = ChefMod.frozenPile;
                ChefMod.frozenThisCombat.add(card.makeSameInstanceOf());
                if (card instanceof AbstractChefCard) {
                    ((AbstractChefCard) card).frozen = true;
                    ((AbstractChefCard) card).triggerWhenFrozen();
                }
                ChefMod.cardsToFreeze.remove(card);
                AbstractDungeon.effectList.add(new FrozenCardVfx(card));

                AbstractDungeon.player.relics.stream()
                        .filter(r -> r instanceof TriggerOnFrozenRelic)
                        .map(r -> (TriggerOnFrozenRelic) r)
                        .forEach(r -> r.triggerOnFrozen(card));

                AbstractDungeon.player.powers.stream()
                        .filter(p -> p instanceof TriggerOnFrozenPower)
                        .map(p -> (TriggerOnFrozenPower) p)
                        .forEach(p -> p.triggerOnFrozen(card));
            }
        }

        private static class DiscardLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(CardGroup.class, "addToTop");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }
}
