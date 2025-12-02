package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.ingredients.Bun;
import it.thesquad.foodtruck.ingredients.Burger;
import it.thesquad.foodtruck.ingredients.Ingredient;
import it.thesquad.foodtruck.ingredients.Patty;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.player.Player;

import java.util.ArrayList;
import java.util.List;

public class AssemblyTable extends Table {

    private final List<Ingredient> items;

    private final int offset = 20;

    public AssemblyTable(Texture texture, int x, int y) {
        super(texture, x, y);
        items = new ArrayList<>();
    }

    @Override
    public void display(SpriteBatch batch) {
        batch.begin();

        tableButton.renderButton(batch);

        for (Ingredient ingredient : items) {
            ingredient.drawOnTable(batch);
        }

        batch.end();
    }

    @Override
    public void update(float dt) {
        if (interactCooldown > 0) interactCooldown -= dt;

        if (interactCooldown < 0) interactCooldown = 0;

        if (tableButton != null) tableButton.update(dt);

        for (Ingredient ingredient : items) {
            ingredient.getSprite().update(dt);
        }
    }

    private void updateIngredientPos() {
        for (int i = 0; i < items.size(); i++) {
            Ingredient current = items.get(i);
            current.getSprite().setX(400 - current.getSprite().getWidth() / 2f);
            current.getSprite().setY(300 - current.getSprite().getHeight() / 2f);
            current.getSprite().setX(current.getSprite().getX() + (offset * i));
        }
    }

    @Override
    protected void interact(Player player) {
        if (interactCooldown != 0) return;
        if (player.getCurrentIngredient() == null && tableItem != null) {

            player.setCurrentIngredient(tableItem);
            if (!items.isEmpty()) {
                items.remove(tableItem);
                updateIngredientPos();
                if (!items.isEmpty()) {
                    tableItem = items.get(items.size() - 1);
                } else {
                    tableItem = null;
                }
            } else {
                tableItem = null;
            }

        } else if (player.getCurrentIngredient() != null) {
            if (items.size() >= 5) return;
            tableItem = player.getCurrentIngredient();
            player.setCurrentIngredient(null);
            items.add(tableItem);

            Sprite s = tableItem.getSprite();
            s.setX(400 - s.getWidth() / 2f);
            s.setY(300 - s.getHeight() / 2f);

            updateIngredientPos();

            //Food check
            Bun bun = null;
            Patty patty = null;
            for (Ingredient ingredient : items) {
                if (ingredient instanceof Bun) bun = (Bun) ingredient;
                if (ingredient instanceof Patty) patty = (Patty) ingredient;
            }

            //Food checks
            if (bun != null && patty != null) {
                items.remove(bun);
                items.remove(patty);
                items.add(new Burger(new Sprite(Utils.resizeTo(new Texture("burger.png"), 60), tableItem.getSprite().getX(), tableItem.getSprite().getY(), false)));
                updateIngredientPos();
                tableItem = items.get(items.size() - 1);
            }
        }
    }
}
