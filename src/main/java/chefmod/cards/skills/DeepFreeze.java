package chefmod.cards.skills;

import chefmod.ChefMod;
import chefmod.actions.FreezeAction;
import chefmod.actions.FunctionalAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class DeepFreeze extends AbstractChefCard {
    public static String ID = makeID(DeepFreeze.class.getSimpleName());

    public DeepFreeze() {
        super(ID,
                0,
                CardType.SKILL,
                CardRarity.RARE,
                CardTarget.SELF
        );
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FunctionalAction(firstUpdate -> {
            while (ChefMod.frozenPile.size() > 0) {
                ChefMod.frozenPile.moveToDiscardPile(ChefMod.frozenPile.getBottomCard());
            }
            return true;
        }));
        addToBot(new FreezeAction(p.drawPile.size()));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            exhaust = false;
        }
        super.upgrade();
    }
}
