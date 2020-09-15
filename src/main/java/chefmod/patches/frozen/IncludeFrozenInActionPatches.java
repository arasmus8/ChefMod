package chefmod.patches.frozen;

import chefmod.ChefMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.actions.unique.BurnIncreaseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IncludeFrozenInActionPatches {
    @SpirePatch(
            clz = ApotheosisAction.class,
            method = "update"
    )
    public static class ApotheosisActionPatch {
        @SpireInsertPatch(
                locator = ApotheosisActionPatchLocator.class
        )
        public static void Insert(ApotheosisAction _instance) {
            Method upgradeMethod;
            try {
                upgradeMethod = ApotheosisAction.class.getDeclaredMethod("upgradeAllCardsInGroup", CardGroup.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return;
            }
            upgradeMethod.setAccessible(true);
            try {
                upgradeMethod.invoke(_instance, ChefMod.frozenPile);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ApotheosisActionPatchLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.FieldAccessMatcher(ApotheosisAction.class, "isDone");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }

    @SpirePatch(
            clz = BurnIncreaseAction.class,
            method = "update"
    )
    public static class BurnIncreaseActionPatch {
        @SpireInsertPatch(
                locator = BurnIncreaseActionPatchLocator.class
        )
        public static void Insert(BurnIncreaseAction _instance) {
            ChefMod.frozenPile.group.stream().filter(c -> c instanceof Burn).forEach(AbstractCard::upgrade);
        }
    }

    private static class BurnIncreaseActionPatchLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "discardPile");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
