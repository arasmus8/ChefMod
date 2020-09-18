package chefmod.cards.skills;

import chefmod.ChefMod;
import chefmod.actions.PlayOldestFrozenCardAction;
import chefmod.actions.XCostAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class SpaghettiCarbonara extends AbstractChefCard {
    public static String ID = makeID(SpaghettiCarbonara.class.getSimpleName());

    public SpaghettiCarbonara() {
        super(ID,
                -1,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        nofreeze = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster monster) {
        addToBot(new XCostAction(this, (amount, params) -> {
            if (params[0] == 0) {
                IntStream.rangeClosed(1, amount)
                        .forEachOrdered(i -> addToBot(new PlayOldestFrozenCardAction(false)));
            } else {
                IntStream.rangeClosed(1, amount)
                        .forEachOrdered(i -> {
                            if (ChefMod.frozenPile.size() > 0) {
                                AbstractCard card = ChefMod.frozenPile.getBottomCard();
                                ChefMod.frozenPile.moveToHand(card, ChefMod.frozenPile);
                                card.freeToPlayOnce = true;
                            }
                        });
            }
            return true;
        }, upgraded ? 1 : 0));
    }
}
