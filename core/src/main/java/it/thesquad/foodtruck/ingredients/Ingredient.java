package it.thesquad.foodtruck.ingredients;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Sprite;

public interface Ingredient {

    void draw(SpriteBatch batch);

    void drawOnTable(SpriteBatch batch);

    Sprite getSprite();


}
