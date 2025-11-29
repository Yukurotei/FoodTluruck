package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.thesquad.foodtruck.Main;
import it.thesquad.foodtruck.dish.CompleteFood;
import it.thesquad.foodtruck.dish.CompleteOrder;
import it.thesquad.foodtruck.ingredients.Ingredient;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.player.Player;

public class AssemblyTable extends Appliance {

    private Texture tableUiTexture;

    private CompleteFood tableItem = new CompleteFood(null, null);

    private Button tableButton;

    private float interactCooldown = 0f;


    public AssemblyTable(Texture texture, int x, int y) {
        super(texture, x, y);
    }

    @Override
    public void init() {
        tableUiTexture = Utils.resizeTo(new Texture("table.png"), 800, 600);

        // Table interaction button
        tableButton = new Button(
            tableUiTexture,
            400 - (tableUiTexture.getWidth() / 2f),
            300 - (tableUiTexture.getHeight() / 2f),
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

        /*
        Ingredient playerIngredient = Player.getInstance().getCurrentIngredient();
        if (playerIngredient != null) {
            playerIngredient.getSprite().render(batch);
        }
         */

        batch.end();
    }

    @Override
    public void end() {
        if (tableButton != null) tableButton.dispose();

        if (tableUiTexture != null) tableUiTexture.dispose();

        tableButton = null;
        tableUiTexture = null;
        //tableItem = null;
    }

    @Override
    public void update(float dt) {

        if (interactCooldown > 0)
            interactCooldown -= dt;

        if (interactCooldown < 0)
            interactCooldown = 0;

        if (tableButton != null) tableButton.update(dt);

        // if (tableItem != null && tableItem.getSprite() != null) {
        //     tableItem.getSprite().update(dt);
        // }

        /*
        Ingredient playerIngredient = Player.getInstance().getCurrentIngredient();
        if (playerIngredient != null && playerIngredient.getSprite() != null) {
            playerIngredient.getSprite().update(dt);
        }
         */
    }



    private void interact(Player player) {
        if (interactCooldown != 0) return;
        if (player.getCurrentIngredient() != null) {
            tableItem.addIngredient(player.getCurrentIngredient());
            player.setCurrentIngredient(null);

            Sprite s = tableItem.getIngredients().get(tableItem.getIngredients().size()-1).getSprite();
            s.setX(400 - s.getWidth() / 2f);
            s.setY(300 - s.getHeight() / 2f);

            System.out.println(tableItem.getAccuracy());
        }
    }

}
