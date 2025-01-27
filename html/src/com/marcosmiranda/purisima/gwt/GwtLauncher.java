package com.marcosmiranda.purisima.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.freetype.gwt.FreetypeInjector;
import com.marcosmiranda.purisima.Purisima;
import com.marcosmiranda.purisima.Constants;

/** Launches the GWT application. */
public class GwtLauncher extends GwtApplication {
        @Override
        public GwtApplicationConfiguration getConfig () {
            // Resizable application, uses available space in browser with no padding:
            // GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(true);
            // cfg.padVertical = 50;
            // cfg.padHorizontal = 50;
            // return cfg;

            // If you want a fixed size application, comment out the above resizable section,
            // and uncomment below:
            return new GwtApplicationConfiguration(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () { return new Purisima(); }

        @Override
        public void onModuleLoad () {
            // Replace HtmlLauncher with the class name
            // If your class is called FooBar.java than the line should be FooBar.super.onModuleLoad();
            FreetypeInjector.inject(GwtLauncher.super::onModuleLoad);
        }
}
