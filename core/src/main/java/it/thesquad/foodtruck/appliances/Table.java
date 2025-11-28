package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.thesquad.foodtruck.Main;
import it.thesquad.foodtruck.ingredients.Ingredient;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.player.Player;

public class Table extends Appliance {

    private Texture tableUiTexture;

    // The item currently on the table
    private Ingredient tableItem = null;

    // Button representing the table’s interact area
    private Button tableButton;

    private boolean justInteracted = false;
    private float interactCooldown = 0f;



    public Table(Texture texture, int x, int y, int w, int h) {
        super(texture, x, y, w, h);
    }

    @Override
    public void init() {
        tableUiTexture = new Texture("table.png");

        // Table interaction button
        tableButton = new Button(
            tableUiTexture,
            400 - (tableUiTexture.getWidth() / 2f),
            300 - (tableUiTexture.getHeight() / 2f),
            () -> interact(Player.getInstance())
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

        // Draw player's held item
        Ingredient playerIngredient = Player.getInstance().getCurrentIngredient();
        if (playerIngredient != null) {
            playerIngredient.getSprite().render(batch);
        }

        batch.end();
    }

    @Override
    public void end() {
        // if (tableButton != null) tableButton.dispose();

        // if (tableUiTexture != null) tableUiTexture.dispose();

        // tableButton = null;
        // tableUiTexture = null;
        //tableItem = null;
    }

    @Override
    public void update(float dt) {

        if (interactCooldown > 0)
            interactCooldown -= dt;

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q) && interactCooldown <= 0f) {
            interact(Player.getInstance());
            interactCooldown = 0.25f; // 250ms debounce
        }

        if (tableButton != null) tableButton.update(dt);

        if (tableItem != null && tableItem.getSprite() != null) {
            tableItem.getSprite().update(dt);
        }

        Ingredient playerIngredient = Player.getInstance().getCurrentIngredient();
        if (playerIngredient != null && playerIngredient.getSprite() != null) {
            playerIngredient.getSprite().update(dt);
        }
    }



    private void interact(Player player) {


        // Pick up from table
        if (player.getCurrentIngredient() == null && tableItem != null) {

            player.setCurrentIngredient(tableItem);
            tableItem = null;

            Sprite s = player.getCurrentIngredient().getSprite();
            s.setX(-1);
            s.setY(-1);

            return;
        }

        // Place on table
        if (tableItem == null && player.getCurrentIngredient() != null) {
            tableItem = player.getCurrentIngredient();
            player.setCurrentIngredient(null);

            Sprite s = tableItem.getSprite();
            s.setX(400 - s.getWidth() / 2f);
            s.setY(300 - s.getHeight() / 2f);

            return;
        }
    }

}
