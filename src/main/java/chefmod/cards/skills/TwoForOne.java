package chefmod.cards.skills;

import basemod.helpers.CardModifierManager;
import chefmod.ChefMod;
import chefmod.cardmods.PlayTwiceCardmod;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class TwoForOne extends AbstractChefCard {
    public static String ID = makeID(TwoForOne.class.getSimpleName());

    public TwoForOne() {
        super(ID,
                3,
                CardType.SKILL,
                CardRarity.RARE,
                CardTarget.SELF
        );
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                ChefMod.frozenPile.group.forEach(this::applyCardmod);
            }

            private void applyCardmod(AbstractCard c) {
                CardModifierManager.removeModifiersById(c, PlayTwiceCardmod.ID, true);
                CardModifierManager.addModifier(c, new PlayTwiceCardmod(upgraded));
            }
        });
    }
}
