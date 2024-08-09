package com.marcosmiranda.purisima;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static com.marcosmiranda.purisima.Constants.BACK_COLOR;
import static com.marcosmiranda.purisima.Constants.BUTTON_HEIGHT;
import static com.marcosmiranda.purisima.Constants.BUTTON_WIDTH;
import static com.marcosmiranda.purisima.Constants.CHECKBOX_SIZE;
import static com.marcosmiranda.purisima.Constants.CURSOR_WIDTH;
import static com.marcosmiranda.purisima.Constants.DEFAULT_VOLUME;
import static com.marcosmiranda.purisima.Constants.EXIT_BUTTON_Y;
import static com.marcosmiranda.purisima.Constants.FRAME_RATE;
import static com.marcosmiranda.purisima.Constants.MAIN_MENU_BUTTON_X;
import static com.marcosmiranda.purisima.Constants.TEXTFIELD_PADDING;
import static com.marcosmiranda.purisima.Constants.WINDOW_HEIGHT;
import static com.marcosmiranda.purisima.Constants.WINDOW_WIDTH;
import static com.marcosmiranda.purisima.Utility.clear;
import static com.marcosmiranda.purisima.Utility.setBackColor;

class NewGameScreen implements Screen {

    private final Purisima game;
    private final Stage stage;
    private final Skin skin;
    private final Preferences prefs;
    private String playerName;
    private boolean music;

