package chefmod.cards.attacks;

import chefmod.ChefMod;
import chefmod.actions.FreezeAction;
import chefmod.cards.AbstractChefCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.relics.RunicPyramid;

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
        if (isEthereal) {
            addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        } else {
            ChefMod.cardsToFreeze.add(this);
            if (AbstractDungeon.player.hasRelic(RunicPyramid.ID) || AbstractDungeon.player.hasPower(EquilibriumPower.POWER_ID) || retain || selfRetain) {
                addToTop(new DiscardSpecificCardAction(this, AbstractDungeon.player.hand));
            }
        }
    }

    @Override
    public void onRetained() {
        addToBot(new FreezeAction(this));
    }
}
