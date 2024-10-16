package io.github.kadmob

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import io.github.kadmob.AndroidKAdmob.getActivity
import io.github.kadmob.model.KAdmobRewardItem

actual class KAdmobRewardedAd actual constructor() {
    private var rewardedAd: RewardedAd? = null

    actual fun loadRewardedAd(adUnitId: String) {
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

    actual fun show(callback: (Result<KAdmobRewardItem?>) -> Unit) {
        val activity = getActivity()
        if (rewardedAd != null) {
            rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    println("Rewarded ad dismissed.")
                }

                override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                    println("Failed to show rewarded ad: ${adError.message}")
                    callback(Result.failure(Exception(adError.message)))
                }

                override fun onAdShowedFullScreenContent() {
                    println("Rewarded ad is showing.")
                    rewardedAd = null // Reset the ad to load a new one after it's shown
                }
            }

            rewardedAd?.show(activity) { rewardItem: RewardItem ->
                // Convert RewardItem to KAdmobRewardItem and pass it in the callback
                val reward = KAdmobRewardItem(rewardItem.type, rewardItem.amount)
                callback(Result.success(reward))
            }
        } else {
            println("Rewarded ad is not ready yet.")
            callback(Result.failure(Exception("Rewarded ad is not ready.")))
        }
    }
}
