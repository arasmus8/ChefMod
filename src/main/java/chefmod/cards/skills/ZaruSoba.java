package chefmod.cards.skills;

import chefmod.ChefMod;
import chefmod.actions.FreezeAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (int i = 0; i < magicNumber && ChefMod.frozenPile.size() > 0; i++) {
                    ChefMod.frozenPile.moveToHand(ChefMod.frozenPile.getBottomCard(), ChefMod.frozenPile);
                }
            }
        });
        addToBot(new FreezeAction(magicNumber));
    }
}
