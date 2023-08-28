package com.marcosmiranda.purisima.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import java.util.ArrayList;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import com.marcosmiranda.purisima.AdsController;
import com.marcosmiranda.purisima.Purisima;

public class AndroidLauncher extends AndroidApplication implements AdsController {

    private static final String APP_ID = "ca-app-pub-2838402743054690~1213936759";
    private static final String AD_UNIT_ID = "ca-app-pub-2838402743054690/7915076470";
    // private static final String AD_UNIT_TEST_ID = "ca-app-pub-3940256099942544/6300978111"; // Test
    protected AdView adView;
    protected View gameView;
    int backColor = Color.rgb(0, 26, 99);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // LibGDX Android config
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.useGyroscope = false;
        cfg.useImmersiveMode = true;
        cfg.useRotationVectorSensor = false;
        cfg.useWakelock = true;

        // Initialize ads
        // MobileAds.initialize(this);
        MobileAds.initialize(this, initializationStatus -> {});

        // Do the stuff that initialize() would do for you
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        RelativeLayout layout = new RelativeLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);

        AdView admobView = createAdView();
        layout.addView(admobView);
        View gameView = createGameView(cfg);
        layout.addView(gameView);

        setContentView(layout);
        startAdvertising(admobView);

        // initialize(new Purisima(), cfg);
    }

    private AdView createAdView() {
        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        // adView.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, 800));
        adView.setAdUnitId(AD_UNIT_ID);
        // adView.setAdUnitId(AD_UNIT_TEST_ID);
        int id = 12345;
        adView.setId(id); // this is an arbitrary id, allows for relative positioning in createGameView()
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        adView.setLayoutParams(params);
        adView.setBackgroundColor(backColor);
        return adView;
    }

    private View createGameView(AndroidApplicationConfiguration cfg) {
        gameView = initializeForView(new Purisima(this), cfg);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ABOVE, adView.getId());
        gameView.setLayoutParams(params);
        return gameView;
    }

    private void startAdvertising(AdView adView) {
        ArrayList<String> testDevices = new ArrayList<>();
        testDevices.add("BE89C404157C24CCDB17A860A9B5B878");

        RequestConfiguration reqConf = new RequestConfiguration.Builder()
                .setTestDeviceIds(testDevices)
                .build();
        MobileAds.setRequestConfiguration(reqConf);

        adView.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) adView.resume();
    }

    @Override
    public void onPause() {
        if (adView != null) adView.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (adView != null) adView.destroy();
        super.onDestroy();
    }

    @Override
    public void showBannerAd() {
        runOnUiThread(() -> {
            startAdvertising(adView);
            adView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void hideBannerAd() {
        runOnUiThread(() -> {
            adView.setVisibility(View.INVISIBLE);
            // adView.setVisibility(View.GONE);
        });
    }

    @Override
    public boolean isWifiOn() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();
        NetworkCapabilities nc = cm.getNetworkCapabilities(network);
        return nc != null && nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
    }

    @Override
    public boolean isDataOn() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();
        NetworkCapabilities nc = cm.getNetworkCapabilities(network);
        return nc != null && nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
    }

    @Override
    public void openPlayStore(String name) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.marcosmiranda." + name)));
    }
}