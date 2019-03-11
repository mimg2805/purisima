package com.marcosmiranda.purisima;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static com.marcosmiranda.purisima.Constants.BACK_COLOR;
import static com.marcosmiranda.purisima.Constants.BUTTON_HEIGHT;
import static com.marcosmiranda.purisima.Constants.BUTTON_WIDTH;
import static com.marcosmiranda.purisima.Constants.CURSOR_WIDTH;
import static com.marcosmiranda.purisima.Constants.DEFAULT_VOLUME;
import static com.marcosmiranda.purisima.Constants.EXIT_BUTTON_Y;
import static com.marcosmiranda.purisima.Constants.FRAME_RATE;
import static com.marcosmiranda.purisima.Constants.MAIN_MENU_BUTTON_X;
import static com.marcosmiranda.purisima.Constants.SMALL_BUTTON_SIZE;
import static com.marcosmiranda.purisima.Constants.TEXTFIELD_PADDING;
import static com.marcosmiranda.purisima.Constants.VOLUME_DIVIDER;
import static com.marcosmiranda.purisima.Constants.WINDOW_HEIGHT;
import static com.marcosmiranda.purisima.Constants.WINDOW_WIDTH;
import static com.marcosmiranda.purisima.Utility.clear;
import static com.marcosmiranda.purisima.Utility.setBackColor;

class OptionsScreen implements Screen {

    private final Purisima game;
    private final Stage stage;
    private final Skin skin;
    private final Preferences prefs;
    private String playerName;
    private int hiScore, volume;
    private boolean music;
    private final Music menuMusic;
    private final Slider volumeSld;

