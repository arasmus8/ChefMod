package chefmod;

import basemod.abstracts.CustomPlayer;
import chefmod.cards.attacks.Strike;
import chefmod.cards.skills.Defend;
import chefmod.cards.skills.QuickThaw;
import chefmod.cards.skills.TodaysSpecial;
import chefmod.energyorb.ChefEnergyOrb;
import chefmod.relics.TrustyPot;
import chefmod.vfx.VictoryVfx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static chefmod.ChefMod.*;

public class TheChef extends CustomPlayer {
    private static final String ID = makeID("theChef");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    public static final String BODY = makeImagePath("character/chef-character.png");
    public static final String SHOULDER1 = makeImagePath("character/shoulder2.png");
    public static final String SHOULDER2 = makeImagePath("character/shoulder.png");
    public static final String CORPSE = makeImagePath("character/corpse.png");

    public TheChef(String name, AbstractPlayer.PlayerClass setClass) {
        super(name, setClass, new ChefEnergyOrb(), null, null);
        initializeClass(BODY,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 20.0F, -10.0F, 166.0F, 327.0F, new EnergyManager(3));


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                76, 76, 0, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        IntStream.rangeClosed(1, 5)
                .forEach(i -> retVal.add(Strike.ID));
        IntStream.rangeClosed(1, 3)
                .forEach(i -> retVal.add(Defend.ID));
        retVal.add(QuickThaw.ID);
        retVal.add(TodaysSpecial.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(TrustyPot.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("MONSTER_BOOK_STAB_0", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "MONSTER_BOOK_STAB_0";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 8;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.CHEF_COLOR;
    }

    @Override
    public Color getCardTrailColor() {
        return chefColor.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new QuickThaw();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheChef(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return chefColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return chefColor.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_VERTICAL};
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(makeImagePath("heart1.png"), "ATTACK_DAGGER_6"));
        panels.add(new CutscenePanel(makeImagePath("heart2.png"), "ATTACK_FIRE"));
        panels.add(new CutscenePanel(makeImagePath("heart3.png")));
        return panels;
    }

    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {
        for (AbstractGameEffect effect : effects) {
            if (effect instanceof VictoryVfx) {
                // already created the effect
                return;
            }
        }
        effects.add(new VictoryVfx());
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_CHEF;
        @SpireEnum(name = "CHEF_COLOR")
        public static AbstractCard.CardColor CHEF_COLOR;
        @SpireEnum(name = "CHEF_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
