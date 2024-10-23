package io.github.kadmob

import io.github.kadmob.adManager.AdRewardedManager
import io.github.kadmob.model.KAdmobRewardItem

actual class KAdmobRewardedAd actual constructor() {
    private val adManager = AdRewardedManager()
    actual fun loadRewardedAd(adUnitId: String) {
        adManager.loadInterstitialAd(adUnitId)
    }

    actual suspend fun show(reloadNewAd: Boolean): Result<KAdmobRewardItem?> {
        return adManager.showAd(reloadNewAd)
    }

}