package io.github.kadmob.adManager

import cocoapods.KAdmob.RewardedAdController
import io.github.kadmob.getCurrentUIViewController
import io.github.kadmob.model.KAdmobRewardItem
import kotlinx.cinterop.ExportObjCClass
import platform.darwin.NSObject

@ExportObjCClass
class AdRewardedManager : NSObject() {

    private var interstitialAdView: RewardedAdController? = null

    fun loadInterstitialAd(adUnitId: String) {
        interstitialAdView = RewardedAdController(adUnitID = adUnitId)
    }

    fun showAd(callback: (Result<KAdmobRewardItem?>) -> Unit) {
        interstitialAdView?.showAdFrom(getCurrentUIViewController()) { rewardItem ->
            val reward: KAdmobRewardItem? = if (rewardItem == null) null else KAdmobRewardItem(
                rewardItem.type(),
                rewardItem.amount().toInt()
            )

            callback(Result.success(reward))


        }

    }
}