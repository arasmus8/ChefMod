package chefmod.cards.skills;

import chefmod.actions.FreezeAction;
import chefmod.actions.PlayOldestFrozenCardAction;
import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class Sorbet extends AbstractChefCard {
    public static String ID = makeID(Sorbet.class.getSimpleName());

    public Sorbet() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.COMMON,
                CardTarget.ENEMY
        );
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            addToBot(new PlayOldestFrozenCardAction(c -> c.type == CardType.POWER));
        }
        applyToEnemy(m, new HungerPower(m));
        addToBot(new FreezeAction(
                magicNumber,
                c -> c.type == CardType.POWER,
                null
        ));
    }
}
