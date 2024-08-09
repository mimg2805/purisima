package com.marcosmiranda.purisima;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
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
    private final Stage stage;
    private final Skin skin;
    private final Preferences prefs;
    private final Array<Goodie> goodies;
    private final Pool<Goodie> goodiePool;
    private double goodieTime;
    private final double fallingSpeed;

    MainMenuScreen(final Purisima purisima) {
        // Store the passed game instance for later use
        game = purisima;
        game.state = GameState.MENU;

        // Create the appropriate objects for drawing
        skin = new Skin();
        stage = new Stage(new StretchViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        Gdx.input.setCatchKey(Input.Keys.MENU, true);

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
        Image logo = new Image(game.assets.get("images/purisima.png", Texture.class));
        logo.setPosition(Constants.LOGO_X, Constants.LOGO_Y);
        stage.addActor(logo);

        // Goodies
        goodies = new Array<>();
        goodiePool = new Pool<Goodie>() {
            @Override
            protected Goodie newObject() {
                return new Goodie(game.assets);
            }
        };
        fallingSpeed = Constants.GOODIE_FALLING_SPEED_MENU;

        // Build the various button styles
        ImageTextButtonStyle playBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        playBtnStyle.up = skin.newDrawable(btnDrawable, Color.LIME);
        playBtnStyle.down = skin.newDrawable(btnDrawable, Color.FOREST);
        playBtnStyle.font = arial16;
        skin.add("playBtnStyle", playBtnStyle);

        ImageTextButtonStyle optionsBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        optionsBtnStyle.up = skin.newDrawable(btnDrawable, Color.ROYAL);
        optionsBtnStyle.down = skin.newDrawable(btnDrawable, Color.NAVY);
        optionsBtnStyle.font = arial16;
        skin.add("optionsBtnStyle", optionsBtnStyle);

        ImageTextButtonStyle helpBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        helpBtnStyle.up = skin.newDrawable(btnDrawable, Color.VIOLET);
        helpBtnStyle.down = skin.newDrawable(btnDrawable, Color.MAGENTA);
        helpBtnStyle.font = arial16;
        skin.add("helpBtnStyle", helpBtnStyle);

        ImageTextButtonStyle moreAppsBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        moreAppsBtnStyle.up = skin.newDrawable(btnDrawable, Color.LIGHT_GRAY);
        moreAppsBtnStyle.down = skin.newDrawable(btnDrawable, Color.DARK_GRAY);
        moreAppsBtnStyle.font = arial16;
        skin.add("moreAppsBtnStyle", moreAppsBtnStyle);

        ImageTextButtonStyle exitBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        exitBtnStyle.up = skin.newDrawable(btnDrawable, Color.SCARLET);
        exitBtnStyle.down = skin.newDrawable(btnDrawable, Color.FIREBRICK);
        exitBtnStyle.font = arial16;
        skin.add("exitBtnStyle", exitBtnStyle);

        // Create the buttons
        int buttonsRow1Y = 300;
        Label playBtnLbl = new Label(" JUGAR ", skin, "default");
        Image playBtnIcon = new Image((game.assets.get("icons/play.png", Texture.class)));
        ImageTextButton playBtn = new ImageTextButton(null, skin, "playBtnStyle");
        playBtn.add(playBtnIcon, playBtnLbl);
        playBtn.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        // playBtn.setPosition(370, buttonsRow1Y);
        playBtn.setPosition(Constants.MAIN_MENU_BUTTON_X, 320);
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
        // optionsBtn.setPosition(580, buttonsRow1Y);
        optionsBtn.setPosition(Constants.MAIN_MENU_BUTTON_X, 245);
        optionsBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new OptionsScreen(game));
            }
        });
        stage.addActor(optionsBtn);

        int buttonsRow2Y = 200;
        Label helpBtnLbl = new Label(" AYUDA ", skin, "default");
        Image helpBtnIcon = new Image((game.assets.get("icons/help.png", Texture.class)));
        ImageTextButton helpBtn = new ImageTextButton(null, skin, "helpBtnStyle");
        helpBtn.add(helpBtnIcon, helpBtnLbl);
        helpBtn.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        // helpBtn.setPosition(370, buttonsRow2Y);
        helpBtn.setPosition(Constants.MAIN_MENU_BUTTON_X, 170);
        helpBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new HelpScreen(game));
            }
        });
        stage.addActor(helpBtn);

        Label moreAppsBtnLbl = new Label(" MÁS APPS ", skin, "default");
        Image moreAppsBtnIcon = new Image((game.assets.get("icons/circle_plus.png", Texture.class)));
        ImageTextButton moreAppsBtn = new ImageTextButton(null, skin, "moreAppsBtnStyle");
        moreAppsBtn.add(moreAppsBtnIcon, moreAppsBtnLbl);
        moreAppsBtn.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        // moreAppsBtn.setPosition(580, buttonsRow2Y);
        moreAppsBtn.setPosition(Constants.MAIN_MENU_BUTTON_X, 95);
        moreAppsBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                game.androidController.openPlayStore();
            }
        });
        stage.addActor(moreAppsBtn);

        int buttonsRow3Y = 50;
        Label exitBtnLbl = new Label(" SALIR ", skin, "default");
        Image exitBtnIcon = new Image((game.assets.get("icons/exit.png", Texture.class)));
        ImageTextButton exitBtn = new ImageTextButton(null, skin, "exitBtnStyle");
        exitBtn.add(exitBtnIcon, exitBtnLbl);
        exitBtn.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        // exitBtn.setPosition(460, buttonsRow3Y);
        exitBtn.setPosition(Constants.MAIN_MENU_BUTTON_X, 20);
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
                for (int i = goodies.size; i == 0; i--) {
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
                goodie.init(fallingSpeed, Constants.WINDOW_WIDTH);
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