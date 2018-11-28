package com.marcosmiranda.purisima;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pool;

class PopUpString implements Pool.Poolable {

    boolean active;
    int x, y;
    String message;
    Color color;
    private boolean string = false;

    PopUpString() {
        message = null;
        color = null;
        x = 0;
        y = 0;
        active = false;
    }

    void init(Goodie goodie) {
        if (!goodie.deadly) {
            if (goodie.points > 0) {
                message = goodie.desc + ": + " + goodie.points;
                color = Color.GREEN;
            } else {
                message = goodie.desc + ": - " + (goodie.points * -1);
                color = Color.RED;
            }
            color.a = 1;
            x = goodie.x - 64;
            y = goodie.y + (goodie.y / 2);
            if (y <= 0) y = 50;
            active = true;
            string = false;
        }
    }

    void init(int x, int y, String message, Color color) {
        this.color = color;
        this.color.a = 1;
        this.message = message;
        this.x = x;
        this.y = y;
        active = true;
        string = true;
    }

    void update() {
        // Fade away message
        if (string) color.a -= 0.009;
        else color.a -= 0.04;
        if (color.a <= 0) active = false;
    }

    @Override
    public void reset() {
        message = null;
        color = null;
        x = 0;
        y = 0;
        active = false;
    }
}