    OptionsScreen(final Purisima purisima) {
        // Store the passed game instance for later use
        game = purisima;
        setBackColor(BACK_COLOR);

        // Get the player preferences
        prefs = Gdx.app.getPreferences("purisima");
        playerName = prefs.getString("playerName", "");
        hiScore = prefs.getInteger("hiScore", 0);
        music = prefs.getBoolean("music", true);
        volume = prefs.getInteger("volume", DEFAULT_VOLUME);
        menuMusic = game.music;

        // Create the appropiate objects for drawing
        skin = new Skin();
        stage = new Stage(new StretchViewport(WINDOW_WIDTH, WINDOW_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        // Add a white pixmap to the skin
        final Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        final String pixmapName = "white";
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add(pixmapName, new Texture(pixmap));

        // Rounded rectangle buttons
        Image btnImage = new Image(game.assets.get("sprites/button.png", Texture.class));
        final Drawable btnDrawable = btnImage.getDrawable();
        Image smallBtnImage = new Image(game.assets.get("sprites/button_small.png", Texture.class));
        Drawable smallBtnDrawable = smallBtnImage.getDrawable();

        // add the check icon for use with the checkbox
        final Sprite check = new Sprite(game.assets.get("icons/check.png", Texture.class));
        final int checkSize = 48;
        check.setSize(checkSize, checkSize);
        skin.add("check", check);

        // Get fonts from the asset manager & build the various element styles
        final BitmapFont arial16 = game.assets.get("arial16.ttf", BitmapFont.class);
        final BitmapFont arial18 = game.assets.get("arial16.ttf", BitmapFont.class);
        final BitmapFont comic16 = game.assets.get("comic16.ttf", BitmapFont.class);
        final BitmapFont comic18 = game.assets.get("comic18.ttf", BitmapFont.class);
        final Label.LabelStyle defaultLblStyle = new Label.LabelStyle(arial16, Color.WHITE);
        final Label.LabelStyle optionsLblStyle = new Label.LabelStyle(comic18, Color.WHITE);
        final Label.LabelStyle resultLblStyle = new Label.LabelStyle(comic16, Color.WHITE);
        skin.add("default", defaultLblStyle);
        skin.add("options", optionsLblStyle);
        skin.add("result", resultLblStyle);

        final ImageTextButton.ImageTextButtonStyle playerOptsBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        playerOptsBtnStyle.up = skin.newDrawable(btnDrawable, Color.LIGHT_GRAY);
        playerOptsBtnStyle.down = skin.newDrawable(btnDrawable, Color.DARK_GRAY);
        playerOptsBtnStyle.font = arial16;
        skin.add("playerOptsBtn", playerOptsBtnStyle);

        final ImageTextButton.ImageTextButtonStyle soundOptsBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        soundOptsBtnStyle.up = skin.newDrawable(btnDrawable, Color.WHITE);
        soundOptsBtnStyle.down = skin.newDrawable(btnDrawable, Color.DARK_GRAY);
        soundOptsBtnStyle.font = arial16;
        skin.add("soundOptsBtn", soundOptsBtnStyle);

        final TextField.TextFieldStyle playerNameTxtFieldStyle = new TextField.TextFieldStyle();
        playerNameTxtFieldStyle.font = arial18;
        playerNameTxtFieldStyle.fontColor = Color.WHITE;
        playerNameTxtFieldStyle.background = skin.newDrawable(btnDrawable, Color.WHITE);
        playerNameTxtFieldStyle.background.setLeftWidth(TEXTFIELD_PADDING);
        playerNameTxtFieldStyle.background.setRightWidth(TEXTFIELD_PADDING);
        playerNameTxtFieldStyle.cursor = skin.newDrawable(pixmapName, Color.DARK_GRAY);
        playerNameTxtFieldStyle.cursor.setMinWidth(CURSOR_WIDTH);
        skin.add("playerNameTxtField", playerNameTxtFieldStyle);

        final ImageButton.ImageButtonStyle greenBtnStyle = new ImageButton.ImageButtonStyle();
        greenBtnStyle.up = skin.newDrawable(smallBtnDrawable, Color.LIME);
        greenBtnStyle.down = skin.newDrawable(btnDrawable, Color.FOREST);
        skin.add("greenBtn", greenBtnStyle);

        final ImageButton.ImageButtonStyle redBtnStyle = new ImageButton.ImageButtonStyle();
        redBtnStyle.up = skin.newDrawable(smallBtnDrawable, Color.SCARLET);
        redBtnStyle.down = skin.newDrawable(btnDrawable, Color.FIREBRICK);
        skin.add("redBtn", redBtnStyle);

        final CheckBox.CheckBoxStyle musicChkBoxStyle = new CheckBox.CheckBoxStyle();
        musicChkBoxStyle.up = skin.newDrawable(smallBtnDrawable, Color.WHITE);
        musicChkBoxStyle.down = skin.newDrawable(smallBtnDrawable, Color.GRAY);
        musicChkBoxStyle.checkboxOn = skin.newDrawable("check", Color.BLACK);
        musicChkBoxStyle.font = arial16;
        skin.add("musicChkBox", musicChkBoxStyle);

        final Slider.SliderStyle volumeSldStyle = new Slider.SliderStyle();
        volumeSldStyle.knob = skin.newDrawable(btnDrawable, Color.BLUE);
        volumeSldStyle.knobDown = skin.newDrawable(btnDrawable, Color.ROYAL);
        volumeSldStyle.background = skin.newDrawable(btnDrawable, Color.TEAL);
        final int knobWidth = 30;
        final int knobHeight = 70;
        final int sliderHeight = 50;
        volumeSldStyle.knob.setMinWidth(knobWidth);
        volumeSldStyle.knobDown.setMinWidth(knobWidth);
        volumeSldStyle.knob.setMinHeight(knobHeight);
        volumeSldStyle.knobDown.setMinHeight(knobHeight);
        volumeSldStyle.background.setMinHeight(sliderHeight);
        skin.add("volumeSld", volumeSldStyle);

        final ImageTextButton.ImageTextButtonStyle backBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        backBtnStyle.up = skin.newDrawable(btnDrawable, Color.SCARLET);
        backBtnStyle.down = skin.newDrawable(btnDrawable, Color.FIREBRICK);
        backBtnStyle.font = arial16;
        skin.add("backBtn", backBtnStyle);

        // Create the elements

        // Change player name row
        final Label playerNameLbl = new Label("Nombre del jugador: ", skin, "options");
        playerNameLbl.setPosition(50, 265);
        stage.addActor(playerNameLbl);

        final Label resultLbl = new Label("", skin, "result");
        resultLbl.setPosition(50, 230);
        resultLbl.setVisible(false);
        stage.addActor(resultLbl);

        final TextField playerNameTxtField = new TextField(playerName, skin, "playerNameTxtField");
        playerNameTxtField.setSize(Constants.TEXTFIELD_WIDTH, Constants.TEXTFIELD_HEIGHT);
        playerNameTxtField.setPosition(270, 250);
        stage.addActor(playerNameTxtField);

        final Image saveBtnIcon = new Image(game.assets.get("icons/save.png", Texture.class));
        final ImageButton saveBtn = new ImageButton(greenBtnStyle);
        saveBtn.add(saveBtnIcon);
        saveBtn.setSize(SMALL_BUTTON_SIZE, SMALL_BUTTON_SIZE);
        saveBtn.setPosition(480, 250);
        saveBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                playerName = playerNameTxtField.getText();
                if (playerName.equals("")) {
                    resultLbl.setText("ERROR: El campo no puede estar vacío.");
                    resultLbl.setColor(Color.RED);
                    resultLbl.setVisible(true);
                } else {
                    prefs.putString("playerName", playerName);
                    prefs.flush();
                    resultLbl.setText("¡Guardado!");
                    resultLbl.setColor(Color.GREEN);
                    resultLbl.setVisible(true);

                }
            }
        });
        stage.addActor(saveBtn);

