package io.github.kadmob.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import io.github.kadmob.model.KAdmobBannerType

@Composable
actual fun KBannerAd(modifier: Modifier, type: KAdmobBannerType, adUnitId: String) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            AdView(context).apply {
                // Set ad size based on the provided banner type
                val adSize = when (type) {
                    KAdmobBannerType.BANNER -> AdSize.BANNER
                    KAdmobBannerType.FULL_BANNER -> AdSize.FULL_BANNER
                    KAdmobBannerType.LARGE_BANNER -> AdSize.LARGE_BANNER
                }
                setAdSize(adSize)
                // Set the ad unit ID
                this.adUnitId = adUnitId
                // Load the ad
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}
