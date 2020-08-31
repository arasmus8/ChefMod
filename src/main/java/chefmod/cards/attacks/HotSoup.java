package chefmod.cards.attacks;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class HotSoup extends AbstractChefCard {
    public static String ID = makeID(HotSoup.class.getSimpleName());

    public HotSoup() {
        super(ID,
                2,
                CardType.ATTACK,
                CardRarity.COMMON,
                CardTarget.ENEMY
        );
        baseDamage = damage = 17;
        upgradeDamageBy = 4;
        damages = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        ChefMod.cardsToFreeze.add(this);
    }
}
