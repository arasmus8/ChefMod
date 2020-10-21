package chefmod.relics;

import basemod.helpers.CardModifierManager;
import chefmod.TheChef;
import chefmod.cardmods.PermanentDamageBonusCardmod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class OldKettle extends AbstractChefRelic {
    public static String ID = makeID(OldKettle.class.getSimpleName());
    private boolean activated = false;
    private static final int DAMAGE_AMOUNT = 5;

    public OldKettle() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK, TheChef.Enums.CHEF_COLOR);
    }

    @Override
    public void atBattleStartPreDraw() {
        activated = false;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (!activated && drawnCard.type == AbstractCard.CardType.ATTACK) {
            flash();
            activated = true;
            CardModifierManager.addModifier(drawnCard, new PermanentDamageBonusCardmod(DAMAGE_AMOUNT));
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(TrustyPot.ID);
    }

    @Override
    public void obtain() {
        int indexOfTrustyPot = IntStream.range(0, AbstractDungeon.player.relics.size())
                .filter(i -> AbstractDungeon.player.relics.get(i).relicId.equals(TrustyPot.ID))
                .findFirst()
                .orElse(-1);
        if (indexOfTrustyPot >= 0) {
            this.instantObtain(AbstractDungeon.player, indexOfTrustyPot, true);
            return;
        }
        super.obtain();
    }

}
