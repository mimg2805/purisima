package com.marcosmiranda.purisima;

import com.badlogic.gdx.graphics.Color;

public class Constants {

    static final float FRAME_RATE = 60f;
    static final int DEFAULT_VOLUME = 20;
    static final float VOLUME_DIVIDER = 100f;
    static final int VIBRATE_MILLISECONDS = 400;

    // window stuff
    static final int WINDOW_WIDTH = 800;
    static final int WINDOW_HEIGHT = 400;
    static final int WINDOW_WIDTH_HALF = WINDOW_WIDTH / 2;
    static final int WINDOW_HEIGHT_HALF = WINDOW_HEIGHT / 2;
    static final Color BACK_COLOR = Color.valueOf("003363");

    // main menu screen stuff
    static final int BUTTON_WIDTH = 200;
    static final int BUTTON_HEIGHT = 70;
    static final int LOGO_X = 80;
    static final int LOGO_Y = 25;
    static final int MAIN_MENU_BUTTON_X = 535;
    static final int EXIT_BUTTON_Y = 30;

    // options screen stuff
    static final int TEXTFIELD_WIDTH = 200;
    static final int TEXTFIELD_HEIGHT = 50;
    static final float TEXTFIELD_PADDING = 5f;
    static final float CURSOR_WIDTH = 2f;
    static final int CHECKBOX_SIZE = 48;
    static final int SMALL_BUTTON_SIZE = 48;

    // history screen
    static final int HISTORY_TABLE_X = 10;
    static final int HISTORY_TABLE_Y = 110;
    static final int HISTORY_TABLE_WIDTH = 740;
    static final int HISTORY_TABLE_HEIGHT = 280;

    // pause dialog stuff
    static final int PAUSE_BUTTON_WIDTH = 72;
    static final int PAUSE_BUTTON_HEIGHT = 72;
    static final int PAUSE_BUTTON_POSITION_X = 720;
    static final int PAUSE_BUTTON_POSITION_Y = 320;
    static final int DIALOG_BUTTON_WIDTH = 170;
    static final int DIALOG_BUTTON_HEIGHT = 60;
    static final int PAUSE_CONTINUE_BUTTON_X = 305;
    static final int PAUSE_EXIT_BUTTON_X = 495;
    static final int DIALOG_BUTTON_Y = 60;

    // game over screen stuff
    static final double NEW_HI_SCORE_BLINK_DELAY = 0.375;

    // goodie stuff
    static final int SPEEDUP_DELAY = 15;
    static final double GOODIE_SPAWN_DELAY = 1.5;
    static final double GOODIE_SPAWN_DELAY_MENU = 1;
    static final double GOODIE_SPAWN_DELAY_SUM = 0.125;
    static final int GOODIE_FALLING_SPEED = 2;
    static final int GOODIE_MAX_FALLING_SPEED = 12;
    static final int GOODIE_FALLING_SPEED_SUM = 1;
    static final int GOODIE_FALLING_SPEED_MENU = 3;
    static final int SPAWN_BORDER_SIZE = 20;
    static final float GOODIE_ROTATION = 2;

    // pouch stuff
    static final int POUCH_MOVING_SPEED = 600;
    static final double POUCH_MOVING_SPEED_SUM = 0.025;

    // points
    static final int MENTA_POINTS = 10;
    static final int GUMMY_POINTS = 10;
    static final int MALVAVISCO_POINTS = 20;
    static final int BASTON_POINTS = 30;
    static final int GOFIO_POINTS = 40;
    static final int CAJETA_POINTS = 40;
    static final int AGUA_LOJA_POINTS = 50;
    static final int CHICHA_POINTS = 50;
    static final int CANIA_AZUCAR_POINTS = 60;
    static final int NARANJA_POINTS = 60;
    static final int LAPIZ_POINTS = 70;
    static final int PELOTA_POINTS = 80;
    static final int MATRACA_POINTS = 90;
    static final int CHOCOLATE_POINTS = 100;
    static final int NACATAMAL_POINTS = 100;

    // message display stuff
    static final int NAME_LOCATION_X = 40;
    static final int NAME_LOCATION_Y = WINDOW_HEIGHT - NAME_LOCATION_X;
    static final int SCORE_LOCATION_X = 40;
    static final int SCORE_LOCATION_Y = WINDOW_HEIGHT - SCORE_LOCATION_X - 20;
    static final String SCORE_PREFIX = "Puntaje: ";
    static final int HI_SCORE_LOCATION_X = 40;
    static final int HI_SCORE_LOCATION_Y = WINDOW_HEIGHT - SCORE_LOCATION_X - 40;
    static final String HI_SCORE_PREFIX = "Puntaje máximo: ";
    static final int LEVEL_MESSAGE_LOCATION_X = WINDOW_WIDTH_HALF - 75;
    static final int LEVEL_MESSAGE_LOCATION_Y = SCORE_LOCATION_Y;

    // explosion stuff
    static final int EXPLOSION_ANIMATION_COLS = 4;
    static final int EXPLOSION_ANIMATION_ROWS = 7;
    //static final int EXPLOSION_ANIMATION_FRAMES = EXPLOSION_ANIMATION_COLS * EXPLOSION_ANIMATION_ROWS;
}