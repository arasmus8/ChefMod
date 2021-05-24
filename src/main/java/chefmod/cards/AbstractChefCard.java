package chefmod.cards;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import chefmod.util.ActionUnit;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;

import static chefmod.ChefMod.*;
import static chefmod.TheChef.Enums.CHEF_COLOR;

public abstract class AbstractChefCard extends CustomCard implements ActionUnit {

    public boolean frozen = false;
    public boolean nofreeze = false;

    protected final CardStrings cardStrings;
    protected final String NAME;
    protected final String DESCRIPTION;
    protected final String UPGRADE_DESCRIPTION;
    protected final String[] EXTENDED_DESCRIPTION;

    public boolean damages = false;
    public boolean blocks = false;
    public boolean hasPreppedActions = false;
    protected Integer upgradeDamageBy;
    protected Integer upgradeBlockBy;
    protected Integer upgradeMagicNumberBy;
    protected Integer upgradeCostTo;

    public AbstractChefCard(final String id,
                            final int cost,
                            final CardType type,
                            final CardRarity rarity,
                            final CardTarget target) {
        this(id, cost, type, rarity, target, CHEF_COLOR, null);
    }

    public AbstractChefCard(final String id,
                            final int cost,
                            final CardType type,
                            final CardRarity rarity,
                            final CardTarget target,
                            final List<CardTags> tagsList) {
        this(id, cost, type, rarity, target, CHEF_COLOR, tagsList);
    }

    public AbstractChefCard(final String id,
                            final int cost,
                            final CardType type,
                            final CardRarity rarity,
                            final CardTarget target,
                            final CardColor color,
                            final List<CardTags> tagsList) {
        super(id, "", getRegionName(id), cost, "", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
        if (color == CHEF_COLOR) {
            setCorrectBannerImage();
            setCorrectFrameImage();
        }
        if (tagsList != null) {
            tags.addAll(tagsList);
        }
    }

    protected static String getBaseImagePath(String id) {
        return id.replaceAll(getModID() + ":", "");
    }

    protected static CustomCard.RegionName getRegionName(String id) {
        return new RegionName(String.format("%s/%s", getModID(), getBaseImagePath(id)));
    }

    @Override
    public void loadCardImage(String img) {
        TextureAtlas cardAtlas = ReflectionHacks.getPrivateStatic(AbstractCard.class, "cardAtlas");
        portrait = cardAtlas.findRegion(img);
    }

    @Override
    protected Texture getPortraitImage() {
        return ImageMaster.loadImage(makeCardPath(String.format("%s.png", getBaseImagePath(cardID))));
    }

    private void setCorrectBannerImage() {
        if (rarity == CardRarity.RARE) {
            setBannerTexture(makeImagePath("cardframe/rare_banner_512.png"), makeImagePath("cardframe/rare_banner_1024.png"));
        } else if (rarity == CardRarity.UNCOMMON) {
            setBannerTexture(makeImagePath("cardframe/uncommon_banner_512.png"), makeImagePath("cardframe/uncommon_banner_1024.png"));
        } else {
            setBannerTexture(makeImagePath("cardframe/common_banner_512.png"), makeImagePath("cardframe/common_banner_1024.png"));
        }
    }

    private void setCorrectFrameImage() {
        if (type == CardType.ATTACK) {
            setPortraitTextures(makeImagePath("cardframe/attack_frame_512.png"), makeImagePath("cardframe/attack_frame_1024.png"));
        } else if (type == CardType.POWER) {
            setPortraitTextures(makeImagePath("cardframe/power_frame_512.png"), makeImagePath("cardframe/power_frame_1024.png"));
        } else {
            setPortraitTextures(makeImagePath("cardframe/skill_frame_512.png"), makeImagePath("cardframe/skill_frame_1024.png"));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded && canUpgrade()) {
            upgradeName();
            if (upgradeDamageBy != null) {
                upgradeDamage(upgradeDamageBy);
            }
            if (upgradeBlockBy != null) {
                upgradeBlock(upgradeBlockBy);
            }
            if (upgradeMagicNumberBy != null) {
                upgradeMagicNumber(upgradeMagicNumberBy);
            }
            if (upgradeCostTo != null) {
                upgradeBaseCost(upgradeCostTo);
            }
            if (UPGRADE_DESCRIPTION != null) {
                rawDescription = UPGRADE_DESCRIPTION;
            }
            initializeDescription();
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (hasPreppedActions) {
            if (isPrepped()) {
                glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
            } else {
                glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
            }
        }
    }

    protected boolean isPrepped() {
        AbstractPlayer p = AbstractDungeon.player;
        CardGroup hand = p.hand;
        return hand.size() > 0 && this == hand.getBottomCard();
    }

    public static boolean isNoFreeze(AbstractCard card) {
        if (card instanceof AbstractChefCard) {
            AbstractChefCard cc = (AbstractChefCard) card;
            return cc.nofreeze;
        }
        return false;
    }

    public DamageInfo makeDamageInfo(DamageInfo.DamageType type) {
        return new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn);
    }

    @Override
    public void dealDamage(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        addToBot(new DamageAction(m, makeDamageInfo(damageTypeForTurn), fx));
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) { }

    @Override
    public void dealAoeDamage(AbstractGameAction.AttackEffect fx) {
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    public void gainBlock() {
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public void triggerWhenFrozen() {
    }

    public void triggerWhenShuffled() {
    }

    @Override
    public void triggerWhenDrawn() {
        frozen = false;
    }
}
