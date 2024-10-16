package io.github.kadmob

import android.app.Activity
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

object AndroidKAdmob {
    private var activity: WeakReference<Activity?> = WeakReference(null)

    internal fun getActivity(): Activity {
        return activity.get()!!
    }

    fun initialization(activity: Activity) {
        this.activity = WeakReference(activity)

        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch(Dispatchers.Main) {
            // Initialize the Google Mobile Ads SDK on the main thread.
            MobileAds.initialize(activity) {}
        }
    }
}
