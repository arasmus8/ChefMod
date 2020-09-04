package chefmod.cards.skills;

import chefmod.actions.FreezeAction;
import chefmod.actions.FunctionalAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class Gelato extends AbstractChefCard {
    public static String ID = makeID(Gelato.class.getSimpleName());

    public Gelato() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.COMMON,
                CardTarget.SELF
        );
        baseBlock = block = 3;
        upgradeBlockBy = 1;
        baseMagicNumber = magicNumber = 2;
        blocks = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FreezeAction(
                magicNumber,
                c -> c.type == CardType.SKILL,
                new FunctionalAction(initialUpdate -> {
                    int count = FreezeAction.frozenCards.size();
                    if (count > 0) {
                        addToBot(new GainBlockAction(p, block * count));
                    }
                    return true;
                })
        ));
    }
}
