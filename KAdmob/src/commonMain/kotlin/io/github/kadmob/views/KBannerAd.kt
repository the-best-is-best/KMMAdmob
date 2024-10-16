package io.github.kadmob.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.kadmob.model.KAdmobBannerType

@Composable
expect fun KBannerAd(
    modifier: Modifier,
    type: KAdmobBannerType,
    adUnitId: String
)