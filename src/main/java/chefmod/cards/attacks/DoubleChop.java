package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class DoubleChop extends AbstractChefCard {
    public static String ID = makeID(DoubleChop.class.getSimpleName());

    public DoubleChop() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 4;
        baseMagicNumber = magicNumber = 2;
        upgradeDamageBy = 2;
        damages = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        IntStream.rangeClosed(1, magicNumber)
                .forEach(i -> dealDamage(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new DiscardPileToTopOfDeckAction(p));
    }
}
