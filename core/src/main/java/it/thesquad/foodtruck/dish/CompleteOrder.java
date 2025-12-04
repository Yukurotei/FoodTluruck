package it.thesquad.foodtruck.dish;

public class CompleteOrder {
    private CompleteFood mainEntree;
    private CompleteFood sideDish;
    private CompleteFood dessertsDish;
    private Drinks drink;
    private boolean isComplete;

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
        this.isComplete = false;
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

    public void complete() {
        isComplete = true;
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


    public void setSideDish(CompleteFood c) {
        sideDish = c;
    }

    public void setDessertsDish(CompleteFood c) {
        dessertsDish = c;
    }

    public void setDrink(Drinks d) {
        drink = d;
    }
    /**
     * @return the obj to a string
     */
    public String toString() {
        return getMainEntree().getFood().getName()+" "+getSideDish().getFood().getName()+" "+getDessertsDish().getFood().getName()+" "+getDrink().getName();
    }

    public int getAccuracy() {
        return (mainEntree.getAccuracy() + sideDish.getAccuracy() + dessertsDish.getAccuracy())/3;
    }
}
