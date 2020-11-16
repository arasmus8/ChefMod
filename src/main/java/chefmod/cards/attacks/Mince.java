package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class Mince extends AbstractChefCard {
    public static String ID = makeID(Mince.class.getSimpleName());

    public Mince() {
        super(ID,
                3,
                CardType.ATTACK,
                CardRarity.RARE,
                CardTarget.ALL_ENEMY
        );
        baseDamage = damage = 7;
        magicNumber = baseMagicNumber = 4;
        upgradeMagicNumberBy = 1;
        damages = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        IntStream.rangeClosed(1, magicNumber).forEachOrdered(i ->
                addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_VERTICAL))
        );
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new DrawPileToHandAction(1, CardType.SKILL));
    }
}