    NewGameScreen(final Purisima purisima) {
        // Store the passed game instance for later use
        game = purisima;
        setBackColor(BACK_COLOR);

        // Show ads, if there's WiFi
        // if (game.adsController.isWifiOn() || game.adsController.isDataOn()) game.adsController.showBannerAd();

        // Get the player preferences
        prefs = Gdx.app.getPreferences("purisima");
        music = prefs.getBoolean("music", true);
        playerName = prefs.getString("playerName", "JUGADOR");

        // Create the appropiate objects for drawing
        skin = new Skin();
        stage = new Stage(new StretchViewport(WINDOW_WIDTH, WINDOW_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        Gdx.input.setCatchKey(Input.Keys.MENU, true);

        // Add a white pixmap to the skin
        Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        String pixmapName = "white";
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add(pixmapName, new Texture(pixmap));
        pixmap.dispose();

        // Rounded rectangle button
        Image btnImage = new Image(game.assets.get("sprites/button.png", Texture.class));
        Drawable btnDrawable = btnImage.getDrawable();

        // Get fonts from the asset manager & build the various element styles
        BitmapFont arial16 = game.assets.get("arial16.ttf", BitmapFont.class);
        BitmapFont arial18 = game.assets.get("arial18.ttf", BitmapFont.class);
        BitmapFont arial24 = game.assets.get("arial24.ttf", BitmapFont.class);
        BitmapFont comic16 = game.assets.get("comic16.ttf", BitmapFont.class);
        Label.LabelStyle defaultLabelStyle = new Label.LabelStyle(arial18, Color.WHITE);
        Label.LabelStyle headerLabelStyle = new Label.LabelStyle(arial24, Color.GOLD);
        Label.LabelStyle errorLabelStyle = new Label.LabelStyle(comic16, Color.RED);
        skin.add("default", defaultLabelStyle);
        skin.add("header", headerLabelStyle);
        skin.add("error", errorLabelStyle);

        TextField.TextFieldStyle playerNameTextFieldStyle = new TextField.TextFieldStyle();
        playerNameTextFieldStyle.font = arial18;
        playerNameTextFieldStyle.fontColor = Color.WHITE;
        playerNameTextFieldStyle.background = skin.newDrawable(btnDrawable, Color.WHITE);
        playerNameTextFieldStyle.background.setLeftWidth(TEXTFIELD_PADDING);
        playerNameTextFieldStyle.background.setRightWidth(TEXTFIELD_PADDING);
        playerNameTextFieldStyle.cursor = skin.newDrawable(pixmapName, Color.DARK_GRAY);
        playerNameTextFieldStyle.cursor.setMinWidth(CURSOR_WIDTH);
        skin.add("playerName", playerNameTextFieldStyle);

        final Sprite check = new Sprite(game.assets.get("icons/check.png", Texture.class));
        check.setSize(CHECKBOX_SIZE, CHECKBOX_SIZE);
        skin.add("check", check);

        final CheckBox.CheckBoxStyle musicCheckBoxStyle = new CheckBox.CheckBoxStyle();
        musicCheckBoxStyle.up = skin.newDrawable(btnDrawable, Color.WHITE);
        musicCheckBoxStyle.down = skin.newDrawable(btnDrawable, Color.GRAY);
        musicCheckBoxStyle.checkboxOn = skin.newDrawable("check", Color.BLACK);
        musicCheckBoxStyle.font = arial16;
        skin.add("musicCheckBoxStyle", musicCheckBoxStyle);

        ImageTextButton.ImageTextButtonStyle nextButtonStyle = new ImageTextButton.ImageTextButtonStyle();
        nextButtonStyle.up = skin.newDrawable(btnDrawable, Color.LIME);
        nextButtonStyle.down = skin.newDrawable(btnDrawable, Color.FOREST);
        nextButtonStyle.font = arial16;
        skin.add("nextButton", nextButtonStyle);

        // Create the elements
        final Label headerLabel = new Label("Introduce tu nombre para empezar:", skin, "header");
        headerLabel.setPosition(200, 335);
        stage.addActor(headerLabel);

        final Label playerNameLabel = new Label("Nombre del jugador: ", skin, "default");
        playerNameLabel.setPosition(210, 285);
        stage.addActor(playerNameLabel);

        final TextField playerNameTextField = new TextField(null, skin, "playerName");
        playerNameTextField.setSize(Constants.TEXTFIELD_WIDTH, Constants.TEXTFIELD_HEIGHT);
        playerNameTextField.setPosition(420, 270);
        playerNameTextField.setText(playerName);
        stage.addActor(playerNameTextField);

        final Label errorLabel = new Label("ERROR: Tu nombre no puede estar vacío.", skin, "error");
        errorLabel.setPosition(250, 230);
        errorLabel.setVisible(false);
        stage.addActor(errorLabel);

        final Label musicLabel = new Label("¿Activar la música? ", skin, "default");
        musicLabel.setPosition(210, 165);
        stage.addActor(musicLabel);

        final CheckBox musicCheckBox = new CheckBox(null, musicCheckBoxStyle);
        musicCheckBox.setSize(CHECKBOX_SIZE, CHECKBOX_SIZE);
        musicCheckBox.setPosition(420, 150);
        musicCheckBox.setChecked(music);
        musicCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music = musicCheckBox.isChecked();
                prefs.putBoolean("music", music);
                prefs.flush();
            }
        });
        stage.addActor(musicCheckBox);

        Label nextButtonLabel = new Label(" EMPEZAR ", skin, "default");
        Image nextButtonIcon = new Image(game.assets.get("icons/arrow-right.png", Texture.class));
        ImageTextButton nextButton = new ImageTextButton(null, skin, "nextButton");
        nextButton.add(nextButtonIcon, nextButtonLabel);
        nextButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        nextButton.setPosition(MAIN_MENU_BUTTON_X, EXIT_BUTTON_Y);
        nextButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                playerName = playerNameTextField.getText();
                if (playerName.isEmpty()) {
                    errorLabel.setVisible(true);
                } else {
                    prefs.putString("playerName", playerName);
                    prefs.putInteger("hiScore", 0);
                    prefs.putInteger("volume", DEFAULT_VOLUME);
                    prefs.flush();
                    dispose();
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        });
        stage.addActor(nextButton);
    }

    @Override
    public void render(float delta) {
        clear();
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
        //dispose();
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        prefs.flush();
        stage.dispose();
        skin.dispose();
    }
}