package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.ingredients.FryItem;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.player.Player;

public class DeepFryer extends Appliance {

    private Texture fryerUiTexture;
    private Texture FryItemTexture;
    private Texture fryererTexture;

    private final Color rawFryItemColor = new Color(219/255f, 108/255f, 141/255f, 1f); // Raw FryItem color (219, 108, 141)

    private Sprite currentFryItem;
    private Button FryItemPile;
    private Button fryerer;
    private boolean isFryItemCooking;
    private FryItem outputFryItem;
    private float cookTime = 0f;

    boolean justPutFryItem = false; //NOTE FOR SEBASTIAN: Reason why this exists is because buttons trigger 2 times when pressed for some reasona

    /**
     *
     * @param texture the texture of the fryer
     * @param x the x-coordinate of the fryer
     * @param y the y-coordinate of the fryer
     * @param w the width of the fryer
     * @param h the height of the fryer
     */
    public DeepFryer(Texture texture, int x, int y, int w, int h) {
        super(texture, x, y, w, h);
    }

    @Override
    public void init() {
        System.out.println("Inited button");
        //Load all texture on init to avoid disk consumption
        fryerUiTexture = new Texture("fryerUI.png");
        FryItemTexture = new Texture("raw_fries.png");
        fryererTexture = new Texture("fryerUI.png");

        FryItemPile = new Button(FryItemTexture, 10f, 10f, () -> {
            if (currentFryItem != null) return;
            currentFryItem = new Sprite(FryItemTexture, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), false);
        });

        fryerer = new Button(fryererTexture, 400 - ((float) fryererTexture.getWidth() / 2), 300 - ((float) fryererTexture.getHeight() / 2), () -> {
            if (isFryItemCooking && !justPutFryItem && currentFryItem == null) {
                if (Player.getInstance().getCurrentIngredient() == null) {
                    isFryItemCooking = false;
                    Texture resizedFryItem = Utils.resizeTo(FryItemTexture, 50);
                    Player.getInstance().setCurrentIngredient(new FryItem(new Sprite(resizedFryItem, Player.getInstance().getX()
                        + (Player.getInstance().getTexture().getWidth() / 2f) - (resizedFryItem.getWidth() / 2f)
                        ,Player.getInstance().getY() + (Player.getInstance().getTexture().getHeight() / 2f) - (resizedFryItem.getHeight() / 2f) - 67, false), outputFryItem.getCookedPercentage()));
                    outputFryItem = null;
                } else {
                    //TODO: Warn player they have something in their hands
                }
            }
            justPutFryItem = false;
            if (currentFryItem == null || isFryItemCooking) return;
            justPutFryItem = true;
            isFryItemCooking = true;
            currentFryItem = null;
            outputFryItem = new FryItem(null, 0);
        });
    }

    /**
     *
     * @param batch the SpriteBatch used for rendering
     */
    @Override
    public void display(SpriteBatch batch) {
        batch.begin();
        batch.draw(fryerUiTexture, 0, 0);

        fryerer.renderButton(batch);

        batch.setColor(rawFryItemColor);
        FryItemPile.renderButton(batch);
        if (currentFryItem != null) {
            currentFryItem.render(batch);
        }
        batch.setColor(Color.SALMON);

        if (isFryItemCooking) {
            if (outputFryItem != null) {
                Color tint;
                float cookedPercentage = outputFryItem.getCookedPercentage();

                if (cookedPercentage < 50f) {
                    float progress = cookedPercentage / 50f;
                    tint = new Color(rawFryItemColor).lerp(Color.SALMON, progress);
                } else {
                    float progress = Math.min((cookedPercentage - 50f) / 50f, 1.0f);
                    tint = new Color(Color.SALMON).lerp(Color.BLACK, progress);
                }
                batch.setColor(tint);
            }

            batch.draw(FryItemTexture, 341, 248);

            // Reset color to white to not affect other drawn objects
            batch.setColor(Color.WHITE);
        }
        batch.end();
    }

    @Override
    public void end() {
        if (FryItemPile != null) FryItemPile.dispose();
        if (fryerer != null) fryerer.dispose();
        if (currentFryItem != null) currentFryItem.dispose();

        if (fryerUiTexture != null) fryerUiTexture.dispose();
        if (FryItemTexture != null) FryItemTexture.dispose();
        if (fryererTexture != null) fryererTexture.dispose();

        FryItemPile = null;
        fryerer = null;
        currentFryItem = null;
        fryerUiTexture = null;
        FryItemTexture = null;
        fryererTexture = null;
    }

    @Override
    public void update(float dt) {
        if (FryItemPile != null) FryItemPile.update(dt);
        if (fryerer != null) fryerer.update(dt);
        if (currentFryItem != null) {
            currentFryItem.update(dt);
            currentFryItem.setX(Gdx.input.getX() - (float) currentFryItem.getSourceTexture().getWidth() / 2);
            currentFryItem.setY(Gdx.graphics.getHeight() - Gdx.input.getY() - (float) currentFryItem.getSourceTexture().getHeight() / 2);
        }

        if (isFryItemCooking && outputFryItem != null) {
            cookTime += dt;
            if (cookTime >= 1.0f) {
                outputFryItem.setCookedPercentage(outputFryItem.getCookedPercentage() + 2);
                System.out.println(outputFryItem.getCookedPercentage());
                cookTime -= 1.0f;
            }
        }
    }
}
