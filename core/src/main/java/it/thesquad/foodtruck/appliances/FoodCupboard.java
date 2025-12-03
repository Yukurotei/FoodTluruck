package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.ingredients.Lettuce;
import it.thesquad.foodtruck.ingredients.Pickle;
import it.thesquad.foodtruck.ingredients.Tomato;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.player.Player;

public class FoodCupboard extends Appliance {

    private Button picklePile;
    private Button tomatoPile;
    private Button lettucePile;
    private Texture cupboardTexture;

    /**
     *
     * @param texture the texture of the appliance
     * @param x       the x-coordinate of the appliance
     * @param y       the y-coordinate of the appliance
     */
    public FoodCupboard(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void init() {
        cupboardTexture = Utils.resizeTo(new Texture("foodCupboard.png"), 400);

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
        lettucePile = new Button(new Texture("lettuce.png"), 300, 300, () -> {
            if (Player.getInstance().getCurrentIngredient() == null) {
                Player.getInstance().setCurrentIngredient(new Lettuce(new Sprite(Utils.resizeTo(new Texture("lettuce.png"), 50), 0, 0, false)));
                lettucePile.dispose();
                this.lettucePile = null;
            }
        });
    }

    @Override
    public void display(SpriteBatch batch) {
        batch.draw(cupboardTexture, -150, -150);

        if (picklePile != null) picklePile.renderButton(batch);
        if (tomatoPile != null) tomatoPile.renderButton(batch);
        if (lettucePile != null) lettucePile.renderButton(batch);

    }

    @Override
    public void update(float dt) {
        if (picklePile != null) picklePile.update(dt);
        if (tomatoPile != null) tomatoPile.update(dt);
        if (lettucePile != null) lettucePile.update(dt);
    }

    @Override
    public void end() {
        if (picklePile != null) picklePile.dispose();
        if (tomatoPile != null) tomatoPile.dispose();
        if (lettucePile != null) lettucePile.dispose();
        cupboardTexture.dispose();
    }
}
