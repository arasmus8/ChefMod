package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Collections;
import java.util.List;

import static chefmod.ChefMod.makeID;

public class Strike extends AbstractChefCard {
    public static String ID = makeID(Strike.class.getSimpleName());
    private static final List<CardTags> tagsList = Collections.singletonList(CardTags.STARTER_STRIKE);

    public Strike () {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.BASIC,
                CardTarget.ENEMY,
                tagsList
        );
        baseDamage = damage = 6;
        upgradeDamageBy = 3;
        damages = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }
}
