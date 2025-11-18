package it.thesquad.foodtruck.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import it.thesquad.foodtruck.logic.Sprite;

public class Player extends Sprite {
    private float speed = 200f;
    private float rotation;
    private boolean processMovement;

    public Player(Texture texture) {
        super(texture, 0, 0);
        this.processMovement = true;
    }

    @Override
    public void update(float delta) {
        // Movement
        if (processMovement) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                y += speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                y -= speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                x -= speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                x += speed * delta;
            }
        }

        // Rotation
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY() - (float) texture.getHeight() / 2;
        rotation = MathUtils.radiansToDegrees * MathUtils.atan2(mouseY - y, mouseX - x);
    }

    public boolean isProcessingMovement() {
        return processMovement;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setProcessingMovement(boolean processMovement) {
        this.processMovement = processMovement;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, texture.getWidth() / 2f, texture.getHeight() / 2f, texture.getWidth(), texture.getHeight(), 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
}
