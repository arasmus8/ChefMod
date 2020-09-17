package chefmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import chefmod.cards.skills.Omelette;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Optional;

import static chefmod.ChefMod.makeID;

public class OmeletteCardmod extends AbstractChefCardmod {
    public static final String ID = makeID(OmeletteCardmod.class.getSimpleName());

    private boolean shuffleCard;

    public OmeletteCardmod(int damage, boolean shuffleCard) {
        this.damage = damage;
        this.shuffleCard = shuffleCard;
    }

    public static void addToCard(AbstractCard card, int damage, boolean shuffleCard) {
        Optional<AbstractChefCardmod> c = OmeletteCardmod.getForCard(card, ID);
        if (c.isPresent()) {
            OmeletteCardmod ocm = (OmeletteCardmod) c.get();
            ocm.shuffleCard = shuffleCard || ocm.shuffleCard;
            ocm.damage += damage;
        } else {
            CardModifierManager.addModifier(card, new OmeletteCardmod(damage, shuffleCard));
        }
    }

    @Override
    public float modifyDamage(float orig, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return orig + damage;
        }
        return orig;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (shuffleCard) {
            AbstractCard c = new Omelette();
            c.upgrade();
            shuffleIn(c);
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new OmeletteCardmod(damage, shuffleCard);
    }
}
