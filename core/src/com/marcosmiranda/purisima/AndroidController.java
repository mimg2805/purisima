package com.marcosmiranda.purisima;

public interface AndroidController {
    // void showBannerAd();
    // void hideBannerAd();
    // boolean isAdLoaded();
    boolean isInterstitialLoaded();
    void loadInterstitial();
    void showInterstitial();
    boolean isWifiOn();
    boolean isDataOn();
    void openPlayStore();
}