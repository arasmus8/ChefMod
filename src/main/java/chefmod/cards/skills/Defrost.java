package chefmod.cards.skills;

import chefmod.ChefMod;
import chefmod.actions.FunctionalAction;
import chefmod.actions.GridSelectAndPerformAction;
import chefmod.actions.PlayOldestFrozenCardAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class Defrost extends AbstractChefCard {
    public static String ID = makeID(Defrost.class.getSimpleName());

    public Defrost() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.COMMON,
                CardTarget.SELF
        );
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            addToBot(new GridSelectAndPerformAction(magicNumber,
                    cardStrings.EXTENDED_DESCRIPTION[1],
                    new FunctionalAction(firstUpdate -> {
                        GridSelectAndPerformAction.selectedCards.forEach(c -> addToBot(new PlayOldestFrozenCardAction(filterCard -> filterCard == c)));
                        return true;
                    })

            ));
        } else {
            addToBot(new GridSelectAndPerformAction(magicNumber,
                    cardStrings.EXTENDED_DESCRIPTION[0],
                    new FunctionalAction(firstUpdate -> {
                        GridSelectAndPerformAction.selectedCards.forEach(c -> {
                            ChefMod.frozenPile.moveToHand(c, ChefMod.frozenPile);
                            if (c instanceof AbstractChefCard) {
                                AbstractChefCard chefCard = (AbstractChefCard) c;
                                chefCard.frozen = false;
                            }
                            c.triggerWhenDrawn();
                        });
                        return true;
                    })
            ));
        }
    }
}
