package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class ReptoDagger extends AbstractChefCard {
    public static String ID = makeID(ReptoDagger.class.getSimpleName());

    public ReptoDagger() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.COMMON,
                CardTarget.ENEMY,
                CardColor.COLORLESS,
                null
        );
        baseDamage = damage = 4;
        upgradeDamageBy = 2;
        damages = true;
        baseMagicNumber = magicNumber = 1;
        shuffleBackIntoDrawPile = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        IntStream.rangeClosed(1, magicNumber).forEachOrdered(i -> dealDamage(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        baseMagicNumber = magicNumber = magicNumber + 1;
    }
}
