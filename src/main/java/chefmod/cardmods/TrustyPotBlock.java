package chefmod.cardmods;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
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

import javax.xml.soap.Text;

import static chefmod.ChefMod.makeID;

public class TrustyPotBlock extends AbstractChefCardmod {
    public TrustyPotBlock (int blockAmount) {
        ID = makeID(TrustyPotBlock.class.getSimpleName());
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

        sb.draw(shieldImg,
                card.current_x + vec.x - imgW / 2f,
                card.current_y + vec.y - imgH / 2f,
                imgW / 2.0F,
                imgH / 2.0F,
                imgW,
                imgH,
                card.drawScale * 1.25f,
                card.drawScale * 1.25f,
                card.angle,
                0,
                0,
                (int)imgW,
                (int)imgH,
                false,
                false);
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
