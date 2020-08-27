package chefmod.cardmods;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.Optional;

import static chefmod.ChefMod.makeID;

public class OneTimeBlockBonusCardmod extends AbstractChefCardmod {
    public static final String CARDMOD_ID = makeID(OneTimeBlockBonusCardmod.class.getSimpleName());

    public OneTimeBlockBonusCardmod(int blockAmount) {
        ID = CARDMOD_ID;
        block = blockAmount;
    }

    public static void add(AbstractCard card, int blockAmount) {
        Optional<AbstractChefCardmod> current = getForCard(card, CARDMOD_ID);
        if (current.isPresent()) {
            current.get().block += blockAmount;
        } else {
            CardModifierManager.addModifier(card, new OneTimeBlockBonusCardmod(blockAmount));
        }
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public float modifyBlock(float current, AbstractCard card) {
        return block + current;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }
}
