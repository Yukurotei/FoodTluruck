package it.thesquad.foodtruck.ingredients;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.player.Player;

public class Tomato implements Ingredient {

    private Sprite tomato;
    private int accuracy = 5;

    public Tomato(Sprite tomato) {
        this.tomato = tomato;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (tomato != null) {
            tomato.setX(Player.getInstance().getX() + (Player.getInstance().getTexture().getWidth() / 2f) - (tomato.getTexture().getWidth() / 2f));
            tomato.setY(Player.getInstance().getY() + (Player.getInstance().getTexture().getHeight() / 2f) - (tomato.getTexture().getHeight() / 2f) - 67);

            tomato.render(batch);
            batch.setColor(Color.WHITE);
        }
    }

    @Override
    public void drawOnTable(SpriteBatch batch) {
        tomato.render(batch);
        batch.setColor(Color.WHITE);
    }

    @Override
    public Sprite getSprite() {
        return tomato;
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
