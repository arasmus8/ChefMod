package chefmod.patches.cards;

import basemod.ReflectionHacks;
import chefmod.cards.AbstractChefCard;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

public class TriggerOnShufflePatches {
    @SpirePatch(
            clz = ShuffleAction.class,
            method = "update"
    )
    public static class ShuffleActionPatch {
        @SpireInsertPatch(
                locator = ShuffleActionPatchLocator.class
        )
        public static void insert(ShuffleAction _instance) {
            CardGroup group = (CardGroup) ReflectionHacks.getPrivate(_instance, ShuffleAction.class, "group");
            group.group.stream()
                    .filter(c -> c instanceof AbstractChefCard)
                    .map(c -> (AbstractChefCard) c)
                    .forEach(AbstractChefCard::triggerWhenShuffled);
        }
    }

    public static class ShuffleActionPatchLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.FieldAccessMatcher(ShuffleAction.class, "isDone");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }

    @SpirePatch(
            clz = EmptyDeckShuffleAction.class,
            method = "update"
    )
    public static class EmptyDeckShuffleActionPatch {
        @SpireInsertPatch(
                locator = EmptyDeckShuffleActionPatchLocator.class
        )
        public static void insert(EmptyDeckShuffleAction _instance) {
            CardGroup group = AbstractDungeon.player.drawPile;
            group.group.stream()
                    .filter(c -> c instanceof AbstractChefCard)
                    .map(c -> (AbstractChefCard) c)
                    .forEach(AbstractChefCard::triggerWhenShuffled);
        }
    }

    public static class EmptyDeckShuffleActionPatchLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.FieldAccessMatcher(EmptyDeckShuffleAction.class, "isDone");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
