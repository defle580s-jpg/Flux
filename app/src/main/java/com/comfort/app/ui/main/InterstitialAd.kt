package com.comfort.app.ui.main

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object InterstitialManager {
    private var interstitialAd: InterstitialAd? = null
    private var adUnitId: String = "ca-app-pub-3087841597132483/3421544600"

    fun init(id: String) { adUnitId = id }

    fun load(context: Context) {
        InterstitialAd.load(context, adUnitId, AdRequest.Builder().build(), object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) { interstitialAd = ad }
            override fun onAdFailedToLoad(error: LoadAdError) { interstitialAd = null }
        })
    }

    fun show(activity: Activity, onDone: () -> Unit) {
        val ad = interstitialAd
        if (ad != null) {
            ad.fullScreenContentCallback = object : com.google.android.gms.ads.FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() { interstitialAd = null; load(activity); onDone() }
                override fun onAdFailedToShowFullScreenContent(e: com.google.android.gms.ads.AdError) { interstitialAd = null; load(activity); onDone() }
            }
            ad.show(activity)
        } else {
            load(activity)
            onDone()
        }
    }
}
