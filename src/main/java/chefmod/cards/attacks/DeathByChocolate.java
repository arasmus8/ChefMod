package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import chefmod.powers.HungerPower;
import chefmod.powers.SatiatedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class DeathByChocolate extends AbstractChefCard {
    public static String ID = makeID(DeathByChocolate.class.getSimpleName());

    public DeathByChocolate() {
        super(ID,
                0,
                CardType.ATTACK,
                CardRarity.RARE,
                CardTarget.ENEMY
        );
        baseDamage = damage = 6;
        upgradeDamageBy = 3;
        magicNumber = baseMagicNumber = 50;
        damages = true;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = baseDamage;
        baseDamage = baseMagicNumber;
        super.calculateCardDamage(mo);
        magicNumber = damage;
        isMagicNumberModified = magicNumber != baseMagicNumber;
        baseDamage = realBaseDamage;
        super.calculateCardDamage(mo);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        magicNumber = baseMagicNumber;
        isMagicNumberModified = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: create VFX
        if (m.hasPower(SatiatedPower.POWER_ID) && m.hasPower(HungerPower.POWER_ID)) {
            addToBot(new DamageAction(m, new DamageInfo(p, magicNumber, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        } else {
            dealDamage(m, AbstractGameAction.AttackEffect.NONE);
        }
    }
}
