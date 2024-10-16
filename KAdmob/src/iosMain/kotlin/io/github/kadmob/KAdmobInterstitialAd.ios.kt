package io.github.kadmob

import io.github.kadmob.adManager.AdInterstitialManager

actual class KAdmobInterstitialAd actual constructor() {
    private val adManager = AdInterstitialManager()
    actual fun loadInterstitialAd(adUnitId: String) {
        adManager.loadInterstitialAd(adUnitId)
    }

    actual fun showInterstitialAd() {
        adManager.showAd()
    }

}