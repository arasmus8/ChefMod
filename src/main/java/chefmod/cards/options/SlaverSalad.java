package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractOptionCard;
import chefmod.recipe.SlaverSaladRecipe;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.cards.colorless.Blind;
import com.megacrit.cardcrawl.cards.colorless.Trip;

import static chefmod.ChefMod.makeID;

@SuppressWarnings("unused")
public class SlaverSalad extends AbstractOptionCard {
    public static String ID = makeID(SlaverSalad.class.getSimpleName());

    private float switchPreviewTimer;

    public SlaverSalad() {
        super(ID);

        switchPreviewTimer = 2f;
        cardsToPreview = new BandageUp();
    }

    @Override
    public void update() {
        super.update();
        switchPreviewTimer -= Gdx.graphics.getDeltaTime();
        if (switchPreviewTimer <= 0f) {
            switchPreviewTimer = 2f;
            if (cardsToPreview.cardID.equals(BandageUp.ID)) {
                cardsToPreview = new Blind();
            } else if (cardsToPreview.cardID.equals(Blind.ID)) {
                cardsToPreview = new Trip();
            } else {
                cardsToPreview = new BandageUp();
            }
        }
    }

    @Override
    public void onChoseThisOption() {
        ChefMod.recipeManager.startRecipe(new SlaverSaladRecipe());
    }
}
