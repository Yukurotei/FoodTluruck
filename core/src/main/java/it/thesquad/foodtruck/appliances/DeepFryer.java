package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.ingredients.Fry;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.player.Player;

public class DeepFryer extends Appliance {

    private Texture fryerUiTexture;
    private Texture fryTexture;
    private Texture fryerButtonTexture;

    private final Color friedColor = new Color(219/255f, 108/255f, 141/255f, 1f); // Target fried color

    private Sprite currentFry;
    private Button fryPile;
    private Button fryerButton;
    private boolean isFrying;
    private Fry outputFry;
    private float cookTime = 0f;

    boolean justPutFry = false;

    public DeepFryer(Texture texture, int x, int y) {
        super(texture, x, y);
    }

    @Override
    public void init() {
        fryerUiTexture = Utils.resizeTo(new Texture("fryerUI.png"), 800, 600);
        fryTexture = new Texture("raw_fries.png");
        fryerButtonTexture = new Texture("fryerBasket.png");

        fryPile = new Button(fryTexture, 10f, 10f, () -> {
            if (currentFry != null) return;
            currentFry = new Sprite(fryTexture, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), false);
        });

        fryerButton = new Button(fryerButtonTexture, 400 - ((float) fryerButtonTexture.getWidth() / 2), 300 - ((float) fryerButtonTexture.getHeight() / 2), () -> {
            if (isFrying && !justPutFry && currentFry == null) {
                if (Player.getInstance().getCurrentIngredient() == null) {
                    isFrying = false;
                    Texture resizedFry = Utils.resizeTo(fryTexture, 50);
                    Player.getInstance().setCurrentIngredient(new Fry(new Sprite(resizedFry, Player.getInstance().getX()
                        + (Player.getInstance().getTexture().getWidth() / 2f) - (resizedFry.getWidth() / 2f)
                        ,Player.getInstance().getY() + (Player.getInstance().getTexture().getHeight() / 2f) - (resizedFry.getHeight() / 2f) - 67, false), outputFry.getCookedPercentage()));
                    outputFry = null;
                }
            }
            justPutFry = false;
            if (currentFry == null || isFrying) return;
            justPutFry = true;
            isFrying = true;
            currentFry = null;
            outputFry = new Fry(null, 0);
        });
    }

    @Override
    public void display(SpriteBatch batch) {
        batch.draw(fryerUiTexture, 0, 0);

        fryerButton.renderButton(batch);

        fryPile.renderButton(batch);
        if (currentFry != null) {
            currentFry.render(batch);
        }

        if (isFrying) {
            if (outputFry != null) {
                Color tint;
                float cookedPercentage = outputFry.getCookedPercentage();

                if (cookedPercentage < 50f) {
                    float progress = cookedPercentage / 50f;
                    tint = new Color(Color.WHITE).lerp(friedColor, progress);
                } else {
                    float progress = Math.min((cookedPercentage - 50f) / 50f, 1.0f);
                    tint = new Color(friedColor).lerp(Color.BLACK, progress);
                }
                batch.setColor(tint);
            }

            batch.draw(fryTexture, 400 - 104, 300 - 104);

            batch.setColor(Color.WHITE);
        }
    }

    @Override
    public void end() {
        if (fryPile != null) fryPile.dispose();
        if (fryerButton != null) fryerButton.dispose();
        if (currentFry != null) currentFry.dispose();

        if (fryerUiTexture != null) fryerUiTexture.dispose();
        if (fryTexture != null) fryTexture.dispose();
        if (fryerButtonTexture != null) fryerButtonTexture.dispose();

        fryPile = null;
        fryerButton = null;
        currentFry = null;
        fryerUiTexture = null;
        fryTexture = null;
        fryerButtonTexture = null;
    }

    @Override
    public void update(float dt) {
        if (fryPile != null) fryPile.update(dt);
        if (fryerButton != null) fryerButton.update(dt);
        if (currentFry != null) {
            currentFry.update(dt);
            currentFry.setX(Gdx.input.getX() - (float) currentFry.getSourceTexture().getWidth() / 2);
            currentFry.setY(Gdx.graphics.getHeight() - Gdx.input.getY() - (float) currentFry.getSourceTexture().getHeight() / 2);
        }

        if (isFrying && outputFry != null) {
            if (outputFry.getCookedPercentage() != 100) {
                cookTime += dt;
                if (cookTime >= 1.0f) {
                    outputFry.setCookedPercentage(outputFry.getCookedPercentage() + 2);
                    System.out.println(outputFry.getCookedPercentage());
                    cookTime -= 1.0f;
                }
            }
        }
    }
}
