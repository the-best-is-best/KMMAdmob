package io.github.kadmob

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import io.github.kadmob.AndroidKAdmob.getActivity
import io.github.kadmob.model.KAdmobRewardItem
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual class KAdmobRewardedAd actual constructor() {
    private var rewardedAd: RewardedAd? = null
    private var _adUnit: String? = null

    actual fun loadRewardedAd(adUnitId: String) {
        _adUnit = adUnitId
        val adRequest = AdRequest.Builder().build()

        // Load the rewarded ad
        RewardedAd.load(
            getActivity(), // You need to provide an activity context
            adUnitId,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    println("Rewarded ad loaded successfully.")
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    println("Failed to load rewarded ad: ${adError.message}")
                }
            }
        )
    }

    actual suspend fun show(reloadNewAd: Boolean): Result<KAdmobRewardItem?> {
        return suspendCancellableCoroutine { cont ->

            val activity = getActivity()

            if (rewardedAd != null) {
                rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        println("Rewarded ad dismissed.")
                        if (reloadNewAd) {
                            loadRewardedAd(_adUnit!!) // Reload new ad if needed
                        }
                        // Resuming with null result to indicate dismissal
                        if (cont.isActive) cont.resume(Result.success(null))
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                        println("Failed to show rewarded ad: ${adError.message}")
                        if (cont.isActive) cont.resume(Result.failure(Exception(adError.message)))
                    }

                    override fun onAdShowedFullScreenContent() {
                        println("Rewarded ad is showing.")
                        rewardedAd = null // Reset the ad to load a new one after it's shown
                    }
                }

                rewardedAd?.show(activity) { rewardItem: RewardItem ->
                    val reward = KAdmobRewardItem(rewardItem.type, rewardItem.amount)
                    // Ensure the continuation is only resumed if it's still active
                    if (cont.isActive) {
                        cont.resume(Result.success(reward))
                    }
                    if (reloadNewAd) {
                        loadRewardedAd(_adUnit!!)
                    }
                }
            } else {
                println("Rewarded ad is not ready yet.")
                // Resume the continuation with failure if no ad is ready
                if (cont.isActive) cont.resume(Result.failure(Exception("Rewarded ad is not ready.")))
            }

            // Handle cancellation scenario to ensure proper cleanup
            cont.invokeOnCancellation {
                println("Ad display coroutine cancelled.")
            }
        }
    }

}
