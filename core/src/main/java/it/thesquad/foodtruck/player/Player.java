package it.thesquad.foodtruck.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import it.thesquad.foodtruck.Main;
import it.thesquad.foodtruck.appliances.Appliance;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;

public class Player extends Sprite {
    private float speed = 200f;
    private float rotation;
    private boolean processMovement;
    private Sprite interactionSprite;
    private Appliance appliance;

    /**
     *
     * @param texture the texture of the player
     * @param interactionSprite the sprite used to indicate interaction
     */
    public Player(Texture texture, Sprite interactionSprite) {
        super(texture, 0, 0);
        this.processMovement = true;
        this.interactionSprite = interactionSprite;
        this.appliance = null;
    }

    /**
     *
     * @param delta the time elapsed since the last update
     */
    @Override
    public void update(float delta) {
        boolean justSwitchedState = false;
        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && Main.gameState == Main.GameState.MINIGAME) {
            Main.gameState = Main.GameState.WORLD;
            appliance.end();
            justSwitchedState = true;
        }

        if (Main.gameState == Main.GameState.WORLD) {
            setProcessingMovement(true);
        } else {
            setProcessingMovement(false);
            interactionSprite.setVisible(false);
        }

        if (processMovement) {
            float oldX = getX();
            float dx = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                dx -= 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                dx += 1;
            }

            setX(getX() + dx * speed * delta);

            boolean inRangeOfAppliance = false;
            for (Sprite sprite : Main.spriteObjects) {
                if (sprite instanceof Appliance) {
                    if (this.collidesWith(sprite)) {
                        setX(oldX);
                        break;
                    }
                    if(Utils.distanceOfSprites(this, sprite) <= 120) {
                        inRangeOfAppliance = true;
                    }
                }
            }
            interactionSprite.setVisible(inRangeOfAppliance);
            if(inRangeOfAppliance && Gdx.input.isKeyJustPressed(Input.Keys.E) && !justSwitchedState) {
                if(Main.gameState == Main.GameState.WORLD) {
                    for (Sprite sprite : Utils.getSpritesSortedByDistance(Main.spriteObjects, this)) {
                        if (sprite instanceof Appliance) {
                            this.appliance = (Appliance) sprite;
                            appliance.init();
                            break;
                        }
                    }
                    Main.gameState = Main.GameState.MINIGAME;
                }
            }


            float oldY = getY();
            float dy = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                dy -= 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                dy += 1;
            }

            setY(getY() + dy * speed * delta);

            for (Sprite sprite : Main.spriteObjects) {
                if (sprite instanceof Appliance) {
                    if (this.collidesWith(sprite)) {
                        setY(oldY);
                        break;
                    }
                }
            }
        }


        // Rotation
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY() - (float) texture.getHeight() / 2;
        rotation = MathUtils.radiansToDegrees * MathUtils.atan2(mouseY - y, mouseX - x);
    }

    /**
     *
     * @return whether the player is processing movement
     */
    public boolean isProcessingMovement() {
        return processMovement;
    }

    /**
     *
     * @return the speed of the player
     */
    public float getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed the new speed of the player
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     *
     * @param processMovement whether the player should process movement
     */
    public void setProcessingMovement(boolean processMovement) {
        this.processMovement = processMovement;
    }

    /**
     *
     * @return the rotation of the player
     */
    public float getRotation() {
        return rotation;
    }

    /**
     *
     * @param rotation the new rotation of the player
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    /**
     *
     * @return the interaction sprite of the player
     */
    public Sprite getInteractionSprite() {
        return interactionSprite;
    }

    /**
     *
     * @param interactionSprite the new interaction sprite of the player
     */
    public void setInteractionSprite(Sprite interactionSprite) {
        this.interactionSprite = interactionSprite;
    }

    /**
     *
     * @return the appliance the player is interacting with
     */
    public Appliance getAppliance() {
        return appliance;
    }

    /**
     *
     * @param appliance the new appliance the player is interacting with
     */
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, texture.getWidth() / 2f, texture.getHeight() / 2f, texture.getWidth(), texture.getHeight(), 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
}
