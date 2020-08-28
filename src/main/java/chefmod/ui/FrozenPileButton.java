package chefmod.ui;

import basemod.ClickableUIElement;
import chefmod.ChefMod;
import chefmod.patches.frozen.ExhaustPileViewScreenPatches;
import chefmod.util.TextureLoader;
import chefmod.vfx.SnowParticleManager;
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
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;

import static chefmod.ChefMod.makeImagePath;

public class FrozenPileButton extends ClickableUIElement {
    private static final float X_OFF = 0f;
    private static final float Y_OFF = 228f;
    private static final float HB_WIDTH = 128f;
    private static final float HB_HEIGHT = 128f;
    private static float COUNT_X = 48.0F * Settings.scale;
    private static float COUNT_Y = -16.0F * Settings.scale;
    private static final float COUNT_OFFSET_X = 48.0F * Settings.scale;
    private static final float COUNT_OFFSET_Y = -18.0F * Settings.scale;
    private static final float DECK_TIP_X = 0F * Settings.scale;
    private static final float DECK_TIP_Y = 128.0F * Settings.scale;
    private final GlyphLayout gl = new GlyphLayout();
    private static final float COUNT_CIRCLE_W = 128.0F * Settings.scale;
    private static final Texture frozenDeck = TextureLoader.getTexture(makeImagePath("frozenDeck.png"));
    private static SnowParticleManager snowParticleManager;

    public FrozenPileButton() {
        super((Texture) null,
                X_OFF,
                Y_OFF,
                HB_WIDTH,
                HB_HEIGHT);
        snowParticleManager = new SnowParticleManager(hitbox.cX, hitbox.cY);
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
        ExhaustPileViewScreenPatches.showFrozen = true;
        AbstractDungeon.exhaustPileViewScreen.open();
    }

    @Override
    public void update() {
        super.update();
        snowParticleManager.update(hitbox.cX, hitbox.cY);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        if (ChefMod.frozenPile != null) {
            if (!AbstractDungeon.overlayMenu.combatDeckPanel.isHidden) {
                snowParticleManager.render(sb, hitbox.cX, hitbox.cY);
                sb.setColor(Color.WHITE);
                float w = frozenDeck.getWidth();
                float h = frozenDeck.getHeight();
                sb.draw(frozenDeck,
                        hitbox.cX - w / 2.0F,
                        hitbox.cY - h / 2.0F,
                        w / 2f,
                        h / 2f,
                        w,
                        h,
                        Settings.scale,
                        Settings.scale,
                        0f,
                        0,
                        0,
                        (int)w,
                        (int)h,
                        false,
                        false);

                String msg = Integer.toString(ChefMod.frozenPile.size());
                gl.setText(FontHelper.eventBodyText, msg);
                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.DECK_COUNT_CIRCLE,
                        hitbox.cX + COUNT_OFFSET_X - COUNT_CIRCLE_W / 2.0F,
                        hitbox.cY + COUNT_OFFSET_Y - COUNT_CIRCLE_W / 2.0F,
                        COUNT_CIRCLE_W,
                        COUNT_CIRCLE_W);
                FontHelper.renderFontCentered(sb, FontHelper.speech_font, msg, hitbox.cX + COUNT_X, hitbox.cY + COUNT_Y);

                hitbox.render(sb);
                if (hitbox.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
                    TipHelper.renderGenericTip(hitbox.cX + DECK_TIP_X, hitbox.cY + DECK_TIP_Y, "Frozen Pile", "This is the Frozen Pile");
                }
            }
        }
    }
}
