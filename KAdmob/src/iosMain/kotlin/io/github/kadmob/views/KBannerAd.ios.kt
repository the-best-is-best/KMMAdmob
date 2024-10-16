package io.github.kadmob.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.KAdmob.KBannerAdView
import io.github.kadmob.model.KAdmobBannerType
import kotlinx.cinterop.useContents

@Composable
actual fun KBannerAd(modifier: Modifier, type: KAdmobBannerType, adUnitId: String) {
    UIKitView(
        modifier = modifier,
        factory = {

            // Create a CGRect for the banner ad

            // Initialize the KBannerAdView
            val bannerAdView = KBannerAdView(adUnitId)
            // Load the ad
            bannerAdView.loadAdOfType(type.ordinal.toLong())


            // Return the banner ad view
            bannerAdView
        },
    )
}