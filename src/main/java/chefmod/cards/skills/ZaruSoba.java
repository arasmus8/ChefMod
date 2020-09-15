package chefmod.cards.skills;

import chefmod.ChefMod;
import chefmod.actions.FreezeAction;
import chefmod.actions.FunctionalAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class ZaruSoba extends AbstractChefCard {
    public static String ID = makeID(ZaruSoba.class.getSimpleName());

    public ZaruSoba() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.COMMON,
                CardTarget.SELF
        );
        baseMagicNumber = magicNumber = 2;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FunctionalAction(firstUpdate -> {
            IntStream.rangeClosed(1, magicNumber)
                    .limit(ChefMod.frozenPile.size())
                    .forEach(i -> ChefMod.frozenPile.moveToHand(ChefMod.frozenPile.getBottomCard(), ChefMod.frozenPile));
            return true;
        }));
        addToBot(new FreezeAction(magicNumber));
    }
}
