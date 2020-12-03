package chefmod.recipe;

import chefmod.cards.options.NemesisSouffle;
import chefmod.powers.SatiatedPower;
import chefmod.util.VfxMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import static chefmod.ChefMod.makeID;

public class NemesisSouffleRecipe extends AbstractRecipe {
    public static final String ID = makeID(NemesisSouffleRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    public NemesisSouffleRecipe() {
        tipHeader = TEXT[0];
        tipBody = TEXT[1];
        ingredientCount = 4;
        card = new NemesisSouffle();
    }

    @Override
    public void onActivate() {
        applyToSelf(new IntangiblePlayerPower(AbstractDungeon.player, 1));
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        applyToEnemy(m, new SatiatedPower(m));
        super.renderFood(VfxMaster.NEMESIS_SOUFFLE);
    }
}
