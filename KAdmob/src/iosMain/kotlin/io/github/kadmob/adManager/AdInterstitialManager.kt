package io.github.kadmob.adManager

import cocoapods.KAdmob.InterstitialAdController
import io.github.kadmob.getCurrentUIViewController
import kotlinx.cinterop.ExportObjCClass
import platform.darwin.NSObject

@ExportObjCClass
class AdInterstitialManager : NSObject() {

    private var interstitialAdView: InterstitialAdController? = null

    fun loadInterstitialAd(adUnitId: String) {
        interstitialAdView = InterstitialAdController(adUnitID = adUnitId)
    }

    fun showAd() {
        interstitialAdView?.showInterstitialFrom(getCurrentUIViewController())

    }
}
