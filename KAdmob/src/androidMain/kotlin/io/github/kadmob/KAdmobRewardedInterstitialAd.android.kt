package io.github.kadmob

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import io.github.kadmob.AndroidKAdmob.getActivity
import io.github.kadmob.model.KAdmobRewardItem
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual class KAdmobRewardedInterstitialAd actual constructor() {

    private var rewardedInterstitialAd: RewardedInterstitialAd? = null
    private var _adUnit: String? = null

    actual fun loadRewardedInterstitialAd(adUnitId: String) {
        val adRequest = AdRequest.Builder().build()

        RewardedInterstitialAd.load(
            getActivity(), adUnitId, adRequest,
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    rewardedInterstitialAd = ad
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    // Handle the error
                    rewardedInterstitialAd = null
                }
            })
    }

    actual suspend fun show(reloadNewAd: Boolean): Result<KAdmobRewardItem?> {
        return suspendCancellableCoroutine { cont ->

            rewardedInterstitialAd?.show(getActivity()) { rewardItem ->
                val reward = KAdmobRewardItem(
                    amount = rewardItem.amount,
                    type = rewardItem.type
                )
                cont.resume(Result.success(reward))
                if (reloadNewAd) {
                    loadRewardedInterstitialAd(_adUnit!!)
                }
            } ?: run {
                cont.resume(Result.failure(Exception("Ad not loaded")))

            }
        }
    }
}
