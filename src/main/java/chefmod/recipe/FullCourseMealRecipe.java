package chefmod.recipe;

import chefmod.util.VfxMaster;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import static chefmod.ChefMod.makeID;

public class FullCourseMealRecipe extends AbstractRecipe {
    public static final String ID = makeID(FullCourseMealRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    private final int block;
    private final boolean nextTurnAsWell;

    public FullCourseMealRecipe(int block, boolean nextTurnAsWell, AbstractCard card) {
        tipHeader = TEXT[0];
        tipBody = TEXT[1] + block + (nextTurnAsWell ? (TEXT[3] + block + TEXT[4]) : TEXT[2]);
        this.block = block;
        this.ingredientCount = 4;
        this.nextTurnAsWell = nextTurnAsWell;
        this.card = card.makeStatEquivalentCopy();
    }

    @Override
    public void onActivate() {
        qAction(new GainBlockAction(AbstractDungeon.player, block));
        if (nextTurnAsWell) {
            applyToSelf(new NextTurnBlockPower(AbstractDungeon.player, block));
        }
        super.renderFood(VfxMaster.FULL_COURSE_MEAL);
    }
}
