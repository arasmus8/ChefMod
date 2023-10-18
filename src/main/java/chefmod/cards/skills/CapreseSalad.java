package chefmod.cards.skills;

import chefmod.actions.FunctionalAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class CapreseSalad extends AbstractChefCard {
    public static String ID = makeID(CapreseSalad.class.getSimpleName());

    public CapreseSalad() {
        super(ID,
                1,
                CardType.SKILL,
                CardRarity.RARE,
                CardTarget.SELF
        );
        baseBlock = block = 10;
        upgradeBlockBy = 4;
        blocks = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
    }

    @Override
    public void triggerWhenShuffled() {
        addToTop(new FunctionalAction(Settings.ACTION_DUR_FAST, firstUpdate -> {
            CardGroup draw = AbstractDungeon.player.drawPile;
            draw.removeCard(this);
            draw.moveToDeck(this, false);
            return true;
        }));
    }
}
