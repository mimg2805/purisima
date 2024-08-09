package com.marcosmiranda.purisima;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Purisima extends Game {

    AssetManager assets;
    SpriteBatch batch;
    OrthographicCamera camera;
    GameState state;
    Music music;
    AndroidController androidController;

    public Purisima(AndroidController androidController){
        this.androidController = androidController;
    }

    @Override
    public void create() {
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