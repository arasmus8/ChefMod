package chefmod.ui;

import basemod.ClickableUIElement;
import chefmod.ChefMod;
import chefmod.TheChef;
import chefmod.patches.frozen.ExhaustPileViewScreenPatches;
import chefmod.util.TextureHelper;
import chefmod.vfx.SnowParticleManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import static chefmod.ChefMod.makeID;
import static chefmod.ChefMod.makeImagePath;

public class FrozenPileButton extends ClickableUIElement {
    private static final float X_OFF = 0f; // 200f * Settings.scale;
    private static final float Y_OFF = 228f;
    private static final float HB_WIDTH = 128f;
    private static final float HB_HEIGHT = 128f;
    private static final float COUNT_X = 48.0F * Settings.scale;
    private static final float COUNT_Y = -16.0F * Settings.scale;
    private static final float COUNT_OFFSET_X = 48.0F * Settings.scale;
    private static final float COUNT_OFFSET_Y = -18.0F * Settings.scale;
    private static final float DECK_TIP_X = 0F * Settings.scale;
    private static final float DECK_TIP_Y = 128.0F * Settings.scale;
    private final Texture frozenDeck = TextureHelper.getTexture(makeImagePath("frozenDeck.png"));
    private static SnowParticleManager snowParticleManager;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("FrozenPile"));
    public static final String[] TEXT = uiStrings.TEXT;

    private final BobEffect bob;

    private boolean isOpen = false;

    public FrozenPileButton() {
        super((Texture) null,
                0f,
                Y_OFF,
                HB_WIDTH,
                HB_HEIGHT);
        snowParticleManager = new SnowParticleManager(hitbox.cX, hitbox.cY);
        bob = new BobEffect(1.1f);
    }

    @Override
    protected void onHover() {
    }

    @Override
    protected void onUnhover() {
    }

    @Override
    protected void onClick() {
        if (isOpen && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.EXHAUST_VIEW) {
            isOpen = false;
            CardCrawlGame.sound.play("DECK_CLOSE");
            AbstractDungeon.closeCurrentScreen();
        } else if (!AbstractDungeon.isScreenUp) {
            if (ChefMod.frozenPile.isEmpty()) {
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, TEXT[2], true));
            } else {
                ExhaustPileViewScreenPatches.showFrozen = true;
                AbstractDungeon.exhaustPileViewScreen.open();
                isOpen = true;
            }
        }
    }

    @Override
    public void setX(float x) {
        super.setX(x - X_OFF);
    }

    @Override
    public void update() {
        super.update();
        snowParticleManager.update(hitbox.cX, hitbox.cY);
        bob.update();
        if (Gdx.input.isKeyJustPressed(ChefMod.frozenPileKey)) {
            onClick();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (ChefMod.frozenPile != null && (AbstractDungeon.player.chosenClass == TheChef.Enums.THE_CHEF || ChefMod.frozenPile.size() > 0)) {
            if (!AbstractDungeon.overlayMenu.combatDeckPanel.isHidden) {
                float x = hitbox.x + hitbox.width / 2f;
                float y = hitbox.y + hitbox.height / 2f;
                snowParticleManager.render(sb, x, y);
                sb.setColor(Color.WHITE);
                TextureHelper.draw(sb, frozenDeck, x, y + bob.y);

                String msg = Integer.toString(ChefMod.frozenPile.size());
                sb.setColor(Color.WHITE);
                TextureHelper.draw(sb,
                        ImageMaster.DECK_COUNT_CIRCLE,
                        x + COUNT_OFFSET_X,
                        y + COUNT_OFFSET_Y);
                FontHelper.renderFontCentered(sb, FontHelper.turnNumFont, msg, x + COUNT_X, y + COUNT_Y);

                hitbox.render(sb);
                if (hitbox.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
                    TipHelper.renderGenericTip(x + DECK_TIP_X, y + DECK_TIP_Y, TEXT[0], TEXT[1]);
                }
            }
        }
    }
}
