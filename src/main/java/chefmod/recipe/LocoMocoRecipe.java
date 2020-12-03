package chefmod.recipe;

import chefmod.actions.FunctionalAction;
import chefmod.powers.SatiatedPower;
import chefmod.util.VfxMaster;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import static chefmod.ChefMod.makeID;

public class LocoMocoRecipe extends AbstractRecipe {
    public static final String ID = makeID(LocoMocoRecipe.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;

    private final int drawAmount;
    private final boolean reduceAttackCost;

    public LocoMocoRecipe(int drawAmount, boolean reduceAttackCost, AbstractCard card) {
        this.drawAmount = drawAmount;
        this.reduceAttackCost = reduceAttackCost;
        tipHeader = TEXT[0];
        tipBody = TEXT[1] + drawAmount + (reduceAttackCost ? TEXT[3] : TEXT[2]);
        ingredientCount = 2;
        this.card = card.makeStatEquivalentCopy();
    }

    @Override
    public void onActivate() {
        monsterList().forEach(m -> applyToEnemy(m, new SatiatedPower(m)));
        qAction(new DrawCardAction(drawAmount, new FunctionalAction(firstUpdate -> {
            if (reduceAttackCost) {
                DrawCardAction.drawnCards.stream()
                        .filter(c -> c.type == AbstractCard.CardType.ATTACK)
                        .forEach(c -> {
                            c.cost = 0;
                            c.costForTurn = 0;
                            c.isCostModified = true;
                            c.superFlash(Color.GOLD.cpy());
                        });
            }
            return true;
        })));
        super.renderFood(VfxMaster.LOCO_MOCO);
    }
}
