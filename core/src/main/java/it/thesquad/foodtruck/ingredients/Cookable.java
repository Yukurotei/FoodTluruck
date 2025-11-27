package it.thesquad.foodtruck.ingredients;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.player.Player;

public class Cookable implements Ingredient {

    private Sprite patty;
    private int cookedPercentage;

    private final Color rawPattyColor = new Color(219/255f, 108/255f, 141/255f, 1f); // Raw patty color (219, 108, 141)

    public Cookable(Sprite patty) {
        this.patty = patty;
        this.cookedPercentage = 0;
    }

    public Cookable(Sprite patty, int cookedPercentage) {
        this.patty = patty;
        this.cookedPercentage = cookedPercentage;
    }

    /**
     * Calculates the overlay color for the patty based on its cooked percentage.
     * Transitions from rawPattyColor to white (cooked), then to black (burnt).
     *
     * @return The calculated tint color.
     */
    private Color getOverlayColor() {
        Color tint;
        float currentCookedPercentage = this.cookedPercentage; // Use the field

        if (currentCookedPercentage < 50f) {
            float progress = currentCookedPercentage / 50f;
            tint = new Color(rawPattyColor).lerp(Color.WHITE, progress);
        } else {
            float progress = Math.min((currentCookedPercentage - 50f) / 50f, 1.0f);
            tint = new Color(Color.WHITE).lerp(Color.BLACK, progress);
        }
        return tint;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (patty != null) {
            patty.setX(Player.getInstance().getX() + (Player.getInstance().getTexture().getWidth() / 2f) - (patty.getTexture().getWidth() / 2f));
            patty.setY(Player.getInstance().getY() + (Player.getInstance().getTexture().getHeight() / 2f) - (patty.getTexture().getHeight() / 2f) - 67);

            batch.setColor(getOverlayColor());
            patty.render(batch);
            batch.setColor(Color.WHITE);
        }
    }

    public Sprite getPatty() {
        return patty;
    }

    public void setPatty(Sprite patty) {
        this.patty = patty;
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
