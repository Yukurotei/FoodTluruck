package it.thesquad.foodtruck;

public static enum Foods {
    // Mains
    BURGER("Burger"),
    RIBS("Ribs"),
    BRISKET("Brisket"),
    SAUSAGES("Sausages"),
    POUTINE("Poutine"),
    Korean_Fried_Chicken("Korean Fried Chicken"),


    // Sides
    FRIES("Fries"),
    SOUP("Soup"),
    ONION_RINGS("Onion Rings"),
    COLESLAW("Coleslaw"),
    MAC_N_CHEESE("Mac n Cheese"),
    SALAD("Salad"),

    // Desserts
    PEACH_COBBLER("Peach Cobbler"),
    PIE("Pie"),
    COOKIES("Cookies"),
    ICE_CREAM("Ice Cream"),
    CARROT_CAKE("Carrot Cake");

    private String name;

    private Foods(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
