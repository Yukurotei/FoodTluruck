package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.thesquad.foodtruck.ingredients.Patty;
import it.thesquad.foodtruck.logic.Button;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.player.Player;

public class Cutter extends Appliance {

    private Texture uiTexture;
    private Texture bunWholeTexture;
    private Texture bunTopTexture;
    private Texture bunBottomTexture;
    private Texture cutterTexture;

    private Sprite currentBun;
    private Sprite cutTop;
    private Sprite cutBottom;

    private Button bunPile;
    private Button cutterButton;

    private boolean isCutting = false;
    private float cutProgress = 0f;

    private boolean justPlaced = false;

    public Cutter(Texture texture, int x, int y, int w, int h) {
        super(texture, x, y, w, h);
    }

    @Override
    public void init() {
        uiTexture = new Texture("cuttingBoard.png");
        bunWholeTexture = new Texture("burgerTopBun.png");
        bunTopTexture = new Texture("burgerTopBun.png");
        bunBottomTexture = new Texture("burgerBottomBun.png");
        cutterTexture = new Texture("knife.png");

        // Bun pile (take whole bun)
        bunPile = new Button(bunWholeTexture, 10f, 10f, () -> {
            if (currentBun != null || isCutting) return;
            currentBun = new Sprite(bunWholeTexture,
                    Gdx.input.getX(),
                    Gdx.graphics.getHeight() - Gdx.input.getY(),
                    false);
        });

        // Cutting station (place + cut)
        cutterButton = new Button(cutterTexture,
                350, 250, () -> {

            if (isCutting && !justPlaced) {
                // Second press then pick up halves
                if (Player.getInstance().getCurrentIngredient() == null &&
                    cutTop != null && cutBottom != null) {

                    // Give player top bun
                    Player.getInstance().setCurrentIngredient(
                        new Patty(cutTop, 100)
                    );

                    // Reset state
                    cutTop = cutBottom = null;
                    isCutting = false;
                }
                return;
            }

            if (currentBun != null && !isCutting) {
                // Place bun on board to cut
                justPlaced = true;
                isCutting = true;
                cutProgress = 0f;

                currentBun.setX(330);
                currentBun.setY(260);
            } else {
                justPlaced = false;
            }
        });
    }

    @Override
    public void update(float dt) {
        if (bunPile != null) bunPile.update(dt);
        if (cutterButton != null) cutterButton.update(dt);

        if (currentBun != null && !isCutting) {
            // follow cursor
            currentBun.setX(Gdx.input.getX() - currentBun.getWidth() / 2f);
            currentBun.setY(Gdx.graphics.getHeight() - Gdx.input.getY() - currentBun.getHeight() / 2f);
        }

        if (isCutting && currentBun != null) {
            cutProgress += dt;
            if (cutProgress >= 1f) {
                performCut();
            }
        }
    }

    private void performCut() {
        // Delete whole bun, create 2 halves
        cutTop = new Sprite(bunTopTexture, 330, 280, false);
        cutBottom = new Sprite(bunBottomTexture, 330, 240, false);

        currentBun.dispose();
        currentBun = null;
        isCutting = true;
    }

    @Override
    public void display(SpriteBatch batch) {
        batch.begin();
        batch.draw(uiTexture, 0, 0);

        bunPile.renderButton(batch);
        cutterButton.renderButton(batch);

        if (currentBun != null) currentBun.render(batch);
        if (cutTop != null) cutTop.render(batch);
        if (cutBottom != null) cutBottom.render(batch);

        batch.end();
    }

    @Override
    public void end() {
        // bunPile.dispose();
        cutterButton.dispose();
        // if (currentBun != null) currentBun.dispose();
        // if (cutTop != null) cutTop.dispose();
        // if (cutBottom != null) cutBottom.dispose();

        uiTexture.dispose();
        // bunWholeTexture.dispose();
        bunTopTexture.dispose();
        bunBottomTexture.dispose();
        if (currentBun != null) currentBun.dispose();
        cutterTexture.dispose();
        bunBottomTexture = null;
        bunTopTexture = null;
        currentBun = null;
    }
}
