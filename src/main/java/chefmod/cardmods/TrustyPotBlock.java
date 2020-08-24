package chefmod.cardmods;

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
        Vector2 vec = new Vector2(0, 0);
        vec.scl(card.drawScale * Settings.scale);
        vec.rotate(card.angle);
        Color color = Color.WHITE.cpy();
        color.a = card.transparency;
        sb.setColor(color);
        Texture hpImg = ImageMaster.BLOCK_ICON;
        sb.draw(hpImg,
                card.hb.cX + vec.x - hpImg.getWidth() / 2f,
                card.hb.cY + vec.y - hpImg.getHeight() / 2f,
                (float) hpImg.getWidth() / 2.0F,
                (float) hpImg.getHeight() / 2.0F,
                (float) hpImg.getWidth(),
                (float) hpImg.getHeight(),
                card.drawScale * 1.25f,
                card.drawScale * 1.25f,
                card.angle,
                0,
                0,
                hpImg.getWidth(),
                hpImg.getHeight(),
                false,
                false);
        String text = "+" + block;
        FontHelper.cardEnergyFont_L.getData().setScale(card.drawScale * 0.5f);
        BitmapFont font = FontHelper.cardEnergyFont_L;
        FontHelper.renderRotatedText(sb,
                font,
                text,
                card.hb.cX,
                card.hb.cY,
                vec.x,
                vec.y,
                card.angle,
                false,
                color);
    }
}
