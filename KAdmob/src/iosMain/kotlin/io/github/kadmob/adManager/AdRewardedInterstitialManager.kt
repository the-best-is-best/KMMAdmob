package io.github.kadmob.adManager

import cocoapods.KAdmob.KRewardAdItem
import cocoapods.KAdmob.RewardedInterstitialAdController
import io.github.kadmob.getCurrentUIViewController
import io.github.kadmob.model.KAdmobRewardItem
import kotlinx.cinterop.ExportObjCClass
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.darwin.NSObject
import kotlin.coroutines.resume

@ExportObjCClass
class AdRewardedInterstitialManager : NSObject() {

    private var interstitialAdView: RewardedInterstitialAdController? = null


    fun loadInterstitialAd(adUnitId: String) {
        interstitialAdView = RewardedInterstitialAdController(adUnitID = adUnitId)
        interstitialAdView!!.loadRewardedInterstitialAd()
    }

    suspend fun showAd(reloadNewAd: Boolean): Result<KAdmobRewardItem?> {
        return suspendCancellableCoroutine { cont ->

            interstitialAdView?.showAdFrom(
                getCurrentUIViewController(),
                reloadNewAd
            ) { rewardItem: KRewardAdItem? ->

                // Handle nullability and type check for rewardItem
                val reward: KAdmobRewardItem? = rewardItem?.let {
                    KAdmobRewardItem(it.type(), it.amount().toInt())
                }

                cont.resume(Result.success(reward))
            }
        }
    }
}