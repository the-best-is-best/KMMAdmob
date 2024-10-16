package io.github.kadmob

import io.github.kadmob.adManager.RewardedInterstitialManager
import io.github.kadmob.model.KAdmobRewardItem

actual class KAdmobRewardedInterstitialAd actual constructor() {
    val rewardedInterstitialAd = RewardedInterstitialManager()
    actual fun loadRewardedInterstitialAd(adUnitId: String) {
        rewardedInterstitialAd.loadInterstitialAd(adUnitId)
    }

    actual fun show(callback: (Result<KAdmobRewardItem?>) -> Unit) {
        rewardedInterstitialAd.showAd(callback)
    }

}