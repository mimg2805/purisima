package com.marcosmiranda.purisima;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

import java.util.Random;

class Goodie implements Pool.Poolable {

    boolean active = true;
    boolean deadly = false;
    Sprite sprite;
    Rectangle rect;
    int points;
    int x, y;
    String desc;
    double fallingSpeed;
    private AssetManager assets;
    private Random rand;

    Goodie(final AssetManager gameAssets) {
        this.assets = gameAssets;
        sprite = null;
        rect = null;
        desc = "";
        fallingSpeed = 0;
        points = 0;
        x = 0;
        y = 0;
        rand = new Random();
    }

    void init(double fallingSpeed, int limit) {
        this.fallingSpeed = fallingSpeed;// + ((float) rand.nextDouble() * Constants.GOODIE_FALLING_SPEED_RANGE);

        switch (rand.nextInt(99)) {
            case 0:
            case 16:
            case 30:
            case 43:
            case 55:
            case 66:
            case 76:
            case 84:
            case 90:
            case 94:
            case 97:
                desc = "Menta";
                sprite = new Sprite(assets.get("sprites/menta.png", Texture.class));
                points = Constants.MENTA_POINTS;
                //deadly = true;
                break;
            case 1:
            case 17:
            case 31:
            case 44:
            case 56:
            case 67:
            case 77:
            case 85:
            case 91:
            case 95:
            case 98:
                desc = "Gummy";
                sprite = new Sprite(assets.get("sprites/gummy.png", Texture.class));
                points = Constants.GUMMY_POINTS;
                //deadly = true;
                break;
            case 2:
            case 18:
            case 32:
            case 45:
            case 57:
            case 68:
            case 78:
            case 86:
            case 92:
            case 96:
                desc = "Malvavisco";
                sprite = new Sprite(assets.get("sprites/malvavisco.png", Texture.class));
                points = Constants.MALVAVISCO_POINTS;
                //deadly = true;
                break;
            case 3:
            case 19:
            case 33:
            case 46:
            case 58:
            case 69:
            case 79:
            case 87:
            case 93:
                desc = "Bastón";
                sprite = new Sprite(assets.get("sprites/baston.png", Texture.class));
                points = Constants.BASTON_POINTS;
                //deadly = true;
                break;
            case 4:
            case 20:
            case 34:
            case 47:
            case 59:
            case 70:
            case 80:
            case 88:
                desc = "Gofio";
                sprite = new Sprite(assets.get("sprites/gofio.png", Texture.class));
                points = Constants.GOFIO_POINTS;
                //deadly = true;
                break;
            case 5:
            case 21:
            case 35:
            case 48:
            case 60:
            case 71:
            case 81:
            case 89:
                desc = "Cajeta";
                sprite = new Sprite(assets.get("sprites/cajeta.png", Texture.class));
                points = Constants.CAJETA_POINTS;
                //deadly = true;
                break;
            case 6:
            case 22:
            case 36:
            case 49:
            case 61:
            case 72:
            case 82:
                desc = "Agua loja";
                sprite = new Sprite(assets.get("sprites/agua_loja.png", Texture.class));
                points = Constants.AGUA_LOJA_POINTS;
                //deadly = true;
                break;
            case 7:
            case 23:
            case 37:
            case 50:
            case 62:
            case 73:
            case 83:
                desc = "Chicha";
                sprite = new Sprite(assets.get("sprites/chicha.png", Texture.class));
                points = Constants.CHICHA_POINTS;
                //deadly = true;
                break;
            case 8:
            case 24:
            case 38:
            case 51:
            case 63:
            case 74:
                desc = "Caña de azúcar";
                sprite = new Sprite(assets.get("sprites/cania_azucar.png", Texture.class));
                points = Constants.CANIA_AZUCAR_POINTS;
                //deadly = true;
                break;
            case 9:
            case 25:
            case 39:
            case 52:
            case 64:
            case 75:
                desc = "Naranja";
                sprite = new Sprite(assets.get("sprites/naranja.png", Texture.class));
                points = Constants.NARANJA_POINTS;
                //deadly = true;
                break;
            case 10:
            case 26:
            case 40:
            case 53:
            case 65:
                desc = "Lápiz";
                sprite = new Sprite(assets.get("sprites/lapiz.png", Texture.class));
                points = Constants.LAPIZ_POINTS;
                //deadly = true;
                break;
            case 11:
            case 27:
            case 41:
            case 54:
                desc = "Pelota";
                sprite = new Sprite(assets.get("sprites/pelota.png", Texture.class));
                points = Constants.PELOTA_POINTS;
                //deadly = true;
                break;
            case 12:
            case 28:
            case 42:
                desc = "Matraca";
                sprite = new Sprite(assets.get("sprites/matraca.png", Texture.class));
                points = Constants.MATRACA_POINTS;
                //deadly = true;
                break;
            case 13:
            case 29:
                sprite = new Sprite(assets.get("sprites/petardo.png", Texture.class));
                points = 0;
                deadly = true;
                break;
            case 14:
                desc = "Chocolate";
                sprite = new Sprite(assets.get("sprites/chocolate.png", Texture.class));
                points = Constants.CHOCOLATE_POINTS;
                //deadly = true;
                break;
            case 15:
                desc = "Nacatamal";
                sprite = new Sprite(assets.get("sprites/nacatamal.png", Texture.class));
                points = Constants.NACATAMAL_POINTS;
                //deadly = true;
                break;
        }

        int width = (int) sprite.getWidth();
        int height = (int) sprite.getHeight();
        x = GetRandomLocation(Constants.SPAWN_BORDER_SIZE, limit - width - Constants.SPAWN_BORDER_SIZE);
        y = Constants.WINDOW_HEIGHT + 10;
        rect = new Rectangle(x, y, width, height);
        sprite.setPosition(x, y);
    }

    void update() {
        if (active) {
            y -= (int) (fallingSpeed);
            rect.y = y;
            sprite.setPosition(x, y);
            sprite.rotate(Constants.GOODIE_ROTATION);
        }
    }

    @Override
    public void reset() {
        points = 0;
        fallingSpeed = 0;
        x = 0;
        y = 0;
        active = false;
        desc = "";
        sprite = null;
        deadly = false;
    }

    private int GetRandomLocation(int min, int range) {
        return min + rand.nextInt(range - min);
    }
}