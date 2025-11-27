package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;

public class DeepFryer extends Appliance {

    private Sprite currentItem;
    private Button pattyPile;
    private Button fryer;
    private boolean isFrying;

    /**
     *
     * @param texture the texture of the fryer
     * @param x the x-coordinate of the fryer
     * @param y the y-coordinate of the fryer
     * @param w the width of the fryer
     * @param h the height of the fryer
     */
    public DeepFryer(Texture texture, int x, int y, int w, int h) {
        super(texture, x, y, w, h);
    }

    @Override
    public void init() {
        System.out.println("Inited button");
        pattyPile = new Button(Utils.resizeTo(new Texture("raw_fries.png"), 200, 200), 10f, 10f, () -> {
            if (currentItem != null) return;
            System.out.println("Making fries");
            currentItem = new Sprite(Utils.resizeTo(new Texture("raw_fries.png"), 200, 200), Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), false);
        });
        Texture fryerTexture = Utils.resizeTo(new Texture("fryer.png"), 800, 600);
        fryer = new Button(fryerTexture, 400 - ((float) fryerTexture.getWidth() / 2), 300 - ((float) fryerTexture.getHeight() / 2), () -> {
            if (currentItem == null || isFrying) return;
            System.out.println("Recieved patty, now cooking it");
            isFrying = true;
            currentItem = null;
        });
    }

    /**
     *
     * @param batch the SpriteBatch used for rendering
     */
    @Override
    public void display(SpriteBatch batch) {
        batch.begin();
        batch.draw(new Texture("fryer.png"), 0, 0);

        pattyPile.renderButton(batch);
        fryer.renderButton(batch);
        if (currentItem != null) {
            currentItem.render(batch);
        }

        if (isFrying) {
            batch.draw(new Texture("raw_fries.png"), 341, 248);
        }
        batch.end();
    }

    @Override
    public void end() {
        System.out.println("Ending");
        pattyPile.dispose();
        fryer.dispose();
        if (currentItem != null) currentItem.dispose();
        pattyPile = null;
        currentItem = null;
    }

    @Override
    public void update(float dt) {
        if (pattyPile != null) pattyPile.update(dt);
        if (fryer != null) fryer.update(dt);
        if (currentItem != null) {
            currentItem.update(dt);
            currentItem.setX(Gdx.input.getX() - (float) currentItem.getSourceTexture().getWidth() / 2);
            currentItem.setY(Gdx.graphics.getHeight() - Gdx.input.getY() - (float) currentItem.getSourceTexture().getHeight() / 2);
        }
    }
}
