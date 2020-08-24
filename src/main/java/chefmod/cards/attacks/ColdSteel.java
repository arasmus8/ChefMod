package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class ColdSteel extends AbstractChefCard {
    public static String ID = makeID(ColdSteel.class.getSimpleName());

    public ColdSteel() {
        super(ID,
                2,
                CardType.ATTACK,
                CardRarity.COMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 17;
        upgradeDamageBy = 3;
        damages = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    @Override
    public void triggerWhenFrozen() {
        updateCost(-1);
    }
}
