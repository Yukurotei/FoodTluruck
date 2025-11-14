package it.thesquad.foodtruck;

public enum Drinks {
    WATER("Water"),
    SWEET_TEA("Sweet Tea"),
    JUICE("Juice"),
    SMOOTHIE("Smoothie"),
    WINE("Wine"),
    BEER("Beer"),
    COCKTAIL("Cocktail"),
    LEMONADE("Lemonade");

    String name;

    private Drinks(String someShit) {
        this.name = someShit;
    }
}