package io.github.kadmob

import io.github.kadmob.model.KAdmobRewardItem

expect class KAdmobRewardedInterstitialAd() {
    fun loadRewardedInterstitialAd(adUnitId: String)
    suspend fun show(reloadNewAd: Boolean = false): Result<KAdmobRewardItem?>

}