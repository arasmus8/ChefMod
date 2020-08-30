package chefmod.util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public interface ActionUnit {
    default void qAction(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    DamageInfo makeDamageInfo(DamageInfo.DamageType type);

    default void dealDamage(AbstractMonster m, AbstractGameAction.AttackEffect fx, DamageInfo.DamageType damageType) {
        qAction(new DamageAction(m, makeDamageInfo(damageType), fx));
    }

    void dealAoeDamage(AbstractGameAction.AttackEffect fx, DamageInfo.DamageType damageType);

    void gainBlock();

    default void makeInHand(AbstractCard c, int i) {
        qAction(new MakeTempCardInHandAction(c, i));
    }

    default void makeInHand(AbstractCard c) {
        makeInHand(c, 1);
    }

    default void shuffleIn(AbstractCard c, int i) {
        qAction(new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    default void shuffleIn(AbstractCard c) {
        shuffleIn(c, 1);
    }

    default void topDeck(AbstractCard c, int i) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    default void topDeck(AbstractCard c) {
        topDeck(c, 1);
    }

    default ArrayList<AbstractMonster> monsterList() {
        ArrayList<AbstractMonster> monsters = new ArrayList<>(AbstractDungeon.getMonsters().monsters);
        monsters.removeIf(AbstractCreature::isDeadOrEscaped);
        return monsters;
    }

    default void applyToEnemy(AbstractMonster m, AbstractPower po) {
        qAction(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    default void applyToSelf(AbstractPower po) {
        qAction(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }
}
