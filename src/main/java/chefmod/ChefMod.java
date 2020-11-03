package chefmod;

import basemod.*;
import basemod.abstracts.CustomSavable;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import chefmod.cards.AbstractChefCard;
import chefmod.cards.AbstractOptionCard;
import chefmod.potions.AntifreezePotion;
import chefmod.potions.PotionOfPlenty;
import chefmod.recipe.NeowNuggetsRecipe;
import chefmod.recipe.RecipeManager;
import chefmod.relics.AbstractChefRelic;
import chefmod.ui.FrozenPileButton;
import chefmod.util.AssetLoader;
import chefmod.util.TextureHelper;
import chefmod.util.VfxMaster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@SpireInitializer
public class ChefMod implements
        CustomSavable<ArrayList<String>>,
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditKeywordsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        PostBattleSubscriber,
        PostDungeonUpdateSubscriber,
        PostDeathSubscriber,
        PostInitializeSubscriber,
        StartGameSubscriber {
    public static String IMAGE_PATH = "chefmodResources/images";
    private static String modID;

    public static CardGroup frozenPile;
    public static ArrayList<AbstractCard> cardsToFreeze;
    public static ArrayList<AbstractCard> frozenThisCombat;
    private static FrozenPileButton frozenPileButton;
    public static RecipeManager recipeManager;

    //Settings
    public static SpireConfig config;
    public static boolean cheesyRecipeAnimations = false;

    public static Color chefColor = new Color(0xCCC39AFF);

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath + ".png";
    }

    public static String makeImagePath(String imagePath) {
        return getModID() + "Resources/images/" + imagePath;
    }

    public static String assetPath(String path) {
        return "chefAssets/" + path;
    }

    public static AssetLoader assets = new AssetLoader();

    public ChefMod() {
        BaseMod.subscribe(this);

        modID = "chefmod";

        BaseMod.addColor(TheChef.Enums.CHEF_COLOR,
                chefColor,
                makeImagePath("cardframe/attack512.png"),
                makeImagePath("cardframe/skill512.png"),
                makeImagePath("cardframe/power512.png"),
                makeImagePath("cardframe/card_energy_orb.png"),
                makeImagePath("cardframe/attack1024.png"),
                makeImagePath("cardframe/skill1024.png"),
                makeImagePath("cardframe/power1024.png"),
                makeImagePath("cardframe/chef_energy_orb.png"),
                makeImagePath("cardframe/card_small_orb.png")
        );
    }

    public static String getModID() {
        return modID;
    }

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static void initialize() {
        ChefMod chefMod = new ChefMod();
    }

    private void saveSettings() {
        try {
            // And based on that boolean, set the settings and save them
            config = new SpireConfig("ChefMod", "chefModConfig");
            config.load();
            config.setBool("cheesyRecipeAnimations", cheesyRecipeAnimations);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receivePostInitialize() {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("Settings"));
        Texture badgeImage = TextureHelper.getTexture(IMAGE_PATH + "/Badge.png");

        try {
            config = new SpireConfig("ChefMod", "chefModConfig");
            config.load();
            cheesyRecipeAnimations = config.getBool("cheesyRecipeAnimations");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ModPanel panel = new ModPanel();
        // Create the on/off button:
        ModLabeledToggleButton toggleCrueltyModeButton = new ModLabeledToggleButton(uiStrings.TEXT[0],
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                cheesyRecipeAnimations,
                panel,
                (label) -> {
                },
                (button) -> {
                    cheesyRecipeAnimations = button.enabled;
                    saveSettings();
                });
        panel.addUIElement(toggleCrueltyModeButton);

        BaseMod.registerModBadge(badgeImage, "ChefMod", "NotInTheFace", "A character who's a chef.", panel);
        BaseMod.addSaveField("chefmod", this);
        ConsoleCommand.addCommand("recipe", RecipeConsoleCommand.class);
        VfxMaster.initialize();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheChef("the Chef", TheChef.Enums.THE_CHEF),
                makeImagePath("charselect/Button.png"),
                makeImagePath("charselect/charselect.png"),
                TheChef.Enums.THE_CHEF
        );

        BaseMod.addPotion(AntifreezePotion.class, AntifreezePotion.LIQUID_COLOR, AntifreezePotion.HYBRID_COLOR, AntifreezePotion.SPOTS_COLOR, AntifreezePotion.POTION_ID, TheChef.Enums.THE_CHEF);
        BaseMod.addPotion(PotionOfPlenty.class, PotionOfPlenty.LIQUID_COLOR, PotionOfPlenty.HYBRID_COLOR, PotionOfPlenty.SPOTS_COLOR, PotionOfPlenty.POTION_ID, TheChef.Enums.THE_CHEF);
    }

    @Override
    public void receiveEditCards() {
        TextureAtlas cardAtlas = (TextureAtlas) ReflectionHacks.getPrivateStatic(AbstractCard.class, "cardAtlas");

        TextureAtlas myCardAtlas = assets.loadAtlas(assetPath("images/cards/cards.atlas"));
        for (TextureAtlas.AtlasRegion region : myCardAtlas.getRegions()) {
            cardAtlas.addRegion(getModID() + "/" + region.name, region);
        }

        new AutoAdd(modID)
                .packageFilter(AbstractChefCard.class)
                .setDefaultSeen(true)
                .cards();
        new AutoAdd(modID)
                .packageFilter(AbstractOptionCard.class)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractChefRelic.class)
                .any(AbstractChefRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditStrings() {
        String lang = "eng";

        if (Settings.language == Settings.GameLanguage.ENG) {
            lang = "eng";
        }

        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/" + lang + "/chefmod-Card-Strings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/" + lang + "/chefmod-Relic-Strings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Resources/localization/" + lang + "/chefmod-Character-Strings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class, getModID() + "Resources/localization/" + lang + "/chefmod-Power-Strings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class, getModID() + "Resources/localization/" + lang + "/chefmod-UI-Strings.json");

        BaseMod.loadCustomStringsFile(PotionStrings.class, getModID() + "Resources/localization/" + lang + "/chefmod-Potion-Strings.json");
    }

    @Override
    public void receiveEditKeywords() {
        String lang = "eng";

        if (Settings.language == Settings.GameLanguage.ENG) {
            lang = "eng";
        }

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/" + lang + "/chefmod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveStartGame() {
        frozenPileButton = new FrozenPileButton();
        frozenPile = new CardGroup(CardGroup.CardGroupType.DRAW_PILE);
        cardsToFreeze = new ArrayList<>();
        frozenThisCombat = new ArrayList<>();
        recipeManager = new RecipeManager();
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        frozenPile.clear();
        cardsToFreeze.clear();
        frozenThisCombat.clear();
        recipeManager.clear();
        if (!abstractRoom.smoked && abstractRoom.eliteTrigger) {
            recipeManager.unlock(abstractRoom.monsters, AbstractDungeon.actNum);
        }
    }

    public static void renderFrozenPile(SpriteBatch spriteBatch, float x) {
        if (frozenPileButton != null) {
            frozenPileButton.setX(x);
            frozenPileButton.render(spriteBatch);
        }
    }

    @Override
    public void receivePostDungeonUpdate() {
        if (frozenPileButton != null) {
            frozenPileButton.update();
            recipeManager.update();
        }
    }

    @Override
    public void receivePostDeath() {
        RecipeManager.unlockedRecipes.clear();
    }

    @Override
    public ArrayList<String> onSave() {
        if (RecipeManager.unlockedRecipes.size() == 0) {
            RecipeManager.unlockedRecipes.add(NeowNuggetsRecipe.ID);
        }
        return RecipeManager.unlockedRecipes;
    }

    @Override
    public void onLoad(ArrayList<String> strings) {
        RecipeManager.unlockedRecipes.clear();
        if (strings != null) {
            RecipeManager.unlockedRecipes.addAll(strings.stream().distinct().collect(Collectors.toList()));
            if (RecipeManager.unlockedRecipes.size() == 0) {
                RecipeManager.unlockedRecipes.add(NeowNuggetsRecipe.ID);
            }
        } else {
            RecipeManager.unlockedRecipes.add(NeowNuggetsRecipe.ID);
        }
    }

    public static void renderCombatUiElements(SpriteBatch sb) {
        if (
                AbstractDungeon.isPlayerInDungeon() &&
                        AbstractDungeon.getCurrMapNode() != null &&
                        AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
                        AbstractDungeon.getMonsters() != null &&
                        !AbstractDungeon.getMonsters().areMonstersDead()
        ) {
            ChefMod.renderFrozenPile(sb, AbstractDungeon.overlayMenu.combatDeckPanel.current_x);
            ChefMod.recipeManager.render(sb);
        }
    }
}
