package it.thesquad.foodtruck.ingredients;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.player.Player;

public class Pickle implements Ingredient {

    private Sprite pickle;

    public Pickle(Sprite pickle) {
        this.pickle = pickle;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (pickle != null) {
            pickle.setX(Player.getInstance().getX() + (Player.getInstance().getTexture().getWidth() / 2f) - (pickle.getTexture().getWidth() / 2f));
            pickle.setY(Player.getInstance().getY() + (Player.getInstance().getTexture().getHeight() / 2f) - (pickle.getTexture().getHeight() / 2f) - 67);

            pickle.render(batch);
            batch.setColor(Color.WHITE);
        }
    }

    @Override
    public void drawOnTable(SpriteBatch batch) {
        pickle.render(batch);
        batch.setColor(Color.WHITE);
    }

    @Override
    public Sprite getSprite() {
        return pickle;
    }

    @Override
    public int getAccuracy() {
        return 0;
    }

    @Override
    public Color getOverlayColor() {
        return Color.CLEAR;
    }
}
