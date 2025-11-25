package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;

public class Grill extends Appliance {

    private Sprite currentPatty;
    private Button pattyPile;

    /**
     *
     * @param texture the texture of the grill
     * @param x the x-coordinate of the grill
     * @param y the y-coordinate of the grill
     * @param w the width of the grill
     * @param h the height of the grill
     */
    public Grill(Texture texture, int x, int y, int w, int h) {
        super(texture, x, y, w, h);
    }

    @Override
    public void init() {
        System.out.println("Inited button");
        pattyPile = new Button(new Texture("patty.png"), 10f, 10f, () -> {
            System.out.println("I GOT FUCKED");
        });
    }

    /**
     *
     * @param batch the SpriteBatch used for rendering
     */
    @Override
    public void display(SpriteBatch batch) {
        batch.begin();
        pattyPile.renderButton(batch);
        batch.end();
    }

    @Override
    public void end() {
        System.out.println("Disposing button");
        pattyPile.dispose();
    }

    @Override
    public void update(float dt) {

    }
}
