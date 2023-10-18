package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class SliceAndDice extends AbstractChefCard {
    public static String ID = makeID(SliceAndDice.class.getSimpleName());

    public SliceAndDice() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.COMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 7;
        upgradeDamageBy = 3;
        damages = true;
        hasPreppedActions = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (isPrepped()) {
            dealDamage(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        }
    }
}
