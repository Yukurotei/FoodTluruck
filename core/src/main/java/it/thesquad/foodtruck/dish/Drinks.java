package it.thesquad.foodtruck.dish;

public enum Drinks {
    WATER("Water"),
    SWEET_TEA("Sweet Tea"),
    JUICE("Juice"),
    SMOOTHIE("Smoothie"),
    WINE("Wine"),
    BEER("Beer"),
    COCKTAIL("Cocktail"),
    LEMONADE("Lemonade");

    private final String name;

    private Drinks(String name) {
        this.name = name;
    }

    /**
     * 
     * @return the category of the drink
     */
    public String getName() {
        return name;
    }
}
