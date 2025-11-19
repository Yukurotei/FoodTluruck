package it.thesquad.foodtruck;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import it.thesquad.foodtruck.appliances.Grill;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.player.Player;

import java.util.ArrayList;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    //private Order testorder;
    public static Player player;
    public static List<Sprite> spriteObjects = new ArrayList<>();

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player(new Texture("player.png"));
        Grill grill = new Grill(Utils.resizeTo(new Texture("grill.png"), 50), 40, 40, 20, 20);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        for (Sprite sprite : spriteObjects) {
            sprite.update(Gdx.graphics.getDeltaTime());
        }

        batch.begin();

        for (Sprite sprite : spriteObjects) {
            sprite.render(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        //player.dispose();
        for (Sprite sprite : spriteObjects) {
            sprite.dispose();
        }
    }
}
