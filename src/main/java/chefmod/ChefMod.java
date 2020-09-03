package chefmod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import chefmod.cards.AbstractChefCard;
import chefmod.recipe.NeowNuggets;
import chefmod.recipe.RecipeManager;
import chefmod.relics.AbstractChefRelic;
import chefmod.ui.FrozenPileButton;
import chefmod.util.TextureHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
        PostInitializeSubscriber,
        StartGameSubscriber {
    public static String IMAGE_PATH = "chefmodResources/images";
    private static String modID;
    private static String artifactID;

    public static CardGroup frozenPile;
    public static ArrayList<AbstractCard> cardsToFreeze;
    private static FrozenPileButton frozenPileButton;
    public static RecipeManager recipeManager;

    public static Color chefColor = new Color(237, 242, 244, 1); //TODO: Set this to your character's favorite color!

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
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeImagePath(String imagePath) {
        return getModID() + "Resources/images/" + imagePath;
    }

    public ChefMod() {
        BaseMod.subscribe(this);

        modID = "chefmod";
        artifactID = "chefmod";

        BaseMod.addColor(TheChef.Enums.CHEF_COLOR,
                chefColor,
                chefColor,
                chefColor,
                chefColor,
                chefColor,
                chefColor,
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

    @SuppressWarnings("unused")
    public static void initialize() {
        ChefMod chefMod = new ChefMod();
    }

    @Override
    public void receivePostInitialize() {
        Texture badgeImage = TextureHelper.getTexture(IMAGE_PATH + "/Badge.png");
        BaseMod.registerModBadge(badgeImage, "ChefMod", "NotInTheFace", "A character who's a chef.", null);
        BaseMod.addSaveField("chefmod", this);
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheChef("the Chef", TheChef.Enums.THE_CHEF),
                makeImagePath("charselect/Button.png"),
                makeImagePath("charselect/charselect.png"),
                TheChef.Enums.THE_CHEF
        );
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(artifactID)
                .packageFilter(AbstractChefCard.class)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(artifactID)
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
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/eng/chefmod-Card-Strings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/eng/chefmod-Relic-Strings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Resources/localization/eng/chefmod-Character-Strings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class, getModID() + "Resources/localization/eng/chefmod-Power-Strings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class, getModID() + "Resources/localization/eng/chefmod-UI-Strings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/chefmod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
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
        recipeManager = new RecipeManager();
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        frozenPile.clear();
        cardsToFreeze.clear();
        recipeManager.clear();
        if (!abstractRoom.smoked && abstractRoom.eliteTrigger) {
            recipeManager.unlock(abstractRoom.monsters, AbstractDungeon.actNum);
        }
    }

    public static void renderFrozenPile(SpriteBatch spriteBatch) {
        if (frozenPileButton != null) {
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
    public ArrayList<String> onSave() {
        if (RecipeManager.unlockedRecipes.size() == 0) {
            RecipeManager.unlockedRecipes.add(NeowNuggets.ID);
        }
        return RecipeManager.unlockedRecipes;
    }

    @Override
    public void onLoad(ArrayList<String> strings) {
        RecipeManager.unlockedRecipes.clear();
        if (strings != null) {
            RecipeManager.unlockedRecipes.addAll(strings);
            if (RecipeManager.unlockedRecipes.size() == 0) {
                RecipeManager.unlockedRecipes.add(NeowNuggets.ID);
            }
        } else {
            RecipeManager.unlockedRecipes.add(NeowNuggets.ID);
        }
    }
}
