package it.thesquad.foodtruck.ingredients;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.player.Player;

public class Bun implements Ingredient {

    private Sprite bread;
    private int accuracy;


    public Bun(Sprite bread) {
        this.bread = bread;
    }


    @Override
    public void draw(SpriteBatch batch) {
        if (bread != null) {
            bread.setX(Player.getInstance().getX() + (Player.getInstance().getTexture().getWidth() / 2f) - (bread.getTexture().getWidth() / 2f));
            bread.setY(Player.getInstance().getY() + (Player.getInstance().getTexture().getHeight() / 2f) - (bread.getTexture().getHeight() / 2f) - 67);

            bread.render(batch);
            batch.setColor(Color.WHITE);
        }
    }

    @Override
    public void drawOnTable(SpriteBatch batch) {
        if (bread != null) {
            bread.render(batch);
            batch.setColor(Color.WHITE);
        }
    }

    public Sprite getSprite() {
        return bread;
    }

    public void setBread(Sprite bread) {
        this.bread = bread;
    }


    @Override
    public int getAccuracy() {
        return accuracy;
    }
}
