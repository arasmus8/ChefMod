package chefmod.cards.options;

import chefmod.ChefMod;
import chefmod.cards.AbstractChefCard;
import chefmod.recipe.SlaverSaladRecipe;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.cards.colorless.Blind;
import com.megacrit.cardcrawl.cards.colorless.Trip;

import static chefmod.ChefMod.makeID;

public class SlaverSalad extends AbstractChefCard {
    public static String ID = makeID(SlaverSalad.class.getSimpleName());

    private float switchPreviewTimer;

    public SlaverSalad() {
        super(ID,
                -2,
                CardType.SKILL,
                CardRarity.SPECIAL,
                CardTarget.SELF
        );

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
