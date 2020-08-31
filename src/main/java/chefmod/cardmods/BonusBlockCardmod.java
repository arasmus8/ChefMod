package chefmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import chefmod.util.TextureHelper;
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

import static chefmod.ChefMod.makeID;

public class BonusBlockCardmod extends AbstractChefCardmod {
    public BonusBlockCardmod(int blockAmount) {
        ID = makeID(BonusBlockCardmod.class.getSimpleName());
        block = blockAmount;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
            gainBlock();
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BonusBlockCardmod(block);
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        Texture shieldImg = ImageMaster.BLOCK_ICON;
        float imgW = shieldImg.getWidth();
        float imgH = shieldImg.getHeight();
        Vector2 vec = new Vector2(-140, 130);
        vec.scl(card.drawScale * Settings.scale);
        vec.rotate(card.angle);

        Color color = Color.WHITE.cpy();
        color.a = card.transparency;
        sb.setColor(color);

        TextureHelper.drawScaledAndRotated(sb, ImageMaster.BLOCK_ICON, card.current_x + vec.x, card.current_y + vec.y, 1.25f, card.angle);

        String text = "+" + block;
        FontHelper.cardEnergyFont_L.getData().setScale(card.drawScale * 0.5f);

        BitmapFont font = FontHelper.cardEnergyFont_L;
        FontHelper.renderRotatedText(sb,
                font,
                text,
                card.current_x + vec.x,
                card.current_y + vec.y,
                0,
                0,
                card.angle,
                false,
                color);
    }
}
