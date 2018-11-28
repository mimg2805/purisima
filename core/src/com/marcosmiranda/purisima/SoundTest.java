package com.marcosmiranda.purisima;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class SoundTest implements Screen {

    private final Purisima game;
    private Stage stage;
    private Skin skin;
    private int fileIndex;
    private String fileName;
    private Music music;

    SoundTest(final Purisima purisima) {
        // Store the passed game instance for later use
        game = purisima;
        Utility.setBackColor(Constants.BACK_COLOR);

        // List music files
        final String path = "music/";
        final FileHandle[] files = Gdx.files.internal("music/").list();
        fileIndex = 0;
        fileName = files[fileIndex].name();
        music = Gdx.audio.newMusic(Gdx.files.internal(path + fileName));
        music.setVolume(20/100f);
        music.setLooping(true);

        // Create the appropiate objects for drawing
        skin = new Skin();
        stage = new Stage(new StretchViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        // Add a white pixmap to the skin
        Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        String pixmapName = "white";
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add(pixmapName, new Texture(pixmap));

        // Get fonts from the asset manager & build the various element styles
        BitmapFont arial16 = game.assets.get("arial16.ttf", BitmapFont.class);
        BitmapFont arial18 = game.assets.get("arial18.ttf", BitmapFont.class);
        BitmapFont arial24 = game.assets.get("arial24.ttf", BitmapFont.class);
        Label.LabelStyle arial16LblStyle = new Label.LabelStyle(arial16, Color.WHITE);
        Label.LabelStyle arial18LblStyle = new Label.LabelStyle(arial18, Color.WHITE);
        Label.LabelStyle arial24LblStyle = new Label.LabelStyle(arial24, Color.WHITE);
        skin.add("arial16", arial16LblStyle);
        skin.add("arial18", arial18LblStyle);
        skin.add("arial24", arial24LblStyle);

        ImageButton.ImageButtonStyle prevSongBtnStyle = new ImageButton.ImageButtonStyle();
        prevSongBtnStyle.up = skin.newDrawable(pixmapName, Color.SCARLET);
        prevSongBtnStyle.down = skin.newDrawable(pixmapName, Color.FIREBRICK);
        skin.add("prevSongBtn", prevSongBtnStyle);

        ImageButton.ImageButtonStyle playSongBtnStyle = new ImageButton.ImageButtonStyle();
        playSongBtnStyle.up = skin.newDrawable(pixmapName, Color.LIME);
        playSongBtnStyle.down = skin.newDrawable(pixmapName, Color.FOREST);
        skin.add("playSongBtn", playSongBtnStyle);

        ImageButton.ImageButtonStyle pauseSongBtnStyle = new ImageButton.ImageButtonStyle();
        pauseSongBtnStyle.up = skin.newDrawable(pixmapName, Color.SCARLET);
        pauseSongBtnStyle.down = skin.newDrawable(pixmapName, Color.FIREBRICK);
        skin.add("pauseSongBtn", pauseSongBtnStyle);

        ImageButton.ImageButtonStyle nextSongBtnStyle = new ImageButton.ImageButtonStyle();
        nextSongBtnStyle.up = skin.newDrawable(pixmapName, Color.LIME);
        nextSongBtnStyle.down = skin.newDrawable(pixmapName, Color.FOREST);
        skin.add("nextSongBtn", nextSongBtnStyle);

        // Create the elements
        final Label headerLbl = new Label(" SOUND TEST ", skin, "arial24");
        headerLbl.setPosition(310, 335);
        stage.addActor(headerLbl);

        final Label songNameLbl = new Label(fileName, skin, "arial16");
        songNameLbl.setPosition(320, 275);
        stage.addActor(songNameLbl);

        final int btnSize = 48;

        final Image prevSongBtnIcon = new Image(game.assets.get("icons/arrow-left.png", Texture.class));
        final ImageButton prevSongBtn = new ImageButton( skin, "prevSongBtn");
        prevSongBtn.add(prevSongBtnIcon);
        prevSongBtn.setSize(btnSize, btnSize);
        prevSongBtn.setPosition(240, 30);
        prevSongBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if(fileIndex > 0){
                    fileIndex--;
                }
                fileName = files[fileIndex].name();
                songNameLbl.setText(fileName);
                music.stop();
                music = Gdx.audio.newMusic(Gdx.files.internal(path + fileName));
            }
        });
        stage.addActor(prevSongBtn);

        final Image playSongBtnIcon = new Image(game.assets.get("icons/play.png", Texture.class));
        final ImageButton playSongBtn = new ImageButton(skin, "playSongBtn");
        playSongBtn.add(playSongBtnIcon);
        playSongBtn.setSize(btnSize, btnSize);
        playSongBtn.setPosition(340, 30);
        playSongBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if (!music.isPlaying()) music.play();
            }
        });
        stage.addActor(playSongBtn);

        final Image pauseSongBtnIcon = new Image(game.assets.get("icons/pause.png", Texture.class));
        final ImageButton pauseSongBtn = new ImageButton(skin, "pauseSongBtn");
        pauseSongBtn.add(pauseSongBtnIcon);
        pauseSongBtn.setSize(btnSize, btnSize);
        pauseSongBtn.setPosition(440, 30);
        pauseSongBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if (music.isPlaying()) music.pause();
            }
        });
        stage.addActor(pauseSongBtn);

        final Image nextSongBtnIcon = new Image(game.assets.get("icons/arrow-right.png", Texture.class));
        final ImageButton nextSongBtn = new ImageButton(skin, "nextSongBtn");
        nextSongBtn.add(nextSongBtnIcon);
        nextSongBtn.setSize(btnSize, btnSize);
        nextSongBtn.setPosition(540, 30);
        nextSongBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                int len = files.length - 1;
                if(fileIndex < len){
                    fileIndex++;
                }
                fileName = files[fileIndex].name();
                songNameLbl.setText(fileName);
                music.stop();
                music = Gdx.audio.newMusic(Gdx.files.internal(path + fileName));
            }
        });
        stage.addActor(nextSongBtn);
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
        stage.dispose();
        skin.dispose();
    }
}