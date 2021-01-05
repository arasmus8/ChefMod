package chefmod.potions;

import basemod.BaseMod;
import chefmod.actions.PlayOldestFrozenCardAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import java.util.stream.IntStream;

import static chefmod.ChefMod.makeID;

public class AntifreezePotion extends AbstractPotion {

    public static final String POTION_ID = makeID(AntifreezePotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final Color LIQUID_COLOR = new Color(0x99FFFFFF);
    public static final Color HYBRID_COLOR = new Color(0xCCCCCCFF);
    public static final Color SPOTS_COLOR = new Color(0xAA6699FF);

    public AntifreezePotion() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.RARE, PotionSize.BOTTLE, PotionColor.NONE);
    }

    @Override
    public void initializeData() {
        potency = getPotency();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        if (Settings.language == Settings.GameLanguage.ZHS) {
            tips.add(new PowerTip(
                    BaseMod.getKeywordTitle("chefmod:冷藏"),
                    BaseMod.getKeywordDescription("chefmod:冷藏")
            ));
        } else {
            tips.add(new PowerTip(
                    BaseMod.getKeywordTitle("chefmod:frozen"),
                    BaseMod.getKeywordDescription("chefmod:frozen")
            ));
        }
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        IntStream.rangeClosed(1, potency)
                .forEachOrdered(i -> addToBot(new PlayOldestFrozenCardAction()));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new AntifreezePotion();
    }

    @Override
    public int getPotency(final int potency) {
        return 5;
    }
}
