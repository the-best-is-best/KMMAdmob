package io.github.kadmob

import io.github.kadmob.adManager.AdRewardedInterstitialManager
import io.github.kadmob.model.KAdmobRewardItem

actual class KAdmobRewardedInterstitialAd actual constructor() {
    private val rewardedInterstitialAd = AdRewardedInterstitialManager()
    actual fun loadRewardedInterstitialAd(adUnitId: String) {
        rewardedInterstitialAd.loadInterstitialAd(adUnitId)
    }

    actual suspend fun show(reloadNewAd: Boolean): Result<KAdmobRewardItem?> {
        return rewardedInterstitialAd.showAd(reloadNewAd)
    }

}