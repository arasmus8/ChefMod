package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class FullBurn extends AbstractChefCard {
    public static String ID = makeID(FullBurn.class.getSimpleName());

    public FullBurn() {
        super(ID,
                3,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ALL_ENEMY
        );
        baseDamage = damage = 23;
        upgradeDamageBy = 5;
        baseMagicNumber = magicNumber = 1;
        upgradeMagicNumberBy = 1;
        damages = true;
        isMultiDamage = true;
        hasPreppedActions = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealAoeDamage(AbstractGameAction.AttackEffect.FIRE);
        if (isPrepped()) {
            addToBot(new GainEnergyAction(magicNumber));
        }
    }
}
