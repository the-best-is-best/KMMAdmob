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
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.launch

@Composable
internal fun App() = AppTheme {
    val interstitialAd = remember { KAdmobInterstitialAd() }

    val rewardAd = remember { KAdmobRewardedAd() }
    rewardAd.loadRewardedAd("ca-app-pub-3940256099942544/1712485313")

    val rewardedInterstitialAd = remember { KAdmobRewardedInterstitialAd() }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedButton(onClick = {
            interstitialAd.loadInterstitialAd("ca-app-pub-7284367511062855/1974850970")

        }) {
            Text("load new ad interstitialAd")
        }
        ElevatedButton(onClick = {
            interstitialAd.showInterstitialAd()

        }) {
            Text("InterstitialAd")
        }
        ElevatedButton(onClick = {
            scope.launch {
                val res = rewardAd.show(true)
                res.onSuccess {
                    println("reward is $it")
                }
            }

        }) {
            Text("rewardAd")
        }
        ElevatedButton(onClick = {
            rewardedInterstitialAd.loadRewardedInterstitialAd("ca-app-pub-3940256099942544/6978759866")

        }) {
            Text("load new ad reward rewardedInterstitialAd")
        }
        ElevatedButton(onClick = {
            scope.launch {
                val res = rewardedInterstitialAd.show()
                res.onSuccess {
                    println("rewardedInterstitialAd is $it")
                }
                res.onFailure {
                    println("rewardedInterstitialAd error $it")
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
            adBannerUnitId = "ca-app-pub-3940256099942544/6300978111" // Test ID
        )


    }
}
