package com.marcosmiranda.purisima;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static com.marcosmiranda.purisima.Utility.clear;
import static com.marcosmiranda.purisima.Utility.setBackColor;

class MoreAppsScreen implements Screen {

    private final Purisima game;
    private final Stage stage;
    private final Skin skin;

    MoreAppsScreen(final Purisima purisima) {
        game = purisima;
        setBackColor(Constants.BACK_COLOR);

        // Create the appropriate objects for drawing
        skin = new Skin();
        stage = new Stage(new StretchViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        Gdx.input.setCatchKey(Input.Keys.MENU, true);

        // Get fonts from the asset manager
        BitmapFont arial16 = game.assets.get("arial16.ttf", BitmapFont.class);
        BitmapFont arial18 = game.assets.get("arial18.ttf", BitmapFont.class);
        LabelStyle arial16LblStyle = new LabelStyle(arial16, Color.WHITE);
        LabelStyle arial18LblStyle = new LabelStyle(arial18, Color.WHITE);
        skin.add("default", arial16LblStyle);
        skin.add("sos", arial18LblStyle);

        // Rounded rectangle button
        Image btnImage = new Image(game.assets.get("sprites/button.png", Texture.class));
        Drawable btnDrawable = btnImage.getDrawable();

        // App images
        Image seniasnicasImg = new Image(game.assets.get("images/seniasnicas.png", Texture.class));
        seniasnicasImg.setScale(0.2f);
        seniasnicasImg.setPosition(20, 200);
        seniasnicasImg.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.adsController.openPlayStore("seniasnicas");
            }
        });
        stage.addActor(seniasnicasImg);

        Image cursoestadisticabasicaImg = new Image(game.assets.get("images/cursoestadisticabasica.png", Texture.class));
        cursoestadisticabasicaImg.setScale(0.2f);
        cursoestadisticabasicaImg.setPosition(400, 200);
        cursoestadisticabasicaImg.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.adsController.openPlayStore("cursoestadisticabasica");
            }
        });
        stage.addActor(cursoestadisticabasicaImg);

        Image nicaroadrageImg = new Image(game.assets.get("images/nicaroadrage.png", Texture.class));
        nicaroadrageImg.setScale(0.2f);
        nicaroadrageImg.setPosition(35, 100);
        nicaroadrageImg.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.adsController.openPlayStore("nicaroadrage");
            }
        });
        stage.addActor(nicaroadrageImg);

        // Texto
        Label moreApps1Lbl = new Label("Más Apps", arial18LblStyle);
        Label moreApps2Lbl = new Label("Click en la imagen para abrir", arial16LblStyle);
        moreApps1Lbl.setPosition(Constants.WINDOW_WIDTH_HALF - (moreApps1Lbl.getWidth() / 2), 360f);
        moreApps2Lbl.setPosition(Constants.WINDOW_WIDTH_HALF - (moreApps2Lbl.getWidth() / 2), 330f);
        stage.addActor(moreApps1Lbl);
        stage.addActor(moreApps2Lbl);

        int lblWidth = 250;

        Label seniasnicasTitleLbl = new Label("Señas Nicas", arial18LblStyle);
        Label seniasnicasDescLbl = new Label("Aprende y enseña lenguaje de señas nicaragüense (LSN)", arial16LblStyle);
        seniasnicasTitleLbl.setPosition(130, 280);
        seniasnicasDescLbl.setPosition(130, 235);
        seniasnicasTitleLbl.setWidth(lblWidth);
        seniasnicasDescLbl.setWidth(lblWidth);
        seniasnicasTitleLbl.setWrap(true);
        seniasnicasDescLbl.setWrap(true);
        stage.addActor(seniasnicasTitleLbl);
        stage.addActor(seniasnicasDescLbl);

        Label cursoestadisticabasicaTitleLbl = new Label("Curso Estadística Básica", arial18LblStyle);
        Label cursoestadisticabasicaDescLbl = new Label("Una guía para el aprendizaje de la estadística.", arial16LblStyle);
        cursoestadisticabasicaTitleLbl.setPosition(520, 280);
        cursoestadisticabasicaDescLbl.setPosition(520, 235);
        cursoestadisticabasicaTitleLbl.setWidth(lblWidth);
        cursoestadisticabasicaDescLbl.setWidth(lblWidth);
        cursoestadisticabasicaTitleLbl.setWrap(true);
        cursoestadisticabasicaDescLbl.setWrap(true);
        stage.addActor(cursoestadisticabasicaTitleLbl);
        stage.addActor(cursoestadisticabasicaDescLbl);

        Label nicaroadrageTitleLbl = new Label("Nica Road Rage", arial18LblStyle);
        Label nicaroadrageDescLbl = new Label("Un pequeño juego infinito de carreras para Android.", arial16LblStyle);
        nicaroadrageTitleLbl.setPosition(130, 135);
        nicaroadrageDescLbl.setPosition(130, 100);
        nicaroadrageTitleLbl.setWidth(lblWidth);
        nicaroadrageDescLbl.setWidth(lblWidth);
        nicaroadrageTitleLbl.setWrap(true);
        nicaroadrageDescLbl.setWrap(true);
        stage.addActor(nicaroadrageTitleLbl);
        stage.addActor(nicaroadrageDescLbl);

        ImageTextButtonStyle backBtnStyle = new ImageTextButtonStyle();
        backBtnStyle.up = skin.newDrawable(btnDrawable, Color.SCARLET);
        backBtnStyle.down = skin.newDrawable(btnDrawable, Color.FIREBRICK);
        backBtnStyle.font = arial16;
        skin.add("backBtn", backBtnStyle);

        Label backBtnLbl = new Label(" VOLVER ", skin);
        Image backBtnIcon = new Image(game.assets.get("icons/exit.png", Texture.class));
        ImageTextButton backBtn = new ImageTextButton(null, skin, "backBtn");
        backBtn.add(backBtnIcon, backBtnLbl);
        backBtn.setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        backBtn.setPosition(Constants.MAIN_MENU_BUTTON_X, Constants.EXIT_BUTTON_Y);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(backBtn);
    }

    @Override
    public void render(float delta) {
        clear();
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
        // dispose();
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