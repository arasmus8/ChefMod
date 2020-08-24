package chefmod.ui;

import basemod.ClickableUIElement;
import chefmod.ChefMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class FrozenPileButton extends ClickableUIElement {
    private static final float X_OFF = 45f;
    private static final float Y_OFF = 128f;
    private static final float HB_WIDTH = 100f;
    private static final float HB_HEIGHT = 100f;
    private final GlyphLayout gl = new GlyphLayout();
    private static final float COUNT_CIRCLE_W = 128.0F * Settings.scale;

    public FrozenPileButton () {
        super((Texture) null,
                X_OFF,
                Y_OFF,
                HB_WIDTH,
                HB_HEIGHT);
    }

    @Override
    protected void onHover() {
    }

    @Override
    protected void onUnhover() {
    }

    @Override
    protected void onClick() {
        System.out.println("Frozen Pile Clicked");
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        if (ChefMod.frozenPile != null) {
            if (!AbstractDungeon.overlayMenu.combatDeckPanel.isHidden) {
                String msg = Integer.toString(ChefMod.frozenPile.size());
                gl.setText(FontHelper.eventBodyText, msg);
                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.DECK_COUNT_CIRCLE, hitbox.cX - COUNT_CIRCLE_W / 2.0F, hitbox.cY - COUNT_CIRCLE_W / 2.0F, COUNT_CIRCLE_W, COUNT_CIRCLE_W);
                FontHelper.renderFontCentered(sb, FontHelper.speech_font, msg, hitbox.cX, hitbox.cY + 2.0F * Settings.scale);

                hitbox.render(sb);
                if (hitbox.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
                    TipHelper.renderGenericTip(hitbox.cX, hitbox.cY + 150.0F * Settings.scale, "Frozen Pile", "This is the Frozen Pile");
                }
            }
        }
    }
}
