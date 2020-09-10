package chefmod.ui;

import basemod.ClickableUIElement;
import chefmod.ChefMod;
import chefmod.patches.frozen.ExhaustPileViewScreenPatches;
import chefmod.util.TextureHelper;
import chefmod.vfx.SnowParticleManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BobEffect;

import static chefmod.ChefMod.makeImagePath;

public class FrozenPileButton extends ClickableUIElement {
    private static final float X_OFF = 0f;
    private static final float Y_OFF = 228f;
    private static final float HB_WIDTH = 128f;
    private static final float HB_HEIGHT = 128f;
    private static final float COUNT_X = 48.0F * Settings.scale;
    private static final float COUNT_Y = -16.0F * Settings.scale;
    private static final float COUNT_OFFSET_X = 48.0F * Settings.scale;
    private static final float COUNT_OFFSET_Y = -18.0F * Settings.scale;
    private static final float DECK_TIP_X = 0F * Settings.scale;
    private static final float DECK_TIP_Y = 128.0F * Settings.scale;
    private static final float BOB_DISTANCE = 22F * Settings.scale;
    private static final Texture frozenDeck = TextureHelper.getTexture(makeImagePath("frozenDeck.png"));
    private static SnowParticleManager snowParticleManager;

    private BobEffect bob;

    public FrozenPileButton() {
        super((Texture) null,
                X_OFF,
                Y_OFF,
                HB_WIDTH,
                HB_HEIGHT);
        snowParticleManager = new SnowParticleManager(hitbox.cX, hitbox.cY);
        bob = new BobEffect(1f);
    }

    @Override
    protected void onHover() {
    }

    @Override
    protected void onUnhover() {
    }

    @Override
    protected void onClick() {
        if (!AbstractDungeon.isScreenUp) {
            ExhaustPileViewScreenPatches.showFrozen = true;
            AbstractDungeon.exhaustPileViewScreen.open();
        }
    }

    @Override
    public void update() {
        super.update();
        snowParticleManager.update(hitbox.cX, hitbox.cY);
        bob.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        if (ChefMod.frozenPile != null && ChefMod.frozenPile.size() > 0) {
            if (!AbstractDungeon.overlayMenu.combatDeckPanel.isHidden) {
                snowParticleManager.render(sb, hitbox.cX, hitbox.cY);
                sb.setColor(Color.WHITE);
                TextureHelper.draw(sb, frozenDeck, hitbox.cX, hitbox.cY + bob.y);

                String msg = Integer.toString(ChefMod.frozenPile.size());
                sb.setColor(Color.WHITE);
                TextureHelper.draw(sb,
                        ImageMaster.DECK_COUNT_CIRCLE,
                        hitbox.cX + COUNT_OFFSET_X,
                        hitbox.cY + COUNT_OFFSET_Y);
                FontHelper.renderFontCentered(sb, FontHelper.speech_font, msg, hitbox.cX + COUNT_X, hitbox.cY + COUNT_Y);

                hitbox.render(sb);
                if (hitbox.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
                    TipHelper.renderGenericTip(hitbox.cX + DECK_TIP_X, hitbox.cY + DECK_TIP_Y, "Frozen Pile", "This is the Frozen Pile");
                }
            }
        }
    }
}
