package io.github.kadmob

import android.app.Activity
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

object AndroidKAdmob {
    private var activity: WeakReference<Activity?> = WeakReference(null)

    internal fun getActivity(): Activity {
        return activity.get()!!
    }

    fun initialization(activity: Activity) {
        this.activity = WeakReference(activity)

        // Initialize AdMob on the main thread using coroutines
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                // Ensure activity is not null
                activity.let {
                    MobileAds.initialize(it) {}
                }
            }
        }
    }
}
