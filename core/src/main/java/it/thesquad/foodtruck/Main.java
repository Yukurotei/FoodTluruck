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
import it.thesquad.foodtruck.logic.AnimatedSprite;
import it.thesquad.foodtruck.logic.AnimationManager;
import it.thesquad.foodtruck.logic.CutsceneEvent;
import it.thesquad.foodtruck.logic.CutsceneManager;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.player.Player;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public enum GameState {
        WORLD, MINIGAME, INTRO
    }
    private SpriteBatch batch;
    public static OrthographicCamera camera;
    public static Queue<Sprite> spriteObjects = new ConcurrentLinkedQueue<>();
    public static Queue<AnimatedSprite> animatedSprites = new ConcurrentLinkedQueue<>();
    public static GameState gameState;
    public static CustomerQueue customerQueue = new CustomerQueue();
    public static AnimationManager animationManager;
    private BitmapFont font;
    public static float timePassed; //in seconds
    private CutsceneManager cutsceneManager;

    @Override
    public void create() {
        timePassed = 0f;
        gameState = GameState.INTRO;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        animationManager = new AnimationManager();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Grill grill = new Grill(Utils.resizeTo(new Texture("grill.png"), 50), 100, 200);
        Cutter cuttingBoard = new Cutter(Utils.resizeTo(new Texture("cuttingBoardSimple.png"), 50), 100, 100);
        Fridge fridge = new Fridge(Utils.resizeTo(new Texture("fridgeTopDown.png"), 50), 360, 360);
        DeepFryer fryer = new DeepFryer(Utils.resizeTo(new Texture("fryerUI.png"), 20), 200, 200);
        Table table = new Table(Utils.resizeTo(new Texture("table.png"), 20), 500, 300);
        AssemblyTable assemblyTable = new AssemblyTable(Utils.resizeTo(new Texture("table.png"), 20), 300, 500);
        FoodCupboard cupboard = new FoodCupboard(Utils.resizeTo(new Texture("table.png"), 20), 500, 500);

        Sprite interactionSprite = new Sprite(Utils.resizeTo(new Texture("interact.png"), 50), 0, 0, true);
        interactionSprite.setVisible(false);
        new Player(Utils.resizeTo(new Texture("john_hands.png"),50), interactionSprite);
        AnimatedSprite demoSprite = new AnimatedSprite("demo", new Texture("libgdx.png"), 0, 0, true);
        customerQueue.add(new Customer());
        font = new BitmapFont();

        //CUTSCENE
        cutsceneManager = new CutsceneManager();
        cutsceneManager.addEvent(new CutsceneEvent(1f, () -> {
            animationManager.animateMove(Utils.getSprite("demo"), 300, 300, 2f, AnimationManager.Easing.EASE_IN_OUT_QUAD);
        }));
        cutsceneManager.addEvent(new CutsceneEvent(4f, () -> {
            animationManager.animateMove(Utils.getSprite("demo"), 100, 100, 5f, AnimationManager.Easing.EASE_IN_OUT_QUAD);
        }));

    //     try {


    //         customerQueue.getElm(0).getClankerReview();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    }
    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        timePassed += dt;
        animationManager.update(dt);
        for (Sprite sprite : spriteObjects) sprite.update(dt);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        if (gameState == GameState.MINIGAME) {
            ScreenUtils.clear(0f, 0f, 0f, 1f);
            batch.begin();
            Player.getInstance().getCurrentAppliance().display(batch);
            batch.end();
        } else if (gameState == GameState.WORLD) {
            batch.begin();
            for (Sprite sprite : spriteObjects) sprite.render(batch);
            font.draw(batch, customerQueue.getElm(0).getOrderMsg(), 30, 30);
            batch.end();
        } else if (gameState == GameState.INTRO) {
            batch.begin();

            cutsceneManager.update(timePassed);

            for (AnimatedSprite aSprite : animatedSprites) {
                aSprite.render(batch);
            }
            batch.end();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        //player.dispose();
        for (Sprite sprite : spriteObjects) sprite.dispose();
    }
}
