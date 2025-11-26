package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;

public class Grill extends Appliance {

    private Sprite currentPatty;
    private Button pattyPile;
    private Button griller;
    private boolean isPattyCooking;

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
            if (currentPatty != null) return;
            System.out.println("Making patty");
            currentPatty = new Sprite(new Texture("patty.png"), Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), false);
        });
        Texture grillerTexture = new Texture("grillGrill.png");
        griller = new Button(grillerTexture, 400 - ((float) grillerTexture.getWidth() / 2), 300 - ((float) grillerTexture.getHeight() / 2), () -> {
            if (currentPatty == null) return;
            System.out.println("Recieved patty, now cooking it");
            isPattyCooking = true;
            currentPatty = null;
        });
    }

    /**
     *
     * @param batch the SpriteBatch used for rendering
     */
    @Override
    public void display(SpriteBatch batch) {
        batch.begin();
        batch.draw(Utils.resizeTo(new Texture("grillUI.png"), 800, 600), 0, 0);

        pattyPile.renderButton(batch);
        griller.renderButton(batch);
        if (currentPatty != null) {
            currentPatty.render(batch);
        }

        if (isPattyCooking) {
            batch.draw(new Texture("patty.png"), 341, 248);
        }
        batch.end();
    }

    @Override
    public void end() {
        System.out.println("Ending");
        pattyPile.dispose();
        griller.dispose();
        if (currentPatty != null) currentPatty.dispose();
        pattyPile = null;
        currentPatty = null;
    }

    @Override
    public void update(float dt) {
        if (pattyPile != null) pattyPile.update(dt);
        if (griller != null) griller.update(dt);
        if (currentPatty != null) {
            currentPatty.update(dt);
            currentPatty.setX(Gdx.input.getX() - (float) currentPatty.getSourceTexture().getWidth() / 2);
            currentPatty.setY(Gdx.graphics.getHeight() - Gdx.input.getY() - (float) currentPatty.getSourceTexture().getHeight() / 2);
        }
    }
}
