package it.thesquad.foodtruck.ingredients;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.player.Player;

public class Bun implements Ingredient {

    Sprite bun;

    public Bun(Sprite sprite) {
        this.bun = sprite;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (bun != null) {
            bun.setX(Player.getInstance().getX() + (Player.getInstance().getTexture().getWidth() / 2f) - (bun.getTexture().getWidth() / 2f));
            bun.setY(Player.getInstance().getY() + (Player.getInstance().getTexture().getHeight() / 2f) - (bun.getTexture().getHeight() / 2f) - 67);

            bun.render(batch);
        }
    }
}