        // Reset high score row
        final Label hiScoreTxtLbl = new Label("Puntaje máximo: ", skin, "options");
        hiScoreTxtLbl.setPosition(50, 180);
        stage.addActor(hiScoreTxtLbl);

        final Label hiScoreLbl = new Label("" + hiScore + "", skin, "options");
        hiScoreLbl.setPosition(270, 180);
        stage.addActor(hiScoreLbl);

        final Image eraseBtnIcon = new Image(game.assets.get("icons/trash.png", Texture.class));
        final ImageButton eraseBtn = new ImageButton(redBtnStyle);
        eraseBtn.add(eraseBtnIcon);
        eraseBtn.setSize(SMALL_BUTTON_SIZE, SMALL_BUTTON_SIZE);
        eraseBtn.setPosition(480, 165);
        eraseBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                hiScore = 0;
                hiScoreLbl.setText("" + hiScore + "");
                prefs.putInteger("hiScore", hiScore);
                prefs.flush();
            }
        });
        stage.addActor(eraseBtn);

        // Toggle music row
        final Label musicTxtLbl = new Label("Música: ", skin, "options");
        musicTxtLbl.setPosition(50, 250);
        musicTxtLbl.setVisible(false);
        stage.addActor(musicTxtLbl);

        final CheckBox musicChkBox = new CheckBox(null, musicChkBoxStyle);
        musicChkBox.setSize(checkSize, checkSize);
        musicChkBox.setPosition(150, 240);
        musicChkBox.setChecked(music);
        musicChkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music = musicChkBox.isChecked();
                if (music) {
                    menuMusic.setVolume(volume / VOLUME_DIVIDER);
                    menuMusic.play();
                } else {
                    menuMusic.pause();
                }
                prefs.putBoolean("music", music);
                prefs.flush();
            }
        });
        musicChkBox.setVisible(false);
        stage.addActor(musicChkBox);

        // Music volume row
        final int volumeBtnsY = 110;

        final Label volumeTxtLbl = new Label("Volumen de la música: ", skin, "options");
        volumeTxtLbl.setPosition(50, 180);
        volumeTxtLbl.setVisible(false);
        stage.addActor(volumeTxtLbl);

        final Label volumeLbl = new Label("" + volume + "", skin, "options");
        volumeLbl.setPosition(280, 180);
        volumeLbl.setVisible(false);
        stage.addActor(volumeLbl);

        final Image volumeDownBtnIcon = new Image(game.assets.get("icons/volume-down.png", Texture.class));
        int volumeDownBtnSize = 50;
        final ImageButton volumeDownBtn = new ImageButton(redBtnStyle);
        volumeDownBtn.add(volumeDownBtnIcon);
        volumeDownBtn.setSize(volumeDownBtnSize, volumeDownBtnSize);
        volumeDownBtn.setPosition(50, volumeBtnsY);
        volumeDownBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (volume > 0) {
                    volume -= 10;
                    volumeUpdate(volume);
                    volumeLbl.setText("" + volume + "");
                }
            }
        });
        volumeDownBtn.setVisible(false);
        stage.addActor(volumeDownBtn);

        volumeSld = new Slider(0, 100, 1, false, skin, "volumeSld");
        volumeSld.setWidth(250);
        volumeSld.setPosition(100, volumeBtnsY-10);
        volumeSld.setValue(volume);
        volumeSld.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                volume = (int) volumeSld.getValue();
                menuMusic.setVolume(volume / VOLUME_DIVIDER);
                volumeLbl.setText("" + volume + "");
                prefs.putInteger("volume", volume);
                prefs.flush();
            }
        });
        volumeSld.setVisible(false);
        stage.addActor(volumeSld);

        final Image volumeUpBtnIcon = new Image(game.assets.get("icons/volume-up.png", Texture.class));
        final int volumeUpBtnSize = 50;
        final ImageButton volumeUpBtn = new ImageButton(greenBtnStyle);
        volumeUpBtn.add(volumeUpBtnIcon);
        volumeUpBtn.setSize(volumeUpBtnSize, volumeUpBtnSize);
        volumeUpBtn.setPosition(350, volumeBtnsY);
        volumeUpBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (volume < 100) {
                    volume += 10;
                    volumeUpdate(volume);
                    volumeLbl.setText("" + volume + "");
                }
            }
        });
        volumeUpBtn.setVisible(false);
        stage.addActor(volumeUpBtn);

        // Options row at the top
        final Label playerOptsBtnLbl = new Label(" JUGADOR ",skin,"default");
        final Image playerOptsBtnIcon = new Image(game.assets.get("icons/cog.png", Texture.class));
        final ImageTextButton playerOptsBtn = new ImageTextButton(null, skin, "playerOptsBtn");
        playerOptsBtn.add(playerOptsBtnIcon, playerOptsBtnLbl);
        playerOptsBtn.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        playerOptsBtn.setPosition(30, 320);
        playerOptsBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                playerNameLbl.setVisible(true);
                playerNameTxtField.setVisible(true);
                saveBtn.setVisible(true);
                hiScoreTxtLbl.setVisible(true);
                hiScoreLbl.setVisible(true);
                eraseBtn.setVisible(true);
                musicTxtLbl.setVisible(false);
                musicChkBox.setVisible(false);
                volumeTxtLbl.setVisible(false);
                volumeLbl.setVisible(false);
                volumeDownBtn.setVisible(false);
                volumeSld.setVisible(false);
                volumeUpBtn.setVisible(false);
                playerOptsBtnStyle.up = skin.newDrawable(btnDrawable, Color.LIGHT_GRAY);
                playerOptsBtnStyle.down = skin.newDrawable(btnDrawable, Color.DARK_GRAY);
                soundOptsBtnStyle.up = skin.newDrawable(btnDrawable, Color.WHITE);
                soundOptsBtnStyle.down = skin.newDrawable(btnDrawable, Color.DARK_GRAY);
            }
        });
        stage.addActor(playerOptsBtn);

        final Label soundOptsBtnLbl = new Label(" SONIDO ",skin,"default");
        final Image soundOptsBtnIcon = new Image(game.assets.get("icons/cog.png", Texture.class));
        final ImageTextButton soundOptsBtn = new ImageTextButton(null, skin, "soundOptsBtn");
        soundOptsBtn.add(soundOptsBtnIcon, soundOptsBtnLbl);
        soundOptsBtn.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        soundOptsBtn.setPosition(260, 320);
        soundOptsBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                playerNameLbl.setVisible(false);
                playerNameTxtField.setVisible(false);
                resultLbl.setVisible(false);
                saveBtn.setVisible(false);
                hiScoreTxtLbl.setVisible(false);
                hiScoreLbl.setVisible(false);
                eraseBtn.setVisible(false);
                musicTxtLbl.setVisible(true);
                musicChkBox.setVisible(true);
                volumeTxtLbl.setVisible(true);
                volumeLbl.setVisible(true);
                volumeDownBtn.setVisible(true);
                volumeSld.setVisible(true);
                volumeUpBtn.setVisible(true);
                playerOptsBtnStyle.up = skin.newDrawable(btnDrawable, Color.WHITE);
                playerOptsBtnStyle.down = skin.newDrawable(btnDrawable, Color.DARK_GRAY);
                soundOptsBtnStyle.up = skin.newDrawable(btnDrawable, Color.LIGHT_GRAY);
                soundOptsBtnStyle.down = skin.newDrawable(btnDrawable, Color.DARK_GRAY);
            }
        });
        stage.addActor(soundOptsBtn);

        // Back button
        final Label backBtnLbl = new Label(" VOLVER ", skin, "default");
        final Image backBtnIcon = new Image(game.assets.get("icons/exit.png", Texture.class));
        final ImageTextButton backBtn = new ImageTextButton(null, skin, "backBtn");
        backBtn.add(backBtnIcon, backBtnLbl);
        backBtn.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        backBtn.setPosition(MAIN_MENU_BUTTON_X, EXIT_BUTTON_Y);
        backBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(backBtn);
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
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
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

    private void volumeUpdate(int volume) {
        if (volume < 0) volume = 0;
        else if (volume > 100) volume = 100;
        menuMusic.setVolume(volume / VOLUME_DIVIDER);
        volumeSld.setValue(volume);
        prefs.putInteger("volume", volume);
        prefs.flush();
    }
}