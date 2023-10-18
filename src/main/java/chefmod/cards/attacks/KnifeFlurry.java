package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class KnifeFlurry extends AbstractChefCard {
    public static String ID = makeID(KnifeFlurry.class.getSimpleName());

    public KnifeFlurry() {
        super(ID,
                2,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 4;
        baseMagicNumber = magicNumber = 4;
        damages = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            selfRetain = true;
        }
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        IntStream.rangeClosed(1, magicNumber)
                .forEachOrdered(i -> dealDamage(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }
}
