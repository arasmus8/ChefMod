package chefmod.cards.attacks;

import basemod.helpers.VfxBuilder;
import chefmod.cards.AbstractChefCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.Arrays;

import static chefmod.ChefMod.makeID;

public class Strike extends AbstractChefCard {
    public static String ID = makeID(Strike.class.getSimpleName());
    private static final ArrayList<CardTags> tagsList = new ArrayList<>(Arrays.asList(CardTags.STARTER_STRIKE, CardTags.STRIKE));

    public Strike() {
        super(ID,
                1,
                CardType.ATTACK,
                CardRarity.BASIC,
                CardTarget.ENEMY,
                tagsList
        );
        baseDamage = damage = 6;
        upgradeDamageBy = 3;
        damages = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            private boolean firstUpdate = true;

            public void iAmDone() {
                isDone = true;
            }

            @Override
            public void update() {
                if (firstUpdate) {
                    firstUpdate = false;
                    AbstractGameEffect vfx = new VfxBuilder(ImageMaster.TINY_STAR, 1f)
                            .setColor(Color.GOLD)
                            .setScale(4f)
                            .arc(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY, Settings.HEIGHT * 0.8f)
                            .andThen(2.5f)
                            .whenStarted(builder -> iAmDone())
                            .whenComplete(builder -> System.out.println(String.format("x:%f, y:%f, scalse:%f", builder.x, builder.y, builder.scale)))
                            .rotate(30f)
                            .velocity(MathUtils.random(45f, 135f), 500f)
                            .gravity(50f)
                            .build();
                    AbstractDungeon.effectList.add(vfx);
                }
            }
        });
        dealDamage(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }
}
