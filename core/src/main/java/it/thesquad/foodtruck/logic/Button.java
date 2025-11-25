package it.thesquad.foodtruck.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import it.thesquad.foodtruck.Main;

public class Button extends Sprite {
    private final Runnable onClick;

    public Button(Texture texture, float x, float y, Runnable onClick) {
        super(texture, x, y, false);
        this.onClick = onClick;
    }

    @Override
    @Deprecated
    public void render(SpriteBatch bt) {
        System.out.println("RENDER FUNCTION IS DEPRECATED FOR BUTTONS, USE renderButton INSTEAD");
    }

    public void renderButton(SpriteBatch bt) {
        bt.draw(texture, x, y);
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Main.camera.unproject(touchPos);
            if (getHitbox().contains(touchPos.x, touchPos.y)) {
                onClick.run();
            }
        }
    }
}
