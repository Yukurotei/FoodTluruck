package it.thesquad.foodtruck.ingredients;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Sprite;

public interface Ingredient {

    void draw(SpriteBatch batch);

    public abstract Sprite getSprite();
    public abstract int getAccuracy();


}
