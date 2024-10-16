package io.github.kadmob

import io.github.kadmob.model.KAdmobRewardItem

expect class KAdmobRewardedAd() {
    fun loadRewardedAd(adUnitId: String)
    fun show(callback: (Result<KAdmobRewardItem?>) -> Unit)

}