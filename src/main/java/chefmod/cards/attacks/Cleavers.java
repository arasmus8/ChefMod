package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class Cleavers extends AbstractChefCard {
    public static String ID = makeID(Cleavers.class.getSimpleName());

    public Cleavers() {
        super(ID,
                2,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ALL_ENEMY
        );
        baseDamage = damage = 11;
        magicNumber = baseMagicNumber = 1;
        upgradeMagicNumberBy = 1;
        damages = true;
        isMultiDamage = true;
        hasPreppedActions = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealAoeDamage(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (isPrepped()) {
            addToBot(new SwordBoomerangAction(makeDamageInfo(DamageInfo.DamageType.NORMAL), magicNumber));
        }
    }
}
