package io.github.kadmob

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import io.github.kadmob.AndroidKAdmob.getActivity

actual class KAdmobInterstitialAd actual constructor() {
    private var interstitialAd: InterstitialAd? = null

    actual fun loadInterstitialAd(adUnitId: String) {
        val adRequest = AdRequest.Builder().build()

        // Load the interstitial ad
        InterstitialAd.load(
            getActivity(), // You need to provide an activity context
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    println("Interstitial ad loaded successfully.")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    println("Failed to load interstitial ad: ${error.message}")
                }
            }
        )
    }

    actual fun showInterstitialAd() {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    println("Ad dismissed.")
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    println("Ad failed to show.")
                }

                override fun onAdShowedFullScreenContent() {
                    println("Ad is showing.")
                    interstitialAd =
                        null // Reset the interstitial ad to load a new one after it's shown
                }
            }
            interstitialAd?.show(getActivity())
        } else {
            println("Interstitial ad is not ready yet.")
        }
    }

}
