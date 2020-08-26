package chefmod.patches.frozen;

import basemod.ReflectionHacks;
import chefmod.ChefMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import javassist.CtBehavior;

public class ExhaustPileViewScreenPatches {

    public static boolean showFrozen = false;

    @SpirePatch(
            clz = ExhaustPileViewScreen.class,
            method = "open"
    )
    public static class OpenExhaustPileViewScreenPatch {
        @SpireInsertPatch( locator = OpenExhaustPileViewScreenPatchLocator.class )
        public static void Insert(ExhaustPileViewScreen _instance) {
            if (showFrozen) {
                CardGroup group = (CardGroup) ReflectionHacks.getPrivate(_instance, ExhaustPileViewScreen.class, "exhaustPileCopy");
                group.clear();
                group.group.addAll(ChefMod.frozenPile.group);
                showFrozen = false;
            }
        }
    }

    private static class OpenExhaustPileViewScreenPatchLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ExhaustPileViewScreen.class, "hideCards");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
