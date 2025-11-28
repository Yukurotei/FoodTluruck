package it.thesquad.foodtruck.dish;

public class CompleteOrder {
    private CompleteFood mainEntree;
    private CompleteFood sideDish;
    private CompleteFood dessertsDish;
    private Drinks drink;

    /**
     * 
     * @param mainEntree   the main entree food item of the order
     * @param sideDish     the side dish food item of the order
     * @param dessertsDish the dessert food item of the order
     * @param drink        the drink of the order
     */

    public CompleteOrder(CompleteFood mainEntree, CompleteFood sideDish, CompleteFood dessertsDish, Drinks drink) {
        this.mainEntree = mainEntree;
        this.sideDish = sideDish;
        this.dessertsDish = dessertsDish;
        this.drink = drink;
    }

    /**
     * 
     * @return the main entree food item of the order
     */
    public CompleteFood getMainEntree() {
        return mainEntree;

    }

    /**
     * 
     * @return the side dish food item of the order
     */
    public CompleteFood getSideDish() {
        return sideDish;
    }

    /**
     * 
     * @return the dessert food item of the order
     */
    public CompleteFood getDessertsDish() {
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
