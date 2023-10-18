package chefmod.cards.attacks;

import chefmod.actions.PlayOldestFrozenCardAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class QuickChop extends AbstractChefCard {
    public static String ID = makeID(QuickChop.class.getSimpleName());

    public QuickChop() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 7;
        upgradeDamageBy = 3;
        baseMagicNumber = magicNumber = 1;
        upgradeMagicNumberBy = 1;
        damages = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        IntStream.rangeClosed(1, magicNumber)
                .forEachOrdered(i -> addToBot(new PlayOldestFrozenCardAction()));
    }
}
