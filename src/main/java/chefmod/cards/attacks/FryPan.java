package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class FryPan extends AbstractChefCard {
    public static String ID = makeID(FryPan.class.getSimpleName());

    public FryPan() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 9;
        upgradeDamageBy = 3;
        damages = true;
        nofreeze = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }
}
