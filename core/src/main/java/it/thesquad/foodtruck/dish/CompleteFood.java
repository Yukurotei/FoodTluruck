package it.thesquad.foodtruck.dish;

import java.util.ArrayList;

import it.thesquad.foodtruck.Sizes;
import it.thesquad.foodtruck.ingredients.Ingredient;


public class CompleteFood {
    private Foods food;
    private Sizes size;
    private int totalAccuracy;
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    /**
     *
     * @param food the food item
     * @param size the size of the food item
     */
    public CompleteFood(Foods food, Sizes size) {
        this.food = food;
        this.size = size;
    }

    /**
     *
     * @return the food item
     */
    public Foods getFood() {
        return food;
    }

    public int getAccuracy() {
        calcTotal();
        return totalAccuracy;
    }

    public void calcTotal() {
        totalAccuracy = 0;
        for (Ingredient ingredient : ingredients) {
            totalAccuracy += ingredient.getAccuracy();
        }
        if (ingredients.size() > 0) {
            totalAccuracy = totalAccuracy / ingredients.size();
        }
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
}
