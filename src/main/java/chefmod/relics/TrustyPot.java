package chefmod.relics;

import basemod.helpers.CardModifierManager;
import chefmod.TheChef;
import chefmod.cardmods.BonusBlockCardmod;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static chefmod.ChefMod.makeID;

public class TrustyPot extends AbstractChefRelic {
    public static String ID = makeID(TrustyPot.class.getSimpleName());
    private boolean activated = false;
    private static final int BLOCK_AMOUNT = 5;

    public TrustyPot() {
        super(ID, RelicTier.STARTER, LandingSound.CLINK, TheChef.Enums.CHEF_COLOR);
    }

    @Override
    public void atBattleStartPreDraw() {
        activated = false;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (!activated) {
            activated = true;
            CardModifierManager.addModifier(drawnCard, new BonusBlockCardmod(BLOCK_AMOUNT));
        }
    }
}
