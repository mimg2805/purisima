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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static com.marcosmiranda.purisima.Constants.*;
import static com.marcosmiranda.purisima.Utility.*;

class HistoryScreen implements Screen {

    private final Purisima game;
    private final Skin skin;
    private final Stage stage;

    HistoryScreen(final Purisima purisima) {
        game = purisima;

        skin = new Skin();
        stage = new Stage(new StretchViewport(WINDOW_WIDTH, WINDOW_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        Gdx.input.setCatchKey(Input.Keys.MENU, true);

        // Get fonts from the asset manager
        BitmapFont arial16 = game.assets.get("arial16.ttf", BitmapFont.class);
        BitmapFont comic18 = game.assets.get("comic18.ttf", BitmapFont.class);
        final Label.LabelStyle defaultLblStyle = new Label.LabelStyle(arial16, Color.WHITE);
        final Label.LabelStyle historyLblStyle = new Label.LabelStyle(comic18, Color.WHITE);
        skin.add("default", defaultLblStyle);
        skin.add("history", historyLblStyle);

        // Rounded rectangle button
        Image btnImage = new Image(game.assets.get("sprites/button.png", Texture.class));
        final Drawable btnDrawable = btnImage.getDrawable();

        final Label lblHistory1 = new Label(
                "Este juego está basado en la festividad nicaragüense de La Gritería.\n" +
                        "Se celebra cada 7 de diciembre, en la víspera de la solemnidad católica\n" +
                        "de La Inmaculada Concepción de María, también llamada \"La Purísima\".\n",
                historyLblStyle);

        final Label lblHistory2 = new Label(
                "Cuenta la leyenda que en 1562, don Pedro Alonso Sánchez de Cepeda y\n" +
                        "Ahumada, hermano de Santa Teresa de Jesús, viajó a Perú pero llegó\n" +
                        "al puerto El Realejo (en la actual Chinandega) debido a una tormenta,\n" +
                        " y que traía consigo una imagen de la Inmaculada Concepción. De ahí,\n" +
                        "viajó al pueblo de Tezoatega (la actual ciudad de El Viejo).\n" +
                        "Los indígenas locales estaban fascinados con la imagen, y no querían\n" +
                        "que se la llevara, por lo que don Pedro la dejó allí y se fue a Perú.\n",
                historyLblStyle);

        final Label lblHistory3 = new Label(
                "En este día, la gente recorre las calles cantando en honor a la Virgen,\n" +
                        "visitando altares eregidos en iglesias y casas particulares, gritando:\n" +
                        "\"¿Quién causa tanta alegría? ¡La Concepción de María!\"\n" +
                        "Usualmente los reciben con un \"brindis\", también llamado \"gorra\",\n" +
                        "que es un conjunto de regalos y dulces variados.\n",
                historyLblStyle);

        final Label lblHistory4 = new Label(
                "El origen de esta fiesta se dio el 7 de diciembre de 1857, en la iglesia\n" +
                        "San Felipe, en la ciudad de León. En este día, el monseñor Gordiano\n" +
                        "Carranza animó al pueblo a visitar todas las casas de la ciudad y a\n" +
                        "alzar altares para rezar, cantar y \"gritar\" a la Virgen.\n" +
                        "Inicialmente era una fiesta única de León, pero con el tiempo\n" +
                        "se esparció al resto del país.\n",
                historyLblStyle);

        final Label lblHistory5 = new Label(
                "Hoy en día, la fiesta tiene un gran significado cultural para muchos\n" +
                        "nicaragüenses, trascendiendo barreras religiosas. Es muy común ver a\n" +
                        "cristianos no católicos, no cristianos, y no creyentes en general,\n" +
                        "participar en la Gritería. Incluso la celebran los nicaragüenses\n" +
                        "en el extranjero, siendo más prevalente en Estados Unidos y Costa Rica.\n" +
                        "Podría compararse a muchas costumbres de otros países,\n" +
                        "como los cánticos navideños, o incluso Halloween.",
                historyLblStyle);

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

        final Table scrollTable = new Table();
        scrollTable.row();
        scrollTable.add(lblHistory1).left();
        scrollTable.row();
        scrollTable.add(lblHistory2).left();
        scrollTable.row();
        scrollTable.add(lblHistory3).left();
        scrollTable.row();
        scrollTable.add(lblHistory4).left();
        scrollTable.row();
        scrollTable.add(lblHistory5).left();
        final ScrollPane scroller = new ScrollPane(scrollTable);
        //scroller.setScrollingDisabled(true, false);
        final Table table = new Table();
        table.setBounds(HISTORY_TABLE_X, HISTORY_TABLE_Y, HISTORY_TABLE_WIDTH, HISTORY_TABLE_HEIGHT);
        table.add(scroller);
        stage.addActor(table);

        setBackColor(BACK_COLOR);
    }

    @Override
    public void render(float delta) {
        clear();
        stage.act(Math.min(delta, 1 / FRAME_RATE));
        stage.draw();
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
        stage.dispose();
        skin.dispose();
    }
}