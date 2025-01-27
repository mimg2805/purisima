package com.marcosmiranda.purisima;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

import java.util.Random;

final class Utility {

    // clear the screen
    static void clear() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    // set background color
    static void setBackColor(Color color) {
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        clear();
    }

    // creates fonts with the given size and loads them to the asset manager
    static void createFonts(AssetManager assets, String fontFileName, int size, float borderWidth, Color borderColor) {
        final FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter.fontFileName = "fonts/" + fontFileName + ".ttf";
        parameter.fontParameters.size = size;
        parameter.fontParameters.borderWidth = borderWidth;
        parameter.fontParameters.borderColor = borderColor;
        parameter.fontParameters.borderStraight = true;
        final String assetName = fontFileName + size + ".ttf";
        assets.load(assetName, BitmapFont.class, parameter);
        //assets.finishLoading();
    }

    static Music selectMusic() {
        String path = "music/";
        final Random rand = new Random();
        switch (rand.nextInt(11)) {
            case 0:
                path += "adiosreinadelcielo.ogg";
                break;
            case 1:
                path += "alabado.ogg";
                break;
            case 2:
                path += "dulceshimnos.ogg";
                break;
            case 3:
                path += "ohvirgendeconcepcion.ogg";
                break;
            case 4:
                path += "poresoelcristianismo.ogg";
                break;
            case 5:
                path += "puesconcebida.ogg";
                break;
            case 6:
                path += "sagradareinadelcielo.ogg";
                break;
            case 7:
                path += "salvesalve.ogg";
                break;
            case 8:
                path += "salvevirgenbella.ogg";
                break;
            case 9:
                path += "tugloria.ogg";
                break;
            case 10:
                path += "virgencitaincomparable.ogg";
                break;
        }

        return Gdx.audio.newMusic(Gdx.files.internal(path));
    }
}
