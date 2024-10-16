<h1 align="center">KAdmob</h1><br>
<div align="center">
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://android-arsenal.com/api?level=21" rel="nofollow">
    <img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat" style="max-width: 100%;">
</a>
  <img src="https://img.shields.io/badge/Platform-Android-brightgreen.svg?logo=android" alt="Badge Android" />
  <img src="https://img.shields.io/badge/iOS-13%2B-blue.svg?logo=apple" alt="iOS 13+ Badge" />

<a href="https://github.com/the-best-is-best/"><img alt="Profile" src="https://img.shields.io/badge/github-%23181717.svg?&style=for-the-badge&logo=github&logoColor=white" height="20"/></a>
</div>

### KAdmob is a Kotlin Multiplatform Mobile (KMM) library that enables seamless integration of Google AdMob advertisements in Android and iOS applications using a unified codebase. It provides a simplified interface for displaying banner ads, interstitial ads, and rewarded video ads across both platforms with minimal configuration

<hr>

[![Maven Central](https://img.shields.io/maven-central/v/io.github.the-best-is-best/kadmob)](https://central.sonatype.com/artifact/io.github.the-best-is-best/kadmob)

KAdmob is available on `mavenCentral()`.

## Install

```kotlin
implementation("io.github.the-best-is-best:kadmob:1.0.0-rc1")
```

## Need add this in pod file if not exist run ` pod init `

```pod
  pod 'KAdmob' , '0.1.0-13-rc'
```

## How to use it

### First in iosApp

```swift
GADMobileAds.sharedInstance().start(completionHandler: nil)
```

### Second in androidMain

```kotlin
        AndroidKAdmob.initialization(this)
```

### in commonMain

#### init unit ads id

```kotlin
  val interstitialAd = KAdmobInterstitialAd()
interstitialAd.loadInterstitialAd("ca-app-pub-7284367511062855/1974850970")

val rewardAd = KAdmobRewardedAd()
rewardAd.loadRewardedAd("ca-app-pub-3940256099942544/1712485313")

val rewardedInterstitialAd = KAdmobRewardedInterstitialAd()
rewardedInterstitialAd.loadRewardedInterstitialAd("ca-app-pub-3940256099942544/6978759866")

```

#### show ad

```kotlin
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

```

#### Note

- ad refreshed auto after show
