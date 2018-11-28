package com.marcosmiranda.purisima;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import static com.marcosmiranda.purisima.Constants.*;

class Pouch {

    boolean active = true;
    Sprite sprite;
    Rectangle rect;
    private final int bagImgWidth;
    private final Rectangle clickArea;
    private double accel;
    private final double movingSpeed;
    private Vector3 touchPos;

    Pouch(final AssetManager gameAssets) {
        sprite = new Sprite(gameAssets.get("sprites/pouch.png", Texture.class));
        bagImgWidth = (int) sprite.getWidth();
        rect = new Rectangle(
                WINDOW_HEIGHT - (bagImgWidth / 2),
                20,
                bagImgWidth,
                sprite.getHeight()
        );
        clickArea = new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT_HALF);
        touchPos = new Vector3();
        movingSpeed = 500;
        accel = 1f;
    }

    void update(OrthographicCamera camera) {
        double delta = Gdx.graphics.getDeltaTime();

        if (active) {
            // Move by touching the screen
            for (int i = 0; i < 20; i++) {
                if (Gdx.input.isTouched(i)) {
                    touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                    camera.unproject(touchPos);
                    if (clickArea.contains(touchPos.x, touchPos.y)) {
                        accel += MOVING_SPEED_SUM; // Moves gradually faster
                        if (touchPos.x < rect.x) { // If you touch left of the bag, you move to the left
                            rect.x -= movingSpeed * accel * delta;
                        } else { //if (touchPos.x > rect.x) { // If you touch to the right, you move to the right
                            rect.x += movingSpeed * accel * delta;
                        }
                    }
                } else {
                    accel = 1f;
                }
            }

            // Clamp the bag to the window if you go out of bounds
            if (rect.x < 0) rect.x = 0;
            else if (rect.x > WINDOW_WIDTH - bagImgWidth)
                rect.x = WINDOW_WIDTH - bagImgWidth;
        } else {
            rect = null;
        }
    }
}