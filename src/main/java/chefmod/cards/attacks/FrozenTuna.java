package chefmod.cards.attacks;

import chefmod.actions.FreezeAction;
import chefmod.cards.AbstractChefCard;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class FrozenTuna extends AbstractChefCard implements StartupCard {
    public static String ID = makeID(FrozenTuna.class.getSimpleName());

    public FrozenTuna() {
        super(ID,
                3,
                CardType.ATTACK,
                CardRarity.RARE,
                CardTarget.ALL_ENEMY
        );
        baseDamage = damage = 18;
        baseMagicNumber = magicNumber = 0;
        upgradeMagicNumberBy = 6;
        damages = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealAoeDamage(AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    @Override
    public void atTurnStart() {
        if (upgraded) {
            baseDamage += magicNumber;
        }
    }

    @Override
    public boolean atBattleStartPreDraw() {
        addToBot(new FreezeAction(this));
        return true;
    }
}
