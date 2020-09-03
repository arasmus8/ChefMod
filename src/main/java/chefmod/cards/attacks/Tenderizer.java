package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static chefmod.ChefMod.makeID;

public class Tenderizer extends AbstractChefCard {
    public static String ID = makeID(Tenderizer.class.getSimpleName());

    public Tenderizer() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.RARE,
                CardTarget.ENEMY
        );
        baseDamage = damage = 8;
        upgradeDamageBy = 4;
        magicNumber = baseMagicNumber = 9;
        damages = true;
        hasPreppedActions = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (isPrepped()) {
            applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        }
    }
}
