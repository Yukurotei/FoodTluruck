package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.thesquad.foodtruck.Main;
import it.thesquad.foodtruck.ingredients.Ingredient;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.player.Player;

public class Table extends Appliance {
    protected Texture tableUiTexture;
    protected Ingredient tableItem = null;
    protected Button tableButton;
    protected float interactCooldown = 0f;


    /**
     * @param texture The texture used for the table created
     * @param x The x-coordinate where the top-left corner of the table will be
     * @param y The y-coordinate where the top-left corner of the table will be
     */
    public Table(Texture texture, int x, int y) {
        super(texture, x, y);
    }

    @Override
    public void init() {
        tableUiTexture = Utils.resizeTo(new Texture("table.png"), 800, 600);

        // Table interaction button
        tableButton = new Button(
            tableUiTexture,
            centerAxis(400, tableUiTexture.getWidth()),
            centerAxis(300, tableUiTexture.getHeight()),
            () -> {
                interact(Player.getInstance());
                interactCooldown = 0.5f;
            }
        );
    }

    

    @Override
    public void display(SpriteBatch batch) {
        batch.begin();

        tableButton.renderButton(batch);

        if (tableItem != null) {
            tableItem.drawOnTable(batch);
        }

        batch.end();
    }

    @Override
    public void end() {
        if (tableButton != null) tableButton.dispose();

        if (tableUiTexture != null) tableUiTexture.dispose();

        tableButton = null;
        tableUiTexture = null;
    }

    @Override
    public void update(float dt) {
        if (interactCooldown > 0) interactCooldown -= dt;
        if (interactCooldown < 0) interactCooldown = 0;
        if (tableButton != null) tableButton.update(dt);
        if (tableItem != null && tableItem.getSprite() != null) tableItem.getSprite().update(dt);
    }



    protected void interact(Player player) {
        if (interactCooldown != 0) return;

        if (player.getCurrentIngredient() == null && tableItem != null) {

            player.setCurrentIngredient(tableItem);
            tableItem = null;

        } else if (tableItem == null && player.getCurrentIngredient() != null) {
            tableItem = player.getCurrentIngredient();
            player.setCurrentIngredient(null);

            Sprite sprite = tableItem.getSprite();
            sprite.setX(400 - sprite.getWidth() / 2f);
            sprite.setY(300 - sprite.getHeight() / 2f);
        }
    }
}
