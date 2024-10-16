package io.github.kadmob

import io.github.kadmob.model.KAdmobRewardItem

expect class KAdmobRewardedInterstitialAd() {
    fun loadRewardedInterstitialAd(adUnitId: String)
    fun show(callback: (Result<KAdmobRewardItem?>) -> Unit)

}