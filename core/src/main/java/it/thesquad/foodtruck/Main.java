package it.thesquad.foodtruck;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import it.thesquad.foodtruck.appliances.Fridge;
import it.thesquad.foodtruck.appliances.Grill;
import it.thesquad.foodtruck.customers.Customer;
import it.thesquad.foodtruck.customers.CustomerQueue;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public enum GameState {
        WORLD,
        MINIGAME
    }
    private SpriteBatch batch;
    private Texture image;
    public static OrthographicCamera camera;
    //private Order testorder;
    public static Player player;
    public static Queue<Sprite> spriteObjects = new ConcurrentLinkedQueue<>();
    public static GameState gameState;
    public static CustomerQueue customerQueue = new CustomerQueue();
    private BitmapFont font;

    @Override
    public void create() {
        gameState = GameState.WORLD;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Sprite interactionSprite = new Sprite(new Texture("interact.png"), 0, 0, true);
        interactionSprite.setVisible(false);
        player = new Player(new Texture("player.png"), interactionSprite);
        Grill grill = new Grill(Utils.resizeTo(new Texture("grill.png"), 50), 40, 40, 20, 20);
        Fridge second = new Fridge(Utils.resizeTo(new Texture("grill.png"), 50), 360, 360, 20, 20);
        customerQueue.add(new Customer());
        font = new BitmapFont();
    }

    @Override
    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        for (Sprite sprite : spriteObjects) {
            sprite.update(Gdx.graphics.getDeltaTime());
        }

        if (gameState == GameState.MINIGAME) {
            ScreenUtils.clear(0f, 0f, 0f, 1f);
            player.getAppliance().display(batch);
            return;
        }
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();

        for (Sprite sprite : spriteObjects) {
            sprite.render(batch);
        }
        
        if(gameState == GameState.WORLD)
            font.draw(batch, customerQueue.getElm(0).getOrderMsg(), 300, 300); 


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
