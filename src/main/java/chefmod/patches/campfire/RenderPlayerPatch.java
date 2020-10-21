package chefmod.patches.campfire;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(
        clz = AbstractRoom.class,
        method = "render"
)
public class RenderPlayerPatch {
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getClassName().equals(AbstractPlayer.class.getName()) && m.getMethodName().equals("render")) {
                    String dungeon = AbstractDungeon.class.getName();
                    String restRoom = RestRoom.class.getName();
                    String campUi = CampfireUI.class.getName();
                    m.replace("{ if (!(" + dungeon + ".getCurrRoom() instanceof " + restRoom + ") || " + campUi + ".hidden) { $proceed($$); } }");
                }
            }
        };
    }
}
