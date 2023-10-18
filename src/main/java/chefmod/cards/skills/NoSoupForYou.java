package chefmod.cards.skills;

import chefmod.actions.FreezeAction;
import chefmod.actions.PlayOldestFrozenCardAction;
import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class NoSoupForYou extends AbstractChefCard {
    public static String ID = makeID(NoSoupForYou.class.getSimpleName());

    public NoSoupForYou() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new HungerPower(m));
        addToBot(new FreezeAction(1, c -> c.type == CardType.ATTACK && c.cost >= 2));
        if (upgraded) {
            addToBot(new PlayOldestFrozenCardAction());
        }
    }
}
