package chefmod.cards.attacks;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class Puree extends AbstractChefCard {
    public static String ID = makeID(Puree.class.getSimpleName());

    public Puree() {
        super(ID,
                3,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 15;
        upgradeDamageBy = 6;
        baseBlock = block = 15;
        upgradeBlockBy = 6;
        damages = true;
        blocks = true;
        hasPreppedActions = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
        dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (isPrepped()) {
            ChefMod.cardsToFreeze.add(this);
        }
    }
}
