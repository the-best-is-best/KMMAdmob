package io.github.kadmob

import io.github.kadmob.model.KAdmobRewardItem

expect class KAdmobRewardedAd() {
    fun loadRewardedAd(adUnitId: String)
    suspend fun show(reloadNewAd: Boolean = false): Result<KAdmobRewardItem?>

}