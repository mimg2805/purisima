package com.marcosmiranda.purisima;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.Random;

import static com.marcosmiranda.purisima.Constants.DEFAULT_VOLUME;
import static com.marcosmiranda.purisima.Constants.DIALOG_BUTTON_HEIGHT;
import static com.marcosmiranda.purisima.Constants.DIALOG_BUTTON_WIDTH;
import static com.marcosmiranda.purisima.Constants.DIALOG_BUTTON_Y;
import static com.marcosmiranda.purisima.Constants.EXPLOSION_ANIMATION_COLS;
import static com.marcosmiranda.purisima.Constants.EXPLOSION_ANIMATION_ROWS;
import static com.marcosmiranda.purisima.Constants.FRAME_RATE;
import static com.marcosmiranda.purisima.Constants.GOODIE_FALLING_SPEED;
import static com.marcosmiranda.purisima.Constants.GOODIE_FALLING_SPEED_SUM;
import static com.marcosmiranda.purisima.Constants.GOODIE_MAX_FALLING_SPEED;
import static com.marcosmiranda.purisima.Constants.GOODIE_SPAWN_DELAY;
import static com.marcosmiranda.purisima.Constants.GOODIE_SPAWN_DELAY_SUM;
import static com.marcosmiranda.purisima.Constants.HI_SCORE_LOCATION_X;
import static com.marcosmiranda.purisima.Constants.HI_SCORE_LOCATION_Y;
import static com.marcosmiranda.purisima.Constants.HI_SCORE_PREFIX;
import static com.marcosmiranda.purisima.Constants.LEVEL_MESSAGE_LOCATION_X;
import static com.marcosmiranda.purisima.Constants.LEVEL_MESSAGE_LOCATION_Y;
import static com.marcosmiranda.purisima.Constants.NAME_LOCATION_X;
import static com.marcosmiranda.purisima.Constants.NAME_LOCATION_Y;
import static com.marcosmiranda.purisima.Constants.PAUSE_BUTTON_HEIGHT;
import static com.marcosmiranda.purisima.Constants.PAUSE_BUTTON_POSITION_X;
import static com.marcosmiranda.purisima.Constants.PAUSE_BUTTON_POSITION_Y;
import static com.marcosmiranda.purisima.Constants.PAUSE_BUTTON_WIDTH;
import static com.marcosmiranda.purisima.Constants.PAUSE_CONTINUE_BUTTON_X;
import static com.marcosmiranda.purisima.Constants.PAUSE_EXIT_BUTTON_X;
import static com.marcosmiranda.purisima.Constants.SCORE_LOCATION_X;
import static com.marcosmiranda.purisima.Constants.SCORE_LOCATION_Y;
import static com.marcosmiranda.purisima.Constants.SCORE_PREFIX;
import static com.marcosmiranda.purisima.Constants.SPEEDUP_DELAY;
import static com.marcosmiranda.purisima.Constants.VIBRATE_MILLISECONDS;
import static com.marcosmiranda.purisima.Constants.VOLUME_DIVIDER;
import static com.marcosmiranda.purisima.Constants.WINDOW_HEIGHT;
import static com.marcosmiranda.purisima.Constants.WINDOW_WIDTH;
import static com.marcosmiranda.purisima.Utility.clear;
import static com.marcosmiranda.purisima.Utility.selectMusic;

class GameScreen implements Screen {
    private final Purisima game;
    private final Button pauseButton;
    private final ImageTextButton continueBtn, exitBtn;
    private final Preferences prefs;
    private boolean vibrator, explosionSoundPlayed;
    private final Stage stage;
    private final Pouch pouch;
    private final Texture bgImg;
    private int score, hiScore;
    private final String playerName;
    private final boolean musicEnabled;
    private final BitmapFont comic16, comic24;
    private final Dialog pauseDialog;
    private final Array<Goodie> goodies;
    private final Array<PopUpString> messages;
    private final Pool<Goodie> goodiePool;
    private final Pool<PopUpString> messagePool;
    private final Animation<TextureRegion> explosionAnimation;
    private final ShapeRenderer shapeRenderer;
    private final Sound explosionSound;

    private double levelTime, goodieTime, fallingSpeed, goodieSpawnDelay;
    private float animationTime;

