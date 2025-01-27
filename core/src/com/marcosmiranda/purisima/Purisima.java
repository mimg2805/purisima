package com.marcosmiranda.purisima;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Purisima extends Game {

    Application.ApplicationType platform;
    AssetManager assets;
    SpriteBatch batch;
    OrthographicCamera camera;
    GameState state;
    Music music;
    public AndroidController androidController;

    @Override
    public void create() {
        platform = Gdx.app.getType();
        assets = new AssetManager();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        music = Utility.selectMusic();

        this.setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        assets.dispose();
        batch.dispose();
        music.dispose();
    }
}
