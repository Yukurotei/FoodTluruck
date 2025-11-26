package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;

public class Fridge extends Appliance {

    private Sprite currentDrink;
    private Button drinkPile;

    /**
     *
     * @param texture the texture of the grill
     * @param x the x-coordinate of the grill
     * @param y the y-coordinate of the grill
     * @param w the width of the grill
     * @param h the height of the grill
     */
    public Fridge(Texture texture, int x, int y, int w, int h) {
        super(texture, x, y, w, h);
    }

    @Override
    public void init() {
        System.out.println("Inited button, is fridge");
        drinkPile = new Button(new Texture("patty.png"), 10f, 10f, () -> {
            if (currentDrink != null) return;
            System.out.println("Making drinking");
            currentDrink = new Sprite(new Texture("patty.png"), Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), false);
        });
    }

    /**
     *
     * @param batch the SpriteBatch used for rendering
     */
    @Override
    public void display(SpriteBatch batch) {
        batch.begin();
        drinkPile.renderButton(batch);
        if (currentDrink != null) {
            currentDrink.render(batch);
        }
        batch.end();
    }

    @Override
    public void end() {
        System.out.println("Ending");
        drinkPile.dispose();
        if (currentDrink != null) currentDrink.dispose();
        drinkPile = null;
        currentDrink = null;
    }

    @Override
    public void update(float dt) {
        if (drinkPile != null) drinkPile.update(dt);
        if (currentDrink != null) {
            currentDrink.update(dt);
            currentDrink.setX(Gdx.input.getX() - (float) currentDrink.getSourceTexture().getWidth() / 2);
            currentDrink.setY(Gdx.graphics.getHeight() - Gdx.input.getY() - (float) currentDrink.getSourceTexture().getHeight() / 2);
        }
    }
}
