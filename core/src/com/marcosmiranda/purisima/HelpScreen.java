package com.marcosmiranda.purisima;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;

class HelpScreen implements Screen {

    private final Purisima game;
    private final Skin skin;
    private final Stage stage;

    HelpScreen(final Purisima purisima) {
        game = purisima;

        skin = new Skin();
        stage = new Stage(new StretchViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        Gdx.input.setCatchKey(Input.Keys.MENU, true);

        // Get fonts from the asset manager
        final BitmapFont arial16 = game.assets.get("arial16.ttf", BitmapFont.class);
        final BitmapFont comic18 = game.assets.get("comic18.ttf", BitmapFont.class);
        final Label.LabelStyle defaultLblStyle = new Label.LabelStyle(arial16, Color.WHITE);
        final Label.LabelStyle helpLblStyle = new Label.LabelStyle(comic18, Color.WHITE);
        skin.add("default", defaultLblStyle);
        skin.add("help", helpLblStyle);

        // Rounded rectangle button
        Image btnImage = new Image(game.assets.get("sprites/button.png", Texture.class));
        final Drawable btnDrawable = btnImage.getDrawable();

        final Label lblHelp1 = new Label(
                "Cuando inicia el juego, estás en control de la bolsa.\n" +
                        "La bolsa se puede controlar presionando la pantalla\n" +
                        "a la izquierda o a la derecha de la bolsa.",
                helpLblStyle);
        lblHelp1.setPosition(50, 320);
        stage.addActor(lblHelp1);

        final Image imgPouch = new Image(game.assets.get("sprites/pouch.png", Texture.class));
        imgPouch.setSize(64, 64);
        imgPouch.setPosition(600, 325);
        stage.addActor(imgPouch);

        final Label lblHelp2 = new Label(
                "Tu objetivo es tratar de capturar los regalos que caen del cielo.\n" +
                        "Cuando agarres uno, te dará cierta cantidad de puntos.\n" +
                        "Pero si lo dejas caer, perderás la mitad de los puntos que da.",
                helpLblStyle);
        lblHelp2.setPosition(50, 230);
        stage.addActor(lblHelp2);

        final Image imgNacatamal = new Image(game.assets.get("sprites/nacatamal.png", Texture.class));
        imgNacatamal.setSize(64, 64);
        imgNacatamal.setPosition(710, 225);
        stage.addActor(imgNacatamal);

        final Label lblHelp3 = new Label(
                "Cada " + Constants.SPEEDUP_DELAY + " segundos, los regalos caerán más rápido.\n" +
                        "¡Y ten cuidado con los fuegos artificiales!\n" +
                        "Perderás si capturas un petardo,\n" +
                        "o si dejas caer muchos regalos.",
                helpLblStyle);
        lblHelp3.setPosition(50, 120);
        stage.addActor(lblHelp3);

        final Image imgFirework = new Image(game.assets.get("sprites/petardo.png", Texture.class));
        imgFirework.setSize(48, 72);
        imgFirework.setPosition(550, 130);
        stage.addActor(imgFirework);

        final Label lblHelp4 = new Label("¡Diviértete!", helpLblStyle);
        lblHelp4.setPosition(600, 120);
        stage.addActor(lblHelp4);

        final ImageTextButton.ImageTextButtonStyle creditsBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        creditsBtnStyle.up = skin.newDrawable(btnDrawable, Color.VIOLET);
        creditsBtnStyle.down = skin.newDrawable(btnDrawable, Color.MAGENTA);
        creditsBtnStyle.font = arial16;
        skin.add("creditsBtn", creditsBtnStyle);

        final ImageTextButton.ImageTextButtonStyle historyBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        historyBtnStyle.up = skin.newDrawable(btnDrawable, Color.CYAN);
        historyBtnStyle.down = skin.newDrawable(btnDrawable, Color.TEAL);
        historyBtnStyle.font = arial16;
        skin.add("historyBtn", historyBtnStyle);

        final ImageTextButton.ImageTextButtonStyle backBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        backBtnStyle.up = skin.newDrawable(btnDrawable, Color.SCARLET);
        backBtnStyle.down = skin.newDrawable(btnDrawable, Color.FIREBRICK);
        backBtnStyle.font = arial16;
        skin.add("backBtn", backBtnStyle);

        final Label creditsBtnLbl = new Label(" CRÉDITOS ", skin);
        final Image creditsBtnIcon = new Image(game.assets.get("icons/star_black.png", Texture.class));
        final ImageTextButton creditsBtn = new ImageTextButton(null, skin, "creditsBtn");
        creditsBtn.add(creditsBtnIcon, creditsBtnLbl);
        creditsBtn.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        creditsBtn.setPosition(65, 30);
        creditsBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new CreditsScreen(game));
            }
        });
        stage.addActor(creditsBtn);

        final Label historyBtnLbl = new Label(" HISTORIA ", skin);
        final Image historyBtnIcon = new Image(game.assets.get("icons/clock.png", Texture.class));
        final ImageTextButton historyBtn = new ImageTextButton(null, skin, "historyBtn");
        historyBtn.add(historyBtnIcon, historyBtnLbl);
        historyBtn.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        historyBtn.setPosition(300, 30);
        historyBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new HistoryScreen(game));
            }
        });
        stage.addActor(historyBtn);

        final Label backBtnLbl = new Label(" VOLVER ", skin);
        final Image backBtnIcon = new Image(game.assets.get("icons/exit.png", Texture.class));
        final ImageTextButton backBtn = new ImageTextButton(null, skin, "backBtn");
        backBtn.add(backBtnIcon, backBtnLbl);
        backBtn.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        backBtn.setPosition(Constants.MAIN_MENU_BUTTON_X, Constants.EXIT_BUTTON_Y);
        backBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(backBtn);

        Utility.setBackColor(Constants.BACK_COLOR);
    }

    @Override
    public void render(float delta) {
        Utility.clear();
        stage.act(Math.min(delta, 1 / Constants.FRAME_RATE));
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
        stage.dispose();
        skin.dispose();
    }
}