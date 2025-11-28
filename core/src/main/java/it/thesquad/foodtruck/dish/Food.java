package it.thesquad.foodtruck.dish;

import it.thesquad.foodtruck.Sizes;


public class Food {
    private Foods food;
    private Sizes size;
    private int accuracy;

    /**
     *
     * @param food the food item
     * @param size the size of the food item
     */
    public Food(Foods food, Sizes size) {
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
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    /**
     *
     * @return the size of the food item
     */
    public Sizes getSize() {
        return size;
    }
}
