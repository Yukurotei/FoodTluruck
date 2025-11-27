package it.thesquad.foodtruck.ingredients;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.player.Player;

public class Fry implements Ingredient {

    private Sprite fry;
    private int cookedPercentage;

    private final Color friedColor = new Color(219/255f, 108/255f, 141/255f, 1f); // Target fried color (219, 108, 141)

    public Fry(Sprite fry) {
        this.fry = fry;
        this.cookedPercentage = 0;
    }

    public Fry(Sprite fry, int cookedPercentage) {
        this.fry = fry;
        this.cookedPercentage = cookedPercentage;
    }

    /**
     * Calculates the overlay color for the fry based on its cooked percentage.
     * Transitions from white (raw) to friedColor, then to black (burnt).
     *
     * @return The calculated tint color.
     */
    private Color getOverlayColor() {
        Color tint;
        float currentCookedPercentage = this.cookedPercentage;

        if (currentCookedPercentage < 50f) {
            float progress = currentCookedPercentage / 50f;
            tint = new Color(Color.WHITE).lerp(friedColor, progress);
        } else {
            float progress = Math.min((currentCookedPercentage - 50f) / 50f, 1.0f);
            tint = new Color(friedColor).lerp(Color.BLACK, progress);
        }
        return tint;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (fry != null) {
            // Position the fry relative to the player
            fry.setX(Player.getInstance().getX() + (Player.getInstance().getTexture().getWidth() / 2f) - (fry.getTexture().getWidth() / 2f));
            fry.setY(Player.getInstance().getY() + (Player.getInstance().getTexture().getHeight() / 2f) - (fry.getTexture().getHeight() / 2f) - 67);

            batch.setColor(getOverlayColor());
            fry.render(batch);
            batch.setColor(Color.WHITE);
        }
    }

    public Sprite getFry() {
        return fry;
    }

    public void setFry(Sprite fry) {
        this.fry = fry;
    }

    public int getCookedPercentage() {
        return cookedPercentage;
    }

    public void setCookedPercentage(int cookedPercentage) {
        if (cookedPercentage < 0) {
            cookedPercentage = 0;
        }
        if (cookedPercentage > 100) {
            cookedPercentage = 100;
        }
        this.cookedPercentage = cookedPercentage;
    }
}
