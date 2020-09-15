package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.SupercooledPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class LiquidNitrogen extends AbstractChefCard {
    public static String ID = makeID(LiquidNitrogen.class.getSimpleName());

    public LiquidNitrogen() {
        super(ID,
                0,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 7;
        upgradeDamageBy = 2;
        baseMagicNumber = magicNumber = 1;
        damages = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        applyToEnemy(m, new SupercooledPower(m, magicNumber));
    }
}
