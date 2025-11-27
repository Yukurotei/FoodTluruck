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

public class Table extends Appliance {

    private Texture tableUiTexture;
    private Texture itemTexture;
    private Texture tableTexture;


    private Sprite currentPatty;
    private Button pattyPile;
    private Button griller;
    private boolean isPattyCooking;
    private Patty outputPatty;

    boolean justPutPatty = false; //NOTE FOR SEBASTIAN: Reason why this exists is because buttons trigger 2 times when pressed for some reasona

    /**
     *
     * @param texture the texture of the table
     * @param x the x-coordinate of the table
     * @param y the y-coordinate of the table
     * @param w the width of the table
     * @param h the height of the table
     */
    public Table(Texture texture, int x, int y, int w, int h) {
        super(texture, x, y, w, h);
    }

    @Override
    public void init() {
        //Load all texture on init to avoid disk consumption
        tableUiTexture = new Texture("table.png");
        itemTexture = new Texture("patty.png");
        tableTexture = new Texture("table.png");

        pattyPile = new Button(itemTexture, 10f, 10f, () -> {
            if (currentPatty != null) return;
            currentPatty = new Sprite(itemTexture, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), false);
        });

        griller = new Button(tableTexture, 400 - ((float) tableTexture.getWidth() / 2), 300 - ((float) tableTexture.getHeight() / 2), () -> {
            if (isPattyCooking && !justPutPatty && currentPatty == null) {
                if (Player.getInstance().getCurrentIngredient() == null) {
                    isPattyCooking = false;
                    Texture resizedPatty = Utils.resizeTo(itemTexture, 50);
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
        batch.draw(tableUiTexture, 0, 0);

        griller.renderButton(batch);

        pattyPile.renderButton(batch);
        if (currentPatty != null) {
            currentPatty.render(batch);
        }

        batch.end();
    }

    @Override
    public void end() {
        if (pattyPile != null) pattyPile.dispose();
        if (griller != null) griller.dispose();
        if (currentPatty != null) currentPatty.dispose();

        if (tableUiTexture != null) tableUiTexture.dispose();
        if (itemTexture != null) itemTexture.dispose();
        if (tableTexture != null) tableTexture.dispose();

        pattyPile = null;
        griller = null;
        currentPatty = null;
        tableUiTexture = null;
        itemTexture = null;
        tableTexture = null;
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
    }
}
