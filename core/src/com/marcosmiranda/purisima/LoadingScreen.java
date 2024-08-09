package com.marcosmiranda.purisima;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static com.marcosmiranda.purisima.Utility.clear;
import static com.marcosmiranda.purisima.Utility.createFonts;
import static com.marcosmiranda.purisima.Utility.setBackColor;

class LoadingScreen implements Screen {

    private final Purisima game;
    private final AssetManager assets;
    private final Stage stage;
    private final Skin skin;
    private Preferences prefs;
    private int progress = 0;
    private final Label progressLbl;

    LoadingScreen(final Purisima purisima) {
        // Store the passed game instance for later use
        game = purisima;
        assets = game.assets;
        setBackColor(Constants.BACK_COLOR);

        // Show ads, if there's WiFi
        // if (game.adsController.isWifiOn() || game.adsController.isDataOn()) game.adsController.showBannerAd();

        // Create the appropiate objects for drawing
        skin = new Skin();
        stage = new Stage(new StretchViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        Gdx.input.setCatchKey(Input.Keys.MENU, true);

        // set the loaders for the generator and the fonts themselves
        final FileHandleResolver resolver = new InternalFileHandleResolver();
        assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        // create and load default fonts
        final float borderWidth = 2f;
        final Color borderColor = Color.DARK_GRAY;
        //createFonts(assets, "arial", 12, borderWidth, borderColor);
        //createFonts(assets, "comic", 12, borderWidth, borderColor);
        //createFonts(assets, "arial", 14, borderWidth, borderColor);
        //createFonts(assets, "comic", 14, borderWidth, borderColor);
        createFonts(assets, "arial", 16, borderWidth, borderColor);
        createFonts(assets, "comic", 16, borderWidth, borderColor);
        createFonts(assets, "arial", 18, borderWidth, borderColor);
        createFonts(assets, "comic", 18, borderWidth, borderColor);
        createFonts(assets, "arial", 20, borderWidth, borderColor);
        createFonts(assets, "comic", 20, borderWidth, borderColor);
        //createFonts(assets, "arial", 22, borderWidth, borderColor);
        createFonts(assets, "comic", 22, borderWidth, borderColor);
        createFonts(assets, "arial", 24, borderWidth, borderColor);
        createFonts(assets, "comic", 24, borderWidth, borderColor);
        //createFonts(assets, "arial", 28, borderWidth, borderColor);
        //createFonts(assets, "comic", 28, borderWidth, borderColor);
        createFonts(assets, "arial", 32, borderWidth, borderColor);
        createFonts(assets, "comic", 32, borderWidth, borderColor);
        //createFonts(assets, "arial", 48, borderWidth, borderColor);
        //createFonts(assets, "comic", 48, borderWidth, borderColor);

        // Load all of the needed resources
        assets.load("images/purisima.png", Texture.class);
        assets.load("images/seniasnicas.png", Texture.class);
        assets.load("images/cursoestadisticabasica.png", Texture.class);
        assets.load("images/nicaroadrage.png", Texture.class);
        assets.load("bg/bg.png", Texture.class);

        assets.load("icons/arrow-right.png", Texture.class);
        assets.load("icons/check.png", Texture.class);
        assets.load("icons/clock.png", Texture.class);
        assets.load("icons/cog.png", Texture.class);
        assets.load("icons/circle_plus.png", Texture.class);
        assets.load("icons/exit.png", Texture.class);
        assets.load("icons/help.png", Texture.class);
        assets.load("icons/pause_button.png", Texture.class);
        assets.load("icons/play.png", Texture.class);
        assets.load("icons/plus.png", Texture.class);
        assets.load("icons/sad_yellow.png", Texture.class);
        assets.load("icons/save.png", Texture.class);
        assets.load("icons/star_black.png", Texture.class);
        assets.load("icons/trash.png", Texture.class);
        assets.load("icons/volume-down.png", Texture.class);
        assets.load("icons/volume-up.png", Texture.class);
        assets.load("icons/wrench.png", Texture.class);

        assets.load("sprites/button.png", Texture.class);
        assets.load("sprites/button_small.png", Texture.class);
        assets.load("sprites/explosion.png", Texture.class);
        assets.load("sprites/agua_loja.png", Texture.class);
        assets.load("sprites/baston.png", Texture.class);
        assets.load("sprites/cajeta.png", Texture.class);
        assets.load("sprites/cania_azucar.png", Texture.class);
        assets.load("sprites/chicha.png", Texture.class);
        assets.load("sprites/chocolate.png", Texture.class);
        assets.load("sprites/gofio.png", Texture.class);
        assets.load("sprites/gummy.png", Texture.class);
        assets.load("sprites/lapiz.png", Texture.class);
        assets.load("sprites/malvavisco.png", Texture.class);
        assets.load("sprites/matraca.png", Texture.class);
        assets.load("sprites/menta.png", Texture.class);
        assets.load("sprites/nacatamal.png", Texture.class);
        assets.load("sprites/naranja.png", Texture.class);
        assets.load("sprites/pelota.png", Texture.class);
        assets.load("sprites/petardo.png", Texture.class);
        assets.load("sprites/pouch.png", Texture.class);

        assets.load("sound/explosion1.wav", Sound.class);
        assets.load("sound/explosion2.wav", Sound.class);
        assets.load("sound/explosion3.wav", Sound.class);

        // Loading text
        final String fontName = "arial24.ttf";
        assets.finishLoadingAsset(fontName);
        final BitmapFont arial24 = game.assets.get(fontName, BitmapFont.class);
        final Label.LabelStyle defaultLabelStyle = new Label.LabelStyle(arial24, Color.WHITE);
        skin.add("default", defaultLabelStyle);

        // Create the elements
        final Label loadingLabel = new Label("Cargando: ", skin, "default");
        loadingLabel.setPosition(310, 200);
        stage.addActor(loadingLabel);

        progressLbl = new Label(progress + "%", skin, "default");
        progressLbl.setPosition(450, 200);
        stage.addActor(progressLbl);
    }

    @Override
    public void render(float delta) {
        clear();
        stage.act(Math.min(delta, 1 / Constants.FRAME_RATE));
        stage.draw();

        if (!assets.update()) {
            progress = (int) (assets.getProgress() * 100f);
            progressLbl.setText(progress + "%");
        } else {
            prefs = Gdx.app.getPreferences("purisima");
            String playerName = prefs.getString("playerName", "");
            if (playerName.isEmpty()) {
                game.setScreen(new NewGameScreen(game));
            } else {
                game.setScreen(new MainMenuScreen(game));
                // game.setScreen(new SoundTest(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        prefs.flush();
        stage.dispose();
        skin.dispose();
    }
}