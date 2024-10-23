package io.github.kadmob.adManager

import cocoapods.KAdmob.RewardedAdController
import io.github.kadmob.getCurrentUIViewController
import io.github.kadmob.model.KAdmobRewardItem
import kotlinx.cinterop.ExportObjCClass
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.darwin.NSObject
import kotlin.coroutines.resume

@ExportObjCClass
class AdRewardedManager : NSObject() {

    private var interstitialAdView: RewardedAdController? = null

    fun loadInterstitialAd(adUnitId: String) {
        interstitialAdView = RewardedAdController(adUnitID = adUnitId)
        interstitialAdView!!.loadRewardedAd()
    }

    suspend fun showAd(reloadNewAd: Boolean): Result<KAdmobRewardItem?> {
        return suspendCancellableCoroutine { cont ->

            interstitialAdView?.showAdFrom(
                getCurrentUIViewController(),
                reloadNewAd
            ) { rewardItem ->
                val reward: KAdmobRewardItem? = if (rewardItem == null) null else KAdmobRewardItem(
                    rewardItem.type(),
                    rewardItem.amount().toInt()
                )

                cont.resume(Result.success(reward))

            }
        }

    }
}