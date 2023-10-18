package chefmod.cards.attacks;

import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Arrays;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class Strike extends AbstractChefCard {
    public static String ID = makeID(Strike.class.getSimpleName());
    private static final ArrayList<CardTags> tagsList = new ArrayList<>(Arrays.asList(CardTags.STARTER_STRIKE, CardTags.STRIKE));

    public Strike() {
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
