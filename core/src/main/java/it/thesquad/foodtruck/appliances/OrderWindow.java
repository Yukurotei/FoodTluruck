package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.graphics.Texture;
import it.thesquad.foodtruck.Main;
import it.thesquad.foodtruck.Sizes;
import it.thesquad.foodtruck.dish.CompleteFood;
import it.thesquad.foodtruck.dish.Foods;
import it.thesquad.foodtruck.ingredients.Burger;
import it.thesquad.foodtruck.player.Player;

public class OrderWindow extends Table {


    /**
     * @param texture The texture used for the table created
     * @param x       The x-coordinate where the top-left corner of the table will be
     * @param y       The y-coordinate where the top-left corner of the table will be
     */
    public OrderWindow(Texture texture, int x, int y) {
        super(texture, x, y);
    }

    @Override
    public void interact(Player player) {
        super.interact(player);
        if (tableItem != null) {
            if (Main.customerQueue.getElm(0).getOrder().getMainEntree().getFood() != null) {
                if (Main.customerQueue.getElm(0).getOrder().getMainEntree().getFood() == Foods.BURGER && tableItem instanceof Burger) {
                    Main.customerQueue.getElm(0).getCompleteOrder().setMainEntree(new CompleteFood(Foods.BURGER, Sizes.MEDIUM, tableItem.getSprite()));
                    Main.customerQueue.getElm(0).getCompleteOrder().getMainEntree().addIngredient(((Burger) tableItem).getPatty());
                    Main.customerQueue.getElm(0).getCompleteOrder().complete(); //TODO: TEMP, CHECK IF ALL ORDERS ARE SATISFIED THEN COMPLETE
                    tableItem = null;
                    this.end();
                    Main.gameState = Main.GameState.WORLD;
                }
            }
        }
    }

}
