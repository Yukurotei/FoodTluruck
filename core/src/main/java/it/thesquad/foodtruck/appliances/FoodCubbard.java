package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.ingredients.Pickle;
import it.thesquad.foodtruck.ingredients.Tomato;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.player.Player;

public class FoodCubbard extends Appliance {

    private Button picklePile;
    private Button tomatoPile;

    /**
     *
     * @param texture the texture of the appliance
     * @param x       the x-coordinate of the appliance
     * @param y       the y-coordinate of the appliance
     */
    public FoodCubbard(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void init() {
        picklePile = new Button(new Texture("pickle.png"), 200, 200, () -> {
            if (Player.getInstance().getCurrentIngredient() == null) {
                Player.getInstance().setCurrentIngredient(new Pickle(new Sprite(new Texture("pickle.png"), 0, 0, false)));
                picklePile.dispose();
                this.picklePile = null;
            }
        });
        tomatoPile = new Button(new Texture("tomato.png"), 400, 200, () -> {
            if (Player.getInstance().getCurrentIngredient() == null) {
                Player.getInstance().setCurrentIngredient(new Tomato(new Sprite(new Texture("tomato.png"), 0, 0, false)));
                tomatoPile.dispose();
                this.tomatoPile = null;
            }
        });
    }

    @Override
    public void display(SpriteBatch batch) {
        batch.begin();

        if (picklePile != null) picklePile.renderButton(batch);
        if (tomatoPile != null) tomatoPile.renderButton(batch);

        batch.end();
    }

    @Override
    public void update(float dt) {
        if (picklePile != null) picklePile.update(dt);
        if (tomatoPile != null) tomatoPile.update(dt);
    }

    @Override
    public void end() {
        if (picklePile != null) picklePile.dispose();
        if (tomatoPile != null) tomatoPile.dispose();
    }
}
