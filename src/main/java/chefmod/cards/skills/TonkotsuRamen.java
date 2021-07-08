package chefmod.cards.skills;

import basemod.helpers.CardModifierManager;
import chefmod.ChefMod;
import chefmod.actions.PlayOldestFrozenCardAction;
import chefmod.cardmods.ReboundCardmod;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

public class TonkotsuRamen extends AbstractChefCard {
    public static String ID = makeID(TonkotsuRamen.class.getSimpleName());

    public TonkotsuRamen() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        nofreeze = true;
        upgradeCostTo = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (ChefMod.frozenPile.size() > 0) {
            CardModifierManager.addModifier(ChefMod.frozenPile.getBottomCard(), new ReboundCardmod());
            addToBot(new PlayOldestFrozenCardAction());
        }
    }
}
