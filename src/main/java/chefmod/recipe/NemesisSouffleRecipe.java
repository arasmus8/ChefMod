package chefmod.recipe;

import chefmod.powers.SatiatedPower;
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
    }

    @Override
    public void onActivate() {
        applyToSelf(new IntangiblePlayerPower(AbstractDungeon.player, 1));
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        applyToEnemy(m, new SatiatedPower(m));
    }
}
