package it.thesquad.foodtruck.dish;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.thesquad.foodtruck.Sizes;
import it.thesquad.foodtruck.ingredients.Ingredient;
import it.thesquad.foodtruck.logic.Sprite;


public class CompleteFood implements Ingredient {
    private Foods food;
    private Sizes size;
    private int totalAccuracy;
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    private Sprite sprite;

    /**
     *
     * @param food the food item
     * @param size the size of the food item
     */
    public CompleteFood(Foods food, Sizes size, Sprite sprite) {
        this.food = food;
        this.size = size;
        this.sprite = sprite;
    }

    /**
     *
     * @return the food item
     */
    public Foods getFood() {
        return food;
    }

    public int getAccuracy() {
        calcTotalAccuracy();
        return totalAccuracy;
    }

    public void calcTotalAccuracy() {
        totalAccuracy = 0;
        for (Ingredient ingredient : ingredients) totalAccuracy += ingredient.getAccuracy();
        
        if (ingredients.size() > 0) totalAccuracy = totalAccuracy / ingredients.size();
    }


    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
    /**
     *
     * @return the size of the food item
     */
    public Sizes getSize() {
        return size;
    }

    public void addIngredient(Ingredient i) {
        ingredients.add(i);
    }

    public void drawOnTable(SpriteBatch batch) {
        if (ingredients.size() != 0) {
            batch.setColor(ingredients.get(ingredients.size()-1).getOverlayColor());
            ingredients.get(ingredients.size()-1).getSprite().render(batch);
            batch.setColor(Color.WHITE);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (ingredients.size() != 0) {
            batch.setColor(ingredients.get(ingredients.size()-1).getOverlayColor());
            ingredients.get(ingredients.size()-1).getSprite().render(batch);
            batch.setColor(Color.WHITE);
        }
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public Color getOverlayColor() {
        return Color.CLEAR;
    }
}