    GameScreen(final Purisima purisima) {
        game = purisima;
        game.adsController.hideBannerAd();
        game.state = GameState.RUNNING;
        shapeRenderer = new ShapeRenderer();

        // Randomly choose an explosion sound
        final Random rand = new Random();
        String filePath = "sound/";
        switch (rand.nextInt(3)) {
            case 0:
                filePath += "explosion1.wav";
                break;
            case 1:
            default:
                filePath += "explosion2.wav";
                break;
            case 2:
                filePath += "explosion3.wav";
                break;
        }
        explosionSound = game.assets.get(filePath, Sound.class);
        explosionSoundPlayed = false;

        final Skin skin = new Skin();
        stage = new Stage(new StretchViewport(WINDOW_WIDTH, WINDOW_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);

        // Add a white pixmap to the skin
        final Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        final String pixmapName = "white";
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add(pixmapName, new Texture(pixmap));
        pixmap.dispose();

        // Rounded rectangle button
        Image btnImage = new Image(game.assets.get("sprites/button.png", Texture.class));
        Drawable btnDrawable = btnImage.getDrawable();

        goodieTime = 0;
        levelTime = 0;
        goodieSpawnDelay = GOODIE_SPAWN_DELAY;
        //goodieTime = levelTime = Gdx.graphics.getDeltaTime();
        vibrator = Gdx.input.isPeripheralAvailable(Input.Peripheral.Vibrator);

        // get the player name and previous high score, if any
        prefs = Gdx.app.getPreferences("purisima");
        playerName = prefs.getString("playerName", "");
        hiScore = prefs.getInteger("hiScore", 0);
        final int volume = prefs.getInteger("volume", DEFAULT_VOLUME);
        musicEnabled = prefs.getBoolean("music", true);

        pouch = new Pouch(game.assets);
        goodies = new Array<Goodie>();
        messages = new Array<PopUpString>();
        goodiePool = new Pool<Goodie>() {
            @Override
            protected Goodie newObject() {
                return new Goodie(game.assets);
            }
        };
        messagePool = new Pool<PopUpString>() {
            @Override
            protected PopUpString newObject() {
                return new PopUpString();
            }
        };
        fallingSpeed = GOODIE_FALLING_SPEED;

        // load fonts
        comic16 = game.assets.get("comic16.ttf", BitmapFont.class);
        final BitmapFont arial24 = game.assets.get("arial24.ttf", BitmapFont.class);
        comic24 = game.assets.get("comic24.ttf", BitmapFont.class);
        Label.LabelStyle comic16LblStyle = new Label.LabelStyle(comic16, Color.WHITE);
        Label.LabelStyle arial24LblStyle = new Label.LabelStyle(arial24, Color.WHITE);
        Label.LabelStyle comic24LblStyle = new Label.LabelStyle(comic24, Color.WHITE);
        skin.add("comic16", comic16LblStyle);
        skin.add("arial24", arial24LblStyle);
        skin.add("comic24", comic24LblStyle);

        // background image
        bgImg = game.assets.get("bg/bg.png", Texture.class);

        // pause and resume button styles
        Button.ButtonStyle gameBtnStyle = new Button.ButtonStyle();
        gameBtnStyle.up = gameBtnStyle.down = skin.newDrawable(pixmapName, Color.CLEAR);
        skin.add("gameBtn", gameBtnStyle);

        // pause button
        Image pauseButtonIcon = new Image(game.assets.get("icons/pause_button.png", Texture.class));
        pauseButton = new Button(skin, "gameBtn");
        pauseButton.add(pauseButtonIcon);
        pauseButton.setPosition(PAUSE_BUTTON_POSITION_X, PAUSE_BUTTON_POSITION_Y);
        pauseButton.setSize(PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);
        pauseButton.setVisible(true);
        pauseButton.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                pause();
            }
        });
        stage.addActor(pauseButton);

        // Continue and exit button styles
        ImageTextButton.ImageTextButtonStyle continueBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        continueBtnStyle.up = skin.newDrawable(btnDrawable, Color.LIME);
        continueBtnStyle.down = skin.newDrawable(btnDrawable, Color.FOREST);
        continueBtnStyle.font = comic16;
        skin.add("continueBtn", continueBtnStyle);

        ImageTextButton.ImageTextButtonStyle exitBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        exitBtnStyle.up = skin.newDrawable(btnDrawable, Color.SCARLET);
        exitBtnStyle.down = skin.newDrawable(btnDrawable, Color.FIREBRICK);
        exitBtnStyle.font = comic16;
        skin.add("exitBtn", exitBtnStyle);

        // Continue and exit buttons
        Image continueBtnIcon = new Image(game.assets.get("icons/play.png", Texture.class));
        Label continueBtnLbl = new Label(" Continuar ", skin, "comic16");
        continueBtn = new ImageTextButton(null, skin, "continueBtn");
        continueBtn.add(continueBtnIcon, continueBtnLbl);

        Image exitBtnIcon = new Image(game.assets.get("icons/exit.png", Texture.class));
        Label exitBtnLbl = new Label(" Salir ", skin, "comic16");
        exitBtn = new ImageTextButton(null, skin, "exitBtn");
        exitBtn.add(exitBtnIcon, exitBtnLbl);

        // initializing the pause dialog
        Window.WindowStyle pauseDialogStyle = new Window.WindowStyle();
        pauseDialogStyle.titleFont = comic24;
        pauseDialogStyle.stageBackground = skin.newDrawable(pixmapName, Color.valueOf("000000AA"));
        skin.add("pauseDialog", pauseDialogStyle);

        pauseDialog = new Dialog(" PAUSA ", skin, "pauseDialog") {
            protected void result(Object result) {
                boolean resume = Boolean.parseBoolean(result.toString());
                if (resume) resume();
                else {
                    game.state = GameState.MENU;
                    if (game.adsController.isWifiOn()) game.adsController.showBannerAd();
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        };
        pauseDialog.getTitleLabel().setAlignment(Align.center);
        pauseDialog.button(continueBtn, true);
        pauseDialog.button(exitBtn, false);

        // Create the explosion
        Texture explosionSheet = game.assets.get("sprites/explosion.png", Texture.class);
        TextureRegion[][] splitExplosionSheet = TextureRegion.split(explosionSheet, explosionSheet.getWidth() / EXPLOSION_ANIMATION_COLS, explosionSheet.getHeight() / EXPLOSION_ANIMATION_ROWS);
        TextureRegion[] explosionFrames = new TextureRegion[EXPLOSION_ANIMATION_COLS * EXPLOSION_ANIMATION_ROWS];
        int index = 0;
        for (int i = 0; i < EXPLOSION_ANIMATION_ROWS; i++) {
            for (int j = 0; j < EXPLOSION_ANIMATION_COLS; j++) {
                explosionFrames[index++] = splitExplosionSheet[i][j];
            }
        }
        explosionAnimation = new Animation<TextureRegion>(0.025f, explosionFrames);
        explosionAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        animationTime = 0f;

        // play music
        if (musicEnabled && !game.music.isPlaying()) {
            game.music.stop();
            game.music = selectMusic();
            game.music.setVolume(volume / VOLUME_DIVIDER);
            game.music.setLooping(true);
            game.music.play();
        }
    }

    @Override
    public void render(float delta) {
        clear();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        shapeRenderer.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.batch.draw(bgImg, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        comic16.setColor(Color.WHITE);

        switch (game.state) {
            case RUNNING:

                if (pouch.active) {
                    game.batch.draw(pouch.sprite, pouch.rect.x, pouch.rect.y);
                    pouch.update(game.camera);
                }

                for (Goodie goodie : goodies) {
                    if (goodie.active) {
                        goodie.sprite.draw(game.batch);
                        goodie.update();
                        if (pouch.active && pouch.rect.overlaps(goodie.rect)) {
                            goodie.active = false;

                            PopUpString points = messagePool.obtain();
                            points.init(goodie);
                            messages.add(points);
                            score += goodie.points;

                            if (goodie.deadly) { // If you grab a deadly goodie, you lose
                                game.state = GameState.GAMEOVER;
                            }
                        } else if (goodie.y < -goodie.sprite.getHeight()) {
                            goodie.points = goodie.points / -2;
                            goodie.active = false;

                            PopUpString points = messagePool.obtain();
                            points.init(goodie);
                            messages.add(points);

                            score += goodie.points;
                        }

                        if (score < 0) { // If you lose points while on 0 points, you lose
                            score = 0;
                            game.state = GameState.GAMEOVER;
                        } else if (score > hiScore) hiScore = score;
                    }
                }

                for (PopUpString message : messages) {
                    if (message.active) {
                        comic24.setColor(message.color);
                        comic24.draw(game.batch, message.message, message.x, message.y);
                        message.update();
                    }
                }

                comic16.setColor(Color.YELLOW);
                comic16.draw(game.batch, playerName, NAME_LOCATION_X, NAME_LOCATION_Y);
                comic16.draw(game.batch, SCORE_PREFIX + score, SCORE_LOCATION_X, SCORE_LOCATION_Y);
                comic16.draw(game.batch, HI_SCORE_PREFIX + hiScore, HI_SCORE_LOCATION_X, HI_SCORE_LOCATION_Y);

                // Clear inactive goodies
                if (goodies.size > 0) {
                    for (int i = goodies.size; i <= 0; i--) {
                        Goodie goodie = goodies.get(i);
                        if (!goodie.active) {
                            goodies.removeIndex(i);
                            goodiePool.free(goodie);
                        }
                    }
                }

                // Clear inactive messages
                if (messages.size > 0) {
                    for (int i = messages.size; i <= 0; i--) {
                        PopUpString message = messages.get(i);
                        if (!message.active) {
                            messages.removeIndex(i);
                            messagePool.free(message);
                        }
                    }
                }

                // Spawn a new goodie every second
                goodieTime += delta;
                while (goodieTime >= goodieSpawnDelay) {
                    Goodie goodie = goodiePool.obtain();
                    goodie.init(fallingSpeed, WINDOW_WIDTH);
                    goodies.add(goodie);
                    goodieTime -= goodieSpawnDelay;
                }

                // Increase the falling speed after 15 seconds
                levelTime += delta;
                while (levelTime >= SPEEDUP_DELAY && fallingSpeed < GOODIE_MAX_FALLING_SPEED && goodies.size > 0 && pouch.active) {
                    fallingSpeed += GOODIE_FALLING_SPEED_SUM;
                    for (Goodie goodie : goodies)
                        if (goodie.active)
                            goodie.fallingSpeed = fallingSpeed;

                    goodieSpawnDelay -= GOODIE_SPAWN_DELAY_SUM;

                    PopUpString message = messagePool.obtain();
                    message.init(LEVEL_MESSAGE_LOCATION_X, LEVEL_MESSAGE_LOCATION_Y, "¡MÁS RÁPIDO!", Color.BLUE);
                    messages.add(message);
                    levelTime -= SPEEDUP_DELAY;
                }
                break;

            case GAMEOVER:
                if (vibrator) Gdx.input.vibrate(VIBRATE_MILLISECONDS);
                pauseButton.setVisible(false);
                pouch.active = false;
                animationTime += delta;
                if (game.music.isPlaying()) game.music.stop();
                if (musicEnabled && !explosionSoundPlayed) {
                    explosionSound.play();
                    explosionSoundPlayed = true;
                }

                // Play explosion animation
                TextureRegion currentFrame = explosionAnimation.getKeyFrame(animationTime, false);
                if (explosionAnimation.isAnimationFinished(animationTime)) {
                    prefs.putInteger("hiScore", hiScore);
                    prefs.flush();
                    game.setScreen(new GameOverScreen(game, score));
                } else {
                    game.batch.draw(currentFrame, pouch.rect.x - pouch.sprite.getWidth() / 2, pouch.rect.y);
                }
                break;

            default:
                break;
        }

        game.batch.end();
        stage.act(Math.min(delta, 1 / FRAME_RATE));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        pauseDialog.show(stage);
        pauseDialog.setVisible(true);

        continueBtn.setSize(DIALOG_BUTTON_WIDTH, DIALOG_BUTTON_HEIGHT);
        exitBtn.setSize(DIALOG_BUTTON_WIDTH, DIALOG_BUTTON_HEIGHT);
        continueBtn.setPosition(PAUSE_CONTINUE_BUTTON_X, DIALOG_BUTTON_Y, Align.center);
        exitBtn.setPosition(PAUSE_EXIT_BUTTON_X, DIALOG_BUTTON_Y, Align.center);

        if (pauseDialog.isVisible()) {
            pauseButton.setVisible(false);
            game.state = GameState.PAUSE;
        }

    }

    @Override
    public void resume() {
        pauseDialog.hide();
        pauseDialog.setVisible(false);
        if (!pauseDialog.isVisible()) {
            pauseButton.setVisible(true);
            game.state = GameState.RUNNING;
        }
    }

    @Override
    public void dispose() {
        prefs.flush();

        bgImg.dispose();

        comic16.dispose();
        comic24.dispose();

        goodies.clear();
        goodiePool.clear();

        messages.clear();
        messagePool.clear();
    }
}