package it.thesquad.foodtruck;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import it.thesquad.foodtruck.appliances.*;
import it.thesquad.foodtruck.customers.Customer;
import it.thesquad.foodtruck.customers.CustomerQueue;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.player.Player;
import it.thesquad.foodtruck.reputation.Review;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public enum GameState {
        WORLD,
        MINIGAME
    }
    private SpriteBatch batch;
    public static OrthographicCamera camera;
    //private Order testorder;
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
        Grill grill = new Grill(Utils.resizeTo(new Texture("grill.png"), 50), 100, 200);
        Cutter cuttingBoard = new Cutter(Utils.resizeTo(new Texture("cuttingBoardSimple.png"), 50), 100, 100);
        Fridge fridge = new Fridge(Utils.resizeTo(new Texture("fridgeTopDown.png"), 50), 360, 360);
        DeepFryer fryer = new DeepFryer(Utils.resizeTo(new Texture("fryerUI.png"), 20), 200, 200);
        Table table = new Table(Utils.resizeTo(new Texture("table.png"), 20), 300, 500);
        Sprite interactionSprite = new Sprite(Utils.resizeTo(new Texture("interact.png"), 50), 0, 0, true);
        interactionSprite.setVisible(false);
        new Player(new Texture("player.png"), interactionSprite);
        customerQueue.add(new Customer());
        font = new BitmapFont();
        try {

            int rating = 0;

            String orderMsg = customerQueue.getElm(0).getOrderMsg();

            customerQueue.getElm(0).setReview(new Review(rating, Utils.getReview(orderMsg,rating)));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            Player.getInstance().getCurrentAppliance().display(batch);
            return;
        }
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();

        for (Sprite sprite : spriteObjects) {
            sprite.render(batch);
        }

        if(gameState == GameState.WORLD) {
            font.draw(batch, customerQueue.getElm(0).getOrderMsg(), 300, 300);
            font.draw(batch, customerQueue.getElm(0).getReview().getReviewText(), 200, 200);
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
