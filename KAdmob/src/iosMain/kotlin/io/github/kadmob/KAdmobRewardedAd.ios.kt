package io.github.kadmob

import io.github.kadmob.adManager.AdRewardedManager
import io.github.kadmob.model.KAdmobRewardItem

actual class KAdmobRewardedAd actual constructor() {
    val adManager = AdRewardedManager()
    actual fun loadRewardedAd(adUnitId: String) {
        adManager.loadInterstitialAd(adUnitId)
    }

    actual fun show(callback: (Result<KAdmobRewardItem?>) -> Unit) {
        adManager.showAd(callback)
    }

}