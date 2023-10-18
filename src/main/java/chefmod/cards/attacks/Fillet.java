package chefmod.cards.attacks;

import chefmod.actions.FreezeAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class Fillet extends AbstractChefCard {
    public static String ID = makeID(Fillet.class.getSimpleName());

    public Fillet() {
        super(ID,
                0,
                CardType.ATTACK,
                CardRarity.COMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 5;
        upgradeDamageBy = 3;
        damages = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        addToBot(new FreezeAction(this.makeStatEquivalentCopy()));
    }
}
