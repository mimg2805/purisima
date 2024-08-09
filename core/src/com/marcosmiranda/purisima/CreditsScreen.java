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

import static com.marcosmiranda.purisima.Constants.*;
import static com.marcosmiranda.purisima.Utility.*;

class CreditsScreen implements Screen {

    private final Purisima game;
    private final Skin skin;
    private final Stage stage;

    CreditsScreen(final Purisima purisima) {
        game = purisima;

        skin = new Skin();
        stage = new Stage(new StretchViewport(WINDOW_WIDTH, WINDOW_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        Gdx.input.setCatchKey(Input.Keys.MENU, true);

        // Get fonts from the asset manager
        BitmapFont arial16 = game.assets.get("arial16.ttf", BitmapFont.class);
        BitmapFont comic20 = game.assets.get("comic20.ttf", BitmapFont.class);
        BitmapFont comic22 = game.assets.get("comic22.ttf", BitmapFont.class);
        Label.LabelStyle defaultLblStyle = new Label.LabelStyle(arial16, Color.WHITE);
        Label.LabelStyle creditsTitleLblStyle = new Label.LabelStyle(comic22, Color.WHITE);
        Label.LabelStyle creditsLblStyle = new Label.LabelStyle(comic20, Color.WHITE);
        skin.add("default", defaultLblStyle);
        skin.add("creditsTitle", creditsTitleLblStyle);
        skin.add("credits", creditsLblStyle);

        // Rounded rectangle button
        Image btnImage = new Image(game.assets.get("sprites/button.png", Texture.class));
        Drawable btnDrawable = btnImage.getDrawable();

        Label creditTitle1 = new Label("Idea original, programación y sprites:", creditsTitleLblStyle);
        creditTitle1.setPosition(50, 350);
        stage.addActor(creditTitle1);

        Label credit1 = new Label("Marcos Iván Miranda García", creditsLblStyle);
        credit1.setPosition(50, 320);
        stage.addActor(credit1);

        Label creditTitle2 = new Label("Caricatura de la Virgen María:", creditsTitleLblStyle);
        creditTitle2.setPosition(50, 270);
        stage.addActor(creditTitle2);

        Label credit2 = new Label(
                "Dibujos para Catequesis\n" +
                "http://dibujosparacatequesis.blogspot.com",
                creditsLblStyle);
        credit2.setPosition(50, 210);
        stage.addActor(credit2);

        Label creditTitle3 = new Label("Iconos:", creditsTitleLblStyle);
        creditTitle3.setPosition(50, 160);
        stage.addActor(creditTitle3);

        Label credit3 = new Label(
                "Font Awesome\n" +
                "https://fontawesome.org",
                creditsLblStyle);
        credit3.setPosition(50, 100);
        stage.addActor(credit3);

        ImageTextButton.ImageTextButtonStyle backBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        backBtnStyle.up = skin.newDrawable(btnDrawable, Color.SCARLET);
        backBtnStyle.down = skin.newDrawable(btnDrawable, Color.FIREBRICK);
        backBtnStyle.font = arial16;
        skin.add("backBtn", backBtnStyle);

        Label backBtnLbl = new Label(" VOLVER ", skin);
        Image backBtnIcon = new Image((game.assets.get("icons/exit.png", Texture.class)));
        ImageTextButton backBtn = new ImageTextButton(null, skin, "backBtn");
        backBtn.add(backBtnIcon, backBtnLbl);
        backBtn.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        backBtn.setPosition(MAIN_MENU_BUTTON_X, EXIT_BUTTON_Y);
        backBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new HelpScreen(game));
            }
        });
        stage.addActor(backBtn);

        setBackColor(BACK_COLOR);
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