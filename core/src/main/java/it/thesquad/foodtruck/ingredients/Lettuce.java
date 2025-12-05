package it.thesquad.foodtruck.ingredients;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.player.Player;

public class Lettuce implements Ingredient {

    private final Sprite lettuce;
    private int accuracy = 5;

    public Lettuce(Sprite lettuce) {
        this.lettuce = lettuce;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (lettuce != null) {
            lettuce.setX(Player.getInstance().getX() + (Player.getInstance().getTexture().getWidth() / 2f) - (lettuce.getTexture().getWidth() / 2f));
            lettuce.setY(Player.getInstance().getY() + (Player.getInstance().getTexture().getHeight() / 2f) - (lettuce.getTexture().getHeight() / 2f) - 67);

            lettuce.render(batch);
            batch.setColor(Color.WHITE);
        }
    }

    @Override
    public void drawOnTable(SpriteBatch batch) {
        lettuce.render(batch);
        batch.setColor(Color.WHITE);
    }

    @Override
    public Sprite getSprite() {
        return lettuce;
    }

    @Override
    public int getAccuracy() {
        return accuracy;
    }

    @Override
    public Color getOverlayColor() {
        return Color.CLEAR;
    }
}
