package com.marcosmiranda.purisima;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static com.marcosmiranda.purisima.Utility.clear;
import static com.marcosmiranda.purisima.Utility.setBackColor;

class MainMenuScreen implements Screen {

    private final Purisima game;
    private Stage stage;
    private Skin skin;
    private Preferences prefs;
    private Texture logo;
    private Array<Goodie> goodies;
    private Pool<Goodie> goodiePool;
    private double goodieTime, fallingSpeed;
    private int logoWidth, logoHeight;

    MainMenuScreen(final Purisima purisima) {
        // Store the passed game instance for later use
        game = purisima;
        game.state = GameState.MENU;

        // Create the appropriate objects for drawing
        skin = new Skin();
        stage = new Stage(new StretchViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);

        // Get fonts from the asset manager
        BitmapFont arial16 = game.assets.get("arial16.ttf", BitmapFont.class);
        BitmapFont arial18 = game.assets.get("arial18.ttf", BitmapFont.class);
        Label.LabelStyle defaultLblStyle = new Label.LabelStyle(arial16, Color.WHITE);
        Label.LabelStyle sosLblStyle = new Label.LabelStyle(arial18, Color.WHITE);
        skin.add("default", defaultLblStyle);
        skin.add("sos", sosLblStyle);

        // Rounded rectangle button
        Image btnImage = new Image(game.assets.get("sprites/button.png", Texture.class));
        Drawable btnDrawable = btnImage.getDrawable();

        // Logo
        logo = game.assets.get("purisima.png", Texture.class);
        logoWidth = logo.getWidth();
        logoHeight = logo.getHeight();

        // Goodies
        goodies = new Array<Goodie>();
        goodiePool = new Pool<Goodie>() {
            @Override
            protected Goodie newObject() {
                return new Goodie(game.assets);
            }
        };
        fallingSpeed = Constants.GOODIE_FALLING_SPEED_MENU;

        // Build the various button styles
        ImageTextButton.ImageTextButtonStyle playBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        playBtnStyle.up = skin.newDrawable(btnDrawable, Color.LIME);
        playBtnStyle.down = skin.newDrawable(btnDrawable, Color.FOREST);
        playBtnStyle.font = arial16;
        skin.add("playBtnStyle", playBtnStyle);

        ImageTextButton.ImageTextButtonStyle optionsBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        optionsBtnStyle.up = skin.newDrawable(btnDrawable, Color.ROYAL);
        optionsBtnStyle.down = skin.newDrawable(btnDrawable, Color.NAVY);
        optionsBtnStyle.font = arial16;
        skin.add("optionsBtnStyle", optionsBtnStyle);

        ImageTextButton.ImageTextButtonStyle helpBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        helpBtnStyle.up = skin.newDrawable(btnDrawable, Color.VIOLET);
        helpBtnStyle.down = skin.newDrawable(btnDrawable, Color.MAGENTA);
        helpBtnStyle.font = arial16;
        skin.add("helpBtnStyle", helpBtnStyle);

        ImageTextButton.ImageTextButtonStyle exitBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        exitBtnStyle.up = skin.newDrawable(btnDrawable, Color.SCARLET);
        exitBtnStyle.down = skin.newDrawable(btnDrawable, Color.FIREBRICK);
        exitBtnStyle.font = arial16;
        skin.add("exitBtnStyle", exitBtnStyle);

        // Create the buttons
        Label playBtnLbl = new Label(" JUGAR ", skin, "default");
        Image playBtnIcon = new Image((game.assets.get("icons/play.png", Texture.class)));
        ImageTextButton playBtn = new ImageTextButton(null, skin, "playBtnStyle");
        playBtn.add(playBtnIcon, playBtnLbl);
        playBtn.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        playBtn.setPosition(Constants.MAIN_MENU_BUTTON_X, 300);
        playBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                //game.adsController.hideBannerAd();
                game.setScreen(new GameScreen(game));
            }
        });
        stage.addActor(playBtn);

        Label optionsBtnLbl = new Label(" OPCIONES ", skin, "default");
        Image optionsBtnIcon = new Image((game.assets.get("icons/wrench.png", Texture.class)));
        ImageTextButton optionsBtn = new ImageTextButton(null, skin, "optionsBtnStyle");
        optionsBtn.add(optionsBtnIcon, optionsBtnLbl);
        optionsBtn.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        optionsBtn.setPosition(Constants.MAIN_MENU_BUTTON_X, 210);
        optionsBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new OptionsScreen(game));
            }
        });
        stage.addActor(optionsBtn);

        Label helpBtnLbl = new Label(" AYUDA ", skin, "default");
        Image helpBtnIcon = new Image((game.assets.get("icons/help.png", Texture.class)));
        ImageTextButton helpBtn = new ImageTextButton(null, skin, "helpBtnStyle");
        helpBtn.add(helpBtnIcon, helpBtnLbl);
        helpBtn.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        helpBtn.setPosition(Constants.MAIN_MENU_BUTTON_X, 120);
        helpBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new HelpScreen(game));
            }
        });
        stage.addActor(helpBtn);

        Label exitBtnLbl = new Label(" SALIR ", skin, "default");
        Image exitBtnIcon = new Image((game.assets.get("icons/exit.png", Texture.class)));
        ImageTextButton exitBtn = new ImageTextButton(null, exitBtnStyle);
        exitBtn.add(exitBtnIcon, exitBtnLbl);
        exitBtn.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        exitBtn.setPosition(Constants.MAIN_MENU_BUTTON_X, Constants.EXIT_BUTTON_Y);
        exitBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                Gdx.app.exit();
            }
        });
        stage.addActor(exitBtn);

        // choose menu music randomly
        prefs = Gdx.app.getPreferences("purisima");
        boolean musicEnabled = prefs.getBoolean("music", true);
        int volume = prefs.getInteger("volume", Constants.DEFAULT_VOLUME);
        if (musicEnabled && !game.music.isPlaying()) {
            game.music.stop();
            game.music = Utility.selectMusic();
            game.music.setVolume(volume / Constants.VOLUME_DIVIDER);
            game.music.setLooping(true);
            game.music.play();
        }

        setBackColor(Constants.BACK_COLOR);
    }

    @Override
    public void render(float delta) {
        if (game.state.equals(GameState.MENU)) {
            clear();
            game.camera.update();
            game.batch.setProjectionMatrix(game.camera.combined);

            game.batch.begin();

            if (goodies.size > 0) {
                for (int i = goodies.size; i <= 0; i--) {
                    Goodie goodie = goodies.get(i);
                    if (!goodie.active) {
                        goodies.removeIndex(i);
                        goodiePool.free(goodie);
                    }
                }
            }

            goodieTime += delta;
            while (goodieTime >= Constants.GOODIE_SPAWN_DELAY_MENU) {
                Goodie goodie = goodiePool.obtain();
                goodie.init(fallingSpeed, 400);
                goodies.add(goodie);
                goodieTime -= Constants.GOODIE_SPAWN_DELAY_MENU;
            }

            for (Goodie goodie : goodies) {
                if (goodie.active) {
                    goodie.sprite.draw(game.batch);
                    goodie.update();
                    if (goodie.y < -goodie.sprite.getHeight()) {
                        goodie.active = false;
                    }
                }
            }

            game.batch.draw(logo, Constants.LOGO_X, Constants.LOGO_Y, logoWidth, logoHeight);
            game.batch.end();

            stage.act(Math.min(delta, 1 / Constants.FRAME_RATE));
            stage.draw();
        }
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
        //dispose();
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        prefs.flush();
        goodiePool.clear();
        stage.dispose();
        skin.dispose();
    }
}