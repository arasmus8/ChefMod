package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class NuggetStrike extends AbstractChefCard {
    public static String ID = makeID(NuggetStrike.class.getSimpleName());

    public NuggetStrike() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.SPECIAL,
                CardTarget.ENEMY,
                CardColor.COLORLESS,
                null
        );
        baseDamage = damage = 6;
        upgradeDamageBy = 3;
        damages = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }
}
