package chefmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import chefmod.util.TextureHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Optional;

import static chefmod.ChefMod.makeID;

public class BonusDamageCardmod extends AbstractChefCardmod {
    public static String ID = makeID(BonusDamageCardmod.class.getSimpleName());

    public BonusDamageCardmod(int damageAmount) {
        damage = damageAmount;
    }

    public static void add(AbstractCard card, int damageAmount) {
        Optional<AbstractChefCardmod> current = getForCard(card, ID);
        if (current.isPresent()) {
            current.get().damage += damageAmount;
        } else {
            CardModifierManager.addModifier(card, new PermanentDamageBonusCardmod(damageAmount));
        }
    }

    @Override
    public float modifyDamage(float current, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + current;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BonusDamageCardmod(damage);
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        Vector2 vec = new Vector2(-140, 130);
        vec.scl(card.drawScale * Settings.scale);
        vec.rotate(card.angle);

        Color color = Color.WHITE.cpy();
        color.a = card.transparency;
        sb.setColor(color);

        TextureHelper.drawScaledAndRotated(sb, ImageMaster.INTENT_ATK_1, card.current_x + vec.x, card.current_y + vec.y, 1.25f, card.angle);

        String text = "+" + damage;
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
