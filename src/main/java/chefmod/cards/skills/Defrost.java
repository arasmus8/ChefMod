package chefmod.cards.skills;

import chefmod.ChefMod;
import chefmod.actions.FunctionalAction;
import chefmod.actions.GridSelectAndPerformAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class Defrost extends AbstractChefCard {
    public static String ID = makeID(Defrost.class.getSimpleName());

    public Defrost() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.COMMON,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 1;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GridSelectAndPerformAction(magicNumber,
                false,
                ChefMod.frozenPile,
                null,
                new FunctionalAction(firstUpdate -> {
                    GridSelectAndPerformAction.selectedCards.forEach(c -> {
                        ChefMod.frozenPile.moveToHand(c, ChefMod.frozenPile);
                        if (c instanceof AbstractChefCard) {
                            AbstractChefCard chefCard = (AbstractChefCard) c;
                            chefCard.frozen = false;
                        }
                    });
                    return true;
                })
        ));
    }
}
