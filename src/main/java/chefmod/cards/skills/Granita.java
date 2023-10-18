package chefmod.cards.skills;

import chefmod.actions.FreezeAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class Granita extends AbstractChefCard {
    public static String ID = makeID(Granita.class.getSimpleName());

    public Granita() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.COMMON,
                CardTarget.ENEMY
        );
        baseMagicNumber = magicNumber = 2;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        addToBot(new FreezeAction(
                2,
                c -> c.type == CardType.ATTACK,
                null
        ));
    }
}
