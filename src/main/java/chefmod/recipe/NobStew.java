package chefmod.recipe;

import basemod.helpers.CardModifierManager;
import chefmod.ChefMod;
import chefmod.cardmods.NeowNuggetsCardmod;
import chefmod.powers.NobStewPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import static chefmod.ChefMod.makeID;

public class NobStew extends AbstractRecipe {
    public static final String ID = makeID(NobStew.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    public NobStew() {
        tipHeader = TEXT[0];
        tipBody = TEXT[1];
        ingredientCount = 3;
    }

    @Override
    public void onActivate() {
        applyToSelf(new NobStewPower(AbstractDungeon.player));
    }
}
