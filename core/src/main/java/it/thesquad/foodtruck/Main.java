package it.thesquad.foodtruck;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import it.thesquad.foodtruck.appliances.*;
import it.thesquad.foodtruck.customers.Customer;
import it.thesquad.foodtruck.customers.CustomerQueue;
import it.thesquad.foodtruck.logic.*;
import it.thesquad.foodtruck.player.Player;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

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
    public static CutsceneManager cutsceneManager;
    private BitmapFont font;
    public static float timePassed; //in seconds

    private Music introSong;
    private Music bgm;
    private ParallaxBackground parallaxBackground;
    private boolean renderParallax = false;
    private Button playButton;
    private AnimatedSprite playButtonAni;
    private AnimatedSprite spotLight;

    private Texture backgroundTexture;

    @Override
    public void create() {
        timePassed = 0f;
        gameState = GameState.INTRO;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        animationManager = new AnimationManager();
        cutsceneManager = new CutsceneManager();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Grill grill = new Grill(Utils.resizeTo(new Texture("grill.png"), 50), 400, 500);
        Cutter cuttingBoard = new Cutter(Utils.resizeTo(new Texture("cuttingBoardSimple.png"), 50), 550, 500);
        Fridge fridge = new Fridge(Utils.resizeTo(new Texture("fridgeTopDown.png"), 50), 670, 500);
        DeepFryer fryer = new DeepFryer(Utils.resizeTo(new Texture("fryerUI.png"), 20), 700, 300);
        Table table = new Table(Utils.resizeTo(new Texture("table.png"), 20), 500, 300);
        AssemblyTable assemblyTable = new AssemblyTable(Utils.resizeTo(new Texture("assemblyTable.png"), 20), 300, 500);
        FoodCupboard cupboard = new FoodCupboard(Utils.resizeTo(new Texture("foodCupboard.png"), 20), 500, 500);

        Sprite interactionSprite = new Sprite(Utils.resizeTo(new Texture("interact.png"), 50), 0, 0, true);
        interactionSprite.setVisible(false);
        new Player(Utils.resizeTo(new Texture("john_hands.png"),50), interactionSprite);
        customerQueue.add(new Customer());
        
        font = new BitmapFont();

        backgroundTexture = Utils.resizeTo(new Texture("floorPattern.png"), 800, 600);

        AnimatedSprite focus = new AnimatedSprite("focus", Utils.resizeTo(new Texture("focus.png"), 250), 0, 0, true);
        focus.setX(400 - focus.getWidth() / 2f);
        focus.setY(300 - focus.getHeight() / 2f);
        focus.setAlpha(0f);

        AnimatedSprite gdxLogo = new AnimatedSprite("gdxLogo", Utils.resizeTo(new Texture("madeWLibGDX.png"), 100), 0, 0, true);
        gdxLogo.setX(400 - gdxLogo.getWidth() / 2f);
        gdxLogo.setY(300 - gdxLogo.getHeight() / 2f);
        gdxLogo.setAlpha(0f);
        animationManager.animateScale(gdxLogo, gdxLogo.getScaleX() / 10, gdxLogo.getScaleY() / 10, 0.1f, AnimationManager.Easing.LINEAR);

        AnimatedSprite allRoadsLeadToRome = new AnimatedSprite("romeChef", new Texture("time_ticking.png"), 0, 0, true);
        allRoadsLeadToRome.setX(400 - allRoadsLeadToRome.getWidth() / 2f + 700);
        allRoadsLeadToRome.setY(300 - allRoadsLeadToRome.getHeight() / 2f);
        allRoadsLeadToRome.setAlpha(0f);

        AnimatedSprite logo = new AnimatedSprite("logo", Utils.resizeTo(new Texture("grease_up.png"), 120), 0, 0, true);
        logo.setX(400 - logo.getWidth() / 2f);
        logo.setY(300 - logo.getHeight() / 2f - 600);

        AnimatedSprite flash = new AnimatedSprite("whiteFlash", Utils.resizeTo(new Texture("whiteCirc.png"), 200), 0, 0, true);
        flash.setX(400 - flash.getWidth() / 2f);
        flash.setY(300 - flash.getHeight() / 2f);
        flash.setAlpha(0f);

        spotLight = new AnimatedSprite("spotLight", Utils.resizeTo(new Texture("spotLight.png"), 500), 0, 0, true);
        spotLight.setX(400 - spotLight.getWidth() / 2f);
        spotLight.setY(300 - spotLight.getHeight() / 2f);
        spotLight.setAlpha(0f);

        Texture parallaxTexture = Utils.rotateTextureRightAngles(new Texture("hat.png"), 180);
        parallaxBackground = new ParallaxBackground(parallaxTexture, new Color(1f, 1f, 1f, 0.5f));
        parallaxBackground.setSpeed(50, 50);

        Texture playButtonTexture = Utils.resizeTo(new Texture("play.png"), 50);
        playButtonAni = new AnimatedSprite("playButton", playButtonTexture, 0, 0, true);
        playButtonAni.setX(400 - playButtonAni.getWidth() / 2f + 100);
        playButtonAni.setY(300 - playButtonAni.getHeight() / 2f - 400);
        playButton = new Button(playButtonTexture, 0, 0, () -> {
            animationManager.animateScale(spotLight, spotLight.getScaleX() * 20, spotLight.getScaleY() * 20, 0.1f, AnimationManager.Easing.LINEAR);
            introSong.stop();
            Sound effect = Gdx.audio.newSound(Gdx.files.internal("audio/playEffect.mp3"));
            effect.play();
            playButton.setVisible(false);
            playButtonAni.setRotation(0f);
            animationManager.animateScale(playButtonAni, playButtonAni.getScaleX() * 100, playButtonAni.getScaleY() * 100, 2, AnimationManager.Easing.EASE_IN_OUT_EXPO);
            cutsceneManager.addEvent(new CutsceneEvent(timePassed + 0.1f, () -> {
                spotLight.setAlpha(1f);
            }));
            cutsceneManager.addEvent(new CutsceneEvent(timePassed + 0.2f, () -> {
                animationManager.animateScale(spotLight, spotLight.getScaleX() / 40, spotLight.getScaleY() / 40, 1f, AnimationManager.Easing.EASE_OUT_BOUNCE);
            }));
            cutsceneManager.addEvent(new CutsceneEvent(timePassed + 2, () -> {
                gameState = GameState.WORLD;
                disposeIntro();
            }));
        });
        playButton.setX(400 - playButton.getWidth() / 2f);
        playButton.setY(300 - playButton.getHeight() / 2f - 400);
        playButton.addOnHoverListener(() -> {
            float randomResult = new Random().nextFloat(0, 1);
            if (randomResult > 0.5f) {
                animationManager.animateRotation(playButtonAni, 20, 0.5f, AnimationManager.Easing.EASE_IN_OUT_BACK);
            } else if (randomResult <= 0.5f) {
                animationManager.animateRotation(playButtonAni, -20, 0.5f, AnimationManager.Easing.EASE_IN_OUT_BACK);
            }
        });
        playButton.addOnUnHoverListener(() -> {
            animationManager.animateRotation(playButtonAni, 0, 0.5f, AnimationManager.Easing.EASE_IN_OUT_BACK);
        });
        ////////////
        //CUTSCENE//
        ////////////
        introSong = Gdx.audio.newMusic(Gdx.files.internal("audio/give-it-one.mp3"));
        introSong.setLooping(false);
        introSong.setVolume(1f);
        introSong.setPosition(2);

        bgm = Gdx.audio.newMusic(Gdx.files.internal("audio/elevator-music.mp3"));
        bgm.setLooping(true);
        bgm.setVolume(0f);

        cutsceneManager.addEvent(new CutsceneEvent(1f, () -> {
            animationManager.animateScale(gdxLogo, gdxLogo.getScaleX() * 10, gdxLogo.getScaleY() * 10, 8f, AnimationManager.Easing.EASE_IN_OUT_EXPO);
            animationManager.animateFade(gdxLogo, 1f, 5f, AnimationManager.Easing.EASE_IN_OUT_QUAD);
            introSong.play();
        }));
        cutsceneManager.addEvent(new CutsceneEvent(3f, () -> {
            animationManager.animateMove(logo, logo.getX(), logo.getY() + 600, 6f, AnimationManager.Easing.EASE_IN_OUT_ELASTIC);
        }));
        cutsceneManager.addEvent(new CutsceneEvent(4.67f, () -> {
            animationManager.animateFade(flash, 1f, 0.5f, AnimationManager.Easing.LINEAR);
        }));
        cutsceneManager.addEvent(new CutsceneEvent(5f, () -> {
            animationManager.animateRotation(gdxLogo, gdxLogo.getRotation() + 15, 5f, AnimationManager.Easing.EASE_OUT_QUAD);
            animationManager.animateMove(gdxLogo, gdxLogo.getX() - 200, gdxLogo.getY() + 150, 3f, AnimationManager.Easing.EASE_IN_OUT_CUBIC);
            animationManager.animateFade(focus, 0.67f, 3f, AnimationManager.Easing.EASE_IN_OUT_EXPO);
            animationManager.animateFade(allRoadsLeadToRome, 1f, 3f, AnimationManager.Easing.EASE_IN_OUT_EXPO);
            animationManager.animateMove(allRoadsLeadToRome, allRoadsLeadToRome.getX() - 300, allRoadsLeadToRome.getY(), 3f, AnimationManager.Easing.EASE_IN_OUT_SINE);
        }));
        cutsceneManager.addEvent(new CutsceneEvent(5.5f, () -> {
            renderParallax = true;
            animationManager.animateFade(flash, 0f, 3f, AnimationManager.Easing.LINEAR);
        }));
        cutsceneManager.addEvent(new CutsceneEvent(9f, () -> {
            animationManager.animateScale(gdxLogo, gdxLogo.getScaleX() - gdxLogo.getScaleX() / 5, gdxLogo.getScaleY() - gdxLogo.getScaleY() / 5, 3f, AnimationManager.Easing.EASE_IN_OUT_EXPO);
            animationManager.animateMove(logo, logo.getX() - 200, logo.getY() - 100, 3f, AnimationManager.Easing.EASE_IN_OUT_SINE);
            animationManager.animateScale(logo, logo.getScaleX() - logo.getScaleX() / 5, logo.getScaleY() - logo.getScaleY() / 5, 3.5f, AnimationManager.Easing.EASE_IN_OUT_BACK);
            animationManager.animateMove(playButtonAni, playButtonAni.getX(), playButtonAni.getY() + 400, 3f, AnimationManager.Easing.EASE_IN_OUT_BACK);
        }));
        cutsceneManager.addEvent(new CutsceneEvent(10f, () -> {
            introSong.setVolume(0.90f);
            animationManager.animateRotation(gdxLogo, 20, 9999 * 3, AnimationManager.Easing.EASE_OSCILLATE_INFINITE);
            animationManager.animateFade(logo, (float) (logo.getAlpha() - 0.5), 9999 * 3, AnimationManager.Easing.EASE_OSCILLATE_INFINITE);
            animationManager.animateMove(allRoadsLeadToRome, allRoadsLeadToRome.getX() + 10, allRoadsLeadToRome.getY() + 10, 9999 * 3, AnimationManager.Easing.EASE_OSCILLATE_INFINITE);
            animationManager.animateFade(focus, 1, 9999 * 3, AnimationManager.Easing.EASE_OSCILLATE_INFINITE);
        }));
        cutsceneManager.addEvent(new CutsceneEvent(12f, () -> {
            playButton.setX(playButtonAni.getX());
            playButton.setY(playButtonAni.getY());
        }));

    //     try {


    //         customerQueue.getElm(0).getClankerReview();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }

        cutsceneManager.addEvent(new CutsceneEvent(3f, () -> {
                animationManager.animateMove(new AnimatedSprite(customerQueue.getElm(0).getSprite()), customerQueue.getElm(0).getSprite().getX() + 200, customerQueue.getElm(0).getSprite().getY() + 200, 6f, AnimationManager.Easing.EASE_IN_OUT_ELASTIC);
        }));
    }

    public void disposeIntro() {
        if (introSong != null) introSong.dispose();
        if (playButton != null) playButton.dispose();
        if (playButtonAni != null) playButtonAni.dispose();

        bgm.play();
        cutsceneManager.addEvent(new CutsceneEvent(timePassed + 1, () -> {
            for (int i = 1; i <= 100; i++) {
                float ia = i;
                cutsceneManager.addEvent(new CutsceneEvent(timePassed + i, () -> {
                    bgm.setVolume(ia);
                }));
            }
        }));
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

        cutsceneManager.update(timePassed);

        if (gameState == GameState.MINIGAME) {
            ScreenUtils.clear(0f, 0f, 0f, 1f);
            batch.begin();
            Player.getInstance().getCurrentAppliance().display(batch);
            batch.end();
        } else if (gameState == GameState.WORLD) {
            if (spotLight != null) {
                spotLight.dispose();
                spotLight = null;
            }
            batch.begin();
            batch.draw(backgroundTexture, 0, 0);
            for (Sprite sprite : spriteObjects) sprite.render(batch);
            for (Sprite sprite : spriteObjects) {
                for (Customer customer : customerQueue.getArrayList()) {
                    if (customer.getSprite() == sprite) {
                        customer.drawOrderMsg(font, batch);
                    }
                }
            }
            batch.end();
        } else if (gameState == GameState.INTRO) {
            parallaxBackground.update(dt);
            batch.begin();

            //ScreenUtils.clear(237/255f, 185/255f, 43/255f, 1f);
            if (renderParallax) parallaxBackground.render(camera, batch);

            for (AnimatedSprite aSprite : animatedSprites) {
                aSprite.render(batch);
            }

            if (playButton != null && playButton.isVisible()) playButton.update(dt);

            batch.end();

            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                gameState = GameState.WORLD;
                disposeIntro();
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Sprite sprite : spriteObjects) sprite.dispose();
    }
}
