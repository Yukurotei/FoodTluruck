package it.thesquad.foodtruck;

import it.thesquad.foodtruck.dish.Drinks;
import it.thesquad.foodtruck.dish.Food;

public class Order {
    private Food mainEntree;
    private Food sideDish;
    private Food dessertsDish;
    private Drinks drink;

    /**
     *
     * @param mainEntree   the main entree food item of the order
     * @param sideDish     the side dish food item of the order
     * @param dessertsDish the dessert food item of the order
     * @param drink        the drink of the order
     */

    public Order(Food mainEntree, Food sideDish, Food dessertsDish, Drinks drink) {
        this.mainEntree = mainEntree;
        this.sideDish = sideDish;
        this.dessertsDish = dessertsDish;
        this.drink = drink;
    }

    /**
     *
     * @return the main entree food item of the order
     */
    public Food getMainEntree() {
        return mainEntree;

    }

    /**
     *
     * @return the side dish food item of the order
     */
    public Food getSideDish() {
        return sideDish;
    }

    /**
     *
     * @return the dessert food item of the order
     */
    public Food getDessertsDish() {
        return dessertsDish;
    }

    /**
     *
     * @return the drink of the order
     */
    public Drinks getDrink() {
        return drink;
    }

    /**
     * @return the obj to a string
     */
    public String toString() {
        return getMainEntree().getFood().getName()+" "+getSideDish().getFood().getName()+" "+getDessertsDish().getFood().getName()+" "+getDrink().getName();
    }
}
