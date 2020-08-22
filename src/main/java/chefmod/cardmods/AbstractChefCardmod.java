package chefmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Optional;

public abstract class AbstractChefCardmod extends AbstractCardModifier {
    protected String ID;
    protected int damage = 0;
    protected int block = 0;

    public static Optional<AbstractChefCardmod> getForCard(AbstractCard card, String cardmodID) {
        ArrayList<AbstractCardModifier> modifiers = CardModifierManager.getModifiers(card, cardmodID);
        if (modifiers.size() > 0) {
            AbstractChefCardmod m = (AbstractChefCardmod) modifiers.get(0);
            return Optional.of(m);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        try {
            AbstractChefCardmod copy = this.getClass().newInstance();
            copy.ID = ID;
            copy.damage = damage;
            copy.block = block;
            return copy;
        } catch (IllegalAccessException | InstantiationException var2) {
            throw new RuntimeException("Failed to auto-generate makeCopy for card_modifier: " + this.ID);
        }
    }

    public void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    private DamageInfo makeDamageInfo(DamageInfo.DamageType type) {
        return new DamageInfo(AbstractDungeon.player, damage, type);
    }

    public void dealDamage(AbstractMonster m, AbstractGameAction.AttackEffect fx, DamageInfo.DamageType damageType) {
        addToBot(new DamageAction(m, makeDamageInfo(damageType), fx));
    }

    public void dealAoeDamage(AbstractGameAction.AttackEffect fx, DamageInfo.DamageType damageType) {
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(damage), damageType, fx));
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
}
