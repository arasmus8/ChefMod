package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.FreezeNextCardPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class Julienne extends AbstractChefCard {
    public static String ID = makeID(Julienne.class.getSimpleName());

    public Julienne() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 8;
        upgradeDamageBy = 3;
        baseMagicNumber = magicNumber = 1;
        damages = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        applyToSelf(new FreezeNextCardPower(p, magicNumber));
    }
}
