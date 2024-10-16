package io.github.kadmob.adManager

import cocoapods.KAdmob.RewardedInterstitialAdController
import io.github.kadmob.getCurrentUIViewController
import io.github.kadmob.model.KAdmobRewardItem

class RewardedInterstitialManager {
    private var rewardedInterstitialView: RewardedInterstitialAdController? = null

    fun loadInterstitialAd(adUnitId: String) {
        rewardedInterstitialView = RewardedInterstitialAdController(adUnitID = adUnitId)
    }

    fun showAd(callback: (Result<KAdmobRewardItem?>) -> Unit) {
        rewardedInterstitialView?.showAdFrom(getCurrentUIViewController()) { rewardItem ->
            val reward: KAdmobRewardItem? = if (rewardItem == null) null else KAdmobRewardItem(
                rewardItem.type(),
                rewardItem.amount().toInt()
            )

            callback(Result.success(reward))


        }

    }

}