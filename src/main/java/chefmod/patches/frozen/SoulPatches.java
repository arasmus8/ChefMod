package chefmod.patches.frozen;

import chefmod.ChefMod;
import chefmod.vfx.FrozenCardVfx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

public class SoulPatches {
    @SpirePatch(
            clz = Soul.class,
            method = "onToDeck",
            paramtypez = { AbstractCard.class, boolean.class, boolean.class }
    )
    public static class OnToBottomOfDeckSoulsPatch {

        @SpireInsertPatch(
                locator = OnToBottomOfDeckLocator.class
        )
        public static void Insert(Soul _instance, AbstractCard card) {
            if (ChefMod.cardsToFreeze.contains(card)) {
                _instance.group = ChefMod.frozenPile;
            }
        }

        private static class OnToBottomOfDeckLocator extends SpireInsertLocator {
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
                ChefMod.cardsToFreeze.remove(card);
                AbstractDungeon.effectList.add(new FrozenCardVfx(card));
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
