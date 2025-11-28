package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.ingredients.Ingredient;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.player.Player;

public class Table extends Appliance {

    private Texture tableUiTexture;
    private Texture tableTexture;

    // The item currently on the table
    private Ingredient tableItem;

    // Button representing the table’s interact area
    private Button tableButton;


    public Table(Texture texture, int x, int y, int w, int h) {
        super(texture, x, y, w, h);
    }

    @Override
    public void init() {
        tableUiTexture = new Texture("table.png");
        tableTexture = new Texture("table.png");

        // Table interaction button
        tableButton = new Button(
            tableTexture,
            400 - (tableTexture.getWidth() / 2f),
            300 - (tableTexture.getHeight() / 2f),
            () -> {
                Player player = Player.getInstance();

                // Picking up the item
                if (tableItem != null && player.getCurrentIngredient() == null) {
                    player.setCurrentIngredient(tableItem);
                    tableItem = null;
                    return;
                }

                // Placing an item
                if (tableItem == null && player.getCurrentIngredient() != null) {
                    tableItem = player.getCurrentIngredient();
                    player.setCurrentIngredient(null);

                    // Move item sprite to table center
                    Sprite s = tableItem.getSprite();
                    s.setX(400 - s.getWidth() / 2f);
                    s.setY(300 - s.getHeight() / 2f);

                }

            }
        );
    }

    @Override
    public void display(SpriteBatch batch) {
        batch.begin();

        // Draw the table background
        batch.draw(tableUiTexture, 0, 0);

        // Draw the table interact button
        tableButton.renderButton(batch);

        // Draw item if present
        if (tableItem != null) {
            tableItem.getSprite().render(batch);
        }

        batch.end();
    }

    @Override
    public void end() {
        if (tableButton != null) tableButton.dispose();

        if (tableUiTexture != null) tableUiTexture.dispose();
        if (tableTexture != null) tableTexture.dispose();

        tableButton = null;
        tableUiTexture = null;
        tableTexture = null;
        tableItem = null;
    }

    @Override
    public void update(float dt) {

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.Q)) {
            interact(Player.getInstance());
        }

        if (tableButton != null) tableButton.update(dt);

        if (tableItem != null && tableItem.getSprite() != null) {
            tableItem.getSprite().update(dt);
        }
    }


    private void interact(Player player) {

        // pic from table
        if (tableItem != null && player.getCurrentIngredient() == null) {
            player.setCurrentIngredient(tableItem);
            tableItem = null;
            return;
        }

        // place on table
        if (tableItem == null && player.getCurrentIngredient() != null) {
            tableItem = player.getCurrentIngredient();
            player.setCurrentIngredient(null);

            Sprite s = tableItem.getSprite();
            s.setX(400 - s.getWidth() / 2f);
            s.setY(300 - s.getHeight() / 2f);
        }
    }



}
