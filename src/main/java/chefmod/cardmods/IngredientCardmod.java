package chefmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import chefmod.ChefMod;
import chefmod.powers.FreshProducePower;
import chefmod.util.TextureHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Optional;

import static chefmod.ChefMod.makeID;
import static chefmod.ChefMod.makeImagePath;

public class IngredientCardmod extends AbstractChefCardmod {
    private static final Texture icon = TextureHelper.getTexture(makeImagePath("vfx/ingredient_badge.png"));
    public static final String ID = makeID(IngredientCardmod.class.getSimpleName());

    private boolean removeImmediately = false;

    public IngredientCardmod() {
    }

    private IngredientCardmod(boolean removeImmediately) {
        this.removeImmediately = removeImmediately;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (removeImmediately) {
            CardModifierManager.removeSpecificModifier(card, this, true);
            ChefMod.recipeManager.checkIngredientCount();
        }
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (!card.purgeOnUse) {
            ChefMod.recipeManager.useIngredient(card);
            Optional.ofNullable(AbstractDungeon.player.getPower(FreshProducePower.POWER_ID))
                    .ifPresent(p -> qAction(new DrawCardAction(AbstractDungeon.player, p.amount)));

        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new IngredientCardmod(true);
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        if (card.purgeOnUse) {
            return;
        }
        Vector2 vec = new Vector2(140, 200);
        vec.scl(card.drawScale * Settings.scale);
        vec.rotate(card.angle);

        Color color = Color.WHITE.cpy();
        color.a = card.transparency;
        sb.setColor(color);

        TextureHelper.drawScaledAndRotated(sb, icon, card.current_x + vec.x, card.current_y + vec.y, 1.25f, card.angle);
    }
}