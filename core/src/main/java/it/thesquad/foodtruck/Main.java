package it.thesquad.foodtruck;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    private CutsceneManager cutsceneManager;
    private BitmapFont font;
    public static float timePassed; //in seconds
    Music introSong;

    @Override
    public void create() {
        timePassed = 0f;
        gameState = GameState.INTRO;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        animationManager = new AnimationManager();
        cutsceneManager = new CutsceneManager();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Grill grill = new Grill(Utils.resizeTo(new Texture("grill.png"), 50), 100, 200);
        Cutter cuttingBoard = new Cutter(Utils.resizeTo(new Texture("cuttingBoardSimple.png"), 50), 100, 100);
        Fridge fridge = new Fridge(Utils.resizeTo(new Texture("fridgeTopDown.png"), 50), 360, 360);
        DeepFryer fryer = new DeepFryer(Utils.resizeTo(new Texture("fryerUI.png"), 20), 200, 200);
        Table table = new Table(Utils.resizeTo(new Texture("table.png"), 20), 500, 300);
        AssemblyTable assemblyTable = new AssemblyTable(Utils.resizeTo(new Texture("table.png"), 20), 300, 500);
        FoodCupboard cupboard = new FoodCupboard(Utils.resizeTo(new Texture("foodCupboard.png"), 20), 500, 500);

        Sprite interactionSprite = new Sprite(Utils.resizeTo(new Texture("interact.png"), 50), 0, 0, true);
        interactionSprite.setVisible(false);
        new Player(Utils.resizeTo(new Texture("john_hands.png"),50), interactionSprite);
        customerQueue.add(new Customer());
        font = new BitmapFont();

        AnimatedSprite focus = new AnimatedSprite("focus", Utils.resizeTo(new Texture("focus.png"), 200), 0, 0, true);
        focus.setX(400 - focus.getWidth() / 2f);
        focus.setY(300 - focus.getHeight() / 2f);
        focus.setAlpha(0f);

        AnimatedSprite gdxLogo = new AnimatedSprite("gdxLogo", Utils.resizeTo(new Texture("madeWLibGDX.png"), 100), 0, 0, true);
        gdxLogo.setX(400 - gdxLogo.getWidth() / 2f);
        gdxLogo.setY(300 - gdxLogo.getHeight() / 2f);
        gdxLogo.setAlpha(0f);
        animationManager.animateScale(gdxLogo, gdxLogo.getScaleX() / 10, gdxLogo.getScaleY() / 10, 0.1f, AnimationManager.Easing.LINEAR);

        AnimatedSprite allRoadsLeadToRome = new AnimatedSprite("romeChef", new Texture("time_ticking.png"), 0, 0, true);
        allRoadsLeadToRome.setX(400 - allRoadsLeadToRome.getWidth() / 2f + 700); //target is +400
        allRoadsLeadToRome.setY(300 - allRoadsLeadToRome.getHeight() / 2f);
        allRoadsLeadToRome.setAlpha(0f);

        AnimatedSprite logo = new AnimatedSprite("logo", Utils.resizeTo(new Texture("grease_up.png"), 100), 0, 0, true);
        logo.setX(400 - logo.getWidth() / 2f);
        logo.setY(300 - logo.getHeight() / 2f - 600);
        ////////////
        //CUTSCENE//
        ////////////
        introSong = Gdx.audio.newMusic(Gdx.files.internal("audio/give-it-one.mp3"));
        introSong.setLooping(false);
        introSong.setVolume(1f);
        introSong.setPosition(2);

        cutsceneManager.addEvent(new CutsceneEvent(1f, () -> {
            animationManager.animateScale(gdxLogo, gdxLogo.getScaleX() * 10, gdxLogo.getScaleY() * 10, 8f, AnimationManager.Easing.EASE_IN_OUT_EXPO);
            animationManager.animateFade(gdxLogo, 1f, 5f, AnimationManager.Easing.EASE_IN_OUT_QUAD);
            introSong.play();
        }));
        cutsceneManager.addEvent(new CutsceneEvent(5f, () -> {
            animationManager.animateRotation(gdxLogo, gdxLogo.getRotation() + 15, 5f, AnimationManager.Easing.EASE_OUT_QUAD);
            animationManager.animateMove(gdxLogo, gdxLogo.getX() - 200, gdxLogo.getY() + 150, 3f, AnimationManager.Easing.EASE_IN_OUT_CUBIC);
            animationManager.animateFade(focus, 0.67f, 3f, AnimationManager.Easing.EASE_IN_OUT_EXPO);
            animationManager.animateFade(allRoadsLeadToRome, 1f, 3f, AnimationManager.Easing.EASE_IN_OUT_EXPO);
            animationManager.animateMove(allRoadsLeadToRome, allRoadsLeadToRome.getX() - 300, allRoadsLeadToRome.getY(), 3f, AnimationManager.Easing.EASE_IN_OUT_SINE);
            animationManager.animateMove(logo, logo.getX(), logo.getY() + 600, 3f, AnimationManager.Easing.EASE_IN_OUT_EXPO);
        }));
        cutsceneManager.addEvent(new CutsceneEvent(9f, () -> {
            animationManager.animateScale(gdxLogo, gdxLogo.getScaleX() - gdxLogo.getScaleX() / 5, gdxLogo.getScaleY() - gdxLogo.getScaleY() / 5, 3f, AnimationManager.Easing.EASE_IN_OUT_EXPO);
            animationManager.animateMove(logo, logo.getX() - 200, logo.getY() - 100, 3f, AnimationManager.Easing.EASE_IN_OUT_SINE);
        }));
        cutsceneManager.addEvent(new CutsceneEvent(10f, () -> {
            introSong.setVolume(0.5f);
        }));
        /*
        cutsceneManager.addEvent(new CutsceneEvent(10f, () -> {
            gameState = GameState.WORLD;
        }));
         */

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

            ScreenUtils.clear(237/255f, 185/255f, 43/255f, 1f);

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
