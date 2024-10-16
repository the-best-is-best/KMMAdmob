package io.github.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kadmob.KAdmobInterstitialAd
import io.github.kadmob.KAdmobRewardedAd
import io.github.kadmob.KAdmobRewardedInterstitialAd
import io.github.kadmob.model.KAdmobBannerType
import io.github.kadmob.views.KBannerAd
import io.github.sample.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    val interstitialAd = KAdmobInterstitialAd()
    interstitialAd.loadInterstitialAd("ca-app-pub-7284367511062855/1974850970")

    val rewardAd = KAdmobRewardedAd()
    rewardAd.loadRewardedAd("ca-app-pub-3940256099942544/1712485313")

    val rewardedInterstitialAd = KAdmobRewardedInterstitialAd()
    rewardedInterstitialAd.loadRewardedInterstitialAd("ca-app-pub-3940256099942544/6978759866")
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedButton(onClick = {
            interstitialAd.showInterstitialAd()
        }) {
            Text("InterstitialAd")
        }
        ElevatedButton(onClick = {
            rewardAd.show {
                it.onSuccess {
                    println("reward is $it")
                }
            }
        }) {
            Text("rewardAd")
        }
        ElevatedButton(onClick = {
            rewardedInterstitialAd.show {
                it.onSuccess {
                    println("rewardedInterstitialAd is $it")
                }
            }
        }) {
            Text("rewardedInterstitialAd")
        }
        KBannerAd(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            type = KAdmobBannerType.LARGE_BANNER,
            adUnitId = "ca-app-pub-3940256099942544/6300978111" // Test ID
        )


    }
}
