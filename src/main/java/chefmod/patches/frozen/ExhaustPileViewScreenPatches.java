package chefmod.patches.frozen;

import basemod.ReflectionHacks;
import chefmod.ChefMod;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import javassist.CtBehavior;

import static chefmod.ChefMod.makeID;

public class ExhaustPileViewScreenPatches {

    public static boolean showFrozen = false;
    private static boolean isShowingFrozen = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("FrozenPileViewScreen"));

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
                isShowingFrozen = true;
            } else {
                isShowingFrozen = false;
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

    @SpirePatch(
            clz = ExhaustPileViewScreen.class,
            method = "render"
    )
    public static class RenderExhaustPileViewScreenPatch {
        @SpireInsertPatch( locator = RenderExhaustPileViewScreenPatchLocator.class )
        public static SpireReturn<Void> Insert(ExhaustPileViewScreen _instance, SpriteBatch sb) {
            if (isShowingFrozen) {
                FontHelper.renderDeckViewTip(sb, uiStrings.TEXT[0], 96.0F * Settings.scale, Settings.CREAM_COLOR);// 311
                return SpireReturn.Return(null);
            } else {
                return SpireReturn.Continue();
            }
        }
    }

    private static class RenderExhaustPileViewScreenPatchLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderDeckViewTip");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
