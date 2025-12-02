package it.thesquad.foodtruck.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import it.thesquad.foodtruck.Main;


public class Button extends Sprite {
    private final Runnable onClick;

    /**
     * @param texture The image that will be used as a button
     * @param x The x-coordinate where the top-left corner of the button will be
     * @param y The y-coordinate where the top-left corner of the button will be
     * @param onClick If the user can click the button or not
     */
    public Button(Texture texture, float x, float y, Runnable onClick) {
        super(texture, x, y, false);

        this.onClick = onClick;
    }

    /**
     * @param button The button that will be rendered to the screen
     */
    public void renderButton(SpriteBatch button) {
        button.draw(texture, x, y);
    }

    /**
     * @param delta The value that keeps the FPS locked
     */
    @Override
    public void update(float delta) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            
            Main.camera.unproject(touchPos);

            if (getHitbox().contains(touchPos.x, touchPos.y)) onClick.run();
        }
    }
}