package chefmod.relics;

import chefmod.TheChef;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static chefmod.ChefMod.makeID;

public class Spatula extends AbstractChefRelic implements TriggerOnFrozenRelic {
    public static String ID = makeID(Spatula.class.getSimpleName());

    public Spatula() {
        super(ID, RelicTier.RARE, LandingSound.FLAT, TheChef.Enums.CHEF_COLOR);
    }

    @Override
    protected int getDamage() {
        return 4;
    }

    @Override
    public void triggerOnFrozen(AbstractCard card) {
        flash();
        dealDamage(AbstractDungeon.getRandomMonster(), AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }
}
