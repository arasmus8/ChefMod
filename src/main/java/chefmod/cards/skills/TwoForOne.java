package chefmod.cards.skills;

import chefmod.ChefMod;
import chefmod.actions.FunctionalAction;
import chefmod.cardmods.PlayTwiceCardmod;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Consumer;

import static chefmod.ChefMod.makeID;

public class TwoForOne extends AbstractChefCard {
    public static String ID = makeID(TwoForOne.class.getSimpleName());

    public TwoForOne() {
        super(ID,
                3,
                CardType.SKILL,
                CardRarity.RARE,
                CardTarget.SELF
        );
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Consumer<AbstractCard> applyCardmod = c -> PlayTwiceCardmod.addToCard(c, upgraded);
        addToBot(new FunctionalAction(first -> {
                    ChefMod.frozenPile.group.forEach(applyCardmod);
                    return true;
                })
        );
    }
}
