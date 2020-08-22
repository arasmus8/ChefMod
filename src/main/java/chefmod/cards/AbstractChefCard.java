package chefmod.cards;

import basemod.abstracts.CustomCard;
import chefmod.TheChef;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static chefmod.ChefMod.getModID;
import static chefmod.ChefMod.makeCardPath;

public abstract class AbstractChefCard extends CustomCard {

    protected final CardStrings cardStrings;
    protected final String NAME;
    protected final String DESCRIPTION;
    protected final String UPGRADE_DESCRIPTION;
    protected final String[] EXTENDED_DESCRIPTION;

    protected boolean damages = false;
    protected boolean blocks = false;
    protected Integer upgradeDamageBy;
    protected Integer upgradeBlockBy;
    protected Integer upgradeMagicNumberBy;
    protected Integer upgradeCostTo;

    public AbstractChefCard(final String id,
                            final int cost,
                            final CardType type,
                            final CardRarity rarity,
                            final CardTarget target) {
        this(id, cost, type, rarity, target, TheChef.Enums.CHEF_COLOR, null);
    }

    public AbstractChefCard(final String id,
                            final int cost,
                            final CardType type,
                            final CardRarity rarity,
                            final CardTarget target,
                            final List<CardTags> tagsList) {
        this(id, cost, type, rarity, target, TheChef.Enums.CHEF_COLOR, tagsList);
    }

    public AbstractChefCard(final String id,
                            final int cost,
                            final CardType type,
                            final CardRarity rarity,
                            final CardTarget target,
                            final CardColor color,
                            final List<CardTags> tagsList) {
        super(id, "ERROR", getCorrectPlaceholderImage(type, id),
                cost, "ERROR", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
        if (tagsList != null) {
            tags.addAll(tagsList);
        }
    }

    public static String getCorrectPlaceholderImage(CardType type, String id) {
        String img = makeCardPath(id.replaceAll((getModID() + ":"), "") + ".png");
        if ((!Gdx.files.internal(img).exists()))
            switch (type) {
                case ATTACK:
                    return makeCardPath("Attack.png");
                case SKILL:
                    return makeCardPath("Skill.png");
                case POWER:
                    return makeCardPath("Power.png");
            }
        return img;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
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

    protected DamageInfo makeDamageInfo() {
        return makeDamageInfo(damageTypeForTurn);
    }

    private DamageInfo makeDamageInfo(DamageInfo.DamageType type) {
        return new DamageInfo(AbstractDungeon.player, damage, type);
    }

    public void dealDamage(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        addToBot(new DamageAction(m, makeDamageInfo(), fx));
    }

    public void dealAoeDamage(AbstractGameAction.AttackEffect fx) {
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    public void gainBlock() {
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public void makeInHand(AbstractCard c, int i) {
        addToBot(new MakeTempCardInHandAction(c, i));
    }

    public void makeInHand(AbstractCard c) {
        makeInHand(c, 1);
    }

    private void shuffleIn(AbstractCard c, int i) {
        addToBot(new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    public void shuffleIn(AbstractCard c) {
        shuffleIn(c, 1);
    }

    public void topDeck(AbstractCard c, int i) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    public void topDeck(AbstractCard c) {
        topDeck(c, 1);
    }

    public ArrayList<AbstractMonster> monsterList() {
        ArrayList<AbstractMonster> monsters = new ArrayList<>(AbstractDungeon.getMonsters().monsters);
        monsters.removeIf(AbstractCreature::isDeadOrEscaped);
        return monsters;
    }

    public void applyToEnemy(AbstractMonster m, AbstractPower po) {
        addToBot(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public void applyToSelf(AbstractPower po) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }
}
