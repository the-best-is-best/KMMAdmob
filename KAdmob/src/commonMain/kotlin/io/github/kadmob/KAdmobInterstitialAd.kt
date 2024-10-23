package io.github.kadmob

expect class KAdmobInterstitialAd() {
    fun loadInterstitialAd(adUnitId: String)

    fun showInterstitialAd(reloadNewAd: Boolean = false)
}







