package chefmod.cards.skills;

import chefmod.actions.FreezeAction;
import chefmod.actions.FunctionalAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class Gelato extends AbstractChefCard {
    public static String ID = makeID(Gelato.class.getSimpleName());

    public Gelato() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.COMMON,
                CardTarget.SELF
        );
        baseBlock = block = 3;
        upgradeBlockBy = 1;
        baseMagicNumber = magicNumber = 2;
        blocks = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FreezeAction(
                magicNumber,
                c -> c.type == CardType.SKILL,
                new FunctionalAction(initialUpdate -> {
                    int totalCost = FreezeAction.frozenCards.stream()
                            .mapToInt(c -> c.costForTurn)
                            .reduce(0, (acc, curr) -> {
                                if (curr == -1) {
                                    return acc + EnergyPanel.getCurrentEnergy();
                                } else if (curr < -1) {
                                    return acc;
                                } else {
                                    return acc + curr;
                                }
                            });
                    if (totalCost > 0) {
                        addToBot(new GainBlockAction(p, block * totalCost));
                    }
                    return true;
                })
        ));
    }
}
