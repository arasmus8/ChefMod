package chefmod.cardmods;

import chefmod.ChefMod;
import chefmod.util.TextureHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

import static chefmod.ChefMod.makeID;
import static chefmod.ChefMod.makeImagePath;

public class IngredientCardmod extends AbstractChefCardmod {
    private static final Texture icon = TextureHelper.getTexture(makeImagePath("vfx/ingredient_badge.png"));
    public static final String CARDMOD_ID = makeID(IngredientCardmod.class.getSimpleName());

    public IngredientCardmod() {
        ID = CARDMOD_ID;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        ChefMod.recipeManager.useIngredient(card);
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        float imgW = icon.getWidth();
        float imgH = icon.getHeight();
        Vector2 vec = new Vector2(140, 130);
        vec.scl(card.drawScale * Settings.scale);
        vec.rotate(card.angle);

        Color color = Color.WHITE.cpy();
        color.a = card.transparency;
        sb.setColor(color);

        sb.draw(icon,
                card.current_x + vec.x - imgW / 2f,
                card.current_y + vec.y - imgH / 2f,
                imgW / 2.0F,
                imgH / 2.0F,
                imgW,
                imgH,
                card.drawScale * 1.25f * Settings.scale,
                card.drawScale * 1.25f * Settings.scale,
                card.angle,
                0,
                0,
                (int) imgW,
                (int) imgH,
                false,
                false);
    }
}