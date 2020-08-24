package chefmod.patches.frozen;

import chefmod.ChefMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.Soul;
import javassist.CtBehavior;

public class SoulPatches {
    @SpirePatch(
            clz = Soul.class,
            method = "onToBottomOfDeck"
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
                Matcher matcher = new Matcher.MethodCallMatcher(CardGroup.class, "addToBottom");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }
}
