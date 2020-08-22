package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class Chop extends AbstractChefCard {
    public static String ID = makeID(Chop.class.getSimpleName());

    public Chop() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.COMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 8;
        upgradeDamageBy = 3;
        damages = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }
}
