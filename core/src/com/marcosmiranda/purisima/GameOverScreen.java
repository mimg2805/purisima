package com.marcosmiranda.purisima;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static com.marcosmiranda.purisima.Constants.*;
import static com.marcosmiranda.purisima.Utility.*;

class GameOverScreen implements Screen {

    private final Purisima game;
    private final Stage stage;
    private final Skin skin;
    private int score, hiScore;
    private double newHiScoreBlink;
    private final Label newHiScoreLbl;
    private final Music gameOverSound;

    GameOverScreen(final Purisima purisima, final int gameScore) {
        // Store the passed game instance for later use
        game = purisima;
        if (game.adsController.isWifiOn()) game.adsController.showBannerAd();
        game.gameState = GameState.GAMEOVER;
        score = gameScore;
        gameOverSound = Gdx.audio.newMusic(Gdx.files.internal("sound/gameover.wav"));
        Preferences prefs = Gdx.app.getPreferences("purisima");
        hiScore = prefs.getInteger("hiScore", 0);
        final boolean music = prefs.getBoolean("music", true);
        final int volume = prefs.getInteger("volume", DEFAULT_VOLUME);

        // Create the appropiate objects for drawing
        skin = new Skin();
        stage = new Stage(new StretchViewport(WINDOW_WIDTH, WINDOW_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);

        // Get fonts from the asset manager
        BitmapFont arial16 = game.assets.get("arial16.ttf", BitmapFont.class);
        BitmapFont arial24 = game.assets.get("arial24.ttf", BitmapFont.class);
        BitmapFont arial32 = game.assets.get("arial32.ttf", BitmapFont.class);
        Label.LabelStyle arial16LblStyle = new Label.LabelStyle(arial16, Color.WHITE);
        Label.LabelStyle arial24LblStyle = new Label.LabelStyle(arial24, Color.WHITE);
        Label.LabelStyle arial32LblStyle = new Label.LabelStyle(arial32, Color.WHITE);
        skin.add("arial16Lbl", arial16LblStyle);
        skin.add("arial24Lbl", arial24LblStyle);
        skin.add("arial32Lbl", arial32LblStyle);


        // Rounded rectangle button
        Image btnImage = new Image(game.assets.get("sprites/button.png", Texture.class));
        Drawable btnDrawable = btnImage.getDrawable();

        Label.LabelStyle gameOverLblStyle1 = new Label.LabelStyle(arial32, Color.YELLOW);
        skin.add("gameOver1", gameOverLblStyle1);

        Label.LabelStyle gameOverLblStyle2 = new Label.LabelStyle();
        gameOverLblStyle2.font = arial24;
        //gameOverLabelStyle2.fontColor = Color.YELLOW;
        skin.add("gameOver2", gameOverLblStyle2);

        // Build the various button styles
        ImageTextButton.ImageTextButtonStyle playBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        playBtnStyle.up = skin.newDrawable(btnDrawable, Color.LIME);
        playBtnStyle.down = skin.newDrawable(btnDrawable, Color.FOREST);
        playBtnStyle.font = arial16;
        skin.add("playBtnStyle", playBtnStyle);

        ImageTextButton.ImageTextButtonStyle exitBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        exitBtnStyle.up = skin.newDrawable(btnDrawable, Color.SCARLET);
        exitBtnStyle.down = skin.newDrawable(btnDrawable, Color.FIREBRICK);
        exitBtnStyle.font = arial16;
        skin.add("exitBtnStyle", exitBtnStyle);

        // Create the elements
        Label gameOverLbl = new Label(" ¡PERDISTE! ", gameOverLblStyle1);
        gameOverLbl.setPosition(WINDOW_WIDTH_HALF - gameOverLbl.getWidth() / 2, WINDOW_HEIGHT - 100);
        stage.addActor(gameOverLbl);

        Label scoreLbl = new Label(" Puntaje: " + score, gameOverLblStyle2);
        scoreLbl.setPosition(WINDOW_WIDTH_HALF - scoreLbl.getWidth() / 2, 250);
        stage.addActor(scoreLbl);

        Label hiScoreLbl = new Label(" Puntaje máximo: " + hiScore, gameOverLblStyle2);
        hiScoreLbl.setPosition(WINDOW_WIDTH_HALF - hiScoreLbl.getWidth() / 2, 200);
        stage.addActor(hiScoreLbl);

        newHiScoreLbl = new Label(" ¡NUEVO PUNTAJE MÁXIMO! ", gameOverLblStyle1);
        newHiScoreLbl.setVisible(false);
        newHiScoreLbl.setPosition(WINDOW_WIDTH_HALF - newHiScoreLbl.getWidth() / 2, WINDOW_HEIGHT_HALF - 70);
        stage.addActor(newHiScoreLbl);

        Image sadImg1 = new Image(game.assets.get("icons/sad_yellow.png", Texture.class));
        Image sadImg2 = new Image(game.assets.get("icons/sad_yellow.png", Texture.class));
        sadImg1.setPosition(gameOverLbl.getX() - 30, gameOverLbl.getY());
        sadImg2.setPosition(gameOverLbl.getX() + gameOverLbl.getWidth(), gameOverLbl.getY());
        stage.addActor(sadImg1);
        stage.addActor(sadImg2);

        Label playBtnLbl = new Label(" Jugar de nuevo ", skin, "arial16Lbl");
        Image playBtnIcon = new Image(game.assets.get("icons/play.png", Texture.class));
        ImageTextButton playBtn = new ImageTextButton(null, skin, "playBtnStyle");
        playBtn.add(playBtnIcon, playBtnLbl);
        playBtn.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        playBtn.setPosition(65, EXIT_BUTTON_Y);
        playBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if (gameOverSound.isPlaying()) gameOverSound.stop();
                dispose();
                game.setScreen(new GameScreen(game));
            }
        });
        stage.addActor(playBtn);

        Label exitBtnLbl = new Label(" Volver al menú ", skin, "arial16Lbl");
        Image exitBtnIcon = new Image(game.assets.get("icons/exit.png", Texture.class));
        ImageTextButton exitBtn = new ImageTextButton(null, skin, "exitBtnStyle");
        exitBtn.add(exitBtnIcon, exitBtnLbl);
        exitBtn.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        exitBtn.setPosition(MAIN_MENU_BUTTON_X, EXIT_BUTTON_Y);
        exitBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if (gameOverSound.isPlaying()) gameOverSound.stop();
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(exitBtn);

        setBackColor(BACK_COLOR);

        if (music) {
            gameOverSound.setVolume(volume / VOLUME_DIVIDER);
            gameOverSound.play();
        }
    }

    @Override
    public void render(float delta) {
        clear();
        if (score > 0 && hiScore > 0 && score >= hiScore) {
            newHiScoreBlink += delta;
            while (newHiScoreBlink >= NEW_HI_SCORE_BLINK_DELAY) {
                newHiScoreLbl.setVisible(!newHiScoreLbl.isVisible());
                newHiScoreBlink -= NEW_HI_SCORE_BLINK_DELAY;
            }
        }
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
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        gameOverSound.dispose();
        stage.dispose();
        skin.dispose();
    }
}