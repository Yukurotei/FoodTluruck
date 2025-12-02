package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.ingredients.Patty;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.player.Player;

public class Grill extends Appliance {

    private Texture grillUiTexture;
    private Texture pattyTexture;
    private Texture grillerTexture;

    private final Color rawPattyColor = new Color(219/255f, 108/255f, 141/255f, 1f); // Raw patty color (219, 108, 141)

    private Sprite currentPatty;
    private Button pattyPile;
    private Button griller;
    private boolean isPattyCooking;
    private Patty outputPatty;
    private float cookTime = 0f;

    boolean justPutPatty = false; //NOTE FOR person: Reason why this exists is because buttons trigger 2 times when pressed for some reasona

    /**
     *
     * @param texture the texture of the grill
     * @param x the x-coordinate of the grill
     * @param y the y-coordinate of the grill
     */
    public Grill(Texture texture, int x, int y) {
        super(texture, x, y);
    }

    @Override
    public void init() {
        //Load all texture on init to avoid disk consumption
        grillUiTexture = new Texture("grillUI.png");
        pattyTexture = new Texture("patty.png");
        grillerTexture = new Texture("grillGrill.png");

        pattyPile = new Button(pattyTexture, 10f, 10f, () -> {
            if (currentPatty != null) return;
            currentPatty = new Sprite(pattyTexture, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), false);
        });

        griller = new Button(grillerTexture, 400 - ((float) grillerTexture.getWidth() / 2), 300 - ((float) grillerTexture.getHeight() / 2), () -> {
            if (isPattyCooking && !justPutPatty && currentPatty == null) {
                if (Player.getInstance().getCurrentIngredient() == null) {
                    isPattyCooking = false;
                    Texture resizedPatty = Utils.resizeTo(pattyTexture, 50);
                    Player.getInstance().setCurrentIngredient(new Patty(new Sprite(resizedPatty, Player.getInstance().getX()
                        + (Player.getInstance().getTexture().getWidth() / 2f) - (resizedPatty.getWidth() / 2f)
                        ,Player.getInstance().getY() + (Player.getInstance().getTexture().getHeight() / 2f) - (resizedPatty.getHeight() / 2f) - 67, false), outputPatty.getCookedPercentage()));
                    outputPatty = null;
                } else {
                    //TODO: Warn player they have something in their hands
                }
            }
            justPutPatty = false;
            if (currentPatty == null || isPattyCooking) return;
            justPutPatty = true;
            isPattyCooking = true;
            currentPatty = null;
            outputPatty = new Patty(null, 0);
        });
    }

    /**
     *
     * @param batch the SpriteBatch used for rendering
     */
    @Override
    public void display(SpriteBatch batch) {
        batch.begin();
        batch.draw(grillUiTexture, 0, 0);

        griller.renderButton(batch);

        batch.setColor(rawPattyColor);
        pattyPile.renderButton(batch);
        if (currentPatty != null) {
            currentPatty.render(batch);
        }
        batch.setColor(Color.WHITE);

        if (isPattyCooking) {
            if (outputPatty != null) {
                Color tint;
                float cookedPercentage = outputPatty.getCookedPercentage();

                if (cookedPercentage < 50f) {
                    float progress = cookedPercentage / 50f;
                    tint = new Color(rawPattyColor).lerp(Color.WHITE, progress);
                } else {
                    float progress = Math.min((cookedPercentage - 50f) / 50f, 1.0f);
                    tint = new Color(Color.WHITE).lerp(Color.BLACK, progress);
                }
                batch.setColor(tint);
            }

            batch.draw(pattyTexture, 341, 248);

            // Reset color to white to not affect other drawn objects
            batch.setColor(Color.WHITE);
        }
        batch.end();
    }

    @Override
    public void end() {
        if (pattyPile != null) pattyPile.dispose();
        if (griller != null) griller.dispose();
        if (currentPatty != null) currentPatty.dispose();

        if (grillUiTexture != null) grillUiTexture.dispose();
        if (pattyTexture != null) pattyTexture.dispose();
        if (grillerTexture != null) grillerTexture.dispose();

        pattyPile = null;
        griller = null;
        currentPatty = null;
        grillUiTexture = null;
        pattyTexture = null;
        grillerTexture = null;
    }

    @Override
    public void update(float dt) {
        if (pattyPile != null) pattyPile.update(dt);
        if (griller != null) griller.update(dt);
        if (currentPatty != null) {
            currentPatty.update(dt);
            currentPatty.setX(Gdx.input.getX() - (float) currentPatty.getSourceTexture().getWidth() / 2);
            currentPatty.setY(Gdx.graphics.getHeight() - Gdx.input.getY() - (float) currentPatty.getSourceTexture().getHeight() / 2);
        }

        if (isPattyCooking && outputPatty != null) {
            if (outputPatty.getCookedPercentage() != 100) {
                cookTime += dt;
                if (cookTime >= 1.0f) {
                    outputPatty.setCookedPercentage(outputPatty.getCookedPercentage() + 2);
                    System.out.println(outputPatty.getCookedPercentage());
                    cookTime -= 1.0f;
                }
            }
        }
    }
}
