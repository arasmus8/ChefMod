package chefmod.cards.attacks;

import chefmod.actions.FreezeAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class IcePick extends AbstractChefCard {
    public static String ID = makeID(IcePick.class.getSimpleName());

    public IcePick() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.COMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 7;
        upgradeDamageBy = 3;
        damages = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        addToBot(new FreezeAction(1));
    }
}
