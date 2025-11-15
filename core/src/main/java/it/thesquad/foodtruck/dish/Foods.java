package it.thesquad.foodtruck.dish;

public enum Foods {
    // Mains
    BURGER("Burger", Category.MAIN),
    RIBS("Ribs", Category.MAIN),
    BRISKET("Brisket", Category.MAIN),
    SAUSAGES("Sausages", Category.MAIN),
    POUTINE("Poutine", Category.MAIN),
    Korean_Fried_Chicken("Korean Fried Chicken", Category.MAIN),


    // Sides
    FRIES("Fries", Category.SIDE),
    SOUP("Soup", Category.SIDE),
    ONION_RINGS("Onion Rings", Category.SIDE),
    COLESLAW("Coleslaw", Category.SIDE),
    MAC_N_CHEESE("Mac n Cheese", Category.SIDE),
    SALAD("Salad", Category.SIDE),

    // Desserts
    PEACH_COBBLER("Peach Cobbler", Category.DESSERT),
    PIE("Pie", Category.DESSERT),
    COOKIES("Cookies", Category.DESSERT),
    ICE_CREAM("Ice Cream", Category.DESSERT),
    CARROT_CAKE("Carrot Cake", Category.DESSERT);

    private final String name;
    private final Category category;

    private Foods(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public enum Category {
        MAIN,
        SIDE,
        DESSERT
    }
}
