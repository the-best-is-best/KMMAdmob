package io.github.kadmob.adManager

import cocoapods.KAdmob.KRewardAdItem
import cocoapods.KAdmob.RewardedInterstitialAdController
import io.github.kadmob.getCurrentUIViewController
import io.github.kadmob.model.KAdmobRewardItem
import kotlinx.cinterop.ExportObjCClass
import platform.darwin.NSObject

@ExportObjCClass
class AdRewardedInterstitialManager : NSObject() {

    private var interstitialAdView: RewardedInterstitialAdController? = null

    fun loadInterstitialAd(adUnitId: String) {
        interstitialAdView = RewardedInterstitialAdController(adUnitID = adUnitId)
    }

    fun showAd(callback: (Result<KAdmobRewardItem?>) -> Unit) {
        interstitialAdView?.showAdFrom(getCurrentUIViewController()) { rewardItem: KRewardAdItem? ->

            // Handle nullability and type check for rewardItem
            val reward: KAdmobRewardItem? = rewardItem?.let {
                KAdmobRewardItem(it.type(), it.amount().toInt())
            }

            callback(Result.success(reward))
        }
    }
}